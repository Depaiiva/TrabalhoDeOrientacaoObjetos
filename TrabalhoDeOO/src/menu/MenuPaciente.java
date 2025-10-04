package menu;

import java.util.List;

import javax.swing.JOptionPane;

import entidades.Paciente;
import servico.ServicoPaciente;

public class MenuPaciente extends Menu {
    private ServicoPaciente servicoPaciente = new ServicoPaciente();

    public void iniciar() {
        exibirMenu("GERENCIAR PACIENTES:",
                        "1 - Criar Paciente\n" +
                        "2 - Listar Pacientes\n" +
                        "3 - Buscar Paciente\n" +
                        "4 - Atualizar Paciente\n" +
                        "5 - Remover Paciente\n");
    }

    @Override
    public void criar() {
        String nome = JOptionPane.showInputDialog("Nome do paciente: ");
        String cpf = JOptionPane.showInputDialog("CPF: ", "000.000.000-00");
        Paciente testePaciente = new Paciente(cpf);
        if (servicoPaciente.blockCpfRep(testePaciente)) {
            String dataNascimento = JOptionPane.showInputDialog("Data de Nascimento: ", "(DD/MM/AAAA)");
            Paciente paciente = new Paciente(nome, cpf, dataNascimento);
            servicoPaciente.criar(paciente);
            JOptionPane.showMessageDialog(null, "Paciente Cadastrado com Sucesso.");
        }
        return;
    }

    @Override
    public void listar() {
        listarPacientes();
    }

    @Override
    public void buscar() {
        String cpfBusca = JOptionPane.showInputDialog("Informe o CPF do paciente: ", "000.000.000-00");
        Paciente paciente = new Paciente(cpfBusca);
        Paciente pacienteEncontrado = servicoPaciente.buscar(paciente);
        JOptionPane.showMessageDialog(null, pacienteEncontrado);
    }

    @Override
    public void atualizar() {
        String cpf = JOptionPane.showInputDialog("Informe o CPF do paciente a ser atualizada:");
        Paciente paciente = servicoPaciente.buscar(new Paciente(cpf));

        if (paciente == null) {
            JOptionPane.showMessageDialog(null, "Paciente Não Encontrado.");
            return;
        }
        int opcao = -1;
        try {
            opcao = Integer.parseInt(JOptionPane.showInputDialog(
                    "O que deseja atualizar?\n" +
                            "1 - Nome\n" +
                            "2 - CPF\n" +
                            "3 - Data de Nascimento\n"));
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Opção inválida! Escolha um número correto.");
            return;
        }

        switch (opcao) {
            case 1:
                String nome = JOptionPane.showInputDialog("Informe o Nome:");
                paciente.setNome(nome);
                JOptionPane.showMessageDialog(null, "Atualizado Com Sucesso.");
                break;
            case 2:
                String cpF = JOptionPane.showInputDialog("Informe o CPF", "000.000.000-00");
                paciente.setCpf(cpF);
                JOptionPane.showMessageDialog(null, "Atualizado Com Sucesso.");
                break;
            case 3:
                String dataNascimento = JOptionPane.showInputDialog("Informe a Data de Nascimento", "(DD/MM/AAAA)");
                paciente.setDataNasciemento(dataNascimento);
                JOptionPane.showMessageDialog(null, "Atualizado Com Sucesso.");
                break;
            default:
                JOptionPane.showMessageDialog(null, "Opção inválida.");
                return;
        }
    }

    @Override
    public void remover() {
        String cpfBusca = JOptionPane.showInputDialog("Informe o CPF do paciente: ");
        Paciente pacienteEncontrado = new Paciente(cpfBusca);
        servicoPaciente.remover(pacienteEncontrado);
    }

    public void listarPacientes() {
        List<Paciente> pacientes = servicoPaciente.listar();

        if (pacientes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum paciente cadastrado.");
            return;
        }

        StringBuilder listaFormatada = new StringBuilder("Lista de Pacientes:\n");
        listaFormatada.append("===================================\n");

        for (Paciente p : pacientes) {
            listaFormatada.append(p.toString()).append("\n");
        }

        JOptionPane.showMessageDialog(null, listaFormatada.toString());
    }

}
