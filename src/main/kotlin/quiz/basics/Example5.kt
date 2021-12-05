package quiz.basics

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

object Example5 {
    fun getNumberFeed() = flow<Int> {
        println("Shuffling Numbers!")
        val numbers = (1..4).shuffled()
        for (i in numbers) {
            emit(i)
            delay(300)
        }

    }
}

fun main(args: Array<String>) {
    val flow1 = Example5.getNumberFeed()
    println("Flow 1")
    runBlocking {
        flow1.collect {
            println(it)
        }
    }
    println("Flow 2")
    runBlocking {
        flow1.collect {
            println(it)
        }
    }
}
/**
 * This will print the same list of numbers twice
 * a) True
 * b) False
 */















/** Explanation
 *
 * Each time a flow is collected, it runs EVERYTHING inside the lambda again. If you call a network request inside a flow,
 * collecting that flow again will re-trigger the flow.
 *
 * */
