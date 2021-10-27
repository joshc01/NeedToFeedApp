package com.cs389team4.needtofeed

import android.app.Application
import android.util.Log
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.storage.s3.AWSS3StoragePlugin

class AmplifyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin()) // Auth
            Amplify.addPlugin(AWSS3StoragePlugin()) // Storage
            Amplify.addPlugin(AWSApiPlugin()) // API
            Amplify.configure(applicationContext)
            Log.i("AmplifyApplication", "Initialized Amplify")
        } catch (error: AmplifyException) {
            Log.e("AmplifyApplication", "Could not initialize Amplify", error)
        }
    }
}
