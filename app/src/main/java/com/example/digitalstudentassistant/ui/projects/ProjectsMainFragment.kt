package com.example.digitalstudentassistant.ui.projects

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.digitalstudentassistant.R
import com.example.digitalstudentassistant.databinding.FragmentProjectsMainBinding
import com.example.digitalstudentassistant.ui.ProjectsActivity
import com.example.digitalstudentassistant.ui.UIState
import com.example.digitalstudentassistant.ui.projectdetails.ProjectDetailsActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ProjectsMainFragment : Fragment() {

    private lateinit var binding: FragmentProjectsMainBinding
    private val projectsViewModel by viewModels<ProjectsViewModel>()
    private lateinit var projectsListAdapter: ProjectsListAdapter
    private var isSorted = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProjectsMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpAdapter()
        setupFloatingButton()
        subscribeShowAllProjects()
        subscribeShowSearchProjects()
        subscribeSortedProjects()
        setUpSortButton()
        projectsViewModel.showAllProjects()
        setUpSearchButton()
        setUpEditTextChanged()
    }

    private fun setUpSearchButton(){
        binding.searchProjectButton.setOnClickListener {
            projectsViewModel.getProjectSearch(binding.editTextSearch.text.toString())
        }
    }

    private fun setUpSortButton(){
        binding.sortProjectButton.setOnClickListener {
            if(isSorted){
                projectsViewModel.showAllProjects()
                binding.sortProjectButton.setBackgroundResource(R.drawable.ic_baseline_sort_24)
                isSorted = false
            }else{
                projectsViewModel.getSortedProjects()
                binding.sortProjectButton.setBackgroundResource(R.drawable.ic_baseline_sort_selected)
                isSorted = true
            }
        }
    }

    private fun setUpEditTextChanged(){
        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(binding.editTextSearch.text.isNullOrEmpty()){
                    projectsViewModel.showAllProjects()
                }
            }
        })
    }

    private fun setUpAdapter(){
        projectsListAdapter = ProjectsListAdapter(requireContext()){
            val intent = Intent(requireContext(), ProjectDetailsActivity::class.java)
            intent.putExtra("projectId", it.id)
            intent.putExtra("title", it.title)
            intent.putExtra("description", it.description)
            intent.putExtra("creatorId", it.creatorId)
            intent.putExtra("communication", it.communication)
            var tags = ""
            for(i in it.tags.indices){
                if(i == it.tags.size - 1){
                    tags += it.tags[i].name
                }else{
                    tags += it.tags[i].name + ":"
                }
            }
            intent.putExtra("tags", tags)
            startActivity(intent)
        }
        binding.projectsRecyclerView.adapter = projectsListAdapter
        binding.projectsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun subscribeShowAllProjects(){
        projectsViewModel.projectsStateFlowPublic.onEach {
            when(it){
                is UIState.Loading -> {
                    binding.loadProjectsProgressBar.visibility = View.VISIBLE
                }
                is UIState.Success -> {
                    binding.loadProjectsProgressBar.visibility = View.INVISIBLE
                    projectsListAdapter.projectsList.clear()
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

    private fun subscribeSortedProjects(){
        projectsViewModel.projectsSortByLikesStateFlowPublic.onEach {
            when(it){
                is UIState.Loading -> {
                    binding.loadProjectsProgressBar.visibility = View.VISIBLE
                }
                is UIState.Success -> {
                    binding.loadProjectsProgressBar.visibility = View.INVISIBLE
                    projectsListAdapter.projectsList.clear()
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

    private fun subscribeShowSearchProjects(){
        projectsViewModel.projectsSearchStateFlowPublic.onEach {
            when(it){
                is UIState.Loading -> {
                    binding.loadProjectsProgressBar.visibility = View.VISIBLE
                }
                is UIState.Success -> {
                    binding.loadProjectsProgressBar.visibility = View.INVISIBLE
                    projectsListAdapter.projectsList.clear()
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

    private fun db(){
//        lifecycle.coroutineScope.launch {
//            projectsViewModel.loadAllProjectsFromDB().collect {
//                projectsListAdapter.projectsList.clear()
//                for (element in it) {
//                    projectsListAdapter.projectsList.add(
//                        element
//                    )
//                }
//                projectsListAdapter.notifyDataSetChanged()
//            }
//        }
    }
    private fun setupFloatingButton(){
        binding.createProjectButton.setOnClickListener {
            val action = ProjectsMainFragmentDirections.actionProjectsMainFragmentToProjectFragment()
            findNavController().navigate(action)
        }
    }
}