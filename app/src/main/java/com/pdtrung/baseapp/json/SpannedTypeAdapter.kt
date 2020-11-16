package com.pdtrung.baseapp.json

import android.text.Spanned
import android.text.SpannedString
import com.google.gson.*
import com.pdtrung.baseapp.util.HtmlUtils
import java.lang.reflect.Type

class SpannedTypeAdapter : JsonDeserializer<Spanned>, JsonSerializer<Spanned> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Spanned {
        val string = json?.asString
        return if (string != null) {
            HtmlUtils.fromHtml(string)
        } else {
            SpannedString("")
        }
    }

    override fun serialize(
        src: Spanned?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(HtmlUtils.toHtml(src))
    }

}