package examples.stateflow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import util.*
import util.TerminalColor.*

object Example2 {
    class Example2ConsoleModel : ConsoleModel() {
        private val stateFlowInternal = MutableStateFlow(0)
        val stateFlow: StateFlow<Int> = stateFlowInternal

        init {
            consoleModelScope.launch {
                while (true) {
                    delay(2500)
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
                by consoleModels<Example2ConsoleModel>()

        override suspend fun main() {
            consoleScope.launch {
                consoleModel.stateFlow
                    .collect {
                        cPrintln("Received $it", CYAN)
                        delay(4000)
                        cPrintln("Processed $it!", RED)
                    }
            }
        }
    }
}

fun main() {
    Example2.App.start()
}
