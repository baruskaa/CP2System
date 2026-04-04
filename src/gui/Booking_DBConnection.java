package gui;

import java.sql.*;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class Booking_DBConnection {

    private static final String DB_URL = "jdbc:derby:Booking_System;create=true";

    public static Connection getConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            return DriverManager.getConnection(DB_URL);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Derby Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection failed!");
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return Pattern.matches(regex, email);
    }

    public static boolean isValidPhone(String phone) {
        return phone.matches("\\d{10,15}");
    }

    // ───── Booking Methods ─────
    public static boolean isDuplicateBooking(String email, String phone, String mealType, java.sql.Date date) {
        String query = "SELECT COUNT(*) FROM bookings WHERE email=? AND phone=? AND meal_type=? AND date=?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            if (conn == null) return false;

            stmt.setString(1, email);
            stmt.setString(2, phone);
            stmt.setString(3, mealType);
            stmt.setDate(4, date);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int getTotalBooked(String mealType, java.sql.Date date) {
        String query = "SELECT SUM(pax) FROM bookings WHERE meal_type=? AND date=?";
        try (Connection conn = getConnection()) {
            if (conn == null) return 0;

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, mealType);
                stmt.setDate(2, date);
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
        String query = "SELECT MAX(booking_id) FROM bookings WHERE meal_type=?";
        try (Connection conn = getConnection()) {
            if (conn == null) return 1;

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, mealType);
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
        String query = "INSERT INTO bookings (Booking_Id, first_name, last_name, email, phone, date, meal_type, pax) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection conn = getConnection()) {
            if (conn == null) return false;

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, bookingId);
                stmt.setString(2, firstName);
                stmt.setString(3, lastName);
                stmt.setString(4, email);
                stmt.setString(5, phone);
                stmt.setDate(6, date);
                stmt.setString(7, mealType);
                stmt.setInt(8, pax);

                return stmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ───── Payment Methods ─────
    public static boolean savePayment(int bookingId, String paymentMethod, String payerName,
                                      String ewalletNum, String cardNum, String secNum, String cardExp) {
        String sql = "INSERT INTO booking_payment (booking_id, payment_method, payer_name, ewallet_num, card_num, sec_num, card_exp) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection()) {
            if (conn == null) return false;

            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setInt(1, bookingId);
                pst.setString(2, paymentMethod);
                pst.setString(3, payerName);
                pst.setString(4, ewalletNum.isEmpty() ? null : ewalletNum);
                pst.setString(5, cardNum.isEmpty() ? null : cardNum);
                pst.setString(6, secNum.isEmpty() ? null : secNum);
                pst.setString(7, cardExp.isEmpty() ? null : cardExp);

                return pst.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static BookingPaymentData getPaymentByBookingId(int bookingId) {
        String sql = "SELECT * FROM booking_payment WHERE booking_id=?";
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
                                rs.getString("card_num"),
                                rs.getString("sec_num"),
                                rs.getString("card_exp")
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
        public final String secNum;
        public final String cardExp;

        public BookingPaymentData(String paymentMethod, String payerName, String ewalletNum,
                                  String cardNum, String secNum, String cardExp) {
            this.paymentMethod = paymentMethod;
            this.payerName = payerName;
            this.ewalletNum = ewalletNum;
            this.cardNum = cardNum;
            this.secNum = secNum;
            this.cardExp = cardExp;
        }
    }
}