package com.cyhee.android.rabit.model

import java.util.*
import android.telephony.mbms.FileInfo



data class User (
    val id: Long,
    val username: String?,
    val email: String,
    val name: String?,
    val phone: String?,
    val brith: Date?,
    val status: UserStatus,
    val createdDate: Date,
    val lastUpdated: Date
)

enum class UserStatus {
    // 활성
    ACTIVE,
    // 비활성
    INACTIVE,
    // 금지
    FORBIDDEN,
    // 삭제됨
    DELETED,
    // 가입 보류
    PENDING
}

data class Goal (
    val id: Long,
    val author: User,
    val parent: Goal?,
    val content: String?,
    val startDate: Date?,
    val endDate: Date?,
    val status: ContentStatus,
    val selectedDays: GoalCycle,
    val createdDate: Date,
    val lastUpdated: Date
)

enum class GoalCycle {
    // every day
    DAILY,
    // per week
    W_ONCE,
    W_TWICE, W_THREE, W_FOUR, W_FIVE, W_SIX,
    // day of week
    W_MON,
    W_TUE, W_WED, W_THUR, W_FRI, W_SAT, W_SUN,
    // per month
    M_ONCE,
    M_TWICE, M_THREE, M_FOUR, M_FIVE, M_SIX,
    // day of month
    M_1ST,
    M_2ND, M_3RD, M_4TH, M_5TH, M_6TH, M_7TH, M_8TH, M_9TH, M_10TH, M_11TH,
    M_12TH, M_13TH, M_14TH, M_15TH, M_16TH, M_17TH, M_18TH, M_19TH, M_20TH, M_21TH,
    M_22TH, M_23TH, M_24TH, M_25TH, M_26TH, M_27TH, M_28TH, M_29TH, M_30TH, M_31TH,
    // per year
    Y_ONCE,
    Y_TWICE, Y_THREE, Y_FOUR, Y_FIVE, Y_SIX, Y_SEVEN, Y_EIGHT, Y_NINE, Y_TEN,
    // month of year
    Y_JAN,
    Y_FEB, Y_MAR, Y_APR, Y_MAY, Y_JUNE, Y_JULY, Y_AUG, Y_SEP, Y_OCT, Y_NOV, Y_DEC
}

data class GoalLog (
    val id: Long,
    val goal: Goal,
    val content: String?,
    val status: ContentStatus,
    val createdDate: Date,
    val lastUpdated: Date
)

data class Comment (
    val id: Long,
    val contentType: ContentType,
    val parentId: Long,
    val author: User,
    val content: String?,
    val status: ContentStatus,
    val createdDate: Date,
    val lastUpdated: Date
)

data class Like (
    val author: User,
    val type: ContentType,
    val parentId: Long,
    val status: RadioStatus
)