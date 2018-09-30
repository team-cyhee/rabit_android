package com.cyhee.android.rabit.api.response

/**
 * OAuth2 기반 token 인증시 받는 token
 */
data class TokenData (
    val accessToken: String,
    val tokenType: String,
    val refreshToken: String,
    val expiresIn: Int,
    val scope: String,
    val user: String,
    val jti: String
)