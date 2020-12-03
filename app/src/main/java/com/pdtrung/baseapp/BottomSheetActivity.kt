package com.pdtrung.baseapp

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.VisibleForTesting
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.pdtrung.baseapp.util.LinkHelper
import java.net.URI
import java.net.URISyntaxException

abstract class BottomSheetActivity : BaseActivity() {
    lateinit var bottomSheet: BottomSheetBehavior<LinearLayout>
    var searchUrl: String? = null

    /*
    todo change
    @Inject
    lateinit var mastodonApi: MastodonApi*/


    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)

        val bottomSheetLayout: LinearLayout = findViewById(R.id.item_status_bottom_sheet)
        bottomSheet = BottomSheetBehavior.from(bottomSheetLayout)
        bottomSheet.state = BottomSheetBehavior.STATE_HIDDEN

        bottomSheet.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN)
                    cancelActiveSearch()
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })

    }

    open fun viewUrl(
        url: String,
        lookupFallbackBehavior: PostLookupFallbackBehavior = PostLookupFallbackBehavior.OPEN_IN_BROWSER
    ){

        if (!looksLikeMastodonUrl(url)){
            openLink(url)
        }
    }

    @VisibleForTesting
    fun cancelActiveSearch(){
        if (isSearching())
            onEndSearch(searchUrl)
    }

    @VisibleForTesting
    fun isSearching(): Boolean {
        return searchUrl != null
    }

    @VisibleForTesting
    fun onEndSearch(url: String?) {
        if (url == searchUrl) {
            // Don't clear query if there's no match,
            // since we might just now be getting the response for a canceled search
            searchUrl = null
            hideQuerySheet()
        }
    }

    @VisibleForTesting
    open fun openLink(url: String) {
        LinkHelper.openLink(url, this)
    }


    private fun hideQuerySheet() {
        bottomSheet.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun showQuerySheet() {
        bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
    }
}

// https://mastodon.foo.bar/@User
// https://mastodon.foo.bar/@User/43456787654678
// https://pleroma.foo.bar/users/User
// https://pleroma.foo.bar/users/43456787654678
// https://pleroma.foo.bar/notice/43456787654678
// https://pleroma.foo.bar/objects/d4643c42-3ae0-4b73-b8b0-c725f5819207
fun looksLikeMastodonUrl(urlString: String): Boolean {
    val uri: URI
    try {
        uri = URI(urlString)
    } catch (e: URISyntaxException) {
        return false
    }

    if (uri.query != null ||
        uri.fragment != null ||
        uri.path == null) {
        return false
    }

    val path = uri.path
    return path.matches("^/@[^/]+$".toRegex()) ||
            path.matches("^/users/[^/]+$".toRegex()) ||
            path.matches("^/@[^/]+/\\d+$".toRegex()) ||
            path.matches("^/notice/\\d+$".toRegex()) ||
            path.matches("^/objects/[-a-f0-9]+$".toRegex())
}

enum class PostLookupFallbackBehavior {
    OPEN_IN_BROWSER,
    DISPLAY_ERROR,
}