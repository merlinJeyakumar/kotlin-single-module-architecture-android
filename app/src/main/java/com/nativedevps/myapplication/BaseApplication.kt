package com.nativedevps.myapplication

import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.nativedevps.arch.BuildConfig
import com.nativedevps.arch.R
import com.nativedevps.data.repositories.remote.usecase.app_authorize.AppAuthorizeUseCase
import com.nativedevps.myapplication.utility.RemoteConfigurationListener
import com.nativedevps.support.base.NativeDevpsApplication
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication : NativeDevpsApplication(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var appAuthorizeUseCase: AppAuthorizeUseCase

    override val firebaseRemoteConfig: FirebaseRemoteConfig
        get() = FirebaseRemoteConfig.getInstance()

    override fun onCreate() {
        super.onCreate()

        initNativeDevps()
        initRemoteConfiguration()
    }

    override val launcherRes: Int
        get() = R.mipmap.ic_launcher
    override val appNameRes: Int
        get() = R.string.app_name

    private fun initNativeDevps() {
        setVersionDetails(
            BuildConfig.VERSION_CODE,
            BuildConfig.VERSION_NAME
        )
    }

    private fun initRemoteConfiguration() {
        firebaseRemoteConfig.fetchAndActivate()
            .addOnCompleteListener(
                RemoteConfigurationListener(
                    applicationContext,
                    preferenceDataSource,
                    appAuthorizeUseCase
                )
            )
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

}