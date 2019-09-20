package sample.search.ui.dagger

import androidx.lifecycle.ViewModel
import sample.base.ui.dagger.ViewModelKey
import sample.search.ui.UserSearchActivity
import sample.search.ui.UserSearchFragment
import sample.search.ui.UserSearchViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Module to declare UI components that have injectable members
 */
@Module
abstract class SearchUiModule {

    @ContributesAndroidInjector
    abstract fun bindUserSearchActivity(): UserSearchActivity

    @ContributesAndroidInjector
    abstract fun bindUserSearchFragment(): UserSearchFragment

    @Binds
    @IntoMap
    @ViewModelKey(UserSearchViewModel::class)
    internal abstract fun userSearchViewModel(viewModel: UserSearchViewModel): ViewModel
}