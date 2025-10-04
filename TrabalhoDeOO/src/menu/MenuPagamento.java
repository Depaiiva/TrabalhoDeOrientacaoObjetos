package menu;

import javax.swing.JOptionPane;
import entidades.Consulta;
import entidades.Paciente;
import servico.ServicoConsulta;
import servico.ServicoPaciente;
import servico.ServicoPagamento;
import java.util.List;

public class MenuPagamento {
    private ServicoConsulta servicoConsulta = new ServicoConsulta();
    private ServicoPagamento servicoPagamento = new ServicoPagamento();
    private ServicoPaciente servicoPaciente = new ServicoPaciente();

    public void iniciar() {
        exibirMenu("GERENCIAR PAGAMENTOS",
                "1 - Listar Consultas Pendentes\n" +
                        "2 - Efetuar Pagamento\n");
    }

    public void listar() {
        String cpfPaciente = JOptionPane.showInputDialog("Digite o CPF do paciente:");
        Paciente paciente = new Paciente(cpfPaciente);

        if (cpfPaciente == null || cpfPaciente.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "CPF inválido.");
            return;
        }

        List<Consulta> consultasPendentes = servicoConsulta.listarConsultasNaoPagasPorPaciente(paciente);

        if (consultasPendentes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há consultas pendentes de pagamento para este paciente.");
            return;
        }

        StringBuilder lista = new StringBuilder(
                "Consultas Pendentes para o paciente: " + servicoPaciente.buscar(paciente) + "\n");
        for (int i = 0; i < consultasPendentes.size(); i++) {
            Consulta consulta = consultasPendentes.get(i);
            double preco = servicoPagamento.obterValorConsulta(consulta.getTipoConsulta()); // Agora está correto
            lista.append(i + 1).append(" - ").append(consulta)
                    .append(" | Valor: R$ ").append(String.format("%.2f", preco)).append("\n");
        }

        JOptionPane.showMessageDialog(null, lista.toString());
    }

    public void exibirMenu(String titulo, String opcoes) {
        int opcao;
        do {
            opcao = Integer.parseInt(JOptionPane.showInputDialog("=== " + titulo + " ===\n" +
                    opcoes +
                    "0 - Voltar\n" +
                    "Escolha uma opção:"));

            switch (opcao) {
                case 1:
                    listar();
                    break;
                case 2:
                    efetuarPagamento();
                    break;
                case 0:
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opção Inválida.");
            }
        } while (opcao != 0);
    }

    public void efetuarPagamento() {
        String cpfPaciente = JOptionPane.showInputDialog("Digite o CPF do paciente:");
        Paciente paciente = new Paciente(cpfPaciente);

        if (cpfPaciente == null || cpfPaciente.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "CPF inválido.");
            return;
        }

        List<Consulta> consultasPendentes = servicoConsulta.listarConsultasNaoPagasPorPaciente(paciente);

        if (consultasPendentes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há consultas pendentes de pagamento para este paciente.");
            return;
        }

        StringBuilder opcoes = new StringBuilder("Escolha a consulta para pagamento:\n");
        for (int i = 0; i < consultasPendentes.size(); i++) {
            Consulta consulta = consultasPendentes.get(i);
            double valorConsulta = servicoPagamento.obterValorConsulta(consulta.getTipoConsulta());
            double valorExames = servicoPagamento.obterValorExames(consulta.getExamesPrescritos());
            double valorTotal = valorConsulta + valorExames;

            opcoes.append(i + 1).append(" - ").append(consulta)
                    .append(" | Valor Consulta: R$ ").append(String.format("%.2f", valorConsulta))
                    .append(" | Valor Exames: R$ ").append(String.format("%.2f", valorExames))
                    .append(" | Valor Total: R$ ").append(String.format("%.2f", valorTotal)).append("\n");
        }

        int escolha = Integer.parseInt(JOptionPane.showInputDialog(opcoes.toString())) - 1;

        if (escolha < 0 || escolha >= consultasPendentes.size()) {
            JOptionPane.showMessageDialog(null, "Opção inválida.");
            return;
        }

        Consulta consultaSelecionada = consultasPendentes.get(escolha);
        double valorConsulta = servicoPagamento.obterValorConsulta(consultaSelecionada.getTipoConsulta());
        double valorExames = servicoPagamento.obterValorExames(consultaSelecionada.getExamesPrescritos());
        double valorTotal = valorConsulta + valorExames;

        StringBuilder mensagemPagamento = new StringBuilder("Detalhes do pagamento:\n")
                .append("Valor da Consulta: R$ ").append(String.format("%.2f", valorConsulta)).append("\n")
                .append("Valor dos Exames: R$ ").append(String.format("%.2f", valorExames)).append("\n")
                .append("Valor Total: R$ ").append(String.format("%.2f", valorTotal)).append("\n");

        int confirmar = JOptionPane.showConfirmDialog(null,
                mensagemPagamento.toString() + "Confirmar pagamento?",
                "Confirmação", JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            servicoPagamento.realizarPagamento(consultaSelecionada);
            JOptionPane.showMessageDialog(null, "Pagamento realizado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Pagamento cancelado.");
        }
    }
}
