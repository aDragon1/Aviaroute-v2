package self.adragon.aviaroute_web.ui.fragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import self.adragon.aviaroute_web.R
import self.adragon.aviaroute_web.data.repository.DocumentsRepository
import self.adragon.aviaroute_web.utils.LOG_TAG
import java.io.File


class Documents : Fragment(R.layout.documens_layout), OnClickListener {

    private lateinit var periodReportButton: Button
    private lateinit var smallAircraftReportButton: Button

    private var dateStart: String? = null
    private var dateEnd: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    private fun initViews(v: View) {
        periodReportButton = v.findViewById(R.id.periodReportButton)
        smallAircraftReportButton = v.findViewById(R.id.smallAircraftReportButton)

        periodReportButton.setOnClickListener(this)
        smallAircraftReportButton.setOnClickListener(this)

        val rangeDatePicker = RangeDatePicker("Выберите период") { start, end ->
            dateStart = start
            dateEnd = end

            Log.d(LOG_TAG, "$start -> $end")
        }
        childFragmentManager.beginTransaction()
            .replace(R.id.rangeDatePickerContainer, rangeDatePicker)
            .commit()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.periodReportButton -> periodReport()
            R.id.smallAircraftReportButton -> aircraftSmallReport()
            else -> {}
        }
    }

    private fun aircraftSmallReport() {
        Toast.makeText(requireContext(), "Получение выписки по рейсам..", Toast.LENGTH_SHORT).show()

        CoroutineScope(Dispatchers.IO).launch {
            val report = DocumentsRepository.getAircraftSmallReport() ?: return@launch
            Log.d(LOG_TAG, "report exists - ${report.exists()}, path - ${report.path}")

            withContext(Dispatchers.Main) {
                val msg = "Выписка получена, попытка открыть файл.."
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                openPDF(report)
            }
        }
    }

    private fun periodReport() {
        if (dateStart == null || dateEnd == null) {
            Toast.makeText(requireContext(), "Выберите период", Toast.LENGTH_SHORT).show()
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            val report = DocumentsRepository.getPeriodReport(dateStart!!, dateEnd!!) ?: return@launch

            Log.d(LOG_TAG, "report exists - ${report.exists()}, path - ${report.path}")

            withContext(Dispatchers.Main) { openPDF(report) }
        }
    }

    private fun openPDF(report: File) {
        val fileUri = FileProvider.getUriForFile(
            requireContext(),
            "self.adragon.aviaroute_web.provider",
            report
        )

        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(fileUri, "application/pdf")
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Log.e(LOG_TAG, e.stackTraceToString())
            Toast.makeText(requireContext(), "Ошибка - ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}