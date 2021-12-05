package examples.basics

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object Example1 {
    fun getNumbersFlow() = flow {
        for (i in 0 until  10) {
            delay(200)
            emit(i)
        }
    }
}

fun main() = runBlocking {
    GlobalScope.launch {
        println("Start Collection")
        Example1.getNumbersFlow().collect {
            println(it)
        }
        println("Stop Collection")
    }
}