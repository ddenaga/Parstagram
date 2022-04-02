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
import com.github.ddenaga.parstagram.fragments.ProfileFragment
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
                    fragmentToShow = ProfileFragment()
                }

            }

            if (fragmentToShow != null) {
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragmentToShow).commit()
            }


            // Return true if user interaction has been handled
            true
        }

        bottomNavigation.selectedItemId = R.id.action_home
    }

}
