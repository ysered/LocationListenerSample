package com.ysered.locationlistenersample

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import android.location.LocationListener
import android.location.LocationManager

@SuppressWarnings("MissingPermission")
class LocationLifecycleObserver(private val lifecycleOwner: LifecycleOwner,
                                private val context: Context,
                                private val listener: LocationListener) : LifecycleObserver {

    private var locationManager: LocationManager? = null

    fun bind() {
        lifecycleOwner.lifecycle.addObserver(this)
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onAddLocationListener() {
        locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, listener)
        val lastKnownLocation = locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (lastKnownLocation != null) {
            listener.onLocationChanged(lastKnownLocation)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onRemoveLocationListener() {
        locationManager?.removeUpdates(listener)
        locationManager = null
    }
}