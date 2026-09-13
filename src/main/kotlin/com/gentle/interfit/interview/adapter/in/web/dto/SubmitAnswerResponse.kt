package com.gentle.interfit.interview.adapter.`in`.web.dto

data class SubmitAnswerResponse(
    val interviewId: String,
    val questionId: String,
    val answer: String,
    val nextQuestion: Question
) {
    data class Question(
        val id: String,
        val content: String
    )
}