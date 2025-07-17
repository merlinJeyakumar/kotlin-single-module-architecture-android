package com.nativedevps.myapplication.domain.model

import androidx.annotation.Keep

@Keep
data class UserInputModel(
    var currency: String,
    var currencyPrice: Double = 0.0,
    var input: Double = 0.0
)