package com.github.ddenaga.parstagram.fragments

import android.util.Log
import com.github.ddenaga.parstagram.models.Post
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser

private const val TAG = "ProfileFragment"
class ProfileFragment : FeedFragment() {
    override fun queryPosts() {
        // Specify which class to query
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        // Find all Post objects
        query.include(Post.KEY_USER)

        // Only return posts from currently signed in user
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser())

        // Return the posts in descending order: ie newer posts will appear first
        query.addDescendingOrder("createdAt")

        // Only return the most recent 20 posts
        query.limit = 20

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

                        listOfPosts.addAll(posts)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }
}