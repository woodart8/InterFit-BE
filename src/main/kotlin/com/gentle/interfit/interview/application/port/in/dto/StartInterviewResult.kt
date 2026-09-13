package com.gentle.interfit.interview.application.port.`in`.dto

data class StartInterviewResult(
    val interviewId: String,
    val questionId: String,
    val questionContent: String
)