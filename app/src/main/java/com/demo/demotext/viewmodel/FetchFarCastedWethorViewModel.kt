package com.demo.demotext.viewmodel

import android.app.Application
import app.rebel.com.bookmylibrary.viewmodel.DataWrapper
import com.appstreet.assignment.util.Constant
import com.appstreet.assignment.util.Coroutines
import com.bookmylibrary.librarian.viewmodel.ErrorModel
import com.demo.demotext.util.NoInternetException
import com.demo.demotext.util.SingleLiveEvent
import com.demo.model.custom.Temprature
import com.demo.model.response.ForcastWethorResponse
import com.demo.model.response.Weth
import java.net.SocketTimeoutException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class FetchFarCastedWethorViewModel : BaseAndroidViewModel {
    var app: Application? = null
    val toastMessage = SingleLiveEvent<String>()
    var response: SingleLiveEvent<DataWrapper<List<Temprature>>>? = null
    fun showToastMessage(msg: String) {
        toastMessage.value = msg
    }

    fun handelAction(data: DataWrapper<List<Temprature>>) {
        response!!.value = data
    }


    fun getForcastedWeather(lat:Double,lng:Double) {
        var data = DataWrapper<List<Temprature>>(null, null, true)
        handelAction(data)
//        val jsonConverter = JSONConverter<LibraryData>()
//        var jsonObj = jsonConverter.objectToJson(libraryData)
//        AppUtil.showLogMessage("e", "requestModel==", jsonObj)
        AppUtil.showLogMessage("e", "address==", repositires.hashCode().toString())

        Coroutines.main {
            try {
                val authResponse = repositires!!.getForcastedWeather(lat,lng)
                authResponse?.let {
                    AppUtil.showLogMessage("e", "response==", it.data.toString())
                    var list:List<Temprature> =perFormTaskHandelData(it.data)
                    response!!.value = DataWrapper(list, null, false)

                }
            } catch (e: NoInternetException) {
                var data = DataWrapper<List<Temprature>>(
                    null,
                    ErrorModel(true, Constant.NET_ERROR),
                    false
                )
                handelAction(data)
            } catch (e: SocketTimeoutException) {
                var data = DataWrapper<List<Temprature>>(
                    null,
                    ErrorModel(true, Constant.SLOW_NET_ERROR),
                    false
                )
                handelAction(data)

            }

        }
    }

    private fun perFormTaskHandelData(response: ForcastWethorResponse?): List<Temprature> {
        var listdata = ArrayList<Temprature>()
        response?.let {
            var list = it.list
            val c = Calendar.getInstance()
            var date: Date? = null
            try {
                val format = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                date = format.parse(list?.get(0)!!.dt_txt)
                c.time = date



                for (i in 1..5) {
                    var values = AppUtil.addDays(date, i)

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
//                    val sortedList = filteredList.sortedWith(
//                        compareBy({ it.main!!.temp_min },
//                            { it.main!!.temp_max })
//                    )
                    var minList=filteredList.sortedWith(
                        compareBy({ it.main!!.temp_min }
                       )
                    )
                    var maxList=filteredList.sortedWith(
                        compareByDescending({ it.main!!.temp_max }
                        )
                    )
//                    for ( datai in minList){
//                        AppUtil.showLogMessage("e", "mintemp", datai.main!!.temp_min.toString())
//                    }
//                    for ( data2 in maxList){
//                        AppUtil.showLogMessage("e", "maxtemp", data2.main!!.temp_max.toString())
//                    }
//                    AppUtil.showLogMessage("e", "minList", minList.toString())
//                    AppUtil.showLogMessage("e", "maxList", maxList.toString())
                    value.max_min =
                        "${maxList[0].main!!.temp_max!!.roundToInt()}°C/ ${minList[0].main!!.temp_min!!.roundToInt()}°C"


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

                AppUtil.showLogMessage("e", "list", listdata.toString())

            } catch (e: ParseException) {
                e.printStackTrace()
            } finally {
                return listdata
            }

        }
        return listdata


    }


    constructor(application: Application) : super(application) {
        app = application
        if (response == null)
            response = SingleLiveEvent<DataWrapper<List<Temprature>>>()

    }


}