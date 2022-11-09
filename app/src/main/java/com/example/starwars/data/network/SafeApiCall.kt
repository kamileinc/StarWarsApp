package com.example.starwars.data.network

import com.example.starwars.utilities.Resource
import com.example.starwars.utilities.UiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

open class SafeApiCall {
    suspend inline fun <T> safeApiCall(crossinline apiCall: suspend () -> T): Resource<T> {
        return try {
            val data = withContext(Dispatchers.IO) {
                apiCall.invoke()
            }
            Resource.Success(data)
        } catch (throwable: Throwable) {
            when (throwable) {
                is HttpException -> {
                    Resource.Failure(
                        UiText.DynamicString(
                            throwable.response()?.errorBody().toString()
                        ), null)
                }
                else -> {
                    Resource.Failure(
                        UiText.DynamicString(
                            throwable.localizedMessage
                        ), null)
                }
            }
        }
    }
}
