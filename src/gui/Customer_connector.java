

package gui;
import java.sql.*;
import java.util.logging.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class Customer_connector extends javax.swing.JFrame {
    

    Connection con;
    Statement stmt;
    ResultSet rs;
    DefaultTableModel UPCOMINGBOOKING = new DefaultTableModel();
    int pax;
    String date, time; 

    public void doconn( ) {
        try{
            String host ="jdbc:derby://localhost:1527/Customer_UpcomingBookings";
            String uName ="CP2G2";
            String uPass ="123";
            con = DriverManager.getConnection(host, uName, uPass);
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM UPCOMINGBOOKING";
            rs = stmt.executeQuery(sql);
        }catch(SQLException err){
            JOptionPane.showMessageDialog(Customer_connector.this, err.getMessage());
        }
    }
    
    public void Refresh_RS_STMT(){
        try {
            stmt.close();
            rs.close();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM UPCOMING BOOKING"; 
            rs = stmt.executeQuery(sql);
        }catch(SQLException ex) {
            Logger.getLogger(Customer_connector.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    } 
        }
    