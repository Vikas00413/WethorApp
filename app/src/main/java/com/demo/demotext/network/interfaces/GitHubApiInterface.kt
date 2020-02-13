package com.demo.demotext.network.interfaces



import com.demo.model.response.CurrentTemperatureResponse
import com.demo.model.response.ForcastWethorResponse

import kotlinx.coroutines.Deferred

import retrofit2.Response
import retrofit2.http.*


/**
 * This is api interface where actual api method call
 * @author vikas kesharvani
 */
interface GitHubApiInterface {


//
//    @POST("libraryadminregistrationapp")
//    fun libRegistration(
//        @Body data: SighnUpData
//    ): Deferred<Response<LibraryAdminResponse>>
//http://api.openweathermap.org/data/2.5/weather?q=Noida&APPID=e39a45e7c5961f0f6573c0774f4f1732&units=metric
//http://api.openweathermap.org/data/2.5/forecast?q=Delhi&APPID=e39a45e7c5961f0f6573c0774f4f1732&Metric=Celsius


    @GET("weather?")
    fun getCurrentWethor(
        @Query("q") q: String, @Query("APPID") APPID: String ,@Query("units") units: String
    ): Deferred<Response<CurrentTemperatureResponse>>

    @GET("forecast?")
    fun getForcastedWether(
        @Query("q") q: String, @Query("APPID") APPID: String ,@Query("Metric") Metric: String
    ): Deferred<Response<ForcastWethorResponse>>

}

