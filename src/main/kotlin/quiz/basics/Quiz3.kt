package quiz.basics

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object Quiz3 {
    fun getNumberFeed() = flow<Int> {
        for (i in 1..4) {
            emit(i)
            delay(300)
        }

    }
}

fun main(args: Array<String>) {
    runBlocking {
        GlobalScope.launch {
            Quiz3.getNumberFeed().collect {
                println(it)
            }
        }.join()
    }
}
/**
 * What will this output?
 *
 * a) 4 3 2 1
 * b) 1 2 3 4
 * c) Nothing
 * d) Exception
 */



