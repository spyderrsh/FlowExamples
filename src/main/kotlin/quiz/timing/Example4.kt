package quiz.timing

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object Example4 {
    fun getNumberFeed() = flow<Int> {
        for (i in 1..4) {
            println(i)
            emit(i)
            delay(300)
        }

    }.conflate()
}

fun main() {
    runBlocking {
        GlobalScope.launch {
            Example4.getNumberFeed()
                .collect {
                    delay(500)
                    println("$it!")
                }
        }.join()
    }
}

/**
 * What will this output?
 *
 * a) 1 1! 2 2! 3 3! 4 4!
 * b) 1 2 1! 3 4 2! 4!
 * c) 1 2 1! 3 4 2! 3!
 * d) 1 2 3 4 4!
 */





































/**
 * Explanation
 * - Timeline -
 *
 * 0 1 2 3 4 5 6 7 8 9 A B C D E F
 * 1 - - 2 - - 3 - - 4
 * C - - - - PC- - - D PC- - - - P
 *           1!        2!        4!
 *                 Drop 3
 */