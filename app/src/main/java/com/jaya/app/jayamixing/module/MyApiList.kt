package com.jaya.app.jayamixing.module

import com.jaya.app.core.domain.model.AppVersionModel
import com.jaya.app.core.domain.model.BaseUrlModel
import com.jaya.app.core.domain.model.GetOtpModel
import com.jaya.app.core.domain.model.ProductSubmitModel
import com.jaya.app.core.domain.model.UserDetailsModel
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

    @GET("967453da83f0011fc01a")
    suspend fun getUserDetails(): UserDetailsModel

    @GET("5c86e92cf796c031bcb6")
    suspend fun getProductDetails(): ProductSubmitModel
}