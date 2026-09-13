package com.gentle.interfit.interview.adapter.out.ai.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class InterviewStartResponse(
    @JsonProperty("interview_id")
    val interviewId: String,
    val question: InterviewQuestion
)