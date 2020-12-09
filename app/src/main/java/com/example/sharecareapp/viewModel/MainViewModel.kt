package com.example.sharecareapp.viewModel

import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.sharecareapp.model.UserItem
import com.example.sharecareapp.repository.UserDataSource
import kotlinx.coroutines.Dispatchers


class MainViewModel : ViewModel() {

    private var usersLiveData: LiveData<PagedList<UserItem>>
     var isListView : Boolean


    init {
        isListView = true
        val config = PagedList.Config.Builder()
                .setPageSize(30)
                .setEnablePlaceholders(false)
                .build()
        usersLiveData = initializedPagedListBuilder(config).build()
    }

    fun getUserList(): LiveData<PagedList<UserItem>> = usersLiveData

    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<Int, UserItem> {

        val dataSourceFactory = object : DataSource.Factory<Int, UserItem>() {
            override fun create(): DataSource<Int, UserItem> {
                return UserDataSource(Dispatchers.IO)
            }
        }
        return LivePagedListBuilder(dataSourceFactory, config)
    }

}