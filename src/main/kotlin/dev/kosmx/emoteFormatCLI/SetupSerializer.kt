package dev.kosmx.emoteFormatCLI

import dev.kosmx.emoteFormatCLI.executorImpl.Executor
import io.github.kosmx.emotes.common.SerializableConfig
import io.github.kosmx.emotes.executor.EmoteInstance

fun setup() {
    EmoteInstance.instance = Executor()

    EmoteInstance.config = SerializableConfig()
}