package com.example.appointment_and_medicine_reminder

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class AddAppointmentActivity : AppCompatActivity() {

    var editAppTitle: EditText? = null
    var editAppPlace: EditText? = null
    var editAppNotes: EditText? = null
    var txtAppDate: TextView? = null
    var txtAppTime: TextView? = null
    var txtAppRemDate: TextView? = null
    var txtAppRemTime: TextView? = null

    var appId: String? = null
    var currentUser: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_appointment)

        val sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        currentUser = sharedPref.getString("currentUser", null)

        editAppTitle = findViewById(R.id.editAppTitle)
        editAppPlace = findViewById(R.id.editAppPlace)
        editAppNotes = findViewById(R.id.editAppNotes)
        txtAppDate = findViewById(R.id.txtAppDate)
        txtAppTime = findViewById(R.id.txtAppTime)
        txtAppRemDate = findViewById(R.id.txtAppRemDate)
        txtAppRemTime = findViewById(R.id.txtAppRemTime)
        val btnSave: Button = findViewById(R.id.btnSaveApp)

        appId = intent.getStringExtra("appId")
        if (appId != null) {
            loadAppointmentData(appId!!)
            findViewById<TextView>(R.id.textViewAddAppTitle).text = "Edit Appointment"
        }

        txtAppDate?.setOnClickListener { showDatePicker(txtAppDate) }
        txtAppTime?.setOnClickListener { showTimePicker(txtAppTime) }
        txtAppRemDate?.setOnClickListener { showDatePicker(txtAppRemDate) }
        txtAppRemTime?.setOnClickListener { showTimePicker(txtAppRemTime) }

        btnSave.setOnClickListener {
            saveAppointment()
        }
    }

    private fun showDatePicker(textView: TextView?) {
        val c = Calendar.getInstance()
        DatePickerDialog(this, { _, y, m, d ->
            textView?.text = "$d/${m + 1}/$y"
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun showTimePicker(textView: TextView?) {
        val c = Calendar.getInstance()
        TimePickerDialog(this, { _, h, m ->
            textView?.text = String.format("%02d:%02d", h, m)
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show()
    }

    private fun loadAppointmentData(id: String) {
        val sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        val data = sharedPref.getString("appointments_$currentUser", "") ?: ""
        val records = data.split("|")
        for (record in records) {
            if (record.startsWith(id)) {
                val app = Appointment.fromSerializedString(record)
                editAppTitle?.setText(app.title)
                editAppPlace?.setText(app.personPlace)
                editAppNotes?.setText(app.notes)
                txtAppDate?.text = app.date
                txtAppTime?.text = app.time
                txtAppRemDate?.text = app.reminderDate
                txtAppRemTime?.text = app.reminderTime
                break
            }
        }
    }

    private fun saveAppointment() {
        val title = editAppTitle?.text.toString().trim()
        val place = editAppPlace?.text.toString().trim()
        val notes = editAppNotes?.text.toString().trim()
        val date = txtAppDate?.text.toString()
        val time = txtAppTime?.text.toString()
        val remDate = txtAppRemDate?.text.toString()
        val remTime = txtAppRemTime?.text.toString()

        if (title.isEmpty() || date == "Date" || time == "Time") {
            Toast.makeText(this, "Please fill required fields", Toast.LENGTH_SHORT).show()
            return
        }

        val sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        val data = sharedPref.getString("appointments_$currentUser", "") ?: ""
        val records = data.split("|").toMutableList()

        val id = appId ?: System.currentTimeMillis().toString()
        val newApp = Appointment(id, title, place, date, time, notes, remDate, remTime)

        val newList = mutableListOf<String>()
        var found = false
        for (record in records) {
            if (record.isNotEmpty()) {
                if (record.startsWith(id)) {
                    newList.add(newApp.toSerializedString())
                    found = true
                } else {
                    newList.add(record)
                }
            }
        }
        if (!found) {
            newList.add(newApp.toSerializedString())
        }

        sharedPref.edit().putString("appointments_$currentUser", newList.joinToString("|")).apply()

        scheduleAlarm(id, title, remDate, remTime)

        Toast.makeText(this, "Appointment Saved", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun scheduleAlarm(id: String, name: String, date: String, time: String) {
        if (date == "Reminder Date" || time == "Reminder Time") return

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        intent.putExtra("id", id)
        intent.putExtra("name", name)

        val pendingIntent = PendingIntent.getBroadcast(
            this,
            id.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance()
        val dateParts = date.split("/")
        val timeParts = time.split(":")
        calendar.set(Calendar.DAY_OF_MONTH, dateParts[0].toInt())
        calendar.set(Calendar.MONTH, dateParts[1].toInt() - 1)
        calendar.set(Calendar.YEAR, dateParts[2].toInt())
        calendar.set(Calendar.HOUR_OF_DAY, timeParts[0].toInt())
        calendar.set(Calendar.MINUTE, timeParts[1].toInt())
        calendar.set(Calendar.SECOND, 0)

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }
}