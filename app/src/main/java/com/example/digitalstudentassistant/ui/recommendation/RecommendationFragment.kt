package com.example.digitalstudentassistant.ui.recommendation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.digitalstudentassistant.databinding.FragmentRecommendationBinding
import com.example.digitalstudentassistant.ui.UIState
import com.example.digitalstudentassistant.ui.projectdetails.ProjectDetailsActivity
import com.example.digitalstudentassistant.ui.projects.ProjectsListAdapter
import com.example.digitalstudentassistant.ui.projects.ProjectsViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class RecommendationFragment : Fragment() {

    private lateinit var binding: FragmentRecommendationBinding
    private val projectsViewModel by viewModels<RecommendationViewModel>()
    private lateinit var projectsRecommendationListAdapter: ProjectRecommendationListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecommendationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpAdapter()
        subscribeRecommendationProjects()
        projectsViewModel.getRecommended()
    }

    private fun setUpAdapter(){
        projectsRecommendationListAdapter =ProjectRecommendationListAdapter(requireContext()){
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
        binding.projectsRecyclerView.adapter = projectsRecommendationListAdapter
        binding.projectsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun subscribeRecommendationProjects(){
        projectsViewModel.recommendedStateFlowPublic.onEach {
            when(it){
                is UIState.Loading -> {
                    binding.loadProjectsProgressBar.visibility = View.VISIBLE
                }
                is UIState.Success -> {
                    binding.loadProjectsProgressBar.visibility = View.INVISIBLE
                    projectsRecommendationListAdapter.projectsList.clear()
                    for (element in it.data) {
                        projectsRecommendationListAdapter.projectsList.add(
                            element
                        )
                    }
                    projectsRecommendationListAdapter.notifyDataSetChanged()
                }
                is UIState.Error -> {
                    binding.loadProjectsProgressBar.visibility = View.INVISIBLE
                }
            }
        }.launchIn(lifecycleScope)
    }
}