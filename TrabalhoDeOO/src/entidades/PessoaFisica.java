package entidades;

public abstract class PessoaFisica {
    private String nome;
    private String dataNasciemento;
    private String cpf;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNasciemento() {
        return dataNasciemento;
    }

    public void setDataNasciemento(String dataNasciemento) {
        this.dataNasciemento = dataNasciemento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "Nome: " + getNome() + "\nData de Nascimento: " + getDataNasciemento() + "\nCPF: " + getCpf();
    }
    
}
