package com.example.sharecareapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(val manager: FragmentManager, tab_count: Int) : FragmentPagerAdapter(manager, tab_count) {
    private val  mFragmentList :  ArrayList<Fragment> =  ArrayList<Fragment>()
    private val  mFragmentTitleList :ArrayList<String> =  ArrayList<String>()
    override fun getCount(): Int = mFragmentList.size

    override fun getItem(position: Int): Fragment = mFragmentList[position]

    fun addFrag(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitleList[position]
    }
}