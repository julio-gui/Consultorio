package com.example.consultorio.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.consultorio.R
import com.example.consultorio.model.Agendamento

class AgendamentoAdapter(
    private val lista: List<Agendamento>,
    private val onItemClick: (Agendamento, Int) -> Unit) :
    RecyclerView.Adapter<AgendamentoAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //val ivCalendar: ImageView = itemView.findViewById(R.id.iv_calendar)
        val tvAgendamento: TextView = itemView.findViewById(R.id.tv_agendamento)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_agendamento, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val agendamento = lista[position]

        holder.tvAgendamento.text = "${agendamento.paciente}\n${agendamento.data} às ${agendamento.horario}"

        holder.itemView.setOnClickListener {
            onItemClick(agendamento, position)
        }
    }
}
