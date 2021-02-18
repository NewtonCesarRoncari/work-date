package com.br.workdate.view.flow


import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.br.workdate.R
import com.br.workdate.view.NavigationActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class BottomNavigationFlowTest : BaseFlowTest() {

    @Rule
    @JvmField
    var activity = ActivityTestRule(NavigationActivity::class.java)

    @Test
    fun testGoToClientScreen() {
        checkBarTitle("Schedules")
        clickBottomNavigation(R.id.listClientFragment, CLIENTS)
        checkBarTitle("Clients")
    }

    @Test
    fun testGoToServiceScreen() {
        checkBarTitle("Schedules")
        clickBottomNavigation(R.id.listServiceFragment, SERVICES)
        checkBarTitle("Services")
    }

    @Test
    fun testGoToReleaseScreen() {
        checkBarTitle("Schedules")
        clickBottomNavigation(R.id.listReleaseFragment, RELEASES)
        checkBarTitle(RELEASES)
    }

    @Test
    fun testGoToAllBottomNavigationScreenAndReturn() {
        clickBottomNavigation(R.id.listClientFragment, CLIENTS)
        checkBarTitle("Clients")
        clickBottomNavigation(R.id.listServiceFragment, SERVICES)
        checkBarTitle("Services")
        clickBottomNavigation(R.id.listReleaseFragment, RELEASES)
        checkBarTitle("Releases")
        clickBottomNavigation(R.id.listScheduleFragment, SCHEDULE)
        checkBarTitle("Schedules")
    }
}
