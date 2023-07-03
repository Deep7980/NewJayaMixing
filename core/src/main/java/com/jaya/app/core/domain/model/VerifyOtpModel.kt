package com.jaya.app.core.domain.model

data class VerifyOtpModel(
    val isMatched: Boolean,
    val message: String,
    val status: Boolean,
    val userId: String
)
