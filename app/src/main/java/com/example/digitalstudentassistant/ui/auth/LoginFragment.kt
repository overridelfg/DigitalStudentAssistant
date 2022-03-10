package com.example.digitalstudentassistant.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.digitalstudentassistant.databinding.FragmentLoginBinding
import com.example.digitalstudentassistant.ui.ProjectsActivity

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

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
        binding.loginButton.setOnClickListener {
            val intent = Intent(requireContext(), ProjectsActivity::class.java)
            startActivity(intent)
        }

        binding.registerButton.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegistrationFragment3()
            findNavController().navigate(action)
        }
    }



}