package gui;

import java.sql.*;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class Booking_DBConnection {

    private static final String host = "jdbc:derby://localhost:1527/Booking_DBConnection";
    private static final String user = "Yanna";
    private static final String pass = "Yannayann4";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(host, user, pass);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection failed!");
            e.printStackTrace();
        }
        return null;
    }

    private static String getBookingTable(String mealType) {
        if (mealType.equalsIgnoreCase("LUNCH")) {
            return "LUNCH_BOOKING";
        } else {
            return "DINNER_BOOKING";
        }
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return Pattern.matches(regex, email);
    }

    public static boolean isValidPhone(String phone) {
        return phone.matches("\\d{10,15}");
    }

    public static boolean isDuplicateBooking(String email, String phone, String mealType, java.sql.Date date) {
        String table = getBookingTable(mealType);
        String query = "SELECT COUNT(*) FROM " + table + " WHERE email=? AND phone=? AND date=?";

        try (Connection conn = getConnection()) {
            if (conn == null) return false;

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, email);
                stmt.setString(2, phone);
                stmt.setDate(3, date);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int getTotalBooked(String mealType, java.sql.Date date) {
        String table = getBookingTable(mealType);
        String query = "SELECT SUM(pax) FROM " + table + " WHERE date=?";

        try (Connection conn = getConnection()) {
            if (conn == null) return 0;

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setDate(1, date);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int total = rs.getInt(1);
                        return rs.wasNull() ? 0 : total;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getNextBookingId(String mealType) {
        String table = getBookingTable(mealType);
        String query = "SELECT MAX(booking_id) FROM " + table;

        try (Connection conn = getConnection()) {
            if (conn == null) return 1;

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int max = rs.getInt(1);
                        return rs.wasNull() ? 1 : max + 1;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static boolean saveBooking(int bookingId, String firstName, String lastName, String email,
                                      String phone, java.sql.Date date, String mealType, int pax) {

        String table = getBookingTable(mealType);
        String query = "INSERT INTO " + table + " (booking_id, first_name, last_name, email, phone, date, pax) VALUES (?,?,?,?,?,?,?)";

        try (Connection conn = getConnection()) {
            if (conn == null) return false;

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, bookingId);
                stmt.setString(2, firstName);
                stmt.setString(3, lastName);
                stmt.setString(4, email);
                stmt.setString(5, phone);
                stmt.setDate(6, date);
                stmt.setInt(7, pax);

                return stmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean savePayment(int bookingId, String paymentMethod, String payerName,
                                     String ewalletNum, String cardNum) {

        String sql = "INSERT INTO BOOKING_PAYMENT (booking_id, payment_method, payer_name, ewallet_num, card_num) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection()) {
            if (conn == null) return false;

            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setInt(1, bookingId);
                pst.setString(2, paymentMethod);
                pst.setString(3, payerName);
                pst.setString(4, (ewalletNum == null || ewalletNum.isEmpty()) ? null : ewalletNum);
                pst.setString(5, (cardNum == null || cardNum.isEmpty()) ? null : cardNum);

                return pst.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static BookingPaymentData getPaymentByBookingId(int bookingId) {
        String sql = "SELECT * FROM BOOKING_PAYMENT WHERE booking_id=?";

        try (Connection conn = getConnection()) {
            if (conn == null) return null;

            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setInt(1, bookingId);

                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        return new BookingPaymentData(
                                rs.getString("payment_method"),
                                rs.getString("payer_name"),
                                rs.getString("ewallet_num"),
                                rs.getString("card_num")
                        );
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class BookingPaymentData {
        public final String paymentMethod;
        public final String payerName;
        public final String ewalletNum;
        public final String cardNum;

        public BookingPaymentData(String paymentMethod, String payerName,
                                  String ewalletNum, String cardNum) {
            this.paymentMethod = paymentMethod;
            this.payerName = payerName;
            this.ewalletNum = ewalletNum;
            this.cardNum = cardNum;
        }
    }
}

