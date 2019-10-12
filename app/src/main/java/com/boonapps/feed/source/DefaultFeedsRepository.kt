package com.boonapps.feed.source

import com.boonapps.feed.data.Post
import com.boonapps.feed.data.Result
import com.boonapps.feed.source.local.LocalSource
import com.boonapps.feed.source.remote.RemoteSource
import com.boonapps.feed.util.wrapEspressoIdlingResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentMap
import javax.inject.Inject

class DefaultFeedsRepository @Inject constructor(
     private val remoteSource: RemoteSource,
     private val localSource: LocalSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : DataSource {

    private var cachedTasks: ConcurrentMap<String, Post>? = null

    override suspend fun getTasks(forceUpdate: Boolean): Result<List<Post>> {

        wrapEspressoIdlingResource {

            return withContext(ioDispatcher) {
                // Respond immediately with cache if available and not dirty
                if (!forceUpdate) {
                    cachedTasks?.let { cachedTasks ->
                        return@withContext Success(cachedTasks.values.sortedBy { it.id })
                    }
                }

                val newTasks = fetchTasksFromRemoteOrLocal(forceUpdate)

                // Refresh the cache with the new tasks
                (newTasks as? Success)?.let { refreshCache(it.data) }

                cachedTasks?.values?.let { tasks ->
                    return@withContext Success(tasks.sortedBy { it.id })
                }

                (newTasks as? Success)?.let {
                    if (it.data.isEmpty()) {
                        return@withContext Success(it.data)
                    }
                }

                return@withContext Error(Exception("Illegal state"))
            }
        }
    }
}