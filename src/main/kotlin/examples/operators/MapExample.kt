package examples.operators

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

object MapExample {
    val chars = 'a'..'z'
    val randomNumberFlow = flow {
        while (true){
            delay(1000)
            emit(Random.nextInt(chars.count()))
        }
    }
}

fun main() {
    runBlocking {
        MapExample.randomNumberFlow
            .map { MapExample.chars.elementAt(it) }
            .collect { println(it) }
    }
}