package com.example.consultorio

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import com.example.consultorio.utils.DBUtils

class VerificacaoAgendasActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.verificacao_agendas)

        val botaoSair = findViewById<LinearLayout>(R.id.botaoSair)
        botaoSair.setOnClickListener {
            Toast.makeText(this, "Saindo...", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            DBUtils.auth.signOut()
            startActivity(intent)
            finish()
        }
        val botaoOrto = findViewById<LinearLayout>(R.id.botaoOrto)
        botaoOrto.setOnClickListener {
            val intent = Intent(this, OrtodontiaAgendamentosActivity::class.java)
            startActivity(intent)
        }
    }
}