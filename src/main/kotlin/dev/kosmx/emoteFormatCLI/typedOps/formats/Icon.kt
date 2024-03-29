package dev.kosmx.emoteFormatCLI.typedOps.formats

import dev.kosmx.emoteFormatCLI.typedOps.ITypedOp
import io.github.kosmx.emotes.common.network.objects.NetData
import java.io.IOException
import java.nio.ByteBuffer

class Icon: ITypedOp {
    override fun read(data: NetData, input: ByteBuffer) {
        //input.rewind()
        (data.emoteData?: throw IOException("Can not add icon to nothing")).extraData["iconData"] = input
    }

    override fun write(data: NetData): ByteBuffer {
        data.emoteData?: throw IOException("No emote")
        if (data.emoteData!!.extraData["iconData"] == null) {
            return ByteBuffer.allocate(0) //no icon does not mean failure
        }
        return (data.emoteData!!.extraData["iconData"] as ByteBuffer).let { it.rewind(); it }
    }
}