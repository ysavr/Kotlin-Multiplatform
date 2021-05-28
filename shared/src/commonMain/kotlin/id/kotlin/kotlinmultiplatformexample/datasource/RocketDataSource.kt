package id.kotlin.kotlinmultiplatformexample.datasource

import id.kotlin.kotlinmultiplatformexample.data.Result
import id.kotlin.kotlinmultiplatformexample.network.SpaceXApi
import id.kotlin.kotlinmultiplatformexample.data.remote.RocketLaunch
import id.kotlin.kotlinmultiplatformexample.db.DatabaseDriverFactory
import id.kotlin.kotlinmultiplatformexample.db.Database

class RocketDataSource {

    private val api = SpaceXApi()

    suspend fun rocket(databaseDriverFactory: DatabaseDriverFactory): Result<List<RocketLaunch>> {
        val db = Database(databaseDriverFactory)
        val cache = db.getAllLaunches()
        return if (cache.isNotEmpty()) {
            Result.Success(cache)
        } else {
            try {
                api.getAllLaunches().also {
                    db.clearDatabase()
                    db.createLaunches(it)
                }
                val caches = db.getAllLaunches()
                Result.Success(caches)
            } catch (e: Throwable) {
              Result.Error(RuntimeException("Error Getting Data", e))
            }
        }
    }
}