package com.cardioguard.evolution.common

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("user_prefs")

object UserPreferences {
    private val TERMS_ACCEPTED = booleanPreferencesKey("terms_accepted")

    fun isTermsAccepted(context: Context): Flow<Boolean> =
        context.dataStore.data.map { prefs ->
            prefs[TERMS_ACCEPTED] ?: false
        }

    suspend fun setTermsAccepted(context: Context, accepted: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[TERMS_ACCEPTED] = accepted
        }
    }
}
