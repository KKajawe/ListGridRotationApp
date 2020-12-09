package com.example.sharecareapp.repository

import com.example.sharecareapp.network.ApiService

class UserDetailRepository (val username: String?,val id: Int?,private val apiService: ApiService) {

    suspend fun getUserDetail() = id?.toLong()?.let { apiService.getUserDetail(it) }
    suspend fun getUserRepos() = username?.let { apiService.getUserRepos(it) }
}