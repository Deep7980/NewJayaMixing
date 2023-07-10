package com.jaya.app.core.domain.model

data class GetLoginModel(
    val isUser: Boolean,
    val message: String,
    val userId: String,
    val status: Boolean
)
