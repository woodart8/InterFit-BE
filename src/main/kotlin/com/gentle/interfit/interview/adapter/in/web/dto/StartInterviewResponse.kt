package com.gentle.interfit.interview.adapter.`in`.web.dto

data class StartInterviewResponse(
    val interviewId: String,
    val question: Question
) {
    data class Question(
        val id: String,
        val content: String
    )
}