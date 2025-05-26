package com.example.consultorio

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.consultorio.utils.FirebaseUtils.auth
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        auth = FirebaseAuth.getInstance()

        val inputEmail = findViewById<EditText>(R.id.input_email)
        val inputPassword = findViewById<EditText>(R.id.input_password)
        val buttonEntrar = findViewById<Button>(R.id.button_entrar)
        val buttonEsqueciSenha = findViewById<Button>(R.id.button_esqueci_senha)

        buttonEntrar.setOnClickListener {
            val email = inputEmail.text.toString().trim()
            val password = inputPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Mostrar progresso (opcional)
            Toast.makeText(this, "Autenticando...", Toast.LENGTH_SHORT).show()

            // Autenticar com Firebase
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Login bem-sucedido
                        val intent = Intent(this, AgendamentosActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Login falhou
                        Toast.makeText(
                            this,
                            "Credenciais incorretas",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        buttonEsqueciSenha.setOnClickListener {
            // Implementar recuperação de senha posteriormente
            Toast.makeText(this, "Funcionalidade em desenvolvimento", Toast.LENGTH_SHORT).show()
        }
    }
}