package quiz.timing

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

object Example1 {
    fun getNumberFeed() = flow<Int> {
        for (i in 1..4) {
            println(i)
            emit(i)
            delay(300)
        }

    }
}

fun main(args: Array<String>) {
    runBlocking {
        GlobalScope.launch {
            val flow = Example1.getNumberFeed().collect {
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