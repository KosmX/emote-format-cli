package dev.kosmx.emoteFormatCLI.typedOps.util

import com.google.gson.*
import io.github.kosmx.emotes.common.emote.EmoteData
import io.github.kosmx.emotes.common.emote.EmoteData.EmoteBuilder
import io.github.kosmx.emotes.common.emote.EmoteFormat
import io.github.kosmx.emotes.server.serializer.EmoteSerializer
import java.lang.reflect.Type
import java.util.*

class HeaderJsonSerializer: JsonSerializer<EmoteData>, JsonDeserializer<EmoteData> {

    companion object {
        val INSTANCE: Gson
        init {
            val builder = GsonBuilder().setPrettyPrinting()
            builder.registerTypeAdapter(EmoteData::class.java, HeaderJsonSerializer())
            INSTANCE = builder.create()
        }
    }

    override fun serialize(emote: EmoteData?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        if (emote == null) throw NullPointerException("emote can not be null")
        val node = JsonObject()
        node.addProperty("version", if (emote.isEasingBefore) 2 else 1) //to make compatible emotes. I won't do it.

        node.add("name", EmoteSerializer.asJson(emote.name))
        node.add("description", EmoteSerializer.asJson(emote.description)) // :D

        if (emote.author != null) {
            node.add("author", EmoteSerializer.asJson(emote.author))
        }

        node.add("uuid", EmoteSerializer.asJson(emote.uuid.toString()))

        return node;
    }

    override fun deserialize(p: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): EmoteData {
        val node: JsonObject = p!!.asJsonObject

        var version = 1
        if (node.has("version")) version = node["version"].asInt
        //Text author = EmoteInstance.instance.getDefaults().emptyTex();
        //Text author = EmoteInstance.instance.getDefaults().emptyTex();
        val emote: EmoteBuilder = EmoteBuilder(EmoteFormat.JSON_EMOTECRAFT)


        //Text name = EmoteInstance.instance.getDefaults().fromJson(node.get("name"));


        //Text name = EmoteInstance.instance.getDefaults().fromJson(node.get("name"));
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

        return emote.build()
    }

}