/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author Bruno Cezar
 */
import javax.swing.JFrame;

public class BalcaoAberto2 {
    public static void main(String[] args) {
        // Instância da tela CadastroVIEW
        CadastroVIEW cadastroView = new CadastroVIEW();
        
        // método setVisible(true) para exibir a tela de CadastroVIEW
        cadastroView.setVisible(true);
        
        // espaço para mais lógica. Analisar a necessidade.
    }

    private static class CadastroVIEW extends JFrame {

        public CadastroVIEW() {
            // Configurações da janela (tamanho, operação padrão ao fechar, etc.)
            setTitle("CadastroVIEW");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }
}



