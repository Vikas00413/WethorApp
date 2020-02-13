package com.demo.model.custom

import com.demo.model.response.Main



class TempChainedComparator:Comparator<Main> {
    private var listComparators: List<Comparator<Main>>? = null

    constructor(vararg  comparators:List<Comparator<Main>>):super(){
        this.listComparators = comparators as List<Comparator<Main>>
    }


    override fun compare(p0: Main?, p1: Main?): Int {
        for (comparator in listComparators!!) {
            val result = comparator.compare(p0, p1)
            if (result != 0) {
                return result
            }
        }
        return 0
    }
}