package data.network.rest

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class ApiClient(private val httpClient: HttpClient) {
    companion object {
        private const val BASE_URL = "tu_url_base_aqui"
    }

    suspend inline fun <reified T> get(
        endpoint: String,
        builder: HttpRequestBuilder.() -> Unit = {}
    ): T = httpClient.get("$BASE_URL$endpoint") {
        contentType(ContentType.Application.Json)
        builder()
    }

    suspend inline fun <reified T> post(
        endpoint: String,
        body: Any,
        builder: HttpRequestBuilder.() -> Unit = {}
    ): T = httpClient.post("$BASE_URL$endpoint") {
        contentType(ContentType.Application.Json)
        setBody(body)
        builder()
    }
}