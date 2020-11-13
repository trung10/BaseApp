package com.pdtrung.baseapp

import android.app.Application
import android.content.Context
import com.pdtrung.baseapp.db.AccountManager
import com.pdtrung.baseapp.db.AppDatabase
import com.pdtrung.baseapp.util.LocaleManager
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class TApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>


    /*
    todo implement Work Manager
    @Inject
    private lateinit var notificationPullJobCreator: NotificationPullJobCreator*/

    private lateinit var appDatabase: AppDatabase
    private lateinit var AccountManager: AccountManager
    private lateinit var serviceLocator: ServiceLocator


    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onCreate() {
        super.onCreate()


    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

    }

    companion object {
        lateinit var localeManager: LocaleManager
    }

    interface ServiceLocator {
        operator fun <T> get(clazz: Class<T>?): T
    }
}