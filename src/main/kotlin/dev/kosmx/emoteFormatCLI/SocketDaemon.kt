package dev.kosmx.emoteFormatCLI

import dev.kosmx.emoteFormatCLI.socket.ConverterConnection
import org.apache.commons.cli.*
import java.net.ServerSocket
import java.util.concurrent.Executors
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val options = Options()
    val portOption = Option("p", "port", true, "Listen on that port")
    //val workerThreads =

    options.addOption(portOption)


    val parser: CommandLineParser = DefaultParser()
    val formatter = HelpFormatter()


    try {
        val cmd = parser.parse(options, args)

        if (!cmd.hasOption(portOption) || cmd.getOptionValue(portOption) == null) {
            formatter.printHelp("Please specify a port!", options)
            exitProcess(2)
        }

        setup()

        val port: Int = cmd.getOptionValue(portOption).toInt()


        val workers = Executors.newFixedThreadPool(8)

        val ss = ServerSocket(port)
        while (true) {
            val cs = ss.accept()
            workers.submit(ConverterConnection(cs))
        }

    } catch (e: ParseException) {
        println(e.message)
        formatter.printHelp("Invalid arguments", options)

        exitProcess(1)
    }
}
