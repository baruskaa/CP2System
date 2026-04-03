/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.RowFilter;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String USER = "your_username";
    private static final String PASSWORD = "your_password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

public class Manager extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Manager.class.getName());
    private TableRowSorter<DefaultTableModel> sorter_today;
    private TableRowSorter<DefaultTableModel> sorter_today2;
    private TableRowSorter<DefaultTableModel> sorter_history;
    private TableRowSorter<DefaultTableModel> sorter_upcom;
    private TableRowSorter<DefaultTableModel> sorter_IHR;
    public void loadData() {
        loadTableData("Customer", tbl_res,tbl_walkin);
        loadTableData("Reservation", tbl_res);
        loadTableData("DineIn", tbl_walkin);
    }
    
    public Manager() {
        initComponents();
        updateTotalPaxLabel();
        updateAvailableSeatsLabel();
        updateTotalwalkinLabel();
        loadData();
        this.setLocationRelativeTo(null);

        // TableRowSorter for sorting on column header clicks
        DefaultTableModel model_today = (DefaultTableModel) tbl_res.getModel();
        sorter_today = new TableRowSorter<>(model_today);
        tbl_res.setRowSorter(sorter_today);
        
        DefaultTableModel model_today2 = (DefaultTableModel) tbl_walkin.getModel();
        sorter_today2 = new TableRowSorter<>(model_today2);
        tbl_walkin.setRowSorter(sorter_today2);

        DefaultTableModel model_history = (DefaultTableModel) tbl_history.getModel();
        sorter_history = new TableRowSorter<>(model_history);
        tbl_history.setRowSorter(sorter_history);
        
        DefaultTableModel model_upcom = (DefaultTableModel) tbl_future.getModel();
        sorter_upcom = new TableRowSorter<>(model_upcom);
        tbl_future.setRowSorter(sorter_upcom);
        
        DefaultTableModel model_IHR = (DefaultTableModel) tbl_IHR.getModel();
        sorter_IHR = new TableRowSorter<>(model_IHR);
        tbl_IHR.setRowSorter(sorter_IHR);

        // Center alignment for all columns
        DefaultTableCellRenderer center_today = new DefaultTableCellRenderer();
        center_today.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tbl_res.getColumnCount(); i++) {
            tbl_res.getColumnModel().getColumn(i).setCellRenderer(center_today);
        }
        
        DefaultTableCellRenderer center_today2 = new DefaultTableCellRenderer();
        center_today2.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tbl_walkin.getColumnCount(); i++) {
            tbl_walkin.getColumnModel().getColumn(i).setCellRenderer(center_today2);
        }
        
        DefaultTableCellRenderer center_history = new DefaultTableCellRenderer();
        center_history.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tbl_history.getColumnCount(); i++) {
        tbl_history.getColumnModel().getColumn(i).setCellRenderer(center_history);
        }
        
        DefaultTableCellRenderer center_upcom = new DefaultTableCellRenderer();
        center_upcom.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tbl_future.getColumnCount(); i++) {
        tbl_future.getColumnModel().getColumn(i).setCellRenderer(center_upcom);  // FIXED: center_upcom
        }
        
        DefaultTableCellRenderer center_IHR = new DefaultTableCellRenderer();
        center_IHR.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tbl_IHR.getColumnCount(); i++) {
        tbl_IHR.getColumnModel().getColumn(i).setCellRenderer(center_IHR);  // FIXED: center_upcom
        }
        
        // SAFE TABLE NO. COMPARATOR (TODAY col 0, HISTORY col 1, UPCOM col 1)
    sorter_today.setComparator(0, (n1, n2) -> {
        if (n1 == null) return 1;
        if (n2 == null) return -1;
        try {
            return Integer.compare(Integer.parseInt(n1.toString()), Integer.parseInt(n2.toString()));
        } catch (NumberFormatException e) {
            return n1.toString().compareTo(n2.toString());
        }
    });
    
    sorter_today2.setComparator(0, (n1, n2) -> {
        if (n1 == null) return 1;
        if (n2 == null) return -1;
        try {
            return Integer.compare(Integer.parseInt(n1.toString()), Integer.parseInt(n2.toString()));
        } catch (NumberFormatException e) {
            return n1.toString().compareTo(n2.toString());
        }
    });
    
    sorter_history.setComparator(1, (n1, n2) -> {
        if (n1 == null) return 1;
        if (n2 == null) return -1;
        try {
            return Integer.compare(Integer.parseInt(n1.toString()), Integer.parseInt(n2.toString()));
        } catch (NumberFormatException e) {
            return n1.toString().compareTo(n2.toString());
        }
    });
    
    sorter_upcom.setComparator(1, (n1, n2) -> {
        if (n1 == null) return 1;
        if (n2 == null) return -1;
        try {
            return Integer.compare(Integer.parseInt(n1.toString()), Integer.parseInt(n2.toString()));
        } catch (NumberFormatException e) {
            return n1.toString().compareTo(n2.toString());
        }
    });
    
    sorter_IHR.setComparator(1, (n1, n2) -> {
        if (n1 == null) return 1;
        if (n2 == null) return -1;
        try {
            return Integer.compare(Integer.parseInt(n1.toString()), Integer.parseInt(n2.toString()));
        } catch (NumberFormatException e) {
            return n1.toString().compareTo(n2.toString());
        }
    });
    
    // SAFE PAX COMPARATOR (TODAY col 3, HISTORY/UPCOM col 4)
    sorter_today.setComparator(3, (n1, n2) -> {
        if (n1 == null) return 1;
        if (n2 == null) return -1;
        try {
            return Integer.compare(Integer.parseInt(n1.toString()), Integer.parseInt(n2.toString()));
        } catch (NumberFormatException e) {
            return n1.toString().compareTo(n2.toString());
        }
    });
    
    sorter_today2.setComparator(3, (n1, n2) -> {
        if (n1 == null) return 1;
        if (n2 == null) return -1;
        try {
            return Integer.compare(Integer.parseInt(n1.toString()), Integer.parseInt(n2.toString()));
        } catch (NumberFormatException e) {
            return n1.toString().compareTo(n2.toString());
        }
    });
    
    sorter_history.setComparator(4, (n1, n2) -> {
        if (n1 == null) return 1;
        if (n2 == null) return -1;
        try {
            return Integer.compare(Integer.parseInt(n1.toString()), Integer.parseInt(n2.toString()));
        } catch (NumberFormatException e) {
            return n1.toString().compareTo(n2.toString());
        }
    });
    
    sorter_upcom.setComparator(4, (n1, n2) -> {
        if (n1 == null) return 1;
        if (n2 == null) return -1;
        try {
            return Integer.compare(Integer.parseInt(n1.toString()), Integer.parseInt(n2.toString()));
        } catch (NumberFormatException e) {
            return n1.toString().compareTo(n2.toString());
        }
    });
    
    sorter_IHR.setComparator(4, (n1, n2) -> {
        if (n1 == null) return 1;
        if (n2 == null) return -1;
        try {
            return Integer.compare(Integer.parseInt(n1.toString()), Integer.parseInt(n2.toString()));
        } catch (NumberFormatException e) {
            return n1.toString().compareTo(n2.toString());
        }
    });
    
    // TIME COMPARATOR (Lunch first) - TODAY col 4, HISTORY/UPCOM col 5
    sorter_today.setComparator(4, (t1, t2) -> {
        if (t1 == null) return 1;
        if (t2 == null) return -1;
        String time1 = t1.toString();
        String time2 = t2.toString();
        if (time1.equals(time2)) return 0;
        if (time1.equals("Lunch")) return -1;
        if (time2.equals("Lunch")) return 1;
        return time1.compareTo(time2);
    });
    
    sorter_today2.setComparator(4, (t1, t2) -> {
        if (t1 == null) return 1;
        if (t2 == null) return -1;
        String time1 = t1.toString();
        String time2 = t2.toString();
        if (time1.equals(time2)) return 0;
        if (time1.equals("Lunch")) return -1;
        if (time2.equals("Lunch")) return 1;
        return time1.compareTo(time2);
    });
    
    sorter_history.setComparator(5, (t1, t2) -> {  // FIXED: Column 5 for HISTORY
        if (t1 == null) return 1;
        if (t2 == null) return -1;
        String time1 = t1.toString();
        String time2 = t2.toString();
        if (time1.equals(time2)) return 0;
        if (time1.equals("Lunch")) return -1;
        if (time2.equals("Lunch")) return 1;
        return time1.compareTo(time2);
    });
    
    sorter_upcom.setComparator(5, (t1, t2) -> {
        if (t1 == null) return 1;
        if (t2 == null) return -1;
        String time1 = t1.toString();
        String time2 = t2.toString();
        if (time1.equals(time2)) return 0;
        if (time1.equals("Lunch")) return -1;
        if (time2.equals("Lunch")) return 1;
        return time1.compareTo(time2);
    });
    
    sorter_IHR.setComparator(5, (t1, t2) -> {
        if (t1 == null) return 1;
        if (t2 == null) return -1;
        String time1 = t1.toString();
        String time2 = t2.toString();
        if (time1.equals(time2)) return 0;
        if (time1.equals("Lunch")) return -1;
        if (time2.equals("Lunch")) return 1;
        return time1.compareTo(time2);
    }); 
        // Search filter
        search_today.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }

            private void filter() {
                String text = search_today.getText().trim();
                if (text.isEmpty()) {
                    sorter_today.setRowFilter(null);
                } else {
                    sorter_today.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
        
        search_today.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }

            private void filter() {
                String text = search_today.getText().trim();
                if (text.isEmpty()) {
                    sorter_today2.setRowFilter(null);
                } else {
                    sorter_today2.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
        
        search_history.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }

            private void filter() {
                String text = search_history.getText().trim();
                if (text.isEmpty()) {
                    sorter_history.setRowFilter(null);
                } else {
                    sorter_history.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
        
        search_upcom.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }

            private void filter() {
                String text = search_upcom.getText().trim();
                if (text.isEmpty()) {
                    sorter_upcom.setRowFilter(null);
                } else {
                    sorter_upcom.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
        search_IHR.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }

            private void filter() {
                String text = search_IHR.getText().trim();
                if (text.isEmpty()) {
                    sorter_IHR.setRowFilter(null);
                } else {
                    sorter_IHR.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
        
        // Initial state: Show TODAY panel, hide HISTORY panel
        pnl_dine_in.setVisible(true);
        pnl_history.setVisible(false);
        pnl_future.setVisible(false);
        pnl_IHR.setVisible(false);

        // Set TODAY button as active
        btn_today.setForeground(new Color(255, 200, 120)); // Highlight active
        
        date_history.addPropertyChangeListener("date", evt -> applyHistoryDateFilter());

        // NEW: Handle clearing date textbox
        date_history.getDateEditor().getUiComponent().addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (date_history.getDate() == null) {
                    sorter_history.setRowFilter(null);
                }
            }
        });
        
         date_upcom.addPropertyChangeListener("date", evt -> applyUpcomingDateFilter());

        // NEW: Handle clearing date textbox
        date_upcom.getDateEditor().getUiComponent().addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (date_upcom.getDate() == null) {
                    sorter_upcom.setRowFilter(null);
                }
            }
        });
        
        

        makeFlatButton(btn_logout);
        
        // Example: custom header background and centered text
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tbl_res.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER); // center text
        tbl_res.getTableHeader().setForeground(new Color(55, 77, 94));  
        tbl_res.getTableHeader().setFont(new java.awt.Font("Century Gothic", java.awt.Font.BOLD, 12));  
        
        DefaultTableCellRenderer headerRenderer5 = (DefaultTableCellRenderer) tbl_walkin.getTableHeader().getDefaultRenderer();
        headerRenderer5.setHorizontalAlignment(JLabel.CENTER); // center text
        tbl_walkin.getTableHeader().setForeground(new Color(55, 77, 94));  
        tbl_walkin.getTableHeader().setFont(new java.awt.Font("Century Gothic", java.awt.Font.BOLD, 12));
        
        DefaultTableCellRenderer headerRenderer2 = (DefaultTableCellRenderer) tbl_history.getTableHeader().getDefaultRenderer();
        headerRenderer2.setHorizontalAlignment(JLabel.CENTER); // center text
        tbl_history.getTableHeader().setForeground(new Color(55, 77, 94));  
        tbl_history.getTableHeader().setFont(new java.awt.Font("Century Gothic", java.awt.Font.BOLD, 14));  
      
        DefaultTableCellRenderer headerRenderer3 = (DefaultTableCellRenderer) tbl_future.getTableHeader().getDefaultRenderer();
        headerRenderer3.setHorizontalAlignment(JLabel.CENTER); // center text
        tbl_future.getTableHeader().setForeground(new Color(55, 77, 94));  
        tbl_future.getTableHeader().setFont(new java.awt.Font("Century Gothic", java.awt.Font.BOLD, 14));  
        
        DefaultTableCellRenderer headerRenderer4 = (DefaultTableCellRenderer) tbl_IHR.getTableHeader().getDefaultRenderer();
        headerRenderer4.setHorizontalAlignment(JLabel.CENTER); // center text
        tbl_IHR.getTableHeader().setForeground(new Color(55, 77, 94));  
        tbl_IHR.getTableHeader().setFont(new java.awt.Font("Century Gothic", java.awt.Font.BOLD, 14));  
    
    }
    // Method to update the total reservations label
        private void updateTotalPaxLabel() {
        int totalPax = 0;
        int rowCount = tbl_res.getRowCount();

        for (int i = 0; i < rowCount; i++) {
        
            Object paxValue = tbl_res.getValueAt(i, 7);
            if (paxValue != null) {
                try {
                    int pax = Integer.parseInt(paxValue.toString());
                    totalPax += pax;
                } catch (NumberFormatException e) {
                // Handle invalid number format if needed
                }
         }
        }

    // Set the label text to show the total PAX
        lbl_totalres.setText("TOTAL RES: " + totalPax);
    }
        
                private void updateTotalwalkinLabel() {
        int totalwalkin = 0;
        int rowCount = tbl_walkin.getRowCount();

        for (int i = 0; i < rowCount; i++) {
        
            Object paxValue = tbl_walkin.getValueAt(i, 6);
            if (paxValue != null) {
                try {
                    int pax = Integer.parseInt(paxValue.toString());
                    totalwalkin += pax;
                } catch (NumberFormatException e) {
                // Handle invalid number format if needed
                }
         }
        }

    // Set the label text to show the total PAX
        lbl_totalwalkin.setText("TOTAL WALK-IN: " + totalwalkin);
    }
        
        private final int MAX_SEATS = 100; // Maximum seats available

private void updateAvailableSeatsLabel() {
    int totalPax = 0;
    int rowCount = tbl_res.getRowCount();

    for (int i = 0; i < rowCount; i++) {
        
        Object paxValue = tbl_res.getValueAt(i, 7);
        if (paxValue != null) {
            try {
                int pax = Integer.parseInt(paxValue.toString());
                totalPax += pax;
            } catch (NumberFormatException e) {
                // Handle invalid number format if needed
            }
        }
    }
    int totalwalkin = 0;
        int rowcunt = tbl_walkin.getRowCount();

        for (int i = 0; i < rowcunt; i++) {
        
            Object paxValue = tbl_walkin.getValueAt(i, 7);
            if (paxValue != null) {
                try {
                    int pax = Integer.parseInt(paxValue.toString());
                    totalwalkin += pax;
                } catch (NumberFormatException e) {
                // Handle invalid number format if needed
                }
         }
        }

    int availableSeats = MAX_SEATS - (totalPax+totalwalkin);
    if (availableSeats < 0) {
        availableSeats = 0; // Prevent negative available seats
    }

    // Set the label text to show the total available seats
    lbl_totalavail.setText("AVAILABLE SEATS: " + availableSeats);
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_nav = new javax.swing.JPanel();
        btn_today = new javax.swing.JButton();
        btn_upcom = new javax.swing.JButton();
        btn_history = new javax.swing.JButton();
        btn_logout = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btn_IHR = new javax.swing.JButton();
        pnl_header = new javax.swing.JPanel();
        lbl_BookingDash = new javax.swing.JLabel();
        lbl_username = new javax.swing.JLabel();
        pnl_dine_in = new javax.swing.JPanel();
        lbl_Dinein = new javax.swing.JLabel();
        lbl_totalres = new javax.swing.JLabel();
        lbl_walkin = new javax.swing.JLabel();
        lbl_reservation = new javax.swing.JLabel();
        lbl_search = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_res = new javax.swing.JTable();
        lbl_totalwalkin = new javax.swing.JLabel();
        lbl_totalavail = new javax.swing.JLabel();
        search_today = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbl_walkin = new javax.swing.JTable();
        bg_today = new javax.swing.JLabel();
        pnl_history = new javax.swing.JPanel();
        date_history = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        search_history = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_history = new javax.swing.JTable();
        bg_today1 = new javax.swing.JLabel();
        pnl_future = new javax.swing.JPanel();
        date_upcom = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        search_upcom = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_future = new javax.swing.JTable();
        bg_today2 = new javax.swing.JLabel();
        pnl_IHR = new javax.swing.JPanel();
        lbl_ihr = new javax.swing.JLabel();
        lbl_searchihr = new javax.swing.JLabel();
        search_IHR = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_IHR = new javax.swing.JTable();
        bg_today3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnl_nav.setBackground(new java.awt.Color(55, 77, 94));
        pnl_nav.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_today.setBackground(new java.awt.Color(55, 77, 94));
        btn_today.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        btn_today.setForeground(new java.awt.Color(255, 255, 255));
        btn_today.setText("DINE-IN");
        btn_today.setBorder(null);
        btn_today.setContentAreaFilled(false);
        btn_today.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_today.setFocusPainted(false);
        btn_today.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_todayMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_todayMouseExited(evt);
            }
        });
        btn_today.addActionListener(this::btn_todayActionPerformed);
        pnl_nav.add(btn_today, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 90, 30));

        btn_upcom.setBackground(new java.awt.Color(55, 77, 94));
        btn_upcom.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        btn_upcom.setForeground(new java.awt.Color(255, 255, 255));
        btn_upcom.setText("FUTURE");
        btn_upcom.setBorder(null);
        btn_upcom.setContentAreaFilled(false);
        btn_upcom.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_upcom.setFocusPainted(false);
        btn_upcom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_upcomMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_upcomMouseExited(evt);
            }
        });
        btn_upcom.addActionListener(this::btn_upcomActionPerformed);
        pnl_nav.add(btn_upcom, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 90, 30));

        btn_history.setBackground(new java.awt.Color(55, 77, 94));
        btn_history.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        btn_history.setForeground(new java.awt.Color(255, 255, 255));
        btn_history.setText("HISTORY");
        btn_history.setBorder(null);
        btn_history.setContentAreaFilled(false);
        btn_history.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_history.setFocusPainted(false);
        btn_history.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_historyMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_historyMouseExited(evt);
            }
        });
        btn_history.addActionListener(this::btn_historyActionPerformed);
        pnl_nav.add(btn_history, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 90, 30));

        btn_logout.setBackground(new java.awt.Color(153, 0, 0));
        btn_logout.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        btn_logout.setForeground(new java.awt.Color(255, 255, 255));
        btn_logout.setText("LOG OUT");
        btn_logout.setBorder(null);
        btn_logout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_logout.addActionListener(this::btn_logoutActionPerformed);
        pnl_nav.add(btn_logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 511, 80, 30));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/logo.png"))); // NOI18N
        pnl_nav.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, -1, 80));

        btn_IHR.setBackground(new java.awt.Color(55, 77, 94));
        btn_IHR.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        btn_IHR.setForeground(new java.awt.Color(255, 255, 255));
        btn_IHR.setText("IHR");
        btn_IHR.setBorder(null);
        btn_IHR.setContentAreaFilled(false);
        btn_IHR.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_IHR.setFocusPainted(false);
        btn_IHR.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_IHRMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_IHRMouseExited(evt);
            }
        });
        btn_IHR.addActionListener(this::btn_IHRActionPerformed);
        pnl_nav.add(btn_IHR, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 90, 30));

        getContentPane().add(pnl_nav, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 580));

        pnl_header.setBackground(new java.awt.Color(55, 91, 115));
        pnl_header.setMaximumSize(new java.awt.Dimension(681, 115));
        pnl_header.setPreferredSize(new java.awt.Dimension(681, 115));

        lbl_BookingDash.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        lbl_BookingDash.setForeground(new java.awt.Color(255, 255, 255));
        lbl_BookingDash.setText("BOOKING DASHBOARD");

        lbl_username.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        lbl_username.setForeground(new java.awt.Color(255, 255, 255));
        lbl_username.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_username.setText("Rodriguez, Penelope");

        javax.swing.GroupLayout pnl_headerLayout = new javax.swing.GroupLayout(pnl_header);
        pnl_header.setLayout(pnl_headerLayout);
        pnl_headerLayout.setHorizontalGroup(
            pnl_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_headerLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lbl_BookingDash)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 211, Short.MAX_VALUE)
                .addComponent(lbl_username, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        pnl_headerLayout.setVerticalGroup(
            pnl_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_headerLayout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addGroup(pnl_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_BookingDash)
                    .addComponent(lbl_username))
                .addGap(33, 33, 33))
        );

        getContentPane().add(pnl_header, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 740, -1));

        pnl_dine_in.setForeground(new java.awt.Color(202, 199, 199));
        pnl_dine_in.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_Dinein.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        lbl_Dinein.setForeground(new java.awt.Color(55, 77, 94));
        lbl_Dinein.setText("DINE-IN");
        pnl_dine_in.add(lbl_Dinein, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 170, 50));

        lbl_totalres.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        lbl_totalres.setForeground(new java.awt.Color(102, 102, 102));
        lbl_totalres.setText("Total:");
        pnl_dine_in.add(lbl_totalres, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 160, 40));

        lbl_walkin.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        lbl_walkin.setForeground(new java.awt.Color(102, 102, 102));
        lbl_walkin.setText("Walk-In");
        pnl_dine_in.add(lbl_walkin, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 110, 20));

        lbl_reservation.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        lbl_reservation.setForeground(new java.awt.Color(102, 102, 102));
        lbl_reservation.setText("Reservations");
        pnl_dine_in.add(lbl_reservation, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 110, 20));

        lbl_search.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lbl_search.setForeground(new java.awt.Color(55, 77, 94));
        lbl_search.setText("Search:");
        pnl_dine_in.add(lbl_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 30, 60, 40));

        jScrollPane1.setForeground(new java.awt.Color(55, 77, 94));

        tbl_res.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        tbl_res.setForeground(new java.awt.Color(55, 77, 94));
        tbl_res.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, "Juan Dela Cruz", null, "09357873489", null, "Lunch",  new Integer(4), "10"},
                {null, "Maria Santos", null, "09174532356", null, "Lunch",  new Integer(3), "5"},
                {null, "Louise Lopez", null, "09876541453", null, "Dinner",  new Integer(2), "2"},
                {null, "Rhian Espinosa", null, "09258653421", null, "Dinner",  new Integer(6), "7"},
                {null, "Justine Dizon", null, "09987823421", null, "Lunch",  new Integer(7), "25"}
            },
            new String [] {
                "VIP ID", "FIRST NAME", "LASTNAME", "CONTACT", "GENDER", "DATE", "TIME", "PAX"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_res.setOpaque(false);
        jScrollPane1.setViewportView(tbl_res);
        if (tbl_res.getColumnModel().getColumnCount() > 0) {
            tbl_res.getColumnModel().getColumn(0).setResizable(false);
            tbl_res.getColumnModel().getColumn(1).setResizable(false);
            tbl_res.getColumnModel().getColumn(1).setHeaderValue("VIP ID ");
            tbl_res.getColumnModel().getColumn(2).setResizable(false);
            tbl_res.getColumnModel().getColumn(3).setResizable(false);
            tbl_res.getColumnModel().getColumn(4).setResizable(false);
            tbl_res.getColumnModel().getColumn(5).setResizable(false);
            tbl_res.getColumnModel().getColumn(6).setResizable(false);
            tbl_res.getColumnModel().getColumn(7).setResizable(false);
        }

        pnl_dine_in.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 720, 160));

        lbl_totalwalkin.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        lbl_totalwalkin.setForeground(new java.awt.Color(102, 102, 102));
        lbl_totalwalkin.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_totalwalkin.setText("Total:");
        pnl_dine_in.add(lbl_totalwalkin, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 430, 160, 40));

        lbl_totalavail.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        lbl_totalavail.setForeground(new java.awt.Color(102, 102, 102));
        lbl_totalavail.setText("Total:");
        pnl_dine_in.add(lbl_totalavail, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 430, 180, 40));

        search_today.addActionListener(this::search_todayActionPerformed);
        search_today.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_todayKeyReleased(evt);
            }
        });
        pnl_dine_in.add(search_today, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 40, 170, -1));

        jScrollPane5.setForeground(new java.awt.Color(55, 77, 94));

        tbl_walkin.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        tbl_walkin.setForeground(new java.awt.Color(55, 77, 94));
        tbl_walkin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, "09357873489", null, "Lunch",  new Integer(4), null, "10"},
                {null, "09174532356", null, "Lunch",  new Integer(3), null, "5"},
                {null, "09876541453", null, "Dinner",  new Integer(2), null, "1"},
                {null, "09258653421", null, "Dinner",  new Integer(6), null, "5"},
                {null, "09987823421", null, "Lunch",  new Integer(7), null, "8"}
            },
            new String [] {
                "FIRST NAME", "LAST NAME", "CONTACT", "GENDER", "DATE", "TIME", "PAX"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_walkin.setOpaque(false);
        jScrollPane5.setViewportView(tbl_walkin);
        if (tbl_walkin.getColumnModel().getColumnCount() > 0) {
            tbl_walkin.getColumnModel().getColumn(0).setResizable(false);
            tbl_walkin.getColumnModel().getColumn(1).setResizable(false);
            tbl_walkin.getColumnModel().getColumn(2).setResizable(false);
            tbl_walkin.getColumnModel().getColumn(3).setResizable(false);
            tbl_walkin.getColumnModel().getColumn(4).setResizable(false);
            tbl_walkin.getColumnModel().getColumn(5).setResizable(false);
            tbl_walkin.getColumnModel().getColumn(6).setResizable(false);
        }

        pnl_dine_in.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 720, 170));

        bg_today.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/bgfd.jpg"))); // NOI18N
        bg_today.setText("Today");
        pnl_dine_in.add(bg_today, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 470));

        getContentPane().add(pnl_dine_in, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 740, 470));

        pnl_history.setForeground(new java.awt.Color(202, 199, 199));
        pnl_history.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        date_history.setDateFormatString("MM-dd-yy");
        pnl_history.add(date_history, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 50, 170, -1));

        jLabel10.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(55, 77, 94));
        jLabel10.setText("Date:");
        pnl_history.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 50, 50, 20));

        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(55, 77, 94));
        jLabel7.setText("HISTORY");
        pnl_history.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 170, 50));

        jLabel9.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(55, 77, 94));
        jLabel9.setText("Search:");
        pnl_history.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 20, 60, 20));

        search_history.addActionListener(this::search_historyActionPerformed);
        search_history.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_historyKeyReleased(evt);
            }
        });
        pnl_history.add(search_history, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 20, 170, -1));

        jScrollPane2.setForeground(new java.awt.Color(55, 77, 94));

        tbl_history.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        tbl_history.setForeground(new java.awt.Color(55, 77, 94));
        tbl_history.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Juan Dela Cruz", null, null, "09357873489", "03-26-26", "Lunch",  new Integer(4)},
                {"Maria Santos", null, null, "09174532356", "03-01-26", "Lunch",  new Integer(3)},
                {"Louise Lopez", null, null, "09876541453", "04-19-26", "Lunch",  new Integer(2)},
                {"Rhian Espinosa", null, null, "09258653421", "03-01-26", "Dinner",  new Integer(6)},
                {"Justine Dizon", null, null, "09987823421", "04-19-26", "Dinner",  new Integer(7)}
            },
            new String [] {
                "FIRST NAME", "LAST NAME", "EMAIL", "CONTACT", "DATE", "TIME", "PAX"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_history.setOpaque(false);
        jScrollPane2.setViewportView(tbl_history);
        if (tbl_history.getColumnModel().getColumnCount() > 0) {
            tbl_history.getColumnModel().getColumn(0).setResizable(false);
            tbl_history.getColumnModel().getColumn(1).setResizable(false);
            tbl_history.getColumnModel().getColumn(2).setResizable(false);
            tbl_history.getColumnModel().getColumn(3).setResizable(false);
            tbl_history.getColumnModel().getColumn(4).setResizable(false);
            tbl_history.getColumnModel().getColumn(5).setResizable(false);
            tbl_history.getColumnModel().getColumn(6).setResizable(false);
        }

        pnl_history.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 700, 330));

        bg_today1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/bgfd.jpg"))); // NOI18N
        bg_today1.setText("Today");
        pnl_history.add(bg_today1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 470));

        getContentPane().add(pnl_history, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 740, 470));

        pnl_future.setForeground(new java.awt.Color(202, 199, 199));
        pnl_future.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        date_upcom.setDateFormatString("MM-dd-yy");
        pnl_future.add(date_upcom, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 50, 170, -1));

        jLabel11.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(55, 77, 94));
        jLabel11.setText("Date:");
        pnl_future.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 50, 50, 20));

        jLabel8.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(55, 77, 94));
        jLabel8.setText("FUTURE");
        pnl_future.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 310, 50));

        jLabel12.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(55, 77, 94));
        jLabel12.setText("Search:");
        pnl_future.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 20, 60, 20));

        search_upcom.addActionListener(this::search_upcomActionPerformed);
        search_upcom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_upcomKeyReleased(evt);
            }
        });
        pnl_future.add(search_upcom, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 20, 170, -1));

        jScrollPane3.setForeground(new java.awt.Color(55, 77, 94));

        tbl_future.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        tbl_future.setForeground(new java.awt.Color(55, 77, 94));
        tbl_future.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Juan Dela Cruz", null, null, "09357873489", "03-26-26", "Lunch",  new Integer(4)},
                {"Maria Santos", null, null, "09174532356", "03-01-26", "Lunch",  new Integer(3)},
                {"Louise Lopez", null, null, "09876541453", "04-19-26", "Lunch",  new Integer(2)},
                {"Rhian Espinosa", null, null, "09258653421", "03-01-26", "Dinner",  new Integer(6)},
                {"Justine Dizon", null, null, "09987823421", "04-19-26", "Dinner",  new Integer(7)}
            },
            new String [] {
                "FIRST NAME", "LAST NAME", "EMAIL", "CONTACT", "DATE", "TIME", "PAX"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_future.setOpaque(false);
        jScrollPane3.setViewportView(tbl_future);
        if (tbl_future.getColumnModel().getColumnCount() > 0) {
            tbl_future.getColumnModel().getColumn(0).setResizable(false);
            tbl_future.getColumnModel().getColumn(1).setResizable(false);
            tbl_future.getColumnModel().getColumn(2).setResizable(false);
            tbl_future.getColumnModel().getColumn(3).setResizable(false);
            tbl_future.getColumnModel().getColumn(4).setResizable(false);
            tbl_future.getColumnModel().getColumn(5).setResizable(false);
            tbl_future.getColumnModel().getColumn(6).setResizable(false);
        }

        pnl_future.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 700, 330));

        bg_today2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/bgfd.jpg"))); // NOI18N
        bg_today2.setText("Today");
        pnl_future.add(bg_today2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 470));

        getContentPane().add(pnl_future, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 740, 470));

        pnl_IHR.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_ihr.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        lbl_ihr.setForeground(new java.awt.Color(55, 77, 94));
        lbl_ihr.setText("In-House Reservation");
        pnl_IHR.add(lbl_ihr, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 390, 50));

        lbl_searchihr.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lbl_searchihr.setForeground(new java.awt.Color(55, 77, 94));
        lbl_searchihr.setText("Search:");
        pnl_IHR.add(lbl_searchihr, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 50, 60, 20));

        search_IHR.addActionListener(this::search_IHRActionPerformed);
        search_IHR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_IHRKeyReleased(evt);
            }
        });
        pnl_IHR.add(search_IHR, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 50, 170, -1));

        jScrollPane4.setForeground(new java.awt.Color(55, 77, 94));

        tbl_IHR.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        tbl_IHR.setForeground(new java.awt.Color(55, 77, 94));
        tbl_IHR.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Juan Dela Cruz", null, null, "09357873489", "03-26-26", "Lunch",  new Integer(4)},
                {"Maria Santos", null, null, "09174532356", "03-01-26", "Lunch",  new Integer(3)},
                {"Louise Lopez", null, null, "09876541453", "04-19-26", "Lunch",  new Integer(2)},
                {"Rhian Espinosa", null, null, "09258653421", "03-01-26", "Dinner",  new Integer(6)},
                {"Justine Dizon", null, null, "09987823421", "04-19-26", "Dinner",  new Integer(7)}
            },
            new String [] {
                "FIRST NAME", "LAST NAME", "EMAIL", "CONTACT", "DATE", "TIME", "PAX"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_IHR.setOpaque(false);
        jScrollPane4.setViewportView(tbl_IHR);
        if (tbl_IHR.getColumnModel().getColumnCount() > 0) {
            tbl_IHR.getColumnModel().getColumn(0).setResizable(false);
            tbl_IHR.getColumnModel().getColumn(1).setResizable(false);
            tbl_IHR.getColumnModel().getColumn(2).setResizable(false);
            tbl_IHR.getColumnModel().getColumn(3).setResizable(false);
            tbl_IHR.getColumnModel().getColumn(4).setResizable(false);
            tbl_IHR.getColumnModel().getColumn(5).setResizable(false);
            tbl_IHR.getColumnModel().getColumn(6).setResizable(false);
        }

        pnl_IHR.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 700, 330));

        bg_today3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/bgfd.jpg"))); // NOI18N
        bg_today3.setText("Today");
        pnl_IHR.add(bg_today3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, -1));

        getContentPane().add(pnl_IHR, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 740, 470));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_todayMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_todayMouseEntered
    if (!btn_today.getForeground().equals(new Color(255, 200, 120))) {
        btn_today.setForeground(new Color(255, 200, 120));
    }
    btn_today.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));       // TODO add your handling code here:
    }//GEN-LAST:event_btn_todayMouseEntered

    private void btn_upcomMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_upcomMouseEntered
        if (!btn_upcom.getForeground().equals(new Color(255, 200, 120))) {
            btn_upcom.setForeground(new Color(255, 200, 120));
        }
        btn_upcom.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
         // TODO add your handling code here:
    }//GEN-LAST:event_btn_upcomMouseEntered

    private void btn_historyMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_historyMouseEntered
    if (!btn_history.getForeground().equals(new Color(255, 200, 120))) {
        btn_history.setForeground(new Color(255, 200, 120));
    }
    btn_history.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));        // TODO add your handling code here:
    }//GEN-LAST:event_btn_historyMouseEntered

    private void btn_todayMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_todayMouseExited
    if (!pnl_dine_in.isVisible()) {  // Only reset if not active tab
        btn_today.setForeground(Color.WHITE);
    }       // TODO add your handling code here:
    }//GEN-LAST:event_btn_todayMouseExited

    private void btn_upcomMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_upcomMouseExited
    if (!pnl_future.isVisible()) {  // Only reset if not active tab
        btn_upcom.setForeground(Color.WHITE);
    }        // TODO add your handling code here:
    }//GEN-LAST:event_btn_upcomMouseExited

    private void btn_historyMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_historyMouseExited
    if (!pnl_history.isVisible()) {  // Only reset if not active tab
        btn_history.setForeground(Color.WHITE);
    }        // TODO add your handling code here:
    }//GEN-LAST:event_btn_historyMouseExited

    private void search_todayKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_todayKeyReleased
    String text = search_today.getText();
    TableRowSorter sorter = (TableRowSorter) tbl_res.getRowSorter();
    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));      // TODO add your handling code here:
    }//GEN-LAST:event_search_todayKeyReleased

    private void btn_upcomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_upcomActionPerformed
    // Show history panel, hide today
        
        pnl_future.setVisible(true);
        pnl_history.setVisible(false);
        pnl_dine_in.setVisible(false);

        // Update button states
        btn_upcom.setForeground(new Color(255, 200, 120)); // Active
        btn_today.setForeground(Color.WHITE);
        btn_history.setForeground(Color.WHITE);
        btn_IHR.setForeground(Color.WHITE);
    }//GEN-LAST:event_btn_upcomActionPerformed

    private void btn_historyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_historyActionPerformed
        // Show history panel, hide today
        pnl_history.setVisible(true);
        pnl_dine_in.setVisible(false);
        pnl_future.setVisible(false);
        pnl_IHR.setVisible(false);

        // Update button states
        btn_history.setForeground(new Color(255, 200, 120)); // Active
        btn_today.setForeground(Color.WHITE);                // Inactive
        btn_upcom.setForeground(Color.WHITE);
        btn_IHR.setForeground(Color.WHITE);
    }//GEN-LAST:event_btn_historyActionPerformed

    private void search_todayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_todayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_search_todayActionPerformed

    private void btn_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_logoutActionPerformed
        // TODO add your handling code here:
        Employee_Login loginWindow = new Employee_Login();
        loginWindow.setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_logoutActionPerformed

    private void btn_todayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_todayActionPerformed
        // Show today panel, hide history
        pnl_dine_in.setVisible(true);
        pnl_history.setVisible(false);
        pnl_future.setVisible(false);

        // Update button states
        btn_today.setForeground(new Color(255, 200, 120));  // Active
        btn_history.setForeground(Color.WHITE);           // Inactive
        btn_upcom.setForeground(Color.WHITE);
        btn_IHR.setForeground(Color.WHITE);
    }//GEN-LAST:event_btn_todayActionPerformed

    private void search_historyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_historyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_search_historyActionPerformed

    private void search_historyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_historyKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_search_historyKeyReleased

    private void search_upcomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_upcomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_search_upcomActionPerformed

    private void search_upcomKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_upcomKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_search_upcomKeyReleased

    private void search_IHRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_IHRActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_search_IHRActionPerformed

    private void search_IHRKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_IHRKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_search_IHRKeyReleased

    private void btn_IHRMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_IHRMouseEntered
        // TODO add your handling code here:
    if (!btn_IHR.getForeground().equals(new Color(255, 200, 120))) {
        btn_IHR.setForeground(new Color(255, 200, 120));
    }
    btn_IHR.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_btn_IHRMouseEntered

    private void btn_IHRMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_IHRMouseExited
        // TODO add your handling code here:
    if (!pnl_IHR.isVisible()) {  // Only reset if not active tab
        btn_IHR.setForeground(Color.WHITE);
    }
    }//GEN-LAST:event_btn_IHRMouseExited

    private void btn_IHRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_IHRActionPerformed
        // TODO add your handling code here:
        pnl_history.setVisible(false);
        pnl_dine_in.setVisible(false);
        pnl_future.setVisible(false);
        pnl_IHR.setVisible(true);

        // Update button states
        btn_IHR.setForeground(new Color(255, 200, 120)); // Active
        btn_today.setForeground(Color.WHITE);                // Inactive
        btn_upcom.setForeground(Color.WHITE);
        btn_history.setForeground(Color.WHITE);
    }//GEN-LAST:event_btn_IHRActionPerformed

     private void makeFlatButton(javax.swing.JButton btn) {
        btn.setFocusPainted(false);
        btn.setBorder(null);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setBackground(new java.awt.Color(153, 0, 0));
        btn.setForeground(java.awt.Color.WHITE);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
}
     
    private void applyHistoryDateFilter() {
        java.util.Date selectedDate = date_history.getDate();
        if (selectedDate == null) {
            sorter_history.setRowFilter(null);  // Show ALL records
            return;
        }

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM-dd-yy");
        String dateStr = sdf.format(selectedDate);

        // Filter column 0 (DATE) - case insensitive
        sorter_history.setRowFilter(RowFilter.regexFilter("(?i)" + dateStr, 0));
    }
    private void applyUpcomingDateFilter() {
    java.util.Date selectedDate = date_upcom.getDate();

    if (selectedDate == null) {
        sorter_upcom.setRowFilter(null);
        return;
    }

    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM-dd-yy");
    String dateStr = sdf.format(selectedDate);

    sorter_upcom.setRowFilter(RowFilter.regexFilter("(?i)" + dateStr, 0));
}
    
     
    /**
 * 
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Manager().setVisible(true));
    }
    // we need name for the table from database po 
    private void loadTableData(String tableName, JTable table) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM " + tableName;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Get metadata to build table model dynamically
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Create table model and set column names
            DefaultTableModel model = new DefaultTableModel();
            for (int i = 1; i <= columnCount; i++) {
                model.addColumn(metaData.getColumnName(i));
            }

            // Add rows to model
            while (rs.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = rs.getObject(i);
                }
                model.addRow(rowData);
            }

            // Set model to the JTable
            table.setModel(model);

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions properly in production code
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bg_today;
    private javax.swing.JLabel bg_today1;
    private javax.swing.JLabel bg_today2;
    private javax.swing.JLabel bg_today3;
    private javax.swing.JButton btn_IHR;
    private javax.swing.JButton btn_history;
    private javax.swing.JButton btn_logout;
    private javax.swing.JButton btn_today;
    private javax.swing.JButton btn_upcom;
    private com.toedter.calendar.JDateChooser date_history;
    private com.toedter.calendar.JDateChooser date_upcom;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lbl_BookingDash;
    private javax.swing.JLabel lbl_Dinein;
    private javax.swing.JLabel lbl_ihr;
    private javax.swing.JLabel lbl_reservation;
    private javax.swing.JLabel lbl_search;
    private javax.swing.JLabel lbl_searchihr;
    private javax.swing.JLabel lbl_totalavail;
    private javax.swing.JLabel lbl_totalres;
    private javax.swing.JLabel lbl_totalwalkin;
    private javax.swing.JLabel lbl_username;
    private javax.swing.JLabel lbl_walkin;
    private javax.swing.JPanel pnl_IHR;
    private javax.swing.JPanel pnl_dine_in;
    private javax.swing.JPanel pnl_future;
    private javax.swing.JPanel pnl_header;
    private javax.swing.JPanel pnl_history;
    private javax.swing.JPanel pnl_nav;
    private javax.swing.JTextField search_IHR;
    private javax.swing.JTextField search_history;
    private javax.swing.JTextField search_today;
    private javax.swing.JTextField search_upcom;
    private javax.swing.JTable tbl_IHR;
    private javax.swing.JTable tbl_future;
    private javax.swing.JTable tbl_history;
    private javax.swing.JTable tbl_res;
    private javax.swing.JTable tbl_walkin;
    // End of variables declaration//GEN-END:variables
}

