package util

enum class TerminalColor() {
    BLACK,
    RED,
    GREEN,
    YELLOW,
    BLUE,
    PURPLE,
    CYAN,
    WHITE;

    val foregroundCode: String = "\u001B[${this.ordinal + 30}m"
    val backgroundCode: String = "\u001B[${this.ordinal + 40}m"
}

val TERMINAL_COLOR_RESET = "\u001B[0m"

fun cPrintln(
    printlnArg: Any,
    foregroundColor: TerminalColor? = null,
    backgroundColor: TerminalColor? = null,
) {
    println(foregroundColor?.foregroundCode.orEmpty() +
            backgroundColor?.backgroundCode.orEmpty() +
            printlnArg +
            TERMINAL_COLOR_RESET
    )
}