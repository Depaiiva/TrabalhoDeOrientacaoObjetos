package entidades;

import java.util.ArrayList;
import java.util.List;

public class Paciente extends PessoaFisica {
    private boolean statusPagamento;
    private  List<Exame> historicoExames;
    private  List<Consulta> historicoConsultas;
    private  List<Medicamento> listaMedicamentos;

    public Paciente(String nome, String cpf, String dataNasciemento) {
        this.setNome(nome);
        this.setCpf(cpf);
        this.setDataNasciemento(dataNasciemento);
        historicoConsultas = new ArrayList<>();
        historicoExames = new ArrayList<>();
        listaMedicamentos = new ArrayList<>();
    }

    public  List<Medicamento> getListaMedicamentos() {
        return listaMedicamentos;
    }

    public void setListaMedicamentos(List<Medicamento> listaMedicamentos) {
        this.listaMedicamentos = listaMedicamentos;
    }

    public List<Consulta> getHistoricoConsultas() {
        if (historicoConsultas == null) {
            historicoConsultas = new ArrayList<>();
        }
        return historicoConsultas;
    }

    public Paciente(String cpf) {
        this.setCpf(cpf);
    }

    public boolean isStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(boolean statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public void setHistoricoConsultas(List<Consulta> historicoConsultas) {
        this.historicoConsultas = historicoConsultas;
    }

    public List<Exame> getHistoricoExames() {
        return historicoExames;
    }

    public void setHistoricoExames(List<Exame> historicoExames) {
        this.historicoExames = historicoExames;
    }

    public void adicionarConsulta(Consulta consulta) {
        if (historicoConsultas == null) {
            historicoConsultas = new ArrayList<>();
        }
        historicoConsultas.add(consulta);
    }

    @Override
    public String toString() {
        return "Nome: " + getNome() + "\n" +
                "Data de Nascimento: " + getDataNasciemento() + "\n" +
                "CPF: " + getCpf() +
                "\n--------------------------------------";
    }

}
