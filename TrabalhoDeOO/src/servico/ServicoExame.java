package servico;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import entidades.Consulta;
import entidades.Exame;
import entidades.Paciente;

public class ServicoExame extends ServicoBase<Exame> {
    private List<Exame> exames;

    public ServicoExame(){
        exames = new ArrayList<>();
    }

    public void prescreverExame(Paciente paciente, Exame exame) {
        for (Consulta consulta : ServicoConsulta.getConsultas()) {
        Paciente p = consulta.getPaciente();
            if (p.getCpf().equals(paciente.getCpf())) {
                consulta.adicionarExame(exame);
                exame.setDataPreescricao(consulta.getData());
                exame.setDataRealizacao(consulta.getData());
                JOptionPane.showMessageDialog(null, "Exame " + exame.getTipoDeExame() + " prescrito para o paciente." + p.getNome(), null, 1);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Consulta não encontrada para o paciente.");
    }

    public List<Exame> listarExamesPorPaciente(Paciente paciente) {
        List<Exame> examesDoPaciente = new ArrayList<>();
        for (Consulta consulta : ServicoConsulta.getConsultas()) {
            String cpf = consulta.getPaciente().getCpf();
            if (cpf.equals(paciente.getCpf())) {
                examesDoPaciente.addAll(consulta.getExamesPrescritos());
            }
        }
        if (examesDoPaciente.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum exame encontrado para o paciente.");
        }
        return examesDoPaciente;
    }

    @Override
    public void criar(Exame exame) {
        exames.add(exame);
        JOptionPane.showMessageDialog(null, "Exame Criado com Sucesso.", null, 1);
    }

    @Override
    public List<Exame> listar() {
        return exames;
    }

    @Override
    public Exame buscar(Exame exame) {
        for(Exame e : exames){
            if(e.getTipoDeExame().equals(exame.getTipoDeExame())){
                return exame;
            }
        }
        return null;
    }

    @Override
    public void remover(Exame exame) {
        exames.remove(exame);
        JOptionPane.showMessageDialog(null, "Exame Removido com Sucesso.", null, 1);
    }
}
