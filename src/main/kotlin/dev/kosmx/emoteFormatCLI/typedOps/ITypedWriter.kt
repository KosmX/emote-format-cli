package dev.kosmx.emoteFormatCLI.typedOps

import io.github.kosmx.emotes.common.network.objects.NetData
import java.nio.ByteBuffer

interface ITypedWriter {
    fun write(data: NetData): ByteBuffer
}