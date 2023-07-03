package com.jaya.app.core.domain.model

data class AppVersionModel(
    val appVersion: AppVersion,
    val message: String,
    val status: Boolean
)

data class AppVersion(
    val isSkipable: Boolean,
    val versionLink: String,
    val releaseDate: String,
    val versionCode: Int,
    val versionMessage: String,
    val versionTitle: String
)
