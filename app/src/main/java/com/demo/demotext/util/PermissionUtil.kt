package com.demo.demotext.util

import AppUtil
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.appstreet.assignment.util.Constant


/**
 * Created by vikas on 16/02/2017.
 * This class use for handel run time permissions
 */

object  PermissionUtil {


        val REQUEST_CONTACTS = 0
        val REQUEST_LOCATION = 1
        val REQUEST_PHONE = 2
        val REQUEST_SMS = 3
        val REQUEST_GET_ACCOUNTS = 4
        val REQUEST_CAMERA = 5
        val REQUEST_STORAGE = 6
        val REQUEST_CALL_LOG = 7

        private val PERMISSIONS_LOCATION = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        private val PERMISSIONS_PHONE =
            arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE)
        private val PERMISSIONS_SMS = arrayOf(
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.SEND_SMS
        )
        private val PERMISSIONS_CAMERA = arrayOf(Manifest.permission.CAMERA)
        private val PERMISSIONS_STORAGE = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
        )
        private val PERMISSON_CALL_LOGS =
            arrayOf(Manifest.permission.READ_CALL_LOG, Manifest.permission.WRITE_CALL_LOG)
        private val PERMISSIONS_CONTACT = arrayOf(Manifest.permission.GET_ACCOUNTS)
        private var instance: PermissionUtil? = null



        fun checkLocationPermisson(nContext: Context): Boolean {
            val resultFineLocation =
                ContextCompat.checkSelfPermission(nContext, Manifest.permission.ACCESS_FINE_LOCATION)
            val resultCourseLocation =
                ContextCompat.checkSelfPermission(nContext, Manifest.permission.ACCESS_COARSE_LOCATION)
            return resultFineLocation == PackageManager.PERMISSION_GRANTED && resultCourseLocation == PackageManager.PERMISSION_GRANTED
        }



        fun requestLocationPermission(activity: AppCompatActivity) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) && ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            ) {

                //  Toast.makeText(mContext, "GPS permission allows us to access location data. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
                AppUtil.showSettingsAlert(activity, Constant.LOCATION_PERMISSON_MESSAGE)

            } else {

                ActivityCompat.requestPermissions(activity, PERMISSIONS_LOCATION, REQUEST_LOCATION)
            }
        }

    }


