package self.adragon.aviaroute_web.data.model

import kotlinx.serialization.Serializable

@Serializable
data class FlightDetails(
    val tailNumber: String,
    val economy: CabinClassInfo,
    val priority: CabinClassInfo,
    val business: CabinClassInfo,
): java.io.Serializable