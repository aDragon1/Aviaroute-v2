package self.adragon.aviaroute_web.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CabinClassInfo(
    val seatsLeft: Int,
    val price: Double,
): java.io.Serializable