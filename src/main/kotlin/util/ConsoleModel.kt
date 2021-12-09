package util

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets.UTF_8
import kotlin.properties.ReadOnlyProperty

private val cmdMainCoroutineDispatcher = ThreadUtil.createDispatcherWithName("main")
val Dispatchers.CmdMain get() = cmdMainCoroutineDispatcher

open class ConsoleModel {
    private var consoleModelScopeInternal: CoroutineScope? = null
    val consoleModelScope: CoroutineScope
        get() {
            return consoleModelScopeInternal ?: run {
                CoroutineScope(Dispatchers.CmdMain + SupervisorJob()).also {
                    consoleModelScopeInternal = it
                }
            }
        }

    /**
     * Call super on override
     */
    open fun onDestroy() {
        val scope = consoleModelScopeInternal
        consoleModelScopeInternal = null
        scope?.cancel()
    }

    /**
     * Call super on override
     */
    open fun onStart() {
    }

    open fun onUserInput(line: String){
    }
}

open class ConsoleApp() {
    open suspend fun main() {/* no-op */}
    val linkedConsoleModels = mutableSetOf<ConsoleModel>()
    private var consoleScopeInternal: CoroutineScope? = null
    val consoleScope: CoroutineScope
        get() {
            return consoleScopeInternal ?: run {
                CoroutineScope(Dispatchers.CmdMain + SupervisorJob()).also {
                    consoleScopeInternal = it
                }
            }
        }

    val keyboardLineFlow: Flow<String> = flow {
        val input = BufferedReader(InputStreamReader(System.`in`, UTF_8))
        var line = input.readLine()
        while (line.isNotBlank()) {
            emit(line)
            line = input.readLine()
        }
    }.flowOn(Dispatchers.IO)

    fun start(useKeyboard: Boolean = false) = runBlocking {
        consoleScope.launch {
            main()
        }
        if(useKeyboard) {
            consoleScope.launch {
                keyboardLineFlow.collect {
                    onLine(it)
                }
                println("Done reading lines from terminal")
            }
        }
        while (consoleScope.isActive) {
            yield()
        }
        linkedConsoleModels.onEach { it.onDestroy() }
    }


    fun addToLifecycleListeners(consoleModel: ConsoleModel) {
        linkedConsoleModels.add(consoleModel)
        consoleModel.onStart()
    }

    open fun onLine(line: String) {
        linkedConsoleModels.onEach { it.onUserInput(line) }
    }
}

inline fun <reified V : ConsoleModel> ConsoleApp.consoleModels() =
    ReadOnlyProperty<ConsoleApp, V> { _, _ ->
        linkedConsoleModels.firstOrNull { it is V } as V? ?: V::class.java.getConstructor()
            .newInstance().also {
                addToLifecycleListeners(it)
            }
    }
