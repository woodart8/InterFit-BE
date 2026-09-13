package com.gentle.interfit.interview.adapter.out.ai.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class InterviewAnswerRequest(
    @JsonProperty("question_id")
    val questionId: String,
    val answer: String
)