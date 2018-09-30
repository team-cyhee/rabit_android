package com.cyhee.android.rabit

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.cyhee.android.rabit.api.core.ResourceApiAdapter
import com.cyhee.android.rabit.api.service.ResourceApi
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    lateinit var disposable : Disposable
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.cyhee.android.rabit", appContext.packageName)
    }

    @Test
    fun resourceApiTest() {
        val restClient: ResourceApi = ResourceApiAdapter.retrofit(ResourceApi::class.java)

        disposable = restClient.users()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    users -> print(users)
                }
    }

    @After
    fun afterTest() {
        if (::disposable.isInitialized)
            disposable.dispose()
    }
}
