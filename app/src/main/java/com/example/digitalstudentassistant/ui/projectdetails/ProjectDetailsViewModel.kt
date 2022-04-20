package com.example.digitalstudentassistant.ui.projectdetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalstudentassistant.data.database.ProjectEntity
import com.example.digitalstudentassistant.data.database.ProjectsDatabase
import com.example.digitalstudentassistant.data.models.requests.UpdateProjectRequest
import com.example.digitalstudentassistant.data.models.responses.Likes
import com.example.digitalstudentassistant.data.models.responses.ProjectResponse
import com.example.digitalstudentassistant.data.models.responses.UserResponse
import com.example.digitalstudentassistant.data.models.responses.Views
import com.example.digitalstudentassistant.data.repositories.ProjectRepositoryImpl
import com.example.digitalstudentassistant.domain.OperationResult
import com.example.digitalstudentassistant.domain.repositories.ProjectRepository
import com.example.digitalstudentassistant.ui.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class ProjectDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val projectRepository: ProjectRepository
    private val projectUpdateStateFlow: MutableStateFlow<UIState<ProjectResponse, String?>> =
        MutableStateFlow(UIState.NothingDo)
    val projectUpdateStateFlowPublic = projectUpdateStateFlow.asStateFlow()

    private val addLikeStateFlow: MutableStateFlow<UIState<ProjectResponse, String?>> =
        MutableStateFlow(UIState.NothingDo)
    val addLikeStateFlowPublic = addLikeStateFlow.asStateFlow()

    private val removeLikeStateFlow: MutableStateFlow<UIState<ProjectResponse, String?>> =
        MutableStateFlow(UIState.NothingDo)
    val removeLikeStateFlowPublic = removeLikeStateFlow.asStateFlow()

    private val showLikesStateFlow: MutableStateFlow<UIState<Likes, String?>> =
        MutableStateFlow(UIState.NothingDo)
    val showLikesStateFlowPublic = showLikesStateFlow.asStateFlow()

    private val postViewStateFlow: MutableStateFlow<UIState<ProjectResponse, String?>> =
        MutableStateFlow(UIState.NothingDo)
    val postViewStateFlowPublic = postViewStateFlow.asStateFlow()

    private val showViewsStateFlow: MutableStateFlow<UIState<Views, String?>> =
        MutableStateFlow(UIState.NothingDo)
    val showViewsStateFlowPublic = showViewsStateFlow.asStateFlow()

    private val getWhoLikedStateFlow: MutableStateFlow<UIState<List<UserResponse>, String?>> =
        MutableStateFlow(UIState.NothingDo)
    val getWhoLikedStateFlowPublic = getWhoLikedStateFlow.asStateFlow()



    init {
        val projectsDao = ProjectsDatabase.create(application).projectsDao()
        projectRepository = ProjectRepositoryImpl(projectsDao, application.applicationContext)
    }

    fun getProjectFromDB(projectId: Int) : Flow<ProjectEntity> {
        return projectRepository.getProjectFromDB(projectId)
    }

    fun updateProject(idProject: String, updateProjectRequest: UpdateProjectRequest){
        viewModelScope.launch {
            projectUpdateStateFlow.value = UIState.Loading
            val result = projectRepository.updateProject(idProject, updateProjectRequest)
            projectUpdateStateFlow.value = when(result){
                is OperationResult.Success -> UIState.Success(result.data)
                is OperationResult.Error -> UIState.Error(result.data)
            }
        }
    }

    fun addLike(idProject: String){
        viewModelScope.launch {
            addLikeStateFlow.value = UIState.Loading
            val result = projectRepository.addLike(projectId = idProject)
            addLikeStateFlow.value = when(result){
                is OperationResult.Success -> UIState.Success(result.data)
                is OperationResult.Error -> UIState.Error(result.data)
            }
        }
    }

    fun removeLike(idProject: String){
        viewModelScope.launch {
            removeLikeStateFlow.value = UIState.Loading
            val result = projectRepository.removeLike(projectId = idProject)
            removeLikeStateFlow.value = when(result){
                is OperationResult.Success -> UIState.Success(result.data)
                is OperationResult.Error -> UIState.Error(result.data)
            }
        }
    }

    fun showLikes(idProject: String){
        viewModelScope.launch {
            showLikesStateFlow.value = UIState.Loading
            val result = projectRepository.showLikes(idProject)
            showLikesStateFlow.value = when(result){
                is OperationResult.Success -> UIState.Success(result.data)
                is OperationResult.Error -> UIState.Error(result.data)
            }
        }
    }

    fun postView(idProject: String){
        viewModelScope.launch {
            postViewStateFlow.value = UIState.Loading
            val result = projectRepository.postView(idProject)
            postViewStateFlow.value = when(result){
                is OperationResult.Success -> UIState.Success(result.data)
                is OperationResult.Error -> UIState.Error(result.data)
            }
        }
    }

    fun showView(idProject: String){
        viewModelScope.launch {
            showViewsStateFlow.value = UIState.Loading
            val result = projectRepository.getViews(idProject)
            showViewsStateFlow.value = when(result){
                is OperationResult.Success -> UIState.Success(result.data)
                is OperationResult.Error -> UIState.Error(result.data)
            }
        }
    }

    fun getWhoLikedProject(idProject: String){
        viewModelScope.launch {
            getWhoLikedStateFlow.value = UIState.Loading
            val result = projectRepository.getWhoLiked(idProject)
            getWhoLikedStateFlow.value = when(result){
                is OperationResult.Success -> UIState.Success(result.data)
                is OperationResult.Error -> UIState.Error(result.data)
            }
        }
    }
}