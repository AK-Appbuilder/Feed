package com.boonapps.feed.di

import android.content.Context
import androidx.room.Room
import com.boonapps.feed.source.DefaultPostRepository
import com.boonapps.feed.source.PostsRepository
import com.boonapps.feed.source.local.FeedsDatabase
import com.boonapps.feed.source.local.LocalDataSource
import com.boonapps.feed.source.remote.ApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ApplicationModuleBinds::class])
object AppModule {

    @JvmStatic
    @Singleton
    @Provides
    fun providePostLocalDataSource(
        database: FeedsDatabase,
        ioDispatcher: CoroutineDispatcher
    ): LocalDataSource {
        return LocalDataSource(
            database.postDao(),
            database.userDao(),
            database.commentDao(),
            ioDispatcher
        )
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideDataBase(context: Context): FeedsDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            FeedsDatabase::class.java,
            "Posts.db"
        ).build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO

    @JvmStatic
    @Singleton
    @Provides
    fun provideGithubService(): ApiService {
        return Retrofit.Builder()
            .baseUrl("http://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

@Module
abstract class ApplicationModuleBinds {

    @Singleton
    @Binds
    abstract fun bindRepository(repo: DefaultPostRepository): PostsRepository
}
