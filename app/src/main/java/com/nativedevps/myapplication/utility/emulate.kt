package com.nativedevps.myapplication.utility

import com.nativedevps.support.coroutines.ErrorApiResult
import com.nativedevps.support.coroutines.NetworkResult
import com.nativedevps.support.coroutines.SuccessApiResult
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.flow.channelFlow

fun <T> emulate(execution: suspend (ProducerScope<NetworkResult<Any>>) -> Unit) = channelFlow<NetworkResult<T>> {
    try {
        trySend(SuccessApiResult(execution(this)))
    } catch (e: Exception) {
        trySend(ErrorApiResult(e.message ?: "execute with debug", e))
    }
}