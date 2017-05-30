package com.ysered.locationlistenersample.util

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.ysered.locationlistenersample.BuildConfig

fun Any.debug(text: String, t: Throwable? = null) {
    if (BuildConfig.DEBUG) {
        Log.d(this::class.java.canonicalName, text, t)
    }
}

fun Activity.showToast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}