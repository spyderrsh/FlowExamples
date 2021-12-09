package examples.sharedflow.band

import ext.bpmToDelayMillis
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import util.*
import util.TerminalColor.*

object Band1 {
    class Band1ConsoleModel : ConsoleModel() {
        val metronomeFlow = metronomeFlow(240.bpmToDelayMillis(), 16)
    }

    object App : ConsoleApp() {

        private val consoleModel
                by consoleModels<Band1ConsoleModel>()

        val bassDrum = Musician(
            setupTimeEst = 5000,
            playSoundOn = listOf(1, 3, 9, 11),
            sound = "bum"
        )

        val snareDrum = Musician(
            setupTimeEst = 8000,
            playSoundOn = listOf(5, 13,),
            sound = "kuh"
        )
        val highHat = Musician(
            setupTimeEst = 10000,
            playSoundOn = listOf(2,4,10,12),
            sound = "tss"
        )
        val crash = Musician(
            setupTimeEst = 1000,
            playSoundOn = listOf(15),
            sound = "CRASH"
        )

        override suspend fun main() {
            consoleModel.metronomeFlow.let { metronome ->
                bassDrum.collectMetronome(metronome, consoleScope)
                snareDrum.collectMetronome(metronome, consoleScope)
                highHat.collectMetronome(metronome, consoleScope)
                crash.collectMetronome(metronome, consoleScope)
            }
        }
    }
}

fun main() {
    Band1.App.start()
}
