package com.idn.muslimmediaapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.idn.muslimmediaapp.fragment.AboutAlQuranFragment
import com.idn.muslimmediaapp.fragment.AlJazeeraFragment
import com.idn.muslimmediaapp.fragment.CommonFragment
import com.idn.muslimmediaapp.fragment.WarningFragment

class SectionPagerAdapter(fa: FragmentActivity)
    : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> CommonFragment()
            1 -> AboutAlQuranFragment()
            2 -> AlJazeeraFragment()
            3 -> WarningFragment()
            else -> AlJazeeraFragment()
        }
    }
}