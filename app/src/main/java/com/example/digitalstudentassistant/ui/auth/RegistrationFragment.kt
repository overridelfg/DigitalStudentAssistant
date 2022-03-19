package com.example.digitalstudentassistant.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.digitalstudentassistant.R
import com.example.digitalstudentassistant.databinding.FragmentRegistrationBinding


class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(layoutInflater)

        return binding.root
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