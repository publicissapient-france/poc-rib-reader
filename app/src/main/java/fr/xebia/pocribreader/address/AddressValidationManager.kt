package fr.xebia.pocribreader.address

import android.content.Context
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.AutocompletePredictionBuffer
import com.google.android.gms.location.places.Places
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds


object AddressValidationManager {
    private var mGoogleApiClient: GoogleApiClient? = null
    fun init(context: Context, callbacksListener: GoogleApiClient.ConnectionCallbacks) {

        mGoogleApiClient = GoogleApiClient.Builder(context)
                .addApi(Places.GEO_DATA_API)
                .addConnectionCallbacks(callbacksListener)
                .build()
        mGoogleApiClient?.connect()
    }

    fun checkAddressValidity(address: String, callback: ResultCallback<AutocompletePredictionBuffer>) {
        val franceSouthWest = LatLng(43.06, -3.97)
        val franceNorthEast = LatLng(50.92, 8.19)
        val bounds = LatLngBounds(franceSouthWest, franceNorthEast)

        val filter = AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                .build()

        val result = Places.GeoDataApi.getAutocompletePredictions(mGoogleApiClient, address, bounds, filter)

        result.setResultCallback(callback)
    }

}