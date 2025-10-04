package servico;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import entidades.Consulta;
import entidades.Exame;
import entidades.Paciente;
import entidades.Pagamento;

public class ServicoPagamento {
    static ServicoConsulta servicoConsulta;
    private static List<Pagamento> pagamentos;

    public ServicoPagamento(){
        pagamentos = new ArrayList<>();
    }

    public double obterValorConsulta(String especialidade) {
        switch (especialidade) {
            case "Cardiologista":
                return 200.00;
            case "Clinico(a) Geral":
                return 150.00;
            case "Dermatologista":
                return 180.00;
            case "Fisioterapia":
                return 120.00;
            case "Fonoaudiologia":
                return 130.00;
            case "Ginecologista":
                return 200.00;
            case "Nutricionista":
                return 100.00;
            case "Oftalmologista":
                return 170.00;
            case "Ortopedista":
                return 190.00;
            case "Pediatria":
                return 160.00;
            default:
                return 150.00;
        }
    }

    public void realizarPagamento(Consulta consulta) {
        if (consulta != null) {
            consulta.setPago(true);
        }
    }
    public void verificarPagamentosPendentes(Paciente paciente) {
        for (Pagamento p : pagamentos) {
            if (p.getPaciente().getCpf().equals(paciente.getCpf()) && !p.isPago()) {
                JOptionPane.showMessageDialog(null, "Não foi possível agendar uma nova consulta: pagamento pendente.");
            }
        }
    }

    public double obterValorExames(List<Exame> exames) {
        double valorTotal = 0.0;
        for (Exame exame : exames) {
            valorTotal += exame.getCustoExame();
        }
        return valorTotal;
    }
    

}
