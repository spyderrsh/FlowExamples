package quiz.stateflow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import util.*

object QuizTBD {
    class QuizTBDConsoleModel : ConsoleModel() {
        private val stateFlowInternal = MutableStateFlow(0)
        val stateFlow: StateFlow<Int> = stateFlowInternal

        init {
            consoleModelScope.launch {
                while (true) {
                    delay(1500)
                    stateFlowInternal.value += 1
                }
            }
        }
    }

    object App : ConsoleApp() {

        private val consoleModel
                by consoleModels<QuizTBDConsoleModel>()

        override suspend fun main() {
            consoleScope.launch {
                consoleModel
            }
        }
    }
}

fun main() {
    QuizTBD.App.start()
}

/**
 * Will this print anything?
 * a) True
 * b) False
 * c) *crash*
 */
