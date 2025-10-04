package servico;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import entidades.Consulta;
import entidades.Exame;
import entidades.Medicamento;
import entidades.Medico;
import entidades.Paciente;

public class ServicoConsulta extends ServicoBase<Consulta> {
    private static List<Consulta> consultas;
    private static ServicoExame servicoExame;
    private static final int LIMITE_CONSULTAS_POR_DIA = 3;

    public ServicoConsulta() {
        consultas = new ArrayList<>();
    }

    public static List<Consulta> getConsultas() {
        return consultas;
    }

    public static void setConsultas(List<Consulta> consultas) {
        ServicoConsulta.consultas = consultas;
    }

    @Override
    public void criar(Consulta consulta) {
        consultas.add(consulta);
    }

    @Override
    public List<Consulta> listar() {

        return consultas;
    }

    @Override
    public Consulta buscar(Consulta consulta) {
        for (Consulta tempConsulta : consultas) {
            if (tempConsulta.getData().equals(consulta.getData())) {
                return tempConsulta;
            }
        }
        return null;
    }

    @Override
    public void remover(Consulta consulta) {
        for (Consulta tempConsulta : consultas) {
            if (tempConsulta.getData().equals(consulta.getData())) {
                consultas.remove(tempConsulta);
                break;
            }
        }
        return;
    }

    public String converterEspecialidadeParaCodigo(int opc) {
        switch (opc) {
            case 1:
                return "Cardiologia";
            case 2:
                return "Clinica Geral";
            case 3:
                return "Dermatologia";
            case 4:
                return "Fisioterapia";
            case 5:
                return "Fonoaudiologia";
            case 6:
                return "Ginecologista";
            case 7:
                return "Nutricionista";
            case 8:
                return "Oftalmologista";
            case 9:
                return "Ortopedista";
            case 10:
                return "Pediatria";
            default:
                return null;
        }
    }

    public void cancelarConsulta(String data) {
        for (Consulta consulta : consultas) {
            if (consulta.getData().equals(data)) {
                consulta.setStatus("CANCELADA");
                JOptionPane.showMessageDialog(null, "Consulta cancelada!");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Consulta não encontrada!");
    }

    public void finalizarConsulta(String data) {
        for (Consulta consulta : consultas) {
            if (consulta.getData().equals(data)) {
                consulta.setStatus("REALIZADA");
                JOptionPane.showMessageDialog(null, "Consulta marcada como realizada!");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Consulta não encontrada!");
    }

    public void prescreverExame(Consulta consulta) {
        buscarPorData(consulta);
        if (consulta == null) {
            JOptionPane.showMessageDialog(null, "Consulta não encontrada.", "ERRO", 0);
            return;
        }

        List<Exame> examesDisponiveis = servicoExame.listar();
        if (examesDisponiveis.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Nenhum exame disponível no sistema.", "WARNING", 2);
            return;
        }

        StringBuilder opcoes = new StringBuilder("Escolha um exame:\n");
        for (int i = 0; i < examesDisponiveis.size(); i++) {
            opcoes.append(i + 1).append(" - ").append(examesDisponiveis.get(i).getTipoDeExame()).append("\n");
        }

        int escolha = Integer.parseInt(JOptionPane.showInputDialog(opcoes.toString())) - 1;

        if (escolha >= 0 && escolha < examesDisponiveis.size()) {
            Exame exameSelecionado = examesDisponiveis.get(escolha);
            consulta.adicionarExame(exameSelecionado);
            JOptionPane.showMessageDialog(null,
                    "Exame " + exameSelecionado.getTipoDeExame() + " prescrito com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Opção inválida.");
        }
    }

    private Consulta buscarPorData(Consulta consulta) {
        for (Consulta c : consultas) {
            if (consulta.getData().equals(c.getData())) {
                JOptionPane.showMessageDialog(null, "Paciente achado.");
                return consulta;
            } else {
                JOptionPane.showMessageDialog(null, "Paciente não encontrado.");
            }
        }
        return null;
    }

    public boolean consultaPacienteNoMesmoDia(Paciente paciente, String data) {
        for (Consulta consulta : consultas) {
            if (consulta.getPaciente().equals(paciente) && consulta.getData().equals(data)) {
                return true;
            }
        }
        return false;
    }

    public boolean consultaMedicaNoMesmoDia(Medico medico, String data, String horario) {
        for (Consulta consulta : consultas) {
            if (consulta.getMedico().equals(medico) && consulta.getData().equals(data)
                    && consulta.getHorario().equals(horario)) {
                return true;
            }
        }
        return false;
    }

    public List<Consulta> listarConsultasNaoPagas() {
        List<Consulta> naoPagas = new ArrayList<>();
        for (Consulta consulta : consultas) {
            if (!consulta.isPago()) {
                naoPagas.add(consulta);
            }
        }
        return naoPagas;
    }

    public List<Consulta> listarConsultasNaoPagasPorPaciente(Paciente paciente) {
        List<Consulta> consultasPendentes = new ArrayList<>();
        for (Consulta consulta : consultas) {
            if (!consulta.isPago() && consulta.getPaciente().getCpf().equals(paciente.getCpf())) {
                consultasPendentes.add(consulta);
            }
        }
        return consultasPendentes;
    }

    public boolean podeAgendarConsulta(Paciente paciente, Medico medico, String data) {
        int consultasPaciente = 0;
        int consultasMedico = 0;

        for (Consulta c : consultas) {
            if (c.getData().equals(data)) {
                if (c.getPaciente().getCpf().equals(paciente.getCpf())) {
                    consultasPaciente++;
                }
                if (c.getMedico().getCrm().equals(medico.getCrm())) {
                    consultasMedico++;
                }
            }
        }

        return consultasPaciente < LIMITE_CONSULTAS_POR_DIA && consultasMedico < LIMITE_CONSULTAS_POR_DIA;
    }

    public void agendarConsulta(Paciente paciente, Medico medico, String data, String horario, String tipoConsulta) {
        if (!podeAgendarConsulta(paciente, medico, data)) {
            JOptionPane.showMessageDialog(null, "O paciente ou médico já atingiu o limite de consultas para este dia.");
            return;
        }

        Consulta novaConsulta = new Consulta(paciente, medico, data, horario, tipoConsulta, null);
        criar(novaConsulta);
        Medico.adicionarConsultaAoHistorico(novaConsulta);
        paciente.adicionarConsulta(novaConsulta);
        JOptionPane.showMessageDialog(null, "Consulta agendada com sucesso!");
    }
    public void adicionarMedicamentoAConsulta(Consulta consulta, Medicamento medicamento) {
        consulta.adicionarMedicamento(medicamento);
    }
    
    public List<Consulta> listarConsultasPorMedico(Medico medico) {
        List<Consulta> consultasDoMedico = new ArrayList<>();
        for (Consulta consulta : consultas) {
            if (consulta.getMedico().equals(medico)) {
                consultasDoMedico.add(consulta);
            }
        }
        return consultasDoMedico;
    }

}
