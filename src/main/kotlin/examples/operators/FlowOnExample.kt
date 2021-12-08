package examples.operators

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import util.ThreadUtil
import java.util.concurrent.Executors

object FlowOnExample {

    fun printCurrentThreadName() =
        println("Thread ${Thread.currentThread().name}")

    val ioDispatcher by lazy {
        ThreadUtil.createDispatcherWithName("io")
    }
    val mainDispatcher by lazy {
        ThreadUtil.createDispatcherWithName("main")
    }
    val defaultDispatcher by lazy {
        ThreadUtil.createDispatcherWithName("default")
    }
    val networkFlow = flow {
        printCurrentThreadName()
        emit(1)
    }.flowOn(defaultDispatcher)
}

fun main() {
    runBlocking {
        with(FlowOnExample) {
            GlobalScope.launch(mainDispatcher) {
                networkFlow
                    .flowOn(ioDispatcher)
                    .collect {
                        printCurrentThreadName()
                    }
                println()
                networkFlow
                    .flowOn(mainDispatcher)
                    .collect {
                        printCurrentThreadName()
                    }
                println()
                networkFlow
                    .flowOn(defaultDispatcher)
                    .collect {
                        printCurrentThreadName()
                    }
            }.join()
        }
    }
}