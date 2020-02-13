package com.demo.demotext.network.interfaces



import com.demo.model.response.RedditMainResponse
import kotlinx.coroutines.Deferred

import retrofit2.Response
import retrofit2.http.*


/**
 * This is api interface where actual api method call
 * @author vikas kesharvani
 */
interface GitHubApiInterface {

//    @GET("repositories?")
//    fun getDeveloper(
//        @Query("language") language: String, @Query("since") since: String
//    ): Deferred<Response<List<Repository>>>
//
//
//    @POST("libraryadminregistrationapp")
//    fun libRegistration(
//        @Body data: SighnUpData
//    ): Deferred<Response<LibraryAdminResponse>>

    @GET("hot.json")
    fun getHotData(
    ): Deferred<Response<RedditMainResponse>>

    @GET("new.json")
    fun getNewData(
    ): Deferred<Response<RedditMainResponse>>

}

