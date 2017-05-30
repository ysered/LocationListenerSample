package com.ysered.locationlistenersample.util

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat

/**
 * Requests [Manifest.permission.ACCESS_FINE_LOCATION] and [Manifest.permission.ACCESS_COARSE_LOCATION] permissions
 * if they aren't enabled.
 *
 * @param [requestCode] to filter results in [Activity.onRequestPermissionsResult]
 */
fun Activity.requestLocationPermissions(requestCode: Int): Unit {
    requestPermissionsIfNeeded(requestCode,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION)
}

/**
 * Requests [permissions] if any of them aren't enabled.
 *
 * @param [requestCode] to filter results in [Activity.onRequestPermissionsResult]
 */
fun Activity.requestPermissionsIfNeeded(requestCode: Int, vararg permissions: String): Unit {
    val toRequest = permissions
            .filter { ActivityCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED }
            .toTypedArray()
    if (toRequest.isNotEmpty()) {
        ActivityCompat.requestPermissions(this, toRequest, requestCode)
    }
}

/**
 * Helper function to process permission results in [Activity.onRequestPermissionsResult].
 *
 * @param [grantResults] flags to indicate which permissions is granted
 * @param [onGranted] function to be executed if all permissions is granted
 * @param [onDenied] function to be executed if some or all permissions is denied
 */
fun processPermissionResults(grantResults: IntArray, onGranted: () -> Unit, onDenied: () -> Unit) {
    val isAllGranted = grantResults.filter { it != PackageManager.PERMISSION_GRANTED }.isEmpty()
    if (isAllGranted) {
        onGranted()
    } else {
        onDenied()
    }
}
