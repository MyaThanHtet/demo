package com.mya.demo.exception

import com.mya.demo.model.dto.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import java.time.LocalDateTime

@ResponseStatus(HttpStatus.NOT_FOUND)
class ResourceNotFoundException(
    message: String
) : RuntimeException(message)

@ExceptionHandler(ResourceNotFoundException::class)
@ResponseStatus(HttpStatus.NOT_FOUND)
fun handleResourceNotFoundException(ex: ResourceNotFoundException): ErrorResponse {
    return ErrorResponse(
        timestamp = LocalDateTime.now(),
        status = HttpStatus.NOT_FOUND.value(),
        error = "Not Found",
        message = ex.message ?: "Resource not found."
    )
}

@ExceptionHandler(MethodArgumentNotValidException::class)
@ResponseStatus(HttpStatus.BAD_REQUEST)
fun handleValidationExceptions(ex: MethodArgumentNotValidException): ErrorResponse {

    val errors = ex.bindingResult.fieldErrors.joinToString(" | ") { error ->
        "${error.field}: ${error.defaultMessage}"
    }

    return ErrorResponse(
        timestamp = LocalDateTime.now(),
        status = HttpStatus.BAD_REQUEST.value(),
        error = "Bad Request (Validation Failed)",
        message = errors
    )
}

@ExceptionHandler(Exception::class)
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
fun handleGenericException(ex: Exception): ErrorResponse {
    return ErrorResponse(
        timestamp = LocalDateTime.now(),
        status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
        error = "Internal Server Error",
        message = "An unexpected error occurred: ${ex.message}"
    )
}