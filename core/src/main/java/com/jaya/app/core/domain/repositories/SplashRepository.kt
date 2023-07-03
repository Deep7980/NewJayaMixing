package com.jaya.app.core.domain.repositories

import com.jaya.app.core.common.Resource
import com.jaya.app.core.domain.model.AppVersionModel
import com.jaya.app.core.domain.model.BaseUrlModel

interface SplashRepository {

    suspend fun appVersion(currentVersion: Int): Resource<AppVersionModel>

    suspend fun baseUrl(): Resource<BaseUrlModel>
}