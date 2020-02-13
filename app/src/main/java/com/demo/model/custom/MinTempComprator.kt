package com.demo.model.custom

import com.demo.model.response.Main

class MinTempComprator :Comparator<Main> {
    override fun compare(p0: Main?, p1: Main?): Int {
        return p0!!.temp_min!!.toInt() - p1!!.temp_min!!.toInt()
    }
}