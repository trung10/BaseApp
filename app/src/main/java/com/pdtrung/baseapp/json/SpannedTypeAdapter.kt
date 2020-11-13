package com.pdtrung.baseapp.json

import android.text.Spanned
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class SpannedTypeAdapter: JsonDeserializer<Spanned>{
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Spanned {
        TODO("Not yet implemented")
    }

}