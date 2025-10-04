package menu;

import java.util.List;

import javax.swing.JOptionPane;

import servico.ServicoConsulta;
import servico.ServicoMedicamento;
import entidades.Consulta;
import entidades.Medicamento;
import entidades.Medico;
import servico.ServicoMedico;

public class MenuMedicamento extends Menu{
    private ServicoMedico servicoMedico = new ServicoMedico();
    private ServicoMedicamento servicoMedicamento = new ServicoMedicamento();
    private ServicoConsulta servicoConsulta = new ServicoConsulta();
    
    public void iniciar() {
        exibirMenu("GERENCIAR MÉDICOS",
                "1 - Criar Medicamento\n" +
                        "2 - Listar Medicamento\n" +
                        "3 - Buscar Medicamento\n" +
                        "4 - Atualizar Medicamento\n" +
                        "5 - Remover Medicamento\n" +
                        "6 - Adicionar Medicamento a Consulta");
    }

    @Override
    public void exibirMenu(String titulo, String opcoes) {
        int opcao = -1;
        do {
            try {
                opcao = Integer.parseInt(JOptionPane.showInputDialog("=== " + titulo + " ===\n" +
                        opcoes +
                        "\n0 - Voltar\n" +
                        "Escolha uma opção:"));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Opção inválida! Escolha um número correto.");
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
                    adicionarMedicamentoAConsulta();
                    break;
                case 0:
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opção Inválida.");
            }
        } while (opcao != 0);
    }

    @Override
    public void criar() {
        String crm = JOptionPane.showInputDialog("Informe o CRM do Médico: ");
        Medico medico = servicoMedico.buscar(new Medico(null, null, null, crm, null));
        if(medico == null){
            JOptionPane.showMessageDialog(null, "Médico NÃO ENCOTRADO", null, 0);
            return;
        }
        String nome = JOptionPane.showInputDialog("Nome do Medicamento: ");
        String dosagem = JOptionPane.showInputDialog("Dosagem do Medicamento: ");
        Medicamento medicamento = new Medicamento(nome, dosagem, null);
        servicoMedicamento.criar(medicamento);
    }
    public void adicionarMedicamentoAConsulta() {
        String crm = JOptionPane.showInputDialog("Informe o CRM do Médico: ");
        Medico medico = servicoMedico.buscar(new Medico(null, null, null, crm, null));
        if (medico == null) {
            JOptionPane.showMessageDialog(null, "Médico NÃO ENCONTRADO", null, 0);
            return;
        }

        List<Consulta> consultasDoMedico = servicoConsulta.listarConsultasPorMedico(medico);
        if (consultasDoMedico.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhuma consulta encontrada para o médico.", "INFO", 1);
            return;
        }

        StringBuilder consultas = new StringBuilder("Consultas do Médico: ").append(medico.getNome()).append(".\n");
        for (int i = 0; i < consultasDoMedico.size(); i++) {
            consultas.append(i + 1).append(" - ").append(consultasDoMedico.get(i).getData()).append("\n");
        }

        int opc = -1;
        try {
            opc = Integer.parseInt(JOptionPane.showInputDialog(null, consultas.toString())) - 1;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Entrada inválida! Tente novamente.");
            return;
        }

        if (opc < 0 || opc >= consultasDoMedico.size()) {
            JOptionPane.showMessageDialog(null, "Opção inválida! Escolha um número correto.");
            return;
        }

        Consulta consultaSelecionada = consultasDoMedico.get(opc);

        // Listar medicamentos e permitir a seleção de um medicamento
        List<Medicamento> medicamentos = servicoMedicamento.listar();
        if (medicamentos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum medicamento encontrado.", "INFO", 1);
            return;
        }

        StringBuilder medicamentosStr = new StringBuilder("Medicamentos disponíveis:\n");
        for (int i = 0; i < medicamentos.size(); i++) {
            medicamentosStr.append(i + 1).append(" - ").append(medicamentos.get(i).getNome()).append("\n");
        }

        int opcMedicamento = -1;
        try {
            opcMedicamento = Integer.parseInt(JOptionPane.showInputDialog(null, medicamentosStr.toString())) - 1;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Entrada inválida! Tente novamente.");
            return;
        }

        if (opcMedicamento < 0 || opcMedicamento >= medicamentos.size()) {
            JOptionPane.showMessageDialog(null, "Opção inválida! Escolha um número correto.");
            return;
        }

        Medicamento medicamentoSelecionado = medicamentos.get(opcMedicamento);

        servicoConsulta.adicionarMedicamentoAConsulta(consultaSelecionada, medicamentoSelecionado);
        JOptionPane.showMessageDialog(null, "Medicamento adicionado à consulta com sucesso!");
    }

    @Override
    public void listar() {
        String crm = JOptionPane.showInputDialog("Informe o CRM do Médico: ");
        Medico medico = servicoMedico.buscar(new Medico(null, null, null, crm, null));
        if(medico == null){
            JOptionPane.showMessageDialog(null, "Médico NÃO ENCOTRADO", null, 0);
            return;
        }
        JOptionPane.showMessageDialog(null, servicoMedicamento.listar(), null, 1);
    }

    @Override
    public void buscar() {
        String crm = JOptionPane.showInputDialog("Informe o CRM do Médico: ");
        Medico medico = servicoMedico.buscar(new Medico(null, null, null, crm, null));
        if(medico == null){
            JOptionPane.showMessageDialog(null, "Médico NÃO ENCOTRADO", null, 0);
            return;
        }
        String nome = JOptionPane.showInputDialog("Nome do Medicamento: ");
        Medicamento medicamento = servicoMedicamento.buscar(new Medicamento(nome, null, null));
        if(medicamento == null){
            JOptionPane.showMessageDialog(null, "Médico NÃO ENCOTRADO", null, 0);
            return;
        } else {
            JOptionPane.showMessageDialog(null, medicamento, null, 1);
        }
    }

    @Override
    public void atualizar() {
        String crm = JOptionPane.showInputDialog("Informe o CRM do Medico a ser Atualizada:");
        Medico medico = servicoMedico.buscar(new Medico(null, null, null, crm, null));

        if (medico == null) {
            JOptionPane.showMessageDialog(null, "Médico Não Encontrado.");
            return;
        }

        String nomeMedicamento = JOptionPane.showInputDialog("Informe o Nome do Medicamento: ");
        Medicamento medicamento = servicoMedicamento.buscar(new Medicamento(nomeMedicamento, null, null));
        if(medicamento == null){
            JOptionPane.showMessageDialog(null, "Medicamento Não Encontrado.", "ERRO", 0);
            return;
        }
        int opcao = -1;
        try {
            opcao = Integer.parseInt(JOptionPane.showInputDialog(
                    "O que deseja atualizar?\n" +
                            "1 - Nome\n" +
                            "2 - Dosagem\n" +
                            "0 - Voltar"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Opção inválida! Escolha um número correto.");
            return;
        }

        switch (opcao) {
            case 1:
                String nome = JOptionPane.showInputDialog("Informe o novo Nome: ");
                medicamento.setNome(nome);
                JOptionPane.showMessageDialog(null, "Atualizado Com Sucesso.");
                break;
            case 2:
                String dosagem = JOptionPane.showInputDialog("Informe a Dosagem: ");
                medicamento.setDosagem(dosagem);
                JOptionPane.showMessageDialog(null, "Atualizado Com Sucesso.");
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
        String crm = JOptionPane.showInputDialog("Informe o CRM do Médico: ");
        Medico medico = servicoMedico.buscar(new Medico(null, null, null, crm, null));
        if(medico == null){
            JOptionPane.showMessageDialog(null, "Médico NÃO ENCOTRADO", null, 0);
            return;
        }
        String nome = JOptionPane.showInputDialog("Nome do Medicamento: ");
        Medicamento medicamento = servicoMedicamento.buscar(new Medicamento(nome, null, null));
        servicoMedicamento.remover(medicamento);
        JOptionPane.showMessageDialog(null, "Medicamento Removido.", null, 1);
    }
    
}
