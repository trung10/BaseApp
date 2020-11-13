package com.pdtrung.baseapp.entity

import android.os.Parcel
import android.os.Parcelable
import android.text.Spanned
import com.google.gson.annotations.SerializedName
import com.pdtrung.baseapp.util.HtmlUtils
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.WriteWith
import java.util.*

@Parcelize
    data class Account(
    val id: String,
    @SerializedName("username") val localUsername: String,
    @SerializedName("acct") val username: String,
    @SerializedName("display_name") val displayName: String,
    val note: @WriteWith<SpannedParceler>() Spanned,
    val url: String,
    val avatar: String,
    val header: String,
    val locked: Boolean = false,
    @SerializedName("followers_count") val followersCount: Int = 0,
    @SerializedName("following_count") val followingCount: Int = 0,
    @SerializedName("statuses_count") val statusesCount: Int = 0,
    val source: AccountSource? = null,
    val bot: Boolean = false,
    val emojis: List<Emoji>? = emptyList(),  // nullable for backward compatibility
    val fields: List<Field>? = emptyList(),  //nullable for backward compatibility
    val moved: Account? = null
) : Parcelable {

    val name: String
        get() = if (displayName.isEmpty()) {
            localUsername
        } else displayName

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Account) {
            return false
        }
        return other.id == this.id
    }

    fun deepEquals(other: Account): Boolean {
        return id == other.id
                && localUsername == other.localUsername
                && displayName == other.displayName
                && note == other.note
                && url == other.url
                && avatar == other.avatar
                && header == other.header
                && locked == other.locked
                && followersCount == other.followersCount
                && followingCount == other.followingCount
                && statusesCount == other.statusesCount
                && source == other.source
                && bot == other.bot
                && emojis == other.emojis
                && fields == other.fields
                && moved == other.moved
    }

    fun isRemote(): Boolean = this.username != this.localUsername
}

@Parcelize
data class AccountSource(
    val privacy: Status.Visibility,
    val sensitive: Boolean,
    val note: String,
    val fields: List<StringField>?
): Parcelable

@Parcelize
data class Field (
    val name: String,
    val value: @WriteWith<SpannedParceler>() Spanned,
    @SerializedName("verified_at") val verifiedAt: Date?
): Parcelable

@Parcelize
data class StringField (
    val name: String,
    val value: String
): Parcelable

object SpannedParceler : Parceler<Spanned> {
    override fun create(parcel: Parcel): Spanned = HtmlUtils.fromHtml(parcel.readString())

    override fun Spanned.write(parcel: Parcel, flags: Int) {
        parcel.writeString(HtmlUtils.toHtml(this))
    }
}