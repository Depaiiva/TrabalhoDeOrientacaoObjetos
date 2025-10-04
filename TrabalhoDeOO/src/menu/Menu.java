package menu;

import javax.swing.JOptionPane;

public abstract class Menu {
    public abstract void criar();

    public abstract void listar();

    public abstract void buscar();

    public abstract void atualizar();

    public abstract void remover();

    public void exibirMenu(String titulo, String opcoes) {
        int opcao = -1;
        do {
            try {
                opcao = Integer.parseInt(JOptionPane.showInputDialog("=== " + titulo + " ===\n" +
                        opcoes +
                        "0 - Voltar\n" +
                        "Escolha uma opção:"));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Opção inválida! Escolha um número correto.");
            }

            switch (opcao) {
                case 1:
                    criar();
                    break;
                case 2:
                    listar();
                    break;
                case 3:
                    buscar();
                    break;
                case 4:
                    atualizar();
                    break;
                case 5:
                    remover();
                    break;
                case 0:
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opção Inválida.");
            }
        } while (opcao != 0);
    }
}
