package com.pdtrung.baseapp.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Emoji(
    val shortcode: String,
    val url: String,
    @SerializedName("visible_in_picker") val visibleInPicker: Boolean?
) : Parcelable