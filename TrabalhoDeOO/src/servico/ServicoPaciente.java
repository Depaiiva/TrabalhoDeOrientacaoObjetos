package servico;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import entidades.Paciente;

public class ServicoPaciente extends ServicoBase<Paciente> {
    private static List<Paciente> pacientes;

    public ServicoPaciente() {
        pacientes = new ArrayList<>();
    }

    @Override
    public void criar(Paciente paciente) {
        if (blockCpfRep(paciente)) {
            pacientes.add(paciente);
        }
    }

    @Override
    public List<Paciente> listar() {
        return pacientes;
    }

    @Override
    public Paciente buscar(Paciente p) {
        for (Paciente paciente : pacientes) {
            if (p.getCpf().equals(paciente.getCpf())) {
                return paciente;
            }
        }
        return null;
    }

    @Override
    public void remover(Paciente p) {
        int indexRemover = -1;

        for (int i = 0; i < pacientes.size(); i++) {
            if (pacientes.get(i).getCpf().equals(p.getCpf())) {
                indexRemover = i;
                break;
            }
        }

        if (indexRemover != -1) {
            pacientes.remove(indexRemover);
            JOptionPane.showMessageDialog(null, "Paciente removido com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "ERRO: Paciente não encontrado.");
        }
    }

    public static List<Paciente> getPacientes() {
        return pacientes;
    }

    public static void setPacientes(List<Paciente> pacientes) {
        ServicoPaciente.pacientes = pacientes;
    }

    public boolean blockCpfRep(Paciente p) {
        for (Paciente paciente : pacientes) {
            if (paciente.getCpf().equals(p.getCpf())) {
                JOptionPane.showMessageDialog(null, "ERRO: CPF JÁ EXISTE NO SISTEMA.");
                return false;
            }
        }
        return true;
    }
}
