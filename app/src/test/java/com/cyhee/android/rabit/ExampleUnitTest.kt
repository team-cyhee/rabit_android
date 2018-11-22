package com.cyhee.android.rabit

import com.cyhee.android.rabit.api.core.AuthApiAdapter
import com.cyhee.android.rabit.api.service.AuthApi
import com.cyhee.android.rabit.model.User
import com.cyhee.android.rabit.activity.sign.register.validator.EmailValidator
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
        val restClient: AuthApi = AuthApiAdapter.retrofit(AuthApi::class.java)
        restClient.tokenByFacebook("Bearer EAAEONxzmYMEBAPdmgpAnAqDJ2YqOc8MQznaE8Nw02DgBuN2f9iTfSgXUPXOlC8Q8bIw9Bx4Xr3vnf7rP7rdlx1KPLNWCZC03jtMxvQ3mFEGuWAEt9Bm5CHOgiIdmfN7jusXZCslncW1iQROsTGZBg4HftZCtyPv8SP7FU7rW5HLKYZAZCt5HLC7pttOYlqBAUnFFnYwZALqYAZDZD")
                .subscribe(
                        {
                            print("hi")
                            print(it)
                        },
                        {
                            print("Bye")
                        }
                )

        Thread.sleep(1000)
    }

    @Test
    fun emailValidation() {
        assert(EmailValidator.valid("email@a.c"))
        assert(!(EmailValidator.valid("email@a")))
        assert(!(EmailValidator.valid("email")))
        assert(!(EmailValidator.valid("")))
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

}
