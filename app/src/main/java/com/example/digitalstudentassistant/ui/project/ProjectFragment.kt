package com.example.digitalstudentassistant.ui.project

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.digitalstudentassistant.R
import com.example.digitalstudentassistant.data.UserPrefsStorage
import com.example.digitalstudentassistant.data.models.requests.ProjectRequest
import com.example.digitalstudentassistant.data.models.requests.TagRequest
import com.example.digitalstudentassistant.databinding.FragmentProjectBinding

import com.example.digitalstudentassistant.ui.UIState

import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

import java.util.*


class ProjectFragment : Fragment() {


    private lateinit var binding: FragmentProjectBinding
    private lateinit var projectViewModel: ProjectViewModel
    private lateinit var userPrefsStorage: UserPrefsStorage
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProjectBinding.inflate(layoutInflater)
        projectViewModel = ViewModelProvider(this).get(ProjectViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpTagsList()
        subscribeCreateProject()
        userPrefsStorage = UserPrefsStorage(requireContext())
        binding.createButton.setOnClickListener {
            createProject()
        }
    }

    private fun createProject(){
        val name = binding.projectNameEditText.text.toString()
        val communication = binding.communicationEditText.text.toString()
        val description = binding.descriptionEditText.text.toString()
        var tags = mutableListOf<TagRequest>()
        for (i in 0 until binding.chipGroup.childCount) {
            val chip = binding.chipGroup.getChildAt(i) as Chip
            tags.add(TagRequest(chip.text.toString(), ""))
        }
        val projectRequest = ProjectRequest(
            title = name,
            description = description,
            communication = communication,
            tags = tags,
            creatorId = "userPrefsStorage.loadUserFromPrefs()!!.id"
        )
        if (inputCheck(name, communication, description)) {
            projectViewModel.createProject(project = projectRequest)
        }
    }

    private fun subscribeCreateProject() {
        projectViewModel.publicProjectCreateStateFlow.onEach {
            when (it) {
                is UIState.Loading -> {
                    binding.createProgressBar.isVisible = true
                }
                is UIState.Success -> {
                    binding.createProgressBar.isVisible = false
                    val action = ProjectFragmentDirections.actionProjectFragmentToProjectsMainFragment()
                    findNavController().navigate(action)
                }
                is UIState.Error -> {
                    binding.createProgressBar.isVisible = false
                    Snackbar.make(requireView(), it.data.toString(), Snackbar.LENGTH_LONG)
                        .setAction("OK") {
                        }.show()
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun addChip(text: String) {
        val chip = Chip(requireContext())
        chip.text = text
        chip.setBackgroundResource(R.color.gray)
        chip.isCloseIconVisible = true
        chip.setOnCloseIconClickListener {
            binding.chipGroup.removeView(chip)
        }
        binding.chipGroup.addView(chip)
    }

    private fun setUpTagsList() {
        binding.addButton.setOnClickListener {
            if (!binding.tagsEditText.text.isNullOrBlank()) {
                addChip(binding.tagsEditText.text.toString())
                binding.tagsEditText.setText("")
            }
        }
    }

    private fun inputCheck(name: String, purpose: String, description: String): Boolean {
        return !(TextUtils.isEmpty(name) && TextUtils.isEmpty(purpose) && TextUtils.isEmpty(
            description
        ))
    }


}