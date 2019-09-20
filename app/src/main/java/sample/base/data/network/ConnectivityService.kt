package sample.base.data.network

import android.content.Context
import android.net.ConnectivityManager
import io.reactivex.Single
import javax.inject.Inject

/**
 * Service for checking network connectivity.
 */
interface ConnectivityService {

    /**
     * Emits single of network connectivity state.
     */
    fun connected(): Single<Boolean>
}

/**
 * Implementation of [ConnectivityService] using the [ConnectivityManager].
 */
class ConnectivityServiceImpl @Inject constructor(
    context: Context
) : ConnectivityService {

    private val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun connected(): Single<Boolean> {
        return Single.fromCallable {
            manager.activeNetworkInfo?.isConnected ?: false
        }
    }
}