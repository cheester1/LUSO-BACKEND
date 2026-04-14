import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionTest {
    public static void main(String[] args) {

        String url = "jdbc:mysql://auth-db1905.hstgr.io:3306/u384030255_lusosac";
        String user = "u384030255_luso";
        String password = System.getenv("DB_PASSWORD");

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ CONEXIÓN EXITOSA");
        } catch (Exception e) {
            System.out.println("❌ ERROR");
            e.printStackTrace();
        }
    }
}