package com.example.digitalstudentassistant.ui.projectdetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.example.digitalstudentassistant.data.database.ProjectEntity
import com.example.digitalstudentassistant.databinding.FragmentProjectDetailsViewBinding
import com.example.digitalstudentassistant.domain.models.Project
import com.example.digitalstudentassistant.ui.project.ProjectViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProjectDetailsViewFragment : Fragment() {

    private lateinit var binding: FragmentProjectDetailsViewBinding
    private lateinit var projectDetailsViewModel : ProjectDetailsViewModel
    private lateinit var project: Project

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProjectDetailsViewBinding.inflate(layoutInflater)
        projectDetailsViewModel = ViewModelProvider(this).get(ProjectDetailsViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val projectId = arguments?.getString("projectId")!!
        lifecycle.coroutineScope.launch {
            projectDetailsViewModel.getProjectFromDB(projectId.toInt()).collect {
                binding.projectNameTextView.text = it.name
                binding.statusTextView.text = it.communication
                binding.purposeTextView.text = it.tags
                binding.descriptionTextView.text = it.description
                project = Project(1,
                    it.name,
                    it.communication,
                    it.description,
                    it.tags)
                setUpEditButton(project)
            }
        }

    }

    private fun setUpEditButton(project: Project){
        val creatorId = 3
        val userId = 3
        if(creatorId == userId){
            binding.editButton.setOnClickListener {
                binding.editButton.isVisible = true
                val action = ProjectDetailsViewFragmentDirections.actionProjectDetailsViewFragmentToProjectDetailsEditingFragment(project)
                findNavController().navigate(action)
            }
        }else{
            binding.editButton.isVisible = false
        }

    }




}