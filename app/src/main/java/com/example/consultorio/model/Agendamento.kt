package com.example.consultorio.model

data class Agendamento(
    val data: String = "",
    val horario: String = "",
    val paciente: String = "",
    val servico: String = "",
    val userId: String = "",
    val finalizado: Boolean = false
)
