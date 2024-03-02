/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author Bruno Cezar
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JButton;

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
            // Configurações da janela
            setTitle("Balcão Aberto");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Adiciona botões ao painel principal
            JButton cadastrarButton = new JButton("Cadastrar Produtos");
            JButton consultarButton = new JButton("Consultar Produtos");

            JPanel panelPrincipal = new JPanel();
            panelPrincipal.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
            
            JLabel logotipo = new JLabel("Balcão Aberto");
            logotipo.setFont(new Font("Arial", Font.BOLD, 24));

            JLabel titulo = new JLabel("(Escolha uma das opções abaixo)");
            titulo.setFont(new Font("Arial", Font.BOLD, 18));

            panelPrincipal.add(logotipo);
            panelPrincipal.add(titulo);

            // Adiciona o painel ao frame
            add(panelPrincipal, BorderLayout.NORTH);
            
            // Adiciona botões ao painel de botões
            JPanel panelBotoes = new JPanel();
            panelBotoes.setLayout(new GridLayout(2, 1));
            panelBotoes.add(cadastrarButton);
            panelBotoes.add(consultarButton);
            
            // Adiciona o painel de botões ao frame
            add(panelBotoes, BorderLayout.CENTER);

            cadastrarButton.addActionListener((ActionEvent e) -> {
                // Lógica para ação de cadastro.
                System.out.println("Ação: Cadastrar Produtos");
            });

            consultarButton.addActionListener((ActionEvent e) -> {
                // Lógica para ação de consulta (listagem)
                System.out.println("Ação: Consultar Produtos");
            });
        }

     }
}

