package dev.kosmx.emoteFormatCLI.typedOps.formats

import dev.kosmx.emoteFormatCLI.typedOps.ITypedOp
import io.github.kosmx.emotes.common.network.objects.NetData
import java.io.IOException
import java.nio.ByteBuffer

class Icon: ITypedOp {
    override fun read(data: NetData, input: ByteBuffer) {
        //input.rewind()
        (data.emoteData?: throw IOException("Can not add icon to nothing")).iconData = input
    }

    override fun write(data: NetData): ByteBuffer {
        data.emoteData?: throw IOException("No emote")
        data.emoteData!!.iconData?: throw IOException("No emote")
        return data.emoteData!!.iconData!!.let { it.rewind(); it }
    }
}