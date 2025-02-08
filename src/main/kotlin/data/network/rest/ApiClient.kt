package data.network.rest

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

internal class ApiClient @PublishedApi internal constructor(
    @PublishedApi internal val httpClient: HttpClient
) {
    companion object {
        const val BASE_URL = "http://127.0.0.1:5000/api"
    }

    suspend inline fun <reified T> get(
        endpoint: String,
        crossinline builder: HttpRequestBuilder.() -> Unit = {}
    ): T = httpClient.get(endpoint) {
        builder()
    }.body()

    suspend inline fun <reified T, reified R> post(
        endpoint: String,
        body: R,
        crossinline builder: HttpRequestBuilder.() -> Unit = {}
    ): T = httpClient.post(endpoint) {
        setBody(body)
        builder()
    }.body()
}