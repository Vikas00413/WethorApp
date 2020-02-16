import android.content.DialogInterface
import android.content.Intent
import android.provider.Settings
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.demo.demotext.R

import java.util.*


class AppUtil {
    class LocationConstants {
        companion object {


            val PACKAGE_NAME = "app.rebel.com.bookmylibrary"


        }

    }

    companion object {


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
        fun showSettingsAlert(activity: AppCompatActivity, message: String) {

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

        fun playGif(imageView: ImageView) {
            Glide.with(imageView.context)
                .asGif()
                .load(R.drawable.loder_gif)
                .into(imageView)
        }
        fun addDays(date: Date?, days: Int): Date? {
            val cal: Calendar = Calendar.getInstance()
            cal.setTime(date)
            cal.add(Calendar.DATE, days) //minus number would decrement the days
            return cal.getTime()
        }

    }


}