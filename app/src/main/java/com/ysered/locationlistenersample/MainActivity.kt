package com.ysered.locationlistenersample

import android.arch.lifecycle.LifecycleActivity
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.widget.TextView
import com.ysered.locationlistenersample.util.processPermissionResults
import com.ysered.locationlistenersample.util.requestLocationPermissionsIfNeeded
import com.ysered.locationlistenersample.util.showToast

class MainActivity : LifecycleActivity() {

    val REQUEST_LOCATION_PERMISSION_CODE = 1

    val longitudeText: TextView by lazy { findViewById(R.id.longitudeText) as TextView }
    val latitudeText: TextView by lazy { findViewById(R.id.latitudeText) as TextView }

    val locationObserver by lazy { LocationLifecycleObserver(this, this, locationListener) }

    val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location?) {
            if (location != null) {
                longitudeText.text = location.longitude.toString()
                latitudeText.text = location.latitude.toString()
            }
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

        override fun onProviderEnabled(provider: String?) {}

        override fun onProviderDisabled(provider: String?) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestLocationPermissionsIfNeeded(REQUEST_LOCATION_PERMISSION_CODE, onGranted = { locationObserver.bind() })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION_CODE) {
            processPermissionResults(grantResults,
                    onGranted = { locationObserver.bind() },
                    onDenied = { showToast("Please enable location permissions") })
        }
    }
}
