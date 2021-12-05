package example1

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlin.random.Random
object Example1 {
    fun getNumberFeed() = flow<Int> {
        for (i in 1..4) {
            emit(i.also {
                println(it)
            })
            delay(300)
        }
    }
}


fun main(args: Array<String>) {
    Example1.getNumberFeed()
}

/**
 * What will this output?
 *
 * a) 4 3 2 1
 * b) 1 2 3 4
 * c) Nothing
 * d) Exception
 */