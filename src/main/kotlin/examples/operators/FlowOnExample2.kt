package examples.operators

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import util.ThreadUtil
import java.util.concurrent.Executors

fun main() {
    runBlocking {
        with(FlowOnExample) {
            GlobalScope.launch(mainDispatcher) {
                networkFlow
                    .flowOn(ioDispatcher)
                    .flowOn(mainDispatcher)
                    .flowOn(defaultDispatcher)
                    .collect {
                        withContext(ioDispatcher) {
                            printCurrentThreadName()
                        }
                    }
            }.join()
        }
    }
}