package self.adragon.aviaroute_web.ui.fragments

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import self.adragon.aviaroute_web.R
import self.adragon.aviaroute_web.data.model.AirportLocation
import self.adragon.aviaroute_web.data.repository.AirportsRepository

class Search : Fragment(R.layout.flight_search), OnClickListener {
    private lateinit var swapButton: ImageButton
    private lateinit var searchButton: Button

    private lateinit var departureAirportInclude: View
    private lateinit var departureIncludeTextView: TextView
    private lateinit var departureIncludeClear: ImageButton
    private var departureLoc: AirportLocation? = null

    private lateinit var arrivalAirportInclude: View
    private lateinit var arrivalIncludeTextView: TextView
    private lateinit var arrivalIncludeClear: ImageButton
    private var arrivalLoc: AirportLocation? = null

    private var departureDate = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViews(view)
        CoroutineScope(Dispatchers.IO).launch {
            val airportLocations = AirportsRepository.getLocations()

            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), "Данные загруженны", Toast.LENGTH_SHORT).show()

                departureAirportInclude.setOnClickListener {
                    AirportSelect(airportLocations) { loc ->
                        departureIncludeTextView.text = loc.airportName
                        departureLoc = loc
                    }.show(childFragmentManager, "")
                }

                arrivalAirportInclude.setOnClickListener {
                    AirportSelect(airportLocations) { loc ->
                        arrivalIncludeTextView.text = loc.airportName
                        arrivalLoc = loc
                    }.show(childFragmentManager, "")
                }
            }
        }

        departureIncludeClear.setOnClickListener {
            departureIncludeTextView.text = ""
            departureLoc = null
        }
        arrivalIncludeClear.setOnClickListener {
            arrivalIncludeTextView.text = ""
            arrivalLoc = null
        }

        val periodDatePicker = DatePicker { date -> departureDate = date }
        childFragmentManager.beginTransaction().replace(R.id.datePickerContainer, periodDatePicker).commit()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.swapButton -> {

                val temp = departureIncludeTextView.text
                departureIncludeTextView.text = arrivalIncludeTextView.text
                arrivalIncludeTextView.text = temp

                val temp2 = departureLoc
                departureLoc = arrivalLoc
                arrivalLoc = temp2
            }

            R.id.searchButton -> {
                if (!handleValidateError()) return

                val arg = Bundle().apply {
                    putSerializable("departureLoc", departureLoc!!)
                    putSerializable("arrivalLoc", arrivalLoc!!)
                    putString("departureDate", departureDate)
                }
                val searchResultFragment = SearchResult().apply { arguments = arg }
                searchResultFragment.show(childFragmentManager, "cool tag")
            }
        }
    }

    private fun setViews(v: View) {
        departureAirportInclude = v.findViewById(R.id.departureAirportInclude)
        arrivalAirportInclude = v.findViewById(R.id.arrivalAirportInclude)

        departureIncludeTextView = departureAirportInclude.findViewById(R.id.tv_search)
        arrivalIncludeTextView = arrivalAirportInclude.findViewById(R.id.tv_search)

        departureIncludeClear = departureAirportInclude.findViewById(R.id.iv_icon_clear)
        arrivalIncludeClear = arrivalAirportInclude.findViewById(R.id.iv_icon_clear)

        swapButton = v.findViewById(R.id.swapButton)
        searchButton = v.findViewById(R.id.searchButton)

        swapButton.setOnClickListener(this)
        searchButton.setOnClickListener(this)

        departureIncludeTextView.hint = "Откуда"
        arrivalIncludeTextView.hint = "Куда"
    }

    private fun handleValidateError(): Boolean {
//        return true // uncomment for test

        val isValidDeparture = departureLoc != null
        val isValidArrival = arrivalLoc != null
        val isValidDate = departureDate.isNotEmpty()

        val s = when {
            !isValidDeparture && !isValidArrival -> "Выберите аэропорт отправления и аэропорт назначения"
            !isValidDeparture && isValidArrival -> "Выберите аэропорт отправления"
            isValidDeparture && !isValidArrival -> "Выберите аэропорт назначения"
            !isValidDate -> "Выберите дату отправления"

            else -> ""
        }

        if (s.isNotEmpty()) {
            Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

}
