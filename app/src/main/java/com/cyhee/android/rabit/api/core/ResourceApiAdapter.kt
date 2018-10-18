package com.cyhee.android.rabit.api.core

import com.cyhee.android.rabit.api.core.interceptors.TokenInterceptor
import com.cyhee.android.rabit.api.resource.RabitUrl

/**
 * AuthApiAdapter Class
 */
object ResourceApiAdapter : BaseApiAdapter(RabitUrl.resourceUrl(), listOf(TokenInterceptor()))