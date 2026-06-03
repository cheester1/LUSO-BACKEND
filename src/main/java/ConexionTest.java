import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionTest {
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://srv1905.hstgr.io:3306/u384030255_LusoTecSac",
                    "u384030255_LusoTec",
                    "LusoTest2026!"
            );

            System.out.println("OK CONECTADO");
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}