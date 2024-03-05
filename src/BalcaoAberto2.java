/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author Bruno Cezar
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class BalcaoAberto2 extends JFrame {

        public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BalcaoAberto2().setVisible(true);
        });
    }
    
    private final String DATABASE_URL = "jdbc:mysql://localhost:3306/uc11_Bruno_Castro";
    private final String USER = "root";
    private final String PASSWORD = "=senaCEad2022";

    private final DefaultTableModel tableModel;
    private final JTable produtosTable;

    public BalcaoAberto2() {
        setTitle("Balcão Aberto");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton cadastrarButton = new JButton("Cadastrar Produtos");
        JButton consultarButton = new JButton("Consultar Produtos");
        JButton venderButton = new JButton("Vender Produto");

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));

        JLabel logotipo = new JLabel("Balcão Aberto");
        logotipo.setFont(new Font("Arial", Font.BOLD, 24));

        panelPrincipal.add(logotipo);

        add(panelPrincipal, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        produtosTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(produtosTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotoes = new JPanel();
        panelBotoes.setLayout(new GridLayout(3, 1));
        panelBotoes.add(cadastrarButton);
        panelBotoes.add(consultarButton);
        panelBotoes.add(venderButton);

        add(panelBotoes, BorderLayout.SOUTH);

        cadastrarButton.addActionListener((ActionEvent e) -> {
            try {
                cadastrarProduto();
                atualizarListaProdutos();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto. Verifique o console para mais detalhes.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        consultarButton.addActionListener((ActionEvent e) -> {
            atualizarListaProdutos();
        });

        venderButton.addActionListener((ActionEvent e) -> {
            venderProduto();
            atualizarListaProdutos();
        });
    }

    private void cadastrarProduto() throws SQLException {
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
                JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void atualizarListaProdutos() {
        try {
            List<String[]> listaProdutos = obterListaProdutos();
            String[] colunas = {"Nome", "Valor", "Status"};

            tableModel.setDataVector(listaProdutos.toArray(Object[][]::new), colunas);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao obter lista de produtos. Verifique o console para mais detalhes.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void venderProduto() {
        try {
            String nomeProduto = JOptionPane.showInputDialog("Nome do Produto a ser vendido:");
            ProdutosDAO produtosDAO = new ProdutosDAO();
            produtosDAO.venderProduto(nomeProduto);
            JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao vender produto. Verifique o console para mais detalhes.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private List<String[]> obterListaProdutos() throws SQLException {
        List<String[]> listaProdutos = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD)) {
            String sql = "SELECT nome, valor, status FROM produtos";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String nome = resultSet.getString("nome");
                    String valor = String.valueOf(resultSet.getInt("valor"));
                    String status = resultSet.getString("status");

                    listaProdutos.add(new String[]{nome, valor, status});
                }
            }
        }

        return listaProdutos;
    }
}











