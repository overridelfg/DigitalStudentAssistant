package com.example.digitalstudentassistant.ui.auth.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.digitalstudentassistant.databinding.FragmentLoginBinding
import com.example.digitalstudentassistant.ui.ProjectsActivity
import com.example.digitalstudentassistant.ui.UIState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
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
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        setUpLoginButton()
        setUpRegisterButton()
        subscribeLogin()

    }

    @SuppressLint("ResourceAsColor")
    private fun validateEditText(): Boolean {
        if (binding.emailEditText.text.isNullOrBlank() ||
            binding.passwordEditText.text.isNullOrBlank()
        ) {
            Snackbar.make(
                requireView(),
                "Username и пароль не могут быть пустыми!",
                Snackbar.LENGTH_LONG
            ).setAction("OK") {

            }.show()
            return false
        }
        if (binding.passwordEditText.text!!.length < 4 || binding.passwordEditText.text!!.length > 30) {
            Snackbar.make(
                requireView(),
                "Пароль должен быть длиной от 4 до 30 символов!",
                Snackbar.LENGTH_LONG
            ).setAction("OK") {
            }.show()
            return false
        }
        return true
    }

    private fun subscribeLogin() {
        loginViewModel.publicLoginStateFlow.onEach {
            when (it) {
                is UIState.Loading -> {
                    binding.loginProgressBar.isVisible = true
                }
                is UIState.Success -> {
                    binding.loginProgressBar.isVisible = false
                    val intent = Intent(requireContext(), ProjectsActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                }
                is UIState.Error -> {
                    binding.loginProgressBar.isVisible = false
                    Toast.makeText(requireContext(), it.data, Toast.LENGTH_LONG).show()
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun setUpLoginButton() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            if (!validateEditText()) {
            } else {
                loginViewModel.login(email, password)
            }
        }
    }

    private fun setUpRegisterButton() {
        binding.registerButton.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegistrationFragment3()
            findNavController().navigate(action)
        }
    }


}