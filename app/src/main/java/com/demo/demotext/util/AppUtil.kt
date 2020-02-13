import android.util.Log
import android.widget.ImageView
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