package fr.xebia.pocribreader

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import fr.xebia.pocribreader.reader.OcrCaptureActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_open_reader.setOnClickListener {
            startActivity(Intent(this, OcrCaptureActivity::class.java))
        }
    }
}
