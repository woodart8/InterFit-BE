package com.gentle.interfit.auth.application.dto

data class LoginResult (
    val accessToken: String,
    val refreshToken: String
)