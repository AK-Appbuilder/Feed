package com.boonapps.feed.posts

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.boonapps.feed.BuildConfig
import com.boonapps.feed.EventObserver
import com.boonapps.feed.R
import com.boonapps.feed.databinding.PostFragmentBinding
import com.boonapps.feed.postdetail.PostDetailFragment
import com.boonapps.feed.util.addFragment
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_main.view.*
import timber.log.Timber
import javax.inject.Inject

class PostsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<PostsViewModel> { viewModelFactory }

    private lateinit var viewDataBinding: PostFragmentBinding

    private lateinit var listAdapter: PostsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = PostFragmentBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel

        }
        setHasOptionsMenu(true)
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Set the lifecycle owner to the lifecycle of the view

        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupListAdapter()
        setupNavigation()

        viewModel.loadPosts()


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Snackbar.make(view, BuildConfig.TEST_URL, Snackbar.LENGTH_LONG).show()
    }

    private fun setupNavigation() {
        viewModel.openPostEvent.observe(this, EventObserver {
            openPostDetail(it)
        })
    }

    private fun openPostDetail(postId: Int) {
        val fragment = PostDetailFragment()
        fragment.arguments = Bundle().apply {
            this.putInt(PostDetailFragment.ARG_POST_ID, postId)
        }
        (activity as AppCompatActivity).addFragment(fragment, R.id.container)
    }

    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            listAdapter = PostsAdapter(viewModel)
            viewDataBinding.postList.adapter = listAdapter
        } else {
            Timber.w("ViewModel not initialized when attempting to set up adapter.")
        }
    }
}
