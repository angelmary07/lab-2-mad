package com.angelsony.wellbeing

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ScheduleAssistantFragment : Fragment() {

    private lateinit var dataManager: DataManager
    private lateinit var tvWaterGlasses: TextView
    private lateinit var pbWaterProgress: ProgressBar
    private lateinit var layoutTodoList: LinearLayout
    private lateinit var tvBookedSessionInfo: TextView

    private val todoItems = mutableListOf(
        TodoItem(title = "Morning 10-minute sunlight & deep breath", isDone = true, priority = "High"),
        TodoItem(title = "Take afternoon mindful stretch pause", isDone = false, priority = "Normal"),
        TodoItem(title = "Hydration check: drink 250ml glass of water", isDone = true, priority = "Normal"),
        TodoItem(title = "Evening screen-free wind-down (09:30 PM)", isDone = false, priority = "High")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_schedule_assistant, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataManager = DataManager.getInstance(requireContext())

        setupAppointments(view)
        setupHabitsAndTodos(view)
    }

    private fun setupAppointments(view: View) {
        tvBookedSessionInfo = view.findViewById(R.id.tvBookedSessionInfo)
        tvBookedSessionInfo.text = "📅 ${dataManager.getBookedAppointment()}\nStatus: Confirmed • 1-hour prior notification active"

        view.findViewById<MaterialButton>(R.id.btnBookDrMaya)?.setOnClickListener {
            showBookingDialog("Dr. Maya Sharma, Ph.D. (Mindfulness & Stress)")
        }

        view.findViewById<MaterialButton>(R.id.btnBookDavid)?.setOnClickListener {
            showBookingDialog("David Vance, LPC (CBT Specialist)")
        }
    }

    private fun showBookingDialog(specialistName: String) {
        val slots = arrayOf(
            "Tuesday, 10:00 AM (Video Call)",
            "Wednesday, 04:30 PM (Video Call)",
            "Thursday, 11:15 AM (Audio Consultation)",
            "Friday, 02:00 PM (In-Person Clinic)"
        )

        AlertDialog.Builder(requireContext())
            .setTitle("Book with $specialistName")
            .setItems(slots) { _, which ->
                val chosenSlot = slots[which]
                val details = "$specialistName — $chosenSlot"
                dataManager.setBookedAppointment(details)
                tvBookedSessionInfo.text = "📅 $details\nStatus: Confirmed • Notification set!"
                Toast.makeText(
                    requireContext(),
                    "Session successfully scheduled! Added to calendar.",
                    Toast.LENGTH_LONG
                ).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun setupHabitsAndTodos(view: View) {
        tvWaterGlasses = view.findViewById(R.id.tvWaterGlasses)
        pbWaterProgress = view.findViewById(R.id.pbWaterProgress)
        layoutTodoList = view.findViewById(R.id.layoutTodoList)

        updateWaterUi()

        view.findViewById<MaterialButton>(R.id.btnPlusWater).setOnClickListener {
            val count = dataManager.incrementWater()
            updateWaterUi()
            Toast.makeText(requireContext(), "Hydration updated: $count/8 glasses 💧", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<MaterialButton>(R.id.btnMinusWater).setOnClickListener {
            val count = dataManager.decrementWater()
            updateWaterUi()
        }

        // To-Do List Add
        val etNewTodo = view.findViewById<EditText>(R.id.etNewTodo)
        view.findViewById<MaterialButton>(R.id.btnAddTodo).setOnClickListener {
            val text = etNewTodo.text.toString().trim()
            if (text.isEmpty()) {
                etNewTodo.error = "Please enter task name"
                Toast.makeText(requireContext(), "Task name cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            todoItems.add(0, TodoItem(title = text, isDone = false))
            etNewTodo.text?.clear()
            renderTodos()
            Toast.makeText(requireContext(), "Task added to Daily Routine!", Toast.LENGTH_SHORT).show()
        }

        renderTodos()
    }

    private fun updateWaterUi() {
        val count = dataManager.getWaterGlasses()
        val ml = count * 250
        tvWaterGlasses.text = "$count of 8 Glasses ($ml ml / 2000 ml)"
        pbWaterProgress.progress = count.coerceAtMost(8)
    }

    private fun renderTodos() {
        layoutTodoList.removeAllViews()
        for (item in todoItems) {
            val cb = CheckBox(requireContext()).apply {
                text = item.title
                isChecked = item.isDone
                textSize = 13f
                setTextColor(android.graphics.Color.parseColor("#1F2421"))
                setOnCheckedChangeListener { _, checked ->
                    item.isDone = checked
                    val status = if (checked) "Completed" else "Pending"
                    Toast.makeText(requireContext(), "Task marked $status: ${item.title}", Toast.LENGTH_SHORT).show()
                }
            }
            layoutTodoList.addView(cb)
        }
    }
}

