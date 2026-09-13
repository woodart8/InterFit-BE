package com.gentle.interfit.interview.adapter.`in`.web

import com.gentle.interfit.interview.adapter.`in`.web.dto.StartInterviewRequest
import com.gentle.interfit.interview.adapter.`in`.web.dto.StartInterviewResponse
import com.gentle.interfit.interview.adapter.`in`.web.dto.SubmitAnswerRequest
import com.gentle.interfit.interview.adapter.`in`.web.dto.SubmitAnswerResponse
import com.gentle.interfit.interview.application.port.`in`.InterviewUseCase
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
                question = StartInterviewResponse.Question(
                    id = result.questionId,
                    content = result.questionContent
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
                questionId = result.questionId,
                answer = result.answer,
                nextQuestion = SubmitAnswerResponse.Question(
                    id = result.nextQuestionId,
                    content = result.nextQuestionContent
                )
            )
        )
    }
}