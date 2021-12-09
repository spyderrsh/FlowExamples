package examples.sharedflow.band

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

fun metronomeFlow(tickMillis: Long, count: Int) = flow<Int> {
    println("")
    while (true){
        (1..count).forEach {
            emit(it) 
            delay(tickMillis)
        }
    }
}