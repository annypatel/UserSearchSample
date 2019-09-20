package sample.base.ui

/**
 * A base sealed class that represents a result of an operation, one of [Value], [Error] or [Complete].
 */
sealed class Result<out T> {

    companion object {
        fun <T> value(value: T): Result<T> = Value(value)
        fun <T> error(error: Throwable): Result<T> = Error(error)
        fun <T> complete(): Result<T> = Complete()
    }
}

/**
 * Represents a value as result of an operation.
 */
data class Value<out T>(val value: T) : Result<T>()

/**
 * Represents an error as result of an operation.
 */
data class Error<out T>(val error: Throwable) : Result<T>()

/**
 * Represents a completion of an operation.
 */
class Complete<out T> : Result<T>()