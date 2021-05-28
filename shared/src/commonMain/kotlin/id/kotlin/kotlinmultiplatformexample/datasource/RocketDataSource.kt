package id.kotlin.kotlinmultiplatformexample.datasource

import id.kotlin.kotlinmultiplatformexample.data.Result
import id.kotlin.kotlinmultiplatformexample.data.network.SpaceXApi
import id.kotlin.kotlinmultiplatformexample.data.remote.RocketLaunch
import id.kotlin.kotlinmultiplatformexample.db.DatabaseDriverFactory
import id.kotlin.kotlinmultiplatformexample.db.Database

class RocketDataSource {

    private val api = SpaceXApi()

    suspend fun rocket(databaseDriverFactory: DatabaseDriverFactory): Result<List<RocketLaunch>> {
        val db = Database(databaseDriverFactory)
        return try {
            val request = api.getAllLaunches()
            request.also {
                db.clearDatabase()
                db.createLaunches(it)
            }
            Result.Success(request)
        } catch (e: Throwable) {
            Result.Error(RuntimeException("Error Getting Data", e))
        }
    }
}