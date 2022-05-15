package dev.kosmx.emoteFormatCLI.typedOps.formats

import dev.kosmx.emoteFormatCLI.socket.setFinalStatic
import dev.kosmx.emoteFormatCLI.typedOps.ITypedOp
import dev.kosmx.emoteFormatCLI.typedOps.util.HeaderJsonSerializer
import io.github.kosmx.emotes.api.proxy.INetworkInstance
import io.github.kosmx.emotes.common.emote.EmoteData
import io.github.kosmx.emotes.common.network.objects.NetData
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStreamReader
import java.nio.ByteBuffer

class EmoteHeaderJSON: ITypedOp {
    override fun read(data: NetData, input: ByteBuffer) {
        val reader = InputStreamReader(ByteArrayInputStream(INetworkInstance.safeGetBytesFromBuffer(input)))
        val emoteData = HeaderJsonSerializer.INSTANCE.fromJson(reader, EmoteData::class.java)

        val originalData: EmoteData = data.emoteData?: throw NullPointerException("Can not set header if there is no data")

        originalData.setFinalStatic(EmoteData::class.java.getDeclaredField("name"), emoteData.name)
        originalData.setFinalStatic(EmoteData::class.java.getDeclaredField("description"), emoteData.description)
        originalData.setFinalStatic(EmoteData::class.java.getDeclaredField("author"), emoteData.author)
        originalData.setFinalStatic(EmoteData::class.java.getDeclaredField("uuid"), emoteData.uuid)

    }

    override fun write(data: NetData): ByteBuffer {
        val out = ByteArrayOutputStream()
        HeaderJsonSerializer.INSTANCE.toJson(data.emoteData)
        out.close()
        return ByteBuffer.wrap(out.toByteArray())
    }

}