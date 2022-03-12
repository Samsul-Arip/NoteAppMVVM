package com.samsul.challengegdsc.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter


class SectionPagerAdapter(
    private val fragmentList: List<Fragment>,
    fm: FragmentManager,
    lifecycle: Lifecycle,
    bundle: Bundle
) : FragmentStateAdapter(fm, lifecycle){

    private var fragmentBundle = bundle

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment {
        fragmentList[position].arguments = this.fragmentBundle

        return fragmentList[position]
    }
}