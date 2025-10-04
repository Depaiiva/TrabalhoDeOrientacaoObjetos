package menu;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.JOptionPane;
import entidades.Consulta;
import entidades.Medico;
import entidades.Paciente;
import servico.ServicoConsulta;
import servico.ServicoMedico;
import servico.ServicoPaciente;

public class MenuConsulta extends Menu {
    private ServicoConsulta servicoConsulta = new ServicoConsulta();
    private ServicoMedico servicoMedico = new ServicoMedico();
    private ServicoPaciente servicoPaciente = new ServicoPaciente();
    private final String[] horariosManha = { "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30" };
    private final String[] horariosTarde = { "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30" };
    private final String[] horariosNoite = { "18:30", "19:00", "19:30", "20:00", "20:30" };

    public void iniciar() {
        exibirMenu("GERENCIAR CONSULTAS",
                "1 - Criar Consulta\n" +
                        "2 - Listar Consultas Por Pacientes\n" +
                        "3 - Listar Consulta por Médico\n" +
                        "4 - Buscar Consulta\n" +
                        "5 - Atualizar Dados da Consulta\n" +
                        "6 - Remover Consulta\n");
    }

    @Override
    public void criar() {
        String cpfPaciente = JOptionPane.showInputDialog("Informe o CPF do paciente:", "000.000.000-00");
        Paciente paciente = servicoPaciente.buscar(new Paciente(cpfPaciente));

        if (paciente == null) {
            JOptionPane.showMessageDialog(null, "Paciente não encontrado!");
            return;
        }

        int opc = -1;
        try {
            opc = Integer.parseInt(JOptionPane.showInputDialog(
                    "Escolha o tipo de consulta:\n" +
                            "1- Cardiologia\n" +
                            "2- Clínica Geral\n" +
                            "3- Dermatologia\n" +
                            "4- Fisioterapia\n" +
                            "5- Fonoaudiologia\n" +
                            "6- Ginecologia\n" +
                            "7- Nutrologia\n" +
                            "8- Oftalmologia\n" +
                            "9- Ortopedia\n" +
                            "10- Pediatria\n"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Opção inválida! Escolha um número correto.");
            return;
        }

        List<Medico> medicosDisponiveis = servicoMedico.buscarMedicosPorEspecializacao(opc);
        if (medicosDisponiveis.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum médico disponível para essa especialidade.");
            return;
        }

        StringBuilder opcoes = new StringBuilder("Escolha um médico:\n");
        for (int i = 0; i < medicosDisponiveis.size(); i++) {
            opcoes.append(i + 1).append(" - ").append(medicosDisponiveis.get(i).getNome()).append("\n");
        }

        String escolhaStr = JOptionPane.showInputDialog(opcoes.toString());

        if (escolhaStr == null || escolhaStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Opção inválida.");
            return;
        }

        Medico medicoSelecionado = null;

        try {
            int escolhaMedico = Integer.parseInt(escolhaStr) - 1;
            if (escolhaMedico < 0 || escolhaMedico >= medicosDisponiveis.size()) {
                JOptionPane.showMessageDialog(null, "Opção inválida.");
                return;
            }

            medicoSelecionado = medicosDisponiveis.get(escolhaMedico);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Entrada inválida. Digite um número correspondente ao médico.");
            return;
        }

        String data;
        while (true) {
            data = JOptionPane.showInputDialog("Escolha a data da consulta (DD/MM/AAAA):");
            try {
                validarData(data);
                break;
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        String horario = selecionarHorario(data);
        if (horario == null) {
            return;
        }

        if (servicoConsulta.consultaMedicaNoMesmoDia(medicoSelecionado, data, horario)) {
            JOptionPane.showMessageDialog(null, "O médico já tem uma consulta nesse horário.");
            return;
        }

        if (servicoConsulta.consultaPacienteNoMesmoDia(paciente, data)) {
            JOptionPane.showMessageDialog(null, "O paciente já tem uma consulta nesse dia.");
            return;
        }

        servicoConsulta.agendarConsulta(paciente, medicoSelecionado, data, horario,
                servicoConsulta.converterEspecialidadeParaCodigo(opc));
    }

    @Override
    public void listar() {
        JOptionPane.showMessageDialog(null, servicoConsulta.listar().toString());
    }

    @Override
    public void buscar() {
        String data = JOptionPane.showInputDialog("Informe a data da consulta:");
        Consulta consulta = new Consulta(null, null, data, null, null, null);
        JOptionPane.showMessageDialog(null, servicoConsulta.buscar(consulta));
    }

    @Override
    public void atualizar() {
        String dataAtual = JOptionPane.showInputDialog("Informe a data da consulta a ser atualizada:");
        Consulta consulta = servicoConsulta.buscar(new Consulta(null, null, dataAtual, null, null, null));

        if (consulta == null) {
            JOptionPane.showMessageDialog(null, "Consulta não encontrada.");
            return;
        }
        int opcao = -1;
        try {
            opcao = Integer.parseInt(JOptionPane.showInputDialog(
                    "O que deseja atualizar?\n" +
                            "1 - Data\n" +
                            "2 - Horário\n" +
                            "3 - Médico\n" +
                            "4 - Status\n"));
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Opção inválida! Escolha um número correto.");
            return;
        }

        switch (opcao) {
            case 1:
                String novaData = JOptionPane.showInputDialog("Informe a nova data (DD/MM/AAAA):");
                consulta.setData(novaData);
                break;
            case 2:
                String novoHorario = selecionarHorario(dataAtual);
                consulta.setHorario(novoHorario);
                break;
            case 3:
                String nomeMedico = JOptionPane.showInputDialog("Informe o nome do novo médico:");
                Medico novoMedico = servicoMedico.buscar(new Medico(nomeMedico, null, null, null, null));
                if (novoMedico != null) {
                    consulta.setMedico(novoMedico);
                } else {
                    JOptionPane.showMessageDialog(null, "Médico não encontrado.");
                    return;
                }
                break;
            case 4:
                String novoStatus = JOptionPane
                        .showInputDialog("Informe o novo status (AGENDADA, CANCELADA, REALIZADA):");
                consulta.setStatus(novoStatus);
                break;
            default:
                JOptionPane.showMessageDialog(null, "Opção inválida.");
                return;
        }
    }

    @Override
    public void exibirMenu(String titulo, String opcoes) {
        int opcao = -1;
        do {
            try {
                opcao = Integer.parseInt(JOptionPane.showInputDialog("=== " + titulo + " ===\n" +
                        opcoes +
                        "0 - Voltar\n" +
                        "Escolha uma opção:"));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Opção inválida! Escolha um número correto.");
            }

            switch (opcao) {
                case 1:
                    criar();
                    break;
                case 2:
                    listarConsultasPaciente();
                    break;
                case 3:
                    listarConsultasMedico();
                    break;
                case 4:
                    buscar();
                    break;
                case 5:
                    atualizar();
                    break;
                case 6:
                    remover();
                    break;
                case 0:
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opção Inválida.");
            }
        } while (opcao != 0);
    }

    @Override
    public void remover() {
        String data = JOptionPane.showInputDialog("Informe a data da consulta:");
        Consulta consulta = new Consulta(null, null, data, null, null, null);
        servicoConsulta.remover(consulta);
    }

    public String selecionarHorario(String dataConsulta) {
        LocalDate hoje = LocalDate.now();
        LocalTime agora = LocalTime.now();

        int periodo = Integer.parseInt(JOptionPane.showInputDialog(
                "Escolha o período:\n" +
                        "1 - Manhã\n" +
                        "2 - Tarde\n" +
                        "3 - Noite"));

        String[] horariosDisponiveis;

        switch (periodo) {
            case 1:
                horariosDisponiveis = horariosManha;
                break;
            case 2:
                horariosDisponiveis = horariosTarde;
                break;
            case 3:
                horariosDisponiveis = horariosNoite;
                break;
            default:
                JOptionPane.showMessageDialog(null, "Período inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                return null;
        }

        StringBuilder opcoes = new StringBuilder("Escolha um horário:\n");
        for (int i = 0; i < horariosDisponiveis.length; i++) {
            opcoes.append((i + 1)).append(" - ").append(horariosDisponiveis[i]).append("\n");
        }
        opcoes.append("0").append(" - ").append("Voltar\n");

        int escolha;
        while (true) {
            try {
                escolha = Integer.parseInt(JOptionPane.showInputDialog(opcoes.toString())) - 1;

                if (escolha == -1) {
                    return null;
                }
                
                if (escolha < 0 || escolha >= horariosDisponiveis.length) {
                    JOptionPane.showMessageDialog(null, "Horário inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                String horarioEscolhido = horariosDisponiveis[escolha];
                LocalTime horarioSelecionado = LocalTime.parse(horarioEscolhido, DateTimeFormatter.ofPattern("HH:mm"));

                LocalDate dataSelecionada = LocalDate.parse(dataConsulta, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                if (dataSelecionada.isEqual(hoje) && horarioSelecionado.isBefore(agora)) {
                    JOptionPane.showMessageDialog(null, "Erro: O horário selecionado já passou!", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                return horarioEscolhido;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Entrada inválida. Digite um número correspondente ao horário.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void listarConsultasPaciente() {
        String cpf = JOptionPane.showInputDialog("Informe o CPF do Paciente: ", "000.000.000-00");
        Paciente p = servicoPaciente.buscar(new Paciente(cpf));
        if (p == null) {
            JOptionPane.showMessageDialog(null, "CPF inválido.", "ERRO", 0);
            return;
        }
        List<Consulta> historicoDeConsultasPaciente = p.getHistoricoConsultas();
        for (Consulta c : historicoDeConsultasPaciente) {
            JOptionPane.showMessageDialog(null, c.toString());
        }
    }

    public void listarConsultasMedico() {
        String crm = JOptionPane.showInputDialog("Informe o CRM do Médico");
        Medico medicoBuscar = servicoMedico.buscar(new Medico(null, null, null, crm, null));
        if (medicoBuscar == null) {
            JOptionPane.showMessageDialog(null, "CPF inválido.", "ERRO", 0);
            return;
        }
        List<Consulta> historicoDeConsultasMedico = medicoBuscar.getHistoricoConsultas();
        for (Consulta c : historicoDeConsultasMedico) {
            JOptionPane.showMessageDialog(null, c.toString());
        }
    }

    private void validarData(String data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            LocalDate dataConsulta = LocalDate.parse(data, formatter);
            LocalDate hoje = LocalDate.now();

            if (dataConsulta.isBefore(hoje)) {
                throw new IllegalArgumentException(
                        "Erro: Não é possível agendar consultas para datas que já passaram.");
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Erro: Data inválida. Use o formato DD/MM/AAAA.");
        }

    }
}
