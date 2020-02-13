package com.rebeledutech.taktiiteacherregistration.util

import com.google.gson.Gson

/**
 * Created by vikas on 11/25/2015.
 */
class JSONConverter<T> {


    fun objectToJson(t: T): String {
        val gson = Gson()
        return gson.toJson(t)
    }

    fun jsonToObject(data: String, t: Class<T>): T {
        val gson = Gson()
        val data1 = gson.fromJson(data, t)
        println(data1)
        return data1
    }
}
