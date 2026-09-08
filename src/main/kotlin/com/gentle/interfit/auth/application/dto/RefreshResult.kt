package com.gentle.interfit.auth.application.dto

data class RefreshResult (
    val accessToken: String,
    val refreshToken: String
)