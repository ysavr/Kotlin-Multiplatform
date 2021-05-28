package id.kotlin.kotlinmultiplatformexample.network

import id.kotlin.kotlinmultiplatformexample.data.remote.RocketLaunch
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*

class SpaceXApi {
    private val httpClient = HttpClient {
        install(JsonFeature) {
            val json = kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
            }
            serializer = KotlinxSerializer(json)
        }
    }

    suspend fun getAllLaunches(): List<RocketLaunch> {
        return httpClient.get(baseUrl + launchApi)
    }

    companion object {
        private const val baseUrl = "https://api.spacexdata.com/"
        private const val launchApi = "v3/launches"
    }
}