package com.example.digitalstudentassistant.ui.cv

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.digitalstudentassistant.R
import com.example.digitalstudentassistant.data.models.requests.CVRequest
import com.example.digitalstudentassistant.databinding.FragmentCVEditBinding
import com.example.digitalstudentassistant.ui.ProjectsActivity
import com.example.digitalstudentassistant.ui.UIState
import com.example.digitalstudentassistant.ui.projectdetails.ProjectDetailsEditingFragmentArgs
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class CVEditFragment : Fragment() {

    private lateinit var binding : FragmentCVEditBinding
    private val args : CVEditFragmentArgs by navArgs()
    private val cvViewModel by activityViewModels<CVViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCVEditBinding.inflate(layoutInflater)
        subscribeUpdateCV()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.cVNameEditText.setText(args.cVdata.nameCV)
        binding.cVAboutInfoEditText.setText(args.cVdata.aboutInfo)
        binding.schoolEditText.setText(args.cVdata.school)
        binding.universityEditText.setText(args.cVdata.university)
        binding.workStatusEditText.setText(args.cVdata.workStatus)
        binding.citizenshipEditText.setText(args.cVdata.citizenship)
        binding.languageEditText.setText(args.cVdata.language)
        binding.workScheduleEditText.setText(args.cVdata.workSchedule)
        binding.skillsEditText.setText(args.cVdata.skill)
        binding.busynessEditText.setText(args.cVdata.busyness)
        setUpUpdateButton()
    }

    private fun setUpUpdateButton(){
        binding.changeButton.setOnClickListener {
            cvViewModel.updateCV(args.cVdata.id, getDataFromInputLayouts())
        }
    }

    private fun subscribeUpdateCV(){
        cvViewModel.publicUpdateCVStateFlow.onEach {
            when(it){
                is UIState.Loading ->{
                    binding.changeCVProgressBar.isVisible = true
                }
                is UIState.Success ->{
                    binding.changeCVProgressBar.isVisible = false
                    startActivity(Intent(requireContext(), ProjectsActivity::class.java))
                }
                is UIState.Error ->{
                    binding.changeCVProgressBar.isVisible = false
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