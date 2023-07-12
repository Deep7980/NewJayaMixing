package com.jaya.app.core.utils

interface AppPreference {

    suspend fun setBaseUrl(url:String,changeImmediate:String)
    suspend fun baseUrl():String?


    companion object {
        const val TOKEN = "user_token"
        const val USER = "user_id"
        const val BASE_URL = "base_url"
    }

}