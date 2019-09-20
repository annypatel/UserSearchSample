package sample

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

/**
 * JUnit runner that creates instance of [TestApp] instead of [App] while running the instrumented tests.
 */
class AndroidJUnitRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, TestApp::class.java.name, context)
    }
}