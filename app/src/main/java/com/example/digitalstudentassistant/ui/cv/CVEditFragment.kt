package com.example.digitalstudentassistant.ui.cv

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.digitalstudentassistant.R
import com.example.digitalstudentassistant.databinding.FragmentCVEditBinding


class CVEditFragment : Fragment() {

    private lateinit var binding : FragmentCVEditBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCVEditBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

}