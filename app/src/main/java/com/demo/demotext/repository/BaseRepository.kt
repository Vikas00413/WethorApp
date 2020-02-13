package com.demo.demotext.repository


import android.util.Log
import com.demo.demotext.network.interfaces.GitHubApiInterface
import com.demo.demotext.util.Result
import com.demo.demotext.MyApplication
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

/**
 * This class is use in GithubRepository class,all network calling classes this will be implemented
 * @author vikas kesharvani
 */
abstract class BaseRepository {
    @Inject
    lateinit var mGitHubApiInterface: GitHubApiInterface

    constructor() {
        MyApplication.mGitHubComponent.inject(this)
    }

    /**
     * this method is use for api call
     */
    suspend fun <T : Any> safeApiCall(
        call: suspend () -> Response<T>,
        errorMessage: String
    ): T? {
        val result = safeApiResult(call, errorMessage)
        var data: T? = null
        when (result) {
            is Result.success ->
                data = result.data
            is Result.Error ->
                Log.d("1.DataRepository", "$errorMessage & Exception - ${result.exception}")
        }
        return data
    }

    private suspend fun <T : Any> safeApiResult(
        call: suspend () -> Response<T>,
        errorMessage: String
    )
            : Result<T> {
        val response = call.invoke()
        if (response.raw().networkResponse() != null) {
            Log.e("BaseRepository", "onResponse: response is from NETWORK...")
            Log.e("Response", response.raw().toString())
        } else if (response.raw().cacheResponse() != null && response.raw().networkResponse() == null) {
            Log.e("BaseRepository", "onResponse: response is from CACHE...")
           // Log.e("Response", response.raw().toString())

        }
        if (response.isSuccessful) {
            AppUtil.showLogMessage("e", "response==>", response.body().toString())
            return Result.success(response.body()!!)
        }
        return Result.success(response.body()!!)



    }
}