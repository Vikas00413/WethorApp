package com.demo.demotext.viewmodel

import android.app.Application
import app.rebel.com.bookmylibrary.viewmodel.DataWrapper
import com.appstreet.assignment.util.Constant
import com.appstreet.assignment.util.Coroutines
import com.bookmylibrary.librarian.viewmodel.ErrorModel
import com.demo.demotext.util.NoInternetException
import com.demo.demotext.util.SingleLiveEvent
import com.demo.model.custom.CurrentTemp
import com.demo.model.response.CurrentTemperatureResponse
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class FetchCurrentWethorViewModel : BaseAndroidViewModel {
    var app: Application? = null
    val toastMessage = SingleLiveEvent<String>()
    var response: SingleLiveEvent<DataWrapper<CurrentTemp>>? = null
    fun showToastMessage(msg: String) {
        toastMessage.value = msg
    }

    fun handelAction(data: DataWrapper<CurrentTemp>) {
        response!!.value = data
    }


    fun getCurrentWeather(lat:Double,lng:Double) {
        var data = DataWrapper<CurrentTemp>(null, null, true)
        handelAction(data)
//        val jsonConverter = JSONConverter<LibraryData>()
//        var jsonObj = jsonConverter.objectToJson(libraryData)
//        AppUtil.showLogMessage("e", "requestModel==", jsonObj)
        AppUtil.showLogMessage("e", "address==", repositires.hashCode().toString())

        Coroutines.main {
            try {
                val authResponse = repositires!!.getCurrentWeather(lat,lng)
                authResponse?.let {
                    AppUtil.showLogMessage("e", "response==", it.data.toString())
                    var currenTemprature:CurrentTemp =performTaskHandelData(it.data)

                    response!!.value = DataWrapper(currenTemprature, null, false)

                }
            }
           catch (e: NoInternetException) {
                var data = DataWrapper<CurrentTemp>(
                    null,
                    ErrorModel(true, Constant.NET_ERROR),
                    false
                )
                handelAction(data)
            } catch (e: SocketTimeoutException) {
                var data = DataWrapper<CurrentTemp>(
                    null,
                    ErrorModel(true, Constant.SLOW_NET_ERROR),
                    false
                )
                handelAction(data)

            }

        }
    }

    private fun performTaskHandelData(response: CurrentTemperatureResponse?): CurrentTemp {
        var currentTemp : CurrentTemp?=null
        response?.let {
            var main = response.main
            var city=""
            response.name?.let {
                city=it
            }
            main?.let {
                var temp = "${it.temp!!.roundToInt()}°C"
                var max_min =
                    "${it.temp_max!!.roundToInt()}°C/ ${it.temp_min!!.roundToInt()}°C"

                val date =
                    SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
                        .format(Date())
                 currentTemp = CurrentTemp(temp, city, max_min, date)

            }
        }
        return currentTemp!!
    }


    constructor(application: Application) : super(application) {
        app = application
        if (response == null)
            response = SingleLiveEvent<DataWrapper<CurrentTemp>>()

    }


}