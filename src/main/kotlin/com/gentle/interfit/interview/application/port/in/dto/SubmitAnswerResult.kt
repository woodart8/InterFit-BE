package com.gentle.interfit.interview.application.port.`in`.dto

data class SubmitAnswerResult(
    val interviewId: String,
    val questionId: String,
    val answer: String,
    val nextQuestionId: String,
    val nextQuestionContent: String
)