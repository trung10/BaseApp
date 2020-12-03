package com.pdtrung.baseapp.util

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatDelegate

class ThemeUtils {

    companion object{
        val APP_THEME_DEFAULT: String = ThemeUtils.THEME_NIGHT

        private val THEME_NIGHT = "night"
        private val THEME_DAY = "day"
        private val THEME_BLACK = "black"
        private val THEME_AUTO = "auto"
        private val THEME_SYSTEM = "auto_system"

        fun getDrawable(
            context: Context, @AttrRes attribute: Int,
            @DrawableRes fallbackDrawable: Int
        ): Drawable? {
            val value = TypedValue()
            @DrawableRes val resourceId: Int
            resourceId = if (context.theme.resolveAttribute(attribute, value, true)) {
                value.resourceId
            } else {
                fallbackDrawable
            }
            return context.getDrawable(resourceId)
        }

        @DrawableRes
        fun getDrawableId(
            context: Context, @AttrRes attribute: Int,
            @DrawableRes fallbackDrawableId: Int
        ): Int {
            val value = TypedValue()
            return if (context.theme.resolveAttribute(attribute, value, true)) {
                value.resourceId
            } else {
                fallbackDrawableId
            }
        }

        @ColorInt
        fun getColor(context: Context, @AttrRes attribute: Int): Int {
            val value = TypedValue()
            return if (context.theme.resolveAttribute(attribute, value, true)) {
                value.data
            } else {
                Color.BLACK
            }
        }

        @ColorRes
        fun getColorId(context: Context, @AttrRes attribute: Int): Int {
            val value = TypedValue()
            context.theme.resolveAttribute(attribute, value, true)
            return value.resourceId
        }

        /** this can be replaced with drawableTint in xml once minSdkVersion >= 23  */
        fun getTintedDrawable(
            context: Context,
            @DrawableRes drawableId: Int,
            @AttrRes colorAttr: Int
        ): Drawable? {
            val drawable = context.getDrawable(drawableId) ?: return null
            setDrawableTint(context, drawable, colorAttr)
            return drawable
        }

        fun setDrawableTint(context: Context, drawable: Drawable, @AttrRes attribute: Int) {
            drawable.setColorFilter(getColor(context, attribute), PorterDuff.Mode.SRC_IN)
        }

        fun setAppNightMode(flavor: String?) {
            when (flavor) {
                THEME_NIGHT, THEME_BLACK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                THEME_DAY -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                THEME_AUTO -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_TIME)
                THEME_SYSTEM -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }
}