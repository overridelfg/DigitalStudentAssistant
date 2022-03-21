package com.example.digitalstudentassistant.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.digitalstudentassistant.R
import com.example.digitalstudentassistant.databinding.FragmentRegistrationBinding
import com.example.digitalstudentassistant.ui.UIState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
//    private val registerViewModel by activityViewModels<RegistrationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

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
    }

    private fun setUpButton(){
        binding.registrationButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val nickname = binding.nicknameEditText.text.toString()
            val password = binding.nicknameEditText.text.toString()
            val telegramUrl = binding.telegramUrlEditText.text.toString()

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