package com.br.workdate.view.fragment

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.br.workdate.R
import com.br.workdate.view.NavigationActivity
import org.junit.Rule
import org.junit.Test

@LargeTest
class ListClientScreenTest {

    @get:Rule
    val activity = ActivityTestRule(NavigationActivity::class.java)

    @Test
    fun showBarTitle() {
        onView(withText("Schedule List"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun showToolBar() {
        onView(withId(R.id.bottom_nav))
            .check(matches(isDisplayed()))
    }
}