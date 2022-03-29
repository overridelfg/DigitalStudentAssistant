package com.example.digitalstudentassistant.ui.projects

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.digitalstudentassistant.databinding.FragmentProjectsMainBinding
import com.example.digitalstudentassistant.ui.projectdetails.ProjectDetailsActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProjectsMainFragment : Fragment() {

    private lateinit var binding: FragmentProjectsMainBinding
    private val projectsViewModel by viewModels<ProjectsViewModel>()
    private lateinit var projectsListAdapter: ProjectsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProjectsMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpAdapter()
        lifecycle.coroutineScope.launch {
            projectsViewModel.loadAllProjectsFromDB().collect {
                projectsListAdapter.projectsList.clear()
                for (element in it) {
                    projectsListAdapter.projectsList.add(
                        element
                    )
                }
                projectsListAdapter.notifyDataSetChanged()
            }
        }

        binding.createProjectButton.setOnClickListener {
            val action = ProjectsMainFragmentDirections.actionProjectsMainFragmentToProjectFragment()
            findNavController().navigate(action)
        }
    }

    private fun setUpAdapter(){
        projectsListAdapter = ProjectsListAdapter(requireContext()){
            val intent = Intent(requireContext(), ProjectDetailsActivity::class.java)
            intent.putExtra("projectId", it.toString())
            startActivity(intent)
        }
        binding.projectsRecyclerView.adapter = projectsListAdapter
        binding.projectsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }
}