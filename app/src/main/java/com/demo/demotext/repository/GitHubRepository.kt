package com.demo.demotext.repository

import android.util.Log

import com.demo.demotext.util.NoInternetException
import com.demo.model.response.RedditMainResponseData

import java.net.SocketTimeoutException

import javax.inject.Singleton

/**
 * This is repository class ,from where api calls occurre
 * @author vikas kesharvani
 */
@Singleton
object GitHubRepository : BaseRepository() {

    /**
     * This is suspend function is use for Hot Data
     */
    @Throws(NoInternetException::class, SocketTimeoutException::class)
    suspend fun getHotData(): RedditMainResponseData {
        var fetchdata = RedditMainResponseData()
        try {
            fetchdata.data = safeApiCall(
                { mGitHubApiInterface.getHotData().await() },
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
     * This is suspend function is use for Hot Data
     */
    @Throws(NoInternetException::class, SocketTimeoutException::class)
    suspend fun getNewData(): RedditMainResponseData {
        var fetchdata = RedditMainResponseData()
        try {
            fetchdata.data = safeApiCall(
                { mGitHubApiInterface.getNewData().await() },
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