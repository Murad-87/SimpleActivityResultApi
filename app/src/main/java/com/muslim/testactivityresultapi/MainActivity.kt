package com.muslim.testactivityresultapi

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var getUsernameButton: Button
    private lateinit var usernameTextView: TextView
    private lateinit var getImageButton: Button
    private lateinit var imageFromGalleryImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()

        //используем готовые контракты
        val contractUsername = ActivityResultContracts.StartActivityForResult()

        val launcherUsername = registerForActivityResult(contractUsername) {
            if (it.resultCode == RESULT_OK) {
                usernameTextView.text = it.data?.getStringExtra(UsernameActivity.EXTRA_USERNAME)
            }
        }

        val contractImage = ActivityResultContracts.GetContent()

        val launcherImage = registerForActivityResult(contractImage) {
            imageFromGalleryImageView.setImageURI(it)
        }

        getUsernameButton.setOnClickListener {

            launcherUsername.launch(UsernameActivity.newIntent(this))

            //old method
//            UsernameActivity.newIntent(this).apply {
//                startActivityForResult(this, RC_USERNAME)
//            }

        }
        getImageButton.setOnClickListener {

            launcherImage.launch("image/*")

            //old method
//            Intent(Intent.ACTION_PICK).apply {
//                type = "image/*"
//                startActivityForResult(this, RC_IMAGE)
//            }
        }
    }

    //old method
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == RC_USERNAME && resultCode == RESULT_OK) {
//            val username = data?.getStringExtra(UsernameActivity.EXTRA_USERNAME)
//            usernameTextView.text = username
//        }
//        if (requestCode == RC_IMAGE && resultCode == RESULT_OK) {
//            val uri = data?.data
//            imageFromGalleryImageView.setImageURI(uri)
//        }
//    }

    private fun initViews() {
        getUsernameButton = findViewById(R.id.get_username_button)
        usernameTextView = findViewById(R.id.username_textview)
        getImageButton = findViewById(R.id.get_image_button)
        imageFromGalleryImageView = findViewById(R.id.image_from_gallery_imageview)
    }

    //old method
//    companion object {
//
//        private const val RC_USERNAME = 100
//        private const val RC_IMAGE = 101
//    }



    //Этот метод используеться когда мы сами создаем контракт
//    val contractUsername = object : ActivityResultContract<Intent, String?>() {
//        override fun createIntent(context: Context, input: Intent): Intent {
//            return input
//        }
//
//        override fun parseResult(resultCode: Int, intent: Intent?): String? {
//            if (resultCode == RESULT_OK) {
//                return intent?.getStringExtra(UsernameActivity.EXTRA_USERNAME)
//            }
//            return null
//        }
//    }


    //Этот метод используеться когда мы сами создаем контракт
//    val contractImage = object : ActivityResultContract<String, Uri?>() {
//        override fun createIntent(context: Context, input: String): Intent {
//            return Intent(Intent.ACTION_PICK).apply {
//                type = input
//            }
//        }
//
//        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
//            return intent?.data
//        }
//
//    }
}