package com.fcfm.poi.pia.adaptadores

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.fcfm.poi.pia.R
import com.fcfm.poi.pia.modelos.Assignment
import kotlinx.android.synthetic.main.assignment_list.view.*
import java.text.SimpleDateFormat
import java.util.*

class AssignmentAdapter(private val assignmentList : MutableList<Assignment>) :
    RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder>(){

    class AssignmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun asignarInformacion(assignment: Assignment) {
            itemView.assignmentTitle.text = assignment.titulo;
            val dateString = String.format("%d/%d/%d %d:%d",
                assignment.timestamp!!.dia,
                assignment.timestamp!!.mes,
                assignment.timestamp!!.anio,
                assignment.timestamp!!.hora,
                assignment.timestamp!!.minuto
            );
            itemView.assignmentDueDate.text = dateString
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssignmentAdapter.AssignmentViewHolder {

        return AssignmentAdapter.AssignmentViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.assignment_list, parent, false)
            //No deberia ser assigment_list(nombre del elemento el layout), el que muestra la tarea?
        )
    }

    override fun onBindViewHolder(holder: AssignmentAdapter.AssignmentViewHolder, position: Int) {

        holder.asignarInformacion(assignmentList[position])
    }

    override fun getItemCount(): Int = assignmentList.size
}