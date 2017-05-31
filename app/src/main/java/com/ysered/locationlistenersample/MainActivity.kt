package com.ysered.locationlistenersample

import android.arch.lifecycle.LifecycleActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.gms.location.LocationListener
import com.ysered.locationlistenersample.util.debug
import com.ysered.locationlistenersample.util.processPermissionResults
import com.ysered.locationlistenersample.util.requestLocationPermissionsIfNeeded
import com.ysered.locationlistenersample.util.showToast

class MainActivity : LifecycleActivity() {

    val REQUEST_LOCATION_PERMISSION_CODE = 1

    val longitudeText: TextView by lazy { findViewById(R.id.longitudeText) as TextView }
    val latitudeText: TextView by lazy { findViewById(R.id.latitudeText) as TextView }

    val locationListener = LocationListener { location ->
        debug("Updating location: longitude = ${location?.longitude}, latitude = ${location?.latitude}")
        if (location != null) {
            longitudeText.text = location.longitude.toString()
            latitudeText.text = location.latitude.toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestLocationPermissionsIfNeeded(REQUEST_LOCATION_PERMISSION_CODE, onGranted = { bindLocationObserver() })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION_CODE) {
            processPermissionResults(grantResults,
                    onGranted = { bindLocationObserver() },
                    onDenied = { showToast("Please enable location permissions") })
        }
    }

    private fun bindLocationObserver() {
        val observer = LocationLifecycleObserver(this, locationListener)
        lifecycle.addObserver(observer)
        debug("Added ${observer::class.java.simpleName} observer to lifecycle")
    }
}
