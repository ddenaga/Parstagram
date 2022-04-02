package com.github.ddenaga.parstagram.activities

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.github.ddenaga.parstagram.R
import com.github.ddenaga.parstagram.fragments.ComposeFragment
import com.github.ddenaga.parstagram.fragments.FeedFragment
import com.github.ddenaga.parstagram.models.Post
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.parse.*
import java.io.File

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager: FragmentManager = supportFragmentManager

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnItemSelectedListener { item ->

            var fragmentToShow: Fragment? = null
            when (item.itemId) {


                R.id.action_home -> {
                    fragmentToShow = FeedFragment()
                }

                R.id.action_compose -> {
                    fragmentToShow = ComposeFragment()
                }

                R.id.action_profile -> {
                    // TODO: Navigate to the profile screen
                    Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
                }

            }

            if (fragmentToShow != null) {
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragmentToShow).commit()
            }


            // Return true if user interaction has been handled
            true
        }

        bottomNavigation.selectedItemId = R.id.action_home
        // queryPosts()
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
