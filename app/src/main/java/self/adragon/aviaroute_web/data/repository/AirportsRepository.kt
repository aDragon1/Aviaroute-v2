package self.adragon.aviaroute_web.data.repository

import android.util.Log
import self.adragon.aviaroute_web.data.remote.AirportsRemote
import self.adragon.aviaroute_web.utils.LOG_TAG

object AirportsRepository {
    private val remote = AirportsRemote()

    suspend fun getLocations() = try {
        remote.fetchLocations()
    } catch (e: Exception) {
        Log.e(LOG_TAG, "${e.message}, ${e.stackTraceToString()} ")
        emptyList()
    }
}