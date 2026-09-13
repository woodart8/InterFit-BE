package com.gentle.interfit.interview.application.port.`in`

import com.gentle.interfit.interview.application.port.`in`.dto.StartInterviewCommand
import com.gentle.interfit.interview.application.port.`in`.dto.StartInterviewResult
import com.gentle.interfit.interview.application.port.`in`.dto.SubmitAnswerCommand
import com.gentle.interfit.interview.application.port.`in`.dto.SubmitAnswerResult

interface InterviewUseCase {

    suspend fun startInterview(
        command: StartInterviewCommand
    ): StartInterviewResult

    suspend fun submitAnswer(
        command: SubmitAnswerCommand
    ): SubmitAnswerResult
}