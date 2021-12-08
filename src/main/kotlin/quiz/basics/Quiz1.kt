package quiz.basics

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

object Quiz1 {
    fun getNumberFeed() = flow<Int> {
        for (i in 1..4) {
            emit(i.also {
                println(it)
            })
            delay(300)
        }
    }
}


fun main() {
    Quiz1.getNumberFeed()
}

/**
 * What will this output?
 *
 * a) 4 3 2 1
 * b) 1 2 3 4
 * c) Nothing
 * d) Exception
 */