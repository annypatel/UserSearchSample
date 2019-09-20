package sample.search.data.database

import androidx.room.Room.inMemoryDatabaseBuilder
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import org.junit.After
import org.junit.Before
import org.junit.Test

class UserDaoTest {

    private lateinit var database: SearchDatabase
    private lateinit var dao: UserDao

    @Before
    fun setup() {
        database = inMemoryDatabaseBuilder(getApplicationContext(), SearchDatabase::class.java)
            .build()
        dao = database.userDao()
    }

    @After
    fun cleanup() {
        database.close()
    }

    @Test
    fun findBy_whenExactUsername_shouldEmitUsers() {
        // GIVEN
        val users = listOf(UserEntity(1, "a", "x", "url"))
        dao.insert(users).subscribe()

        // WHEN
        val observer = dao.findBy("a")
            .test()

        // THEN
        observer.assertValue(users)
            .assertNoErrors()
            .assertComplete()
    }

    @Test
    fun findBy_whenPrefixOfUsername_shouldEmitUsers() {
        // GIVEN
        val users = listOf(UserEntity(1, "ab", "xy", "url"))
        dao.insert(users).subscribe()

        // WHEN
        val observer = dao.findBy("a")
            .test()

        // THEN
        observer.assertValue(users)
            .assertNoErrors()
            .assertComplete()
    }

    @Test
    fun findBy_whenExactDisplayName_shouldEmitUsers() {
        // GIVEN
        val users = listOf(UserEntity(1, "a", "x", "url"))
        dao.insert(users).subscribe()

        // WHEN
        val observer = dao.findBy("x")
            .test()

        // THEN
        observer.assertValue(users)
            .assertNoErrors()
            .assertComplete()
    }

    @Test
    fun findBy_whenPrefixOfDisplayName_shouldEmitUsers() {
        // GIVEN
        val users = listOf(UserEntity(1, "ab", "xy", "url"))
        dao.insert(users).subscribe()

        // WHEN
        val observer = dao.findBy("x")
            .test()

        // THEN
        observer.assertValue(users)
            .assertNoErrors()
            .assertComplete()
    }

    @Test
    fun findBy_whenNoMatch_shouldEmitEmpty() {
        // GIVEN
        val users = listOf(UserEntity(1, "a", "x", "url"))
        dao.insert(users).subscribe()

        // WHEN
        val observer = dao.findBy("z")
            .test()

        // THEN
        observer.assertValue(emptyList())
            .assertNoErrors()
            .assertComplete()
    }
}