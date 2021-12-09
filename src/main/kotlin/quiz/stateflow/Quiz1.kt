package quiz.stateflow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import util.*

object Quiz1 {
    class Quiz1ConsoleModel : ConsoleModel() {
        private val userInputList =
            mutableListOf<String>("Please type something:")
        private val stateFlowInternal =
            MutableStateFlow(userInputList as List<String>)
        val stateFlow = stateFlowInternal.asStateFlow()

        override fun onUserInput(line: String) {
            userInputList.add(line)
        }
    }

    object App : ConsoleApp() {
        private val consoleModel
                by consoleModels<Quiz1ConsoleModel>()

        override suspend fun main() {
            consoleScope.launch {
                consoleModel.stateFlow.collect {
                    println(it)
                }
            }
        }
    }
}

fun main() {
    Quiz1.App.start(useKeyboard = true)
}

/**
 * What happens on the UI when the user inputs a string?
 * a) Prints a list of all strings
 * b) Prints the previous list ("Please type something:")
 * c) Nothing
 * d) *crash*
 */
