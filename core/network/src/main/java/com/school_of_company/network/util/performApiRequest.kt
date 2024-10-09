package com.school_of_company.network.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

internal inline fun <reified T> performApiRequest(crossinline apiCall: suspend () -> T): Flow<T> = flow {
    emit(
        ExpoApiHandler<T>()
            .httpRequest { apiCall() }
            .sendRequest()
    )
}.flowOn(Dispatchers.IO)