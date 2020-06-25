package com.br.workdate.view.fragment

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.android21buttons.fragmenttestrule.FragmentTestRule
import com.br.workdate.R
import com.br.workdate.view.NavigationActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class ListReleaseFragmentTest {

    @get:Rule
    val fragment = FragmentTestRule(NavigationActivity::class.java, ListReleaseFragment::class.java)

    @Test
    fun returnDisplayCardViewResume() {
        onView(withId(R.id.resume_cardView)).check(matches(isDisplayed()))
    }

    @Test
    fun returnDisplayTabLayoutReleases() {
        onView(withId(R.id.fragment_release_tabLayout)).check(matches(isDisplayed()))
    }

    @Test
    fun returnDisplayViewPagerReleases() {
        onView(withId(R.id.fragment_release_viewpager)).check(matches(isDisplayed()))
    }

    @Test
    fun returnDisplayAnimationEmptyReleaseWhenNoHaveReleases() {
        onView(withId(R.id.revenue_list_animation)).check(matches(isDisplayed()))
    }

}