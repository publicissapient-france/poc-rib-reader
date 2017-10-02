package fr.xebia.pocribreader

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.location.places.AutocompletePrediction
import fr.xebia.pocribreader.address.AddressValidationManager

class MainActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AddressValidationManager.init(this, this)
    }

    override fun onConnected(p0: Bundle?) {
        Log.e("Xebia", "Connected")

        AddressValidationManager.checkAddressValidity("156 Boulevard Haussmann, 75008 Paris", ResultCallback {
            Log.e("Xebia", "Returned " + it.count)

            for (prediction: AutocompletePrediction in it) {
                Log.e("Xebia", prediction.getFullText(null).toString())
            }
        })
    }

    override fun onConnectionSuspended(p0: Int) {
        Log.e("Xebia", "Connection suspended :(")
    }
}
