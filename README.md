# TrabalhoDeOrientacaoObjetos
Feito por Carlos Henrique de Paiva Munis com a matricula 221022480.
tem como objetivo de representar um sistema de uma clinica médica.
Como Funciona a Aplicação
A aplicação é organizada em três camadas principais:

Domínio (Entidades):
São representadas as classes que descrevem os elementos do sistema, como Paciente, Médico, Consulta, Exame, Pagamento e Prescrição. Cada uma dessas classes armazena os dados relevantes e possui métodos para manipular e acessar as informações de forma segura.

Serviços:
A camada de serviços contém a lógica de negócio responsável pelas operações essenciais do sistema, como cadastro, busca, atualização e remoção de registros. Por meio de uma estrutura genérica, os serviços operam sobre os diferentes tipos de entidades, permitindo a reutilização de código e a aplicação consistente das regras de negócio (por exemplo, validação de datas e horários, verificação de disponibilidade, controle de pagamentos pendentes e bloqueio de CPF/CRM duplicados).

Interface (Menus):
A camada de interface é composta por menus e telas de diálogo que interagem com o usuário. Através de caixas de diálogo, o sistema coleta informações, exibe mensagens e delega as operações para os serviços. Essa separação garante que a lógica de negócio esteja isolada da apresentação, tornando a aplicação mais robusta e fácil de atualizar.

Principais Funcionalidades
Cadastro e Atualização de Pacientes e Médicos:

Registro de informações pessoais e históricas (como consultas e exames).
Validação de dados para evitar duplicidade (por exemplo, CPF ou CRM já registrados).
Agendamento de Consultas:

Agendamento com validação da disponibilidade de médicos e evitando conflitos no mesmo dia para o paciente.
Validação de datas para impedir agendamentos em dias passados.
Escolha de horário por período (manhã, tarde ou noite) com verificação se o horário já passou no dia atual.
Prescrição de Exames e Medicamentos:

Associação de exames e prescrições às consultas, permitindo o acompanhamento dos procedimentos solicitados.
Gestão de Pagamentos:

Vinculação de cada atendimento aos seus respectivos pagamentos, possibilitando o controle de pendências e facilitando a geração de relatórios financeiros.
Execução
Para executar o sistema, certifique-se de ter um ambiente Java configurado (JDK 8 ou superior). Compile as classes e execute a classe principal (por exemplo, a classe que inicializa os menus do sistema). A interface utiliza caixas de diálogo para interagir com o usuário, o que torna o sistema intuitivo e fácil de utilizar.

Conclusão
Este projeto demonstra a aplicação prática dos conceitos de orientação a objetos em um cenário real, atendendo às necessidades de uma clínica médica com uma estrutura modular e de fácil manutenção. A integração das camadas de domínio, serviços e os menu garante que as operações sejam realizadas de forma consistente e segura, facilitando a evolução e adaptação do sistema conforme novas demandas surgirem.
