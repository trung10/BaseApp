package com.pdtrung.baseapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pdtrung.baseapp.R

class TimelineFragment : Fragment() {

    enum class Kind {
        HOME,
        PUBLIC_LOCAL,
        PUBLIC_FEDERATED,
        TAG,
        USER,
        USER_PINNED,
        USER_WITH_REPLIES,
        FAVOURITES,
        LIST,
        BOOKMARKS
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timeline, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(kind: Kind) =
            TimelineFragment().apply {
                arguments = Bundle().apply {
                }
            }

        @JvmStatic
        fun newInstance(kind: Kind, hashtagOrId: String/*, enableSwipeToRefresh: Boolean*/) =
            TimelineFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}