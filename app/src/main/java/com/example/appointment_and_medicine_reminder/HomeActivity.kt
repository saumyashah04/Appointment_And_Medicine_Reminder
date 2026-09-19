package com.example.appointment_and_medicine_reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity() {

    var currentUser: String? = null
    var containerList: LinearLayout? = null
    var btnMedicines: Button? = null
    var btnAppointments: Button? = null
    var showingMedicines: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        currentUser = sharedPref.getString("currentUser", null)

        if (currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        containerList = findViewById(R.id.containerList)
        btnMedicines = findViewById(R.id.btnMedicines)
        btnAppointments = findViewById(R.id.btnAppointments)
        val btnLogout: Button = findViewById(R.id.buttonLogout)
        val fabAdd: FloatingActionButton = findViewById(R.id.fabAdd)

        btnMedicines?.setOnClickListener {
            showingMedicines = true
            updateUI()
        }

        btnAppointments?.setOnClickListener {
            showingMedicines = false
            updateUI()
        }

        btnLogout.setOnClickListener {
            val editor = sharedPref.edit()
            editor.remove("currentUser")
            editor.apply()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        fabAdd.setOnClickListener {
            if (showingMedicines) {
                startActivity(Intent(this, AddMedicineActivity::class.java))
            } else {
                startActivity(Intent(this, AddAppointmentActivity::class.java))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    fun updateUI() {
        containerList?.removeAllViews()

        if (showingMedicines) {
            btnMedicines?.setBackgroundColor(resources.getColor(R.color.medical_blue))
            btnMedicines?.setTextColor(resources.getColor(R.color.white))
            btnAppointments?.setBackgroundColor(resources.getColor(android.R.color.transparent))
            btnAppointments?.setTextColor(resources.getColor(R.color.medical_blue))
            loadAndDisplayMedicines()
        } else {
            btnAppointments?.setBackgroundColor(resources.getColor(R.color.medical_blue))
            btnAppointments?.setTextColor(resources.getColor(R.color.white))
            btnMedicines?.setBackgroundColor(resources.getColor(android.R.color.transparent))
            btnMedicines?.setTextColor(resources.getColor(R.color.medical_blue))
            loadAndDisplayAppointments()
        }
    }

    private fun loadAndDisplayMedicines() {
        val sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        val data = sharedPref.getString("medicines_$currentUser", "") ?: ""
        if (data.isEmpty()) return

        val records = data.split("|")
        for (record in records) {
            if (record.isEmpty()) continue
            val med = Medicine.fromSerializedString(record)
            addMedicineView(med)
        }
    }

    private fun addMedicineView(med: Medicine) {
        val view = LayoutInflater.from(this).inflate(R.layout.item_medicine, containerList, false)
        val txtName = view.findViewById<TextView>(R.id.txtMedName)
        val txtDosage = view.findViewById<TextView>(R.id.txtMedDosage)
        val txtTime = view.findViewById<TextView>(R.id.txtMedTime)
        val btnEdit = view.findViewById<Button>(R.id.btnEditMed)
        val btnDelete = view.findViewById<Button>(R.id.btnDeleteMed)

        txtName.text = med.name
        txtDosage.text = med.dosage
        txtTime.text = "${med.time} (${med.date})"

        btnEdit.setOnClickListener {
            val intent = Intent(this, AddMedicineActivity::class.java)
            intent.putExtra("medId", med.id)
            startActivity(intent)
        }

        btnDelete.setOnClickListener {
            deleteMedicine(med.id)
            updateUI()
        }

        containerList?.addView(view)
    }

    private fun loadAndDisplayAppointments() {
        val sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        val data = sharedPref.getString("appointments_$currentUser", "") ?: ""
        if (data.isEmpty()) return

        val records = data.split("|")
        for (record in records) {
            if (record.isEmpty()) continue
            val app = Appointment.fromSerializedString(record)
            addAppointmentView(app)
        }
    }

    private fun addAppointmentView(app: Appointment) {
        val view = LayoutInflater.from(this).inflate(R.layout.item_appointment, containerList, false)
        val txtTitle = view.findViewById<TextView>(R.id.txtAppTitle)
        val txtPlace = view.findViewById<TextView>(R.id.txtAppPlace)
        val txtTime = view.findViewById<TextView>(R.id.txtAppTime)
        val btnEdit = view.findViewById<Button>(R.id.btnEditApp)
        val btnDelete = view.findViewById<Button>(R.id.btnDeleteApp)

        txtTitle.text = app.title
        txtPlace.text = app.personPlace
        txtTime.text = "${app.time} (${app.date})"

        btnEdit.setOnClickListener {
            val intent = Intent(this, AddAppointmentActivity::class.java)
            intent.putExtra("appId", app.id)
            startActivity(intent)
        }

        btnDelete.setOnClickListener {
            deleteAppointment(app.id)
            updateUI()
        }

        containerList?.addView(view)
    }

    private fun deleteMedicine(id: String) {
        val sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        val data = sharedPref.getString("medicines_$currentUser", "") ?: ""
        val records = data.split("|").toMutableList()
        val newList = mutableListOf<String>()
        for (record in records) {
            if (record.isNotEmpty() && !record.startsWith(id)) {
                newList.add(record)
            }
        }
        sharedPref.edit().putString("medicines_$currentUser", newList.joinToString("|")).apply()
        Toast.makeText(this, "Medicine deleted", Toast.LENGTH_SHORT).show()
        cancelAlarm(id)
    }

    private fun deleteAppointment(id: String) {
        val sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        val data = sharedPref.getString("appointments_$currentUser", "") ?: ""
        val records = data.split("|").toMutableList()
        val newList = mutableListOf<String>()
        for (record in records) {
            if (record.isNotEmpty() && !record.startsWith(id)) {
                newList.add(record)
            }
        }
        sharedPref.edit().putString("appointments_$currentUser", newList.joinToString("|")).apply()
        Toast.makeText(this, "Appointment deleted", Toast.LENGTH_SHORT).show()
        cancelAlarm(id)
    }

    private fun cancelAlarm(id: String) {
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            id.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }
}