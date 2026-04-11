package gui;
import java.awt.Color;
import javax.swing.*;


public class Customer_About_Us extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Customer_About_Us.class.getName());

    
    public Customer_About_Us() {
        initComponents();
        this.setLocationRelativeTo(null);
        makeFlatButton(btn_navReservations);
        makeFlatButton(btn_navLogout);
    }
    
    private void makeFlatButton(javax.swing.JButton btn) {
        btn.setFocusPainted(false);
        btn.setBorder(null);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

        @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        pnl_header = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
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
        setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnl_header.setBackground(new java.awt.Color(55, 91, 115));
        pnl_header.setMaximumSize(new java.awt.Dimension(681, 115));
        pnl_header.setPreferredSize(new java.awt.Dimension(681, 115));

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("ABOUT US");

        javax.swing.GroupLayout pnl_headerLayout = new javax.swing.GroupLayout(pnl_header);
        pnl_header.setLayout(pnl_headerLayout);
        pnl_headerLayout.setHorizontalGroup(
            pnl_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_headerLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel2)
                .addContainerGap(604, Short.MAX_VALUE))
        );
        pnl_headerLayout.setVerticalGroup(
            pnl_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_headerLayout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(33, 33, 33))
        );

        getContentPane().add(pnl_header, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 740, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ABOUT.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 740, 470));

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
    }// </editor-fold>//GEN-END:initComponents

    private void jButtoAboutus_text2nPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
    }//GEN-LAST:event_jButton1ActionPerformed

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

    private void btn_navReservationsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_navReservationsMouseEntered
        if (!btn_navReservations.getBackground().equals(new Color(217,180,95))) {
            btn_navReservations.setBackground(new Color(217,180,95));
        }
        btn_navReservations.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_btn_navReservationsMouseEntered

    private void btn_navReservationsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_navReservationsMouseExited
        btn_navReservations.setBackground(new Color(185,153,79));
    }//GEN-LAST:event_btn_navReservationsMouseExited

    private void btn_navLogoutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_navLogoutMouseEntered
        if (!btn_navLogout.getBackground().equals(new Color(183,14,14))) {
            btn_navLogout.setBackground(new Color(183,14,14));
        }
        btn_navLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_btn_navLogoutMouseEntered

    private void btn_navLogoutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_navLogoutMouseExited
        btn_navLogout.setBackground(new Color(153,0,0));
    }//GEN-LAST:event_btn_navLogoutMouseExited

  
    public static void main(String args[]) {
       
        java.awt.EventQueue.invokeLater(() -> new Customer_About_Us().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Aboutus_Label5;
    private javax.swing.JLabel Aboutus_label6;
    private javax.swing.JButton btn_navAbout;
    private javax.swing.JButton btn_navDine;
    private javax.swing.JButton btn_navHome;
    private javax.swing.JButton btn_navLogout;
    private javax.swing.JButton btn_navMenu;
    private javax.swing.JButton btn_navProf;
    private javax.swing.JButton btn_navReservations;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel pnl_header;
    private javax.swing.JPanel pnl_nav2;
    // End of variables declaration//GEN-END:variables
}
