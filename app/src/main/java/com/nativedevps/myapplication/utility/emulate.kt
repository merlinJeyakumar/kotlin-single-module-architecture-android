package com.nativedevps.myapplication.utility

import com.nativedevps.support.coroutines.ErrorApiResult
import com.nativedevps.support.coroutines.NetworkResult
import com.nativedevps.support.coroutines.SuccessApiResult
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

fun <T> emulate(function: suspend () -> NetworkResult<T>): Flow<NetworkResult<T>> {
    return channelFlow {
        try {
            trySend(function.invoke())
        } catch (e: Exception) {
            trySend(ErrorApiResult(e.message ?: "execute with debug", e))
        }
    }
}