package com.gentle.interfit.interview.adapter.out.ai.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class InterviewAnswerResponse(
    @JsonProperty("interview_id")
    val interviewId: String,

    @JsonProperty("question_id")
    val questionId: String,

    val answer: String,

    @JsonProperty("next_question")
    val nextQuestion: InterviewQuestion
)