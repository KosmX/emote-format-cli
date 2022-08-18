package dev.kosmx.emoteFormatCLI.typedOps.formats

import dev.kosmx.emoteFormatCLI.typedOps.ITypedOp
import dev.kosmx.playerAnim.core.data.AnimationFormat
import dev.kosmx.playerAnim.core.data.gson.AnimationSerializing
import io.github.kosmx.emotes.api.proxy.INetworkInstance
import io.github.kosmx.emotes.common.network.objects.NetData
import io.github.kosmx.emotes.server.serializer.UniversalEmoteSerializer
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.OutputStreamWriter
import java.nio.ByteBuffer

class JsonFormat: ITypedOp {
    override fun read(data: NetData, input: ByteBuffer) {
        val inputStream = ByteArrayInputStream(INetworkInstance.safeGetBytesFromBuffer(input))
        val emotes = UniversalEmoteSerializer.readData(inputStream, null, AnimationFormat.JSON_EMOTECRAFT.extension)

        data.emoteData = emotes[0]
    }

    override fun write(data: NetData): ByteBuffer {
        val out = ByteArrayOutputStream()
        AnimationSerializing.writeAnimation(data.emoteData, OutputStreamWriter(out))
        out.close()
        return ByteBuffer.wrap(out.toByteArray())
    }

}