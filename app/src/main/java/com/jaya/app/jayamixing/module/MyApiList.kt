package com.jaya.app.jayamixing.module

import com.jaya.app.core.domain.model.AppVersionModel
import com.jaya.app.core.domain.model.BaseUrlModel
import com.jaya.app.core.domain.model.CuttingManTypesModel
import com.jaya.app.core.domain.model.FloorManagerType
import com.jaya.app.core.domain.model.FloorManagerTypesModel
import com.jaya.app.core.domain.model.GetOtpModel
import com.jaya.app.core.domain.model.MixingLabourModel
import com.jaya.app.core.domain.model.MixingManTypesModel
import com.jaya.app.core.domain.model.OvenManTypesModel
import com.jaya.app.core.domain.model.PackingSupervisorTypesModel
import com.jaya.app.core.domain.model.ProductSubmitModel
import com.jaya.app.core.domain.model.ProductTypesModel
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

    @GET("0abcee43260f8743a170")
    suspend fun getFloorTypes(): FloorManagerTypesModel

    @GET("61acf630747f10fb04da")
    suspend fun getProductTypes(): ProductTypesModel

    @GET("3cb6315d57ccd01072a5")
    suspend fun getMixingManTypes(): MixingManTypesModel

    @GET("89cd67d649006ac2d3b0")
    suspend fun getCuttingManTypes(): CuttingManTypesModel

    @GET("320565ebc83d2cad5811")
    suspend fun getOvenManTypes(): OvenManTypesModel

    @GET("e477edba338e1e5be843")
    suspend fun getPackingSupervisorTypes(): PackingSupervisorTypesModel

    @GET("ea14cfe83c330e6dd9ed")
    suspend fun getMixingLabourDetails(): MixingLabourModel
}