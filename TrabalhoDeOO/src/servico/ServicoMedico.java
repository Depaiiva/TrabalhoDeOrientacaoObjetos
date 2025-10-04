package servico;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import entidades.Medico;
import entidades.Paciente;

public class ServicoMedico extends ServicoBase<Medico> {
    private static List<Medico> medicos;

    public ServicoMedico() {
        medicos = new ArrayList<>();
    }

    @Override
    public void criar(Medico medico) {
        medicos.add(medico);
    }

    @Override
    public List<Medico> listar() {
        return medicos;
    }

    @Override
    public Medico buscar(Medico medico) {
        for (Medico med : medicos) {
            if (med.getCrm().equals(medico.getCrm())) {
                return med;
            }
        }
        return null;
    }

    @Override
    public void remover(Medico m) {
        int indexRemover = -1;
        for (int i = 0; i < medicos.size(); i++) {
            if (medicos.get(i).getCrm().equals(m.getCrm())) {
                indexRemover = i;
                break;
            }
        }
        if (indexRemover != -1) {
            medicos.remove(indexRemover);
            JOptionPane.showMessageDialog(null, "Medico removido com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "ERRO: MÉDICO NÃO ENCOTRADO.");
        }

    }

    public boolean blockCrmRep(String crm) {
        for (Medico med : medicos) {
            if (med.getCrm().equals(crm)) {
                return true;
            }
        }
        return false;
    }

    public List<Medico> getMedicos() {
        return medicos;
    }

    public void setMedicos(List<Medico> medicos) {
        ServicoMedico.medicos = medicos;
    }

    public List<Medico> buscarMedicosPorEspecializacao(int especialidade) {
        String especialidadeStr = converterEspecialidadeParaCodigo(especialidade);
        List<Medico> medicosFiltrados = new ArrayList<>();
        for (Medico medico : medicos) {
            if (medico.getEspecialidade().equals(especialidadeStr)) {
                medicosFiltrados.add(medico);
            }
        }
        return medicosFiltrados;
    }

    public String converterEspecialidadeParaCodigo(int opc) {
        switch (opc) {
            case 1:
                return "Cardiologista";
            case 2:
                return "Clinico(a) Geral";
            case 3:
                return "Dermatologista";
            case 4:
                return "Fisioterapeuta";
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
                return "Pediatra";
            default:
                return null;
        }
    }

    public boolean blockCpfRep(String cpf) {
        // Verifica se o CPF já está cadastrado como paciente
        for (Paciente paciente : ServicoPaciente.getPacientes()) {
            if (paciente.getCpf().equals(cpf)) {
                return true; // CPF já existe como paciente
            }
        }

        // Verifica se o CPF já está cadastrado como médico
        for (Medico medico : medicos) { // Supondo que exista um método para obter médicos
            if (medico.getCpf().equals(cpf)) {
                return true; // CPF já existe como médico
            }
        }

        return false; // CPF não está em uso
    }

}
