package app.punk.data

import app.punk.data.entities.ErrorResult
import app.punk.data.entities.Result
import app.punk.data.entities.Success
import app.punk.data.mappers.Mapper
import app.punk.extensions.bodyOrThrow
import app.punk.extensions.isFromNetwork
import app.punk.extensions.toException
import retrofit2.Response

class RetrofitRunner {
    suspend fun <T, E> executeForResponse(mapper: Mapper<T, E>, request: suspend () -> Response<T>): Result<E> {
        return try {
            val response = request()
            if (response.isSuccessful) {
                Success(data = mapper.map(response.bodyOrThrow()), responseModified = response.isFromNetwork())
            } else {
                ErrorResult(response.toException())
            }
        } catch (e: Exception) {
            ErrorResult(e)
        }
    }

    suspend fun <T> executeForResponse(request: suspend () -> Response<T>): Result<Unit> {
        val unitMapper = object : Mapper<T, Unit> {
            override fun map(from: T) = Unit
        }
        return executeForResponse(unitMapper, request)
    }
}