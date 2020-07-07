package com.br.workdate.view.flow

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.br.workdate.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher

internal const val CLIENTS = "Clients"
internal const val SERVICES = "Services"
internal const val RELEASES = "Releases"
internal const val SCHEDULE = "Schedule"

abstract class BaseFlowTest {

    protected fun clickBottomNavigation(idBottom: Int, nameBottom: String) {
        Espresso.onView(
            Matchers.allOf(
                withId(idBottom),
                withContentDescription(nameBottom),
                isDescendantOfA(withId(R.id.bottom_nav)),
                isDisplayed()
            )
        ).perform(click())
    }

    protected fun checkBarTitle(barTitle: String) {
        Espresso.onView(
            Matchers.allOf(
                withText(barTitle),
                isDisplayed(),
                isDescendantOfA(withId(R.id.action_bar))
            )
        )
            .check(matches(withText(barTitle)))
    }

    protected fun clickFabNew(idBottom: Int) {
        Espresso.onView(
            Matchers.allOf(
                withId(idBottom),
                isDescendantOfA(withId(R.id.frame_navigation)),
                isDisplayed()
            )
        ).perform(click())
    }

    protected fun clickSaveDialog() {
        Espresso.onView(
            Matchers.allOf(
                withId(android.R.id.button1), withText("SAVE"),
                childAtPosition(
                    childAtPosition(
                        withClassName(Matchers.`is`("android.widget.ScrollView")),
                        0
                    ),
                    3
                )
            )
        ).perform(scrollTo(), click())
    }

    protected fun insertDataInFieldWithParent(idField: Int, idFieldParent: Int, data: String) {
        Espresso.onView(
            Matchers.allOf(
                withId(idField),
                childAtPosition(
                    childAtPosition(
                        withId(idFieldParent),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        ).perform(typeText(data), closeSoftKeyboard())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}