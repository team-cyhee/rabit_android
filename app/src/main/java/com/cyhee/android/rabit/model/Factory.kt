package com.cyhee.android.rabit.model

import java.util.*

object UserFactory {
    data class Post(
        val username: String,
        val email: String,
        val password: String,
        val phone: String?,
        val birth: Date?
    )
}