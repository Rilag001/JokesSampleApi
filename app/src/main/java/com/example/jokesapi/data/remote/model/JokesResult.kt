package com.example.jokesapi.data.remote.model

data class JokesResult<T>(
    val failure: JokesResultFailure? = null,
    val data: T? = null,
) {
    val isSuccessful = failure == null
}

fun <T> JokesResultFailure?.asResult() = JokesResult<T>(failure = this)

/**
 * Utility function where we don't need to expose any return type.
 * Keep error information, but just return Unit for successes.
 */
internal fun <T> JokesResult<T>.toUnitResult(): JokesResult<Unit> {
    return JokesResult(this.failure, if (this.data == null) null else Unit)
}

sealed class JokesResultFailure {
    /**
     *  [ServerError] indicates the request came through but
     *  errors where encountered during processing
     *  indicating the server could not complete the
     *  request and resulted in a per use-case not successful
     *  status code.
     *
     *  @param [statusCode] the status code the response returned
     */
    data class ServerError(val statusCode: Int) : JokesResultFailure()

    /**
     *  [NetworkOffline] indicates the devices is not connected to the network
     */
    object NetworkOffline : JokesResultFailure()

    /**
     * [NotFound] indicates the request came through but that item is not found
     */
    object NotFound : JokesResultFailure()

    /**
     * Unexpected error and the corresponding throwable
     */
    data class UnexpectedError(val throwable: Throwable? = null) : JokesResultFailure()
}
