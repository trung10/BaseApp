package com.pdtrung.baseapp

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.pdtrung.baseapp.components.conversation.ConversationsFragment
import com.pdtrung.baseapp.fragment.NotificationsFragment
import com.pdtrung.baseapp.fragment.TimelineFragment

/** this would be a good case for a sealed class, but that does not work nice with Room */

const val HOME = "Home"
const val NOTIFICATIONS = "Notifications"
const val LOCAL = "Local"
const val FEDERATED = "Federated"
const val DIRECT = "Direct"
const val HASHTAG = "Hashtag"

data class TabData(
    val id: String,
    @StringRes val text: Int,
    @DrawableRes val icon: Int,
    val fragment: (List<String>) -> Fragment,
    val arguments: List<String> = emptyList()
)

fun createTabDataFromId(id: String, arguments: List<String> = emptyList()): TabData {
    return when (id) {
        HOME -> TabData(
            HOME,
            R.string.title_home,
            R.drawable.ic_home_24dp,
            { TimelineFragment.newInstance(TimelineFragment.Kind.HOME) })
        NOTIFICATIONS -> TabData(
            NOTIFICATIONS,
            R.string.title_home,
            R.drawable.ic_home_24dp,
            { NotificationsFragment.newInstance() })
        LOCAL -> TabData(
            LOCAL,
            R.string.title_home,
            R.drawable.ic_home_24dp,
            { TimelineFragment.newInstance(TimelineFragment.Kind.PUBLIC_LOCAL) })
        FEDERATED -> TabData(
            FEDERATED,
            R.string.title_home,
            R.drawable.ic_home_24dp,
            { TimelineFragment.newInstance(TimelineFragment.Kind.PUBLIC_FEDERATED) })
        DIRECT -> TabData(
            DIRECT,
            R.string.title_home,
            R.drawable.ic_home_24dp,
            { ConversationsFragment.newInstance() })
        HASHTAG -> TabData(
            HASHTAG,
            R.string.title_home,
            R.drawable.ic_home_24dp,
            { args ->
                TimelineFragment.newInstance(
                    TimelineFragment.Kind.TAG,
                    args.getOrNull(0).orEmpty()
                )
            },
            arguments
        )
        else -> throw IllegalArgumentException("unknown tab type")
    }
}

fun defaultTabs(): List<TabData> {
    return listOf(
        createTabDataFromId(HOME),
        createTabDataFromId(NOTIFICATIONS),
        createTabDataFromId(LOCAL),
        createTabDataFromId(FEDERATED)
    )
}