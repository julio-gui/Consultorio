package com.example.consultorio

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException

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

            Toast.makeText(this, "Autenticando...", Toast.LENGTH_SHORT).show()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, AgendamentosActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            "Credenciais incorretas",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        buttonEsqueciSenha.setOnClickListener {
            val email = inputEmail.text.toString().trim()
            if (email.isEmpty()) {
                Toast.makeText(this, "Digite seu email para redefinir a senha", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            AlertDialog.Builder(this)
                .setTitle("Redefinir senha")
                .setMessage("Enviar email de redefinição para $email?")
                .setPositiveButton("Enviar") { _, _ -> redefinirSenha(email) }
                .setNegativeButton("Cancelar", null)
                .show()
        }
    }
    private fun redefinirSenha(email: String) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Digite um email válido", Toast.LENGTH_SHORT).show()
            return
        }

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this,
                        "Email de redefinição enviado para $email",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    val errorMsg = when (task.exception) {
                        is FirebaseAuthInvalidUserException -> "Email não cadastrado"
                        else -> "Erro ao enviar email. Tente novamente."
                    }
                    Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
                }
            }
    }
}