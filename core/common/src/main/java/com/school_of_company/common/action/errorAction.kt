package com.school_of_company.common.action

internal fun badRequestExceptionAction() {
    println("Handling BadRequest")
}

internal fun unauthorizedExceptionAction() {
    println("Handling Unauthorized")
}

internal fun tokenExpirationExceptionAction() {
    println("Handling TokenExpiration")
}

internal fun forbiddenExceptionAction() {
    println("Handling Forbidden")
}

internal fun notFoundExceptionAction() {
    println("Handling NotFound")
}

internal fun notAcceptableExceptionAction() {
    println("Handling NotAcceptable")
}

internal fun timeOutExceptionAction() {
    println("Handling TimeOut")
}

internal fun conflictExceptionAction() {
    println("Handling Conflict")
}

internal fun tooManyRequestExceptionAction() {
    println("Handling TooManyRequest")
}

internal fun serverExceptionAction() {
    println("Handling Server")
}

internal fun noInternetExceptionAction() {
    println("Handling NoInternet")
}

internal fun otherHttpExceptionAction() {
    println("Handling OtherHttpException")
}

internal fun unKnownExceptionAction() {
    println("Handling UnKnown")
}