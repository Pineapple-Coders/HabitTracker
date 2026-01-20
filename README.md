# Habit Tracker App

![Kotlin](https://img.shields.io/badge/Kotlin-1.9-blue?logo=kotlin)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-UI-orange?logo=jetpackcompose)
![MVVM](https://img.shields.io/badge/Architecture-MVVM-brightgreen)
![Room DB](https://img.shields.io/badge/Database-Room-red)
![Glance](https://img.shields.io/badge/Widget-Glance-purple)
![License: MIT](https://img.shields.io/badge/License-MIT-yellow)

---

A minimal, distraction free **habit tracker app** built using **Jetpack Compose, SOLID Principles, MVVM, Room DB, and Dependency Injection**.
This app helps users **track habits they want to leave**, such as smoking, drinking, junk food, or gaming, and measures how long they've stayed away from them.

---

## Screenshots

<p align="center">
  <img src="Screenshots/Screen1.png" width="250"/>
  <img src="Screenshots/Screen2.png" width="250"/>
  <img src="Screenshots/Screen3.png" width="250"/>
</p>

---

## Features

### Core Features
- **Add Habits** Create new habits you want to quit (e.g., Smoking, Drinking, Junk Food, Gaming)
- **Live Timers** See how long it's been since you last gave in to a habit
- **Streak Tracking** Track current streak and longest streak for each habit
- **History Log** Keep a record of past resets for accountability
- **Reset Option** One tap reset when you slip, so the timer starts again

### New Features
- **Home Screen Widget** Quick glance widget showing your habit streak right on your home screen using Glance API
- **App Shortcuts** Long press the app icon for quick actions like adding a new habit or viewing achievements
- **Achievement Badges** Unlock achievements as you progress through milestones (Fresh Start, Committed, Warrior, Champion, Master, Legend, Titan)
- **Circular Progress Indicators** Beautiful animated progress rings showing your daily and overall progress
- **Large Timer Display** Prominent circular timer showing days, hours, and minutes since your last reset

### UI/UX
- **Modern Material Design** Clean, minimal interface with smooth animations
- **Dark and Light Themes** Support for system theme preferences
- **Responsive Layout** Optimized for various screen sizes

---

## Tech Stack

| Category | Technology |
|----------|------------|
| Language | Kotlin |
| UI Framework | Jetpack Compose |
| Architecture | MVVM + Clean Architecture |
| State Management | StateFlow, ViewModel |
| Database | Room DB |
| Dependency Injection | Hilt |
| Widget | Glance API |
| Async Operations | Kotlin Coroutines + Flow |

---

## Project Structure

```
app/
├── data/
│   ├── local/          # Room database and DAOs
│   ├── mapper/         # Data mappers
│   └── repository/     # Repository implementations
├── domain/
│   ├── model/          # Domain models
│   ├── repository/     # Repository interfaces
│   └── usecase/        # Use cases
├── presentation/
│   ├── components/     # Reusable UI components
│   ├── screens/        # App screens
│   ├── theme/          # App theming
│   └── viewmodel/      # ViewModels
├── widget/             # Home screen widget
└── di/                 # Dependency injection modules
```

---

## Getting Started

### Prerequisites
- Android Studio Hedgehog or newer
- Kotlin 1.9+
- Jetpack Compose 1.5+
- Minimum SDK: 24 (Android 7.0)
- Target SDK: 34 (Android 14)

### Installation

1. Clone this repository:
```bash
git clone https://github.com/AtulYadav01/HabitTracker.git
cd HabitTracker
```

2. Open the project in Android Studio

3. Sync Gradle and build the project

4. Run the app on an emulator or physical device:
```bash
./gradlew installDebug
```

---

## License

This project is open source under the MIT License.

