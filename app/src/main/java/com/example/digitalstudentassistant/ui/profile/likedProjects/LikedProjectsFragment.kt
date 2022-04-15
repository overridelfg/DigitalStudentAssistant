package com.example.digitalstudentassistant.ui.profile.likedProjects

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
import com.example.digitalstudentassistant.databinding.FragmentLikedProjectsBinding
import com.example.digitalstudentassistant.ui.UIState
import com.example.digitalstudentassistant.ui.profile.userProjects.UserProjectsListAdapter
import com.example.digitalstudentassistant.ui.projectdetails.ProjectDetailsActivity
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class LikedProjectsFragment : Fragment() {
    
    private lateinit var binding: FragmentLikedProjectsBinding
    private val viewModel by viewModels<LikedProjectViewModel>()
    private lateinit var projectsListAdapter: UserProjectsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLikedProjectsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpAdapter()
        subscribeGetUserProjects()
        viewModel.getLiked()
    }

    private fun setUpAdapter(){
        projectsListAdapter = UserProjectsListAdapter(requireContext()){
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
        binding.likedProjectsRecyclerView.adapter = projectsListAdapter
        binding.likedProjectsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }


    private fun subscribeGetUserProjects(){
        viewModel.likedProjectsStateFlowPublic.onEach {
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