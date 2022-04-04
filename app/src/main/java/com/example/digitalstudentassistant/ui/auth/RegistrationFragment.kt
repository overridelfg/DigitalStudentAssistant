package com.example.digitalstudentassistant.ui.auth

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.digitalstudentassistant.R
import com.example.digitalstudentassistant.data.UserPrefsStorage
import com.example.digitalstudentassistant.databinding.FragmentRegistrationBinding
import com.example.digitalstudentassistant.domain.models.User
import com.example.digitalstudentassistant.ui.ProjectsActivity
import com.example.digitalstudentassistant.ui.UIState
import com.google.android.material.chip.Chip
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
//    private val registerViewModel by activityViewModels<RegistrationViewModel>()
    private lateinit var userPrefsStorage: UserPrefsStorage
    private var interests : String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpInterestsList()
//        binding.registerProgressBar.visibility = View.INVISIBLE
//        registerViewModel.publicRegisterStateFlow.onEach {
//            when(it){
//                is UIState.Loading ->{
//                    binding.registerProgressBar.visibility = View.VISIBLE
//                }
//                is UIState.Success -> {
//                    binding.registerProgressBar.visibility = View.INVISIBLE
//                }
//                is UIState.Error ->{
//                    binding.registerProgressBar.visibility = View.INVISIBLE
//                }
//            }
//        }.launchIn(lifecycleScope)
        userPrefsStorage = UserPrefsStorage(requireContext())
        setUpButton()
    }

    private fun setUpInterestsList(){
        binding.addButton.setOnClickListener {
            if(!binding.interestsEditText.text.isNullOrBlank()){
                addChip(binding.interestsEditText.text.toString())
                binding.interestsEditText.setText("")
            }
        }
    }

    private fun addChip(text: String){
        val chip = Chip(requireContext())
        chip.text = text
        chip.setBackgroundResource(R.color.gray)
        chip.isCloseIconVisible = true
        chip.setOnCloseIconClickListener {
            binding.chipGroup.removeView(chip)
        }
        binding.chipGroup.addView(chip)
    }

    private fun setUpButton(){
        binding.registrationButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val nickname = binding.nicknameEditText.text.toString()
            val password = binding.nicknameEditText.text.toString()
            val telegramUrl = binding.telegramUrlEditText.text.toString()
            val firstname = binding.firstnameEditText.text.toString()
            val lastname = binding.lastnameEditText.text.toString()
            val surname = binding.surnameEditText.text.toString()
            val phone = binding.phoneEditText.text.toString()

            userPrefsStorage.saveUserToPrefs(
                User(
                    id = 1,
                    email = email,
                    nickname = nickname,
                    phoneNumber = phone,
                    firstname = firstname,
                    lastname = lastname,
                    surname = surname,
                    telegram = telegramUrl,
                    password = password,
                    token = "3sdfsdufishf"
                )
            )

            val intent = Intent(requireContext(), ProjectsActivity::class.java)
            startActivity(intent)

//            registerViewModel.register(email, nickname, password)
        }
    }

    private fun setUpRegistrationButtonValidation(){
        binding.apply {
            registrationButton.isEnabled = emailEditText.text.isNullOrBlank() &&
                    nicknameEditText.text.isNullOrBlank() &&
                    passwordEditText.text.isNullOrBlank() &&
                    telegramUrlEditText.text.isNullOrBlank()
        }
    }
}