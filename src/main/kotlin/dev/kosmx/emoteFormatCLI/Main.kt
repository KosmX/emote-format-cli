package dev.kosmx.emoteFormatCLI

import org.apache.commons.cli.Option
import org.apache.commons.cli.Options

val progDescription = """Emotes Format Tools CLI
    Tools to convert emotecraft format from and to.
Args:
""".trimIndent()

fun main() {
    val options = Options()
    val input = Option("i", "input", true ,"The array input, \"-\" if the input will be received in the stdin, \"*\" if passed as an \$arg")
    val output = Option("o", "output", true, "The result array, \"-\" if use stdout")
    val mode = Option("m", "mode", true, "Operation Mode, see help for more info")
    val help = Option("h", "help", false, "Print the help text")
    val arg = Option("a", "arg", true, "the input json if used -i *")
    val input2 = Option("i2", "input2", true, "Secondary input, see modes")

    setup()

}