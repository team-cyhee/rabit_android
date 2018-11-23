package com.cyhee.android.rabit.model

import java.util.*

object UserFactory {
    data class Post(
        val username: String,
        val name: String,
        val email: String,
        val password: String,
        val phone: String?,
        val birth: Date?
    )
}

object GoalFactory {
    data class Post(
        val content: String,
        val startDate: Date,
        val endDate: Date?,
        val doUnit: GoalUnit?,
        val doTimes: Int?
    )
}

object GoalLogFactory {
    data class Post(
        val content: String
    ) {
        var fileId: Long? = null
    }
}

object CommentFactory {
    data class Post(
        val content: String
    )
}

object QuestionFactory {
    data class Post(
        val content: String
    )
}