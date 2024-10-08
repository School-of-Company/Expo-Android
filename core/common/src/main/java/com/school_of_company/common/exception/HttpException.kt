package com.school_of_company.common.exception

// 400: Bad Request
class BadRequestException(
    override val message: String?
) : RuntimeException()

// 401: Unauthorized - No authentication
class UnauthorizedException(
    override val message: String?
) : RuntimeException()

// 403: Forbidden - No permission
class ForBiddenException(
    override val message: String?
) : RuntimeException()

// 404: Not Found - The requested resource could not be found
class NotFoundException(
    override val message: String?
) : RuntimeException()

// 406: Not Acceptable - The client requested an unsupported format
class NotAcceptableException(
    override val message: String?
) : RuntimeException()

// 408: Request Timeout
class TimeOutException(
    override val message: String?
) : RuntimeException()

// 409: Conflict - A conflict occurred
class ConflictException(
    override val message: String?
) : RuntimeException()

// 429: Too Many Requests - Rate limit exceeded
class TooManyRequestException(
    override val message: String?
) : RuntimeException()

// 50X: Server Error
class ServerException(
    override val message: String?
) : RuntimeException()

// Undefined or custom HTTP status codes
class OtherHttpException(
    val code: Int,
    override val message: String?
) : RuntimeException()

// Unknown Error - Unexpected error occurred
class UnKnownException(
    override val message: String?
) : RuntimeException()
