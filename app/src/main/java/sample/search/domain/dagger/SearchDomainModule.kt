package sample.search.domain.dagger

import sample.search.domain.AddToBlacklistUseCase
import sample.search.domain.AddToBlacklistUseCaseImpl
import sample.search.domain.IsBlacklistedUseCase
import sample.search.domain.IsBlacklistedUseCaseImpl
import sample.search.domain.SearchUserUseCase
import sample.search.domain.SearchUserUseCaseImpl
import dagger.Binds
import dagger.Module

/**
 * Module to inject user search related use cases into application component.
 */
@Module
abstract class SearchDomainModule {

    @Binds
    abstract fun searchUserUseCase(searchUser: SearchUserUseCaseImpl): SearchUserUseCase

    @Binds
    abstract fun addToBlacklistUseCase(searchUser: AddToBlacklistUseCaseImpl): AddToBlacklistUseCase

    @Binds
    abstract fun isBlacklistedUseCase(searchUser: IsBlacklistedUseCaseImpl): IsBlacklistedUseCase
}