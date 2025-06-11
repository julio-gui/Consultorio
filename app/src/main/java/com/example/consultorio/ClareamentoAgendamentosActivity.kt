package com.example.consultorio

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.consultorio.adapters.AgendamentoAdapter
import com.example.consultorio.model.Agendamento
import com.example.consultorio.utils.DBUtils.firestore

class ClareamentoAgendamentosActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AgendamentoAdapter
    private val listaAgendamentos = mutableListOf<Agendamento>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.agendamentos)

        val titulo = findViewById<TextView>(R.id.titulo)
        titulo.text = "Agendamentos - Clareamentos"

        recyclerView = findViewById(R.id.recycler_agendamentos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = AgendamentoAdapter(listaAgendamentos) { agendamento, position ->
            mostrarDialogoFinalizar(agendamento, position)
        }
        recyclerView.adapter = adapter

        carregarAgendamentosClareamento()
    }

    private fun mostrarDialogoFinalizar(agendamento: Agendamento, position: Int) {
        AlertDialog.Builder(this)
            .setTitle("Finalizar agendamento")
            .setMessage("Deseja marcar este agendamento como finalizado?")
            .setPositiveButton("Sim") { _, _ ->
                firestore.collection("agendamentos")
                    .whereEqualTo("data", agendamento.data)
                    .whereEqualTo("horario", agendamento.horario)
                    .whereEqualTo("userId", agendamento.userId)
                    .whereEqualTo("servico", agendamento.servico)
                    .limit(1)
                    .get()
                    .addOnSuccessListener { docs ->
                        if (!docs.isEmpty) {
                            val doc = docs.documents[0].reference
                            doc.update("finalizado", true)
                                .addOnSuccessListener {
                                    listaAgendamentos.removeAt(position)
                                    adapter.notifyItemRemoved(position)
                                    Toast.makeText(this, "Agendamento finalizado", Toast.LENGTH_SHORT).show()
                                }
                        }
                    }
            }
            .setNegativeButton("Voltar", null)
            .show()
    }

    private fun carregarAgendamentosClareamento() {
        firestore.collection("agendamentos")
            .whereEqualTo("servico", "Clareamento")
            .whereEqualTo("finalizado", false)
            .get()
            .addOnSuccessListener { resultado ->
                listaAgendamentos.clear()
                for (doc in resultado) {
                    val agendamento = doc.toObject(Agendamento::class.java)
                    listaAgendamentos.add(agendamento)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Erro ao carregar agendamentos", Toast.LENGTH_SHORT).show()
            }
    }
}
