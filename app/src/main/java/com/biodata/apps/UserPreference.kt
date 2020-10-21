package com.biodata.apps

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class UserPreference(
    context: Context
) {
    private val applicationContext = context.applicationContext
    private val dataStore: DataStore<Preferences>
    private val gson = Gson()

    init {
        dataStore = applicationContext.createDataStore(
            name = "userpreferences"
        )
    }

    val user: Flow<User?>
        get() = dataStore.data.map { preferences ->
            gson.fromJson(preferences[KEY_USER], User::class.java)
        }

    suspend fun saveUser(user: User) {
        dataStore.edit { preferences ->
            preferences[KEY_USER] = gson.toJson(user)
        }
    }

    companion object {
        val KEY_USER = preferencesKey<String>("key_user")
    }
}