package com.example.newspetapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.newspetapp.R
import com.example.newspetapp.data.module.UploadRequestBody
import com.example.newspetapp.data.module.User

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_main)
    }

    companion object{
        const val TEXT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sapien erat, scelerisque a pharetra vitae, viverra et nisi. Integer ullamcorper erat sit amet venenatis condimentum. Curabitur nec gravida lectus, ut molestie nisi. Nunc purus velit, aliquam sed varius in, elementum eu nibh. Cras justo turpis, molestie ut nisi aliquet, feugiat placerat augue. Suspendisse potenti. Suspendisse tempus magna quis mi volutpat, non tincidunt metus gravida. Duis aliquam, sem eget finibus pulvinar, arcu ligula vehicula felis, eget laoreet erat nisl nec arcu. Pellentesque erat libero, posuere non tempus sit amet, finibus at augue. Vestibulum sed ornare enim. Aliquam tincidunt dictum turpis quis gravida. Phasellus interdum tristique tellus, eget pulvinar lectus hendrerit vitae. Nulla a viverra diam. Donec quis auctor lorem."
//        val USER_SULTAN = User(0, "Sultan", "sultan@gmail.kg")
        const val IMAGE = "https://res.cloudinary.com/neomedtech/image/upload/v1/media/news/ce40bd5211f3cf56e18dbd828c41eb47_ankc5j"
        const val MAIN_MODE = "main"
        const val SAVED_MODE = "saved"
    }
}