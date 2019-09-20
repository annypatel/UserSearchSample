package sample.search.domain

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

class IsBlacklistedUseCaseImplTest {

    private val mockedRepository = mock<BlacklistRepository>()
    private val isBlacklisted = IsBlacklistedUseCaseImpl(mockedRepository)

    @Test
    fun isBlacklisted_shouldCallRepository() {
        // WHEN
        isBlacklisted("a")

        // THEN
        verify(mockedRepository).isBlacklisted("a")
    }
}