package com.example.digitalstudentassistant.ui.auth.register

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.digitalstudentassistant.data.UserPrefsStorage
import com.example.digitalstudentassistant.databinding.FragmentRegistrationBinding
import com.example.digitalstudentassistant.domain.models.User
import com.example.digitalstudentassistant.ui.ProjectsActivity
import com.example.digitalstudentassistant.ui.UIState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private val registerViewModel by activityViewModels<RegistrationViewModel>()
    private lateinit var userPrefsStorage: UserPrefsStorage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userPrefsStorage = UserPrefsStorage(requireContext())
        subscribeRegister()
        setUpButton()
    }

    private fun subscribeRegister() {
        registerViewModel.publicRegisterStateFlow.onEach {
            when (it) {
                is UIState.Loading -> {
                    binding.registerProgressBar.visibility = View.VISIBLE
                }
                is UIState.Success -> {
                    binding.registerProgressBar.visibility = View.INVISIBLE
                    val intent = Intent(requireContext(), ProjectsActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                }
                is UIState.Error -> {
                    binding.registerProgressBar.visibility = View.INVISIBLE
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun setUpButton() {
        binding.registrationButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val nickname = binding.nicknameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val telegramUrl = binding.telegramUrlEditText.text.toString()
            val firstname = binding.firstnameEditText.text.toString()
            val lastname = binding.lastnameEditText.text.toString()
            val phone = binding.phoneEditText.text.toString()

            if (setUpRegistrationButtonValidation()) {
                registerViewModel.register(
                    email = email,
                    nickname = nickname,
                    telegram = telegramUrl,
                    firstname = firstname,
                    lastname = lastname,
                    phoneNumber = phone,
                    password = password
                )
            }
        }
    }

    private fun setUpRegistrationButtonValidation(): Boolean {
        binding.apply {
            if (emailEditText.text.isNullOrBlank() &&
                nicknameEditText.text.isNullOrBlank() &&
                passwordEditText.text.isNullOrBlank() &&
                firstnameEditText.text.isNullOrBlank() &&
                lastnameEditText.text.isNullOrBlank() &&
                phoneEditText.text.isNullOrBlank() &&
                telegramUrlEditText.text.isNullOrBlank()
            ) {
                Snackbar.make(requireView(), "Неправильно введены данные!", Snackbar.LENGTH_LONG)
                    .setAction("OK") {
                    }.show()
                return false
            } else if (!Patterns.EMAIL_ADDRESS.matcher(emailEditText.text.toString()).matches()) {
                Snackbar.make(requireView(), "Неверный email!", Snackbar.LENGTH_LONG)
                    .setAction("OK") {
                    }.show()
                return false
            } else if (passwordEditText.text!!.length < 4 || passwordEditText.text!!.length > 30) {
                Snackbar.make(
                    requireView(),
                    "Пароль должен быть длиной от 4 до 30 символов!",
                    Snackbar.LENGTH_LONG
                ).setAction("OK") {
                }.show()
                return false
            } else if(!phoneEditText.text.toString().matches("^[0-9]{10,13}$".toRegex())){
                Snackbar.make(
                    requireView(),
                    "Неверный формат номера телефона",
                    Snackbar.LENGTH_LONG
                ).setAction("OK") {
                }.show()
            }
            return true
        }
    }
}