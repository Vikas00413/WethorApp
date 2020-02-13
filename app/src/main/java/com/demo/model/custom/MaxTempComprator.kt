package com.demo.model.custom

import com.demo.model.response.Main

class MaxTempComprator :Comparator<Main> {
    override fun compare(p0: Main?, p1: Main?): Int {
        return p0!!.temp_max!!.toInt() - p1!!.temp_max!!.toInt()
    }
}