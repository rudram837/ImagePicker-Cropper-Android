package com.app.imagepicker

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.canhub.cropper.CropImageView
import java.io.ByteArrayOutputStream

class CropImageActivity : AppCompatActivity() {

    private lateinit var back: ImageView
    private lateinit var check: ImageView
    private lateinit var cropImageView: CropImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crop_image)

        back = findViewById(R.id.back)
        check = findViewById(R.id.check)
        cropImageView = findViewById(R.id.cropImageView)

        back.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val uri = intent.getStringExtra("uri")
        if (uri != null) {
            cropImageView.setImageUriAsync(Uri.parse(uri))
        }

        check.setOnClickListener {
            saveCrop()
        }
    }

    private fun saveCrop() {
        try {
            val bitmap = cropImageView.getCroppedImage()

            if (bitmap != null) {
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream)

                val intent = Intent()
                intent.putExtra("crop_image", stream.toByteArray()) // ✅ FIXED

                setResult(RESULT_OK, intent)
                finish()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}