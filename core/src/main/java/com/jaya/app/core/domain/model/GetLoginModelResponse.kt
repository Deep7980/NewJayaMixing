package com.jaya.app.core.domain.model

data class GetLoginModel(
    val userCreds: UserData,
    val isUser: Boolean,
    val message: String,
    val userId: String,
    val status: Boolean
)
