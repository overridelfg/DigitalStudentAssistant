package com.example.digitalstudentassistant.ui.projectdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.digitalstudentassistant.R
import com.example.digitalstudentassistant.databinding.FragmentProjectDetailsEditingBinding


class ProjectDetailsEditingFragment : Fragment() {

    private lateinit var binding : FragmentProjectDetailsEditingBinding
    private val args : ProjectDetailsEditingFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProjectDetailsEditingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.projectNameEditText.setText(args.projectData.name)
        binding.purposeEditText.setText(args.projectData.purpose)
        binding.descriptionEditText.setText(args.projectData.description)
        binding.deadlineProjectFromDateEditText.setText(args.projectData.deadlineProjectDateFrom)
        binding.deadlineProjectToDateEditText.setText(args.projectData.deadlineProjectDateTo)
        binding.deadlineTeamFromDateEditText.setText(args.projectData.deadlineTeamDateFrom)
        binding.deadlineTeamToDateEditText.setText(args.projectData.deadlineTeamDateTo)
        binding.numberOfPeopleEditText.setText(args.projectData.participantsNumber.toString())
        binding.statusEditText.setText(args.projectData.status)
    }

}