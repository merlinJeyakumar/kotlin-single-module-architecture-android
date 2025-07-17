package com.nativedevps.myapplication.ui.home

import android.app.Application
import com.nativedevps.base.NativeDevpsBaseViewModel
import com.nativedevps.data.repositories.remote.usecase.app_authorize.AppAuthorizeUseCase
import com.nativedevps.data.repositories.remote.usecase.get_account.RetrieveAccountUseCase
import com.nativedevps.myapplication.data.rest.user.UserListRetrievalUseCase
import com.nativedevps.myapplication.domain.datasource.local.IPreferences
import com.nativedevps.myapplication.domain.model.user_list.UserListRequestModel
import com.nativedevps.myapplication.domain.model.user_list.UsersListResponseModel
import com.nativedevps.support.coroutines.ErrorApiResult
import com.nativedevps.support.coroutines.NetworkResult
import com.nativedevps.support.inline.toJson
import com.nativedevps.support.utility.debugging.Log
import com.nativedevps.support.utility.encryption.SecurityUtil
import com.nativedevps.support.utility.threading.runOnAsyncThread
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    private val userListRetrievalUseCase: UserListRetrievalUseCase,
    private val preferences: IPreferences
) : NativeDevpsBaseViewModel(application) {
    @Inject
    lateinit var retrieveAccountUseCase: RetrieveAccountUseCase

    @Inject
    lateinit var appAuthorizeUseCase: AppAuthorizeUseCase

    val currencies get() = preferences.userModel //flow collector of persistence to retrieve currencies

    override fun onCreate() {
        //noop
    }

    /*
    * Subscribing currencies from rest api and listening for success/error result
    * */
    fun retrieveUserList(): Flow<NetworkResult<UsersListResponseModel>> {
        return userListRetrievalUseCase.invoke(
            UserListRequestModel(
                1, 10
            )
        ).onEach {
            if (it is ErrorApiResult) {
                toast(it.message)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

    fun mockCallInitialApis() = runOnAsyncThread {
        val fingerPrint = SecurityUtil(context).getFingerPrint()
        Log.d("fingerPrint: $fingerPrint")

        appAuthorizeUseCase.invoke(Unit).firstOrNull()?.also {
            //this do something
        }
        val accountResponse = retrieveAccountUseCase.invoke(Unit).firstOrNull()?.also {
            Log.e("accountResponse", "accountResponse ${it.toJson()}")
        }
    }

}