package com.example.myapplication.jwt
import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class TokenManager private constructor(context: Context) {

    private val sharedPreferences by lazy {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            context,
            "secure_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun saveJwtToken(token: String) {
        with(sharedPreferences.edit()) {
            putString("jwt_token", token)
            apply()
        }
    }

    fun getJwtToken(): String? {
        return sharedPreferences.getString("jwt_token", null)
    }

    fun deleteJwtToken() {
        with(sharedPreferences.edit()) {
            remove("jwt_token")
            apply()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: TokenManager? = null

        fun getInstance(context: Context): TokenManager =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: TokenManager(context.applicationContext).also { INSTANCE = it }
            }
    }
}

object TokenManagerHolder {
    lateinit var tokenManager: TokenManager
        private set

    fun initialize(tokenManager: TokenManager) {
        this.tokenManager = tokenManager
    }

    fun retriveTokenManager(): TokenManager {
        if (!::tokenManager.isInitialized) {
            throw IllegalStateException("TokenManager is not initialized")
        }
        return tokenManager
    }
}
