package com.gentle.interfit.interview.adapter.out.ai.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class InterviewGenerateFollowUpRequest (
    @JsonProperty("question_id")
    val questionId : String,
)