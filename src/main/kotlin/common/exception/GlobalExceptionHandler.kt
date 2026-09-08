package common.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(
        e: BusinessException
    ): ResponseEntity<ErrorResponse> {

        val errorCode = e.errorCode

        return ResponseEntity
            .status(errorCode.status)
            .body(
                ErrorResponse(
                    status = errorCode.status.value(),
                    code = errorCode.name,
                    message = errorCode.message
                )
            )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(
        e: MethodArgumentNotValidException
    ): ResponseEntity<ErrorResponse> {

        val message = e.bindingResult
            .fieldErrors
            .firstOrNull()
            ?.defaultMessage
            ?: "잘못된 요청입니다."

        return ResponseEntity
            .badRequest()
            .body(
                ErrorResponse(
                    status = 400,
                    code = "VALIDATION_ERROR",
                    message = message
                )
            )
    }
}