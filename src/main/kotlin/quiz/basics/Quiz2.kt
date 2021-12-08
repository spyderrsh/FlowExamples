package quiz.basics

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

object Quiz2 {
    fun getNumberFeed() = flow<Int> {
        for (i in 1..4) {
            println(i)
            emit(i)
            delay(300)
        }

    }
}

fun main() {
    GlobalScope.launch {
        Quiz2.getNumberFeed().collect {
            println("$it")
        }
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