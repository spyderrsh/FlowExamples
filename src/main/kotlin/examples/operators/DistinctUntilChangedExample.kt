package examples.operators

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.random.Random

object DistinctUntilChangedExample {
    val chars = 'a'..'z'
    var characterEmissions: ULong = 0u
    val charIterator = chars.iterator()
    var currentChar = charIterator.nextChar()
    val dataFlow: Flow<Any> = channelFlow {
        launch {
            while (true) {
                send(currentChar)
                ++characterEmissions
            }
        }
        launch {
            while (true) {
                delay(500)
                currentChar = charIterator.nextChar()
            }
        }
    }
}

fun main() {
    runBlocking {
        with(DistinctUntilChangedExample) {
            dataFlow
                .distinctUntilChanged()
                .take(10)
                .collect { println(it) }
            println(characterEmissions)
        }
    }
}