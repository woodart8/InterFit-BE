package com.gentle.interfit.interview.application.service

import com.gentle.interfit.interview.application.port.`in`.InterviewUseCase
import com.gentle.interfit.interview.application.port.`in`.dto.StartInterviewCommand
import com.gentle.interfit.interview.application.port.`in`.dto.StartInterviewResult
import com.gentle.interfit.interview.application.port.`in`.dto.SubmitAnswerCommand
import com.gentle.interfit.interview.application.port.`in`.dto.SubmitAnswerResult
import com.gentle.interfit.interview.application.port.out.InterviewAiPort
import org.springframework.stereotype.Service

@Service
class InterviewService(
    private val interviewAiPort: InterviewAiPort
) : InterviewUseCase {

    override suspend fun startInterview(
        command: StartInterviewCommand
    ): StartInterviewResult {

        val response = interviewAiPort.startInterview(
            resumeId = command.resumeId
        )

        return StartInterviewResult(
            interviewId = response.interviewId,
            questionId = response.question.id,
            questionContent = response.question.content
        )
    }

    override suspend fun submitAnswer(
        command: SubmitAnswerCommand
    ): SubmitAnswerResult {

        val response = interviewAiPort.submitAnswer(
            interviewId = command.interviewId,
            questionId = command.questionId,
            answer = command.answer
        )

        return SubmitAnswerResult(
            interviewId = response.interviewId,
            questionId = response.questionId,
            answer = response.answer,
            nextQuestionId = response.nextQuestion.id,
            nextQuestionContent = response.nextQuestion.content
        )
    }
}