package com.demo.demotext.util

/**
 *This class is use for return the Response of retrofit result
 * @author vikas kesharvani
 */
sealed class Result<out T : Any>{
    data class success<out T : Any>(val data : T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}