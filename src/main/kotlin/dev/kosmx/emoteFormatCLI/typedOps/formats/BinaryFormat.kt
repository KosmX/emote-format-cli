package dev.kosmx.emoteFormatCLI.typedOps.formats

import dev.kosmx.emoteFormatCLI.typedOps.ITypedOp
import io.github.kosmx.emotes.api.proxy.INetworkInstance
import io.github.kosmx.emotes.common.emote.EmoteFormat
import io.github.kosmx.emotes.common.network.EmotePacket
import io.github.kosmx.emotes.common.network.objects.NetData
import io.github.kosmx.emotes.server.serializer.UniversalEmoteSerializer
import java.io.ByteArrayInputStream
import java.nio.ByteBuffer

class BinaryFormat: ITypedOp {
    override fun read(data: NetData, input: ByteBuffer) {
        val inputStream = ByteArrayInputStream(INetworkInstance.safeGetBytesFromBuffer(input))
        val emotes = UniversalEmoteSerializer.readData(inputStream, null, EmoteFormat.BINARY.extension)

        data.emoteData = emotes[0]
    }

    override fun write(data: NetData): ByteBuffer {
        return EmotePacket.Builder().configureToSaveEmote(data.emoteData).build().write()
        //val idk = UniversalEmoteSerializer.writeEmoteData()
    }
}