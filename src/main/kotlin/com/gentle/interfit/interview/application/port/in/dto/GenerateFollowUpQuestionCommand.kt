package com.gentle.interfit.interview.application.port.`in`.dto

data class GenerateFollowUpQuestionCommand (
    val interviewId: String,
    val questionId: String,
)