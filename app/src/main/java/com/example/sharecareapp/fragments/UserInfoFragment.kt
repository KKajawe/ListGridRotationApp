package com.example.sharecareapp.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.sharecareapp.R
import com.example.sharecareapp.model.UserItemX
import com.example.sharecareapp.viewModel.UserDetailViewModel
import kotlinx.android.synthetic.main.fragment_user_info.*


class UserInfoFragment : Fragment() {

    private val fragViewModel by activityViewModels<UserDetailViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_user_info, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragViewModel.userItemDetail.observe(viewLifecycleOwner, Observer { userItem -> updateUI(userItem) })
    }

    private fun updateUI(userItem: UserItemX) {
        followers.text = checknullString(userItem.followers.toString())
        following.text = checknullString(userItem.following.toString())
        company.text =checknullString(userItem.company.toString())
        location.text = checknullString(userItem.location.toString())
        email.text = checknullString(userItem.email.toString())
        blog.text = checknullString(userItem.blog.toString())
        twt_username.text = checknullString(userItem.twitterUsername.toString())
    }

    private fun checknullString(str: String) : String{
        return if(str.equals("null"))
            "-"
        else
            str
    }
}