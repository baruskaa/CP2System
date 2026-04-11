package gui;


import java.awt.Color;
import java.text.SimpleDateFormat;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author ONIN
 */
public class Customer_BookingHistory extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Customer_BookingHistory.class.getName());
    private TableRowSorter<DefaultTableModel> sorter;
    /**
     * Creates new form Reservation_History2
     */
    public Customer_BookingHistory() {
        initComponents();
        this.setLocationRelativeTo(null); 
        
        
        //TABLE SORTER
        DefaultTableModel model = (DefaultTableModel) tbl_historyuser.getModel();
        sorter = new TableRowSorter<>(model);
        tbl_historyuser.setRowSorter(sorter);

        loadHistoryTable();
        centerTableData(tbl_historyuser);
        styleTableHeaders(tbl_historyuser);
        makeFlatButton(btn_navReservations);
        makeFlatButton(btn_navLogout);
        makeFlatButton(btn_RESET);

        date_historyuser.getDateEditor().addPropertyChangeListener(evt -> {
            if ("date".equals(evt.getPropertyName())) {
                filterByDate();
            }
        });
        
        
        java.util.List<javax.swing.RowSorter.SortKey> startupKeys = new java.util.ArrayList<>();
        startupKeys.add(new javax.swing.RowSorter.SortKey(1, javax.swing.SortOrder.DESCENDING)); 
        startupKeys.add(new javax.swing.RowSorter.SortKey(2, javax.swing.SortOrder.DESCENDING)); 
        startupKeys.add(new javax.swing.RowSorter.SortKey(1, javax.swing.SortOrder.DESCENDING)); 
        sorter.setSortKeys(startupKeys);
        
    }
    
    private void loadHistoryTable() {
        DefaultTableModel model = (DefaultTableModel) tbl_historyuser.getModel();
        model.setRowCount(0); 

        if (UserSession.loggedInEmail == null || UserSession.loggedInEmail.trim().isEmpty()) {
            return; 
        }

        Connect db = new Connect();
        db.DoConnect();

        if (db.con != null) {
            String query = "SELECT o.OR_ID, o.D_DATE, o.D_TIME, o.PAX " +
                           "FROM DBHOUSE.ONLINERESERVATIONS o " +
                           "JOIN DBHOUSE.VIPACCOUNTS v ON o.VIP_ID = v.VIP_ID " +
                           "WHERE v.EMAIL = ? " +
                           "ORDER BY o.D_DATE DESC";

            try (java.sql.PreparedStatement pst = db.con.prepareStatement(query)) {
                pst.setString(1, UserSession.loggedInEmail);
                
                try (java.sql.ResultSet rs = pst.executeQuery()) {
                    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yy"); 
                    
                    while (rs.next()) {
                        java.sql.Date sqlDate = rs.getDate("D_DATE");
                        String dateStr = (sqlDate != null) ? sdf.format(sqlDate) : "";

                        model.addRow(new Object[]{
                            rs.getString("OR_ID"),
                            dateStr,
                            rs.getString("D_TIME"),
                            rs.getInt("PAX")
                        });
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error loading history: " + e.getMessage());
                e.printStackTrace();
            } finally {
                try { db.con.close(); } catch (Exception ex) {}
            }
        }
    }

    private void filterByDate() {
        java.util.Date selectedDate = date_historyuser.getDate();
        
        if (selectedDate == null) {
            sorter.setRowFilter(null); 
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yy");
            String dateStr = sdf.format(selectedDate);
            sorter.setRowFilter(RowFilter.regexFilter("^" + dateStr + "$", 1)); 
        }
    }
    
   private void centerTableData(javax.swing.JTable table) {
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            centerRenderer.setOpaque(false); 
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
    }
    
     private void styleTableHeaders(javax.swing.JTable table) {
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        headerRenderer.setForeground(new Color(55, 77, 94));
        headerRenderer.setFont(new java.awt.Font("Century Gothic", java.awt.Font.BOLD, 14));
        table.getTableHeader().setDefaultRenderer(headerRenderer);
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDateChooser10 = new com.toedter.calendar.JDateChooser();
        pnl_header = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        pnl_history6 = new javax.swing.JPanel();
        date_historyuser = new com.toedter.calendar.JDateChooser();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        btn_RESET = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_historyuser = new javax.swing.JTable();
        bg_today1 = new javax.swing.JLabel();
        pnl_nav2 = new javax.swing.JPanel();
        Aboutus_Label5 = new javax.swing.JLabel();
        Aboutus_label6 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        btn_navAbout = new javax.swing.JButton();
        btn_navHome = new javax.swing.JButton();
        btn_navMenu = new javax.swing.JButton();
        btn_navDine = new javax.swing.JButton();
        btn_navReservations = new javax.swing.JButton();
        btn_navLogout = new javax.swing.JButton();
        btn_navProf = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(57, 77, 94));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnl_header.setBackground(new java.awt.Color(55, 91, 115));
        pnl_header.setMaximumSize(new java.awt.Dimension(681, 115));
        pnl_header.setPreferredSize(new java.awt.Dimension(681, 115));

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("MY RESERVATIONS");

        javax.swing.GroupLayout pnl_headerLayout = new javax.swing.GroupLayout(pnl_header);
        pnl_header.setLayout(pnl_headerLayout);
        pnl_headerLayout.setHorizontalGroup(
            pnl_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_headerLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(354, Short.MAX_VALUE))
        );
        pnl_headerLayout.setVerticalGroup(
            pnl_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_headerLayout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(33, 33, 33))
        );

        getContentPane().add(pnl_header, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 740, -1));

        pnl_history6.setForeground(new java.awt.Color(202, 199, 199));
        pnl_history6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        date_historyuser.setDateFormatString("MM-dd-yy");
        date_historyuser.setMaxSelectableDate(new java.util.Date(1924880509000L));
        date_historyuser.setMinSelectableDate(new java.util.Date(946659709000L));
        pnl_history6.add(date_historyuser, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 42, 170, 30));

        jLabel25.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(55, 77, 94));
        jLabel25.setText("Date:");
        pnl_history6.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 40, 50, 30));

        jLabel26.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(55, 77, 94));
        jLabel26.setText("HISTORY");
        pnl_history6.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 170, 50));

        btn_RESET.setBackground(new java.awt.Color(55, 91, 115));
        btn_RESET.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        btn_RESET.setForeground(new java.awt.Color(255, 255, 255));
        btn_RESET.setText("RESET");
        btn_RESET.setBorder(null);
        btn_RESET.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_RESET.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_RESETMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_RESETMouseExited(evt);
            }
        });
        btn_RESET.addActionListener(this::btn_RESETActionPerformed);
        pnl_history6.add(btn_RESET, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 40, 80, 30));

        jScrollPane2.setForeground(new java.awt.Color(55, 77, 94));

        tbl_historyuser.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        tbl_historyuser.setForeground(new java.awt.Color(55, 77, 94));
        tbl_historyuser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, "03-26-26", null,  new Integer(4)},
                {null, "03-01-26", null,  new Integer(3)},
                {null, "04-19-26", null,  new Integer(2)},
                {null, "03-01-26", null,  new Integer(6)},
                {null, "04-19-26", null,  new Integer(7)}
            },
            new String [] {
                "REFERENCE NUMBER", "DATE", "TIME", "PAX"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_historyuser.setOpaque(false);
        jScrollPane2.setViewportView(tbl_historyuser);
        if (tbl_historyuser.getColumnModel().getColumnCount() > 0) {
            tbl_historyuser.getColumnModel().getColumn(0).setResizable(false);
            tbl_historyuser.getColumnModel().getColumn(1).setResizable(false);
            tbl_historyuser.getColumnModel().getColumn(2).setResizable(false);
            tbl_historyuser.getColumnModel().getColumn(3).setResizable(false);
        }

        pnl_history6.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 700, 330));

        bg_today1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bgfd.jpg"))); // NOI18N
        pnl_history6.add(bg_today1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 470));

        getContentPane().add(pnl_history6, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 740, 470));

        pnl_nav2.setBackground(new java.awt.Color(57, 77, 94));
        pnl_nav2.setMaximumSize(new java.awt.Dimension(158, 580));
        pnl_nav2.setMinimumSize(new java.awt.Dimension(158, 580));
        pnl_nav2.setPreferredSize(new java.awt.Dimension(158, 580));
        pnl_nav2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Aboutus_Label5.setFont(new java.awt.Font("Segoe UI Emoji", 0, 10)); // NOI18N
        Aboutus_Label5.setForeground(new java.awt.Color(153, 153, 153));
        Aboutus_Label5.setText("© 2026 The House Of 7 Buffet.");
        pnl_nav2.add(Aboutus_Label5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 540, -1, -1));

        Aboutus_label6.setFont(new java.awt.Font("Segoe UI Emoji", 0, 10)); // NOI18N
        Aboutus_label6.setForeground(new java.awt.Color(153, 153, 153));
        Aboutus_label6.setText("All Rights Reserved.");
        pnl_nav2.add(Aboutus_label6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 550, -1, -1));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo.png"))); // NOI18N
        pnl_nav2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, -1, 80));

        btn_navAbout.setBackground(new java.awt.Color(55, 77, 94));
        btn_navAbout.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btn_navAbout.setForeground(new java.awt.Color(255, 255, 255));
        btn_navAbout.setText("About Us");
        btn_navAbout.setBorder(null);
        btn_navAbout.setContentAreaFilled(false);
        btn_navAbout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_navAbout.setFocusPainted(false);
        btn_navAbout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_navAboutMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_navAboutMouseExited(evt);
            }
        });
        btn_navAbout.addActionListener(this::btn_navAboutActionPerformed);
        pnl_nav2.add(btn_navAbout, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 120, 30));

        btn_navHome.setBackground(new java.awt.Color(55, 77, 94));
        btn_navHome.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btn_navHome.setForeground(new java.awt.Color(255, 255, 255));
        btn_navHome.setText("Home");
        btn_navHome.setBorder(null);
        btn_navHome.setContentAreaFilled(false);
        btn_navHome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_navHome.setFocusPainted(false);
        btn_navHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_navHomeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_navHomeMouseExited(evt);
            }
        });
        btn_navHome.addActionListener(this::btn_navHomeActionPerformed);
        pnl_nav2.add(btn_navHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 120, 30));

        btn_navMenu.setBackground(new java.awt.Color(55, 77, 94));
        btn_navMenu.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btn_navMenu.setForeground(new java.awt.Color(255, 255, 255));
        btn_navMenu.setText("Menu & Specials");
        btn_navMenu.setBorder(null);
        btn_navMenu.setContentAreaFilled(false);
        btn_navMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_navMenu.setFocusPainted(false);
        btn_navMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_navMenuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_navMenuMouseExited(evt);
            }
        });
        btn_navMenu.addActionListener(this::btn_navMenuActionPerformed);
        pnl_nav2.add(btn_navMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 120, 30));

        btn_navDine.setBackground(new java.awt.Color(55, 77, 94));
        btn_navDine.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btn_navDine.setForeground(new java.awt.Color(255, 255, 255));
        btn_navDine.setText("Dine Club");
        btn_navDine.setBorder(null);
        btn_navDine.setContentAreaFilled(false);
        btn_navDine.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_navDine.setFocusPainted(false);
        btn_navDine.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_navDineMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_navDineMouseExited(evt);
            }
        });
        btn_navDine.addActionListener(this::btn_navDineActionPerformed);
        pnl_nav2.add(btn_navDine, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 120, 30));

        btn_navReservations.setBackground(new java.awt.Color(185, 153, 79));
        btn_navReservations.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        btn_navReservations.setForeground(new java.awt.Color(55, 91, 115));
        btn_navReservations.setText("MY RESERVATIONS");
        btn_navReservations.setBorder(null);
        btn_navReservations.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_navReservations.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_navReservationsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_navReservationsMouseExited(evt);
            }
        });
        btn_navReservations.addActionListener(this::btn_navReservationsActionPerformed);
        pnl_nav2.add(btn_navReservations, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 120, 30));

        btn_navLogout.setBackground(new java.awt.Color(153, 0, 0));
        btn_navLogout.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        btn_navLogout.setForeground(new java.awt.Color(206, 206, 206));
        btn_navLogout.setText("LOG OUT");
        btn_navLogout.setBorder(null);
        btn_navLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_navLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_navLogoutMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_navLogoutMouseExited(evt);
            }
        });
        btn_navLogout.addActionListener(this::btn_navLogoutActionPerformed);
        pnl_nav2.add(btn_navLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 490, 80, 30));

        btn_navProf.setBackground(new java.awt.Color(55, 77, 94));
        btn_navProf.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btn_navProf.setForeground(new java.awt.Color(255, 255, 255));
        btn_navProf.setText("Profile");
        btn_navProf.setBorder(null);
        btn_navProf.setContentAreaFilled(false);
        btn_navProf.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_navProf.setFocusPainted(false);
        btn_navProf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_navProfMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_navProfMouseExited(evt);
            }
        });
        btn_navProf.addActionListener(this::btn_navProfActionPerformed);
        pnl_nav2.add(btn_navProf, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 120, 30));

        getContentPane().add(pnl_nav2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_navAbout1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_navAbout1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_navAbout1MouseEntered

    private void btn_navAbout1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_navAbout1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_navAbout1MouseExited

    private void btn_navAbout1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_navAbout1ActionPerformed
        new Customer_About_Us().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_navAbout1ActionPerformed

    private void btn_navHome1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_navHome1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_navHome1MouseEntered

    private void btn_navHome1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_navHome1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_navHome1MouseExited

    private void btn_navHome1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_navHome1ActionPerformed
        new Customer_Homepage().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_navHome1ActionPerformed

    private void btn_navMenu1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_navMenu1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_navMenu1MouseEntered

    private void btn_navMenu1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_navMenu1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_navMenu1MouseExited

    private void btn_navMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_navMenu1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_navMenu1ActionPerformed

    private void btn_navDine1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_navDine1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_navDine1MouseEntered

    private void btn_navDine1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_navDine1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_navDine1MouseExited

    private void btn_navDine1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_navDine1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_navDine1ActionPerformed

    private void btn_RESETActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_RESETActionPerformed
    date_historyuser.setDate(null);
     if (sorter != null) {
         sorter.setRowFilter(null);
        }
     loadHistoryTable();
    }//GEN-LAST:event_btn_RESETActionPerformed

    private void btn_navLogout1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_navLogout1ActionPerformed

        int choice = JOptionPane.showConfirmDialog(null, "Log out of your account?", "Confirm Log Out", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            UserSession.loggedInEmail = null;
            new Customer_Login().setVisible(true);
            this.dispose();

        } else if (choice == JOptionPane.NO_OPTION) {
            return;
        }
    }//GEN-LAST:event_btn_navLogout1ActionPerformed

    private void btn_navProf1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_navProf1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_navProf1MouseEntered

    private void btn_navProf1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_navProf1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_navProf1MouseExited

    private void btn_navProf1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_navProf1ActionPerformed
        new Customer_AcctProfile().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_navProf1ActionPerformed

    private void btn_navAboutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_navAboutMouseEntered
        if (!btn_navAbout.getForeground().equals(new Color(255, 200, 120))) {
            btn_navAbout.setForeground(new Color(255, 200, 120));
        }
        btn_navAbout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_btn_navAboutMouseEntered

    private void btn_navAboutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_navAboutMouseExited

        btn_navAbout.setForeground(Color.WHITE);
    }//GEN-LAST:event_btn_navAboutMouseExited

    private void btn_navAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_navAboutActionPerformed
        new Customer_About_Us().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_navAboutActionPerformed

    private void btn_navHomeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_navHomeMouseEntered
        if (!btn_navHome.getForeground().equals(new Color(255, 200, 120))) {
            btn_navHome.setForeground(new Color(255, 200, 120));
        }
        btn_navHome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_btn_navHomeMouseEntered

    private void btn_navHomeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_navHomeMouseExited
        btn_navHome.setForeground(Color.WHITE);
    }//GEN-LAST:event_btn_navHomeMouseExited

    private void btn_navHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_navHomeActionPerformed
        new Customer_Homepage().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_navHomeActionPerformed

    private void btn_navMenuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_navMenuMouseEntered
        if (!btn_navMenu.getForeground().equals(new Color(255, 200, 120))) {
            btn_navMenu.setForeground(new Color(255, 200, 120));
        }
        btn_navMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_btn_navMenuMouseEntered

    private void btn_navMenuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_navMenuMouseExited
        btn_navMenu.setForeground(Color.WHITE);
    }//GEN-LAST:event_btn_navMenuMouseExited

    private void btn_navMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_navMenuActionPerformed
        new Customer_Menu().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_navMenuActionPerformed

    private void btn_navDineMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_navDineMouseEntered
        if (!btn_navDine.getForeground().equals(new Color(255, 200, 120))) {
            btn_navDine.setForeground(new Color(255, 200, 120));
        }
        btn_navDine.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_btn_navDineMouseEntered

    private void btn_navDineMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_navDineMouseExited
        btn_navDine.setForeground(Color.WHITE);
    }//GEN-LAST:event_btn_navDineMouseExited

    private void btn_navDineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_navDineActionPerformed
        new Customer_DineClub().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_navDineActionPerformed

    private void btn_navReservationsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_navReservationsMouseEntered
        if (!btn_navReservations.getBackground().equals(new Color(217,180,95))) {
            btn_navReservations.setBackground(new Color(217,180,95));
        }
        btn_navReservations.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_btn_navReservationsMouseEntered

    private void btn_navReservationsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_navReservationsMouseExited
        btn_navReservations.setBackground(new Color(185,153,79));
    }//GEN-LAST:event_btn_navReservationsMouseExited

    private void btn_navReservationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_navReservationsActionPerformed
        new Customer_BookingHistory().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_navReservationsActionPerformed

    private void btn_navLogoutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_navLogoutMouseEntered
        if (!btn_navLogout.getBackground().equals(new Color(183,14,14))) {
            btn_navLogout.setBackground(new Color(183,14,14));
        }
        btn_navLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_btn_navLogoutMouseEntered

    private void btn_navLogoutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_navLogoutMouseExited
        btn_navLogout.setBackground(new Color(153,0,0));
    }//GEN-LAST:event_btn_navLogoutMouseExited

    private void btn_navLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_navLogoutActionPerformed

        int choice = JOptionPane.showConfirmDialog(null, "Log out of your account?", "Confirm Log Out", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            UserSession.loggedInEmail = null;
            new Customer_Login().setVisible(true);
            this.dispose();

        } else if (choice == JOptionPane.NO_OPTION) {
            return;
        }
    }//GEN-LAST:event_btn_navLogoutActionPerformed

    private void btn_navProfMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_navProfMouseEntered
        if (!btn_navProf.getForeground().equals(new Color(255, 200, 120))) {
            btn_navProf.setForeground(new Color(255, 200, 120));
        }
        btn_navProf.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_btn_navProfMouseEntered

    private void btn_navProfMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_navProfMouseExited
        btn_navProf.setForeground(Color.WHITE);
    }//GEN-LAST:event_btn_navProfMouseExited

    private void btn_navProfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_navProfActionPerformed
        new Customer_AcctProfile().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_navProfActionPerformed

    private void btn_RESETMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_RESETMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_RESETMouseEntered

    private void btn_RESETMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_RESETMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_RESETMouseExited
private JFrame frame;
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new Customer_BookingHistory().setVisible(true));
    }
    
    private void makeFlatButton(javax.swing.JButton btn) {
        btn.setFocusPainted(false);
        btn.setBorder(null);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Aboutus_Label5;
    private javax.swing.JLabel Aboutus_label6;
    private javax.swing.JLabel bg_today1;
    private javax.swing.JButton btn_RESET;
    private javax.swing.JButton btn_navAbout;
    private javax.swing.JButton btn_navDine;
    private javax.swing.JButton btn_navHome;
    private javax.swing.JButton btn_navLogout;
    private javax.swing.JButton btn_navMenu;
    private javax.swing.JButton btn_navProf;
    private javax.swing.JButton btn_navReservations;
    private com.toedter.calendar.JDateChooser date_historyuser;
    private com.toedter.calendar.JDateChooser jDateChooser10;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel pnl_header;
    private javax.swing.JPanel pnl_history6;
    private javax.swing.JPanel pnl_nav2;
    private javax.swing.JTable tbl_historyuser;
    // End of variables declaration//GEN-END:variables
}
