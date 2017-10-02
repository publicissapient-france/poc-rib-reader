/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.xebia.pocribreader.reader

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.text.TextBlock
import fr.xebia.pocribreader.reader.ui.camera.GraphicOverlay
import fr.xebia.pocribreader.result.BankAccountInfoActivity
import fr.xebia.pocribreader.result.BankAccountManager
import android.util.SparseArray
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import fr.xebia.pocribreader.address.AddressValidationManager


/**
 * A very simple Processor which receives detected TextBlocks and adds them to the overlay
 * as OcrGraphics.
 */
class OcrDetectorProcessor internal constructor(private val mGraphicOverlay: GraphicOverlay<OcrGraphic>?,
                                                private val bankAccountManager: BankAccountManager,
                                                private val activity: AppCompatActivity) : Detector.Processor<TextBlock>, GoogleApiClient.ConnectionCallbacks {
    var ibanFound = false
    var addressFound = false
    var ibanString : String? = String()
    var addressString : String? = String()

    /**
     * Called by the detector to deliver detection results.
     * If your application called for it, this could be a place to check for
     * equivalent detections by tracking TextBlocks that are similar in location and content from
     * previous frames, or reduce noise by eliminating TextBlocks that have not persisted through
     * multiple detections.
     */
    override fun receiveDetections(detections: Detector.Detections<TextBlock>) {
        mGraphicOverlay?.clear()
        val items = detections.detectedItems

        AddressValidationManager.init(activity, this)

        if(!ibanFound) {
            (0 until items.size())
                    .map { items.valueAt(it) }
                    .map { OcrGraphic(mGraphicOverlay, it) }
                    .forEach {
                        val value = it.textBlock?.value!!
                        if (bankAccountManager.checkIban(it.textBlock?.value)) {
                            Log.e("Xebia" , "Iban found " + value)
                            ibanFound = true
                            ibanString = it.textBlock?.value
                        }
                        mGraphicOverlay?.add(it)
                    }
        }
        if(!addressFound) {
            (0 until items.size())
                    .map { items.valueAt(it) }
                    .map { OcrGraphic(mGraphicOverlay, it) }
                    .forEach {
                        val value = it.textBlock?.value!!
                        AddressValidationManager.checkAddressValidity(value, ResultCallback{
                            if(it.count > 0) {
                                addressFound = true
                                addressString = it.get(0).toString()
                                Log.e("Xebia" , "Address found : " + value)
                            }
                        })
                        mGraphicOverlay?.add(it)
                    }
        }

        if(addressFound && ibanFound) {
            bankAccountManager.bankAccount.address = addressString!!
            val intent = Intent(activity, BankAccountInfoActivity::class.java)
            intent.putExtra(BankAccountInfoActivity.BANK_ACCOUNT_PARAM, bankAccountManager.bankAccount)
            activity.startActivity(intent)
            activity.finish()
        }
    }

    /**
     * Frees the resources associated with this detection processor.
     */
    override fun release() {
        mGraphicOverlay?.clear()
    }

    override fun onConnected(p0: Bundle?) {
    }

    override fun onConnectionSuspended(p0: Int) {
    }
}
