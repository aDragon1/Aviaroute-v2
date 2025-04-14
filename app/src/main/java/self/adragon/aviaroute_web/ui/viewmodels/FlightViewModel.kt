package self.adragon.aviaroute_web.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import self.adragon.aviaroute_web.data.model.Flight
import self.adragon.aviaroute_web.data.repository.FlightsRepository

class FlightViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FlightsRepository

    // Поток всех полученных данных
    private val _flightsFlow = MutableStateFlow<List<Flight>>(emptyList())
    val flightsFlow = _flightsFlow.asStateFlow()

    // Поток отфильтрованных данных (можешь на него подписываться в UI)
    private val _filter: MutableStateFlow<Any?> = MutableStateFlow(null)

    val filteredFlightsFlow = combine(flightsFlow, _filter) { flights, filter ->
        filter?.let { applyFilters(flights, it) } ?: flights
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun setFilter(filter: Any?) {
        _filter.value = filter
    }

    // В будущем можно будет добавить фильтрацию, например:
    private fun applyFilters(flights: List<Flight>, filter: Any): List<Flight> {
        val filtered = flights.filter { segments ->
            true
        }
        return filtered
    }

    fun getFlights(depID: Int?, arrID: Int?, depDate: String) =
        viewModelScope.launch(Dispatchers.IO) {
            if (depID == null || arrID == null) return@launch

            val result = repository.getFlights(depID, arrID, depDate)
            _flightsFlow.update { result }
        }
}
