package com.cyhee.android.rabit.model

import java.util.*

import kotlin.collections.ArrayList

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
    val startDate: Date,
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

data class FileInfo (
    val id: Long,
    val path: String,
    val size: Long,
    val name: String,
    val orgName: String,
    val extension: String
)

abstract class MainInfo {
    abstract var likeNum: Int
    abstract var commentNum: Int
    abstract var companionNum: Int
    abstract var lastUpdated: Date
    abstract var type: ContentType
    abstract var liked: Boolean
}

data class GoalInfo (
        var id: Long,
        var author: User,
        var parent: Goal?,
        var content: String,
        var startDate: Date,
        var endDate: Date?,
        var doUnit: GoalUnit?,
        var doTimes: Int?,
        var logNum: Int,
        var file: List<FileInfo> = ArrayList(),
        override var likeNum: Int,
        override var commentNum: Int,
        override var companionNum: Int,
        override var lastUpdated: Date,
        override var liked: Boolean,
        override var type: ContentType = ContentType.GOAL
) : MainInfo() {
    override fun toString(): String {
        return this.content
    }
}

data class GoalLogInfo (
    var id: Long,
    var author: User,
    var goal: Goal,
    var content: String,
    var createDate: Date,
    var file: List<FileInfo> = ArrayList(),
    override var likeNum: Int,
    override var commentNum: Int,
    override var companionNum: Int,
    override var lastUpdated: Date,
    override var liked: Boolean,
    override var type: ContentType = ContentType.GOALLOG
) : MainInfo()

data class WallInfo (
    var id: Long,
    var username: String,
    var followerNum: Int,
    var followeeNum: Int,
    var goalContents: List<String> = ArrayList()
)

data class SearchForm (
    var id: Long,
    var type: ContentType,
    var user: User,
    var content: String?
)

data class Notice (
    var id: Long,
    var title: String,
    var content: String
)