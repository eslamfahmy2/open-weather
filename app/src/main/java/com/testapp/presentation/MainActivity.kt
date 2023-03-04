package com.testapp.presentation

import android.app.Activity
import android.content.BroadcastReceiver
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.testapp.presentation.components.navigation.MainAppGraph
import com.testapp.presentation.components.theme.MyApplicationTestUiAppTheme
import com.testapp.utils.NoConnectionReceiver
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //listen for network state
    private lateinit var internetConnectionBroadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTestUiAppTheme {
                MainAppGraph()
            }
        }
        internetConnectionBroadcastReceiver =
            NoConnectionReceiver(WeakReference<Activity>(this).get() ?: this)
    }
}
