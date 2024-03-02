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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BalcaoAberto2 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CadastroVIEW cadastroView = new CadastroVIEW();
            cadastroView.setVisible(true);
         });
    }

    private static class CadastroVIEW extends JFrame {
        private final String DATABASE_URL = "jdbc:mysql://localhost:3306/uc11_Bruno_Castro";
        private final String USER = "root";
        private final String PASSWORD = "=senaCEad2022";

        public CadastroVIEW() {
            setTitle("Balcão Aberto");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

            add(panelPrincipal, BorderLayout.NORTH);

            JPanel panelBotoes = new JPanel();
            panelBotoes.setLayout(new GridLayout(2, 1));
            panelBotoes.add(cadastrarButton);
            panelBotoes.add(consultarButton);

            add(panelBotoes, BorderLayout.CENTER);

            cadastrarButton.addActionListener((ActionEvent e) -> {
                cadastrarProduto();
            });

            consultarButton.addActionListener((ActionEvent e) -> {
                System.out.println("Ação: Consultar Produtos");
            });
        }

        private void cadastrarProduto() {
            try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD)) {
                String nomeProduto = JOptionPane.showInputDialog("Nome do Produto:");
                int valorProduto = Integer.parseInt(JOptionPane.showInputDialog("Valor do Produto:"));
                String statusProduto = JOptionPane.showInputDialog("Status do Produto:");

                String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
                
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, nomeProduto);
                    preparedStatement.setInt(2, valorProduto);
                    preparedStatement.setString(3, statusProduto);

                    preparedStatement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto. Verifique o console para mais detalhes.");
            }
        }
    }
}


