package entidades;

import java.util.ArrayList;
import java.util.List;



public class Consulta {
    private String data;
    private String horario;
    private int duracao;
    private String status;
    private Paciente paciente;
    private Medico medico;
    private List<Exame> examesPrescritos;
    private List<Medicamento> medicamentosPrescritos;
    private String tipoConsulta;
    private boolean pago;

    public List<Medicamento> getMedicamentosPrescritos() {
        return medicamentosPrescritos;
    }

    public void setMedicamentosPrescritos(List<Medicamento> medicamentosPrescritos) {
        this.medicamentosPrescritos = medicamentosPrescritos;
    }

    public String getTipoConsulta() {
        return tipoConsulta;
    }

    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public void setTipoConsulta(String tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

    public Consulta(Paciente paciente, Medico medico, String data, String horario, String tipoConsulta, String status) {
        this.paciente = paciente;
        this.data = data;
        this.medico = medico;
        this.horario = horario;
        this.tipoConsulta = tipoConsulta;
        this.status = "AGENDADA";
        examesPrescritos = new ArrayList<>();
        medicamentosPrescritos = new ArrayList<>();
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status.equals("AGENDADA") || status.equals("CANCELADA") || status.equals("REALIZADA")) {
            this.status = status;
        } else {
            throw new IllegalArgumentException("Status inválido!");
        }
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public List<Exame> getExamesPrescritos() {
        return examesPrescritos;
    }

    public void setExamesPreescritos(List<Exame> examesPreescritos) {
        this.examesPrescritos = examesPreescritos;
    }

    public List<Medicamento> getMedicamentos() {
        return medicamentosPrescritos;
    }

    public void setMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentosPrescritos = medicamentos;
    }

    public void adicionarExame(Exame exame) {
        examesPrescritos.add(exame);
    }

    public void adicionarMedicamento(Medicamento medicamento) {
        medicamentosPrescritos.add(medicamento);
    }

    @Override
    public String toString() {
        String dados = "Consulta : " + getData() + " às " + getHorario();
        dados += "\nPaciente: " + paciente.getNome();
        dados += "\nMédico: " + medico.getNome();
        dados += "\nTipo de consulta: " + getTipoConsulta();
        dados += "\nExames: " + (getExamesPrescritos() != null ? getExamesPrescritos() : "Sem exames.");
        dados += "\nStatus: " + getStatus();
        dados += "\n Pagamento: " + (isPago() ? "REALIZADO." : "PENDENTE.");
        return dados;
    }
}
