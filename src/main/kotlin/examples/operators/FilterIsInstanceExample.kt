package examples.operators

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.random.Random

object FilterIsInstanceExample {
    val chars = 'a'..'z'
    var randomNumbersGenerated: ULong = 0u
    val dataFlow: Flow<Any> = channelFlow {
        launch {
            while (true) {
                send(Random.nextInt())
                randomNumbersGenerated++
            }
        }
        launch {
            while (true) {
                delay(1000)
                send(chars.random())
            }
        }
    }
}

fun main() {
    runBlocking {
        with(FilterIsInstanceExample) {
            dataFlow
                .filterIsInstance<Char>()
                .take(10)
                .collect { println(it) }
            println(randomNumbersGenerated)
            delay(1000)
            println(randomNumbersGenerated)
        }
    }
}