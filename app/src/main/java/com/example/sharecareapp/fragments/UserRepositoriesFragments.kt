package com.example.sharecareapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.sharecareapp.R
import com.example.sharecareapp.adapters.UserRepositoryAdapter
import com.example.sharecareapp.model.RepositoryList
import com.example.sharecareapp.util.Status.*
import com.example.sharecareapp.viewModel.UserDetailViewModel
import kotlinx.android.synthetic.main.fragment_repo_list.*
import retrofit2.Response

class UserRepositoriesFragments : Fragment() {

    private lateinit var adapterUser: UserRepositoryAdapter
    private val repo_fragViewModel by activityViewModels<UserDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?  = inflater.inflate(R.layout.fragment_repo_list, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterUser = UserRepositoryAdapter(RepositoryList())
        repository_list_view?.adapter = adapterUser
        repo_fragViewModel.getUserRepos().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    SUCCESS -> {
                        Log.d("UserInfoFragment", it.data.toString())
                        resource.data?.let { userRepoResponse -> updateUI(userRepoResponse) }
                    }
                    ERROR -> {
                        Log.d("UserInfoFragment", it.message!!)
                    }
                    LOADING -> {
                        Log.d("UserInfoFragment", "Loading")
                    }
                }
            }
        })
    }

    private fun updateUI(userRepoResponse: Response<RepositoryList>) {
        adapterUser.apply {
            val repoList: RepositoryList = userRepoResponse.body()!!
            updateRepoList(repoList)
            notifyDataSetChanged()
        }
    }

}