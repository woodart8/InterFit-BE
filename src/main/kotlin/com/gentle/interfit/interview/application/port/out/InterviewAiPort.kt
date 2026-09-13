package com.gentle.interfit.interview.application.port.out

import com.gentle.interfit.interview.adapter.out.ai.dto.InterviewAnswerResponse
import com.gentle.interfit.interview.adapter.out.ai.dto.InterviewQuestion
import com.gentle.interfit.interview.adapter.out.ai.dto.InterviewStartResponse

interface InterviewAiPort {

    suspend fun startInterview(
        resumeId: String
    ): InterviewStartResponse

    suspend fun submitAnswer(
        interviewId: String,
        questionId: String,
        answer: String
    ): InterviewAnswerResponse

    suspend fun generateNewQuestion(
        interviewId: String
    ): InterviewQuestion

    suspend fun generateFollowUpQuestion(
        interviewId: String,
        questionId: String
    ): InterviewQuestion
}