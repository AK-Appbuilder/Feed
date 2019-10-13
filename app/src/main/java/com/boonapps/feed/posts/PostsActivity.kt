package com.boonapps.feed.posts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.boonapps.feed.R
import com.boonapps.feed.util.addFragment


class PostsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if( savedInstanceState == null) {
            addFragment(PostsFragment(), R.id.container)
        }
    }


    override fun onBackPressed() {

        val count = supportFragmentManager.backStackEntryCount

        if (count <= 1 ) {
            finish()
        } else {
            supportFragmentManager.popBackStackImmediate()
        }
    }
}
