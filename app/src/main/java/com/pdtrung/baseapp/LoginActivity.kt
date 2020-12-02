package com.pdtrung.baseapp

import android.os.Bundle
import com.pdtrung.baseapp.di.Injectable

class LoginActivity : BaseActivity(), Injectable {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


    }
}