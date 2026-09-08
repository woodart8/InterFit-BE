package com.gentle.interfit.auth.application.port.`in`

import com.gentle.interfit.auth.application.dto.RefreshResult

interface RefreshUseCase {

    fun refresh(refreshToken: String): RefreshResult
}