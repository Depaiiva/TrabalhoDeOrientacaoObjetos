package menu;

import javax.swing.JOptionPane;

import entidades.Consulta;
import entidades.Medico;
import servico.ServicoMedico;

public class MenuMedico extends Menu {
    private ServicoMedico servicoMedico = new ServicoMedico();
    private final String especialidades = "1 - Cardiologista\n" +
            "2 - Clinico(a) Geral\n" +
            "3 - Dermatologista\n" +
            "4 - Fisioterapeuta\n" +
            "5 - Fonoaudiologia\n" +
            "6 - Ginecologista\n" +
            "7 - Nutricionista\n" +
            "8 - Oftalmologista\n" +
            "9 - Ortopedista\n" +
            "10 - Pediatra\n";

    public void iniciar() {
        exibirMenu("GERENCIAR MÉDICOS",
                "1 - Criar Médico\n" +
                        "2 - Listar Médicos\n" +
                        "3 - Buscar Médico\n" +
                        "4 - Atualizar Médico\n" +
                        "5 - Remover Médico\n" +
                        "6 - Exibir Historico Médico\n");
    }

    @Override
    public void criar() {
        String nome = JOptionPane.showInputDialog("Informe o nome do Médico: ");
        String crm = JOptionPane.showInputDialog("Informe o CRM do Médico: ", "000000/DF");
        if (servicoMedico.blockCrmRep(crm)) {
            JOptionPane.showMessageDialog(null, "ERRO: CRM JÁ EXISTE NO SISTEMA.");
            return;
        }
        String cpf = JOptionPane.showInputDialog("Informe o CPF do Médico: ", "000.000.000-00");
        if (servicoMedico.blockCpfRep(cpf)) {
            JOptionPane.showMessageDialog(null, "ERRO: CPF JÁ EXISTE NO SISTEMA.");
            return;
        }
        String dataNascimento = JOptionPane.showInputDialog("Informe a data de nascimento do Médico: ",
                "(DD/MM/AAAA)");
        int especialidade = -1;
        try {
            especialidade = Integer
                    .parseInt(JOptionPane.showInputDialog("Informe o especialidade do Médico: \n" + especialidades));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Opção inválida! Escolha um número correto.");
        }
        if (especialidade > 0 && especialidade < 11) {
            Medico medico = new Medico(nome, cpf, dataNascimento, crm,
                    servicoMedico.converterEspecialidadeParaCodigo(especialidade));
            servicoMedico.criar(medico);
            JOptionPane.showMessageDialog(null, "Médico Criado com Sucesso.");
            return;
        } else {
            JOptionPane.showMessageDialog(null, "Opção Invalida.");
            return;
        }
    }

    @Override
    public void listar() {
        JOptionPane.showMessageDialog(null, servicoMedico.listar().toString());
    }

    @Override
    public void buscar() {
        String crmBuscar = JOptionPane.showInputDialog("informe o CRM do médico: ", "000000/DF");
        Medico medico = new Medico(null, null, null, crmBuscar, null);
        JOptionPane.showMessageDialog(null, servicoMedico.buscar(medico));
    }

    @Override
    public void atualizar() {
        String crm = JOptionPane.showInputDialog("Informe o CRM do Medico a ser Atualizada:", "000000/DF");
        Medico medico = servicoMedico.buscar(new Medico(null, null, null, crm, null));

        if (medico == null) {
            JOptionPane.showMessageDialog(null, "Médico Não Encontrado.");
            return;
        }
        int opcao = -1;
        try {
            opcao = Integer.parseInt(JOptionPane.showInputDialog(
                    "O que deseja atualizar?\n" +
                            "1 - Nome\n" +
                            "2 - CPF\n" +
                            "3 - Data de Nascimento\n" +
                            "4 - Especialidade\n" +
                            "5 - CRM\n" +
                            "0 - Voltar"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Opção inválida! Escolha um número correto.");
            return;
        }

        switch (opcao) {
            case 1:
                String nome = JOptionPane.showInputDialog("Informe o Nome:");
                medico.setNome(nome);
                JOptionPane.showMessageDialog(null, "Atualizado Com Sucesso.");
                break;
            case 2:
                String cpF = JOptionPane.showInputDialog("Informe o CPF", "000000/DF");
                medico.setCpf(cpF);
                JOptionPane.showMessageDialog(null, "Atualizado Com Sucesso.");
                break;
            case 3:
                String dataNascimento = JOptionPane.showInputDialog("Informe a Data de Nascimento", "(DD/MM/AAAA)");
                medico.setDataNasciemento(dataNascimento);
                JOptionPane.showMessageDialog(null, "Atualizado Com Sucesso.");
                break;
            case 4:
                int opc = -1;
                try {
                    opc = Integer.parseInt(
                            JOptionPane.showInputDialog("Informe o especialidade do Médico: \n" + especialidades));
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Opção inválida! Escolha um número correto.");
                }
                if (opc > 0 && opc < 11) {
                    medico.setEspecialidade(servicoMedico.converterEspecialidadeParaCodigo(opc));
                    JOptionPane.showMessageDialog(null, "Atualizado Com Sucesso.");
                } else {
                    JOptionPane.showMessageDialog(null, "Opção Invalida.");
                }
                break;
            case 5:
                while (true) {
                    String crmNovo = JOptionPane.showInputDialog("Informe o CRM", "000000/DF");
                    if (crmNovo == null) {
                        break;
                    }
                    if (servicoMedico.blockCrmRep(crmNovo)) {
                        JOptionPane.showMessageDialog(null, "ERRO: CRM JÁ EXISTE. Digite novamente.");
                        continue;
                    }
                    medico.setCrm(crmNovo);
                    JOptionPane.showMessageDialog(null, "Atualizado Com Sucesso.");
                    break;
                }
                break;
            case 0:
                return;
            default:
                JOptionPane.showMessageDialog(null, "Opção inválida.");
                return;
        }
    }

    @Override
    public void remover() {
        String crm = JOptionPane.showInputDialog("Informe o CRM do médico: ", "000000/DF");
        Medico medico = new Medico(null, null, null, crm, null);
        servicoMedico.remover(medico);
    }

    public void exibirHistoricoMedico() {
        String crm = JOptionPane.showInputDialog("Digite o CRM do médico:", "000000/DF");
        Medico medicoEncontrado = servicoMedico.buscar(new Medico(null, null, null, crm, null));
        if (medicoEncontrado == null) {
            JOptionPane.showMessageDialog(null, "Médico não encontrado!");
            return;
        }

        StringBuilder historico = new StringBuilder("Histórico Médico de " + medicoEncontrado.getNome() + ":\n");

        for (Consulta c : medicoEncontrado.getHistoricoConsultas()) {
            historico.append("Paciente: ").append(c.getPaciente().getNome())
                    .append(" | Data: ").append(c.getData())
                    .append(" | Horário: ").append(c.getHorario()).append("\n");
        }

        JOptionPane.showMessageDialog(null, historico);
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
                continue;
            }

            switch (opcao) {
                case 1:
                    criar();
                    break;
                case 2:
                    listar();
                    break;
                case 3:
                    buscar();
                    break;
                case 4:
                    atualizar();
                    break;
                case 5:
                    remover();
                    break;
                case 6:
                    exibirHistoricoMedico();
                    break;
                case 0:
                    break;
                default:
            }
        } while (opcao != 0);
    }

}
