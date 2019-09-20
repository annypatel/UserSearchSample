package sample.search.ui

import androidx.annotation.StringRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasChildCount
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import sample.search.R

/**
 * Calls specified [init] function on new instance of [UserSearchRobot].
 */
inline fun userSearch(init: UserSearchRobot.() -> Unit) = UserSearchRobot().init()

/**
 * Test robot for user search screen.
 */
class UserSearchRobot {

    fun search() {
        onView(withId(androidx.appcompat.R.id.search_button))
            .perform(click())
    }

    fun enterQuery(query: String) {
        onView(withId(androidx.appcompat.R.id.search_src_text))
            .perform(typeText(query))
    }

    fun clearQuery() {
        onView(withId(androidx.appcompat.R.id.search_src_text))
            .perform(clearText())
    }

    fun userNameShouldBe(username: String) {
        onView(withText(username))
            .check(matches(isDisplayed()))
    }

    fun displayNameShouldBe(displayName: String) {
        onView(withText(displayName))
            .check(matches(isDisplayed()))
    }

    fun snackbarMessageShouldBe(@StringRes message: Int) {
        onView(withId(R.id.snackbar_text))
            .check(matches(withText(message)))
    }

    fun userListShouldBeEmpty() {
        onView(withId(R.id.user_search_result_list))
            .check(matches(hasChildCount(0)))
    }

    fun offlineBannerShouldBeVisible() {
        onView(withId(R.id.offline_banner))
            .check(matches(isDisplayed()))
    }
}