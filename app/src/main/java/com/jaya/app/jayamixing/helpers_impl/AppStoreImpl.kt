package com.jaya.app.jayamixing.helpers_impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import com.jaya.app.core.common.PrefConstants
import com.jaya.app.core.domain.model.Domain
import com.jaya.app.core.domain.model.ProdDetails
import com.jaya.app.core.domain.model.UserData
import com.jaya.app.core.helpers.AppStore
import com.jaya.app.core.utils.AppPreference
import com.jaya.app.jayamixing.extensions.decodeJson
import com.jaya.app.jayamixing.extensions.encodeJson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class AppStoreImpl @Inject constructor(
    private val prefs: DataStore<Preferences>
):AppStore {
    override suspend fun storeBaseUrl(url: String) {
        prefs.edit {
            it[stringPreferencesKey(PrefConstants.BASE_URL)] = url
        }
    }

    override suspend fun setUserToken(token: String?) {
        if (token != null) {
            prefs.edit {
                it[stringPreferencesKey(AppPreference.TOKEN)] = token
            }
        } else {
            prefs.edit {
                it.remove(stringPreferencesKey(AppPreference.TOKEN))
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun userToken(): String? = prefs.data.mapLatest {
        it[stringPreferencesKey(AppPreference.TOKEN)]
    }.first()

    override suspend fun login(): Boolean = userToken() != null && userId().isNotEmpty()

    override suspend fun userId(): String {
        return prefs.data.map {
            it[stringPreferencesKey(PrefConstants.USER_ID)]
        }.first() ?: ""
    }

    override suspend fun logout() {
        prefs.edit {
            if (it.contains(stringPreferencesKey(PrefConstants.USER_ID))) {
                it.remove(stringPreferencesKey(PrefConstants.USER_ID))
            }
        }
    }

    override suspend fun isLoggedIn(): Boolean {
        val userID = prefs.data.map {
            it[stringPreferencesKey(PrefConstants.USER_ID)]
        }.first()

        if (!userID.isNullOrEmpty()) return true

        return false
    }

    override suspend fun setUserCredentials(userData: UserData) {
        prefs.edit {
            it[stringPreferencesKey(PrefConstants.USER_CREDS)] = userData.encodeJson()
        }
    }

    override suspend fun credentials(): UserData? {
        val data = prefs.data.mapLatest {
            it[stringPreferencesKey(PrefConstants.USER_CREDS)]
        }.first()

        if (data != null) {
            return data.decodeJson()
        }
        return null
    }

    override suspend fun dashboardProductDetails(): ProdDetails? {
        val data = prefs.data.mapLatest {
            it[stringPreferencesKey(PrefConstants.PROD_DETAILS)]
        }.first()

        if (data != null) {
            return data.decodeJson()
        }
        return null
    }

    override suspend fun setDashboardProductDetails(prodDetails: ProdDetails) {
        prefs.edit {
            it[stringPreferencesKey(PrefConstants.PROD_DETAILS)] = prodDetails.encodeJson()
        }
    }

    override suspend fun setUserId(id: String?) {
        if (id != null) {
            prefs.edit {
                it[stringPreferencesKey(PrefConstants.USER_ID)] = id
            }
        } else {
            prefs.edit {
                it.remove(stringPreferencesKey(PrefConstants.USER_ID))
            }
        }
    }

    override suspend fun setBaseUrl(url: String, changeImmediate: String) {
        if (changeImmediate == Domain.CHANGE) {
            prefs.edit {
                it[stringPreferencesKey(AppPreference.BASE_URL)] = url
            }
        } else {
            prefs.edit {
                it[stringPreferencesKey(AppPreference.BASE_URL)] = ""
            }
        }
    }

    override suspend fun baseUrl(): String? {
        val url = prefs.data.map {
            it[stringPreferencesKey(AppPreference.BASE_URL)]
        }.first()

        return if (!url.isNullOrEmpty()) "https://$url" else null
    }




}