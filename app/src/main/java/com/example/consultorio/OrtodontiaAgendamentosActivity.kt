package com.example.consultorio

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.consultorio.adapters.AgendamentoAdapter
import com.example.consultorio.model.Agendamento
import com.example.consultorio.utils.DBUtils.firestore

class OrtodontiaAgendamentosActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AgendamentoAdapter
    private val listaAgendamentos = mutableListOf<Agendamento>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.agendamentos)

        recyclerView = findViewById(R.id.recycler_agendamentos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = AgendamentoAdapter(listaAgendamentos)
        recyclerView.adapter = adapter

        carregarAgendamentosOrtodontia()
    }

    private fun carregarAgendamentosOrtodontia() {
        firestore.collection("agendamentos")
            .whereEqualTo("servico", "Ortodontia")
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
