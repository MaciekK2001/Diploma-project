package com.example.myapplication.security

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.myapplication.network.ApiClient

class UserData(context: Context){
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "SecurePrefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    companion object{
        @Volatile
        private var INSTANCE: UserData? = null

        fun getInstance(context: Context): UserData =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserData(context.applicationContext).also { INSTANCE = it }
            }
    }

    fun saveUserId(userId: String) {
        with(sharedPreferences.edit()) {
            putString("userId", userId)
            apply()
        }
    }

    fun getUserId(): String? {
        return sharedPreferences.getString("userId", null)
    }

    fun saveUsername(username: String) {
        with(sharedPreferences.edit()) {
            putString("username", username)
            apply()
        }
    }

    fun getUsername(): String? {
        return sharedPreferences.getString("username", null)
    }

    fun clearUserData() {
        with(sharedPreferences.edit()) {
            remove("userId")
            remove("username")
            apply()
        }
    }

    suspend fun getData(){
        val userData = ApiClient.api.getUserBasicData()
        if (userData != null) {
            saveUsername(userData.username)
            saveUserId(userData.userId)
        }
    }

}

object UserDataHolder{
    lateinit var userData: UserData

    fun initialize(userData: UserData) {
        this.userData = userData
    }

    fun retrieveUserDataHolder(): UserData {
        if (!::userData.isInitialized) {
            throw IllegalStateException("UserData is not initialized")
        }
        return userData
    }
}