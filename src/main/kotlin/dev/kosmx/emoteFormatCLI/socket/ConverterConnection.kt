package dev.kosmx.emoteFormatCLI.socket

import dev.kosmx.emoteFormatCLI.typedOps.ITypedOp
import dev.kosmx.emoteFormatCLI.typedOps.formats.BinaryFormat
import dev.kosmx.emoteFormatCLI.typedOps.formats.EmoteHeaderJSON
import dev.kosmx.emoteFormatCLI.typedOps.formats.Icon
import dev.kosmx.emoteFormatCLI.typedOps.formats.JsonFormat
import io.github.kosmx.emotes.api.proxy.INetworkInstance
import io.github.kosmx.emotes.common.emote.EmoteData
import io.github.kosmx.emotes.common.network.objects.NetData
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.net.Socket
import java.nio.BufferUnderflowException
import java.nio.ByteBuffer
import java.util.UUID

class ConverterConnection(private val socket: Socket): Runnable {

    companion object {
        val types: Map<Int, ITypedOp>
        init {
            val mutableMap = mutableMapOf<Int, ITypedOp>()
            mutableMap[0] = dev.kosmx.emoteFormatCLI.typedOps.formats.Nothing()
            mutableMap[1] = BinaryFormat()
            mutableMap[2] = JsonFormat()
            mutableMap[3] = Icon()
            //mutableMap[4] = TODO
            //mutableMap[5] = TODO
            mutableMap[8] = EmoteHeaderJSON()
            types = mutableMap.toMap()
        }
    }


    override fun run() {
        socket.use {socket ->
            try {
                val inputStream = DataInputStream(
                    socket.getInputStream() ?: throw IOException("Can not read socket, input stream is null")
                ).let {
                    val l = it.readInt()
                    DataInputStream(ByteArrayInputStream(it.readNBytes(l)))
                }
                val outByteData = ByteArrayOutputStream()
                val outputStream = DataOutputStream(outByteData)

                val inputs = inputStream.read()
                if (inputs < 0) throw BufferUnderflowException()

                val netData = NetData()

                for (i in 0 until inputs) {
                    val type = inputStream.read()
                    val size = inputStream.readInt()
                    val data = ByteBuffer.wrap(inputStream.readNBytes(size))
                    val op = types[type] ?: throw IOException("Invalid type: $type")
                    op.read(netData, data)
                }

                val emoteData = netData.emoteData
                if ((emoteData ?: throw NullPointerException("Emote must not be null")).uuid == null || emoteData.isUUIDGenerated) {
                    netData.emoteData = emoteData.let {
                        val mutable = it.mutableCopy()
                        mutable.uuid = UUID.randomUUID()
                        mutable.build()
                    }
                }

                val outputs = inputStream.read()
                if (outputs < 0) throw BufferUnderflowException()
                outputStream.write(outputs)
                for (i in 0 until outputs) {
                    val type = inputStream.read()
                    val op = types[type] ?: throw IOException("Invalid type: $type")
                    val data: ByteBuffer = op.write(netData)
                    val byteData = INetworkInstance.safeGetBytesFromBuffer(data)
                    outputStream.write(type)
                    outputStream.writeInt(byteData.size)
                    outputStream.write(byteData)
                }

                (socket.getOutputStream() ?: throw IOException("Can not write socket, output stream is null")).let {
                    outputStream.close()
                    val bytes = outByteData.toByteArray()
                    val out = DataOutputStream(socket.getOutputStream())
                    out.writeInt(bytes.size)
                    out.write(bytes)
                    out.close()
                }

            } catch (t: Throwable) {
                System.err.println(t)
                t.printStackTrace(System.err)
            }
        }
    }
}


@Throws(Exception::class)
fun EmoteData.setFinalStatic(field: Field, newValue: Any?) {
    field.isAccessible = true
    val modifiersField: Field = Field::class.java.getDeclaredField("modifiers")
    modifiersField.isAccessible = true
    modifiersField.setInt(field, field.modifiers and Modifier.FINAL.inv())
    field.set(this, newValue)
}