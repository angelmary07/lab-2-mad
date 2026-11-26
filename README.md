# Mobile Application Development (LAB MAD) Coursework Repository
**Student:** Angel Mary Sony (`angelmary07`)  
**Institution:** Christ University  
**Tech Stack:** Kotlin, Android XML, Material Design 3, Gradle 8.11.1 / AGP 8.7.2  
**Repository:** [https://github.com/angelmary07/lab-2-mad](https://github.com/angelmary07/lab-2-mad)

---

## 📂 Project Organization & Directory Structure

This repository is organized into **three distinct lab exercise folders** as per the university syllabus rubrics, alongside the full-fledged **MindWell Companion** 8-pillar production app:

```
LAB MAD/
├── settings.gradle.kts                # Multi-project orchestrator for all 4 modules
├── build.gradle.kts                   # Root build script (AGP 8.7.2 + Kotlin 2.0.21)
├── README.md                          # Comprehensive submission documentation
│
├── Lab_1A_Layouts/                    # 📁 LAB EXERCISE 1A: 5 Layouts Implementation
│   ├── build.gradle.kts
│   ├── settings.gradle.kts
│   └── app/src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/angelsony/lab1a/
│       │   ├── MainActivity.kt               # Central Hub navigating to all 5 layouts
│       │   ├── ConstraintLayoutActivity.kt   # Layout 1: ConstraintLayout (Patient Intake)
│       │   ├── LinearLayoutActivity.kt       # Layout 2: LinearLayout (Vitals Dashboard)
│       │   ├── RelativeLayoutActivity.kt     # Layout 3: RelativeLayout (Mood Assessment)
│       │   ├── TableLayoutActivity.kt        # Layout 4: TableLayout (Sleep Matrix)
│       │   └── FrameLayoutActivity.kt        # Layout 5: FrameLayout (Zen Breathing Timer)
│       └── res/layout/
│           ├── activity_main.xml             # Hub layout
│           ├── activity_constraint.xml       # ConstraintLayout implementation
│           ├── activity_linear.xml           # LinearLayout with layout_weight
│           ├── activity_relative.xml         # RelativeLayout with relative alignments
│           ├── activity_table.xml            # TableLayout with TableRows
│           └── activity_frame.xml            # FrameLayout with stacked Z-layers
│
├── Lab_1B_Views/                      # 📁 LAB EXERCISE 1B: 7 Required Views Implementation
│   ├── build.gradle.kts
│   ├── settings.gradle.kts
│   └── app/src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/angelsony/lab1b/
│       │   └── MainActivity.kt               # Interactive logic wiring all 7 views + Live Readout
│       └── res/layout/
│           └── activity_main.xml             # ScrollView containing SeekBar, Spinner, CheckBox,
│                                             # RadioButton, Switch, and Coordinator FAB
│
├── Lab_2_UserInteraction/             # 📁 LAB EXERCISE 2: User Interaction & Dynamic Processing
│   ├── build.gradle.kts
│   ├── settings.gradle.kts
│   └── app/src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/angelsony/lab2/
│       │   ├── MainActivity.kt               # Launcher & View binding
│       │   └── CalculatorController.kt       # +, -, *, /, Clear, Toast, Div/0, Dynamic Colors,
│       │                                     # Click highlight & Operation display
│       └── res/layout/
│           └── activity_main.xml             # Material 3 Dynamic Calculator UI
│
└── WellbeingApp/                      # 🌟 PRODUCTION APP: MindWell Companion (8 Pillars)
    └── app/src/main/java/com/angelsony/wellbeing/
        ├── MainActivity.kt                   # 5-Tab Navigation & SOS Emergency Bar
        ├── LoginActivity.kt                  # Auth & Guest Demo Mode
        ├── HomeFragment.kt                   # Daily Overview, Quotes, Water Intake
        ├── MoodJournalFragment.kt            # Mood Emojis & Multimedia Vault
        ├── SleepRelaxFragment.kt             # Sleep Tracker, 4-7-8 Breathing, Ambient Sounds
        ├── ScheduleAssistantFragment.kt      # Therapist Appointments & Habit Checklist
        └── CalculatorFragment.kt             # Integrated Lab 2 Calculator module
```

---

## 🎯 Lab-by-Lab Rubric Verification

### 1. Lab Exercise 1A: Implement Different Types of Layouts (100 pts)
* **Domain Selected:** MindWell Healthcare & Mental Health Companion (Rubric: 1 Mark)
* **Five Layouts Implemented (Rubric: 5 Marks):**
  1. **`ConstraintLayout` (`activity_constraint.xml`)**: Patient intake registration form with chained guidelines, relative widget constraints, and input fields.
  2. **`LinearLayout` (`activity_linear.xml`)**: Vitals & biometrics dashboard with horizontal/vertical orientations and weighted score cards (`layout_weight="1"`).
  3. **`RelativeLayout` (`activity_relative.xml`)**: Daily clinical mood assessment form using `layout_toRightOf`, `layout_below`, `layout_alignParentBottom`.
  4. **`TableLayout` (`activity_table.xml`)**: Weekly sleep tracking matrix structured using `<TableRow>` items, headers, numeric rows, and average calculation.
  5. **`FrameLayout` (`activity_frame.xml`)**: Zen meditation room with stacked Z-layers (background canvas, circular pulsing halo, centered timer label, and floating controls).
* **UI Components (Rubric: 2 Marks):** `TextView`, `EditText`, `ImageView`, `Button`, `CheckBox`, `RadioButton`.
* **Application Execution (Rubric: 2 Marks):** Clean navigation hub in `MainActivity.kt` opens and demonstrates all 5 layouts on click.

---

### 2. Lab Exercise 1B: Implement Different Types of Views (100 pts)
Demonstrates the **7 required views** in a unified, interactive clinical intake and wellness monitor:
1. **`SeekBar`**: Stress & energy gauge (0–100) with dynamic status categories ("Low Stress", "Moderate", "Elevated").
2. **`FloatingActionButton (FAB)`**: Quick-submission floating action button with white vector icon and drop shadow.
3. **`ScrollView`**: Fluid vertical scrolling container wrapping all cards and form components.
4. **`Spinner`**: Dropdown specialist selector (Clinical Psychology, CBT, Sleep Clinic, Wellbeing Coaching).
5. **`CheckBox`**: Lifestyle checklist (Sleep, Hydration, Exercise, Mindfulness) updating live counters.
6. **`RadioButton` & `RadioGroup`**: Consultation mode selector (Video, In-Person clinic, Audio call).
7. **`Switch` (`MaterialSwitch`)**: Toggle switches for automated bedtime alerts (10:30 PM) and 24/7 SOS dispatch.
* **Bonus Feature:** Real-time **Dynamic Views Summary (Evaluator Readout)** card at the bottom that dynamically reflects updates from all 7 views as the user interacts with them.

---

### 3. Lab Exercise 2: User Interaction & Dynamic Processing (100 pts)
Dynamic numerical calculator built strictly using **Kotlin** and **XML**:
* **UI Components:**
  * `TextView` (Title): "Dynamic Numerical Processor & Wellbeing Calculator"
  * `EditText` (Inputs): `etFirstValue` and `etSecondValue` with numerical decimal keyboards.
  * `Button` (Operations): `+`, `−`, `×`, `÷`.
  * `TextView` (Result): Dynamic result display with operation description.
  * `Button` (Clear): Resets inputs, result label, and button highlights.
* **Functional Logic:**
  * **Addition, Subtraction, Multiplication, Division**: Floating-point and integer formatted math.
  * **Toast on Empty Fields**: Warning toast displayed if inputs are blank.
  * **Division by Zero Protection**: Intercepts division by `0.0`, displays `"Error: Division by zero is undefined!"`, marks `etSecondValue.error`, and paints the result crimson red.
  * **Clear Operation**: Restores default ink color, wipes inputs, and resets state.
  * **Dynamic Color Switching**:
    * Addition ➔ Emerald Green (`#2E7D32`)
    * Subtraction ➔ Ocean Blue (`#1565C0`)
    * Multiplication ➔ Royal Purple (`#6A1B9A`)
    * Division ➔ Deep Orange (`#D84315`)
    * Division by Zero Error ➔ Crimson Red (`#C62828`)
  * **Button Click Color Highlight**: Active operator button changes to Golden Amber (`#FFB703`) with dark text.
  * **Display Operation Performed**: Results show full equation: e.g., `"Addition Performed: 25.0 + 10.0 = 35.0"`.

---

## 🚀 How to Run

### Option A: Open Whole Repository in Android Studio
1. Launch **Android Studio**.
2. Select **File > Open** and open `C:\Users\angel\Documents\LAB MAD`.
3. Android Studio detects all 4 modules (`:Lab_1A_Layouts`, `:Lab_1B_Views`, `:Lab_2_UserInteraction`, `:WellbeingApp`).
4. Select any lab module from the top Run Configurations dropdown and click **Run (▶)**.

### Option B: Open an Individual Lab
1. Select **File > Open** and browse directly to:
   * `Lab_1A_Layouts` for 5 Layouts
   * `Lab_1B_Views` for 7 Views
   * `Lab_2_UserInteraction` for Dynamic Calculator
2. Each folder contains its own self-contained Gradle configuration ready for immediate build and execution.
