package com.jaya.app.jayamixing.module

import com.jaya.app.core.domain.model.AppVersionModel
import com.jaya.app.core.domain.model.BaseUrlModel
import com.jaya.app.core.domain.model.GetOtpModel
import com.jaya.app.core.domain.model.VerifyOtpModel
import retrofit2.http.GET

interface MyApiList {

    @GET("f009b9789f522195cad1")
    suspend fun getBaseUrl(): BaseUrlModel

    @GET("860574053ecef85a029e")
    suspend fun getAppVersion(): AppVersionModel

    @GET("b5c867c3bf0cda68eda5")
    suspend fun getOtp(): GetOtpModel

    @GET("2e71ccced6e82f10cb90")
    suspend fun verifyOtp(): VerifyOtpModel
}