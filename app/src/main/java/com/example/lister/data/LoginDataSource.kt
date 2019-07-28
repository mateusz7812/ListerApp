package com.example.lister.data

import android.util.Log
import com.example.lister.data.model.LoggedInUser
import com.example.lister.data.model.ServerResponse
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            Log.d("output4", "entered")
            val loggedUser = getUserFromDB(username, password) ?: throw Exception("login failed")
            Log.d("output4", loggedUser.toString())
            return Result.Success(loggedUser)
        } catch (e: Throwable) {
            Log.e("output5", e.toString())
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }

    fun getUserFromDB(username: String, password: String): LoggedInUser?{
        val requestFactory = ServerRequestFactory()
        val accountResponse = getAccount(requestFactory, username, password)
        if (accountResponse.status != "handled") return null
        val accountMap = accountResponse.objects?.get(0) ?: return null
        requestFactory.account = mutableMapOf("type" to "account", "login" to username, "password" to password)
        createSession(requestFactory, accountMap["id"]!! as String)
        val sessionResponse = getSession(requestFactory, accountMap["id"]!! as String)
        if (sessionResponse.status != "handled" || sessionResponse.objects == null)
            return null
        return LoggedInUser(
                accountMap["id"] as Int,
                accountMap["nick"] as String,
                sessionResponse.objects[0]["key"] as String
            )
    }

    private fun getSession(
        requestFactory: ServerRequestFactory,
        userId: String
    ): ServerResponse {
        return requestFactory.generateRequest(
            `object` = mutableMapOf("type" to "session", "user_id" to userId),
            action = "get"
        ).send()
    }

    private fun createSession(
        requestFactory: ServerRequestFactory,
        userId: String
    ): ServerResponse {
        return requestFactory.generateRequest(
            `object` = mutableMapOf("type" to "session", "user_id" to userId),
            action = "add"
        ).send()
    }

    private fun getAccount(
        requestFactory: ServerRequestFactory,
        username: String,
        password: String
    ): ServerResponse {
        return requestFactory.generateRequest(
            `object` = mutableMapOf("type" to "account", "login" to username, "password" to password),
            action = "get"
        ).send()
    }
}

