package com.example.digitalstudentassistant.ui.cv

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.digitalstudentassistant.R
import com.example.digitalstudentassistant.data.UserPrefsStorage
import com.example.digitalstudentassistant.databinding.FragmentCVViewBinding
import com.example.digitalstudentassistant.domain.models.CV


class CVViewFragment : Fragment() {

    private lateinit var binding: FragmentCVViewBinding
    private lateinit var userPrefsStorage: UserPrefsStorage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCVViewBinding.inflate(layoutInflater)
        userPrefsStorage = UserPrefsStorage(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpLayout()
    }

    private fun setUpLayout() {
        val cvId = arguments?.getString("cvId")!!
        val nameCV = arguments?.getString("nameCV")!!
        val aboutInfo = arguments?.getString("aboutInfo")!!
        val school = arguments?.getString("school")!!
        val university = arguments?.getString("university")!!
        val workStatus = arguments?.getString("workStatus")!!
        val citizenship = arguments?.getString("citizenship")!!
        val language = arguments?.getString("language")!!
        val workSchedule = arguments?.getString("workSchedule")!!
        val skill = arguments?.getString("skill")!!
        val busyness = arguments?.getString("busyness")!!
        val userId = arguments?.getString("userId")!!
        if(userId != userPrefsStorage.loadUserFromPrefs()!!.id){
            binding.changeButton.isVisible = false
        }
        binding.nameCVTextView.text = nameCV
        binding.aboutInfoTextView.text = aboutInfo
        binding.skillsTextView.text = skill
        binding.workStatusTextView.text = workStatus
        binding.schoolTextView.text = school
        binding.universityTextView.text = university
        binding.citizenshipTextView.text = citizenship
        binding.languageTextView.text = language
        binding.workScheduleTextView.text = workSchedule
        binding.busynessTextView.text = busyness
        setUpChangeButton(CV(cvId, nameCV, aboutInfo, school, university, workStatus, citizenship, language, workSchedule, skill, busyness))
    }

    private fun setUpChangeButton(cv : CV){
        binding.changeButton.setOnClickListener {
            findNavController().navigate(CVViewFragmentDirections.actionCVViewFragmentToCVEditFragment(cv))
        }
    }
}