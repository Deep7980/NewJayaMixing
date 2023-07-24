package com.jaya.app.core.helpers

import com.jaya.app.core.domain.model.ProdDetails
import com.jaya.app.core.domain.model.UserData

interface AppStore {

    suspend fun setBaseUrl(url:String,changeImmediate:String)
    suspend fun baseUrl():String?
    suspend fun storeBaseUrl(url: String)
    suspend fun login():Boolean
    suspend fun userId(): String

    suspend fun setUserToken(token: String?)
    suspend fun userToken(): String?

    suspend fun setUserId(id: String?)
    suspend fun logout()
    suspend fun isLoggedIn(): Boolean

    suspend fun setUserCredentials(userData: UserData)
    suspend fun credentials(): UserData?

    suspend fun dashboardProductDetails(): ProdDetails?

    suspend fun setDashboardProductDetails(prodDetails: ProdDetails)


    companion object {
        const val TOKEN = "user_token"
        const val USER = "user_id"
        const val BASE_URL = "base_url"
    }




}