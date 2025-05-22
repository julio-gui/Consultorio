package com.example.consultorio

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CadastroActivity : AppCompatActivity() {
    private val TAG = "CadastroActivity"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cadastro)

        val btnVoltar = findViewById<Button>(R.id.button_voltar)
        btnVoltar.setOnClickListener {
            finish()
        }

        val btnConcluir = findViewById<Button>(R.id.button_concluir)
        btnConcluir.setOnClickListener {
            registrarUsuario()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun registrarUsuario() {
        val inputName = findViewById<EditText>(R.id.input_name)
        val inputNumber = findViewById<EditText>(R.id.input_number)
        val inputEmail = findViewById<EditText>(R.id.input_email)
        val inputPassword = findViewById<EditText>(R.id.input_password)
        val inputConfirmPassword = findViewById<EditText>(R.id.input_confirm_password)

        if (inputName.text.toString().trim().isEmpty() ||
            inputNumber.text.toString().trim().isEmpty() ||
            inputEmail.text.toString().trim().isEmpty() ||
            inputPassword.text.toString().trim().isEmpty()) {
            showToast("Preencha todos os campos obrigatórios")
            return
        }

        if (inputPassword.text.toString() != inputConfirmPassword.text.toString()) {
            showToast("As senhas não coincidem")
            return
        }

        showToast("Cadastrando...")

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val paciente = mapOf(
                    "nome" to inputName.text.toString().trim(),
                    "numero" to inputNumber.text.toString().trim(),
                    "email" to inputEmail.text.toString().trim(),
                    "senha" to inputPassword.text.toString()
                )

                Log.d(TAG, "Tentando conectar ao Supabase...")

                val response = SupabaseClient.supabase.postgrest["pacientes"].insert(paciente)
                Log.d(TAG, "Resposta do Supabase: $response")

                withContext(Dispatchers.Main) {
                    showToast("Cadastro realizado com sucesso!")
                    startActivity(Intent(this@CadastroActivity, MainActivity::class.java))
                    finish()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Erro no cadastro", e)
                withContext(Dispatchers.Main) {
                    showToast("Erro ao cadastrar: ${e.message ?: "Erro desconhecido"}")
                }
            }
        }
    }

    private fun showToast(message: String) {
        runOnUiThread {
            Toast.makeText(this@CadastroActivity, message, Toast.LENGTH_LONG).show()
        }
    }
}