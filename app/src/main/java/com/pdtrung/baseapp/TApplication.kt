package com.pdtrung.baseapp

import android.app.Application
import android.content.Context
import com.pdtrung.baseapp.util.LocaleManager
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class TApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private lateinit var localeManager: LocaleManager

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onCreate() {
        super.onCreate()


    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

    }
}