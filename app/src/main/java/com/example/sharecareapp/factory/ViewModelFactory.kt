package com.example.sharecareapp.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sharecareapp.network.ApiService
import com.example.sharecareapp.repository.UserDetailRepository
import com.example.sharecareapp.viewModel.UserDetailViewModel

class ViewModelFactory(val username: String?,val id: Int?, private val apiService: ApiService) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserDetailViewModel::class.java)) {
            return UserDetailViewModel(UserDetailRepository(username,id,apiService)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}