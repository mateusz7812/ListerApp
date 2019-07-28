package com.example.lister.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val userId: Int,
    val displayName: String,
    val sessionKey: String
)
