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

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/uc11_Bruno_Castro";
    private static final String USER = "root";
    private static final String PASSWORD = "=senaCEad2022";

    private final DefaultTableModel tableModel;
    private final JTable produtosTable;

    private static final String COLUMN_NAME = "Nome";
    private static final String COLUMN_VALUE = "Valor";
    private static final String COLUMN_STATUS = "Status";

    public BalcaoAberto2() {
        setTitle("Balcão Aberto");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton cadastrarButton = new JButton("Cadastrar Produtos");
        JButton consultarButton = new JButton("Consultar Produtos");
        JButton venderButton = new JButton("Vender Produto");
        JButton listarVendidosButton = new JButton("Listar Produtos Vendidos");

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
        panelBotoes.setLayout(new GridLayout(4, 1));
        panelBotoes.add(cadastrarButton);
        panelBotoes.add(consultarButton);
        panelBotoes.add(venderButton);
        panelBotoes.add(listarVendidosButton);

        add(panelBotoes, BorderLayout.SOUTH);

        cadastrarButton.addActionListener((ActionEvent e) -> {
            try {
                cadastrarProduto();
                atualizarListaProdutos();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto. Detalhes: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        consultarButton.addActionListener((ActionEvent e) -> {
            atualizarListaProdutos();
        });

        venderButton.addActionListener((ActionEvent e) -> {
            venderProduto();
            atualizarListaProdutos();
        });

        listarVendidosButton.addActionListener((ActionEvent e) -> {
            try {
                listarProdutosVendidos();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao listar produtos vendidos. Detalhes: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
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
        List<String[]> listaProdutos = obterListaProdutos();
        String[] colunas = {COLUMN_NAME, COLUMN_VALUE, COLUMN_STATUS};
        tableModel.setDataVector(listaProdutos.toArray(Object[][]::new), colunas);
    }

    private void venderProduto() {
        try {
            String nomeProduto = JOptionPane.showInputDialog("Nome do Produto a ser vendido:");
            ProdutosDAO produtosDAO = new ProdutosDAO();
            produtosDAO.venderProduto(nomeProduto);
            JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao vender produto. Detalhes: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarProdutosVendidos() throws SQLException {
        List<String[]> listaProdutosVendidos = obterListaProdutosVendidos();
        String[] colunas = {COLUMN_NAME, COLUMN_VALUE, COLUMN_STATUS};

        tableModel.setDataVector(listaProdutosVendidos.toArray(Object[][]::new), colunas);
    }

    private List<String[]> obterListaProdutos() {
        List<String[]> listaProdutos = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD)) {
            String sql = "SELECT nome, valor, status FROM produtos";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String nome = resultSet.getString(COLUMN_NAME);
                    String valor = String.valueOf(resultSet.getInt(COLUMN_VALUE));
                    String status = resultSet.getString(COLUMN_STATUS);

                    listaProdutos.add(new String[]{nome, valor, status});
                }
            }
        } catch (SQLException e) {
            // Trate a exceção conforme necessário
            e.printStackTrace();
        }

        return listaProdutos;
    }

    private List<String[]> obterListaProdutosVendidos() throws SQLException {
        List<String[]> listaProdutosVendidos = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD)) {
            String sql = "SELECT nome, valor, status FROM produtos WHERE status = 'Vendido'";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String nome = resultSet.getString(COLUMN_NAME);
                    String valor = String.valueOf(resultSet.getInt(COLUMN_VALUE));
                    String status = resultSet.getString(COLUMN_STATUS);

                    listaProdutosVendidos.add(new String[]{nome, valor, status});
                }
            }
        } catch (SQLException e) {
            // Trate a exceção conforme necessário
            e.printStackTrace();
        }

        return listaProdutosVendidos;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BalcaoAberto2().setVisible(true);
        });
    }
}













