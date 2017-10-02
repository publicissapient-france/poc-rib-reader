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
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.text.TextBlock
import fr.xebia.pocribreader.reader.ui.camera.GraphicOverlay
import fr.xebia.pocribreader.result.BankAccountInfoActivity
import fr.xebia.pocribreader.result.BankAccountManager

/**
 * A very simple Processor which receives detected TextBlocks and adds them to the overlay
 * as OcrGraphics.
 */
class OcrDetectorProcessor internal constructor(private val mGraphicOverlay: GraphicOverlay<OcrGraphic>?,
                                                private val bankAccountManager: BankAccountManager,
                                                private val activity: AppCompatActivity) : Detector.Processor<TextBlock> {

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
        (0 until items.size())
                .map { items.valueAt(it) }
                .map { OcrGraphic(mGraphicOverlay, it) }
                .forEach {
                    if (bankAccountManager.checkIban(it.textBlock?.value)) {
                        val intent = Intent(activity, BankAccountInfoActivity::class.java)
                        intent.putExtra(BankAccountInfoActivity.BANK_ACCOUNT_PARAM, bankAccountManager.bankAccount)
                        activity.startActivity(intent)
                        activity.finish()
                    }
                    mGraphicOverlay?.add(it)
                }
    }

    /**
     * Frees the resources associated with this detection processor.
     */
    override fun release() {
        mGraphicOverlay?.clear()
    }
}
