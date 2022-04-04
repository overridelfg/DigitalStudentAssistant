package com.example.digitalstudentassistant.ui.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.digitalstudentassistant.MainActivity
import com.example.digitalstudentassistant.R
import com.example.digitalstudentassistant.data.UserPrefsStorage
import com.example.digitalstudentassistant.databinding.FragmentProfileBinding
import com.google.android.material.chip.Chip


class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding
    private lateinit var userPrefsStorage : UserPrefsStorage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userPrefsStorage = UserPrefsStorage(requireContext())
        val user = userPrefsStorage.loadUserFromPrefs()
        var interests = mutableListOf<String>()
        if (user != null) {
            binding.emailTextView.text = binding.emailTextView.text.toString() + " " + user.email
            binding.nameTextView.text =
                binding.nameTextView.text.toString() +
                        " " + user.firstname +
                        " " + user.lastname +
                        " " + user.surname
            interests = user.interests.split(":") as MutableList<String>
            for(i in interests){
                addChip(i)
            }
        }
        setUpButtonExit()
    }

    private fun addChip(text: String){
        val chip = Chip(requireContext())
        chip.text = text
        chip.setBackgroundResource(R.color.gray)
        chip.isCloseIconVisible = true
        binding.chipGroup.addView(chip)
    }

    private fun setUpButtonExit(){
        binding.logoutButton.setOnClickListener {
            userPrefsStorage.saveUserToPrefs(null)
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }
    }

}