package dev.kosmx.emoteFormatCLI.executorImpl

import io.github.kosmx.emotes.executor.EmoteInstance
import io.github.kosmx.emotes.executor.Logger
import io.github.kosmx.emotes.executor.dataTypes.IClientMethods
import io.github.kosmx.emotes.executor.dataTypes.IDefaultTypes
import io.github.kosmx.emotes.executor.dataTypes.IGetters
import io.github.kosmx.emotes.server.config.Serializer
import io.github.kosmx.emotes.server.serializer.EmoteSerializer
import java.nio.file.Path
import java.util.logging.Level

class Executor: EmoteInstance() {

    init {
        Serializer()
    }
    override fun getLogger(): Logger {
        return Logger() { _: Level, _: String -> /* nothing */ }
    }

    override fun getDefaults(): IDefaultTypes {
        throw java.lang.UnsupportedOperationException("This is not a game instance!")
    }

    override fun getGetters(): IGetters {
        throw java.lang.UnsupportedOperationException("This is not a game instance!")
    }

    override fun getClientMethods(): IClientMethods {
        throw java.lang.UnsupportedOperationException("This is not a game instance!")
    }

    override fun isClient(): Boolean {
        throw java.lang.UnsupportedOperationException("This is not a game instance!")
    }

    override fun getGameDirectory(): Path {
        throw java.lang.UnsupportedOperationException("This is not a game instance!")
    }
}