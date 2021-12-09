package examples.stateflow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import util.*
import util.TerminalColor.*

object Example1 {
    class Example1ConsoleModel : ConsoleModel() {
        private val stateFlowInternal = MutableStateFlow(0)
        val stateFlow: StateFlow<Int> = stateFlowInternal

        init {
            consoleModelScope.launch {
                while (true) {
                    delay(500)
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
                by consoleModels<Example1ConsoleModel>()

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
    Example1.App.start()
}
