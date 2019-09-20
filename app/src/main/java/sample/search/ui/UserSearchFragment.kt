package sample.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat.getDrawable
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import sample.search.R
import sample.base.ui.observe
import sample.search.domain.UserSearchResult
import dagger.android.support.DaggerFragment
import kotterknife.bindView
import javax.inject.Inject

/**
 * Main fragment displaying and handling interactions with the view.
 */
class UserSearchFragment : DaggerFragment() {
    private val toolbar: Toolbar by bindView(R.id.toolbar)
    private val offlineBanner: View by bindView(R.id.offline_banner)
    private val userSearchResultList: RecyclerView by bindView(R.id.user_search_result_list)

    @Inject
    internal lateinit var factory: ViewModelProvider.Factory
    private val viewModel by viewModels<UserSearchViewModel> { factory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_user_search, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        setUpList()

        viewModel.searchResults.observe(
            this,
            { onUserSearchResults(it) },
            { onUserSearchError(it) }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_user_search, menu)

        val searchView: SearchView = menu.findItem(R.id.search_menu_item).actionView as SearchView
        searchView.queryHint = getString(R.string.search_users_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.onQueryTextChange(newText)
                return true
            }
        })
    }

    private fun onUserSearchResults(result: UserSearchResult) {
        offlineBanner.visibility = if (result.offline) View.VISIBLE else View.GONE
        val adapter = userSearchResultList.adapter as UserSearchAdapter
        adapter.setUsers(result.users)
    }

    private fun onUserSearchError(ignore: Throwable) {
        view?.let {
            Snackbar.make(it, R.string.search_failure_message, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setUpToolbar() {
        val act = activity as UserSearchActivity
        act.setSupportActionBar(toolbar)
    }

    private fun setUpList() {
        val divider = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        getDrawable(requireContext(), R.drawable.search_item_divider)?.let {
            divider.setDrawable(it)
        }

        with(userSearchResultList) {
            addItemDecoration(divider)
            adapter = UserSearchAdapter(150, resources.getDimensionPixelSize(R.dimen.search_item_avatar_radius))
            layoutManager = LinearLayoutManager(activity).apply {
                orientation = RecyclerView.VERTICAL
            }
            setHasFixedSize(true)
        }
    }
}