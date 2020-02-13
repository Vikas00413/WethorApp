

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PorterDuff
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import android.text.Html
import android.text.TextUtils
import android.text.format.DateUtils
import android.util.Base64
import android.util.Log
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


import com.demo.demotext.R
import com.google.android.material.textfield.TextInputEditText
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AppUtil {
    class LocationConstants {
        companion object {
            val SUCCESS_RESULT = 0

            val FAILURE_RESULT = 1

            val PACKAGE_NAME = "app.rebel.com.bookmylibrary"

            val RECEIVER = "$PACKAGE_NAME.RECEIVER"

            val RESULT_DATA_KEY = "$PACKAGE_NAME.RESULT_DATA_KEY"

            val LOCATION_DATA_EXTRA = "$PACKAGE_NAME.LOCATION_DATA_EXTRA"

            val LOCATION_DATA_AREA = "$PACKAGE_NAME.LOCATION_DATA_AREA"
            val LOCATION_DATA_CITY = "$PACKAGE_NAME.LOCATION_DATA_CITY"
            val LOCATION_DATA_STREET = "$PACKAGE_NAME.LOCATION_DATA_STREET"
            val LOCATION_DATA_PINCODE =
                "$PACKAGE_NAME.LOCATION_DATA_PINCODE"
            val LOCATION_DATA_STATE =
                "$PACKAGE_NAME.LOCATION_DATA_STATE"
        }

    }

    companion object {


        fun printKeyHash(context: Activity): String? {
            val packageInfo: PackageInfo
            var key: String? = null
            try {
                //getting application package name, as defined in manifest
                val packageName = context.applicationContext.packageName

                //Retriving package info
                packageInfo = context.packageManager.getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNATURES
                )

                Log.e("LibraryPackage Name=", context.applicationContext.packageName)

                for (signature in packageInfo.signatures) {
                    val md = MessageDigest.getInstance("SHA")
                    md.update(signature.toByteArray())
                    key = String(Base64.encode(md.digest(), 0))

                    // String key = new String(Base64.encodeBytes(md.digest()));
                    Log.e("Key Hash=", key)
                }
            } catch (e1: PackageManager.NameNotFoundException) {
                Log.e("Name not found", e1.toString())
            } catch (e: NoSuchAlgorithmException) {
                Log.e("No such an algorithm", e.toString())
            } catch (e: Exception) {
                Log.e("Exception", e.toString())
            }

            return key
        }

        fun genrateRandomNumber(): Int {
            val r1 = Random(9)
            val r = Random(System.currentTimeMillis())
            return ((r1.nextInt(1) + r.nextInt(2)) * 1000000 + r.nextInt(1000000))
        }

        fun showLogMessage(where: String, messageTag: String, message: String?) {
            if (message != null)
                when (where) {
                    "e" -> Log.e(messageTag, message)
                    "w" -> Log.w(messageTag, message)
                    "v" -> Log.v(messageTag, message)
                    "i" -> Log.i(messageTag, message)
                    "d" -> Log.d(messageTag, message)
                    else -> {
                        Log.e(messageTag, message)
                    }
                }
        }

        @SuppressLint("MissingPermission")
        fun isConnectedToInternet(context: Context): Boolean {
            val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
            return networkInfo?.isConnected == true
        }

        fun isLocationEnabled(context: Context): Boolean {
            val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
            var gps_enabled = false
            var network_enabled = false


            try {
                gps_enabled = lm!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
            } catch (ex: Exception) {
            }

            try {
                network_enabled = lm!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            } catch (ex: Exception) {
            }

            if (!gps_enabled && !network_enabled) {
                // notify user

                return false
            }
            return true
        }


        fun setUpToolBar(toolbar: androidx.appcompat.widget.Toolbar?, context: Context, title: String) {

            if (toolbar != null) {
                toolbar!!.setLogo(null)
                toolbar!!.setTitle(Html.fromHtml("<font color=#ffffff>" + title + "</font>"))
            }
            //toolbar.setLogo(R.drawable.white_logo);
            (context as AppCompatActivity).setSupportActionBar(toolbar)
            if ((context as AppCompatActivity).getSupportActionBar() != null) {
                (context as AppCompatActivity).getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
                (context as AppCompatActivity).getSupportActionBar()!!.setDisplayShowHomeEnabled(true)
            }

            val upArrow = ContextCompat.getDrawable(context, R.drawable.abc_ic_ab_back_material)
            upArrow!!.setColorFilter(ContextCompat.getColor(context, android.R.color.white), PorterDuff.Mode.SRC_ATOP)
            (context as AppCompatActivity).getSupportActionBar()!!.setHomeAsUpIndicator(upArrow)
        }

        fun setUpToolBarDiffColor(toolbar: androidx.appcompat.widget.Toolbar?, context: Context, title: String) {

            if (toolbar != null) {
                toolbar!!.setLogo(null)
                toolbar!!.setTitle(Html.fromHtml("<font color=#000000>" + title + "</font>"))
            }
            //toolbar.setLogo(R.drawable.white_logo);
            (context as AppCompatActivity).setSupportActionBar(toolbar)
            if ((context as AppCompatActivity).getSupportActionBar() != null) {
                (context as AppCompatActivity).getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
                (context as AppCompatActivity).getSupportActionBar()!!.setDisplayShowHomeEnabled(true)
            }

            val upArrow = ContextCompat.getDrawable(context, R.drawable.abc_ic_ab_back_material)
            upArrow!!.setColorFilter(Color.parseColor("#3A4759"), PorterDuff.Mode.SRC_ATOP)
            (context as AppCompatActivity).getSupportActionBar()!!.setHomeAsUpIndicator(upArrow)
        }
        fun setUpToolBarBlackColor(toolbar: androidx.appcompat.widget.Toolbar?, context: Context, title: String) {

            if (toolbar != null) {
                toolbar!!.setLogo(null)
                toolbar!!.setTitle(Html.fromHtml("<font color=#000000>" + title + "</font>"))
            }
            //toolbar.setLogo(R.drawable.white_logo);
            (context as AppCompatActivity).setSupportActionBar(toolbar)
            if ((context as AppCompatActivity).getSupportActionBar() != null) {
                (context as AppCompatActivity).getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
                (context as AppCompatActivity).getSupportActionBar()!!.setDisplayShowHomeEnabled(true)
            }

            val upArrow = ContextCompat.getDrawable(context, R.drawable.abc_ic_ab_back_material)
            upArrow!!.setColorFilter(Color.parseColor("#3A4759"), PorterDuff.Mode.SRC_ATOP)
            (context as AppCompatActivity).getSupportActionBar()!!.setHomeAsUpIndicator(upArrow)
        }
        fun showCustomDialog(context: Context, msg: String) {
            val builder = AlertDialog.Builder(context)
            builder.setMessage(msg).setPositiveButton(android.R.string.ok, object : DialogInterface.OnClickListener {
                public override fun onClick(dialog: DialogInterface, which: Int) {
                    dialog.dismiss()

                }
            }).setIcon(android.R.drawable.ic_dialog_alert).show()
        }

        @SuppressLint("LongLogTag")
        fun getCompleteAddressString(context: Context, LATITUDE: Double, LONGITUDE: Double): List<Address>? {
            var strAdd = ""
            val geocoder = Geocoder(context, Locale.getDefault())
            var addresses: List<Address>? = null
            try {
                addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1)
                if (addresses != null) {
                    val returnedAddress = addresses!!.get(0)
                    val strReturnedAddress = StringBuilder("")

                    for (i in 0 until returnedAddress.getMaxAddressLineIndex()) {
                        strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n")
                    }
                    strAdd = strReturnedAddress.toString()
                    Log.e("My Current loction address", "" + strReturnedAddress.toString())
                } else {
                    Log.e("My Current loction address", "No Address returned!")
                }

            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("My Current loction address", "Canont get Address!")
            } finally {
                return addresses
            }

        }

        fun showSettingsAlert(activity: Activity, message: String) {

            val alertDialog = AlertDialog.Builder(activity)
            alertDialog.setTitle("Alert")

            alertDialog.setMessage(message)
            // On pressing Settings button
            alertDialog.setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                public override fun onClick(dialog: DialogInterface, which: Int) {
                    val intent = Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", activity.getPackageName(), null)
                    )
                    activity.startActivityForResult(intent, 1)
                }
            })
            // on pressing cancel button
            alertDialog.setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
                public override fun onClick(dialog: DialogInterface, which: Int) {
                    dialog.cancel()
                }
            })
            alertDialog.show()
        }


        fun getBitmapFromImageView(lPtProfileImage: ImageView): Bitmap {

            lPtProfileImage.buildDrawingCache()
            val bmappg = lPtProfileImage.getDrawingCache()
            val bospg = ByteArrayOutputStream()
            bmappg.compress(Bitmap.CompressFormat.PNG, 100, bospg)


            return bmappg
        }

        /**
         * Convert image to base64 string
         *
         * @param lPtProfileImage
         * @return
         */
        fun convertImageToBaseCode(lPtProfileImage: Bitmap): String {
            val imageString: String
            val bospg = ByteArrayOutputStream()
            lPtProfileImage.compress(Bitmap.CompressFormat.PNG, 80, bospg)
            val bb = bospg.toByteArray()
            imageString = Base64.encodeToString(bb, Base64.DEFAULT)
            return imageString
        }

        fun isValidEmail(email: CharSequence): Boolean {
            if (!TextUtils.isEmpty(email)) {
                return Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
            return false
        }

        fun isValidPhoneNumber(phoneNumber: CharSequence): Boolean {
            if (!TextUtils.isEmpty(phoneNumber)) {
                return Patterns.PHONE.matcher(phoneNumber).matches()
            }
            return false
        }


        fun isGreterDate(todaydate: String, givendate: String): Boolean {
            var check = false
            try {
                // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

                //        Date date1 = sdf.parse("2009-12-31");
                //        Date date2 = sdf.parse("2010-01-31");
                val date1 = sdf.parse(todaydate)
                val date2 = sdf.parse(givendate)
                Log.e("todaydate", todaydate)
                Log.e("givendate", givendate)

                println(sdf.format(date1))
                println(sdf.format(date2))

                if (date1.after(date2)) {
                    Log.e("Result", "Date1 is after Date2")
                    check = true
                }

                if (date1.before(date2)) {
                    Log.e("Result", "Date1 is before Date2")
                    check = false
                }

                if (date1 == date2) {
                    Log.e("Result", "Date1 is equal Date2")
                    check = true
                }

            } catch (ex: ParseException) {
                ex.printStackTrace()
                check = false
            }

            return check
        }

        fun getTextTime(selectedTimeInMillis: Long): CharSequence {
            return DateUtils.getRelativeTimeSpanString(
                selectedTimeInMillis,
                System.currentTimeMillis(),
                DateUtils.SECOND_IN_MILLIS
            )
        }

        fun parseTimeToAMPM(time: String? = null): String? {
            val inputPattern = "HH:mm"
            val outputPattern = "hh:mm a"
            val inputFormat = SimpleDateFormat(inputPattern)
            val outputFormat = SimpleDateFormat(outputPattern)

            var date: Date? = null
            var str: String? = null

            try {
                date = inputFormat.parse(time)
                str = outputFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return str
        }

        fun getMiliSecondOfDate(date: String): Long {
//17/07/2019
            //val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            var timeInMilliseconds: Long = 0L
            try {
                var mDate = sdf.parse(date)

                timeInMilliseconds = mDate.getTime();
                System.out.println("Date in milli :: " + timeInMilliseconds);
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return timeInMilliseconds
        }

        @Throws(NullPointerException::class)
        fun getRealPathFromURI(context: Context, contentUri: Uri): String? {
            var cursor: Cursor? = null
            var res: String? = null
            try {
                val proj = arrayOf(MediaStore.Images.Media.DATA)
                cursor = context.contentResolver.query(contentUri, proj, null, null, null)
                if (cursor!!.moveToFirst()) {
                    val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    // cursor.moveToFirst();
                    res = cursor.getString(column_index)
                }
                return res
            } catch (e: NullPointerException) {
                return res
            } finally {
                cursor?.close()
            }

        }

        fun getMultiPartMedia(path: String?, apiMediaKey: String): MultipartBody.Part? {
            var media: MultipartBody.Part? = null
            if (path != null) {
                val file = File(path)
                val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                // val requestFile = RequestBody.create(MediaType.parse("image/*"), file)


                media = MultipartBody.Part.createFormData(apiMediaKey, file.name, requestFile)
            }
            return media
        }


        fun getReqBody(param: String): RequestBody {
            return RequestBody.create(MediaType.parse("multipart/form-data"), param)
            //return RequestBody.create(MediaType.parse("text/plain"), param)
        }





        fun editTextClearFromDrawableRight(edit_text:TextInputEditText){
            edit_text.setOnTouchListener(View.OnTouchListener { v, event ->
                val DRAWABLE_LEFT = 0
                val DRAWABLE_TOP = 1
                val DRAWABLE_RIGHT = 2
                val DRAWABLE_BOTTOM = 3
                if (event.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= edit_text.getRight() - edit_text.getCompoundDrawables().get(
                            DRAWABLE_RIGHT
                        ).getBounds().width()
                    ) { // your action here
                        edit_text.setText("")
                        return@OnTouchListener true
                    }
                }
                false
            })
        }


    }


}