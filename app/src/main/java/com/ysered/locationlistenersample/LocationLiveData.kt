package com.ysered.locationlistenersample

import android.arch.lifecycle.LiveData
import android.content.Context
import android.location.Location
import android.os.Bundle
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.ysered.locationlistenersample.util.debug

@SuppressWarnings("MissingPermission")
class LocationLiveData(context: Context) : LiveData<Location>(),
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private val googleApiClient: GoogleApiClient = GoogleApiClient.Builder(context)
            .addConnectionCallbacks(this)
            .addApi(LocationServices.API)
            .build()

    private val locationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(1000)
            .setFastestInterval(1000)

    override fun onActive() {
        super.onActive()
        googleApiClient.connect()
        debug("Become active!")
    }

    override fun onInactive() {
        super.onInactive()
        if (googleApiClient.isConnected) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this)
        }
        googleApiClient.disconnect()
        debug("Become inactive!")
    }

    override fun onConnected(bundle: Bundle?) {
        val lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient)
        if (lastLocation != null) {
            value = lastLocation
        }
        if (hasObservers()) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this)
        }
    }

    override fun onConnectionSuspended(cause: Int) {}

    override fun onConnectionFailed(result: ConnectionResult) {}

    override fun onLocationChanged(location: Location?) {
        if (location != null) {
            value = location
        }
    }
}
