package dev.kosmx.emoteFormatCLI.typedOps.formats

import dev.kosmx.emoteFormatCLI.typedOps.ITypedOp
import dev.kosmx.emoteFormatCLI.typedOps.util.AnimationHeader
import dev.kosmx.emoteFormatCLI.typedOps.util.HeaderJsonSerializer
import dev.kosmx.playerAnim.core.data.KeyframeAnimation
import io.github.kosmx.emotes.api.proxy.INetworkInstance
import io.github.kosmx.emotes.common.network.objects.NetData
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.nio.ByteBuffer

class EmoteHeaderJSON: ITypedOp {
    override fun read(data: NetData, input: ByteBuffer) {
        val reader = InputStreamReader(ByteArrayInputStream(INetworkInstance.safeGetBytesFromBuffer(input)))
        val emoteData = HeaderJsonSerializer.INSTANCE.fromJson(reader, AnimationHeader::class.java)

        val originalData: KeyframeAnimation = data.emoteData?: throw NullPointerException("Can not set header if there is no data")
        val mutable = originalData.mutableCopy()

        /*
        originalData.setFinalStatic(EmoteData::class.java.getDeclaredField("name"), emoteData.name)
        originalData.setFinalStatic(EmoteData::class.java.getDeclaredField("description"), emoteData.description)
        originalData.setFinalStatic(EmoteData::class.java.getDeclaredField("author"), emoteData.author)
        originalData.setFinalStatic(EmoteData::class.java.getDeclaredField("uuid"), emoteData.uuid)
        */
        mutable.name = emoteData.name
        mutable.description = emoteData.description
        mutable.author = emoteData.author
        emoteData.uuid?.let { mutable.uuid = it }
        mutable.nsfw = emoteData.nsfw
        data.emoteData = mutable.build()

    }

    override fun write(data: NetData): ByteBuffer {
        val out = ByteArrayOutputStream()
        val writer = OutputStreamWriter(out)
        HeaderJsonSerializer.INSTANCE.toJson(data.emoteData, writer)
        writer.close()
        out.close()
        return ByteBuffer.wrap(out.toByteArray())
    }

}