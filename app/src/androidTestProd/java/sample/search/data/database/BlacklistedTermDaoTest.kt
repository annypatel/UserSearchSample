package sample.search.data.database

import androidx.room.EmptyResultSetException
import androidx.room.Room.inMemoryDatabaseBuilder
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class BlacklistedTermDaoTest {

    private lateinit var database: SearchDatabase
    private lateinit var dao: BlacklistedTermDao

    @Before
    fun setup() {
        database = inMemoryDatabaseBuilder(getApplicationContext(), SearchDatabase::class.java)
            .build()
        dao = database.blacklistedTermDao()
    }

    @After
    fun cleanup() {
        database.close()
    }

    @Test
    fun findBy_whenExactMatch_shouldEmitBlacklistedTerm() {
        // GIVEN
        val expected = BlacklistedTermEntity(term = "x")
        dao.insert(expected).subscribe()

        // WHEN
        val observer = dao.findBy("x")
            .test()

        // THEN
        observer.assertValue { it.term == expected.term }
            .assertComplete()
            .assertNoErrors()
    }

    @Test
    fun findBy_whenNoMatch_shouldEmitError() {
        // GIVEN
        dao.insert(BlacklistedTermEntity(term = "x"))
            .subscribe()

        // WHEN
        val observer = dao.findBy("y")
            .test()

        // THEN
        observer.assertNoValues()
            .assertNotComplete()
            .assertError { it is EmptyResultSetException }
    }

    @Test
    fun findBy_whenPrefixMatch_shouldEmitBlacklistedTerm() {
        // GIVEN
        val expected = BlacklistedTermEntity(term = "x")
        dao.insert(expected).subscribe()

        // WHEN
        val observer = dao.findBy("xyz")
            .test()

        // THEN
        observer.assertValue { it.term == expected.term }
            .assertComplete()
            .assertNoErrors()
    }

    @Test
    fun import_shouldInsertBlacklistedTerms() {
        // GIVEN
        val db = database.openHelper.writableDatabase

        // WHEN
        BlacklistedTermDao.import(getApplicationContext(), db)

        // THEN
        Assert.assertThat(dao.count(), equalTo(100L))
    }
}