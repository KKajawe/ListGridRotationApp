package com.example.sharecareapp.viewModel

import androidx.lifecycle.*
import com.example.sharecareapp.repository.UserDetailRepository
import com.example.sharecareapp.model.RepositoryList
import com.example.sharecareapp.model.User
import com.example.sharecareapp.model.UserItemX
import com.example.sharecareapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Response


class UserDetailViewModel(val userDetailRepository: UserDetailRepository) : ViewModel() {
    private var userDetailItem = MutableLiveData<UserItemX>()
    private var repoDataList = MutableLiveData<RepositoryList>()
    val userItemDetail: LiveData<UserItemX> get() = userDetailItem
    val userRepoDataList: LiveData<RepositoryList> get() = repoDataList

    fun setUserItemLivedata(userItem: UserItemX) {
        userDetailItem.value = userItem
    }

    fun setRepoListLivedata(repoList: RepositoryList) {
        repoDataList.value = repoList
    }


    fun getUserDetail() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = userDetailRepository.getUserDetail()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getUserRepos() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = userDetailRepository.getUserRepos()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

}