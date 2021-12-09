package examples.sharedflow.band

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.NumberFormat
import kotlin.random.Random

data class Musician(val setupTimeEst: Int, val playSoundOn: List<Int>, val sound: String) {
    fun collectMetronome(metronome: Flow<Int>, consoleScope: CoroutineScope) {
        consoleScope.launch {
            delay(Random.nextLong((setupTimeEst - 1000L).coerceAtLeast(1000L), setupTimeEst + 1000L))
            metronome.collect {
                if (playSoundOn.contains(it)) {
                    println("${numberFormat.format(it)} - $sound")
                }
            }
        }
    }
    companion object {
        val numberFormat = DecimalFormat("##")
    }
}
