package com.example.digitalstudentassistant.ui.project

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.digitalstudentassistant.R
import com.example.digitalstudentassistant.data.UserPrefsStorage
import com.example.digitalstudentassistant.data.database.ProjectEntity
import com.example.digitalstudentassistant.data.models.requests.ProjectRequest
import com.example.digitalstudentassistant.data.models.requests.TagRequest
import com.example.digitalstudentassistant.databinding.FragmentProjectBinding

import com.example.digitalstudentassistant.domain.models.Project
import com.example.digitalstudentassistant.ui.UIState

import com.example.digitalstudentassistant.ui.textChanges
import com.google.android.material.chip.Chip
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.lang.Error

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
            activity?.onBackPressed()
        }
    }

    private fun createProject(){
        val name = binding.projectNameEditText.text.toString()
        val communication = binding.communicationEditText.text.toString()
        val description = binding.descriptionEditText.text.toString()
        val status = binding.tagsEditText.text.toString()
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
            val project = ProjectEntity(
                1,
                name,
                communication,
                description,
                status
            )
            projectViewModel.addProject(project)
            projectViewModel.createProject(project = projectRequest)
        }
        val action = ProjectFragmentDirections.actionProjectFragmentToProjectsMainFragment()
        findNavController().navigate(action)
    }

    private fun subscribeCreateProject() {
        projectViewModel.publicProjectCreateStateFlow.onEach {
            when (it) {
                is UIState.Loading -> {

                }
                is UIState.Success -> {

                }
                is UIState.Error -> {

                }
            }
        }
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