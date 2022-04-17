package com.example.digitalstudentassistant.ui.courses

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.digitalstudentassistant.R
import com.example.digitalstudentassistant.databinding.FragmentCoursesBinding
import com.example.digitalstudentassistant.ui.UIState
import com.example.digitalstudentassistant.ui.projectdetails.ProjectDetailsActivity
import com.example.digitalstudentassistant.ui.projects.ProjectsListAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class CoursesFragment : Fragment() {

    private lateinit var binding: FragmentCoursesBinding
    private val coursesViewModel by viewModels<CoursesViewModel>()
    private lateinit var coursesListAdapter: CoursesListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCoursesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpAdapter()
        subscribeCourses()
        coursesViewModel.getAllCourses()
    }

    private fun setUpAdapter(){
        coursesListAdapter = CoursesListAdapter(requireContext()){
            val intent = Intent(requireContext(), ProjectDetailsActivity::class.java)
            startActivity(intent)
        }
        binding.coursesRecyclerView.adapter = coursesListAdapter
        binding.coursesRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun subscribeCourses(){
        coursesViewModel.publicGetCursesStateFlow.onEach {
            when(it){
                is UIState.Loading ->{
                    binding.loadCoursesProgressBar.isVisible = true
                }
                is UIState.Success -> {
                    binding.loadCoursesProgressBar.isVisible = false
                    coursesListAdapter.coursesList.clear()
                    for (element in it.data) {
                        coursesListAdapter.coursesList.add(
                            element
                        )
                    }
                    coursesListAdapter.notifyDataSetChanged()
                }
                is UIState.Error ->{
                    Snackbar.make(
                        requireView(),
                        it.data.toString(),
                        Snackbar.LENGTH_LONG
                    ).setAction("OK") {
                    }.show()
                }
            }
        }.launchIn(lifecycleScope)
    }

}