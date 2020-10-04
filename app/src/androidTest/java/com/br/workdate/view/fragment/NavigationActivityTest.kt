package com.br.workdate.view.fragment

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.br.workdate.R
import com.br.workdate.view.NavigationActivity
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

@LargeTest
class NavigationActivityTest {

    @get:Rule
    val activity = ActivityTestRule(NavigationActivity::class.java)

    @Ignore("for Jenkins run")
    @Test
    fun showBarTitle() {
        onView(withText("Schedules"))
            .check(matches(isDisplayed()))
    }

    @Ignore("for Jenkins run")
    @Test
    fun showToolBar() {
        onView(withId(R.id.bottom_nav))
            .check(matches(isDisplayed()))
    }

    @Ignore("for Jenkins run")
    @Test
    fun testFrameNavigation() {
        onView(withId(R.id.frame_navigation))
            .check(matches(isDisplayed()))
    }

}