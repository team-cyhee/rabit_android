package com.cyhee.android.rabit.model

import java.util.*
import android.telephony.mbms.FileInfo



data class User (
    val id: Long,
    val username: String,
    val email: String,
    val name: String?,
    val phone: String?,
    val birth: Date?,
    val status: UserStatus,
    val createDate: Date,
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
    val content: String,
    val startDate: Date?,
    val endDate: Date?,
    val status: ContentStatus,
    val doUnit: GoalUnit?,
    val doTimes: Int?,
    val createDate: Date,
    val lastUpdated: Date
) {
    override fun toString(): String {
        return this.content
    }
}

enum class GoalUnit {
    DAILY, WEEKLY, MONTHLY, YEARLY
}

data class GoalCycle (
    val unit: GoalUnit?,
    val times: Int?
)

data class GoalLog (
    val id: Long,
    val goal: Goal,
    val content: String?,
    val status: ContentStatus,
    val createDate: Date,
    val lastUpdated: Date
)

data class Comment (
    val id: Long,
    val contentType: ContentType,
    val parentId: Long,
    val author: User,
    val content: String?,
    val status: ContentStatus,
    val createDate: Date,
    val lastUpdated: Date
)

data class Like (
    val author: User,
    val type: ContentType,
    val parentId: Long,
    val status: RadioStatus
)

abstract class MainInfo {
    abstract var likeNum: Int
    abstract var commentNum: Int
    abstract var companionNum: Int
    abstract var comments: Page<Comment>
    abstract var lastUpdated: Date
    abstract var type: ContentType
}

data class GoalInfo (
    var id: Long,
    var author: User,
    var parent: Goal?,
    var content: String,
    var startDate: Date?,
    var endDate: Date?,
    var selectedDays: GoalCycle,
    var logNum: Int,
    override var likeNum: Int,
    override var commentNum: Int,
    override var companionNum: Int,
    override var comments: Page<Comment>,
    override var lastUpdated: Date,
    override var type: ContentType = ContentType.GOAL
) : MainInfo()

data class GoalLogInfo (
    var id: Long,
    var author: User,
    var goal: Goal,
    var content: String,
    var createDate: Date,
    override var likeNum: Int,
    override var commentNum: Int,
    override var companionNum: Int,
    override var comments: Page<Comment>,
    override var lastUpdated: Date,
    override var type: ContentType = ContentType.GOALLOG
) : MainInfo()

data class WallInfo (
    var id: Long,
    var username: String,
    var followerNum: Int,
    var followeeNum: Int,
    var goalContents: List<String>
)