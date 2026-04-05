package gui;
import java.sql.*;
import javax.swing.JOptionPane;

public class Connect {
    public Connection con; 
    
    public void DoConnect() {
        try {
            String host = "jdbc:derby://localhost:1527/DBHOUSE";
            con = DriverManager.getConnection(host, "dbhouse", "dbhouse");
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, "Error (Connection): " + err.getMessage());
        }
    }
}