package com.gentle.interfit.interview.adapter.out.ai.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class InterviewStartRequest(
    @JsonProperty("resume_id")
    val resumeId: String
)