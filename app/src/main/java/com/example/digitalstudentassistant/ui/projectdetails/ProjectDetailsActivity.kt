package com.example.digitalstudentassistant.ui.projectdetails

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import com.example.digitalstudentassistant.data.database.ProjectEntity
import com.example.digitalstudentassistant.databinding.ActivityProjectDetailsBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProjectDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProjectDetailsBinding
    private val projectDetailsViewModel by viewModels<ProjectDetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProjectDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var projectDetails: ProjectEntity
        val bundle = intent.extras
        val projectId = bundle!!.getString("projectId")
        lifecycle.coroutineScope.launch {
            projectDetailsViewModel.getProjectFromDB(projectId!!.toInt()).collect {
                binding.projectNameTextView.text = it.name
            }
        }
    }
}