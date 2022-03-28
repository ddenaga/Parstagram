package com.github.ddenaga.parstagram

import android.app.Application
import com.github.ddenaga.parstagram.models.Post
import com.parse.Parse
import com.parse.ParseObject

class ParseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        ParseObject.registerSubclass(Post::class.java)

        Parse.initialize(Parse.Configuration.Builder(this)
            .applicationId(BuildConfig.back4app_app_id)
            .clientKey(BuildConfig.back4app_client_key)
            .server(BuildConfig.back4app_server_url)
            .build())
    }
}