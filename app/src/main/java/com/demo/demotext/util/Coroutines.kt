package com.appstreet.assignment.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Main Coroutines Class where all coroutines are initiated
 * @author vikas kesharvani
 */

object Coroutines {
    /**
     * This Method is use run coroutines on Main Thread
     */
    fun main(work: suspend (() -> Unit)) =
        CoroutineScope(Dispatchers.Main).launch {
            work()

        }

    /**
     * This Method is use run coroutines on Worker Thread
     * use it for IO or Network Operation
     */

    fun io(work: suspend (() -> Unit)) =
        CoroutineScope(Dispatchers.IO).launch {
            work()
        }

}