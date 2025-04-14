package self.adragon.aviaroute_web.ui.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import self.adragon.aviaroute_web.R
import self.adragon.aviaroute_web.data.model.AirportLocation
import self.adragon.aviaroute_web.data.model.Flight
import self.adragon.aviaroute_web.ui.adapters.FlightRVAdapter
import self.adragon.aviaroute_web.ui.viewmodels.FlightViewModel
import self.adragon.aviaroute_web.utils.LOG_TAG

class SearchResult : DialogFragment(R.layout.flight_search_result) {

    private lateinit var backImageButton: ImageButton
    private lateinit var paramsImgButton: ImageButton

    private lateinit var flightSearchInfoCodesTextView: TextView

    private lateinit var infoDepartureTextView: TextView
    private lateinit var infoArrivalTextView: TextView
    private lateinit var infoDateTextView: TextView
    private lateinit var infoFoundTextView: TextView

    private lateinit var flightSearchResultRecyclerView: RecyclerView

    private lateinit var searchedFlightInfoDialog: SearchedFlightInfo
    private val searchInfoTag = "searchInfoTag"

    private lateinit var clicked: Flight

    private val flightViewModel: FlightViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?) =
        Dialog(requireContext(), R.style.FullScreenDialogTheme)

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        val departureLoc = arguments?.getSerializable("departureLoc", AirportLocation::class.java)
        val arrivalLoc = arguments?.getSerializable("arrivalLoc", AirportLocation::class.java)
        val departureDate = arguments?.getString("departureDate") ?: ""
        Log.d(LOG_TAG, "Dep loc = $departureLoc")
        Log.d(LOG_TAG, "Arr loc = $arrivalLoc")
        Log.d(LOG_TAG, "Dep time = $departureDate")

        flightViewModel.getFlights(departureLoc?.airportId, arrivalLoc?.airportId, departureDate)
        val adapter = FlightRVAdapter { clickedItem ->
            clicked = clickedItem

            if (::searchedFlightInfoDialog.isInitialized && searchedFlightInfoDialog.isShown)
                return@FlightRVAdapter
            searchedFlightInfoDialog = SearchedFlightInfo(clicked)
            searchedFlightInfoDialog.show(childFragmentManager, searchInfoTag)
        }
        flightSearchResultRecyclerView.adapter = adapter

        lifecycleScope.launch {
            flightViewModel.flightsFlow.onEach {
                updateUI(
                    it, departureLoc, arrivalLoc, departureDate, adapter
                )
            }.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).launchIn(this)
        }

        backImageButton.setOnClickListener {
            dismiss()
        }

        paramsImgButton.setOnClickListener {
            val msg = "Params button on click is not implemented yet"
            Log.d(LOG_TAG, msg)
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

//            if (!paramsDialog.isShown)
//                paramsDialog.show(childFragmentManager, "")
        }
    }

    private fun updateUI(
        flights: List<Flight>, departureLoc: AirportLocation?,
        arrivalLoc: AirportLocation?, departureDate: String, adapter: FlightRVAdapter
    ) {
        adapter.fillData(flights)
        flightSearchResultRecyclerView.adapter = adapter

        val departureMessage = "Откуда: $departureLoc"
        val destinationMessage = "Куда: $arrivalLoc"
        val dateMessage = "Дата вылета: $departureDate"
        val foundMessage = "\nНайдено билетов: ${flights.size}"

        infoDepartureTextView.text = departureMessage
        infoArrivalTextView.text = destinationMessage
        infoDateTextView.text = dateMessage
        infoFoundTextView.text = foundMessage
    }

    private fun initViews(view: View) {
        flightSearchInfoCodesTextView = view.findViewById(R.id.flightSearchInfoCodesTextView)

        infoDepartureTextView = view.findViewById(R.id.flightSearchInfoDepartureTextView)
        infoArrivalTextView = view.findViewById(R.id.flightSearchInfoArrivalTextView)
        infoDateTextView = view.findViewById(R.id.flightSearchInfDateTextView)
        infoFoundTextView = view.findViewById(R.id.flightSearchInfoFoundTextView)

        backImageButton = view.findViewById(R.id.backImageButton)
        paramsImgButton = view.findViewById(R.id.paramsImgButton)

        flightSearchResultRecyclerView = view.findViewById(R.id.flightSearchResultRecyclerView)
        flightSearchResultRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}