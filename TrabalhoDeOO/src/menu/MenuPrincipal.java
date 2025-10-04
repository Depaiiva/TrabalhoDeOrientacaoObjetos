package menu;

import javax.swing.JOptionPane;

public class MenuPrincipal {
    static MenuPaciente menuPaciente = new MenuPaciente();
    static MenuMedico menuMedico = new MenuMedico();
    static MenuConsulta menuConsulta = new MenuConsulta();
    static MenuPagamento menuPagamento = new MenuPagamento();
    static MenuExame menuExame = new MenuExame();
    static MenuMedicamento menuMedicamento = new MenuMedicamento();

    public static void menuOpcoes() {
        int opcoes = -1;
        do {
            try {
                opcoes = Integer.parseInt(JOptionPane.showInputDialog(null, "=== SISTEMA DE GERENCIAMENTO DE CLÍNICA MÉDICA ===\n"
                        +  "1 - Gerenciar Pacientes \n"
                        + "2 - Gerenciar Medicos \n"
                        + "3 - Gerenciar Consultas \n"
                        + "4 - Gerenciar Exames \n"
                        + "5 - Gerenciar Medicamentos\n"
                        + "6 - Gerenciar Pagamentos\n"
                        + "0 - Sair\n\n"));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Opção inválida! Escolha um número correto.");
                continue;
            }
            switch (opcoes) {
                case 1:
                    menuPaciente.iniciar();
                    break;
                case 2:
                    menuMedico.iniciar();
                    break;
                case 3:
                    menuConsulta.iniciar();
                    break;
                case 4:
                    menuExame.iniciar();
                    break;
                case 5: menuMedicamento.iniciar();
                    break;
                case 6:
                    menuPagamento.iniciar();
                    break;
                case 0:
                    JOptionPane.showMessageDialog(null, "ENCERRANDO...");
                    break;
                default:
                    break;
            }
        } while (opcoes != 0);

    }
}
