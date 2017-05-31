package com.ysered.locationlistenersample

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.location.Location
import android.os.Bundle
import android.widget.TextView
import com.ysered.locationlistenersample.util.debug
import com.ysered.locationlistenersample.util.processPermissionResults
import com.ysered.locationlistenersample.util.requestLocationPermissionsIfNeeded
import com.ysered.locationlistenersample.util.showToast

class MainActivity : LifecycleActivity() {

    val REQUEST_LOCATION_PERMISSION_CODE = 1

    val longitudeText: TextView by lazy { findViewById(R.id.longitudeText) as TextView }
    val latitudeText: TextView by lazy { findViewById(R.id.latitudeText) as TextView }
    val locationViewModel: LocationViewModel by lazy { ViewModelProviders.of(this).get(LocationViewModel::class.java) }

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
                    onDenied = { showToast("Please enable location permission") })
        }
    }

    private fun bindLocationObserver() {
        debug("Binding location observer...")
        locationViewModel.location.observe(this, Observer<Location?> { location ->
            debug("Updating location: longitude = ${location?.longitude}, latitude = ${location?.latitude}")
            if (location != null) {
                longitudeText.text = location.longitude.toString()
                latitudeText.text = location.latitude.toString()
            }
        })
    }
}
