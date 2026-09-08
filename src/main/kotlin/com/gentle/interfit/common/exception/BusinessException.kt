package com.gentle.interfit.common.exception

class BusinessException(
    val errorCode: ErrorCode
) : RuntimeException(errorCode.message)