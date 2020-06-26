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
        checkBarTitle("Schedule List")
        clickBottomNavigation(R.id.listClientFragment, CLIENTS)
        checkBarTitle("Client List")
    }

    @Test
    fun testGoToServiceScreen() {
        checkBarTitle("Schedule List")
        clickBottomNavigation(R.id.listServiceFragment, SERVICES)
        checkBarTitle("Service List")
    }

    @Test
    fun testGoToReleaseScreen() {
        checkBarTitle("Schedule List")
        clickBottomNavigation(R.id.listReleaseFragment, RELEASES)
        checkBarTitle(RELEASES)
    }

    @Test
    fun testGoToAllBottomNavigationScreenAndReturn() {
        clickBottomNavigation(R.id.listClientFragment, CLIENTS)
        checkBarTitle("Client List")
        clickBottomNavigation(R.id.listServiceFragment, SERVICES)
        checkBarTitle("Service List")
        clickBottomNavigation(R.id.listReleaseFragment, RELEASES)
        checkBarTitle("Releases")
        clickBottomNavigation(R.id.listScheduleFragment, SCHEDULE)
        checkBarTitle("Schedule List")
    }
}
