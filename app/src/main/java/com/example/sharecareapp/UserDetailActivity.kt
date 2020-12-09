package com.example.sharecareapp

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.sharecareapp.adapters.ViewPagerAdapter
import com.example.sharecareapp.factory.ViewModelFactory
import com.example.sharecareapp.fragments.UserInfoFragment
import com.example.sharecareapp.fragments.UserRepositoriesFragments
import com.example.sharecareapp.model.UserItem
import com.example.sharecareapp.model.UserItemX
import com.example.sharecareapp.network.ApiService
import com.example.sharecareapp.network.RetrofitBuilder
import com.example.sharecareapp.util.Status
import com.example.sharecareapp.viewModel.UserDetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.android.synthetic.main.activity_detail.*


class UserDetailActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
//view Model setup
        val user: UserItem = intent.getSerializableExtra("userItem") as UserItem
        setUpViewModel(user.login, user.id)
        setUpObserver()

 //Collapsing toolbar setup
        Glide.with(header.context)
                .load(user.avatarUrl)
                .into(header)
        collapse_toolbar.setContentScrimColor(
                ContextCompat.getColor(
                        this,
                        R.color.colorPrimary
                )
        )
        collapse_toolbar.setStatusBarScrimColor(
                ContextCompat.getColor(
                        this,
                        R.color.colorPrimaryDark
                )
        )


//viewPager and tab setup
        this.setupViewPager(viewpager)
        tab_layout.addTab(this.tab_layout.newTab().setText("Tab 1"))
        tab_layout.addTab(tab_layout.newTab().setText("Tab 2"))
        tab_layout.setupWithViewPager(viewpager)

        tab_layout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewpager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun setUpObserver() {
        viewModel.getUserDetail().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        //   resource.data?.let { user -> retrieveList(user) }
                       viewModel.setUserItemLivedata( resource.data?.body()!!)
                        if (supportActionBar != null) supportActionBar?.title = resource.data.body()?.name
                        supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {

                    }
                }
            }
        })
    }

    private fun setUpViewModel(username: String?, id: Int?) {
        viewModel = ViewModelProviders.of(
                this,
                ViewModelFactory(username, id, (RetrofitBuilder.getRetrofit().create(ApiService::class.java)))
        ).get(UserDetailViewModel::class.java)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        viewPager.offscreenPageLimit = 2
        val adapter = ViewPagerAdapter(
                supportFragmentManager,
                tab_layout.tabCount
        )
        adapter.addFrag(
                UserInfoFragment(), "Overview"
        )
        adapter.addFrag(
                UserRepositoriesFragments(), "Repository"
        )
        viewPager.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        lateinit var viewModel: UserDetailViewModel
        val userDetailActivity = UserDetailActivity()
    }

}