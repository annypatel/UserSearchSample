package sample.search.ui

import android.content.Intent
import androidx.test.rule.ActivityTestRule
import sample.search.R
import sample.base.data.network.offline
import sample.okReplayRuleChain
import okreplay.OkReplay
import org.junit.Rule
import org.junit.Test

class UserSearchActivityTest {

    private val activity = ActivityTestRule(UserSearchActivity::class.java)
    @get:Rule val okReplayRule = okReplayRuleChain(activity)

    @Test
    @OkReplay
    fun search_whenSuccessful_shouldDisplayResult() = userSearch {
        // GIVEN
        activity.launchActivity(Intent())

        // WHEN
        search()
        enterQuery("b")

        // THEN
        userNameShouldBe("bruce")
        displayNameShouldBe("Bruce Wayne")
    }

    @Test
    @OkReplay
    fun search_whenOffline_shouldDisplayResultWithOfflineBanner() = userSearch {
        // GIVEN
        activity.launchActivity(Intent())
        search()
        enterQuery("b")
        clearQuery()

        // WHEN
        offline {
            enterQuery("b")
        }

        // THEN
        offlineBannerShouldBeVisible()
        userNameShouldBe("bruce")
        displayNameShouldBe("Bruce Wayne")
    }

    @Test
    @OkReplay
    fun search_whenFails_shouldDisplayNothing() = userSearch {
        // GIVEN
        activity.launchActivity(Intent())

        // WHEN
        search()
        enterQuery("c")

        // THEN
        userListShouldBeEmpty()
    }

    @Test
    @OkReplay
    fun search_whenNetworkError_shouldDisplaySnackbar() = userSearch {
        // GIVEN
        activity.launchActivity(Intent())

        // WHEN
        search()
        enterQuery("d")

        // THEN
        userListShouldBeEmpty()
        snackbarMessageShouldBe(R.string.search_failure_message)
    }
}