package com.github.ddenaga.parstagram.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.ddenaga.parstagram.R
import com.github.ddenaga.parstagram.models.Post
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        queryPosts()
    }

    // Query for all posts in our server
    fun queryPosts() {
        // Specify which class to query
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        // Find all Post objects
        query.include(Post.KEY_USER)
        query.findInBackground(object: FindCallback<Post> {
            override fun done(posts: MutableList<Post>?, e: ParseException?) {
                if (e != null) {
                    // Something has went wrong
                    Log.e(TAG, "Error in fetching posts: $e")
                    e.printStackTrace()
                }
                else {
                    if (posts != null) {
                        // Print out all the posts
                        for (post in posts) {
                            Log.i(TAG, "Post from ${post.getUser()?.username}: ${post.getDescription()}")
                        }
                    }
                }
            }
        })
    }
}
