package com.github.ddenaga.parstagram

import android.app.Application
import com.parse.Parse

class ParseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Parse.initialize(Parse.Configuration.Builder(this)
            .applicationId(BuildConfig.back4app_app_id)
            .clientKey(BuildConfig.back4app_client_key)
            .server(BuildConfig.back4app_server_url)
            .build())
    }
}