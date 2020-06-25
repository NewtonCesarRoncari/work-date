package com.br.workdate.view


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.br.workdate.R
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class NavigationActivityScreenTest {

    @Rule
    @JvmField
    var activity = ActivityTestRule(NavigationActivity::class.java)

    @Test
    fun testGoToClientScreen() {
        checkBarTitle("Schedule List")
        clickBottomNavigation(R.id.listClientFragment, "Clients")
        checkBarTitle("Client List")
    }

    @Test
    fun testGoToServiceScreen() {
        checkBarTitle("Schedule List")
        clickBottomNavigation(R.id.listServiceFragment, "Services")
        checkBarTitle("Service List")
    }

    @Test
    fun testGoToReleaseScreen() {
        checkBarTitle("Schedule List")
        clickBottomNavigation(R.id.listReleaseFragment, "Releases")
        checkBarTitle("Releases")
    }

    @Test
    fun testGoToAllBottomNavigationScreenAndReturn() {
        clickBottomNavigation(R.id.listClientFragment, "Clients")
        checkBarTitle("Client List")
        clickBottomNavigation(R.id.listServiceFragment, "Services")
        checkBarTitle("Service List")
        clickBottomNavigation(R.id.listReleaseFragment, "Releases")
        checkBarTitle("Releases")
        clickBottomNavigation(R.id.listScheduleFragment, "Schedule")
        checkBarTitle("Schedule List")
    }

    private fun checkBarTitle(barTitle: String) {
        onView(
            allOf(
                withText(barTitle),
                isDisplayed(),
                isDescendantOfA(withId(R.id.action_bar))
            )
        )
            .check(matches(withText(barTitle)))
    }

    private fun clickBottomNavigation(idBottom: Int, nameBottom: String) {
        onView(
            allOf(
                withId(idBottom),
                withContentDescription(nameBottom),
                isDescendantOfA(withId(R.id.bottom_nav)),
                isDisplayed()
            )
        ).perform(click())
    }
}
