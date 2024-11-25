package com.CioffiDeVivo.dietideals

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.CioffiDeVivo.dietideals.data.AppContainer
import com.CioffiDeVivo.dietideals.data.DefaultAppContainer
import com.CioffiDeVivo.dietideals.data.UserPreferencesRepository
import com.CioffiDeVivo.dietideals.utils.AppViewModelFactory

private const val USER_ID_PREFERENCE_NAME = "user_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = USER_ID_PREFERENCE_NAME
)

class DietiDealsApplication: Application() {
    lateinit var appContainer: AppContainer
    lateinit var userPreferencesRepository: UserPreferencesRepository
    lateinit var appViewModelFactory: AppViewModelFactory
    override fun onCreate() {
        super.onCreate()
        userPreferencesRepository = UserPreferencesRepository(dataStore)
        appContainer = DefaultAppContainer(userPreferencesRepository)
        appViewModelFactory = AppViewModelFactory(userPreferencesRepository, appContainer)
    }
}