package com.gentle.interfit.interview.domain

class InterviewQuestion(
    val id: Long?,
    val interviewId: Long,
    val content: String,
    val sequence: Int,
    val isFollowUp: Boolean
)