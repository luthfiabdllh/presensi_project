package com.example.pertemuan6

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.pertemuan6.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val kehadiranList = resources.getStringArray(R.array.kehadiranlist)

        with(binding) {
            // Get Array
            val monthList = resources.getStringArray(R.array.month)

            //Initiate
            var selectedTime = "${timePicker.hour}:${timePicker.minute}"
            val _tempCalendar: Calendar = Calendar.getInstance()
            _tempCalendar.timeInMillis = System.currentTimeMillis()
            var selectedDate =
                "${_tempCalendar.get(Calendar.DAY_OF_MONTH)} ${monthList[_tempCalendar.get(Calendar.MONTH)]} ${_tempCalendar.get(Calendar.YEAR)}"

            // Kehadiran Dropdown =======================================
            val adapterKehadiran = ArrayAdapter<String>(
                this@MainActivity,
                android.R.layout.simple_spinner_item, kehadiranList
            )

            kehadiranSpinner.adapter = adapterKehadiran

            // Selected Kehadiran
            kehadiranSpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        // Get selected item
                        val selectedKehadiran = parent?.getItemAtPosition(position).toString()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }

                }
            kehadiranSpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        val selectedKehadiran = parent?.getItemAtPosition(position).toString()
                        if (selectedKehadiran == "Hadir Tepat Waktu") {
                            keteranganEdittext.isGone
                        } else {
                            keteranganEdittext.isVisible
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }

            // Time Picker =======================================
            timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
                selectedTime = String.format("%02d:%02d", hourOfDay, minute)
            }
            // Time Picker =======================================
            timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
                selectedTime = String.format("%02d:%02d", hourOfDay, minute)
            }

            // Date Picker =======================================
            datepicker.init(
                _tempCalendar.get(Calendar.YEAR),
                _tempCalendar.get(Calendar.MONTH),
                _tempCalendar.get(Calendar.DAY_OF_MONTH)
            ) { _, year, monthOfYear, dayOfMonth ->
                selectedDate = "$dayOfMonth ${monthList[monthOfYear]} $year"
            }

            // Button Click =======================================
            submitButton.setOnClickListener {
                val selectedKehadiran = kehadiranSpinner.selectedItem.toString()
                val message = "Kehadiran: $selectedKehadiran\nWaktu: $selectedTime\nTanggal: $selectedDate"
                Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}