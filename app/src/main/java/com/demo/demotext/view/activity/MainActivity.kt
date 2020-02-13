package com.demo.demotext.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.MutableLiveData
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

import com.demo.demotext.R
import com.demo.demotext.adapter.FragmentPagerAdapter

import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var search = MutableLiveData<CharSequence>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragmentAdapter = FragmentPagerAdapter(supportFragmentManager)
        viewpager_main.adapter = fragmentAdapter
        tabs_main.setupWithViewPager(viewpager_main)
        tabs_main.getTabAt(0)?.select()
        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(char: Editable?) {
            }

            override fun beforeTextChanged(char: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(char: CharSequence?, p1: Int, p2: Int, p3: Int) {

                search.value = char

            }

        })

        viewpager_main.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {

                et_search.setText("")
            }

        })

    }
}
