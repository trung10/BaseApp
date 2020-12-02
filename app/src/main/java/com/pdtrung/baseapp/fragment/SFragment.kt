package com.pdtrung.baseapp.fragment

import android.content.Intent
import com.pdtrung.baseapp.BottomSheetActivity
import com.pdtrung.baseapp.R
import com.pdtrung.baseapp.di.Injectable
import com.pdtrung.baseapp.entity.Filter
import java.util.regex.Matcher

abstract class SFragment : BaseFragment(), Injectable {


    protected abstract fun removeItem(position: Int)
    protected abstract fun onReblog(reblog: Boolean, position: Int)

    private lateinit var nottomSheetActivity: BottomSheetActivity

    private lateinit var filters: List<Filter>
    private var filterRemoveRegex: Boolean = false
    private lateinit var filterRemoveRegexMatcherL: Matcher

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        activity?.let {
            it.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
        }
    }

}