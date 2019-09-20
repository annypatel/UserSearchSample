package sample.base.data.network

import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThat
import org.junit.Test
import retrofit2.Retrofit

class NetworkClientTest {

    interface ApiService

    @Test
    fun create_givenServiceClass_returnsInstanceOfService() {
        val client = NetworkClient(
            Retrofit.Builder()
                .baseUrl("https://examp.le")
                .build()
        )

        val apiService = client.create(ApiService::class)

        assertNotNull(apiService)
        assertThat(apiService, instanceOf(ApiService::class.java))
    }
}