package com.cyhee.android.rabit

import com.cyhee.android.rabit.api.core.AuthApiAdapter
import com.cyhee.android.rabit.api.core.ResourceApiAdapter
import com.cyhee.android.rabit.api.service.AuthApi
import com.cyhee.android.rabit.api.service.ResourceApi
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun loginTest() {
        val restClient: AuthApi = AuthApiAdapter.retrofit(AuthApi::class.java)
        val tokenRequest = restClient.token("user1", "user1")
        val tokenResponse = tokenRequest.execute()
        assert(tokenResponse.isSuccessful)
        print(tokenResponse.body())
    }

    @Test
    fun resourceApiTest() {
        val restClient: ResourceApi = ResourceApiAdapter.retrofit(ResourceApi::class.java)
        val request = restClient.users()
        val response = request.execute()
        print(response.body())
    }
}
