package examples.stateflow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import util.*
import util.TerminalColor.*

object Example3 {
    class Example3ConsoleModel : ConsoleModel() {
        private val stateFlowInternal = MutableStateFlow(0)
        val stateFlow: StateFlow<Int> = stateFlowInternal

        init {
            consoleModelScope.launch {
                while (true) {
                    delay(1500)
                    stateFlowInternal.value += 1
                    println(
                        "Sending ${stateFlowInternal.value}"
                    )
                }
            }
        }
    }

    object App : ConsoleApp() {

        private val consoleModel
                by consoleModels<Example3ConsoleModel>()

        override suspend fun main() {
            consoleScope.launch {
                consoleModel
                delay(4000)
                cPrintln("Starting to collect", backgroundColor = RED)
                consoleModel.stateFlow
                    .collect {
                        cPrintln("Received $it", CYAN)
                        delay(2000)
                        cPrintln("Processed $it!", RED)
                    }
            }
        }
    }
}

fun main() {
    Example3.App.start()
}
