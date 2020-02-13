package com.demo.demotext.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.demo.demotext.R
import com.demo.demotext.adapter.HotAdapter
import com.demo.demotext.databinding.FragmentNewBinding
import com.demo.demotext.view.activity.MainActivity
import com.demo.demotext.viewmodel.GetHotDataViewModel
import com.demo.model.response.RedditMainResponse
import kotlinx.android.synthetic.main.fragment_hot.*

class NewFragment : Fragment() {
    private lateinit var adapter: HotAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding: FragmentNewBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_new, container, false)

        adapter = HotAdapter(activity!!)
        binding.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var viewModel: GetHotDataViewModel =
            ViewModelProviders.of(this).get(GetHotDataViewModel::class.java)
        viewModel.getNewData()
        (activity as MainActivity).search.observe(this , Observer {
            adapter.filter.filter(it)
        })
        viewModel.response!!.observe(this, Observer { datawrapper ->
            if (datawrapper != null) {
                if (datawrapper.isShowLoader) {
                    progressBar.visibility = View.VISIBLE

                } else {
                    progressBar.visibility = View.GONE

                }
                if (datawrapper.data != null) {
                    var response: RedditMainResponse = datawrapper.data!!
                    if (response.data!!.children!!.isNotEmpty()) {
                        adapter.addData(response.data!!.children!!)
                        AppUtil.showLogMessage("e", "size", "" + response.data!!.children!!.size)

                    } else {

                    }

                } else {

//                    datawrapper.error?.let {
//                        Toast.makeText(activity, it.error, Toast.LENGTH_LONG).show()
//                    }!!
                }


            }


        })
    }
}