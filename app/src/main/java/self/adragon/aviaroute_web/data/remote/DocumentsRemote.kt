package self.adragon.aviaroute_web.data.remote

import io.ktor.client.request.forms.FormDataContent
import io.ktor.http.Parameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class DocumentsRemote : ApiRemote() {
    override val route = "documents"

    suspend fun getAircraftSmallReport(): File? {
        try {
            val fileBytes = get<ByteArray>("$route/aircraft/small")
            val tempFile = withContext(Dispatchers.IO) {
                File.createTempFile("aircraft_small", ".pdf")
            }
            tempFile.writeBytes(fileBytes)
            return tempFile
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    suspend fun getPeriodReport(dateStart: String, dateEnd: String): File? {
        val postParams = FormDataContent(Parameters.build {
            append("date_start", dateStart)
            append("date_end", dateEnd)
        })

        try {

            val fileBytes = post<ByteArray>("$route/schedule/daily", postParams)
            val tempFile = withContext(Dispatchers.IO) {
                File.createTempFile("period_report", ".pdf")
            }
            tempFile.writeBytes(fileBytes)
            return tempFile

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

}