package com.boonapps.feed

import com.boonapps.feed.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

class PostsApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {

        return DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}