package com.example.consultorio

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.consultorio.utils.DBUtils.auth
import com.example.consultorio.utils.DBUtils.firestore

class ServicosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.servicos)

        checkUserRole()

        val buttonSair = findViewById<Button>(R.id.button_sair)
        buttonSair.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            auth.signOut()
            startActivity(intent)
            finish()
        }
        val buttonAgendarOrtodontia = findViewById<Button>(R.id.btn_agendar_ortodontia)
        val buttonAgendarClareamento = findViewById<Button>(R.id.btn_agendar_clareamento)
        val buttonAgendarAvaliacao = findViewById<Button>(R.id.btn_agendar_avaliacao)
        buttonAgendarOrtodontia.setOnClickListener {
            val intent = Intent(this, SelecionarDataActivity::class.java)
            intent.putExtra("servico", "Ortodontia")
            startActivity(intent)
        }
        buttonAgendarClareamento.setOnClickListener {
            val intent = Intent(this, SelecionarDataActivity::class.java)
            intent.putExtra("servico", "Clareamento")
            startActivity(intent)
        }
        buttonAgendarAvaliacao.setOnClickListener {
            val intent = Intent(this, SelecionarDataActivity::class.java)
            intent.putExtra("servico", "Avaliacao")
            startActivity(intent)
        }
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, ServicosActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
        }
    }

    private fun checkUserRole() {
        val uid = auth.currentUser?.uid ?: return
        firestore.collection("pacientes").document(uid).get()
            .addOnSuccessListener {
                val role = it.getString("role")
                if (role == "admin") {
                    startActivity(Intent(this, VerificacaoAgendasActivity::class.java))
                    finish()
                }
            }
    }
}