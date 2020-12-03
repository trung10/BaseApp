package com.pdtrung.baseapp.util

import android.os.Parcel
import android.os.Parcelable
import android.text.TextPaint
import android.text.style.URLSpan
import android.view.View

open class CustomURLSpan : URLSpan {
    constructor(url: String) : super(url)

    private constructor(src: Parcel) : super(src)

    override fun onClick(widget: View) {
        LinkHelper.openLink(url, widget.context)
    }

    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        ds.isUnderlineText = false
    }

    companion object {
        val CREATOR: Parcelable.Creator<CustomURLSpan> =
            object : Parcelable.Creator<CustomURLSpan> {
                override fun createFromParcel(source: Parcel?): CustomURLSpan {
                    return CustomURLSpan(source!!)
                }

                override fun newArray(size: Int): Array<CustomURLSpan?> {
                    return arrayOfNulls<CustomURLSpan>(size)
                }
            }
    }
}