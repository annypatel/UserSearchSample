package sample.search.data.dagger

import android.content.Context
import com.google.gson.Gson
import io.reactivex.Single
import sample.search.data.network.UserRaw
import sample.search.data.network.UserSearchResponse
import sample.search.data.network.UserSearchService
import java.io.InputStreamReader

class MockUserSearchService(
    private val context: Context
) : UserSearchService {

    override fun searchUsers(query: String): Single<UserSearchResponse> {
        return Single.fromCallable {
            val reader = InputStreamReader(context.assets.open("all_users.json"))
            Gson().fromJson(reader, Array<UserRaw>::class.java)
        }.map {
            val users = it.filter {
                it.username.startsWith(query) || it.displayName.startsWith(query)
            }
            UserSearchResponse(true, users)
        }
    }
}
