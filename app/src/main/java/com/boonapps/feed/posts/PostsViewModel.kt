package com.boonapps.feed.posts

import androidx.lifecycle.*
import com.boonapps.feed.Event
import com.boonapps.feed.data.Post
import com.boonapps.feed.data.Result
import com.boonapps.feed.source.PostsRepository
import com.boonapps.feed.util.wrapEspressoIdlingResource
import kotlinx.coroutines.launch
import java.util.ArrayList
import javax.inject.Inject

class PostsViewModel @Inject constructor(
    private val postsRepository: PostsRepository
) : ViewModel() {

    private val _items = MutableLiveData<List<Post>>().apply { value = emptyList() }
    val items: LiveData<List<Post>> = _items

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    // Not used at the moment
    private val isDataLoadingError = MutableLiveData<Boolean>()

    private val _openPostEvent = MutableLiveData<Event<Int>>()
    val openPostEvent: LiveData<Event<Int>> = _openPostEvent

    // This LiveData depends on another so we can use a transformation.
    val empty: LiveData<Boolean> = Transformations.map(_items) {
        it.isEmpty()
    }

    init {
        loadPosts()
    }

    /**
     * Called by Data Binding.
     */
    fun openPost(postId: Int) {
        _openPostEvent.value = Event(postId)
    }

    fun loadPosts() {
        _dataLoading.value = true
        wrapEspressoIdlingResource {

            viewModelScope.launch {
                val postsResult = postsRepository.getPosts()

                if (postsResult is Result.Success) {
                    val posts = postsResult.data

                    isDataLoadingError.value = false
                    _items.value = ArrayList(posts)
                } else {
                    isDataLoadingError.value = false
                    _items.value = emptyList()
                }
                _dataLoading.value = false
            }
        }
    }

    fun refresh() {
        loadPosts()
    }
}
