# ZenFlow - Pomodoro Focus & Productivity App

A mindful productivity app combining the Pomodoro Technique with task management, mood tracking, and achievement unlocking. Built entirely in **Java** using **MVVM architecture** with **Room database** for offline-first storage.

## 🎯 Features

### Core Functionality
- **Pomodoro Timer**: Customizable focus sessions (default 25 min) with short (5 min) and long (15 min) breaks
- **Task Management**: Create, organize, and track tasks with priority levels and categories
- **Mood Tracking**: Log emotional state before and after focus sessions
- **Statistics Dashboard**: Track total sessions, focus time, and completed tasks
- **Achievements System**: Unlock badges for milestones (first focus, streaks, task completion)

### Technical Highlights
- 100% **offline** - no backend required
- **MVVM architecture** with Repository pattern
- **Room Database** for local persistence
- **LiveData** for reactive UI updates
- **Material Design 3** components
- Clean separation of concerns (Data → Repository → ViewModel → UI)

## 📱 App Structure

### Package Organization
```
com.zenflow.app/
├── data/
│   ├── local/
│   │   ├── entities/     # Room entities
│   │   ├── dao/          # Data Access Objects
│   │   └── ZenFlowDatabase.java
│   └── prefs/
│       └── SettingsPreferences.java
├── repository/
│   └── ZenFlowRepository.java
├── ui/
│   ├── main/             # MainActivity & MainViewModel
│   ├── tasks/            # Task list, adapter, bottom sheet
│   ├── timer/            # Timer UI & ViewModel
│   ├── mood/             # Mood check-in
│   ├── stats/            # Statistics screen
│   └── achievements/     # Achievement grid
├── timer/
│   ├── PomodoroEngine.java    # Timer logic
│   └── TimerState.java
└── ZenFlowApp.java       # Application class
```

## 🛠️ Tech Stack

| Component | Technology |
|-----------|-----------|
| Language | Java 11 |
| Architecture | MVVM |
| Database | Room 2.6.1 |
| Async | Executors + LiveData |
| UI | XML Layouts, Material 3 |
| Libraries | RecyclerView, ViewModel, LiveData, ConstraintLayout |
| Animations | Lottie (optional) |

## 📊 Database Schema

### Entities
1. **TaskEntity**: Tasks with title, description, priority, category, estimated pomodoros
2. **SessionEntity**: Focus/break sessions linked to tasks
3. **MoodLogEntity**: Pre/post session mood logs
4. **AchievementEntity**: Unlockable achievements

### Relationships
- Sessions → Tasks (Foreign Key)
- MoodLogs → Sessions (Foreign Key)

## 🎨 UI Screens

1. **Timer Screen** (Default)
   - Large countdown display
   - Start/Pause/Stop controls
   - Quick access to break timers
   
2. **Task List**
   - Active tasks with priority indicators
   - Checkbox to complete
   - Bottom sheet to add new tasks
   
3. **Statistics**
   - Total focus sessions
   - Total time focused
   - Completed task count
   
4. **Achievements**
   - Grid of unlockable badges
   - Progress tracking

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or newer
- JDK 11+
- Android SDK with API 24+ (minSdk)

### Build & Run
1. Clone this repository
2. Open in Android Studio
3. Sync Gradle
4. Run on emulator or device (API 24+)

### Configuration
Settings are stored in `SharedPreferences`:
- Focus duration (default: 25 min)
- Short break (default: 5 min)
- Long break (default: 15 min)
- Cycles before long break (default: 4)

## 📝 Key Implementation Details

### PomodoroEngine
- Uses Android's `CountDownTimer` for accurate timing
- Emits `LiveData<TimerState>` for UI observation
- Callback interface for completion events

### Repository Pattern
- Single source of truth for data operations
- Wraps all DAO calls in `Executor` for background execution
- Exposes `LiveData` for automatic UI updates

### ViewModels
- Each screen has dedicated ViewModel + Factory
- Repository injected via custom `ViewModelProvider.Factory`
- No direct database access from UI layer

### Achievement System
- Predefined achievements initialized on first launch
- Automatically checked and unlocked via repository
- Examples: "First Focus", "Week Streak", "100 Sessions"

## 🎓 What This Project Demonstrates

**For Recruiters:**
- Clean architecture (MVVM + Repository)
- Android fundamentals (Activities, Fragments, RecyclerView)
- Room database design with relationships
- Lifecycle-aware components (ViewModel, LiveData)
- Material Design implementation
- Offline-first architecture
- Production-ready Java code

**Complexity Level:**
- Intermediate to Advanced
- Suitable for showcasing as a portfolio project
- Demonstrates understanding of Android architecture components

## 📄 License

This is a portfolio/resume project. Feel free to use as reference.

## 👨‍💻 Author

Built as a demonstration of Java Android development skills using modern architecture patterns.

---

**Note**: This is a complete, production-quality app built entirely in Java without any external backend dependencies. Perfect for demonstrating Android fundamentals and MVVM architecture to potential employers.

