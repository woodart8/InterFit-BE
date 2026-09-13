package com.gentle.interfit.interview.adapter.out.ai

import com.gentle.interfit.interview.adapter.out.ai.dto.InterviewAnswerRequest
import com.gentle.interfit.interview.adapter.out.ai.dto.InterviewAnswerResponse
import com.gentle.interfit.interview.adapter.out.ai.dto.InterviewGenerateFollowUpRequest
import com.gentle.interfit.interview.adapter.out.ai.dto.InterviewQuestion
import com.gentle.interfit.interview.adapter.out.ai.dto.InterviewStartRequest
import com.gentle.interfit.interview.adapter.out.ai.dto.InterviewStartResponse
import com.gentle.interfit.interview.application.port.out.InterviewAiPort
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.web.reactive.function.client.bodyToMono

@Component
class FastApiInterviewAdapter(
    @Qualifier("fastApiWebClient")
    private val webClient: WebClient
) : InterviewAiPort {

    override suspend fun startInterview(
        resumeId: String
    ): InterviewStartResponse {

        return webClient.post()
            .uri("/api/interviews")
            .bodyValue(
                InterviewStartRequest(
                    resumeId = resumeId
                )
            )
            .retrieve()
            .bodyToMono<InterviewStartResponse>()
            .awaitSingle()
    }

    override suspend fun submitAnswer(
        interviewId: String,
        questionId: String,
        answer: String
    ): InterviewAnswerResponse {

        return webClient.post()
            .uri("/api/interviews/$interviewId/answer")
            .bodyValue(
                InterviewAnswerRequest(
                    questionId = questionId,
                    answer = answer
                )
            )
            .retrieve()
            .bodyToMono<InterviewAnswerResponse>()
            .awaitSingle()
    }

    override suspend fun generateNewQuestion(
        interviewId: String
    ): InterviewQuestion {

        return webClient.post()
            .uri("/api/interviews/$interviewId/questions/new")
            .retrieve()
            .bodyToMono<InterviewQuestion>()
            .awaitSingle()
    }

    override suspend fun generateFollowUpQuestion(
        interviewId: String,
        questionId: String
    ): InterviewQuestion {

        return webClient.post()
            .uri("/api/interviews/$interviewId/questions/follow-up")
            .bodyValue(
                InterviewGenerateFollowUpRequest(
                    questionId = questionId,
                )
            )
            .retrieve()
            .bodyToMono<InterviewQuestion>()
            .awaitSingle()
    }
}