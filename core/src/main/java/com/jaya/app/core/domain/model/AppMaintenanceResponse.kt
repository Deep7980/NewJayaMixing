package com.jaya.app.core.domain.model

data class AppMaintenanceResponse(
    val status: Boolean,
    val message: String,
    val version: AppVersion,
    val domain: Domain
)
