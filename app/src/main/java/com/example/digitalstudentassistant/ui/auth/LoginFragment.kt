package com.example.digitalstudentassistant.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.digitalstudentassistant.MainActivity
import com.example.digitalstudentassistant.databinding.FragmentLoginBinding
import com.example.digitalstudentassistant.ui.ProjectsActivity
import com.example.digitalstudentassistant.ui.UIState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.onEach

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        binding.emailEditText.addTextChangedListener {
////            binding.loginButton.isEnabled = binding.emailEditText.text.isNullOrBlank() &&
////                    binding.passwordEditText.text.isNullOrBlank()
////        }
////
////        binding.passwordEditText.addTextChangedListener {
////            binding.loginButton.isEnabled = binding.emailEditText.text.isNullOrBlank() &&
////                    binding.passwordEditText.text.isNullOrBlank()
////        }
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            //loginViewModel.login(email, password)
            val intent = Intent(requireContext(), ProjectsActivity::class.java)
            startActivity(intent)
        }

        binding.registerButton.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegistrationFragment3()
            findNavController().navigate(action)
        }
    }

    private fun subscribeLogin(){
        loginViewModel.publicLoginStateFlow.onEach {
            when(it){
                is UIState.Loading -> {
                    binding.loginProgressBar.isVisible = true
                }
                is UIState.Success -> {
                    binding.loginProgressBar.isVisible = false
                    val intent = Intent(requireContext(), ProjectsActivity::class.java)
                    startActivity(intent)
                }
                is UIState.Error -> {
                    binding.loginProgressBar.isVisible = false
                    Toast.makeText(requireContext(), it.data, Toast.LENGTH_LONG)
                }
            }
        }
    }




}