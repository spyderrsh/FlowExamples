package examples.operators

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.random.Random

object CombineExample {
    private val chars = 'a'..'z'
    private val numbas = '0'..'9'
    val charFlow = flow {
        chars.forEach {
            delay(150)
            emit(it)
        }
    }
    val numbaFlow = flow {
        numbas.forEach {
            delay(500)
            emit(it)
        }
    }
}

fun main() {
    runBlocking {
        with(CombineExample) {
            combine(charFlow, numbaFlow) { letta, numba ->
                    letta to numba
                }
                .collect { println("${it.first}${it.second}") }
        }
    }
}