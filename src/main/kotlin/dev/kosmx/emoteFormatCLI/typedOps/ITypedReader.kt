package dev.kosmx.emoteFormatCLI.typedOps

import io.github.kosmx.emotes.common.network.objects.NetData
import java.nio.ByteBuffer

interface ITypedReader {
    fun read(data: NetData, input: ByteBuffer)
}