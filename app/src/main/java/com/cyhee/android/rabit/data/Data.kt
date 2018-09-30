package com.cyhee.android.rabit.data

import java.util.*

data class User (
    val username: String,
    val password: String,
    val email: String,
    val phone: String,
    val birth: Date
)