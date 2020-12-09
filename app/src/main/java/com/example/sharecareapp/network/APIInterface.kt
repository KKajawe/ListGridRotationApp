package com.example.sharecareapp.network

import com.example.sharecareapp.model.RepositoryList
import com.example.sharecareapp.model.User
import com.example.sharecareapp.model.UserItemX
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun getUsers(@Query("since") last_Item_id: Int,
                         @Query("per_page") count_To_display: Int): Response<User>

    @GET("user/{id}")
    suspend fun getUserDetail(@Path("id")  id:Long): Response<UserItemX>

    @GET("users/{username}/repos")
    suspend fun getUserRepos(@Path("username")  name:String): Response<RepositoryList>

}