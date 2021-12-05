package quiz.multipleflow

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object Example2 {
    fun getNumberFeed() = flow<Int> {
        for (i in 1..4) {
            emit(i)
            delay(300)
        }

    }
    fun getLetterFeed() = flow<Char> {
        for (i in listOf('a','b','c','d')) {
            emit(i)
            delay(300)
        }

    }
}

fun main(args: Array<String>) {
    runBlocking {
        val numberJob = GlobalScope.launch {
            Example2.getNumberFeed().collect {
                println(it)
            }
        }
        val letterJob = GlobalScope.launch {
                Example2.getLetterFeed().collect {
                    println(it)
                }
        }
        numberJob.join()
        letterJob.join()
    }
}
/**
 * What will this output?
 *
 * a) 1 a 2 b 3 c 4 d
 * b) a b c d 1 2 3 4
 * c) a 1 b 2 c 3 d 4
 * d) 1 2 3 4 a b c d
 * e) Race condition versions of (a)/(c)
 */