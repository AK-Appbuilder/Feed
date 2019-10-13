package com.boonapps.feed.di

import androidx.lifecycle.ViewModel
import com.boonapps.feed.postdetail.PostDetailFragment
import com.boonapps.feed.postdetail.PostDetailViewModel
import com.boonapps.feed.posts.PostsFragment
import com.boonapps.feed.posts.PostsViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class PostDetailModule {

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun postDetailFragment(): PostDetailFragment

    @Binds
    @IntoMap
    @ViewModelKey(PostDetailViewModel::class)
    abstract fun bindViewModel(viewmodel: PostDetailViewModel): ViewModel
}
