package sample.search.ui

import android.os.Bundle
import sample.search.R
import dagger.android.support.DaggerAppCompatActivity

/**
 * Launcher activity. Kept light and simple to delegate view logic to fragment(s) it attaches.
 */
class UserSearchActivity : DaggerAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_search)
    }
}
