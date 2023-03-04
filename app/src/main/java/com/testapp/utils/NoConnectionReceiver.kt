package com.testapp.utils

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.testapp.presentation.MainActivity

class NoConnectionReceiver constructor(private val activity: Activity) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val isDisconnected = intent?.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)
        val parentActivity = activity
        if (isDisconnected == true) {
            if (parentActivity is MainActivity) {

            }
        }
    }
}