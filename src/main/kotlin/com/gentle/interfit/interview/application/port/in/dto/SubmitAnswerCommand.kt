package com.gentle.interfit.interview.application.port.`in`.dto

data class SubmitAnswerCommand(
    val interviewId: String,
    val questionId: String,
    val answer: String
)