package com.demo.demotext.repository

import android.util.Log
import com.appstreet.assignment.util.Constant

import com.demo.demotext.util.NoInternetException
import com.demo.model.response.CurrentTemperatureResponseData
import com.demo.model.response.ForcastWethorResponseData


import java.net.SocketTimeoutException

import javax.inject.Singleton

/**
 * This is repository class ,from where api calls occurre
 * @author vikas kesharvani
 */
@Singleton
object GitHubRepository : BaseRepository() {


    /**
     * This is suspend function is use for get current weather
     */
    @Throws(NoInternetException::class, SocketTimeoutException::class)
    suspend fun getCurrentWeather(lat:Double,lng:Double): CurrentTemperatureResponseData {
        var fetchdata = CurrentTemperatureResponseData()
        try {
            fetchdata.data = safeApiCall(
                {
                    mGitHubApiInterface.getCurrentWethor(lat,lng, Constant.APP_ID, Constant.UNIT)
                        .await()
                },
                "Data Not Found"
            )
            return fetchdata
        } catch (e: NoInternetException) {
            Log.e("Connectivity_error", "No internet connection", e)
            throw NoInternetException("No internet connection")
        } catch (e: SocketTimeoutException) {
            Log.e("Connectivity_error", "Slow Internet Connection", e)
            throw SocketTimeoutException("Slow Internet Connection")

        }
    }

    /**
     * This is suspend function is use for get current weather
     */
    @Throws(NoInternetException::class, SocketTimeoutException::class)
    suspend fun getForcastedWeather(lat: Double,lng: Double): ForcastWethorResponseData {
        var fetchdata = ForcastWethorResponseData()
        try {
            fetchdata.data = safeApiCall(
                {
                    mGitHubApiInterface.getForcastedWether(lat,lng, Constant.APP_ID, Constant.UNIT)
                        .await()
                },
                "Data Not Found"
            )
            return fetchdata
        } catch (e: NoInternetException) {
            Log.e("Connectivity_error", "No internet connection", e)
            throw NoInternetException("No internet connection")
        } catch (e: SocketTimeoutException) {
            Log.e("Connectivity_error", "Slow Internet Connection", e)
            throw SocketTimeoutException("Slow Internet Connection")

        }
    }

}