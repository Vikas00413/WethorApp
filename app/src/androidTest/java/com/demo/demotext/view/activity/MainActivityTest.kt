package com.demo.demotext.view.activity

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.demo.demotext.R
import org.hamcrest.CoreMatchers.allOf
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat
import java.util.*

/**
 * this is Test class for main activity
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest{

    @Test
    fun testActivity_inView() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.main)).check(matches(isDisplayed()))
    }

    @Test
      fun isTopLeftTextDateIsTodayDate(){
        val date =
            SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
                .format(Date())

        onView(allOf(withId(R.id.tv_date), withText(date)))
    }



}