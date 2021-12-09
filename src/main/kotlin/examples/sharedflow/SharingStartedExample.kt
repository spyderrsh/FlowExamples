package examples.sharedflow

import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import util.*

object SharingStartedExample {
    class SharingStartedConsoleModel : ConsoleModel() {
        private val heartBeatFlow = heartBeatFlow(1500)
        val eagerHeartBeatFlow = heartBeatFlow
            .onEach { cPrintln("eager beat", TerminalColor.CYAN) }
            .shareIn(
                consoleModelScope,
                SharingStarted.Eagerly,
            )
        val lazyHeartBeatFlow = heartBeatFlow
            .onEach { cPrintln("lazy beat", TerminalColor.YELLOW) }
            .shareIn(
                consoleModelScope,
                SharingStarted.Lazily,
            )
        val whileSubscribedHeartBeatFlow = heartBeatFlow
            .onEach { cPrintln("whileSubscribed beat", TerminalColor.PURPLE) }
            .shareIn(
                consoleModelScope,
                SharingStarted.WhileSubscribed()
            )
    }

    object App : ConsoleApp() {

        private val consoleModel
                by consoleModels<SharingStartedConsoleModel>()

        override suspend fun main() {
            cPrintln("Loading ViewModel", backgroundColor = TerminalColor.RED)
            consoleModel
            delay(6000)
            cPrintln("Launching Collectors", backgroundColor = TerminalColor.RED)
            consoleScope.launch {
                consoleModel.eagerHeartBeatFlow
                    .take(5)
                    .collect {
                        cPrintln("eager collect", backgroundColor = TerminalColor.CYAN)
                    }
            }
            consoleScope.launch {
                consoleModel.lazyHeartBeatFlow
                    .take(5)
                    .collect {
                        cPrintln(
                            "lazy collect",
                            foregroundColor = TerminalColor.BLACK,
                            backgroundColor = TerminalColor.YELLOW
                        )
                    }
            }
            consoleScope.launch {
                consoleModel.whileSubscribedHeartBeatFlow
                    .take(5)
                    .collect {
                        cPrintln(
                            "whileSubscribed collect",
                            backgroundColor = TerminalColor.PURPLE
                        )
                    }
            }
            delay(9000)
            cPrintln("Collectors finishing up", backgroundColor = TerminalColor.RED)
            delay(3000)
            cPrintln("Cancelling scopes", backgroundColor = TerminalColor.RED)
            consoleModel.consoleModelScope.cancel()
            consoleScope.cancel()
        }
    }
}

fun main() {
    SharingStartedExample.App.start()
}
