package sample.search.domain

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

class AddToBlacklistUseCaseImplTest {

    private val mockedRepository = mock<BlacklistRepository>()
    private val addToBlacklist = AddToBlacklistUseCaseImpl(mockedRepository)

    @Test
    fun addToBlacklist_shouldCallRepository() {
        // WHEN
        addToBlacklist("a")

        // THEN
        verify(mockedRepository).add("a")
    }
}