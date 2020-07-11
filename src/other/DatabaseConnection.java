package other;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    
    // FIELDS
    
    private static final String SERVER = "localhost";
    private static final Integer PORT = 1527;
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String JDBC_TYPE = "derby";
    private static final String DATABASE_NAME = "health_clinic";
    private static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    private static final String URL = "jdbc:%1s://%2s:%3s/%4s";
    private static Connection connection;
    
    // CONSTRUCTOR
    
    public DatabaseConnection() {}
    
    // HELPER FUNCTIONS
    
    private static String buildConnectionUrl () {
        return String.format(URL, JDBC_TYPE, SERVER, PORT, DATABASE_NAME);
    }
    
    public static Connection connect () {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection (buildConnectionUrl (), USER, PASSWORD);
            return connection;
        } 
        catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
    
    public static void disconnect (Connection connection) throws SQLException {
        if (connection != null && connection.isClosed()) {
            connection.close();
            return;
        }
        
        throw new SQLException ("Invalid connection object");
    }
}
