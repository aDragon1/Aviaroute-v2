package self.adragon.aviaroute_web.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

const val API_URL = "http://195.26.230.200:6969"

abstract class ApiRemote {
    abstract val route: String

    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    suspend inline fun <reified T> fetchJSON(endpoint: String): T =
        Json.decodeFromString(client.get("$API_URL/$endpoint").body())

    suspend inline fun <reified T> postJSON(
        endpoint: String, params: FormDataContent
    ): T = Json.decodeFromString(client.post("$API_URL/$endpoint") {
        contentType(ContentType.Application.Json)
        setBody(params)
    }.body())

    suspend inline fun <reified T> get(endpoint: String): T =
        client.get("$API_URL/$endpoint").body()

    suspend inline fun <reified T> post(
        endpoint: String, params: FormDataContent
    ): T = client.post("$API_URL/$endpoint") {
        contentType(ContentType.Application.Json)
        setBody(params)
    }.body()
}
