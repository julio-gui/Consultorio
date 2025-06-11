# Consultório App

Aplicativo Android voltado para o agendamento de serviços odontológicos, permitindo que pacientes realizem marcações e que administradores visualizem e organizem os agendamentos. O sistema é integrado ao Firebase para autenticação e armazenamento de dados.

---

## Índice

- [Funcionalidades](#funcionalidades)
- [Tecnologias utilizadas](#tecnologias-utilizadas)
- [Estrutura do projeto](#estrutura-do-projeto)
- [Pré-requisitos](#pre-requisitos)
- [Como executar](#como-executar)
- [Licença](#licença)
- [Autores](#autores)

---

## Funcionalidades

### Paciente
- Autenticação com email e senha
- Recuperação de senha
- Agendamento de procedimentos (clareamento, ortodontia, avaliação)
- Visualização dos detalhes do agendamento realizado

### Administrador
- Login com redirecionamento para a tela de verificação de agendas
- Acesso a diferentes tipos de agendamentos por procedimento
- Marcação de consultas como finalizadas
- Logout da aplicação

---

## Tecnologias utilizadas

- **Android SDK**
- **Kotlin**
- **Firebase Authentication**
- **Firebase Firestore**
- **RecyclerView** para listar agendamentos
- **Material Design** (layouts com ConstraintLayout, LinearLayout)

---

## Estrutura do projeto

consultorio/
├── adapters/
│   └── AgendamentoAdapter.kt      # Adaptador para RecyclerView de agendamentos
├── model/
│   └── Agendamento.kt            # Modelo de dados para agendamentos
├── utils/
│   └── DBUtils.kt                # Utilitários para Firebase
├── activities/
│   ├── MainActivity.kt           # Tela inicial (login/cadastro)
│   ├── LoginActivity.kt          # Tela de login
│   ├── CadastroActivity.kt       # Tela de cadastro de usuário
│   ├── ServicosActivity.kt       # Tela de seleção de serviços (paciente)
│   ├── VerificacaoAgendasActivity.kt # Tela de seleção de serviços (admin)
│   ├── SelecionarDataActivity.java # Tela de seleção de data
│   ├── SelecionarHorarioActivity.java # Tela de seleção de horário
│   ├── ConfirmacaoAgendamentoActivity.java # Tela de confirmação
│   ├── AgendamentoFinalizadoActivity.kt # Tela de confirmação final
│   ├── OrtodontiaAgendamentosActivity.kt # Lista de agendamentos de ortodontia
│   ├── ClareamentoAgendamentosActivity.kt # Lista de agendamentos de clareamento
│   └── AvaliacaoAgendamentosActivity.kt # Lista de agendamentos de avaliação
└── res/
├── layout/                   # Layouts XML para todas as telas
├── drawable/                 # Ícones e imagens
└── values/                   # Cores, strings, estilos

## Pré-requisitos
    - Android Studio instalado
    - SDK do Android
    - JDK 11 ou superior

## Como executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/seu-repositorio.git
   
2. Abra o projeto no Android Studio.

3. Configure o Firebase:

4. Crie um projeto no Firebase Console

5. Adicione o arquivo google-services.json na pasta app/

6. Habilite Authentication (email/senha)

7. Configure o Firestore com as coleções esperadas (pacientes, agendamentos, etc.)

8. Execute o projeto em um emulador ou dispositivo real.

## Licença
    Este projeto está licenciado sob a Licença MIT.

## Autores
    Julio Guilherme – Desenvolvedor backend
    Kaio Siqueira Ramos - Desenvolvedor frontend
    Maria Vitória - Designer de protótipo e desenvolvedora frontend
    Matheus Chagas Gama - Desenvolvedor frontend
    Ronald Santos - Desenvolvedor frontend
