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

        val dataFormatada = formatarData(agendamento.data)
        holder.tvAgendamento.text = "${agendamento.paciente}\n${dataFormatada} às ${agendamento.horario}"

        holder.itemView.setOnClickListener {
            onItemClick(agendamento, position)
        }
    }

    private fun formatarData(dataOriginal: String): String? {
        try {
            val partes =
                dataOriginal.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val dia = if (partes[0].length == 1) "0" + partes[0] else partes[0]
            val mes = if (partes[1].length == 1) "0" + partes[1] else partes[1]
            return dia + "/" + mes + "/" + partes[2]
        } catch (e: Exception) {
            return dataOriginal
        }
    }
}
