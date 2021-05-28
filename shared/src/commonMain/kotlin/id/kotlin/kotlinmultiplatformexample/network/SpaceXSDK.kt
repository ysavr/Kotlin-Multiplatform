package id.kotlin.kotlinmultiplatformexample.network

import id.kotlin.kotlinmultiplatformexample.data.remote.RocketLaunch
import id.kotlin.kotlinmultiplatformexample.db.Database
import id.kotlin.kotlinmultiplatformexample.db.DatabaseDriverFactory

class SpaceXSDK(databaseDriverFactory: DatabaseDriverFactory) {
    private var database: Database = Database(databaseDriverFactory)
    private val api = SpaceXApi()

    suspend fun getLaunches(): List<RocketLaunch>{
        val cache = database.getAllLaunches()
        return if (cache.isNotEmpty()) {
            cache
        } else {
            api.getAllLaunches().also {
                database.clearDatabase()
                database.createLaunches(it)
            }
        }
    }
}