package com.example.consultorio.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.consultorio.R
import com.example.consultorio.model.Agendamento

class AgendamentoAdapter(
    private val lista: List<Agendamento>,
    private val onItemClick: (Agendamento, Int) -> Unit) :
    RecyclerView.Adapter<AgendamentoAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtData: TextView = itemView.findViewById(R.id.txt_data)
        val txtHora: TextView = itemView.findViewById(R.id.txt_hora)
        val txtPaciente: TextView = itemView.findViewById(R.id.txt_paciente)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_agendamento, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val agendamento = lista[position]
        holder.txtData.text = "Data: ${agendamento.data}"
        holder.txtHora.text = "Horário: ${agendamento.horario}"
        holder.txtPaciente.text = "Paciente: ${agendamento.paciente}"
        holder.itemView.setOnClickListener {
            onItemClick(agendamento, position)
        }
    }
}
