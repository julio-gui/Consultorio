package com.example.consultorio

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CadastroActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cadastro)

        // Inicializa Firebase Auth e Firestore
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val btnVoltar = findViewById<Button>(R.id.button_voltar)
        btnVoltar.setOnClickListener {
            finish()
        }

        val btnConcluir = findViewById<Button>(R.id.button_concluir)
        btnConcluir.setOnClickListener {
            registrarUsuario()
        }
    }

    private fun registrarUsuario() {
        val inputName = findViewById<EditText>(R.id.input_name)
        val inputNumber = findViewById<EditText>(R.id.input_number)
        val inputEmail = findViewById<EditText>(R.id.input_email)
        val inputPassword = findViewById<EditText>(R.id.input_password)
        val inputConfirmPassword = findViewById<EditText>(R.id.input_confirm_password)

        // Validação dos campos
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

        val email = inputEmail.text.toString().trim()
        val password = inputPassword.text.toString().trim()

        // Criação do usuário no Firebase Auth
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Usuário criado com sucesso no Auth, agora salvar dados adicionais no Firestore
                    val user = auth.currentUser
                    val userId = user?.uid ?: ""

                    val paciente = hashMapOf(
                        "nome" to inputName.text.toString().trim(),
                        "numero" to inputNumber.text.toString().trim(),
                        "email" to email,
                        // Não armazenamos a senha no Firestore por segurança
                        "uid" to userId
                    )

                    // Salva os dados do paciente no Firestore
                    db.collection("pacientes")
                        .document(userId)
                        .set(paciente)
                        .addOnSuccessListener {
                            showToast("Cadastro realizado com sucesso!")
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                        .addOnFailureListener { e ->
                            showToast("Erro ao salvar dados do paciente: ${e.message}")
                            // Opcional: deletar o usuário criado no Auth se falhar no Firestore
                            user?.delete()
                        }
                } else {
                    showToast("Erro no cadastro: ${task.exception?.message ?: "Erro desconhecido"}")
                }
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@CadastroActivity, message, Toast.LENGTH_LONG).show()
    }
}