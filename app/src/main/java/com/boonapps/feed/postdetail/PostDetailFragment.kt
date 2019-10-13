package com.boonapps.feed.postdetail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.boonapps.feed.R
import com.boonapps.feed.databinding.PostDetailFragmentBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class PostDetailFragment : DaggerFragment() {

    private lateinit var viewDataBinding: PostDetailFragmentBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<PostDetailViewModel> { viewModelFactory }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.post_detail_fragment, container, false)
        viewDataBinding = PostDetailFragmentBinding.bind(view).apply {
            viewmodel = viewModel
        }
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner

        viewModel.loadPost(arguments?.getInt(ARG_POST_ID))

        setHasOptionsMenu(true)
        return view
    }

    companion object {
        const val ARG_POST_ID = "post_id"
    }

}
