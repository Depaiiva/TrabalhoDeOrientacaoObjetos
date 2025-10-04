package entidades;

public class Exame {
    private String tipoDeExame;
    private String dataPrescricao;
    private String dataRealizacao;
    private String resultadoDoExame;
    private double custoExame;

    public Exame(String tipoDeExame, double custoExame){
        this.tipoDeExame = tipoDeExame;
        this.custoExame = custoExame;
    }

    public String getDataPrescricao() {
        return dataPrescricao;
    }

    public void setDataPrescricao(String dataPrescricao) {
        this.dataPrescricao = dataPrescricao;
    }

    public Exame(String tipoDeExame) {
        this.tipoDeExame = tipoDeExame;
    }

    public String getTipoDeExame() {
        return tipoDeExame;
    }

    public void setTipoDeExame(String tipoDeExame) {
        this.tipoDeExame = tipoDeExame;
    }

    public String getDataPreescricao() {
        return dataPrescricao;
    }

    public void setDataPreescricao(String dataPreescricao) {
        this.dataPrescricao = dataPreescricao;
    }

    public String getDataRealizacao() {
        return dataRealizacao;
    }

    public void setDataRealizacao(String dataRealizacao) {
        this.dataRealizacao = dataRealizacao;
    }

    public String getResultadoDoExame() {
        return resultadoDoExame;
    }

    public void setResultadoDoExame(String resultadoDoExame) {
        this.resultadoDoExame = resultadoDoExame;
    }

    public double getCustoExame() {
        return custoExame;
    }

    public void setCustoExame(double custoExame) {
        this.custoExame = custoExame;
    }

    @Override
public String toString() {
    return "Tipo de Exame: " + tipoDeExame + 
           ", Data de Prescrição: " + (dataPrescricao != null ? dataPrescricao : "Não definida") +
           ", Data de Realização: " + (dataRealizacao != null ? dataRealizacao : "Não realizada") +
           ", Resultado: " + (resultadoDoExame != null ? resultadoDoExame : "Pendente") +
           ", Custo: R$" + custoExame;
}


}
