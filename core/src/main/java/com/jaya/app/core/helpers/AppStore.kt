package com.jaya.app.core.helpers

interface AppStore {

    suspend fun storeBaseUrl(url: String)
    suspend fun login(userId:String,)
    suspend fun userId(): String

    suspend fun setUserId(id: String?)
    suspend fun logout()
    suspend fun isLoggedIn(): Boolean


}