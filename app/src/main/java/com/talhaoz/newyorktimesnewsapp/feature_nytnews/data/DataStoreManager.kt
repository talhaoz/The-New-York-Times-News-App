package com.talhaoz.newyorktimesnewsapp.feature_nytnews.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class DataStoreManager(private val context: Context) {

    suspend fun writeToPreferences(isFirstLaunch: Boolean) {
        context.userDataStore.edit { preferences ->
            preferences[IS_FIRST_LAUNCH] = isFirstLaunch
        }
    }

    val readFromPreferences : Flow<Boolean>
        get() = context.userDataStore.data.map { preferences ->
            preferences[IS_FIRST_LAUNCH] ?: true
        }

    companion object {
        private const val DATASTORE_NAME = "nyt_preferences"
        private val IS_FIRST_LAUNCH = booleanPreferencesKey("isFirstLaunch")

        private val Context.userDataStore by preferencesDataStore(name = DATASTORE_NAME)
    }

}