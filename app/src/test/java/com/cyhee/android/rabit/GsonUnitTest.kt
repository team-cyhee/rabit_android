package com.cyhee.android.rabit

import com.cyhee.android.rabit.api.core.RabitGson
import com.cyhee.android.rabit.model.*
import org.junit.Test
import java.util.*

class GsonUnitTest {
    @Test
    fun mainInfo() {
        val gson = RabitGson.gson()

        val user = User(1, "username", "email", "name",
                "01012341234", Date(), UserStatus.ACTIVE, Date(), Date())
        val goal = Goal(1, user, null, "content", Date(), Date(),
                ContentStatus.ACTIVE, GoalCycle.DAILY, Date(), Date())
        val comment = Comment(1, ContentType.GOAL, 1, user, "content",
                ContentStatus.ACTIVE, Date(), Date())
        val comments = Page(mutableListOf(comment))
        val goalInfo = GoalInfo(1, user, goal, "content", Date(), Date(),
                GoalCycle.DAILY, 1, 1, comments, Date())

        val logInfo = GoalLogInfo(1, user, goal, "content", 1,
                1, comments, Date())

        val goalJson = gson.toJson(goalInfo, GoalInfo::class.java)
        val logJson = gson.toJson(logInfo, GoalLogInfo::class.java)

        println(goalJson)
        println(logJson)

        val goalFromJson = gson.fromJson(goalJson, MainInfo::class.java)
        val logFromJson = gson.fromJson(logJson, MainInfo::class.java)

        println(goalFromJson)
        println(logFromJson)
    }
}