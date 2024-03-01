/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Bruno Cezar
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaginaLogin extends JFrame {

    private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private JButton botaoLogin;
    private String tipoUsuario;

    public PaginaLogin() {
        SwingUtilities.invokeLater(() -> {
            criarInterfaceUsuario();
        });
    }

    private void criarInterfaceUsuario() {
        setTitle("BALCÃO ABERTO");

        // Adicionar logotipo no topo
        JPanel painelSuperior = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel logotipo = new JLabel("- BALCÃO ABERTO -");
        logotipo.setFont(new Font("Arial", Font.BOLD, 24));
        painelSuperior.add(logotipo, gbc);

        // Criar o formulário de login
        JPanel painelFormularioLogin = new JPanel();
        painelFormularioLogin.setLayout(new GridLayout(5, 1));

        // Adicionar o campo de usuário
        JLabel rotuloUsuario = new JLabel("Login:");
        campoUsuario = new JTextField(5);
        painelFormularioLogin.add(rotuloUsuario);
        painelFormularioLogin.add(campoUsuario);

        // Adicionar o campo de senha
        JLabel rotuloSenha = new JLabel("Senha:");
        campoSenha = new JPasswordField(5);
        painelFormularioLogin.add(rotuloSenha);
        painelFormularioLogin.add(campoSenha);

        // Adicionar o formulário de login ao frame
        setLayout(new BorderLayout());
        add(painelSuperior, BorderLayout.NORTH);
        add(painelFormularioLogin, BorderLayout.CENTER);

        // Criar um painel separado para o botão de login
        JPanel painelBotaoLogin = new JPanel();
        botaoLogin = new JButton("LOGIN");
        botaoLogin.setPreferredSize(new Dimension(80, 40));
        painelBotaoLogin.add(botaoLogin);
        add(painelBotaoLogin, BorderLayout.SOUTH);

        // Definição do tamanho e a localização do frame
        setSize(350, 400); // Ajuste para acomodar o logotipo
        setLocationRelativeTo(null);

        // Definir o botão padrão
        getRootPane().setDefaultButton(botaoLogin);

        // Adicionar um ouvinte de ação ao botão de login
        botaoLogin.addActionListener((ActionEvent e) -> {
            // Validar o usuário e senha
            String usuario = campoUsuario.getText();
            String senha = String.valueOf(campoSenha.getPassword());

            if (usuario.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, digite um usuário e uma senha.");
                return;
            }

            // Verificar se o usuário e a senha estão corretos
            if (verificarAutenticidade(usuario, senha)) {
                // Login bem-sucedido
                exibirMensagemBemVindo(usuario, tipoUsuario);
                configurarPermissoes();
            } else {
                // Login falhou
                JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos.");
            }
        });
    }

    // Método para verificar a autenticidade do usuário e senha
    private boolean verificarAutenticidade(String usuario, String senha) {
        // Lógica de autenticação com banco de dados
        String url = "jdbc:mysql://localhost:3306/uc11_Bruno_Castro";
        String usuarioBD = "root";
        String senhaBD = "=senaCEad2022";

        try (Connection connection = DriverManager.getConnection(url, usuarioBD, senhaBD)) {
            String sql = "SELECT tipoUsuario FROM Usuario WHERE nome = ? AND senha = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, usuario);
                preparedStatement.setString(2, senha);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        tipoUsuario = resultSet.getString("tipoUsuario");
                        return true; // Usuário autenticado
                    }
                }
            }
        } catch (SQLException ex) {
     }

        return false; // Autenticação falhou
    }

    // Método para exibir mensagem de boas-vindas
    private void exibirMensagemBemVindo(String usuario, String tipoUsuario) {
        JOptionPane.showMessageDialog(this,
                "Olá " + usuario + ", sua permissão é de " + tipoUsuario + ". Seja bem-vindo!");
    }

    // Método para configurar as permissões com base no tipo de usuário
    private void configurarPermissoes() {
        // Adapte conforme necessário - Aqui, desabilitamos ou habilitamos os botões
        // de acordo com o tipo de usuário.
        JFrame frame = new JFrame("Balcão Aberto");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JButton cadastroButton = new JButton("Cadastrar Peça");
        JButton consultaButton = new JButton("Consultar");
        JButton excluirButton = new JButton("Excluir Peça");  // Botão adicionado para a exclusão

        // Adiciona a lógica de visibilidade dos botões com base no tipo de usuário
        switch (tipoUsuario) {
            case "Administrador":
                cadastroButton.setVisible(true);
                consultaButton.setVisible(true);
                excluirButton.setVisible(true);
                break;
            case "Leiloeiro":
                cadastroButton.setVisible(true);
                consultaButton.setVisible(true);
                excluirButton.setVisible(false);
                break;
            case "Usuário":
                cadastroButton.setVisible(false);
                consultaButton.setVisible(true);
                excluirButton.setVisible(false);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Tipo de usuário desconhecido.");
                break;
        }

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));

        JLabel logotipo = new JLabel("Balcão Aberto");
        logotipo.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel titulo = new JLabel("(Escolha uma das opções abaixo)");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));

        panelPrincipal.add(logotipo);
        panelPrincipal.add(titulo);

        JPanel panelBotoes = new JPanel();
        panelBotoes.add(cadastroButton);
        panelBotoes.add(consultaButton);
        panelBotoes.add(excluirButton);

        frame.setLayout(new BorderLayout());
        frame.add(panelPrincipal, BorderLayout.NORTH);
        frame.add(panelBotoes, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new PaginaLogin().setVisible(true);
    }
}
