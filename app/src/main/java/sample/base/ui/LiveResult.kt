package sample.base.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * Type alias for the [LiveData] of type [Result].
 */
typealias LiveResult<T> = LiveData<Result<T>>

/**
 * Type alias for the [MutableLiveData] of type [Result].
 */
typealias MutableLiveResult<T> = MutableLiveData<Result<T>>

/**
 * To observer the [LiveData] of type [Result].
 */
fun <T> LiveResult<T>.observe(
    owner: LifecycleOwner,
    value: (T) -> Unit = {},
    error: (Throwable) -> Unit = {},
    complete: () -> Unit = {}
) {
    observe(owner, Observer {
        when (it) {
            is Value -> value(it.value)
            is Error -> error(it.error)
            is Complete -> complete()
        }
    })
}