package com.example.digitalstudentassistant.ui.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.digitalstudentassistant.MainActivity
import com.example.digitalstudentassistant.R
import com.example.digitalstudentassistant.data.UserPrefsStorage
import com.example.digitalstudentassistant.databinding.FragmentProfileBinding
import com.example.digitalstudentassistant.domain.models.CV
import com.example.digitalstudentassistant.ui.UIState
import com.example.digitalstudentassistant.ui.cv.CVActivity
import com.example.digitalstudentassistant.ui.cv.CVViewModel
import com.example.digitalstudentassistant.ui.projectdetails.ProjectDetailsActivity
import com.example.digitalstudentassistant.ui.projects.ProjectsListAdapter
import com.example.digitalstudentassistant.ui.projects.ProjectsViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding
    private lateinit var userPrefsStorage : UserPrefsStorage
    private lateinit var cVListAdapter: CVListAdapter
    private val cVViewModel by viewModels<CVViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpProfileUI()
        setUpButtonExit()
        setUpButtonUserProjects()
        setUpAdapter()
        setUpCreateCVButton()
        subscribeGetUserCV()
        setUpButtonLikedProjects()
        cVViewModel.getCV()
    }


    @SuppressLint("SetTextI18n")
    private fun setUpProfileUI(){
        userPrefsStorage = UserPrefsStorage(requireContext())
        val user = userPrefsStorage.loadUserFromPrefs()
        if (user != null) {
            binding.emailTextView.text =user.email
            binding.nameTextView.text = user.nickname
        }
    }


    private fun setUpCreateCVButton(){
        binding.createCVButton.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToCreateCVFragment()
            findNavController().navigate(action)
        }
    }

    private fun setUpButtonExit(){
        binding.logoutButton.setOnClickListener {
            userPrefsStorage.saveUserToPrefs(null)
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }
    }
    private fun setUpButtonUserProjects(){
        binding.userProjectButton.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToUserProjectsFragment()
            findNavController().navigate(action)
        }
    }

    private fun setUpButtonLikedProjects(){
        binding.likedProjectsButton.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToLikedProjectsFragment()
            findNavController().navigate(action)
        }
    }
    private fun setUpAdapter(){
        cVListAdapter = CVListAdapter{
            val intent = Intent(requireContext(), CVActivity::class.java)
            intent.putExtra("cvId", it.id)
            intent.putExtra("nameCV", it.nameCV)
            intent.putExtra("aboutInfo", it.aboutInfo)
            intent.putExtra("school", it.school)
            intent.putExtra("university", it.university)
            intent.putExtra("workStatus", it.workStatus)
            intent.putExtra("citizenship", it.citizenship)
            intent.putExtra("language", it.language)
            intent.putExtra("workSchedule", it.workSchedule)
            intent.putExtra("skill", it.skill)
            intent.putExtra("busyness", it.busyness)
            startActivity(intent)
        }

        binding.CVRecyclerView.adapter = cVListAdapter
        binding.CVRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun subscribeGetUserCV(){
        cVViewModel.publicGetCVStateFlow.onEach {
            when(it){
                is UIState.Loading ->{

                }
                is UIState.Success -> {
                    cVListAdapter.cVList.clear()
                    for (element in it.data) {
                        cVListAdapter.cVList.add(
                            element
                        )
                    }
                    cVListAdapter.notifyDataSetChanged()
                }
                is UIState.Error ->{

                }
            }
        }.launchIn(lifecycleScope)
    }

}