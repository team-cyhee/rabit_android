package com.cyhee.android.rabit

import com.cyhee.android.rabit.api.core.AuthApiAdapter
import com.cyhee.android.rabit.api.core.ResourceApiAdapter
import com.cyhee.android.rabit.api.service.AuthApi
import com.cyhee.android.rabit.api.service.ResourceApi
import com.cyhee.android.rabit.data.User
import com.cyhee.android.rabit.sign.register.validator.EmailValidator
import com.cyhee.android.rabit.sign.register.validator.PasswordValidator
import org.junit.Test
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun loginTest() {
       /* val restClient: AuthApi = AuthApiAdapter.retrofit(AuthApi::class.java)
        val tokenRequest = restClient.token("user1", "user1")
        val tokenResponse = tokenRequest.execute()
        assert(tokenResponse.isSuccessful)
        print(tokenResponse.body())*/
    }

    @Test
    fun emailValidation() {
        assert(EmailValidator.valid("email@a.c"))
        assert(!(EmailValidator.valid("email@a")))
        assert(!(EmailValidator.valid("email")))
    }

    @Test
    fun test() {
        print("a@asdc".contains("[@#$]".toRegex()))
        print("abc".contains("^(?=.*[A-Z])(?=.*[a-z])".toRegex()))
        print("abcD".matches("^(?=.*[A-Z])(?=.*[a-z]).+".toRegex()))
        print("abc".matches("^(?=.*[A-Z])(?=.*[a-z])[A-Za-z\\d@\$!%*#?&]+".toRegex()))
        print("abc2".matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@\$!%*#?&]+".toRegex()))
    }

    @Test
    fun aa() {
        val user: User? = null
        var username: String? = "aa"
        username = user?.username
        print(username)
        print("Good!")
    }

    @Test
    fun nestedApi() {
        val restClient: AuthApi = AuthApiAdapter.retrofit(AuthApi::class.java)
        val user = User("abc23","aaaa1234","a@a.c","", Date())
        restClient.exists(user.username)
                .onErrorResumeNext {
                    print(it.toString())
                    restClient.register(user)
                }
                .subscribe{
                    print("hi")
                }
        Thread.sleep(5000)
    }
}
