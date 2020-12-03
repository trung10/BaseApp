package com.pdtrung.baseapp.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.preference.PreferenceManager
import com.pdtrung.baseapp.R
import com.pdtrung.baseapp.entity.Status
import com.pdtrung.baseapp.interfaces.LinkListener
import java.net.URI
import java.net.URISyntaxException

class LinkHelper {

    companion object {
        fun getDomain(urlString: String): String {
            val uri: URI = try {
                URI(urlString)
            } catch (e: URISyntaxException) {
                return ""
            }
            val host = uri.host
            return when {
                host == null -> {
                    ""
                }
                host.startsWith("www.") -> {
                    host.substring(4)
                }
                else -> {
                    host
                }
            }
        }

        /**
         * Finds links, mentions, and hashtags in a piece of text and makes them clickable, associating
         * them with callbacks to notify when they're clicked.
         *
         * @param view the returned text will be put in
         * @param content containing text with mentions, links, or hashtags
         * @param mentions any '@' mentions which are known to be in the content
         * @param listener to notify about particular spans that are clicked
         */
        fun setClickableText(
            view: TextView, content: Spanned,
            mentions: List<Status.Mention>?,
            listener: LinkListener
        ) {
            val builder = SpannableStringBuilder(content)
            val urlSpans = content.getSpans(0, content.length, URLSpan::class.java)
            for (span in urlSpans) {
                val start = builder.getSpanStart(span)
                val end = builder.getSpanEnd(span)
                val flags = builder.getSpanFlags(span)
                val text = builder.subSequence(start, end)
                var customSpan: ClickableSpan? = null
                if (text[0] == '#') {
                    val tag = text.subSequence(1, text.length).toString()
                    customSpan = object : ClickableSpanNoUnderline() {
                        override fun onClick(widget: View) {
                            listener.onViewTag(tag)
                        }
                    }
                } else if (text[0] == '@' && mentions != null && mentions.isNotEmpty()) {
                    val accountUsername = text.subSequence(1, text.length).toString()
                    /* There may be multiple matches for users on different instances with the same
                 * username. If a match has the same domain we know it's for sure the same, but if
                 * that can't be found then just go with whichever one matched last. */
                    var id: String? = null
                    for (mention in mentions) {
                        if (mention.localUsername.contentEquals(accountUsername)) {
                            id = mention.id
                            if (mention.url.contains(getDomain(span.url))) {
                                break
                            }
                        }
                    }
                    if (id != null) {
                        val accountId: String = id
                        customSpan = object : ClickableSpanNoUnderline() {
                            override fun onClick(widget: View) {
                                listener.onViewAccount(accountId)
                            }
                        }
                    }
                }
                if (customSpan == null) {
                    customSpan = object : CustomURLSpan(span.url) {
                        override fun onClick(widget: View) {
                            listener.onViewUrl(url)
                        }
                    }
                }
                builder.removeSpan(span)
                builder.setSpan(customSpan, start, end, flags)

                /* Add zero-width space after links in end of line to fix its too large hitbox.
             * See also : https://github.com/tuskyapp/Tusky/issues/846
             *            https://github.com/tuskyapp/Tusky/pull/916 */
                if (end >= builder.length || builder.subSequence(end, end + 1).toString() == "\n") {
                    builder.insert(end, "\u200B")
                }
            }
            view.text = builder
            view.movementMethod = LinkMovementMethod.getInstance()
        }

        /**
         * Put mentions in a piece of text and makes them clickable, associating them with callbacks to
         * notify when they're clicked.
         *
         * @param view the returned text will be put in
         * @param mentions any '@' mentions which are known to be in the content
         * @param listener to notify about particular spans that are clicked
         */
        fun setClickableMentions(
            view: TextView, mentions: Array<Status.Mention>?, listener: LinkListener
        ) {
            if (mentions == null || mentions.size == 0) {
                view.text = null
                return
            }
            val builder = SpannableStringBuilder()
            var start = 0
            var end = 0
            var flags: Int
            var firstMention = true
            for (mention in mentions) {
                val accountUsername: String = mention.localUsername
                val accountId: String = mention.id
                val customSpan: ClickableSpan = object : ClickableSpanNoUnderline() {
                    override fun onClick(widget: View) {
                        listener.onViewAccount(accountId)
                    }
                }
                end += 1 + accountUsername.length // length of @ + username
                flags = builder.getSpanFlags(customSpan)
                if (firstMention) {
                    firstMention = false
                } else {
                    builder.append(" ")
                    start += 1
                    end += 1
                }
                builder.append("@")
                builder.append(accountUsername)
                builder.setSpan(customSpan, start, end, flags)
                builder.append("\u200B") // same reasonning than in setClickableText
                end += 1 // shift position to take the previous character into account
                start = end
            }
            view.text = builder
            view.movementMethod = LinkMovementMethod.getInstance()
        }

        /**
         * Opens a link, depending on the settings, either in the browser or in a custom tab
         *
         * @param url a string containing the url to open
         * @param context context
         */
        fun openLink(url: String?, context: Context) {
            val uri = Uri.parse(url).normalizeScheme()
            val useCustomTabs = PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean("customTabs", false)
            if (useCustomTabs) {
                openLinkInCustomTab(uri, context)
            } else {
                openLinkInBrowser(uri, context)
            }
        }

        /**
         * opens a link in the browser via Intent.ACTION_VIEW
         *
         * @param uri the uri to open
         * @param context context
         */
        fun openLinkInBrowser(uri: Uri?, context: Context) {
            val intent = Intent(Intent.ACTION_VIEW, uri)
            try {
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Log.w("LinkHelper", "Actvity was not found for intent, $intent")
            }
        }

        /**
         * tries to open a link in a custom tab
         * falls back to browser if not possible
         *
         * @param uri the uri to open
         * @param context context
         */
        fun openLinkInCustomTab(uri: Uri?, context: Context) {
            val toolbarColor: Int = ThemeUtils.getColor(context, R.attr.custom_tab_toolbar)
            val customTabsIntent = CustomTabsIntent.Builder()
                .setToolbarColor(toolbarColor)
                .setShowTitle(true)
                .build()
            try {
                customTabsIntent.launchUrl(context, uri)
            } catch (e: ActivityNotFoundException) {
                Log.w("LinkHelper", "Activity was not found for intent $customTabsIntent")
                openLinkInBrowser(uri, context)
            }
        }
    }
}