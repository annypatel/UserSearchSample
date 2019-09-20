package sample

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import okreplay.AndroidTapeRoot
import okreplay.OkReplayConfig
import okreplay.OkReplayRuleChain
import okreplay.TapeMode
import org.junit.rules.RuleChain
import org.junit.rules.TestRule

/**
 * Wraps [ActivityTestRule] into [OkReplayRuleChain].
 */
fun Any.okReplayRuleChain(activityRule: ActivityTestRule<*>): TestRule {
    val tapeRoot = AndroidTapeRoot(getInstrumentation().context, this::class.java)
    val interceptor = getApplicationContext<TestApp>().component.okReplayInterceptor()
    val config = OkReplayConfig.Builder()
        .tapeRoot(tapeRoot)
        .defaultMode(TapeMode.READ_WRITE)
        .sslEnabled(true)
        .interceptor(interceptor)
        .build()

    val permissionRule = GrantPermissionRule.grant(WRITE_EXTERNAL_STORAGE)
    val okReplayRule = OkReplayRuleChain(config, activityRule).get()
    return RuleChain.outerRule(permissionRule)
        .around(okReplayRule)
}