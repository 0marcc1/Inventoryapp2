import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnector {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/store";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }

    public static void addProduct(Product product) {
        try (Connection connection = getConnection()) {
            String query = "INSERT INTO products (productID, name, costOfSale, costOfPurchase, quantity) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, product.getProductID());
                preparedStatement.setString(2, product.getName());
                preparedStatement.setDouble(3, product.getCostOfSale());
                preparedStatement.setDouble(4, product.getCostOfPurchase());
                preparedStatement.setInt(5, product.getQuantity());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    public static void deleteProduct(String productID) {
        try (Connection connection = getConnection()) {
            String query = "DELETE FROM products WHERE productID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, productID);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Product deleted successfully from the database.");
                } else {
                    System.out.println("Product with ID " + productID + " not found in the database.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error deleting product from the database.");
        }
    }
}