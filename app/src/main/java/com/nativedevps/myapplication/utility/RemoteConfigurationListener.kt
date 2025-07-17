package com.nativedevps.myapplication.utility

import android.content.Context
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.nativedevps.data.repositories.remote.usecase.app_authorize.AppAuthorizeUseCase
import com.nativedevps.domain.datasources.local.IDataStorePreferenceDataSource
import com.nativedevps.support.base.firebase_remote_config.NativeDevelopersRemoteConfigurationListener

class RemoteConfigurationListener(
    context: Context,
    private val dataStoreRepositoryDataSource: IDataStorePreferenceDataSource,
    private val appAuthorizeUseCase: AppAuthorizeUseCase
) : NativeDevelopersRemoteConfigurationListener(
    context,
    dataStoreRepositoryDataSource,
    appAuthorizeUseCase
) {
    override fun onConfigurationReceived(
        isSuccess: Boolean,
        firebaseRemoteConfig: FirebaseRemoteConfig,
        exception: Exception?,
    ) {
    }
}