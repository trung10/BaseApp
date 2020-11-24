package com.pdtrung.baseapp

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.pdtrung.baseapp.db.AccountManager
import com.pdtrung.baseapp.di.Injectable
import com.pdtrung.baseapp.interfaces.PermissionRequester
import java.util.*
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), Injectable {

    @Inject
    lateinit var accountManager: AccountManager

    private val REQUESTER_NONE = Int.MAX_VALUE
    private val requesters: HashMap<Int, PermissionRequester>? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)

        if (requiresLogin()){
            redirectIfNotLoggedIn()
        }


    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
    }

    protected fun redirectIfNotLoggedIn(){
        val account = accountManager.activeAccount

        if (account != null){
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivityWithSlideInAnimation(intent)
            startActivity(intent)
        }
    }

    fun startActivityWithSlideInAnimation(intent: Intent?) {
        super.startActivity(intent)
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }



    protected open fun requiresLogin(): Boolean {
        return true
    }
}