package util

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

fun heartBeatFlow(timeMillis: Long) = flow<Unit> {
    while (true){
        delay(timeMillis)
        emit(Unit)
    }
}