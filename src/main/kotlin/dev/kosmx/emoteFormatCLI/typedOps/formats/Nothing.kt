package dev.kosmx.emoteFormatCLI.typedOps.formats

import dev.kosmx.emoteFormatCLI.typedOps.ITypedOp
import io.github.kosmx.emotes.common.network.objects.NetData
import java.nio.ByteBuffer

class Nothing: ITypedOp {
    override fun read(data: NetData, input: ByteBuffer) {

    }

    override fun write(data: NetData): ByteBuffer {
        return ByteBuffer.allocate(0)
    }
}