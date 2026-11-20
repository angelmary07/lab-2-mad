# MindWell Companion — Smart Wellbeing & Dynamic Calculator App
> **Android Application developed using Kotlin and XML**  
> Course: Mobile Application Development (LAB MAD)

---

## 🌟 Executive Overview
**MindWell Companion** is a comprehensive, production-grade Android application combining a **full 8-pillar Mental Health & Daily Wellbeing suite** with the required **Dynamic Numerical Processing Lab Assignment**.

Designed with Material Design 3, soothing natural aesthetics (Forest Moss, Terracotta Clay, Warm Sand, and Calm Slate), and an intuitive 5-tab Bottom Navigation interface, the app looks, feels, and functions like a premium commercial wellness product.

---

## 📋 Comprehensive Feature Matrix

### Part 1: The 8 Wellbeing Pillars

| # | Feature | Details & Implementation |
|---|---|---|
| **1** | **Sleep Tracker** | • Log bedtime & wake time with interactive TimePicker dialogs<br>• Automatic duration calculation handling midnight crossing (e.g. 11:00 PM to 07:15 AM = 8h 15m)<br>• 5-Star sleep quality rating with clinical sleep tips<br>• Bedtime reminder toggle with scheduled notification status<br>• Weekly sleep trend chart with Mon–Sun bars against 8h target |
| **2** | **Mood Tracker** | • Daily mood check-in with 6 interactive emoji cards: 🤩 Radiant, 😊 Good, 😌 Calm, 😐 Neutral, 😔 Low, 😣 Stressed<br>• Tag selectors: Sleep, Workout, Work, Friends<br>• Notes field for emotional triggers<br>• Weekly mood trend pattern summary & streak counter |
| **3** | **Multimedia Journal** | • Daily reflection text journaling<br>• Simulated Audio Voice Notes (recording indicator, timer, and audio playback)<br>• Media photo/video attachment previews<br>• Live search and filter by keyword, date, or mood tag<br>• PIN-protected Private Vault entries with 4-digit lock/unlock dialog |
| **4** | **Meditation & Relaxation** | • 4-7-8 Guided Breathing exercise with animated pulsating circle and cycle timer<br>• Guided meditation session cards (Morning Dawn, Anxiety Release, Deep Sleep, Loving Kindness)<br>• Ambient calming sound player (Gentle Rain, Ocean Waves, Zen Bell, Forest Birds)<br>• Mindful streaks and total meditation minutes tracker |
| **5** | **Appointment Scheduling** | • Certified therapist directory (Dr. Maya Sharma Ph.D., David Vance CBT Specialist) with ratings, specialties, and fees<br>• Interactive booking flow for Video Call, Phone, or In-Person clinic sessions<br>• Upcoming booked appointments calendar card with reminder notification and cancellation option |
| **6** | **Personal Assistant** | • Daily To-Do list with interactive checkboxes, task adder, and priority badges<br>• Habit tracker with live Water Intake counter (+ and − buttons, animated progress bar to 8 glasses)<br>• Daily movement & medicine checklist<br>• Daily routine planner |
| **7** | **Insights Dashboard** | • Weekly Wellbeing Zenith score (88/100)<br>• Combined health radar: Sleep (85%), Mood (90%), Meditation (4-day streak)<br>• Quick action grid connecting all core pillars |
| **8** | **Security & Privacy** | • User authentication & sign-in with remembered credentials<br>• Guest demo mode for instant evaluation<br>• Local secure SharedPreferences data persistence<br>• **24/7 Emergency Helplines SOS Directory** (Tele-MANAS `14416`, KIRAN `1800-599-0019`, 988 Suicide & Crisis Lifeline) with one-tap dialer (`Intent.ACTION_DIAL`) |

---

### Part 2: LAB MAD Assignment — Dynamic Numerical Processor
The assignment requirements are implemented in **`CalculatorController.kt`**, accessible via **Tab 5 ("Calculator")** on the bottom navigation bar and also as a standalone **`CalculatorActivity.kt`**.

| Assignment Requirement | Implementation in MindWell |
|---|---|
| **Platform & Language** | Built 100% using **XML** layouts and **Kotlin** classes. |
| **UI: TextView Title** | `tvAppTitle`: Displays "Dynamic Numerical Processor & Wellbeing Calculator". |
| **UI: EditText Inputs** | `etFirstValue` and `etSecondValue`: Accept numerical values with `numberDecimal\|numberSigned`. |
| **UI: Operation Buttons** | `btnAdd`, `btnSubtract`, `btnMultiply`, `btnDivide` (+, −, ×, ÷). |
| **UI: TextView Result** | `tvResult`: Displays calculated result dynamically along with operation details. |
| **UI: Clear Button** | `btnClear`: Clears both EditText inputs, resets `tvResult`, restores default ink color, and resets button colors. |
| **Functionality: 4 Operations** | Performs Addition, Subtraction, Multiplication, and Division with exact floating-point / decimal formatting. |
| **Functionality: Empty Field Toast** | When either input is left empty, shows Toast: `"Please enter both numerical values!"` and highlights error on the field. |
| **Functionality: Division by Zero** | If denominator is `0.0`, sets `etSecondValue.error = "Divider cannot be 0"`, shows Toast `"Error: Division by zero is undefined!"`, and turns result text red. |
| **Functionality: Dynamic Result Color** | Result TextView changes color dynamically based on operation:<br>• **Addition**: Emerald Green (`#2E7D32`)<br>• **Subtraction**: Ocean Blue (`#1565C0`)<br>• **Multiplication**: Royal Purple (`#6A1B9A`)<br>• **Division**: Deep Orange (`#D84315`)<br>• **Error / Div by 0**: Crimson Red (`#C62828`) |
| **Challenge: Input Validation** | Strict numerical regex and `toDoubleOrNull()` parsing prevents invalid non-numeric data with helpful error toasts. |
| **Challenge: Button Click Color** | Active clicked operation button turns Golden Amber (`#FFB703`) with dark text to visually highlight the selected operation. |
| **Challenge: Operation with Result** | Displays both operation performed and result: e.g. `"Addition Performed: 25.0 + 10.0 = 35.0"`. |
| **Wellbeing Presets** | Includes quick calculation presets: Sleep Deficit (`8.0 - 6.5`) and Daily Water Goal (`70kg × 35ml`). |

---

## 🏗️ Project Architecture & File Structure

```
LAB MAD/
├── settings.gradle.kts                # Root project Gradle configuration
├── build.gradle.kts                   # Root build script
├── gradle.properties                  # AndroidX & JVM optimization flags
├── gradle/wrapper/
│   └── gradle-wrapper.properties      # Configured for Gradle 8.11.1
└── WellbeingApp/
    ├── settings.gradle.kts            # Standalone project Gradle configuration
    ├── build.gradle.kts               # Subproject build script
    └── app/
        ├── build.gradle.kts           # App module (compileSdk 35, ViewBinding, Material3)
        └── src/main/
            ├── AndroidManifest.xml    # App manifest with LoginActivity launcher, MainActivity & CalculatorActivity
            ├── java/com/angelsony/wellbeing/
            │   ├── LoginActivity.kt              # Screen 1: Welcome & Login with Guest demo mode
            │   ├── MainActivity.kt               # Central Hub with 5-tab Bottom Navigation & SOS bar
            │   ├── HomeFragment.kt               # Tab 1: Dashboard, Quotes, Insights & Water Tracker
            │   ├── MoodJournalFragment.kt        # Tab 2: Mood Emoji Check-in & Multimedia Journal
            │   ├── SleepRelaxFragment.kt         # Tab 3: Sleep Tracker, 4-7-8 Breathing & Ambient Sounds
            │   ├── ScheduleAssistantFragment.kt  # Tab 4: Counselor Appointments & Habit Tracker
            │   ├── CalculatorFragment.kt         # Tab 5: Lab Dynamic Calculator (Embedded)
            │   ├── CalculatorActivity.kt         # Standalone Lab Dynamic Calculator Activity
            │   ├── CalculatorController.kt       # Assignment Logic, Validation & Color Switcher
            │   ├── DataManager.kt                # Persistent Local Repository (SharedPreferences)
            │   ├── HomeActivity.kt               # Legacy Home screen (Kotlin)
            │   ├── MoodActivity.kt               # Legacy Mood screen (Kotlin)
            │   ├── SleepActivity.kt              # Legacy Sleep screen (Kotlin)
            │   ├── MeditateActivity.kt           # Legacy Meditate screen (Kotlin)
            │   └── Ui.kt                         # Edge-to-edge window insets handler
            └── res/
                ├── drawable/                     # 35+ custom vectors (lotus logo, mood emojis, nav icons, cards)
                ├── layout/                       # All XML layouts (ConstraintLayout, CoordinatorLayout, MaterialCardView)
                ├── menu/bottom_nav_menu.xml      # 5 Tab navigation menu
                └── values/                       # colors.xml, strings.xml, themes.xml (Light & Dark)
```

---

## 🚀 How to Run in Android Studio

1. Open **Android Studio** (Ladybug / Meerkat / Hedgehog or newer).
2. Choose **File > Open** and select the folder:
   ```
   C:\Users\angel\Documents\LAB MAD
   ```
   *(Or select the `WellbeingApp` folder directly — both are fully configured with Gradle!)*
3. Allow Android Studio to sync Gradle files (**File > Sync Project with Gradle Files**).
4. Select an Android Virtual Device (AVD Emulator with API 24 to 35) or connect a physical device via USB debugging.
5. Click the green **Run (▶)** button.
6. The app launches directly into `LoginActivity`:
   - Enter your name and click **"Sign In & Begin Journey"** or click **"Continue as Guest (Demo Mode)"** to immediately enter the dashboard.
   - Navigate across the 5 tabs to test Sleep, Mood, Journal, Breathing, Appointments, Habits, and the **Dynamic Calculator**!

