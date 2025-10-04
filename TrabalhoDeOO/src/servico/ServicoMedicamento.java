package servico;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import entidades.Consulta;
import entidades.Medicamento;
import entidades.Paciente;

public class ServicoMedicamento extends ServicoBase<Medicamento> {
    private static List<Medicamento> medicamentos;
    private ServicoPaciente servicoPaciente = new ServicoPaciente();

    public ServicoMedicamento() {
        medicamentos = new ArrayList<>();
    }

    public void prescreverMedicamento(Paciente paciente, Medicamento medicamento) {
        for (Consulta consulta : ServicoConsulta.getConsultas()) {
            Paciente p = consulta.getPaciente();
            if (p.getCpf().equals(paciente.getCpf())) {
                consulta.adicionarMedicamento(medicamento);
                JOptionPane.showMessageDialog(null, "Medicamento " + medicamento.getNome() + " prescrito para o paciente " + p.getNome() + ".");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Consulta não encontrada para o paciente.");
    }

    public void listarMedicamentosPorPaciente(Paciente paciente) {
        Paciente p = servicoPaciente.buscar(paciente);
        if(p == null){
            JOptionPane.showMessageDialog(null, "PACIENTE NÃO ENCONTRADO", "ERRO", 0);
        }
        StringBuilder medicamentosListados = new StringBuilder("Medicamentos:\n");
        for(int i = 0; i < p.getListaMedicamentos().size(); i++){
            medicamentosListados.append(i + 1).append(" - ").append(p.getListaMedicamentos().get(i).getNome()).append("\n");
        }
        JOptionPane.showMessageDialog(null, medicamentosListados, null, 3);
    }

    @Override
    public void criar(Medicamento medicamento) {
        medicamentos.add(medicamento);
    }

    @Override
    public List<Medicamento> listar() {
        return medicamentos;
    }

    @Override
    public Medicamento buscar(Medicamento medicamento) {
        for(Medicamento medicamentosBuscados : medicamentos){
            if(medicamento.getNome().equals(medicamentosBuscados.getNome())){
                return medicamentosBuscados;
            }
        }
        return null;
    }

    @Override
    public void remover(Medicamento medicamento) {
        for(Medicamento medicamentosBuscados : medicamentos){
            if(medicamento.getNome().equals(medicamentosBuscados.getNome())){
                medicamentos.remove(medicamentosBuscados);
            }
        }
        JOptionPane.showMessageDialog(null, "Medicamento Não Encontrado", null, 0);
    }
}
