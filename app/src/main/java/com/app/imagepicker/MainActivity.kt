package com.app.imagepicker

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.io.ByteArrayInputStream

class MainActivity : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var imageview: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.pickbtn)
        imageview = findViewById(R.id.iv)

        button.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pikImage.launch(intent)
        }
    }

    private val pikImage = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK && it.data != null) {
            val uri = it.data?.data

            val intent = Intent(this, CropImageActivity::class.java)
            intent.putExtra("uri", uri.toString())

            getCropImage.launch(intent)
        }
    }

    private val getCropImage = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK && it.data != null) {

            val imageByte = it.data!!.getByteArrayExtra("crop_image")

            if (imageByte != null) {
                val stream = ByteArrayInputStream(imageByte)
                val bitmap = BitmapFactory.decodeStream(stream)

                imageview.setImageBitmap(bitmap)
            }
        }
    }
}