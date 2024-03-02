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
        // Aqui, você deve criar uma instância da sua CadastroVIEW
        CadastroVIEW cadastroView = new CadastroVIEW();
        
        // Chame o método setVisible(true) para exibir a tela de CadastroVIEW
        cadastroView.setVisible(true);
        
        // Aqui você pode adicionar mais lógica, se necessário.
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



