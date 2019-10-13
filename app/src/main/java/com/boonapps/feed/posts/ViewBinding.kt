package com.boonapps.feed.posts

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.boonapps.feed.data.Post

/**
 * [BindingAdapter]s for the [Post]s list.
 */
@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<Post>) {
    (listView.adapter as PostsAdapter).submitList(items)
}