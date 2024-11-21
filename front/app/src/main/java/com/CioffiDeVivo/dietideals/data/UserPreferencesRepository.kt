package com.CioffiDeVivo.dietideals.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    private companion object{
        val USER_ID = stringPreferencesKey("user_id")
        val TOKEN = stringPreferencesKey("token")
        val NAME = stringPreferencesKey("name")
        val EMAIL = stringPreferencesKey("email")
        val IS_SELLER = booleanPreferencesKey("is_seller")
        const val TAG = "UserPreferencesRepo"
    }

    suspend fun saveUserId(userId: String){
        dataStore.edit { preferences ->
            preferences[USER_ID] = userId
        }
    }

    suspend fun saveToken(token: String){
        dataStore.edit { preferences ->
            preferences[TOKEN] = token
        }
    }

    suspend fun saveName(name: String){
        dataStore.edit { preferences ->
            preferences[NAME] = name
        }
    }

    suspend fun saveEmail(email: String){
        dataStore.edit { preferences ->
            preferences[EMAIL] = email
        }
    }

    suspend fun saveIsSeller(isSeller: Boolean){
        dataStore.edit { preferences ->
            preferences[IS_SELLER] = isSeller
        }
    }

    val userId: Flow<String> = dataStore.data
        .catch {
            if(it is IOException){
                Log.e(TAG, "Error Reading Preferences")
                emit(emptyPreferences())
            } else{
                throw it
            }
        }
        .map { preferences ->
            preferences[USER_ID] ?: ""
        }

    val token: Flow<String> = dataStore.data
        .catch {
            if(it is IOException){
                Log.e(TAG, "Error Reading Preferences")
                emit(emptyPreferences())
            } else{
                throw it
            }
        }
        .map { preferences ->
            preferences[TOKEN] ?: ""
        }

    val name: Flow<String> = dataStore.data
        .catch {
            if(it is IOException){
                Log.e(TAG, "Error Reading Preferences")
                emit(emptyPreferences())
            } else{
                throw it
            }
        }
        .map { preferences ->
            preferences[NAME] ?: ""
        }

    val email: Flow<String> = dataStore.data
        .catch {
            if(it is IOException){
                Log.e(TAG, "Error Reading Preferences")
                emit(emptyPreferences())
            } else{
                throw it
            }
        }
        .map { preferences ->
            preferences[EMAIL] ?: ""
        }

    val isSeller: Flow<Boolean> = dataStore.data
        .catch {
            if(it is IOException){
                Log.e(TAG, "Error Reading Preferences")
                emit(emptyPreferences())
            } else{
                throw it
            }
        }
        .map { preferences ->
            preferences[IS_SELLER] ?: false
        }
}