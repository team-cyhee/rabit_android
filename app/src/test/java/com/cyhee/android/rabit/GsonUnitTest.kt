package com.cyhee.android.rabit

import com.cyhee.android.rabit.api.core.RabitGson
import com.cyhee.android.rabit.model.*
import org.junit.Test
import java.util.*

class GsonUnitTest {
    @Test
    fun mainInfo() {
        val gson = RabitGson.gson()

        gson.fromJson("apod", String::class.java)

        print(gson.fromJson("apod", String::class.java))
        /*val gson = RabitGson.gson()

        val user = User(1, "username", "email", "name",
                "01012341234", Date(), UserStatus.ACTIVE, Date(), Date())
        val goal = Goal(1, user, null, "content", Date(), Date(),
                ContentStatus.ACTIVE, GoalCycle.DAILY, Date(), Date())
        val comment = Comment(1, ContentType.GOAL, 1, user, "content",
                ContentStatus.ACTIVE, Date(), Date())
        val comments = Page(mutableListOf(comment))
        val goalInfo = GoalInfo(1, user, goal, "content", Date(), Date(),
                GoalCycle.DAILY, 1, 1, 1, comments, Date())

        val logInfo = GoalLogInfo(1, user, goal, "content", Date(),
                1, 1, 1, comments, Date())

        val goalJson = gson.toJson(goalInfo, GoalInfo::class.java)
        val logJson = gson.toJson(logInfo, GoalLogInfo::class.java)

        println(goalJson)
        println(logJson)

        val goalFromJson = gson.fromJson(goalJson, MainInfo::class.java)
        val logFromJson = gson.fromJson(logJson, MainInfo::class.java)

        assert(goalFromJson is GoalInfo)
        assert(logFromJson is GoalLogInfo)

        print(gson.fromJson("{\"id\":14,\"create_date\":1539095067618,\"last_updated\":1539095067618,\"author\":{\"id\":1,\"create_date\":1538235339764,\"last_updated\":1538235339764,\"email\":\"test@rabit\",\"username\":\"test1\",\"name\":null,\"phone\":null,\"birth\":null,\"status\":\"PENDING\",\"photo\":[]},\"parent\":null,\"content\":\"A\",\"start_date\":1522540800000,\"end_date\":null,\"status\":\"ACTIVE\",\"selected_days\":null,\"files\":[]}",Goal::class.java))
        print(gson.fromJson("{\"type\":\"GOAL\",\"like_num\":0,\"comment_num\":0,\"companion_num\":0,\"comments\":{\"content\":[],\"pageable\":{\"sort\":{\"sorted\":false,\"unsorted\":true},\"offset\":0,\"page_size\":2,\"page_number\":0,\"paged\":true,\"unpaged\":false},\"last\":true,\"total_elements\":0,\"total_pages\":0,\"number\":0,\"size\":2,\"sort\":{\"sorted\":false,\"unsorted\":true},\"number_of_elements\":0,\"first\":true},\"last_updated\":1539095072167,\"id\":15,\"author\":{\"id\":1,\"create_date\":1538235339764,\"last_updated\":1538235339764,\"email\":\"test@rabit\",\"username\":\"test1\",\"name\":null,\"phone\":null,\"birth\":null,\"status\":\"PENDING\",\"photo\":[]},\"parent\":null,\"content\":\"B\",\"start_date\":1522540800000,\"end_date\":null,\"selected_days\":null,\"files\":[]}",MainInfo::class.java))*/
    }
}