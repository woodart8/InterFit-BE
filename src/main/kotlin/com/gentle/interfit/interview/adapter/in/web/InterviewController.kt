package com.gentle.interfit.interview.adapter.`in`.web

import com.gentle.interfit.interview.adapter.`in`.web.dto.GenerateFollowUpRequest
import com.gentle.interfit.interview.adapter.`in`.web.dto.InterviewQuestion
import com.gentle.interfit.interview.adapter.`in`.web.dto.StartInterviewRequest
import com.gentle.interfit.interview.adapter.`in`.web.dto.StartInterviewResponse
import com.gentle.interfit.interview.adapter.`in`.web.dto.SubmitAnswerRequest
import com.gentle.interfit.interview.adapter.`in`.web.dto.SubmitAnswerResponse
import com.gentle.interfit.interview.application.port.`in`.InterviewUseCase
import com.gentle.interfit.interview.application.port.`in`.dto.GenerateFollowUpQuestionCommand
import com.gentle.interfit.interview.application.port.`in`.dto.GenerateNewQuestionCommand
import com.gentle.interfit.interview.application.port.`in`.dto.StartInterviewCommand
import com.gentle.interfit.interview.application.port.`in`.dto.SubmitAnswerCommand
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/interviews")
class InterviewController(
    private val interviewUseCase: InterviewUseCase
) {

    @PostMapping
    suspend fun startInterview(
        @RequestBody request: StartInterviewRequest
    ): ResponseEntity<StartInterviewResponse> {

        val result = interviewUseCase.startInterview(
            StartInterviewCommand(
                resumeId = request.resumeId
            )
        )

        return ResponseEntity.ok(
            StartInterviewResponse(
                interviewId = result.interviewId,
                interviewQuestion = InterviewQuestion(
                    questionId = result.questionId,
                    content = result.content
                )
            )
        )
    }

    @PostMapping("/{interviewId}/answer")
    suspend fun submitAnswer(
        @PathVariable interviewId: String,
        @RequestBody request: SubmitAnswerRequest
    ): ResponseEntity<SubmitAnswerResponse> {

        val result = interviewUseCase.submitAnswer(
            SubmitAnswerCommand(
                interviewId = interviewId,
                questionId = request.questionId,
                answer = request.answer
            )
        )

        return ResponseEntity.ok(
            SubmitAnswerResponse(
                interviewId = result.interviewId,
                questionId = result.questionId
            )
        )
    }

    @PostMapping("/{interviewId}/questions/new")
    suspend fun generateNewQuestion(
        @PathVariable interviewId: String
    ): ResponseEntity<InterviewQuestion> {

        val result = interviewUseCase.generateNewQuestion(
            GenerateNewQuestionCommand(
                interviewId = interviewId
            )
        )

        return ResponseEntity.ok(
            InterviewQuestion(
                questionId = result.questionId,
                content = result.content
            )
        )
    }

    @PostMapping("/{interviewId}/questions/follow-up")
    suspend fun generateFollowUpQuestion(
        @PathVariable interviewId: String,
        @RequestBody request: GenerateFollowUpRequest
    ): ResponseEntity<InterviewQuestion> {

        val result = interviewUseCase.generateFollowUpQuestion(
            GenerateFollowUpQuestionCommand(
                interviewId = interviewId,
                questionId = request.questionId,
            )
        )

        return ResponseEntity.ok(
            InterviewQuestion(
                questionId = result.questionId,
                content = result.content
            )
        )
    }
}