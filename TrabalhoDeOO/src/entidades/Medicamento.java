package entidades;

public class Medicamento {
    private String nome;
    private String dosagem;
    private String frequencia;

    public Medicamento(String nome, String dosagem, String frequencia){
        this.nome = nome;
        this.dosagem = dosagem;
        this.frequencia = frequencia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDosagem() {
        return dosagem;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    public String getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(String frequencia) {
        this.frequencia = frequencia;
    }

}
