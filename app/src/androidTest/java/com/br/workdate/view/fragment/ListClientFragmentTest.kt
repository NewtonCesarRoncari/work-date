package com.br.workdate.view.fragment

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.android21buttons.fragmenttestrule.FragmentTestRule
import com.br.workdate.R
import com.br.workdate.view.NavigationActivity
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class ListClientFragmentTest {

    @get:Rule
    val fragment = FragmentTestRule(NavigationActivity::class.java, ListClientFragment::class.java)

    @Ignore("for Jenkins run")
    @Test
    fun returnDisplayWheNoHaveSchedules() {
        Thread.sleep(500)
        onView(withId(R.id.client_list_animation)).check(matches(isDisplayed()))
    }

    @Ignore("for Jenkins run")
    @Test
    fun returnDisplayButton() {
        onView(withId(R.id.new_client)).check(matches(isDisplayed()))
    }
}