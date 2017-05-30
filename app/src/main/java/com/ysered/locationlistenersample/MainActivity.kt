package com.ysered.locationlistenersample

import android.arch.lifecycle.LifecycleActivity
import android.os.Bundle
import android.widget.Toast
import com.ysered.locationlistenersample.util.debug
import com.ysered.locationlistenersample.util.processPermissionResults
import com.ysered.locationlistenersample.util.requestLocationPermissions

class MainActivity : LifecycleActivity() {

    val REQUEST_LOCATION_PERMISSION_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestLocationPermissions(REQUEST_LOCATION_PERMISSION_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION_CODE) {
            processPermissionResults(grantResults,
                    onGranted = {
                        // TODO: use location listener
                        debug("Permissions granted!")
                    },
                    onDenied = {
                        Toast.makeText(this, "Please enable location permissions", REQUEST_LOCATION_PERMISSION_CODE).show()
                    }
            )
        }
    }
}
