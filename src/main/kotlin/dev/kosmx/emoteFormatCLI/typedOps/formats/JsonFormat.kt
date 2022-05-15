package dev.kosmx.emoteFormatCLI.typedOps.formats

import dev.kosmx.emoteFormatCLI.typedOps.ITypedOp
import io.github.kosmx.emotes.api.proxy.INetworkInstance
import io.github.kosmx.emotes.common.emote.EmoteFormat
import io.github.kosmx.emotes.common.network.objects.NetData
import io.github.kosmx.emotes.server.serializer.UniversalEmoteSerializer
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

class JsonFormat: ITypedOp {
    override fun read(data: NetData, input: ByteBuffer) {
        val inputStream = ByteArrayInputStream(INetworkInstance.safeGetBytesFromBuffer(input))
        val emotes = UniversalEmoteSerializer.readData(inputStream, null, EmoteFormat.JSON_EMOTECRAFT.extension)

        data.emoteData = emotes[0]
    }

    override fun write(data: NetData): ByteBuffer {
        val out = ByteArrayOutputStream()
        UniversalEmoteSerializer.writeEmoteData(out, data.emoteData, EmoteFormat.JSON_EMOTECRAFT)
        out.close()
        return ByteBuffer.wrap(out.toByteArray())
    }

}