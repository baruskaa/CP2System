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

/**
 *
 * @author kyshgel
 */
public class Manager extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Manager.class.getName());
    private TableRowSorter<DefaultTableModel> sorter_today;
    private TableRowSorter<DefaultTableModel> sorter_history;
    private TableRowSorter<DefaultTableModel> sorter_upcom;
    
    
    public Manager() {
        initComponents();
        this.setLocationRelativeTo(null);

        // TableRowSorter for sorting on column header clicks
        DefaultTableModel model_today = (DefaultTableModel) tbl_today.getModel();
        sorter_today = new TableRowSorter<>(model_today);
        tbl_today.setRowSorter(sorter_today);

        DefaultTableModel model_history = (DefaultTableModel) tbl_history.getModel();
        sorter_history = new TableRowSorter<>(model_history);
        tbl_history.setRowSorter(sorter_history);
        
        DefaultTableModel model_upcom = (DefaultTableModel) tbl_upcom.getModel();
        sorter_upcom = new TableRowSorter<>(model_upcom);
        tbl_upcom.setRowSorter(sorter_upcom);

        // Center alignment for all columns
        DefaultTableCellRenderer center_today = new DefaultTableCellRenderer();
        center_today.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tbl_today.getColumnCount(); i++) {
            tbl_today.getColumnModel().getColumn(i).setCellRenderer(center_today);
        }
        
        DefaultTableCellRenderer center_history = new DefaultTableCellRenderer();
        center_history.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tbl_history.getColumnCount(); i++) {
        tbl_history.getColumnModel().getColumn(i).setCellRenderer(center_history);
        }
        
        DefaultTableCellRenderer center_upcom = new DefaultTableCellRenderer();
        center_upcom.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tbl_upcom.getColumnCount(); i++) {
        tbl_upcom.getColumnModel().getColumn(i).setCellRenderer(center_upcom);  // FIXED: center_upcom
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

        // Count total non-empty rows for TABLE NO.
        int total_today = 0;
        for (int i = 0; i < tbl_today.getRowCount(); i++) {
            Object value = tbl_today.getValueAt(i, 0);
            if (value != null && !value.toString().trim().equals("")) {
                total_today++;
            }
        }
        lbl_total.setText(String.valueOf(total_today));
        
        
        
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
        
        // Initial state: Show TODAY panel, hide HISTORY panel
        pnl_today.setVisible(true);
        pnl_history.setVisible(false);
        pnl_upcom.setVisible(false);

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
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tbl_today.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER); // center text
        tbl_today.getTableHeader().setForeground(new Color(55, 77, 94));  
        tbl_today.getTableHeader().setFont(new java.awt.Font("Century Gothic", java.awt.Font.BOLD, 14));  
        
        DefaultTableCellRenderer headerRenderer2 = (DefaultTableCellRenderer) tbl_history.getTableHeader().getDefaultRenderer();
        headerRenderer2.setHorizontalAlignment(JLabel.CENTER); // center text
        tbl_history.getTableHeader().setForeground(new Color(55, 77, 94));  
        tbl_history.getTableHeader().setFont(new java.awt.Font("Century Gothic", java.awt.Font.BOLD, 14));  
      
        DefaultTableCellRenderer headerRenderer3 = (DefaultTableCellRenderer) tbl_upcom.getTableHeader().getDefaultRenderer();
        headerRenderer3.setHorizontalAlignment(JLabel.CENTER); // center text
        tbl_upcom.getTableHeader().setForeground(new Color(55, 77, 94));  
        tbl_upcom.getTableHeader().setFont(new java.awt.Font("Century Gothic", java.awt.Font.BOLD, 14));  
    
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
        pnl_header = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lbl_username = new javax.swing.JLabel();
        pnl_today = new javax.swing.JPanel();
        lbl_total = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        search_today = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_today = new javax.swing.JTable();
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
        pnl_upcom = new javax.swing.JPanel();
        txt_newTable = new javax.swing.JTextField();
        btn_assign = new javax.swing.JButton();
        btn_del = new javax.swing.JButton();
        date_upcom = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        search_upcom = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_upcom = new javax.swing.JTable();
        bg_today2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnl_nav.setBackground(new java.awt.Color(55, 77, 94));
        pnl_nav.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_today.setBackground(new java.awt.Color(55, 77, 94));
        btn_today.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        btn_today.setForeground(new java.awt.Color(255, 255, 255));
        btn_today.setText("TODAY");
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
        btn_upcom.setText("UPCOMING");
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
        pnl_nav.add(btn_upcom, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 90, 30));

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
        pnl_nav.add(btn_history, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 90, 30));

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

        getContentPane().add(pnl_nav, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 580));

        pnl_header.setBackground(new java.awt.Color(55, 91, 115));
        pnl_header.setMaximumSize(new java.awt.Dimension(681, 115));
        pnl_header.setPreferredSize(new java.awt.Dimension(681, 115));

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("BOOKING DASHBOARD");

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
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 211, Short.MAX_VALUE)
                .addComponent(lbl_username, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        pnl_headerLayout.setVerticalGroup(
            pnl_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_headerLayout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addGroup(pnl_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lbl_username))
                .addGap(33, 33, 33))
        );

        getContentPane().add(pnl_header, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 740, -1));

        pnl_today.setForeground(new java.awt.Color(202, 199, 199));
        pnl_today.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_total.setFont(new java.awt.Font("Century Gothic", 1, 20)); // NOI18N
        lbl_total.setForeground(new java.awt.Color(102, 102, 102));
        lbl_total.setText("30");
        pnl_today.add(lbl_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 420, 70, 40));

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(55, 77, 94));
        jLabel4.setText("TODAY");
        pnl_today.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 170, 50));

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("TOTAL:");
        pnl_today.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 420, 70, 40));

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(55, 77, 94));
        jLabel5.setText("Search:");
        pnl_today.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 30, 60, 40));

        search_today.addActionListener(this::search_todayActionPerformed);
        search_today.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_todayKeyReleased(evt);
            }
        });
        pnl_today.add(search_today, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 40, 170, -1));

        jScrollPane1.setForeground(new java.awt.Color(55, 77, 94));

        tbl_today.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        tbl_today.setForeground(new java.awt.Color(55, 77, 94));
        tbl_today.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                { new Integer(1), "Juan Dela Cruz", "09357873489",  new Integer(4), "Lunch"},
                { new Integer(2), "Maria Santos", "09174532356",  new Integer(3), "Lunch"},
                { new Integer(3), "Louise Lopez", "09876541453",  new Integer(2), "Dinner"},
                { new Integer(4), "Rhian Espinosa", "09258653421",  new Integer(6), "Dinner"},
                { new Integer(5), "Justine Dizon", "09987823421",  new Integer(7), "Lunch"}
            },
            new String [] {
                "TABLE NO.", "CUSTOMER NAME", "CONTACT", "PAX", "TIME"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_today.setOpaque(false);
        jScrollPane1.setViewportView(tbl_today);
        if (tbl_today.getColumnModel().getColumnCount() > 0) {
            tbl_today.getColumnModel().getColumn(0).setResizable(false);
            tbl_today.getColumnModel().getColumn(1).setResizable(false);
            tbl_today.getColumnModel().getColumn(2).setResizable(false);
            tbl_today.getColumnModel().getColumn(3).setResizable(false);
            tbl_today.getColumnModel().getColumn(4).setResizable(false);
        }

        pnl_today.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 700, 330));

        bg_today.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/bgfd.jpg"))); // NOI18N
        bg_today.setText("Today");
        pnl_today.add(bg_today, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 470));

        getContentPane().add(pnl_today, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 740, 470));

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
                {"03-26-26",  new Integer(1), "Juan Dela Cruz", "09357873489",  new Integer(4), "Lunch"},
                {"03-01-26",  new Integer(2), "Maria Santos", "09174532356",  new Integer(3), "Lunch"},
                {"04-19-26",  new Integer(3), "Louise Lopez", "09876541453",  new Integer(2), "Lunch"},
                {"03-01-26",  new Integer(4), "Rhian Espinosa", "09258653421",  new Integer(6), "Dinner"},
                {"04-19-26",  new Integer(5), "Justine Dizon", "09987823421",  new Integer(7), "Dinner"}
            },
            new String [] {
                "DATE", "TABLE NO.", "CUSTOMER NAME", "CONTACT", "PAX", "TIME"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
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
        }

        pnl_history.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 700, 330));

        bg_today1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/bgfd.jpg"))); // NOI18N
        bg_today1.setText("Today");
        pnl_history.add(bg_today1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 470));

        getContentPane().add(pnl_history, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 740, 470));

        pnl_upcom.setForeground(new java.awt.Color(202, 199, 199));
        pnl_upcom.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_newTable.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_newTable.setText("New Table No.");
        txt_newTable.addActionListener(this::txt_newTableNew_tableActionPerformed);
        pnl_upcom.add(txt_newTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, -1, 30));

        btn_assign.setBackground(new java.awt.Color(65, 93, 120));
        btn_assign.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btn_assign.setForeground(new java.awt.Color(65, 93, 120));
        btn_assign.setText("Assign");
        btn_assign.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(65, 93, 120), 2, true));
        btn_assign.setContentAreaFilled(false);
        btn_assign.setFocusPainted(false);
        btn_assign.addActionListener(this::btn_assignAssign_ButtonActionPerformed);
        pnl_upcom.add(btn_assign, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 420, 60, 30));

        btn_del.setBackground(new java.awt.Color(65, 93, 120));
        btn_del.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btn_del.setForeground(new java.awt.Color(65, 93, 120));
        btn_del.setText("Delete");
        btn_del.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(65, 93, 120), 2, true));
        btn_del.setContentAreaFilled(false);
        btn_del.setFocusPainted(false);
        btn_del.setFocusable(false);
        btn_del.setRequestFocusEnabled(false);
        btn_del.addActionListener(this::btn_delRemove_buttonActionPerformed);
        pnl_upcom.add(btn_del, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 420, 60, 30));

        date_upcom.setDateFormatString("MM-dd-yy");
        pnl_upcom.add(date_upcom, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 50, 170, -1));

        jLabel11.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(55, 77, 94));
        jLabel11.setText("Date:");
        pnl_upcom.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 50, 50, 20));

        jLabel8.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(55, 77, 94));
        jLabel8.setText("UPCOMING");
        pnl_upcom.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 310, 50));

        jLabel12.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(55, 77, 94));
        jLabel12.setText("Search:");
        pnl_upcom.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 20, 60, 20));

        search_upcom.addActionListener(this::search_upcomActionPerformed);
        search_upcom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_upcomKeyReleased(evt);
            }
        });
        pnl_upcom.add(search_upcom, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 20, 170, -1));

        jScrollPane3.setForeground(new java.awt.Color(55, 77, 94));

        tbl_upcom.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        tbl_upcom.setForeground(new java.awt.Color(55, 77, 94));
        tbl_upcom.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"03-26-26",  new Integer(1), "Juan Dela Cruz", "09357873489",  new Integer(4), "Lunch"},
                {"03-01-26",  new Integer(2), "Maria Santos", "09174532356",  new Integer(3), "Lunch"},
                {"04-19-26",  new Integer(3), "Louise Lopez", "09876541453",  new Integer(2), "Lunch"},
                {"03-01-26",  new Integer(4), "Rhian Espinosa", "09258653421",  new Integer(6), "Dinner"},
                {"04-19-26",  new Integer(5), "Justine Dizon", "09987823421",  new Integer(7), "Dinner"}
            },
            new String [] {
                "DATE", "TABLE NO.", "CUSTOMER NAME", "CONTACT", "PAX", "TIME"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_upcom.setOpaque(false);
        jScrollPane3.setViewportView(tbl_upcom);
        if (tbl_upcom.getColumnModel().getColumnCount() > 0) {
            tbl_upcom.getColumnModel().getColumn(0).setResizable(false);
            tbl_upcom.getColumnModel().getColumn(1).setResizable(false);
            tbl_upcom.getColumnModel().getColumn(2).setResizable(false);
            tbl_upcom.getColumnModel().getColumn(3).setResizable(false);
            tbl_upcom.getColumnModel().getColumn(4).setResizable(false);
            tbl_upcom.getColumnModel().getColumn(5).setResizable(false);
        }

        pnl_upcom.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 700, 330));

        bg_today2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/bgfd.jpg"))); // NOI18N
        bg_today2.setText("Today");
        pnl_upcom.add(bg_today2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 470));

        getContentPane().add(pnl_upcom, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 740, 470));

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
    if (!pnl_today.isVisible()) {  // Only reset if not active tab
        btn_today.setForeground(Color.WHITE);
    }       // TODO add your handling code here:
    }//GEN-LAST:event_btn_todayMouseExited

    private void btn_upcomMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_upcomMouseExited
    if (!pnl_upcom.isVisible()) {  // Only reset if not active tab
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
    TableRowSorter sorter = (TableRowSorter) tbl_today.getRowSorter();
    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));      // TODO add your handling code here:
    }//GEN-LAST:event_search_todayKeyReleased

    private void btn_upcomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_upcomActionPerformed
    // Show history panel, hide today
        
        pnl_upcom.setVisible(true);
        pnl_history.setVisible(false);
        pnl_today.setVisible(false);

        // Update button states
        btn_upcom.setForeground(new Color(255, 200, 120)); // Active
        btn_today.setForeground(Color.WHITE);
        btn_history.setForeground(Color.WHITE);
    }//GEN-LAST:event_btn_upcomActionPerformed

    private void btn_historyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_historyActionPerformed
        // Show history panel, hide today
        pnl_history.setVisible(true);
        pnl_today.setVisible(false);
        pnl_upcom.setVisible(false);

        // Update button states
        btn_history.setForeground(new Color(255, 200, 120)); // Active
        btn_today.setForeground(Color.WHITE);                // Inactive
        btn_upcom.setForeground(Color.WHITE);
    }//GEN-LAST:event_btn_historyActionPerformed

    private void search_todayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_todayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_search_todayActionPerformed

    private void btn_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_logoutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_logoutActionPerformed

    private void btn_todayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_todayActionPerformed
        // Show today panel, hide history
        pnl_today.setVisible(true);
        pnl_history.setVisible(false);
        pnl_upcom.setVisible(false);

        // Update button states
        btn_today.setForeground(new Color(255, 200, 120));  // Active
        btn_history.setForeground(Color.WHITE);           // Inactive
        btn_upcom.setForeground(Color.WHITE);
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

    private void txt_newTableNew_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_newTableNew_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_newTableNew_tableActionPerformed

    private void btn_assignAssign_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_assignAssign_ButtonActionPerformed
          DefaultTableModel model = (DefaultTableModel) tbl_upcom.getModel();
    int viewRow = tbl_upcom.getSelectedRow();

    if (viewRow != -1) {
        int modelRow = tbl_upcom.convertRowIndexToModel(viewRow);

        String tableNum = txt_newTable.getText().trim();

        // 🔥 PUT IT HERE
        if (tableNum.isEmpty() || !tableNum.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Enter valid table number!");
            return;
        }

        model.setValueAt(Integer.parseInt(tableNum), modelRow, 1);

        txt_newTable.setText("");
    } else {
        JOptionPane.showMessageDialog(this, "Select a guest first!");
    }
    }//GEN-LAST:event_btn_assignAssign_ButtonActionPerformed

    private void btn_delRemove_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delRemove_buttonActionPerformed
            DefaultTableModel model = (DefaultTableModel) tbl_upcom.getModel();
        int viewRow = tbl_upcom.getSelectedRow();

        if (viewRow != -1) {
            int modelRow = tbl_upcom.convertRowIndexToModel(viewRow); // 🔥 FIX
            model.removeRow(modelRow);
        } else {
            JOptionPane.showMessageDialog(this, "Select a reservation to remove.");
        }
    }//GEN-LAST:event_btn_delRemove_buttonActionPerformed

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bg_today;
    private javax.swing.JLabel bg_today1;
    private javax.swing.JLabel bg_today2;
    private javax.swing.JButton btn_assign;
    private javax.swing.JButton btn_del;
    private javax.swing.JButton btn_history;
    private javax.swing.JButton btn_logout;
    private javax.swing.JButton btn_today;
    private javax.swing.JButton btn_upcom;
    private com.toedter.calendar.JDateChooser date_history;
    private com.toedter.calendar.JDateChooser date_upcom;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbl_total;
    private javax.swing.JLabel lbl_username;
    private javax.swing.JPanel pnl_header;
    private javax.swing.JPanel pnl_history;
    private javax.swing.JPanel pnl_nav;
    private javax.swing.JPanel pnl_today;
    private javax.swing.JPanel pnl_upcom;
    private javax.swing.JTextField search_history;
    private javax.swing.JTextField search_today;
    private javax.swing.JTextField search_upcom;
    private javax.swing.JTable tbl_history;
    private javax.swing.JTable tbl_today;
    private javax.swing.JTable tbl_upcom;
    private javax.swing.JTextField txt_newTable;
    // End of variables declaration//GEN-END:variables
}

