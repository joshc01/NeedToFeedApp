package com.cs389team4.needtofeed

import android.app.Application
import android.util.Log

import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.AWSDataStorePlugin
import com.amplifyframework.storage.s3.AWSS3StoragePlugin

import com.onesignal.OneSignal

class NeedToFeedApplication : Application() {
    companion object {
        const val ONESIGNAL_APP_ID = "0fe7c2a7-cf9a-41be-81b1-f1e81f40f89e"
        const val TAG = "NeedToFeedApplication"
    }

    override fun onCreate() {
        super.onCreate()

        // Amplify config
        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin()) // Auth
            Amplify.addPlugin(AWSS3StoragePlugin()) // Storage
            Amplify.addPlugin(AWSApiPlugin()) // API
            Amplify.addPlugin(AWSDataStorePlugin()) // DataStore
            Amplify.configure(applicationContext)
            Log.i(TAG, "Initialized Amplify")
        } catch (error: AmplifyException) {
            Log.e(TAG, "Could not initialize Amplify", error)
        }

        // OneSignal config

        // Enable verbose OneSignal logging for debug
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // Initialize OneSignal
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
    }
}
