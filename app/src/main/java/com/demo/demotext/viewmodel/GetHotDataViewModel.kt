package com.demo.demotext.viewmodel

import android.app.Application
import app.rebel.com.bookmylibrary.viewmodel.DataWrapper
import com.appstreet.assignment.util.Constant
import com.appstreet.assignment.util.Coroutines
import com.bookmylibrary.librarian.viewmodel.ErrorModel
import com.demo.demotext.util.NoInternetException
import com.demo.demotext.util.SingleLiveEvent
import com.demo.model.response.RedditMainResponse
import java.net.SocketTimeoutException

class GetHotDataViewModel : BaseAndroidViewModel {
    var app: Application? = null
     val toastMessage = SingleLiveEvent<String>()
     var response: SingleLiveEvent<DataWrapper<RedditMainResponse>>? = null
    fun showToastMessage(msg: String) {
        toastMessage.value = msg
    }

    fun handelAction(data: DataWrapper<RedditMainResponse>) {
        response!!.value = data
    }


    fun getHotData() {
        var data = DataWrapper<RedditMainResponse>(null, null, true)
        handelAction(data)
//        val jsonConverter = JSONConverter<LibraryData>()
//        var jsonObj = jsonConverter.objectToJson(libraryData)
//        AppUtil.showLogMessage("e", "requestModel==", jsonObj)
        AppUtil.showLogMessage("e", "address==", repositires.hashCode().toString())

        Coroutines.main {
            try {
                val authResponse = repositires!!.getHotData()
                authResponse?.let {
                    AppUtil.showLogMessage("e", "response==", it.data.toString())
                    response!!.value = DataWrapper(it.data, null, false)

                }
            } catch (e: NoInternetException) {
                var data = DataWrapper<RedditMainResponse>(
                    null,
                    ErrorModel(true, Constant.NET_ERROR),
                    true
                )
                handelAction(data)
            } catch (e: SocketTimeoutException) {
                var data = DataWrapper<RedditMainResponse>(
                    null,
                    ErrorModel(true, Constant.SLOW_NET_ERROR),
                    true
                )
                handelAction(data)

            }

        }
    }
    fun getNewData() {
        var data = DataWrapper<RedditMainResponse>(null, null, true)
        handelAction(data)
//        val jsonConverter = JSONConverter<LibraryData>()
//        var jsonObj = jsonConverter.objectToJson(libraryData)
//        AppUtil.showLogMessage("e", "requestModel==", jsonObj)
        AppUtil.showLogMessage("e", "address==", repositires.hashCode().toString())

        Coroutines.main {
            try {
                val authResponse = repositires!!.getNewData()
                authResponse?.let {
                    AppUtil.showLogMessage("e", "response==", it.data.toString())
                    response!!.value = DataWrapper(it.data, null, false)

                }
            } catch (e: NoInternetException) {
                var data = DataWrapper<RedditMainResponse>(
                    null,
                    ErrorModel(true, Constant.NET_ERROR),
                    true
                )
                handelAction(data)
            } catch (e: SocketTimeoutException) {
                var data = DataWrapper<RedditMainResponse>(
                    null,
                    ErrorModel(true, Constant.SLOW_NET_ERROR),
                    true
                )
                handelAction(data)

            }

        }
    }

    constructor(application: Application) : super(application) {
        app = application
        if (response == null)
            response = SingleLiveEvent<DataWrapper<RedditMainResponse>>()

    }


}