package com.example.digitalstudentassistant.ui.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.digitalstudentassistant.MainActivity
import com.example.digitalstudentassistant.R
import com.example.digitalstudentassistant.data.UserPrefsStorage
import com.example.digitalstudentassistant.databinding.FragmentProfileBinding
import com.example.digitalstudentassistant.domain.models.CV
import com.example.digitalstudentassistant.ui.cv.CVActivity
import com.example.digitalstudentassistant.ui.projectdetails.ProjectDetailsActivity
import com.example.digitalstudentassistant.ui.projects.ProjectsListAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable


class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding
    private lateinit var userPrefsStorage : UserPrefsStorage
    private lateinit var cVListAdapter: CVListAdapter

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
        setUpEditButton()
        setUpButtonUserProjects()
        setUpAdapter()
        setUpCreateCVButton()
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

    private fun setUpEditButton(){
        binding.editProfileButton.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToProfileEditFragment()
            findNavController().navigate(action)
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
        val action = ProfileFragmentDirections.actionProfileFragmentToUserProjectsFragment()
        findNavController().navigate(action)
    }
    private fun setUpAdapter(){
        cVListAdapter = CVListAdapter{
            val intent = Intent(requireContext(), CVActivity::class.java)
            intent.putExtra("courseId", it.nameCV)
            startActivity(intent)
        }
        cVListAdapter.cVList.add(CV("Android developer",
        "","","","","","","","",""))
        cVListAdapter.cVList.add(CV("IOS developer",
            "","","","","","","","",""))
        binding.CVRecyclerView.adapter = cVListAdapter
        binding.CVRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

}