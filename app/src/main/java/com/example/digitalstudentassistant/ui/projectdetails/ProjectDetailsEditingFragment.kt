package com.example.digitalstudentassistant.ui.projectdetails

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.digitalstudentassistant.R
import com.example.digitalstudentassistant.data.models.requests.Tag
import com.example.digitalstudentassistant.data.models.requests.UpdateProjectRequest
import com.example.digitalstudentassistant.databinding.FragmentProjectDetailsEditingBinding
import com.example.digitalstudentassistant.ui.ProjectsActivity
import com.example.digitalstudentassistant.ui.UIState
import com.example.digitalstudentassistant.ui.project.ProjectViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class ProjectDetailsEditingFragment : Fragment() {

    private lateinit var binding : FragmentProjectDetailsEditingBinding
    private val args : ProjectDetailsEditingFragmentArgs by navArgs()
    private lateinit var projectEditViewModel : ProjectDetailsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProjectDetailsEditingBinding.inflate(layoutInflater)
        projectEditViewModel = ViewModelProvider(this)[ProjectDetailsViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpAddChipButton()
        setUpButtonUpdate()
        subscribeUpdateProject()
        binding.projectNameEditText.setText(args.title)
        binding.communicationEditText.setText(args.communication)
        binding.descriptionEditText.setText(args.description)
        val tags = arguments?.getString("tags")!!.split(":") as MutableList<String>
        for(i in tags){
            addChip(i)
        }
    }

    private fun addChip(text: String){
        val chip = Chip(requireContext())
        chip.text = text
        chip.setOnCloseIconClickListener {
            binding.chipGroup.removeView(chip)
        }
        chip.setBackgroundResource(R.color.gray)
        chip.isCloseIconVisible = true
        binding.chipGroup.addView(chip)
    }

    private fun setUpAddChipButton(){
        binding.addButton.setOnClickListener {
            addChip(binding.tagsEditText.text.toString())
        }
    }

    private fun setUpButtonUpdate(){
        binding.createButton.setOnClickListener {
            val title = binding.projectNameEditText.text.toString()
            val description = binding.descriptionEditText.text.toString()
            val communication = binding.communicationEditText.text.toString()
            val tags = mutableListOf<Tag>()
            for (i in 0 until binding.chipGroup.childCount) {
                val chip = binding.chipGroup.getChildAt(i) as Chip
                tags.add(Tag(name = chip.text.toString(), about = ""))
            }
            projectEditViewModel.updateProject(
                args.projectId,
                UpdateProjectRequest(title = title, description = description, communication = communication, tags = tags)
            )

        }
    }

    private fun subscribeUpdateProject(){
        projectEditViewModel.projectUpdateStateFlowPublic.onEach {
            when(it){
                is UIState.Loading -> {
                    binding.updateProgressBar.isVisible = true
                }
                is UIState.Success -> {
                    binding.updateProgressBar.isVisible = false
                    val intent = Intent(requireContext(), ProjectsActivity::class.java)
                    startActivity(intent)
                }
                is UIState.Error -> {
                    binding.updateProgressBar.isVisible = false
                    Snackbar.make(requireView(), it.data.toString(), Snackbar.LENGTH_LONG)
                        .setAction("OK") {
                        }.show()
                }
            }
        }.launchIn(lifecycleScope)
    }

}