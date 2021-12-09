package examples.stateflow

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import util.ConsoleApp
import util.ConsoleModel
import util.consoleModels
import util.heartBeatFlow

object ExampleTBD {
    class Example2ConsoleModel : ConsoleModel() {
        private val userInputInternal = MutableStateFlow("")
        val userInputFlow: StateFlow<String> = userInputInternal

        override fun onUserInput(line: String) {
            userInputInternal.value = line
        }
    }

    object App : ConsoleApp() {
        private val consoleModel by consoleModels<Example2ConsoleModel>()

        override suspend fun main() {
            consoleScope.launch {
                consoleModel.userInputFlow
                    .filterNot { it.isEmpty() }
                    .collect {
                        println("Got $it")
                    }
            }
            consoleScope.launch {

                consoleModel.userInputFlow.flatMapLatest { input ->
                    heartBeatFlow(5000).map { input }
                }.collect {
                    println(
                        when {
                            it.isEmpty() -> "Please go ahead and type here"
                            else -> "Last typed: $it"
                        }
                    )
                }
            }
        }
    }
}

fun main() {
    ExampleTBD.App.start(useKeyboard = true)
}
