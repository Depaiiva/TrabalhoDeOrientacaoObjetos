package menu;

import java.util.List;

import javax.swing.JOptionPane;

import entidades.Consulta;
import entidades.Exame;
import entidades.Medico;
import entidades.Paciente;
import servico.ServicoConsulta;
import servico.ServicoExame;
import servico.ServicoMedico;
import servico.ServicoPaciente;

public class MenuExame extends Menu {
    private ServicoMedico servicoMedico = new ServicoMedico();
    private ServicoExame servicoExame = new ServicoExame();
    private ServicoPaciente servicoPaciente = new ServicoPaciente();

    public void iniciar() {
        exibirMenu("GERENCIAR EXAMES:",
                "1 - Criar Exame\n" +
                        "2 - Listar Exames\n" +
                        "3 - Buscar Exame\n" +
                        "4 - Atualizar Exame\n" +
                        "5 - Remover Exame\n" +
                        "6 - Preescrever Exame Para Paciente\n" +
                        "7 - Resultados dos Exames\n");
    }

    public Medico buscarMedico() {
        String crm = JOptionPane.showInputDialog("Informe o CRM do Médico");
        Medico medico = new Medico(null, null, null, crm, null);
        Medico medicoBuscado = servicoMedico.buscar(medico);
        if (medicoBuscado == null) {
            JOptionPane.showMessageDialog(null, "MÉDICO NÃO ENCONTRADO.");
            return null;
        }
        return medicoBuscado;
    }

    @Override
    public void criar() {
        Medico m = buscarMedico();
        if (m == null) {
            return;
        }
        String tipoExame = JOptionPane.showInputDialog("Tipo do Exame:");
        float custo = Float.parseFloat(JOptionPane.showInputDialog("Informe o custo do Exame."));
        Exame exame = new Exame(tipoExame, custo);
        servicoExame.criar(exame);
    }

    @Override
    public void listar() {
        Medico m = buscarMedico();
        if (m == null) {
            return;
        }
        JOptionPane.showMessageDialog(null, servicoExame.listar().toString());
    }

    @Override
    public void buscar() {
        Medico m = buscarMedico();
        if (m == null) {
            return;
        }
        String tipoExames = JOptionPane.showInputDialog("Informe o tipo de Exame");
        Exame exame = new Exame(tipoExames);
        Exame exameBuscado = servicoExame.buscar(exame);
        if (exameBuscado == null) {
            JOptionPane.showMessageDialog(null, "ERRO: EXAME NÃO ENCONTRADO");
            return;
        }
        JOptionPane.showMessageDialog(null, exameBuscado.toString());
    }

    @Override
    public void atualizar() {
        Medico medico = buscarMedico();
        if (medico == null) {
            return;
        }
        StringBuilder consultasDoMedico = new StringBuilder("Consultas do Médico: ").append(medico.getNome())
                .append(".\n");
        for (int i = 0; i < ServicoConsulta.getConsultas().size(); i++) {
            if (medico.getCrm().equals(ServicoConsulta.getConsultas().get(i).getMedico().getCrm())) {
                consultasDoMedico.append(i + 1).append(" - ").append(ServicoConsulta.getConsultas().get(i));
            }
        }
        if (consultasDoMedico.toString().equals("Consultas do Médico: " + medico.getNome() + ".\n")) {
            JOptionPane.showMessageDialog(null, "Este médico não possui consultas registradas.");
            return;
        }

        int opc = -1;
        try {
            opc = Integer.parseInt(JOptionPane.showInputDialog(null, consultasDoMedico.toString())) - 1;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Entrada inválida! Tente novamente.");
            return;
        }

        if (opc < 0 || opc >= ServicoConsulta.getConsultas().size()) {
            JOptionPane.showMessageDialog(null, "Opção inválida! Escolha um número correto.");
            return;
        }

        Consulta c = ServicoConsulta.getConsultas().get(opc);

        int opcao = -1;
        try {
            opcao = Integer.parseInt(JOptionPane.showInputDialog("O que você deseja fazer?\n" +
                    "1 - Adicionar Exame:" +
                    "2 - Alterar Exame:"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Opção inválida! Escolha um número correto.");
        }
        switch (opcao) {
            case 1:
                List<Exame> examesCriados = servicoExame.listar();
                String tipoExame = JOptionPane.showInputDialog("Qual tipo de exame deseja Adicionar?");
                for (Exame e : examesCriados) {
                    if (tipoExame.equalsIgnoreCase(e.getTipoDeExame())) {
                        Exame criarExame = new Exame(tipoExame);
                        c.adicionarExame(servicoExame.buscar(criarExame));
                        JOptionPane.showMessageDialog(null, "Exame Criado e Adicionado à Consulta.");
                    }
                }
                break;
            case 2:
                List<Exame> exameDeConsulta = c.getExamesPrescritos();
                String pegaExame = JOptionPane.showInputDialog("Qual Exame deseja Alterar?");
                for (int i = 0; i < exameDeConsulta.size(); i++) {
                    if (pegaExame.equals(exameDeConsulta.get(i).getTipoDeExame())) {
                        servicoExame.remover(c.getExamesPrescritos().get(i));
                        String novoExame = JOptionPane.showInputDialog("Qual é o novo tipo de Exame?");
                        Exame exameNovo = new Exame(novoExame);
                        servicoExame.criar(exameNovo);
                        c.adicionarExame(exameNovo);
                        JOptionPane.showMessageDialog(null, "Exame Criado e Alterado com Sucesso.");
                    }
                }

            default:
                break;
        }
    }

    @Override
    public void remover() {
        buscarMedico();
        String tipoConsulta = JOptionPane.showInputDialog("Qual Exame você deseja excluir?");
        Exame e = new Exame(tipoConsulta);
        Exame exameBuscado = servicoExame.buscar(e);
        servicoExame.remover(exameBuscado);
    }

    public void resultadoDoExame() {
        // Buscar o médico
        Medico medico = buscarMedico();
        if (medico == null) {
            return;
        }
    
        String cpf = JOptionPane.showInputDialog("Informe o CPF do paciente:");
        Paciente paciente = servicoPaciente.buscar(new Paciente(cpf));
        if (paciente == null) {
            JOptionPane.showMessageDialog(null, "Paciente Não Consta no Sistema", "ERRO", 0);
            return;
        }
    

        List<Exame> examesDoPaciente = servicoExame.listarExamesPorPaciente(paciente);
        if (examesDoPaciente.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Este paciente não possui exames prescritos.", "INFO", 1);
            return;
        }
    
        StringBuilder exames = new StringBuilder("Exames do Paciente: ").append(paciente.getNome()).append(".\n");
        for (int i = 0; i < examesDoPaciente.size(); i++) {
            exames.append(i + 1).append(" - ").append(examesDoPaciente.get(i).getTipoDeExame()).append("\n");
        }
    
        int opc = -1;
        try {
            opc = Integer.parseInt(JOptionPane.showInputDialog(null, exames.toString())) - 1;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Entrada inválida! Tente novamente.");
            return;
        }
    
        if (opc < 0 || opc >= examesDoPaciente.size()) {
            JOptionPane.showMessageDialog(null, "Opção inválida! Escolha um número correto.");
            return;
        }
    
        Exame exameSelecionado = examesDoPaciente.get(opc);
        String resultado = JOptionPane.showInputDialog("Informe o resultado do exame " + exameSelecionado.getTipoDeExame() + ":");
        exameSelecionado.setResultadoDoExame(resultado);
    
        JOptionPane.showMessageDialog(null, "Resultado do exame atualizado com sucesso!");
    }

    public void prescreverExameParaPaciente() {
        String crm = JOptionPane.showInputDialog(null, "Informe o CRM do Médico: ", null, 3);
        Medico medico = servicoMedico.buscar(new Medico(null, null, null, crm, null));
        if (medico == null) {
            JOptionPane.showMessageDialog(null, "Medico Não Consta no Sistema", "ERRO", 0);
            return;
        }
        String cpf = JOptionPane.showInputDialog("Informe o CPF do paciente:");
        Paciente p = servicoPaciente.buscar(new Paciente(cpf));
        if (p == null) {
            JOptionPane.showMessageDialog(null, "Paciente Não Consta no Sistema", "ERRO", 0);
            return;
        }
        String exame = JOptionPane.showInputDialog("Informe o Exame:");
        Exame e = servicoExame.buscar(new Exame(exame));
        if (e == null) {
            JOptionPane.showMessageDialog(null, "Exame Não Consta no Sistema", "ERRO", 0);
            return;
        }
        servicoExame.prescreverExame(p, e);
    }

    public void listarExamesPorPacientes() {
        String cpf = JOptionPane.showInputDialog("Informe o CPF do paciente: ");
        Paciente p = new Paciente(cpf);
        servicoExame.listarExamesPorPaciente(p);
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
                    prescreverExameParaPaciente();
                    break;
                case 7:
                    resultadoDoExame();
                case 0:
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opção Inválida.");
            }
        } while (opcao != 0);
    }
}
