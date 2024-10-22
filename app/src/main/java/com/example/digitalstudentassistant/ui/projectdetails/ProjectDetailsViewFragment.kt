package com.example.digitalstudentassistant.ui.projectdetails

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.digitalstudentassistant.R
import com.example.digitalstudentassistant.data.UserPrefsStorage
import com.example.digitalstudentassistant.databinding.FragmentProjectDetailsViewBinding
import com.example.digitalstudentassistant.domain.models.Project
import com.example.digitalstudentassistant.ui.UIState
import com.example.digitalstudentassistant.ui.profile.likedProjects.LikedProjectViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProjectDetailsViewFragment : Fragment() {

    private lateinit var binding: FragmentProjectDetailsViewBinding
    private lateinit var projectDetailsViewModel : ProjectDetailsViewModel
    private val likedViewModel by viewModels<LikedProjectViewModel>()
    private lateinit var userPrefsStorage: UserPrefsStorage
    private lateinit var usersListAdapter: UsersListAdapter
    private lateinit var project: Project

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProjectDetailsViewBinding.inflate(layoutInflater)
        projectDetailsViewModel = ViewModelProvider(this).get(ProjectDetailsViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpChipLike()
        subscribeAddLike()
        subscribeRemoveLike()
        subscribeShowLikes()
        subscribeShowViews()
        setUpAdapter()
        subscribePostView()
        subscribeGetWhoLiked()
        subscribeGetLikedProjects()
        userPrefsStorage = UserPrefsStorage(requireContext())
        val projectId = arguments?.getString("projectId")!!
        val title = arguments?.getString("title")!!
        val description = arguments?.getString("description")!!
        val communication = arguments?.getString("communication")!!
        val creatorId = arguments?.getString("creatorId")!!
        val tags = arguments?.getString("tags")!!.split(":")
        val tagsString = arguments?.getString("tags")!!
        for (i in tags){
            addChip(i)
        }
        likedViewModel.getLiked()
        projectDetailsViewModel.showLikes(projectId)
        binding.projectNameTextView.text = title
        binding.descriptionTextView.text = description
        binding.communicationTextView.text = communication
        projectDetailsViewModel.postView(projectId)
        projectDetailsViewModel.showView(projectId)
        projectDetailsViewModel.getWhoLikedProject(projectId)
        binding.editButton.setOnClickListener {
            setUpEditButton(projectId, title, description, communication, tagsString, creatorId)
        }

        if(arguments?.getString("creatorId")!! == userPrefsStorage.loadUserFromPrefs()!!.id){
            binding.editButton.isVisible = true
            binding.likeChip.isClickable = false
        } else{
            binding.editButton.isVisible = false
            binding.likeChip.isClickable = true
        }
    }

    private fun addChip(text: String){
        val chip = Chip(requireContext())
        chip.text = text
        chip.setBackgroundResource(R.color.gray)
        chip.isCloseIconVisible = true
        binding.chipGroup.addView(chip)
    }


    private fun setUpAdapter(){
        usersListAdapter = UsersListAdapter{
            val intent = Intent(requireContext(), UserDetailsActivity::class.java)
            intent.putExtra("userId", it.id)
            intent.putExtra("email", it.email)
            intent.putExtra("nickname", it.nickname)
            intent.putExtra("firstName", it.firstName)
            intent.putExtra("lastName", it.lastName)
            intent.putExtra("telegram", it.telegram)
            startActivity(intent)
        }

        binding.likedUsers.adapter = usersListAdapter
        binding.likedUsers.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun setUpChipLike(){
        binding.likeChip.setOnClickListener {
            binding.likeChip.isSelected = ! binding.likeChip.isSelected
            if(binding.likeChip.isSelected){
                projectDetailsViewModel.addLike(arguments?.getString("projectId")!!)
                binding.likeChip.text = (binding.likeChip.text.toString().toInt() + 1).toString()
                binding.likeChip.setChipIconResource(R.drawable.ic_thumb_up)
            }else{
                projectDetailsViewModel.removeLike(arguments?.getString("projectId")!!)
                binding.likeChip.setChipIconResource(R.drawable.ic_outline_thumb_up)
                binding.likeChip.text = (binding.likeChip.text.toString().toInt() - 1).toString()
            }
        }
    }

    private fun subscribeAddLike(){
        projectDetailsViewModel.addLikeStateFlowPublic.onEach {
            when(it) {
                is UIState.Loading -> {
                }
                is UIState.Success -> {
                    Snackbar.make(requireView(), "liked", Snackbar.LENGTH_LONG)
                        .setAction("OK") {
                        }.show()
                }
                is UIState.Error -> {
                    Snackbar.make(requireView(), it.data.toString(), Snackbar.LENGTH_LONG)
                        .setAction("OK") {
                        }.show()
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun subscribeRemoveLike(){
        projectDetailsViewModel.removeLikeStateFlowPublic.onEach {
            when(it) {
                is UIState.Loading -> {
                }
                is UIState.Success -> {
                    Snackbar.make(requireView(), "unliked", Snackbar.LENGTH_LONG)
                        .setAction("OK") {
                        }.show()
                }
                is UIState.Error -> {
                    Snackbar.make(requireView(), it.data.toString(), Snackbar.LENGTH_LONG)
                        .setAction("OK") {
                        }.show()
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun subscribeShowLikes(){
        projectDetailsViewModel.showLikesStateFlowPublic.onEach {
            when(it) {
                is UIState.Loading -> {
                }
                is UIState.Success -> {
                    binding.likeChip.text = it.data.int.toString()
                }
                is UIState.Error -> {
                    Snackbar.make(requireView(), it.data.toString(), Snackbar.LENGTH_LONG)
                        .setAction("OK") {
                        }.show()
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun subscribePostView(){
        projectDetailsViewModel.postViewStateFlowPublic.onEach {
            when(it) {
                is UIState.Loading -> {

                }
                is UIState.Success -> {

                }
                is UIState.Error -> {

                }
            }
        }.launchIn(lifecycleScope)
    }

    @SuppressLint("SetTextI18n")
    private fun subscribeShowViews(){
        projectDetailsViewModel.showViewsStateFlowPublic.onEach {
            when(it) {
                is UIState.Loading -> {

                }
                is UIState.Success -> {
                    binding.statusTextView.text = "Просмотров: " + it.data.int.toString()
                }
                is UIState.Error -> {

                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun subscribeGetLikedProjects(){
        likedViewModel.likedProjectsStateFlowPublic.onEach {
            when(it){
                is UIState.Loading -> {
                }
                is UIState.Success -> {
                    for (project in it.data){
                        if(project.id == arguments?.getString("projectId")!!){
                            binding.likeChip.isSelected = true
                            binding.likeChip.setChipIconResource(R.drawable.ic_thumb_up)
                        }
                    }
                }
                is UIState.Error -> {
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun subscribeGetWhoLiked(){
        projectDetailsViewModel.getWhoLikedStateFlowPublic.onEach {
            when(it){
                is UIState.Loading -> {
                }
                is UIState.Success -> {
                    usersListAdapter.usersList.clear()
                    for (element in it.data) {
                        usersListAdapter.usersList.add(
                            element
                        )
                    }
                    usersListAdapter.notifyDataSetChanged()
                }
                is UIState.Error -> {
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun setUpEditButton(projectId: String,title: String, description: String, communication: String, tagsString: String, creatorId: String){
            val action = ProjectDetailsViewFragmentDirections.actionProjectDetailsViewFragmentToProjectDetailsEditingFragment(
                projectId,
                title,
                description,
                communication,
                tagsString,
                creatorId)
            findNavController().navigate(action)
    }




}