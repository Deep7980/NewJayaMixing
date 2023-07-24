package com.jaya.app.jayamixing.repository_impl

import android.util.Log
import com.jaya.app.core.common.Resource
import com.jaya.app.core.domain.model.AppMaintenanceResponse
import com.jaya.app.core.domain.model.AppVersionModel
import com.jaya.app.core.domain.model.BaseUrlModel
import com.jaya.app.core.domain.repositories.SplashRepository
import com.jaya.app.core.helpers.AppStore
import com.jaya.app.core.utils.AppPreference
import com.jaya.app.jayamixing.data.source.remote.AppMaintenanceApi
import com.jaya.app.jayamixing.module.MyApiList
import retrofit2.Retrofit
import javax.inject.Inject

class SplashRepositoryImpl @Inject constructor(
    private val myApiList: MyApiList,
    private val httpClient: Retrofit,
    private val apStore: AppStore
):SplashRepository {
    //    override suspend fun appVersion(currentVersion: Int): Resource<AppVersionModel> {
//        return try {
//            Resource.Success(myApiList.getAppVersion())
//        } catch (ex: Exception) {
//            Resource.Error(message = ex.message)
//        }
//    }
//
//    override suspend fun baseUrl(): Resource<BaseUrlModel> {
//        return try {
//            Resource.Success(myApiList.getBaseUrl())
//        } catch (ex: Exception) {
//            Resource.Error(message = ex.message)
//        }
//    }
    override suspend fun appMaintenance(currentAppVersion: Int): Resource<AppMaintenanceResponse> {
        return try {
            Resource.Loading<AppMaintenanceResponse>()
            val base = apStore.baseUrl()

            val url = if (base != null) {
                "$base/app/mixing/appMaintenanace"
            } else {
                "/app/mixing/appMaintenanace"
            }
            val result = httpClient.create(AppMaintenanceApi::class.java)
                .maintainApp(
                    url,
                    currentAppVersion
                )

            Resource.Success(result)
        } catch (ex: Exception) {
            Log.d("TESTING", "${ex.message}")
            Resource.Error(message = ex.message)
        } finally {
            Resource.Loading<AppMaintenanceResponse>(state = false)
        }
    }
}