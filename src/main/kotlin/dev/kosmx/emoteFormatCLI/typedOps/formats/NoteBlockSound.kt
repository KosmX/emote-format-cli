package dev.kosmx.emoteFormatCLI.typedOps.formats

import dev.kosmx.emoteFormatCLI.typedOps.ITypedOp
import dev.kosmx.playerAnim.core.data.opennbs.NBS
import dev.kosmx.playerAnim.core.data.opennbs.network.NBSPacket
import io.github.kosmx.emotes.common.network.objects.NetData
import java.nio.ByteBuffer
import java.util.HashMap

//note block sound OP
class NoteBlockSound : ITypedOp {
    override fun read(data: NetData, input: ByteBuffer) {
        val nbs = NBSPacket().read(input)
        data.getExtraData()["nbs"] = nbs
    }

    override fun write(data: NetData): ByteBuffer {
        val nbs = ((data.emoteData?: throw IllegalStateException("No emote data found")).extraData["nbs"]?: throw IllegalStateException("No nbs data found")) as NBS
        return nbs.let {
            val bytebuffer = ByteBuffer.allocate(NBSPacket.calculateMessageSize(it))
            NBSPacket(it).write(bytebuffer); bytebuffer
        }

    }

    fun NetData.getExtraData(): HashMap<String, Any> {
        val field = NetData::class.java.getDeclaredField("extraData")
        field.isAccessible = true
        return field.get(this) as HashMap<String, Any>
    }
}