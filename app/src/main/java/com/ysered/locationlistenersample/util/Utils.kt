package com.ysered.locationlistenersample.util

import android.util.Log
import com.ysered.locationlistenersample.BuildConfig

fun Any.debug(text: String, t: Throwable? = null) {
    if (BuildConfig.DEBUG) {
        Log.d(this::class.java.canonicalName, text, t)
    }
}
