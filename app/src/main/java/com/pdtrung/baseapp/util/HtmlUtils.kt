package com.pdtrung.baseapp.util

import android.os.Build
import android.text.Html
import android.text.Spanned

class HtmlUtils {
    companion object {
        fun fromHtml(html: String?): Spanned {
            val result: Spanned = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(html)
            }
            /* Html.fromHtml returns trailing whitespace if the html ends in a </p> tag, which
             * all status contents do, so it should be trimmed. */
            return trimTrailingWhitespace(result) as Spanned
        }

        fun toHtml(text: Spanned?): String {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.toHtml(text, Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE)
            } else {
                Html.toHtml(text)
            }
        }

        private fun trimTrailingWhitespace(s: CharSequence): CharSequence? {
            var i = s.length
            do {
                i--
            } while (i >= 0 && Character.isWhitespace(s[i]))
            return s.subSequence(0, i + 1)
        }
    }
}