/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Bruno Cezar
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProdutosDAO {

    private final String DATABASE_URL = "jdbc:mysql://localhost:3306/uc11_Bruno_Castro";
    private final String USER = "root";
    private final String PASSWORD = "=senaCEad2022";

    public void venderProduto(String nomeProduto) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD)) {
            String sql = "UPDATE produtos SET status = 'Vendido' WHERE nome = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, nomeProduto);
                preparedStatement.executeUpdate();
            }
        }
    }
}
