package com.jaya.app.core.helpers

import com.jaya.app.core.domain.model.ProdDetails
import com.jaya.app.core.domain.model.UserData

interface AppStore {

    suspend fun storeBaseUrl(url: String)
    suspend fun login(userId:String,)
    suspend fun userId(): String

    suspend fun setUserId(id: String?)
    suspend fun logout()
    suspend fun isLoggedIn(): Boolean

    suspend fun setUserCredentials(userData: UserData)
    suspend fun credentials(): UserData?

    suspend fun dashboardProductDetails(): ProdDetails?

    suspend fun setDashboardProductDetails(prodDetails: ProdDetails)




}