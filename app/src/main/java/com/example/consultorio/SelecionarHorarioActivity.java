package com.example.consultorio;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SelecionarHorarioActivity extends AppCompatActivity {

    private GridLayout gridHorarios;
    private String[] horarios = {
            "9:00", "10:00", "11:00",
            "13:00", "14:00", "15:00",
            "16:00", "17:00", "18:00"
    };

    private String horarioSelecionado = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecionar_horario);

        gridHorarios = findViewById(R.id.gridHorarios);
        Button btnConfirmar = findViewById(R.id.btnConfirmarHorario);

        for (String hora : horarios) {
            TextView tv = new TextView(this);
            tv.setText(hora);
            tv.setTextColor(Color.parseColor("#2196F3")); // Azul
            // tv.setBackgroundResource(R.drawable.borda_azul); // Borda azul
            tv.setTextSize(20);
            tv.setTypeface(null, Typeface.BOLD);
            tv.setPadding(32, 32, 32, 32);
            tv.setGravity(Gravity.CENTER);
            tv.setClickable(true);
            tv.setFocusable(true);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.setMargins(10, 10, 10, 10);
            tv.setLayoutParams(params);

            tv.setOnClickListener(v -> {
                horarioSelecionado = hora;
                Toast.makeText(this, "Horário selecionado: " + hora, Toast.LENGTH_SHORT).show();
            });

            gridHorarios.addView(tv);
        }

        btnConfirmar.setOnClickListener(v -> {
            if (horarioSelecionado != null) {
                String dataSelecionada = getIntent().getStringExtra("data");
                String servico = getIntent().getStringExtra("servico");
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> agendamento = new HashMap<>();
                agendamento.put("userId", userId);
                agendamento.put("data", dataSelecionada);
                agendamento.put("horario", horarioSelecionado);
                agendamento.put("servico", servico);

                db.collection("agendamentos")
                        .whereEqualTo("data", dataSelecionada)
                        .whereEqualTo("horario", horarioSelecionado)
                        .get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                Toast.makeText(this, "Horário indisponível. Escolha outro.", Toast.LENGTH_SHORT).show();
                            } else {
                                agendamento.put("userId", userId);
                                agendamento.put("data", dataSelecionada);
                                agendamento.put("horario", horarioSelecionado);
                                agendamento.put("servico", servico);

                                db.collection("agendamentos")
                                        .add(agendamento)
                                        .addOnSuccessListener(documentReference -> {
                                            Toast.makeText(this, "Confirmado: " + horarioSelecionado, Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(this, MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            finish();

                                        })
                                        .addOnFailureListener(e -> {
                                            Toast.makeText(this, "Erro ao salvar agendamento: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                        });
                            }
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(this, "Erro ao verificar disponibilidade: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        });
            } else {
                Toast.makeText(this, "Selecione um horário primeiro", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

