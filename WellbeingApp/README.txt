SMART WELLBEING COMPANION, setup (2 minutes)

1. Android Studio > New Project > "Empty Views Activity"
   Name: Wellbeing | Package: com.angelsony.wellbeing | Language: Java | Min SDK: API 24
2. In the project, DELETE: MainActivity.java and res/layout/activity_main.xml
3. Copy the contents of this zip's app/src/main/ into your project's app/src/main/
   and choose "Overwrite" when asked (AndroidManifest.xml, colors, strings, themes).
4. If the template created res/values-night/themes.xml with a different theme, the
   one in this zip replaces it. Make sure there is no leftover red error in themes.
5. File > Sync Project with Gradle Files, then Run.

Layouts used: ConstraintLayout (Login), LinearLayout (Home), RelativeLayout (Mood),
TableLayout (Sleep), FrameLayout (Meditate).
