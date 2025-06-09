package com.example.consultorio

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.consultorio.utils.DBUtils

class ServicosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.servicos)

        val buttonSair = findViewById<Button>(R.id.button_sair)
        buttonSair.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            DBUtils.auth.signOut()
            startActivity(intent)
            finish()
        }
        val buttonAgendarOrtodontia = findViewById<Button>(R.id.btn_agendar_ortodontia)
        buttonAgendarOrtodontia.setOnClickListener {
            val intent = Intent(this, SelecionarDataActivity::class.java)
            startActivity(intent)
        }
    }
}