package entidades;

import java.util.ArrayList;
import java.util.List;

public class Medico extends PessoaFisica {
    private String especialidade;
    private String crm;
    private boolean crmBloqueado;
    private static List<Consulta> historicoConsultas;
    
        public Medico(String nome, String cpf, String dataNascimento, String crm, String especialidade) {
            this.setNome(nome);
            this.setCpf(cpf);
            this.setDataNasciemento(dataNascimento);
            this.crm = crm;
            this.especialidade = especialidade;
            Medico.historicoConsultas = new ArrayList<>();
        }
    
        public void setHistoricosConsultas(List<Consulta> historicosConsultas) {
            Medico.historicoConsultas = historicosConsultas;
        }
    
        public String getEspecialidade() {
            return especialidade;
        }
    
        public void setEspecialidade(String especialidade) {
            this.especialidade = especialidade;
        }
    
        public boolean isCrmBloqueado() {
            return crmBloqueado;
        }
    
        public void setCrmBloqueado(boolean crmBloqueado) {
            this.crmBloqueado = crmBloqueado;
        }
    
        public String getCrm() {
            return crm;
        }
    
        public void setCrm(String crm) {
            this.crm = crm;
        }
    
        public List<Consulta> getConsultas() {
            return historicoConsultas;
        }
    
        public void setConsultas(List<Consulta> consultas) {
            Medico.historicoConsultas = consultas;
        }
    
        public static void adicionarConsultaAoHistorico(Consulta consulta) {
            historicoConsultas.add(consulta);
    }

    @Override
    public String toString() {
        return "Nome: " + getNome() + "\n" +
        "Data de Nascimento: " + getDataNasciemento() + "\n" +
        "CPF: " + getCpf() + "\n" +
        "CRM: " + getCrm() + "\n" +
        "Especialidade: " + getEspecialidade() + "\n" +
        "\n--------------------------------------";
    }

    public List<Consulta> getHistoricoConsultas() {
        return historicoConsultas;
    }

    public static void setHistoricoConsultas(List<Consulta> historicoConsultas) {
        Medico.historicoConsultas = historicoConsultas;
    }

}
