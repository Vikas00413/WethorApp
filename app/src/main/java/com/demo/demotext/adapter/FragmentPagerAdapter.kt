package com.demo.demotext.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.demo.demotext.view.fragment.HotFragment
import com.demo.demotext.view.fragment.NewFragment


class FragmentPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {


    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                var bundle = Bundle()
               // bundle.putString(Constant.GO_KEY, goPage)
                var fragment = HotFragment()
                fragment.arguments = bundle
                fragment
            }
            else -> {
                var bundle = Bundle()
              //  bundle.putString(Constant.GO_KEY, goPage)
                var fragment =
                    NewFragment()
                fragment.arguments = bundle
                fragment
            }


        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Hot"
            else -> "New"

        }
    }
}