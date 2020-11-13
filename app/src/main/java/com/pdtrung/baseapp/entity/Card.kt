package com.pdtrung.baseapp.entity

import android.os.Parcelable
import android.text.Spanned
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.WriteWith

@Parcelize
data class Card(
    val url: String,
    val title: @WriteWith<SpannedParceler>() Spanned,
    val description: @WriteWith<SpannedParceler>() Spanned,
    @SerializedName("author_name") val authorName: String,
    val image: String,
    val type: String,
    val width: Int,
    val height: Int
) : Parcelable {

    override fun hashCode(): Int {
        return url.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Card) {
            return false
        }
        val account = other as Card?
        return account?.url == this.url
    }

}