package com.example.workmanagertest.sync

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.util.Log

/**
 * Servicio de autenticación dummy requerido por SyncAdapter
 */
class AuthenticatorService : Service() {
    private lateinit var authenticator: Authenticator

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "AuthenticatorService created")
        authenticator = Authenticator(this)
    }

    override fun onBind(intent: Intent?): IBinder {
        Log.d(TAG, "AuthenticatorService bound")
        return authenticator.iBinder
    }

    companion object {
        private const val TAG = "AuthenticatorService"
    }
}

/**
 * Implementación dummy del AbstractAccountAuthenticator
 */
class Authenticator(val context: Context) : AbstractAccountAuthenticator(context) {

    override fun editProperties(
        response: AccountAuthenticatorResponse,
        accountType: String
    ) {
        Log.d(TAG, "editProperties called")
        response.onError(android.accounts.AccountManager.KEY_ERROR_CODE, "Not supported")
    }

    override fun addAccount(
        response: AccountAuthenticatorResponse,
        accountType: String,
        authTokenType: String?,
        requiredFeatures: Array<String>?,
        options: Bundle?
    ) {
        Log.d(TAG, "addAccount called")
        response.onError(android.accounts.AccountManager.KEY_ERROR_CODE, "Not supported")
    }

    override fun getAuthToken(
        response: AccountAuthenticatorResponse,
        account: Account,
        authTokenType: String,
        options: Bundle?
    ) {
        Log.d(TAG, "getAuthToken called")
        response.onError(android.accounts.AccountManager.KEY_ERROR_CODE, "Not supported")
    }

    override fun getAuthTokenLabel(authTokenType: String): String {
        Log.d(TAG, "getAuthTokenLabel called")
        return "Token"
    }

    override fun updateCredentials(
        response: AccountAuthenticatorResponse,
        account: Account,
        authTokenType: String?,
        options: Bundle?
    ) {
        Log.d(TAG, "updateCredentials called")
        response.onError(android.accounts.AccountManager.KEY_ERROR_CODE, "Not supported")
    }

    override fun hasFeatures(
        response: AccountAuthenticatorResponse,
        account: Account,
        features: Array<String>
    ) {
        Log.d(TAG, "hasFeatures called")
        response.onResult(Bundle().apply {
            putBoolean(android.accounts.AccountManager.KEY_BOOLEAN_RESULT, false)
        })
    }

    override fun confirmCredentials(
        response: AccountAuthenticatorResponse,
        account: Account,
        options: Bundle?
    ) {
        Log.d(TAG, "confirmCredentials called")
        response.onError(android.accounts.AccountManager.KEY_ERROR_CODE, "Not supported")
    }

    companion object {
        private const val TAG = "Authenticator"
    }
}

