package com.appstreet.assignment.util

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

/**
 * This class is use for defining Extension function
 * @author vikas kesharvani
 */
/**
 * This method is use for showing toast message
 */
fun Context.toast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_LONG ).show()
}

/**
 * This method is use for show a view
 */
fun View.show(){
    visibility = View.VISIBLE
}

/**
 * This method is use for hide a view
 */
fun View.hide(){
    visibility = View.GONE
}

/**
 * This meythod is use show snackbar
 */
fun View.snackbar(message: String){
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.setAction("Ok") {
            snackbar.dismiss()
        }
    }.show()
}
