package com.pdtrung.baseapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import retrofit2.Call

open class BaseFragment : Fragment() {
    protected lateinit var callList: List<Call<Any>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        callList = ArrayList()
    }

    override fun onDestroy() {
        for (call in callList) {
            call.cancel()
        }

        super.onDestroy()
    }
}