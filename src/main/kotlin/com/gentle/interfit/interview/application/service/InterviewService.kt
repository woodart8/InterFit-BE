package com.gentle.interfit.interview.application.service

import com.gentle.interfit.interview.application.port.`in`.InterviewUseCase
import com.gentle.interfit.interview.application.port.`in`.dto.GenerateFollowUpQuestionCommand
import com.gentle.interfit.interview.application.port.`in`.dto.GenerateFollowUpQuestionResult
import com.gentle.interfit.interview.application.port.`in`.dto.GenerateNewQuestionCommand
import com.gentle.interfit.interview.application.port.`in`.dto.GenerateNewQuestionResult
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
            content = response.question.content
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
            questionId = response.questionId
        )
    }

    override suspend fun generateNewQuestion(
        command: GenerateNewQuestionCommand
    ): GenerateNewQuestionResult {

        val response = interviewAiPort.generateNewQuestion(
            interviewId = command.interviewId
        )

        return GenerateNewQuestionResult(
            questionId = response.id,
            content = response.content
        )
    }

    override suspend fun generateFollowUpQuestion(
        command: GenerateFollowUpQuestionCommand
    ): GenerateFollowUpQuestionResult {

        val response = interviewAiPort.generateFollowUpQuestion(
            interviewId = command.interviewId,
            questionId = command.questionId
        )

        return GenerateFollowUpQuestionResult(
            questionId = response.id,
            content = response.content
        )
    }
}