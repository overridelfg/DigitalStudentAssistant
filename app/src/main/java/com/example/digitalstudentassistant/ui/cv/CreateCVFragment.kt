package com.example.digitalstudentassistant.ui.cv

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.digitalstudentassistant.R
import com.example.digitalstudentassistant.databinding.FragmentCVEditBinding
import com.example.digitalstudentassistant.databinding.FragmentCreateCVBinding

class CreateCVFragment : Fragment() {

    private lateinit var binding : FragmentCreateCVBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateCVBinding.inflate(layoutInflater)
        return binding.root
    }


}