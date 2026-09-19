package com.example.appointment_and_medicine_reminder

class Appointment(
    var id: String,
    var title: String,
    var personPlace: String,
    var date: String,
    var time: String,
    var notes: String,
    var reminderDate: String,
    var reminderTime: String
) {
    // Simple method to convert to string for persistence
    fun toSerializedString(): String {
        return "$id~$title~$personPlace~$date~$time~$notes~$reminderDate~$reminderTime"
    }

    companion object {
        fun fromSerializedString(data: String): Appointment {
            val parts = data.split("~")
            return Appointment(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7])
        }
    }
}