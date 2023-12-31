package com.nativedevps.myapplication.data.rest.user

import com.nativedevps.myapplication.domain.datasource.rest.UserApiService
import com.nativedevps.myapplication.domain.model.user_list.UserListRequestModel
import com.nativedevps.myapplication.domain.model.user_list.UsersListResponseModel
import com.nativedevps.myapplication.utility.emulate
import com.nativedevps.support.base_class.FlowUseCase
import com.nativedevps.support.coroutines.ErrorApiResult
import com.nativedevps.support.coroutines.NetworkResult
import com.nativedevps.support.coroutines.SuccessApiResult
import com.nativedevps.support.utility.networking.emulateNetworkCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

/**
 * Injecting ApiService
 * @return result with status of success/failure {@link NetworkResult}
 */
class UserListRetrievalUseCase @Inject constructor(
    private val exchangeApiService: UserApiService,
) : FlowUseCase<UserListRequestModel, UsersListResponseModel>() {

    override fun performAction(parameters: UserListRequestModel): Flow<NetworkResult<UsersListResponseModel>> {
        return emulate {
            exchangeApiService.userList(parameters.asQueryMap()).emulateNetworkCall()
        }
    }
}