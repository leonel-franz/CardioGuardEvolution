package com.cardioguard.evolution.common.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "cardioguard_prefs")

@Singleton
class DataStoreManager @Inject constructor(private val context: Context) {

    companion object {
        val KEY_TOKEN = stringPreferencesKey("auth_token")
        val KEY_USER = stringPreferencesKey("auth_user")
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_TOKEN] = token
        }
    }

    suspend fun saveUser(user: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_USER] = user
        }
    }

    val authToken: Flow<String?>
        get() = context.dataStore.data.map { it[KEY_TOKEN] }

    val authUser: Flow<String?>
        get() = context.dataStore.data.map { it[KEY_USER] }

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}
