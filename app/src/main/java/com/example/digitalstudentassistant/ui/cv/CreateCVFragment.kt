package com.example.digitalstudentassistant.ui.cv

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.digitalstudentassistant.R
import com.example.digitalstudentassistant.data.models.requests.CVRequest
import com.example.digitalstudentassistant.databinding.FragmentCVEditBinding
import com.example.digitalstudentassistant.databinding.FragmentCreateCVBinding
import com.example.digitalstudentassistant.domain.OperationResult
import com.example.digitalstudentassistant.domain.models.CV
import com.example.digitalstudentassistant.ui.UIState
import com.example.digitalstudentassistant.ui.profile.ProfileFragmentDirections
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CreateCVFragment : Fragment() {

    private lateinit var binding : FragmentCreateCVBinding
    private val cVViewModel  by viewModels<CVViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateCVBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpCreateCVButton()
        subscribeAddCV()
    }

    private fun setUpCreateCVButton(){
        binding.createButton.setOnClickListener {
            val cv = getDataFromInputLayouts()
            cVViewModel.addCV(cv = cv)
        }
    }

    private fun subscribeAddCV(){
        cVViewModel.publicAddCVStateFlow.onEach {
            when(it){
                is UIState.Loading -> {
                    binding.createCVProgressBar.isVisible = true
                }
                is UIState.Success ->{
                    binding.createCVProgressBar.isVisible = false
                    findNavController().navigate(CreateCVFragmentDirections.actionCreateCVFragmentToProfileFragment())
                }
                is UIState.Error -> {
                    binding.createCVProgressBar.isVisible = false
                    Snackbar.make(requireView(), it.data.toString(), Snackbar.LENGTH_LONG)
                        .setAction("OK") {
                        }.show()
                }
            }
        }.launchIn(lifecycleScope)
    }
    private fun getDataFromInputLayouts() : CVRequest{
        val nameCV = binding.cVNameEditText.text.toString()
        val aboutInfo = binding.cVAboutInfoEditText.text.toString()
        val school = binding.schoolEditText.text.toString()
        val university = binding.universityEditText.text.toString()
        val workStatus = binding.workStatusEditText.text.toString()
        val citizenship = binding.citizenshipEditText.text.toString()
        val language = binding.languageEditText.text.toString()
        val workSchedule = binding.workScheduleEditText.text.toString()
        val skill = binding.skillsEditText.text.toString()
        val busyness = binding.busynessEditText.text.toString()
        return CVRequest(nameCV = nameCV,
            aboutInfo = aboutInfo,
            school = school,
            university = university,
            workStatus = workStatus,
            citizenship = citizenship,
            language = language,
            workSchedule = workSchedule,
            skill = skill,
            busyness = busyness
        )
    }
}