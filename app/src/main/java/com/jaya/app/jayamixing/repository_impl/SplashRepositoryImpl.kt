package com.jaya.app.jayamixing.repository_impl

import com.jaya.app.core.common.Resource
import com.jaya.app.core.domain.model.AppVersionModel
import com.jaya.app.core.domain.model.BaseUrlModel
import com.jaya.app.core.domain.repositories.SplashRepository
import com.jaya.app.jayamixing.module.MyApiList
import javax.inject.Inject

class SplashRepositoryImpl @Inject constructor(
    private val myApiList: MyApiList
):SplashRepository {
    override suspend fun appVersion(currentVersion: Int): Resource<AppVersionModel> {
        return try {
            Resource.Success(myApiList.getAppVersion())
        } catch (ex: Exception) {
            Resource.Error(message = ex.message)
        }
    }

    override suspend fun baseUrl(): Resource<BaseUrlModel> {
        return try {
            Resource.Success(myApiList.getBaseUrl())
        } catch (ex: Exception) {
            Resource.Error(message = ex.message)
        }
    }
}