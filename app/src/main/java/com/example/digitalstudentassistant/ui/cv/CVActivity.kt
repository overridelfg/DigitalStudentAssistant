package com.example.digitalstudentassistant.ui.cv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.example.digitalstudentassistant.R

class CVActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cvactivity)

        findNavController(R.id.fragmentContainerView).setGraph(R.navigation.nav_cv, intent.extras)
    }
}