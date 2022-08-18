package dev.kosmx.emoteFormatCLI.typedOps.util

import com.google.gson.*
import dev.kosmx.playerAnim.core.data.gson.AnimationJson
import java.lang.reflect.Type
import java.util.*

class HeaderJsonSerializer: JsonSerializer<AnimationHeader>, JsonDeserializer<AnimationHeader> {

    companion object {
        val INSTANCE: Gson
        init {
            val builder = GsonBuilder().setPrettyPrinting()
            builder.registerTypeAdapter(AnimationHeader::class.java, HeaderJsonSerializer())
            INSTANCE = builder.create()
        }
    }

    override fun serialize(emote: AnimationHeader?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        if (emote == null) throw NullPointerException("emote can not be null")
        val node = JsonObject()

        node.add("name", AnimationJson.asJson(emote.name))
        node.add("description", AnimationJson.asJson(emote.description)) // :D

        node.add("author", AnimationJson.asJson(emote.author))

        node.add("uuid", AnimationJson.asJson(emote.uuid.toString()))
        node.addProperty("nsfw", emote.nsfw)

        return node
    }

    override fun deserialize(p: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): AnimationHeader {
        val node: JsonObject = p!!.asJsonObject

        val emote = AnimationHeader()


        emote.name = node["name"].toString()
        if (node.has("author")) {
            emote.author = node["author"].toString()
        }

        if (node.has("uuid")) {
            emote.uuid = UUID.fromString(node["uuid"].asString)
        }


        if (node.has("description")) {
            emote.description = node["description"].toString()
        }

        if (node.has("nsfw")) {
            emote.nsfw = node["nsfw"].asBoolean
        }

        return emote
    }

}

data class AnimationHeader(var name: String = "",
                      var description: String = "",
                      var author: String = "",
                      var nsfw: Boolean = false,
                      var uuid: UUID? = null)