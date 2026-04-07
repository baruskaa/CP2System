package gui;
import java.awt.Color;
import javax.swing.*;
public class Customer_AcctProfile extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Customer_AcctProfile.class.getName());
    private String currentUserEmail; // Store the logged-in user's email
    
    public Customer_AcctProfile() {
    initComponents();
    setLocationRelativeTo(null);
    
    // Check if someone is actually logged in before pulling data
    if (UserSession.loggedInEmail != null) {
        loadUserProfile();
    } else {
        JOptionPane.showMessageDialog(this, "Please log in first.");
    }
    
    makeFlatButton(btn_navLogout);
    makeFlatButton(btn_profSave);
    makeFlatButton(btn_navReservations);
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_header = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btn_profSave = new javax.swing.JButton();
        txt_profFirstName = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txt_profEmail = new javax.swing.JTextField();
        txt_profBday = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txt_profCPNum = new javax.swing.JFormattedTextField();
        jLabel16 = new javax.swing.JLabel();
        txt_profLastName = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txt_profGender = new javax.swing.JTextField();
        txt_profPass = new javax.swing.JPasswordField();
        chk_createShowPass = new javax.swing.JCheckBox();
        jLabel18 = new javax.swing.JLabel();
        greet_user_accprof_label1 = new javax.swing.JLabel();
        bg_today = new javax.swing.JLabel();
        pnl_nav6 = new javax.swing.JPanel();
        Aboutus_Label9 = new javax.swing.JLabel();
        Aboutus_label10 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        btn_navAbout = new javax.swing.JButton();
        btn_navHome = new javax.swing.JButton();
        btn_navMenu = new javax.swing.JButton();
        btn_navDine = new javax.swing.JButton();
        btn_navReservations = new javax.swing.JButton();
        btn_navLogout = new javax.swing.JButton();
        btn_navProf = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnl_header.setBackground(new java.awt.Color(55, 91, 115));
        pnl_header.setMaximumSize(new java.awt.Dimension(681, 115));
        pnl_header.setPreferredSize(new java.awt.Dimension(681, 115));

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("MY PROFILE");

        javax.swing.GroupLayout pnl_headerLayout = new javax.swing.GroupLayout(pnl_header);
        pnl_header.setLayout(pnl_headerLayout);
        pnl_headerLayout.setHorizontalGroup(
            pnl_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_headerLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_headerLayout.setVerticalGroup(
            pnl_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_headerLayout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(33, 33, 33))
        );

        getContentPane().add(pnl_header, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 740, -1));

        jLabel10.setBackground(new java.awt.Color(47, 74, 91));
        jLabel10.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(47, 74, 91));
        jLabel10.setText("E-mail:");
        jLabel10.setToolTipText("");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 290, -1, -1));

        btn_profSave.setBackground(new java.awt.Color(55, 91, 115));
        btn_profSave.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        btn_profSave.setForeground(new java.awt.Color(255, 255, 255));
        btn_profSave.setText("SAVE CHANGES");
        btn_profSave.setBorder(null);
        btn_profSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_profSave.addActionListener(this::btn_profSaveActionPerformed);
        getContentPane().add(btn_profSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 450, 120, 30));

        txt_profFirstName.setBackground(new java.awt.Color(229, 229, 229));
        txt_profFirstName.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txt_profFirstName.setForeground(new java.awt.Color(47, 74, 91));
        txt_profFirstName.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        txt_profFirstName.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt_profFirstName.addActionListener(this::txt_profFirstNameActionPerformed);
        getContentPane().add(txt_profFirstName, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 240, 280, -1));

        jLabel11.setBackground(new java.awt.Color(47, 74, 91));
        jLabel11.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(47, 74, 91));
        jLabel11.setText("Password:");
        jLabel11.setToolTipText("");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 360, -1, -1));

        jLabel13.setBackground(new java.awt.Color(47, 74, 91));
        jLabel13.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(47, 74, 91));
        jLabel13.setText("First Name:");
        jLabel13.setToolTipText("");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 220, -1, -1));

        txt_profEmail.setBackground(new java.awt.Color(229, 229, 229));
        txt_profEmail.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txt_profEmail.setForeground(new java.awt.Color(47, 74, 91));
        txt_profEmail.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        txt_profEmail.addActionListener(this::txt_profEmailActionPerformed);
        getContentPane().add(txt_profEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 310, 280, 29));

        txt_profBday.setEditable(false);
        txt_profBday.setBackground(new java.awt.Color(229, 229, 229));
        txt_profBday.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txt_profBday.setForeground(new java.awt.Color(47, 74, 91));
        txt_profBday.setText("2026-03-04");
        txt_profBday.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        txt_profBday.addActionListener(this::txt_profBdayActionPerformed);
        getContentPane().add(txt_profBday, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 380, 110, 29));

        jLabel14.setBackground(new java.awt.Color(47, 74, 91));
        jLabel14.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(47, 74, 91));
        jLabel14.setText("Last Name:");
        jLabel14.setToolTipText("");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 290, -1, -1));

        jLabel15.setBackground(new java.awt.Color(47, 74, 91));
        jLabel15.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(47, 74, 91));
        jLabel15.setText("Mobile Number (+63):");
        jLabel15.setToolTipText("");
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 220, -1, -1));

        txt_profCPNum.setBackground(new java.awt.Color(229, 229, 229));
        txt_profCPNum.setBorder(null);
        txt_profCPNum.setForeground(new java.awt.Color(47, 74, 91));
        try {
            txt_profCPNum.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("  ###########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_profCPNum.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txt_profCPNum.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txt_profCPNum.setMargin(new java.awt.Insets(2, 12, 2, 12));
        getContentPane().add(txt_profCPNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 240, 280, 29));

        jLabel16.setBackground(new java.awt.Color(47, 74, 91));
        jLabel16.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(47, 74, 91));
        jLabel16.setText("Gender:");
        jLabel16.setToolTipText("");
        getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 360, -1, -1));

        txt_profLastName.setBackground(new java.awt.Color(229, 229, 229));
        txt_profLastName.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txt_profLastName.setForeground(new java.awt.Color(47, 74, 91));
        txt_profLastName.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        txt_profLastName.addActionListener(this::txt_profLastNameActionPerformed);
        getContentPane().add(txt_profLastName, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 310, 280, -1));

        jLabel17.setBackground(new java.awt.Color(47, 74, 91));
        jLabel17.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(47, 74, 91));
        jLabel17.setText("Birthday:");
        jLabel17.setToolTipText("");
        getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 360, -1, -1));

        txt_profGender.setEditable(false);
        txt_profGender.setBackground(new java.awt.Color(229, 229, 229));
        txt_profGender.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txt_profGender.setForeground(new java.awt.Color(47, 74, 91));
        txt_profGender.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        txt_profGender.addActionListener(this::txt_profGenderActionPerformed);
        getContentPane().add(txt_profGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 380, 110, -1));

        txt_profPass.setBackground(new java.awt.Color(229, 229, 229));
        txt_profPass.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txt_profPass.setForeground(new java.awt.Color(47, 74, 91));
        txt_profPass.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 10, 5, 10));
        txt_profPass.setCaretColor(new java.awt.Color(47, 74, 91));
        txt_profPass.addActionListener(this::txt_profPassActionPerformed);
        getContentPane().add(txt_profPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 380, 280, 29));

        chk_createShowPass.setFont(new java.awt.Font("Century Gothic", 1, 10)); // NOI18N
        chk_createShowPass.setForeground(new java.awt.Color(47, 74, 91));
        chk_createShowPass.setBorder(null);
        chk_createShowPass.addActionListener(this::chk_createShowPassActionPerformed);
        getContentPane().add(chk_createShowPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 410, 30, 20));

        jLabel18.setBackground(new java.awt.Color(47, 74, 91));
        jLabel18.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(47, 74, 91));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("Show Password ");
        jLabel18.setToolTipText("");
        getContentPane().add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 410, 100, 20));

        greet_user_accprof_label1.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        greet_user_accprof_label1.setForeground(new java.awt.Color(57, 77, 94));
        greet_user_accprof_label1.setText("Manage your account.");
        getContentPane().add(greet_user_accprof_label1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 150, 300, -1));

        bg_today.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bgfd.jpg"))); // NOI18N
        getContentPane().add(bg_today, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 740, 470));

        pnl_nav6.setBackground(new java.awt.Color(57, 77, 94));
        pnl_nav6.setMaximumSize(new java.awt.Dimension(158, 580));
        pnl_nav6.setMinimumSize(new java.awt.Dimension(158, 580));
        pnl_nav6.setPreferredSize(new java.awt.Dimension(158, 580));
        pnl_nav6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Aboutus_Label9.setFont(new java.awt.Font("Segoe UI Emoji", 0, 10)); // NOI18N
        Aboutus_Label9.setForeground(new java.awt.Color(153, 153, 153));
        Aboutus_Label9.setText("© 2026 The House Of 7 Buffet.");
        pnl_nav6.add(Aboutus_Label9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 540, -1, -1));

        Aboutus_label10.setFont(new java.awt.Font("Segoe UI Emoji", 0, 10)); // NOI18N
        Aboutus_label10.setForeground(new java.awt.Color(153, 153, 153));
        Aboutus_label10.setText("All Rights Reserved.");
        pnl_nav6.add(Aboutus_label10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 550, -1, -1));

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo.png"))); // NOI18N
        pnl_nav6.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, -1, 80));

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
        pnl_nav6.add(btn_navAbout, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 120, 30));

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
        pnl_nav6.add(btn_navHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 120, 30));

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
        pnl_nav6.add(btn_navMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 120, 30));

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
        pnl_nav6.add(btn_navDine, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 120, 30));

        btn_navReservations.setBackground(new java.awt.Color(185, 153, 79));
        btn_navReservations.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        btn_navReservations.setForeground(new java.awt.Color(55, 91, 115));
        btn_navReservations.setText("MY RESERVATIONS");
        btn_navReservations.setBorder(null);
        btn_navReservations.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_navReservations.addActionListener(this::btn_navReservationsActionPerformed);
        pnl_nav6.add(btn_navReservations, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 120, 30));

        btn_navLogout.setBackground(new java.awt.Color(153, 0, 0));
        btn_navLogout.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        btn_navLogout.setForeground(new java.awt.Color(206, 206, 206));
        btn_navLogout.setText("LOG OUT");
        btn_navLogout.setBorder(null);
        btn_navLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_navLogout.addActionListener(this::btn_navLogoutActionPerformed);
        pnl_nav6.add(btn_navLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 490, 80, 30));

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
        pnl_nav6.add(btn_navProf, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 120, 30));

        getContentPane().add(pnl_nav6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_today5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_today5MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_today5MouseEntered

    private void btn_today5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_today5MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_today5MouseExited

    private void btn_today5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_today5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_today5ActionPerformed

    private void btn_today6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_today6MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_today6MouseEntered

    private void btn_today6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_today6MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_today6MouseExited

    private void btn_today6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_today6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_today6ActionPerformed

    private void btn_today7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_today7MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_today7MouseEntered

    private void btn_today7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_today7MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_today7MouseExited

    private void btn_today7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_today7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_today7ActionPerformed

    private void btn_today8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_today8MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_today8MouseEntered

    private void btn_today8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_today8MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_today8MouseExited

    private void btn_today8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_today8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_today8ActionPerformed

    private void btn_logout2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_logout2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_logout2ActionPerformed

    private void txt_profFirstNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_profFirstNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_profFirstNameActionPerformed

    private void txt_profEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_profEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_profEmailActionPerformed

    private void txt_profBdayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_profBdayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_profBdayActionPerformed

    private void txt_profPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_profPassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_profPassActionPerformed

    private void chk_createShowPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chk_createShowPassActionPerformed
        if (chk_createShowPass.isSelected()) {
            txt_profPass.setEchoChar((char)0);
        } else {
            txt_profPass.setEchoChar('*');
        }
    }//GEN-LAST:event_chk_createShowPassActionPerformed

    private void txt_profLastNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_profLastNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_profLastNameActionPerformed

    private void txt_profGenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_profGenderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_profGenderActionPerformed

    private void btn_profSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_profSaveActionPerformed
           // 1. Capture the NEW data from the text fields
        String newFName = txt_profFirstName.getText();
        String newLName = txt_profLastName.getText();
        String newEmail = txt_profEmail.getText();
        String newCPNum = txt_profCPNum.getText().trim();
        String newPass = new String(txt_profPass.getPassword());

        String str = "UPDATE DBHOUSE.VIPACCOUNTS SET F_NAME=?, L_NAME=?, EMAIL=?, CP_NUM=?, PASS=? WHERE EMAIL=?";

        try (java.sql.Connection con = java.sql.DriverManager.getConnection("jdbc:derby://localhost:1527/DBHOUSE", "dbhouse", "dbhouse");
             java.sql.PreparedStatement pst = con.prepareStatement(str)) {

            pst.setString(1, newFName);
            pst.setString(2, newLName);
            pst.setString(3, newEmail);
            pst.setString(4, newCPNum);
            pst.setString(5, newPass);
            pst.setString(6, UserSession.loggedInEmail); // The "Where" clause uses the session email

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                UserSession.loggedInEmail = newEmail; 

                javax.swing.JOptionPane.showMessageDialog(this, "Profile updated successfully!");

            }

        } catch (java.sql.SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "DB Error: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_profSaveActionPerformed

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

    private void btn_navReservations1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_navReservations1ActionPerformed
        new Customer_BookingHistory().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_navReservations1ActionPerformed

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
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_navAboutMouseEntered

    private void btn_navAboutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_navAboutMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_navAboutMouseExited

    private void btn_navAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_navAboutActionPerformed
        new Customer_About_Us().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_navAboutActionPerformed

    private void btn_navHomeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_navHomeMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_navHomeMouseEntered

    private void btn_navHomeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_navHomeMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_navHomeMouseExited

    private void btn_navHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_navHomeActionPerformed
        new Customer_Homepage().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_navHomeActionPerformed

    private void btn_navMenuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_navMenuMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_navMenuMouseEntered

    private void btn_navMenuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_navMenuMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_navMenuMouseExited

    private void btn_navMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_navMenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_navMenuActionPerformed

    private void btn_navDineMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_navDineMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_navDineMouseEntered

    private void btn_navDineMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_navDineMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_navDineMouseExited

    private void btn_navDineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_navDineActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_navDineActionPerformed

    private void btn_navReservationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_navReservationsActionPerformed
        new Customer_BookingHistory().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_navReservationsActionPerformed

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
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_navProfMouseEntered

    private void btn_navProfMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_navProfMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_navProfMouseExited

    private void btn_navProfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_navProfActionPerformed
        new Customer_AcctProfile().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_navProfActionPerformed

    private void loadUserProfile() {
        String sql = "SELECT * FROM DBHOUSE.VIPACCOUNTS WHERE EMAIL = ?";

        try (java.sql.Connection con = java.sql.DriverManager.getConnection("jdbc:derby://localhost:1527/DBHOUSE", "dbhouse", "dbhouse");
             java.sql.PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, UserSession.loggedInEmail); 
            java.sql.ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                txt_profFirstName.setText(rs.getString("F_NAME"));
                txt_profLastName.setText(rs.getString("L_NAME"));
                txt_profEmail.setText(rs.getString("EMAIL"));
                txt_profCPNum.setText(rs.getString("CP_NUM"));
                txt_profGender.setText(rs.getString("GENDER"));
                txt_profBday.setText(rs.getString("BDAY"));
                txt_profPass.setText(rs.getString("PASS"));

                txt_profGender.setEditable(false);
                txt_profBday.setEditable(false);
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }
    private void makeFlatButton(javax.swing.JButton btn) {
        btn.setFocusPainted(false);
        btn.setBorder(null);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }      
    
    private void setActiveButton(javax.swing.JButton activeBtn) {
        javax.swing.JButton[] buttons = {btn_navHome, btn_navMenu, btn_navDine, btn_navAbout, btn_navReservations};

        for (javax.swing.JButton btn : buttons) {
            btn.setForeground(Color.WHITE);
        }
            activeBtn.setForeground(new Color(255, 200, 120));
    }
    /*private void switchPanel(javax.swing.JPanel targetPanel) {
        pnl_today.setVisible(false);
        pnl_history.setVisible(false);
        pnl_upcom.setVisible(false);
        pnl_acc.setVisible(false);
        targetPanel.setVisible(true);
    }*/
    
       
       
    /**
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
        java.awt.EventQueue.invokeLater(() -> new Customer_AcctProfile().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Aboutus_Label9;
    private javax.swing.JLabel Aboutus_label10;
    private javax.swing.JLabel bg_today;
    private javax.swing.JButton btn_navAbout;
    private javax.swing.JButton btn_navDine;
    private javax.swing.JButton btn_navHome;
    private javax.swing.JButton btn_navLogout;
    private javax.swing.JButton btn_navMenu;
    private javax.swing.JButton btn_navProf;
    private javax.swing.JButton btn_navReservations;
    private javax.swing.JButton btn_profSave;
    private javax.swing.JCheckBox chk_createShowPass;
    private javax.swing.JLabel greet_user_accprof_label1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JPanel pnl_header;
    private javax.swing.JPanel pnl_nav6;
    private javax.swing.JTextField txt_profBday;
    private javax.swing.JFormattedTextField txt_profCPNum;
    private javax.swing.JTextField txt_profEmail;
    private javax.swing.JTextField txt_profFirstName;
    private javax.swing.JTextField txt_profGender;
    private javax.swing.JTextField txt_profLastName;
    private javax.swing.JPasswordField txt_profPass;
    // End of variables declaration//GEN-END:variables
}
