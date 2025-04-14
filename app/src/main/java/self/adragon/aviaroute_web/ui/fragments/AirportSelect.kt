package self.adragon.aviaroute_web.ui.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import self.adragon.aviaroute_web.R
import self.adragon.aviaroute_web.data.model.AirportLocation
import self.adragon.aviaroute_web.ui.adapters.AirportSelectRVAdapter

class AirportSelect(
    private val airportLocations: List<AirportLocation>,
    private val onItemSelect: (AirportLocation) -> Unit
) : DialogFragment(R.layout.airport_select) {

    private lateinit var backImageButton: ImageButton
    private lateinit var airportListRecyclerView: RecyclerView
    private lateinit var airportNameEditText: EditText
    private lateinit var airportSelectCountTextView: TextView

    override fun onCreateDialog(savedInstanceState: Bundle?) =
        Dialog(requireContext(), R.style.FullScreenDialogTheme)

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backImageButton = view.findViewById(R.id.backImageButton)
        airportNameEditText = view.findViewById(R.id.AirportNameEditText)
        airportListRecyclerView = view.findViewById(R.id.AirportListRecyclerView)
        airportSelectCountTextView = view.findViewById(R.id.AirportSelectCountTextView)

        airportSelectCountTextView.text = "Аэропортов найдено: ${airportLocations.size}"
        airportListRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = AirportSelectRVAdapter { loc ->
            onItemSelect(loc)
            dismissNow()
        }
        adapter.fillData(airportLocations)
        airportListRecyclerView.adapter = adapter

        airportNameEditText.addTextChangedListener {
            val text = airportNameEditText.text
            val newNames = if (text.isEmpty()) airportLocations else
                airportLocations.filter {
                    "${it.countryName}, ${it.cityName}, ${it.airportName}".contains(
                        text,
                        ignoreCase = true
                    )
                }
            airportSelectCountTextView.text = "Аэропортов найдено: ${newNames.size}"
            adapter.fillData(newNames)
        }

        backImageButton.setOnClickListener { dismiss() }
    }
}