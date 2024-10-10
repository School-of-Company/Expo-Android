package com.school_of_company.common.network

import com.school_of_company.common.exception.*

class ErrorHandlingBuilder {
    var badRequestAction: () -> Unit = {}
    var unauthorizedAction: () -> Unit = {}
    var forbiddenAction: () -> Unit = {}
    var notFoundAction: () -> Unit = {}
    var notAcceptableAction: () -> Unit = {}
    var timeOutAction: () -> Unit = {}
    var conflictAction: () -> Unit = {}
    var tooManyRequestAction: () -> Unit = {}
    var serverAction: () -> Unit = {}
    var noInternetAction: () -> Unit = {}
    var otherHttpAction: () -> Unit = {}
    var unknownAction: () -> Unit = {}

    fun build(): (Throwable) -> Unit = { throwable ->
        when (throwable) {
            is BadRequestException -> badRequestAction()
            is UnauthorizedException, is TokenExpirationException -> unauthorizedAction()
            is ForbiddenException -> forbiddenAction()
            is NotFoundException -> notFoundAction()
            is NotAcceptableException -> notAcceptableAction()
            is TimeOutException -> timeOutAction()
            is ConflictException -> conflictAction()
            is TooManyRequestException -> tooManyRequestAction()
            is ServerException -> serverAction()
            is NoInternetException -> noInternetAction()
            is OtherHttpException -> otherHttpAction()
            else -> unknownAction()
        }
    }
}

fun Throwable.errorHandling(init: ErrorHandlingBuilder.() -> Unit) {
    ErrorHandlingBuilder().apply(init).build()(this)
}