package com.demo.demotext.view.activity


import AppUtil
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.demo.demotext.R
import com.demo.demotext.adapter.TemperatreAdapter
import com.demo.demotext.databinding.ActivityMainBinding
import com.demo.demotext.viewmodel.FetchCurrentWethorViewModel
import com.demo.demotext.viewmodel.FetchFarCastedWethorViewModel
import com.demo.model.custom.CurrentTemp
import com.demo.model.custom.Temprature
import com.demo.model.response.CurrentTemperatureResponse
import com.demo.model.response.ForcastWethorResponse
import com.demo.model.response.Weth
import kotlinx.android.synthetic.main.activity_main.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    var city = "Noida"
    private lateinit var viewModelCurrent: FetchCurrentWethorViewModel
    private lateinit var viewModelFarCasted: FetchFarCastedWethorViewModel
    private lateinit var adapter: TemperatreAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //AppUtil.playGif(iv_loader)
        viewModelCurrent = ViewModelProviders.of(this).get(FetchCurrentWethorViewModel::class.java)

        viewModelCurrent.getCurrentWeather(city)
        adapter = TemperatreAdapter(this)
        binding.adapter = adapter
        viewModelCurrent.response!!.observe(this, Observer { datawrapper ->
            if (datawrapper != null) {
                if (datawrapper.isShowLoader) {
                    iv_loader.visibility = View.VISIBLE

                } else {
                    iv_loader.visibility = View.GONE
                }
                if (datawrapper.data != null) {
                    rl_failure.visibility = View.GONE
                    rl_success.visibility = View.VISIBLE
                    var response: CurrentTemperatureResponse = datawrapper.data!!
                    response?.let {
                        var main = response.main
                        main?.let {
                            var temp = "${it.temp!!.roundToInt()}°C"
                            var max_min =
                                "${it.temp_min!!.roundToInt()}°C/ ${it.temp_max!!.roundToInt()}°C"

                            val date =
                                SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
                                    .format(Date())
                            var currentTemp = CurrentTemp(temp, city, max_min, date)
                            binding.data = currentTemp
                        }
                    }


                } else {
                    rl_failure.visibility = View.VISIBLE
                    rl_success.visibility = View.GONE
//                    datawrapper.error?.let {
//                        Toast.makeText(this, it.error, Toast.LENGTH_LONG).show()
//                    }!!
                }


            } else {
                rl_failure.visibility = View.VISIBLE
                rl_success.visibility = View.GONE
            }


        })
        viewModelFarCasted =
            ViewModelProviders.of(this).get(FetchFarCastedWethorViewModel::class.java)

        viewModelFarCasted.getForcastedWeather(city)

        viewModelFarCasted.response!!.observe(this, Observer { datawrapper ->
            if (datawrapper != null) {
                if (datawrapper.isShowLoader) {
                    iv_loader.visibility = View.VISIBLE

                } else {
                    iv_loader.visibility = View.GONE
                }
                if (datawrapper.data != null) {
                    var response: ForcastWethorResponse = datawrapper.data!!
                    response?.let {
                        var list = it.list
                        val c = Calendar.getInstance()
                        var date: Date? = null
                        try {
                            //2020-02-13 06:00:00
                            val format = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                            date = format.parse(list?.get(0)!!.dt_txt)
                            c.time = date


                            var listdata = ArrayList<Temprature>()
                            for (i in 1..5) {
                                var values = AppUtil.addDays(date, i)
//                                AppUtil.showLogMessage("e", "added date", AppUtil.addDays(date,1).toString())
                                var day = values.toString().split("\\s".toRegex())[0]
                                val finalString = format.format(values)
                                var temprature =
                                    Temprature(date = finalString, day = day, max_min = "")
                                listdata.add(temprature)

                            }
                            AppUtil.showLogMessage("e", "list", listdata.toString())
                            for (value in listdata) {
                                var filteredList: List<Weth> = list.filter { data ->
                                    data.dt_txt!!.split("\\s".toRegex())[0] == value.date!!.split("\\s".toRegex())[0]
                                }

                                // AppUtil.showLogMessage("e", "filter list", filteredList.toString())
                                val sortedList = filteredList.sortedWith(
                                    compareBy({ it.main!!.temp_min },
                                        { it.main!!.temp_max })
                                )
                                value.max_min =
                                    "${sortedList[0].main!!.temp_min!!.roundToInt()}°C/ ${sortedList[sortedList.size - 1].main!!.temp_max!!.roundToInt()}°C"
                                for (mydata in sortedList) {

//                                    AppUtil.showLogMessage("e", "min", ""+mydata.main!!.temp_min)
//                                    AppUtil.showLogMessage("e", "max", ""+mydata.main!!.temp_max)
                                }
                                //  value.date= value.date!!.split("\\s".toRegex())[0]

                                val format2 =
                                    SimpleDateFormat("dd-MMM-yyyy")
                                val mydata = format.parse(value.date)
                                value.date = format2.format(mydata!!).toString()
                                //println(format2.format(date))
                                when (value.day) {
                                    "Sun" -> {
                                        value.day = "Sunday"
                                    }
                                    "Mon" -> {
                                        value.day = "Monday"
                                    }
                                    "Tue" -> {
                                        value.day = "Tuesday"
                                    }
                                    "Wed" -> {
                                        value.day = "Wednesday"
                                    }
                                    "Thu" -> {
                                        value.day = "Thursday"
                                    }
                                    "Fri" -> {
                                        value.day = "Friday"
                                    }
                                    "Sat" -> {
                                        value.day = "Saturday"
                                    }

                                }
                            }
                            val bottomUp: Animation = AnimationUtils.loadAnimation(
                                this,
                                R.anim.bottom_up
                            )

                            rl_recycler.startAnimation(bottomUp)
                            rl_recycler.visibility = View.VISIBLE
                            adapter.addData(listdata)
                            AppUtil.showLogMessage("e", "list", listdata.toString())

                        } catch (e: ParseException) {
                            e.printStackTrace()
                        }

                        for (data in list!!) {

                        }
                    }


                } else {

//                    datawrapper.error?.let {
//                        Toast.makeText(this, it.error, Toast.LENGTH_LONG).show()
//                    }!!
                }


            }


        })
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.btn_retry -> {
                viewModelCurrent.getCurrentWeather(city)
                viewModelFarCasted.getForcastedWeather(city)
            }
        }
    }
}
