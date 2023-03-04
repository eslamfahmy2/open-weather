package com.testapp.utils

import com.testapp.AppApplication

object ApplicationContextSingleton {
    private lateinit var appApplicationContext: AppApplication

    fun initialize(application: AppApplication) {
        appApplicationContext = application
    }

    fun getString(stringId: Int): String {
        return appApplicationContext.getString(stringId)
    }

    fun getString(stringId: Int, stringArgument: String): String {
        return appApplicationContext.getString(stringId, stringArgument)
    }
}