package com.example.sharecareapp.repository

import android.util.Log
import androidx.paging.ItemKeyedDataSource
import com.example.sharecareapp.model.User
import com.example.sharecareapp.model.UserItem
import com.example.sharecareapp.network.ApiService
import com.example.sharecareapp.network.RetrofitBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response
import kotlin.coroutines.CoroutineContext


class UserDataSource(coroutineContext: CoroutineContext) : ItemKeyedDataSource<Int, UserItem>() {
    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job)
    private val apiService = RetrofitBuilder.getRetrofit().create(ApiService::class.java)
    override fun getKey(item: UserItem): Int {
        return item.id!!
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<UserItem>) {
        scope.launch {
            try {
                val response: Response<User> = apiService.getUsers(0, params.requestedLoadSize)
                when {
                    response.isSuccessful -> {
                        val userList = response.body() as User
                        callback.onResult(userList)
                    }
                    else -> {
                        Log.e("UserDataSource", "Error in initial load!")
                    }

                }

            } catch (exception: Exception) {
                Log.e("UserDataSource", "Exception : Failed to fetch data!")
            }

        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<UserItem>) {
        scope.launch {
            try {
                val response: Response<User> = apiService.getUsers(params.key - 1, params.requestedLoadSize)
                when {
                    response.isSuccessful -> {
                        val userList = response.body() as User
                        callback.onResult(userList)
                    }
                    else -> {
                        Log.e("UserDataSource", "Error in After load!")
                    }
                }

            } catch (exception: Exception) {
                Log.e("UserDataSource", "Exception : Failed to fetch data!")
            }

        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<UserItem>) {

    }

}