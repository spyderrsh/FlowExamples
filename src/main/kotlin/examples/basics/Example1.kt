package examples.basics

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

object Example1 {
    fun getNumbersFlow() = flow {
        for (i in 0 until  10) {
            delay(500)
            emit(i)
        }
    }
}

fun main() {
    return runBlocking {
        GlobalScope.launch {
            println("Start Collection")
            Example1.getNumbersFlow().collect {
                println(it)
            }
            println("Stop Collection")
        }.join()
    }
}