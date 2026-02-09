import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    private static final String URL =
            "jdbc:mysql://localhost:3306/diario_leitura?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "Clara";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
