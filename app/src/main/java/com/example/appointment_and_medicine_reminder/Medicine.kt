package com.example.appointment_and_medicine_reminder

class Medicine(
    var id: String,
    var name: String,
    var dosage: String,
    var date: String,
    var time: String,
    var notes: String,
    var reminderDate: String,
    var reminderTime: String
) {
    // Simple method to convert to string for persistence
    fun toSerializedString(): String {
        return "$id~$name~$dosage~$date~$time~$notes~$reminderDate~$reminderTime"
    }

    companion object {
        fun fromSerializedString(data: String): Medicine {
            val parts = data.split("~")
            return Medicine(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7])
        }
    }
}