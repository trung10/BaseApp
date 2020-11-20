package com.pdtrung.baseapp.fragment

import com.pdtrung.baseapp.di.Injectable
import com.pdtrung.baseapp.entity.Filter
import java.util.regex.Matcher

abstract class SFragment : BaseFragment(), Injectable {


    protected abstract fun removeItem(position: Int)
    protected abstract fun onReblog(reblog: Boolean, position: Int)

    private lateinit var nottomSheetActivity: BottomSheetActivity

    private lateinit var filters: List<Filter>
    private lateinit var filterRemoveRegex: Boolean = false
    private lateinit var filterRemoveRegexMatcherL: Matcher
}