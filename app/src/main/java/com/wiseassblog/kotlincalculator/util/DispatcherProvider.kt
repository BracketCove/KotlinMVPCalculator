package com.wiseassblog.kotlincalculator.util

import kotlinx.coroutines.experimental.Dispatchers
import kotlin.coroutines.experimental.CoroutineContext

object DispatcherProvider {
    fun provideUIContext(): CoroutineContext {
        return Dispatchers.Main
    }

    fun provideIOContext(): CoroutineContext {
        return Dispatchers.IO
    }
}