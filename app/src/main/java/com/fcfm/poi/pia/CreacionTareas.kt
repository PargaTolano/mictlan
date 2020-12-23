package com.fcfm.poi.pia

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.fcfm.poi.pia.modelos.Assignment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import kotlinx.android.synthetic.main.activity_creacion_tareas.*
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class CreacionTareas : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    var day=0
    var month=0
    var year=0
    var hour=0
    var minute=0

    var saveday=0
    var savemonth=0
    var saveyear=0
    var savehour=0
    var saveminute=0

    private lateinit var chatroomId : String

    val database = FirebaseDatabase.getInstance();
    var chatroomRef     = database.getReference("chatrooms");
    private lateinit var assignmentRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creacion_tareas)

        chatroomId  = intent.getStringExtra("chatroomId") ?: ""

        if(chatroomId.isEmpty()){
            finish();
        }

        assignmentRef = chatroomRef.child(chatroomId).child("chats");

        pickDate()
        pickHour()

        asignarBtn.setOnClickListener{
            if( tituloText.text.isNotEmpty()        &&
                tituloText.text.isNotBlank()        &&
                instruccionesText.text.isNotEmpty() &&
                instruccionesText.text.isNotBlank()
              )
            {
                val tt = tituloText.text.toString()
                val it = instruccionesText.text.toString()

                //Sacar Date Time Hecho String
                val localDate = LocalDate.of(saveyear,savemonth,saveday)
                val localTime = LocalTime.of(savehour, saveminute)
                val localDT   = LocalDateTime.of(localDate!!, localTime!!)

                val pt = puntosText.text.toString().toInt()

                createAssignment(Assignment("", tt, it, pt, localDT))

                val intent = Intent(this, dashBoardActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun createAssignment(assignment:Assignment){
        val assignmentFirebase = assignmentRef.push()
        assignment.id = assignmentFirebase.key ?: ""
        assignmentFirebase.setValue(assignment)
    }

    private fun getDateTimeCalendar(){
        val cal= Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)
    }

    private fun pickDate(){
        tv_date1.setOnClickListener {
            getDateTimeCalendar()

            DatePickerDialog(this,this,year,month,day).show()
        }
    }

    private  fun pickHour(){
        tv_timer1.setOnClickListener {
            getDateTimeCalendar()

            TimePickerDialog(this, this,hour,minute,true).show()

        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        saveday = dayOfMonth
        savemonth = month
        saveyear = year

        tv_dia1.text="$saveday-$savemonth-$saveyear"
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savehour= hourOfDay
        saveminute=minute

        tv_hora1.text="Hora:$savehour Minuto:$saveminute"
    }
    /* class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

         override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
             // Use the current time as the default values for the picker
             val c = Calendar.getInstance()
             val hour = c.get(Calendar.HOUR_OF_DAY)
             val minute = c.get(Calendar.MINUTE)

             // Create a new instance of TimePickerDialog and return it
             return TimePickerDialog(this.context, this, hour, minute, true)

         }

         override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
             // Do something with the time chosen by the user

         }


     }

     fun showTimePickerDialog(v: View) {
         TimePickerFragment().show(supportFragmentManager, "timePicker")
     }

     class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

         override fun onCreateDialog(savedInstanceState: Bundle?): DatePickerDialog {
             // Use the current date as the default date in the picker
             val c = Calendar.getInstance()
             val year = c.get(Calendar.YEAR)
             val month = c.get(Calendar.MONTH)
             val day = c.get(Calendar.DAY_OF_MONTH)

             // Create a new instance of DatePickerDialog and return it
             return DatePickerDialog(this.requireContext(), this,year, month, day)
         }


         override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
             // Do something with the date chosen by the user


         }
     }

     fun showDatePickerDialog(v: View) {
         val newFragment = DatePickerFragment()
         newFragment.show(supportFragmentManager, "datePicker")
     }*/
}
