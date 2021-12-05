package quiz.multipleflow

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object Example1 {
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
        GlobalScope.launch {
            Example1.getNumberFeed().collect {
                println(it)
            }
            Example1.getLetterFeed().collect {
                println(it)

            }
        }.join()
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


/**
 *
 *
 *
 *
 *
 *
 *
 *
 * Explanation:
 * `collect` is a suspending function. The second collection doesn't finish until the first collection is finished.
 */