package examples.stateflow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import util.*
import util.TerminalColor.*

object Example4 {
    class Example4ConsoleModel : ConsoleModel() {
        private val stateFlowInternal = MutableStateFlow(0)
        val stateFlow: StateFlow<Int> = stateFlowInternal

        init {
            consoleModelScope.launch {
                repeat(3) {
                    delay(1500)
                    stateFlowInternal.value = 0
                    println(
                        "Sending ${stateFlowInternal.value}"
                    )
                }
                stateFlowInternal.value = 1
                println(
                    "Sending ${stateFlowInternal.value}"
                )
            }
        }
    }

    object App : ConsoleApp() {

        private val consoleModel
                by consoleModels<Example4ConsoleModel>()

        override suspend fun main() {
            consoleScope.launch {
                consoleModel.stateFlow
                    .collect {
                        cPrintln("Received $it", CYAN)
                    }
            }
        }
    }
}

fun main() {
    Example4.App.start()
}
