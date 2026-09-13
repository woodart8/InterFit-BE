package com.gentle.interfit.interview.application.port.`in`

import com.gentle.interfit.interview.application.port.`in`.dto.GenerateFollowUpQuestionCommand
import com.gentle.interfit.interview.application.port.`in`.dto.GenerateFollowUpQuestionResult
import com.gentle.interfit.interview.application.port.`in`.dto.GenerateNewQuestionCommand
import com.gentle.interfit.interview.application.port.`in`.dto.GenerateNewQuestionResult
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

    suspend fun generateNewQuestion(
        command: GenerateNewQuestionCommand
    ): GenerateNewQuestionResult

    suspend fun generateFollowUpQuestion(
        command: GenerateFollowUpQuestionCommand
    ): GenerateFollowUpQuestionResult
}