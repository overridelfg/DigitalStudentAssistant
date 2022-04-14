package com.example.digitalstudentassistant.ui.cv

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.digitalstudentassistant.R
import com.example.digitalstudentassistant.databinding.FragmentCVEditBinding
import com.example.digitalstudentassistant.databinding.FragmentCreateCVBinding
import com.example.digitalstudentassistant.domain.OperationResult
import com.example.digitalstudentassistant.domain.models.CV
import com.example.digitalstudentassistant.ui.UIState
import com.example.digitalstudentassistant.ui.profile.ProfileFragmentDirections
import kotlinx.coroutines.flow.onEach

class CreateCVFragment : Fragment() {

    private lateinit var binding : FragmentCreateCVBinding
    private val cVViewModel  by activityViewModels<CVViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateCVBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpCreateCVButton()
//        subscribeAddCV()
    }

    private fun setUpCreateCVButton(){
        binding.createButton.setOnClickListener {
            val cv = getDataFromInputLayouts()
//            cVViewModel.addCV(cv = cv)
            findNavController().navigate(CreateCVFragmentDirections.actionCreateCVFragmentToProfileFragment())
        }
    }

    private fun subscribeAddCV(){
        cVViewModel.publicCVStateFlow.onEach {
            when(it){
                is UIState.Loading -> {

                }
                is UIState.Success ->{

                }
                is UIState.Error -> {

                }
            }
        }
    }
    private fun getDataFromInputLayouts() : CV{
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
        return CV(nameCV = nameCV,
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