package com.jaya.app.jayamixing.utility

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.jaya.app.core.domain.model.Domain
import com.jaya.app.core.utils.AppPreference
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppPreferenceImpl @Inject constructor(
    private val instance: DataStore<Preferences>
):AppPreference {
    override suspend fun setBaseUrl(url: String, changeImmediate: String) {
        if (changeImmediate == Domain.CHANGE) {
            instance.edit {
                it[stringPreferencesKey(AppPreference.BASE_URL)] = url
            }
        } else {
            instance.edit {
                it[stringPreferencesKey(AppPreference.BASE_URL)] = ""
            }
        }
    }

    override suspend fun baseUrl(): String? {
        val url = instance.data.map {
            it[stringPreferencesKey(AppPreference.BASE_URL)]
        }.first()

        return if (!url.isNullOrEmpty()) "https://$url" else null
    }
}