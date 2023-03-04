package com.testapp.data.networking.interceptor

import android.content.Context
import com.testapp.R
import java.io.IOException

class NoConnectivityException(val context: Context) : IOException() {
    override val message: String
        get() = context.getString(R.string.no_internet_connection_error)
}