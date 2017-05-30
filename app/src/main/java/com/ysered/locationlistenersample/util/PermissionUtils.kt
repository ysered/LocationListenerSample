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
 * @param [onGranted] function to be executed if permissions already granted
 */
fun Activity.requestLocationPermissionsIfNeeded(requestCode: Int, onGranted: () -> Unit): Unit {
    requestPermissionsIfNeeded(requestCode,
            onGranted,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION)
}

/**
 * Requests permissions if some of them aren't granted.
 *
 * @param [requestCode] to filter results in [Activity.onRequestPermissionsResult]
 * @param [onGranted] function to be executed if permissions already granted
 * @param [permissions] array of permission to be requested if some of them aren't grated
 */
fun Activity.requestPermissionsIfNeeded(requestCode: Int, onGranted: () -> Unit, vararg permissions: String): Unit {
    val toRequest = permissions
            .filter { ActivityCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED }
            .toTypedArray()
    if (toRequest.isNotEmpty()) {
        ActivityCompat.requestPermissions(this, toRequest, requestCode)
    } else {
        onGranted()
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
