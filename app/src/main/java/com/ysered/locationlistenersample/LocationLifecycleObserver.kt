package com.ysered.locationlistenersample

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import android.os.Bundle
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.ysered.locationlistenersample.util.debug

@SuppressWarnings("MissingPermission")
class LocationLifecycleObserver(context: Context, private val listener: LocationListener)
    : LifecycleObserver, GoogleApiClient.ConnectionCallbacks {

    private val locationRequest = LocationRequest.create().apply {
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        interval = 1000
        fastestInterval = 1000
    }

    private var googleApiClient: GoogleApiClient? = GoogleApiClient.Builder(context)
            .addConnectionCallbacks(this)
            .addApi(LocationServices.API)
            .build()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAddLocationListener() {
        googleApiClient?.connect()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onRemoveLocationListener() {
        debug("Disconnecting...")
        googleApiClient?.disconnect()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroyLocationListener() {
        googleApiClient = null
    }

    override fun onConnected(bundle: Bundle?) {
        debug("Google API client is connected!")
        googleApiClient?.let {
            val lastLocation = LocationServices.FusedLocationApi.getLastLocation(it)
            listener.onLocationChanged(lastLocation)
            LocationServices.FusedLocationApi.requestLocationUpdates(it, locationRequest, listener)
            debug("Requesting location updates...")
        }
    }

    override fun onConnectionSuspended(cause: Int) {}
}