# Consultório App

Aplicativo Android desenvolvido como parte de um projeto acadêmico da disciplina de Programação Mobile, no curso de Análise e Desenvolvimento de Sistemas. O aplicativo tem como objetivo facilitar o agendamento de serviços odontológicos, permitindo que:

- Pacientes realizem marcações de consultas.
- Administradores visualizem, organizem e gerenciem os agendamentos.

O sistema utiliza o Firebase para autenticação de usuários e armazenamento de dados, garantindo um funcionamento eficiente e seguro.

---

## Índice

- [Funcionalidades](#funcionalidades)
- [Tecnologias utilizadas](#tecnologias-utilizadas)
- [Estrutura do projeto](#estrutura-do-projeto)
- [Pré-requisitos](#pré-requisitos)
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

```
consultorio/
├── adapters/
│   └── AgendamentoAdapter.kt              # Adaptador para RecyclerView de agendamentos
├── model/
│   └── Agendamento.kt                    # Modelo de dados para agendamentos
├── utils/
│   └── DBUtils.kt                        # Utilitários para Firebase
├── activities/
│   ├── MainActivity.kt                   # Tela inicial (login/cadastro)
│   ├── LoginActivity.kt                  # Tela de login
│   ├── CadastroActivity.kt               # Tela de cadastro de usuário
│   ├── ServicosActivity.kt               # Tela de seleção de serviços (paciente)
│   ├── VerificacaoAgendasActivity.kt     # Tela de seleção de serviços (admin)
│   ├── SelecionarDataActivity.java       # Tela de seleção de data
│   ├── SelecionarHorarioActivity.java    # Tela de seleção de horário
│   ├── ConfirmacaoAgendamentoActivity.java # Tela de confirmação
│   ├── AgendamentoFinalizadoActivity.kt  # Tela de confirmação final
│   ├── OrtodontiaAgendamentosActivity.kt # Lista de agendamentos de ortodontia
│   ├── ClareamentoAgendamentosActivity.kt # Lista de agendamentos de clareamento
│   └── AvaliacaoAgendamentosActivity.kt  # Lista de agendamentos de avaliação
└── res/
    ├── layout/                           # Layouts XML para todas as telas
    ├── drawable/                         # Ícones e imagens
    └── values/                           # Cores, strings, estilos
```

---

## Pré-requisitos

- Android Studio instalado
- SDK do Android
- JDK 11 ou superior

---

## Como executar

1. Clone o repositório:
```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
```

2. Abra o projeto no Android Studio.

3. Configure o Firebase:

   - Crie um projeto no Firebase Console
   - Adicione o arquivo `google-services.json` na pasta `app/`
   - Habilite Authentication (email/senha)
   - Configure o Firestore com as coleções esperadas (pacientes, agendamentos, etc.)

4. Execute o projeto em um emulador ou dispositivo real.

---

## Licença

Este projeto está licenciado sob a Licença MIT.

---

## Autores

- **Julio Guilherme** – Desenvolvedor backend
- **Kaio Siqueira Ramos** - Desenvolvedor frontend
- **Maria Vitória** - Designer de protótipo e desenvolvedora frontend
- **Matheus Chagas Gama** - Desenvolvedor frontend
- **Ronald Santos** - Desenvolvedor frontend
