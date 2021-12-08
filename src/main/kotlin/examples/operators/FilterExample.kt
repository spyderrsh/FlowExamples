package examples.operators

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

object FilterExample {
    val chars = 'a'..'z'
    val randomNumberFlow = flow {
        while (true) {
            emit(Random.nextInt())
        }
    }
}

fun main() {
    runBlocking {
        with(FilterExample) {
            randomNumberFlow
                .filter { it in 0 until chars.count() }
                .map { chars.elementAt(it) }
                .collect { println(it) }
        }
    }
}