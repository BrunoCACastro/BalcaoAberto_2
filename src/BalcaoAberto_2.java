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
import java.awt.event.ActionListener;

public class BalcaoAberto_2 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> exibirPaginaLogin());
    }

    private static void exibirPaginaLogin() {
        PaginaLogin paginaLogin = new PaginaLogin();
        paginaLogin.setVisible(true);

        paginaLogin.addLoginListener((ActionEvent e) -> {
            if (paginaLogin.isAutenticado()) {
                SwingUtilities.invokeLater(() -> {
                    paginaLogin.dispose();  
                    exibirMenuPrincipal(paginaLogin.getTipoUsuario());
                });
            }
        });
    }

    private static void exibirMenuPrincipal(String tipoUsuario) {
        JFrame frame = new JFrame("BALCÃO ABERTO");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JButton cadastroButton = new JButton("Cadastrar Peça");
        JButton consultaButton = new JButton("Consultar");
        JButton excluirButton = new JButton("Excluir Peça");  

        // Adicionar lógica para mostrar ou ocultar botões com base no tipo de usuário
        switch (tipoUsuario) {
            case "admin" -> {
            }
            case "leiloeiro" -> // Se o nome do usuário for 'leiloeiro', mostrar APENAS os botões de 'Cadastrar Peça' e 'Pesquisar Peça'
                excluirButton.setVisible(false);
            case "usuario" -> {
                // Se o nome do usuário for 'usuario', mostrar APENAS o botão de 'Pesquisar Peça'
                cadastroButton.setVisible(false);
                excluirButton.setVisible(false);
            }
            default -> {
            }
        }
        // Se o nome do usuário for 'admin', mostrar todos os botões

        cadastroButton.addActionListener((ActionEvent e) -> {
            CadastrarPeca cadastroTela = new CadastrarPeca();
            cadastroTela.setVisible(true);
        });

        consultaButton.addActionListener((ActionEvent e) -> {
            PesquisarPeca pesquisarTela = new PesquisarPeca();
            pesquisarTela.setVisible(true);
        });

        excluirButton.addActionListener((ActionEvent e) -> {
            ExcluirPeca excluirTela = new ExcluirPeca();
            excluirTela.setVisible(true);
        });

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));

        JLabel logotipo = new JLabel("BALCÃO ABERTO");
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

    private static class PaginaLogin extends JFrame {

        private JTextField campoUsuario;
        private JPasswordField campoSenha;
        private JButton botaoLogin;
        private boolean autenticado;
        private String tipoUsuario;

        public PaginaLogin() {
            setTitle("CENAFLIX");

            campoUsuario = new JTextField(10);
            campoSenha = new JPasswordField(10);
            botaoLogin = new JButton("Login");

            botaoLogin.addActionListener((ActionEvent e) -> {
                String usuario = campoUsuario.getText();
                String senha = String.valueOf(campoSenha.getPassword());

                if (usuario.isEmpty() || senha.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor, digite um usuário e uma senha.");
                    return;
                }

                autenticado = verificarAutenticidade(usuario, senha);
                tipoUsuario = obterTipoUsuario(usuario);

                if (autenticado) {
                    JOptionPane.showMessageDialog(this, "Login bem-sucedido!");
                } else {
                    JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos.");
                }
            });

            setLayout(new BorderLayout());

            JPanel panelFormularioLogin = new JPanel();
            panelFormularioLogin.setLayout(new GridLayout(5, 1));

            JLabel rotuloUsuario = new JLabel("Login:");
            panelFormularioLogin.add(rotuloUsuario);
            panelFormularioLogin.add(campoUsuario);

            JLabel rotuloSenha = new JLabel("Senha:");
            panelFormularioLogin.add(rotuloSenha);
            panelFormularioLogin.add(campoSenha);

            panelFormularioLogin.add(botaoLogin);

            add(panelFormularioLogin, BorderLayout.CENTER);

            setSize(350, 200);
            setLocationRelativeTo(null);
        }

        private boolean verificarAutenticidade(String usuario, String senha) {
            // TODO: Adicionar lógica de autenticação, como verificar no banco de dados
            // Se a autenticação for bem-sucedida, retorne true; caso contrário, retorne false.
            // Por enquanto, apenas retorna true para fins de demonstração.
            return true;
        }

        private String obterTipoUsuario(String usuario) {
            // TODO: Adicionar lógica para obter o tipo de usuário do banco de dados
            // Por enquanto, retorna tipos fixos com base no nome do usuário para fins de demonstração.
            return switch (usuario) {
                case "admin" -> "admin";
                case "leiloeiro" -> "leiloeiro";
                default -> "usuario";
            };
        }

        public boolean isAutenticado() {
            return autenticado;
        }

        public String getTipoUsuario() {
            return tipoUsuario;
        }

        public void addLoginListener(ActionListener listener) {
            botaoLogin.addActionListener(listener);
        }
    }

    private static class CadastrarPeca extends JFrame {
        public CadastrarPeca() {
            setTitle("Cadastrar Peca");
            setSize(300, 200);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
        }
    }

    private static class PesquisarPeca extends JFrame {
        public PesquisarPeca() {
            setTitle("Pesquisar Peça");
            setSize(300, 200);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
        }
    }

    private static class ExcluirPeca extends JFrame {
        public ExcluirPeca() {
            setTitle("Excluir Peca");
            setSize(300, 200);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
        }
    }
}
