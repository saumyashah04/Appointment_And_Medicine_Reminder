---
# 💊 Appointment And Medicine Reminder
 
**Appointment And Medicine Reminder** is an Android application developed using **Kotlin** and **Android Studio**. The application allows users to log in, add medicine and appointment reminders, and get notified when it's time to take a medicine or attend an appointment.
 
The project is designed with a simple and user-friendly interface using **Material Design**, making it easy to manage reminders directly from an Android device.
 
## 📱 Features
 
* 🔐 **User Login**
 
  * Username and password login system.
  * User session is saved locally.
 
* 💊 **Reminder List**
 
  * View all saved medicine/appointment reminders in a list.
  * Each entry displays its name, dosage/notes, date, time, and status.
 
* ➕ **Add Reminder**
 
  * Add a new medicine or appointment reminder.
  * Enter name, dosage/notes, date, and time.
 
* ✏️ **Edit Reminder**
 
  * Tap an existing reminder to edit its details.
  * Update name, dosage/notes, date, and time.
 
* 🗑️ **Delete Reminder**
 
  * Long press a reminder to delete it.
  * A confirmation dialog is displayed before deletion.
 
* ⏰ **Scheduled Notifications**
 
  * Select a date and time for the reminder.
  * The application schedules a notification/alarm for the selected time.
 
* 💾 **Local Data Storage**
 
  * Reminder information is stored locally on the device.
  * No internet connection or external database is required.
 
* 🚪 **Logout**
 
  * Users can log out from the main screen.
  * The application returns to the login screen.
 
## 🛠️ Technologies Used
 
| Technology            | Usage                            |
| ---------------------- | --------------------------------- |
| **Kotlin**            | Application programming language |
| **Android Studio**    | Development environment          |
| **Android SDK**       | Android application development  |
| **Material Design**   | UI components and styling        |
| **ConstraintLayout**  | Screen layouts                   |
| **AlarmManager**      | Scheduling reminders              |
| **BroadcastReceiver** | Handling scheduled reminders     |
| **Gradle Kotlin DSL** | Project build configuration      |
 
## 📂 Project Structure
 
```text
Appointment_And_Medicine_Reminder/
│
├── app/
│   └── src/
│       ├── main/
│       │   ├── java/
│       │   │   └── com/example/appointment_and_medicine_reminder/
│       │   │       ├── LoginActivity.kt
│       │   │       ├── MainActivity.kt
│       │   │       ├── AddReminderActivity.kt
│       │   │       ├── DataManager.kt
│       │   │       ├── Reminder.kt
│       │   │       └── ReminderReceiver.kt
│       │   │
│       │   ├── res/
│       │   │   ├── layout/
│       │   │   └── values/
│       │   │
│       │   └── AndroidManifest.xml
│       │
│       └── test/
│
├── build.gradle.kts
├── settings.gradle.kts
└── gradle/
    └── libs.versions.toml
```
 
## 🚀 How to Run the Project
 
1. Clone the repository:
   ```bash
   git clone <YOUR-GITHUB-REPOSITORY-URL>
   ```
2. Open the `Appointment_And_Medicine_Reminder` folder in **Android Studio**.
3. Allow Gradle to sync and download dependencies.
4. Select an Android Emulator or a physical Android device.
5. Click the **Run ▶** button in Android Studio.
 
## 📖 Application Flow
 
```text
Start Application
       ↓
   Login Screen
       ↓
   Valid Login
       ↓
   My Reminders
       ↓
 ┌─────┴──────────┐
 ↓                ↓
Add Reminder    View Reminders
 ↓                ↓
Enter Details     ↓
 ↓             Tap Reminder
Date/Time         ↓
 ↓             Edit Reminder
Save Reminder
       ↓
Reminder Added to List
```
 
## ⏰ Add a Reminder
 
1. Log in to the application.
2. Tap the **+** button on the main screen.
3. Enter the medicine/appointment name.
4. Enter dosage or notes if required.
5. Select the date.
6. Select the time.
7. Tap **SAVE REMINDER**.
 
## 💾 Data Management
 
The `DataManager` class handles saving, loading, adding, updating, and deleting reminders, as well as managing the logged-in user session.
 
## 🔔 Reminder Notifications
 
The application uses Android's `AlarmManager` to schedule reminders. The `ReminderReceiver` receives the scheduled alarm and shows a notification with the reminder name and time.
 
## 🔒 Permissions
 
```xml
android.permission.SCHEDULE_EXACT_ALARM
android.permission.USE_EXACT_ALARM
```
 
## 📸 Screenshots
 
<img width="350" height="778" alt="Screenshot 1" src="https://github.com/user-attachments/assets/0c189d0b-a273-484f-820e-2ad05e565d15" />
<img width="350" height="778" alt="Screenshot 2" src="https://github.com/user-attachments/assets/c664a98e-32ab-4e51-960a-a35a06f20577" />
<img width="350" height="778" alt="Screenshot 3" src="https://github.com/user-attachments/assets/ef0cd7fc-09dd-4ae1-8fc4-954667d5374c" />
<img width="350" height="778" alt="Screenshot 4" src="https://github.com/user-attachments/assets/a288b20d-0fab-4cc4-8a86-fd6a73b4de4b" />
<img width="350" height="778" alt="Screenshot 5" src="https://github.com/user-attachments/assets/077ec9e4-8fa1-414e-a525-74c424e1486f" />
<img width="350" height="778" alt="Screenshot 6" src="https://github.com/user-attachments/assets/6eb54eb5-2c0a-4cf7-b8f9-bdf29d5283ea" />
<img width="350" height="778" alt="Screenshot 7" src="https://github.com/user-attachments/assets/1aeac7ea-5909-473a-a044-515d411864fe" />
 
## 🔮 Future Improvements
 
* 🔔 Rich notifications with action buttons (Mark as taken / Snooze)
* 🔁 Recurring/repeat reminders
* ☁️ Cloud database synchronization
* 📊 Medicine intake history
* 🌓 Light/Dark theme switching
* 🔐 Secure authentication
 
## 👨‍💻 Project Information
 
**Project Name:** Appointment And Medicine Reminder
**Platform:** Android
**Language:** Kotlin
**IDE:** Android Studio
**Version:** 1.0
