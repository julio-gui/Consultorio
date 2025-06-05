package com.example.consultorio

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.consultorio.utils.DBUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.bem_vindo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tela_bem_vindo)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        checkUserLogged()

        val criarLoginBtn = findViewById<Button>(R.id.btnCriarLogin)
        criarLoginBtn.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }

        val buttonLogin = findViewById<Button>(R.id.button_login)
        buttonLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun checkUserLogged() {
        if (DBUtils.auth.currentUser != null) {
            startActivity(Intent(this, ServicosActivity::class.java))
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        checkUserLogged()
    }
}