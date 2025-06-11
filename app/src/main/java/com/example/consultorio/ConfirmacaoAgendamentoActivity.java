package com.example.consultorio;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ConfirmacaoAgendamentoActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private String userId;
    private Boolean finalizado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmacao_agendamento);
        db = FirebaseFirestore.getInstance();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        buscarDadosPaciente();
    }

    private void buscarDadosPaciente() {
        db.collection("pacientes")
                .document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String nomePaciente = documentSnapshot.getString("nome");
                        String dataRecebida = getIntent().getStringExtra("data");
                        String horario = getIntent().getStringExtra("horario");
                        String servico = getIntent().getStringExtra("servico");
                        String dataFormatada = formatarData(dataRecebida);
                        TextView tvPaciente = findViewById(R.id.tvPaciente);
                        TextView tvData = findViewById(R.id.tvData);
                        TextView tvHorario = findViewById(R.id.tvHorario);
                        tvPaciente.setText("Paciente: " + nomePaciente);
                        tvData.setText("Data: " + dataFormatada);
                        tvHorario.setText("Horário: " + horario);
                        configurarBotaoConfirmar(nomePaciente, dataRecebida, horario, servico, finalizado);
                    } else {
                        Toast.makeText(this, "Paciente não encontrado", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Erro ao buscar dados do paciente", Toast.LENGTH_SHORT).show();
                    finish();
                });
    }

    private void configurarBotaoConfirmar(String nomePaciente, String dataFormatada, String horario, String servico, Boolean finalizado) {
        Button btnConfirmar = findViewById(R.id.btnConfirmar);
        btnConfirmar.setOnClickListener(v -> {
            Map<String, Object> agendamento = new HashMap<>();
            agendamento.put("userId", userId);
            agendamento.put("paciente", nomePaciente);
            agendamento.put("data", dataFormatada);
            agendamento.put("horario", horario);
            agendamento.put("servico", servico);
            agendamento.put("finalizado", finalizado);
            db.collection("agendamentos")
                    .whereEqualTo("data", dataFormatada)
                    .whereEqualTo("horario", horario)
                    .get()
                    .addOnSuccessListener(querySnapshot -> {
                        if (querySnapshot.isEmpty()) {
                            confirmarAgendamento(agendamento, nomePaciente, dataFormatada, horario, servico, finalizado);
                        } else {
                            Toast.makeText(this, "Horário indisponível", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Erro ao verificar horário", Toast.LENGTH_SHORT).show();
                    });
        });
    }

    private void confirmarAgendamento(Map<String, Object> agendamento, String nome, String data, String horario, String servico, Boolean finalizado) {
        db.collection("agendamentos")
                .add(agendamento)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Agendamento confirmado!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, AgendamentoFinalizadoActivity.class);
                    intent.putExtra("paciente", nome);
                    intent.putExtra("data", data);
                    intent.putExtra("horario", horario);
                    intent.putExtra("servico", servico);
                    intent.putExtra("finalizado", false);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Erro ao confirmar agendamento", Toast.LENGTH_SHORT).show();
                });
    }

    private String formatarData(String dataOriginal) {
        try {
            String[] partes = dataOriginal.split("/");
            String dia = partes[0].length() == 1 ? "0" + partes[0] : partes[0];
            String mes = partes[1].length() == 1 ? "0" + partes[1] : partes[1];
            return dia + "/" + mes + "/" + partes[2];
        } catch (Exception e) {
            return dataOriginal;
        }
    }
}