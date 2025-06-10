package com.example.consultorio

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AgendamentoFinalizadoActivity : AppCompatActivity() {

    private lateinit var txtDetalhes: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.agendamento_finalizado)

        txtDetalhes = findViewById(R.id.appointment_details_text)

        findViewById<ImageView>(R.id.corner_icon).setOnClickListener {
            startActivity(ServicosActivity.createIntent(this))
            finish()
        }

        val nome = intent.getStringExtra("paciente")
        val servico = intent.getStringExtra("servico")
        val data = intent.getStringExtra("data")
        val horario = intent.getStringExtra("horario")

        val detalhes = """
            Paciente: $nome
            Procedimento: $servico
            Data: $data
            Horário: $horario hrs
        """.trimIndent()

        txtDetalhes.text = detalhes
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        startActivity(ServicosActivity.createIntent(this))
        finish()
    }

}
