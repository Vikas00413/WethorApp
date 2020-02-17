package com.demo.demotext.view.activity


import AppUtil
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.appstreet.assignment.util.Constant
import com.demo.demotext.R
import com.demo.demotext.adapter.TemperatreAdapter
import com.demo.demotext.databinding.ActivityMainBinding
import com.demo.demotext.viewmodel.FetchCurrentWethorViewModel
import com.demo.demotext.viewmodel.FetchFarCastedWethorViewModel
import com.demo.model.custom.CurrentTemp
import com.demo.model.custom.Temprature

import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_main.*

import android.location.LocationListener
import com.appstreet.assignment.util.toast
import com.demo.demotext.MyApplication
import com.demo.demotext.util.PermissionUtil
import javax.inject.Inject

/**
 * this is main activity where current and future weather is displyed
 */
class MainActivity : AppCompatActivity(), View.OnClickListener, LocationListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModelCurrent: FetchCurrentWethorViewModel
    private lateinit var viewModelFarCasted: FetchFarCastedWethorViewModel
    private var mLocationManager: LocationManager? = null
    private lateinit var mLocationRequest: LocationRequest
    private val TAG = MainActivity::class.java!!.getSimpleName()
    private lateinit var adapter: TemperatreAdapter
    @Inject
    lateinit var mPermissonUtil: PermissionUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        AppUtil.playGif(iv_loader)
        MyApplication.mNetComponent.inject(this)
        viewModelCurrent = ViewModelProviders.of(this).get(FetchCurrentWethorViewModel::class.java)

        btn_retry.setOnClickListener(this)
        adapter = TemperatreAdapter(this)
        binding.adapter = adapter
        viewModelCurrent.response!!.observe(this, Observer { datawrapper ->
            if (datawrapper != null) {
                if (datawrapper.isShowLoader) {
                    iv_loader.visibility = View.VISIBLE

                } else {
                    iv_loader.visibility = View.GONE
                    if (datawrapper.data != null) {
                        rl_failure.visibility = View.GONE
                        rl_success.visibility = View.VISIBLE
                        var response: CurrentTemp = datawrapper.data!!
                          response?.let {
                              binding.data = it
                          }


                    } else {
                        rl_failure.visibility = View.VISIBLE
                        rl_success.visibility = View.GONE

                    }
                }



            } else {
                rl_failure.visibility = View.VISIBLE
                rl_success.visibility = View.GONE
            }


        })

        viewModelFarCasted =
            ViewModelProviders.of(this).get(FetchFarCastedWethorViewModel::class.java)



        viewModelFarCasted.response!!.observe(this, Observer { datawrapper ->
            if (datawrapper != null) {
                if (datawrapper.isShowLoader) {
                    iv_loader.visibility = View.VISIBLE

                } else {
                    iv_loader.visibility = View.GONE
                    if (datawrapper.data != null) {
                        var list: List<Temprature> = datawrapper.data!!
                        if(!list.isNullOrEmpty()){
                            val bottomUp: Animation = AnimationUtils.loadAnimation(
                                this,
                                R.anim.bottom_up
                            )

                            rl_recycler.startAnimation(bottomUp)
                            rl_recycler.visibility = View.VISIBLE
                            adapter.addData(list)
                            AppUtil.showLogMessage("e", "list", list.toString())
                        }


                    }

                }


            }


        })
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.btn_retry -> {
               checkLocationPermisson()
            }
        }
    }


    private fun displayLocationSettingsRequest(context: Context) {
        val googleApiClient = GoogleApiClient.Builder(context)
            .addApi(LocationServices.API).build()
        googleApiClient.connect()

        mLocationRequest = LocationRequest.create()
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)

        mLocationRequest.setInterval(1000)
        mLocationRequest.setFastestInterval(1000)

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)

        val builder = LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest)
        builder.setAlwaysShow(true)

        val result =
            LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
        result.setResultCallback { result ->
            val status = result.status
            when (status.statusCode) {
                LocationSettingsStatusCodes.CANCELED -> AppUtil.showLogMessage(
                    "e",
                    TAG,
                    "Cancelled"
                )
                LocationSettingsStatusCodes.SUCCESS -> {

                    AppUtil.showLogMessage(
                        "e",
                        TAG,
                        "All location settings are satisfied."
                    )
                    checkLocationPermisson()
                }
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {

                    AppUtil.showLogMessage(
                        "e",
                        TAG,
                        "Location settings are not satisfied. Show the user a dialog to upgrade location settings "
                    )
                    try {

                        status.startResolutionForResult(
                            this@MainActivity,
                            Constant.REQUEST_CHECK_SETTINGS
                        )
                    } catch (e: IntentSender.SendIntentException) {
                        Log.i(TAG, "PendingIntent unable to execute request.")
                    }

                }
                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE ->

                    AppUtil.showLogMessage(
                        "e",
                        TAG,
                        "Location settings are inadequate, and cannot be fixed here. Dialog not created."
                    )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        displayLocationSettingsRequest(this)
    }

    protected override fun onDestroy() {
        super.onDestroy()
        if (mLocationManager != null)
            mLocationManager!!.removeUpdates(this)
    }

    override fun onLocationChanged(p0: Location?) {
    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
    }

    override fun onProviderEnabled(p0: String?) {
    }

    override fun onProviderDisabled(p0: String?) {
    }


    private fun checkLocationPermisson() {

        if (!mPermissonUtil.checkLocationPermisson(this)) {
            mPermissonUtil.requestLocationPermission(this)

        } else {
            getLocation()

        }
    }

    /**
     * this is permisson handler override method,based on action your perform on permsson dialoge
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {

            PermissionUtil.REQUEST_LOCATION -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation()
                // startActivity(new Intent(LoginActivity.this, ConfirmActivity.class));
            } else {
                toast(Constant.DENIED_LOCATION_PERMISSON_MESSAGE)


            }
        }
    }

    private fun getLocation() {

        mLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val criteria = Criteria()
        criteria.accuracy = Criteria.ACCURACY_FINE
        val provider = mLocationManager!!.getBestProvider(criteria, false)
        Log.e(TAG, "provider==$provider")
        //            if (provider != null) {
        getLastKnownLocation()

    }

    private fun getLastKnownLocation() {
        val mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        try {
            mFusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    // GPS location can be null if GPS is switched off
                    if (location != null) {
                        Log.e(TAG, "onSuccess==$location")
                        var lat = location.latitude
                        var lng = location.longitude

                        viewModelCurrent.getCurrentWeather(lat,lng)
                        viewModelFarCasted.getForcastedWeather(lat,lng)
                    }
                }
                .addOnFailureListener { e ->
                    toast(getString(R.string.errMsgNoLocation))
                    e.printStackTrace()
                }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }


    }
}
