package com.example.digitalstudentassistant.ui.profile.userProjects

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
import com.example.digitalstudentassistant.databinding.FragmentUserProjectsBinding
import com.example.digitalstudentassistant.ui.UIState
import com.example.digitalstudentassistant.ui.projectdetails.ProjectDetailsActivity
import com.example.digitalstudentassistant.ui.projects.ProjectsListAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class UserProjectsFragment : Fragment() {

    private lateinit var binding : FragmentUserProjectsBinding
    private val projectsViewModel by viewModels<UserProjectsViewModel>()
    private lateinit var projectsListAdapter: UserProjectsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserProjectsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpAdapter()
        subscribeGetUserProjects()
        projectsViewModel.getUserProjects()
    }

    private fun setUpAdapter(){
        projectsListAdapter = UserProjectsListAdapter(requireContext()){
            val intent = Intent(requireContext(), ProjectDetailsActivity::class.java)
            intent.putExtra("projectId", it.toString())
            startActivity(intent)
        }
        binding.userProjectsRecyclerView.adapter = projectsListAdapter
        binding.userProjectsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }


    private fun subscribeGetUserProjects(){
        projectsViewModel.userProjectsStateFlowPublic.onEach {
            when(it){
                is UIState.Loading -> {
                    binding.loadProjectsProgressBar.visibility = View.VISIBLE
                }
                is UIState.Success -> {
                    binding.loadProjectsProgressBar.visibility = View.INVISIBLE
                    projectsListAdapter.projectsList.clear()
                    if(it.data.isNotEmpty()){
                        binding.instructionsTextView.isVisible = false
                    }
                    for (element in it.data) {
                        projectsListAdapter.projectsList.add(
                            element
                        )
                    }
                    projectsListAdapter.notifyDataSetChanged()
                }
                is UIState.Error -> {
                    binding.loadProjectsProgressBar.visibility = View.INVISIBLE
                }
            }
        }.launchIn(lifecycleScope)
    }

}