package sample

import sample.base.ui.Error
import sample.base.ui.Result
import sample.base.ui.Value
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

/**
 * Matcher for checking [result][Result] is [value][Value] or not.
 */
fun <T, R : Result<T>> isValue(value: T): Matcher<in R?> {
    val matcher = object : TypeSafeMatcher<Value<T>>() {

        override fun describeTo(description: Description) {
            description.appendText("Value")
                .appendValue(value)
        }

        override fun matchesSafely(item: Value<T>): Boolean {
            return item.value == value
        }
    }

    @Suppress("UNCHECKED_CAST")
    return matcher as Matcher<R?>
}

/**
 * Matcher for checking [result][Result] is [error][Error] or not.
 */
fun <T, R : Result<T>> isError(error: Throwable): Matcher<R?> {
    val matcher = object : TypeSafeMatcher<Error<T>>() {

        override fun describeTo(description: Description) {
            description.appendText("Error")
                .appendValue(error)
        }

        override fun matchesSafely(item: Error<T>): Boolean {
            return item.error == error
        }
    }
    @Suppress("UNCHECKED_CAST")
    return matcher as Matcher<R?>
}