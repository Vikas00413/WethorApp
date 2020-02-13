package com.demo.demotext.view.activity

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

import com.demo.demotext.MyApplication


abstract class BaseActivity : AppCompatActivity() {

    protected var dialog: ProgressDialog? = null
    private var ctx: Context? = null
    private var activity: AppCompatActivity? = null
    //    private TinyDB db;



    //https://androidclarified.com/android-image-upload-example/
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        activity = this

        ctx = applicationContext
        val window = activity!!.window
        MyApplication.mNetComponent.inject(this)

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
// finally change the color
        window.statusBarColor = Color.parseColor("#D84D65")
        //        db = TinyDB.getInstance();

    }

    protected fun showLoader(message: String) {
        if (dialog == null) {
            dialog = ProgressDialog(activity)
        }
        dialog!!.setMessage(message)
        dialog!!.setCancelable(false)
        dialog!!.setCanceledOnTouchOutside(false)
        dialog!!.show()
    }

    protected fun hideLoader() {
        if (dialog != null && dialog!!.isShowing)
            dialog!!.dismiss()
    }

    protected fun showToast(msg: String) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show()

    }


    //    protected void showAnim() {
    //        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    //    }


    //    protected void showKeyboard(AppCompatActivity activity) {
    //        View v = activity.getCurrentFocus();
    //        if (v != null) {
    //            InputMethodManager imManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
    //            if (imManager != null) {
    //                imManager.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
    //            }
    //        }
    //    }

    protected fun hideKeyboard(activity: AppCompatActivity) {
        val view = activity.currentFocus
        if (view != null) {
            val imManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imManager?.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    companion object {
        private val TAG = BaseActivity::class.java!!.getSimpleName()
    }

    //this method is to on touch outside hide softkeyboard
    //    protected void hidekeyboardOutsidetouch(final AppCompatActivity activity, View view) {
    //        //Set up touch listener for non-text box views to hide keyboard.
    //        if (!(view instanceof EditText)) {
    //            view.setOnTouchListener(new View.OnTouchListener() {
    //                public boolean onTouch(View v, MotionEvent event) {
    //                    hideKeyboard(activity);
    //                    return false;
    //                }
    //            });
    //        }
    //        //If a rv_border_color container, iterate over children and seed recursion.
    //        if (view instanceof ViewGroup) {
    //            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
    //                View innerView = ((ViewGroup) view).getChildAt(i);
    //                hidekeyboardOutsidetouch(activity, innerView);
    //            }
    //        }
    //    }
    fun makeWindowNotTouch() {
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    fun clearTouchEvent() {
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public override fun onDestroy() {
        super.onDestroy()

    }



}