package self.adragon.aviaroute_web.data.repository

import self.adragon.aviaroute_web.data.remote.DocumentsRemote


object DocumentsRepository {
    private val remote = DocumentsRemote()

    suspend fun getPeriodReport(dateStart: String, dateEnd: String) =
        remote.getPeriodReport(dateStart, dateEnd)

    suspend fun getAircraftSmallReport() = remote.getAircraftSmallReport()
}