package entidades;

public class Pagamento {
    private Paciente paciente;
    private Exame exame;
    private Consulta consulta;
    private String dataPagamento;
    private boolean pago;
    private double valor;
    private String metodoPagamento;

    public Pagamento( Consulta consulta, double valor, String metodoPagamento) {
        this.consulta = consulta;
        this.valor = valor;
        this.metodoPagamento = metodoPagamento;
        this.pago = false;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Exame getExame() {
        return exame;
    }

    public void setExame(Exame exame) {
        this.exame = exame;
    }

    public String getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(String dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Exame getExames() {
        return exame;
    }

    public void setExames(Exame exame) {
        this.exame = exame;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

}
