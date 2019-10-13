package com.boonapps.feed.postdetail

import androidx.lifecycle.*
import com.boonapps.feed.data.Post
import com.boonapps.feed.data.Result
import com.boonapps.feed.data.User
import com.boonapps.feed.source.PostsRepository
import com.boonapps.feed.util.wrapEspressoIdlingResource
import kotlinx.coroutines.launch
import javax.inject.Inject

class PostDetailViewModel @Inject constructor(
    private val feedsRepository: PostsRepository
) : ViewModel() {

    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post> = _post

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _commentsCount = MutableLiveData<Int>()
    val commentsCount = _commentsCount

    private val _isDataAvailable = MutableLiveData<Boolean>()
    val isDataAvailable: LiveData<Boolean> = _isDataAvailable

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    fun loadPost(postId: Int?) {
        if (_isDataAvailable.value == true || _dataLoading.value == true) {
            return
        }

        // Show loading indicator
        _dataLoading.value = true

        wrapEspressoIdlingResource {

            viewModelScope.launch {
                if (postId != null) {
                    feedsRepository.getPost(postId).let { result ->
                        if (result is Result.Success) {
                            onPostLoaded(result.data)
                            loadUser(result.data.userId)
                            loadCommentsCount(result.data.id)
                        } else {
                            onDataNotAvailable(result)
                        }
                    }
                }
                _dataLoading.value = false
            }
        }
    }

    private fun loadUser(userId: Int?) {
        wrapEspressoIdlingResource {

            viewModelScope.launch {
                if (userId != null) {
                    feedsRepository.getUser(userId).let { result ->
                        if (result is Result.Success) {
                            onUserLoaded(result.data)
                        }
                    }
                }
                _dataLoading.value = false
            }
        }
    }


    private fun loadCommentsCount(postId: Int?){
        wrapEspressoIdlingResource {

            viewModelScope.launch {
                if (postId != null) {
                    feedsRepository.getCommentsCount(postId).let { result ->
                        if (result is Result.Success) {
                            onCommentCountLoaded(result.data)
                        }
                    }
                }
                _dataLoading.value = false
            }
        }
    }

    private fun setPost(post: Post?) {
        this._post.value = post
        _isDataAvailable.value = post != null
    }

    private fun onUserLoaded(user: User) {
        this._user.value = user
    }

    private fun onCommentCountLoaded(no: Int) {
        this.commentsCount.value = no
    }

    private fun onPostLoaded(post: Post) {
        setPost(post)
    }

    private fun onDataNotAvailable(result: Result<Post>) {
        _post.value = null
        _isDataAvailable.value = false
    }
}
