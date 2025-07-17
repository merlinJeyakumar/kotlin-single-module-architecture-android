package com.nativedevps.myapplication.domain.model.currency

import androidx.annotation.Keep

@Keep
data class CurrencyModel(
    var currency: String,
    var price: Double
)