package com.github.ddenaga.parstagram.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.ddenaga.parstagram.R
import com.github.ddenaga.parstagram.adapters.PostAdapter
import com.github.ddenaga.parstagram.models.Post
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery

private const val TAG = "FeedFragment"
class FeedFragment : Fragment() {

    lateinit var rvPosts: RecyclerView
    lateinit var adapter: PostAdapter
    private var listOfPosts: MutableList<Post> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvPosts = view.findViewById(R.id.rvPosts)
        // 1. Create a layout for each row in list
        // 2. Create a data source for each row
        // 3. Create adapter that will bridge data and raw layout
        // 4. Set adapter on RecyclerView
        adapter = PostAdapter(requireContext(), listOfPosts)
        rvPosts.adapter = adapter
        // 5. Set layout manager on RecyclerView
        rvPosts.layoutManager = LinearLayoutManager(requireContext())
        queryPosts()
    }

    // Query for all posts in our server
    fun queryPosts() {
        // Specify which class to query
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        // Find all Post objects
        query.include(Post.KEY_USER)

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