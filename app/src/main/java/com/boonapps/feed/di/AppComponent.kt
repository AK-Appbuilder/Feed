package com.boonapps.feed.di

import android.content.Context
import com.boonapps.feed.PostsApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        AndroidSupportInjectionModule::class,
        PostsModule::class,
        PostDetailModule::class
    ])
interface AppComponent : AndroidInjector<PostsApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }
}
