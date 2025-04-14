package self.adragon.aviaroute_web.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AirportLocation(
    val airportId: Int,
    val countryName: String,
    val cityName: String,
    val airportName: String,
    val airportCode: String
) : java.io.Serializable {
    override fun toString() = "$countryName, $cityName, $airportName ($airportCode)"
}