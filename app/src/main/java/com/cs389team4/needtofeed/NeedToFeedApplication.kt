package com.cs389team4.needtofeed

import android.app.Application
import android.util.Log
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.AWSDataStorePlugin
import com.amplifyframework.storage.s3.AWSS3StoragePlugin

class NeedToFeedApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Amplify config
        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin()) // Auth
            Amplify.addPlugin(AWSS3StoragePlugin()) // Storage
            Amplify.addPlugin(AWSApiPlugin()) // API
            Amplify.addPlugin(AWSDataStorePlugin()) // DataStore
            Amplify.configure(applicationContext)
            Log.i("NeedToFeedApplication", "Initialized Amplify")
        } catch (error: AmplifyException) {
            Log.e("NeedToFeedApplication", "Could not initialize Amplify", error)
        }
    }
}
