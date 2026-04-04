package gui;
public class Customer_AcctProfile extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Customer_AcctProfile.class.getName());
    public Customer_AcctProfile() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menu_accprof_lbl = new javax.swing.JLabel();
        dineclub_accprof_lbl = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        copyright1_accprof_lbl = new javax.swing.JLabel();
        copyright2_accprof_lbl = new javax.swing.JLabel();
        accprof_btn = new javax.swing.JButton();
        logout_accprof_btn = new javax.swing.JButton();
        greet_user_accprof_label = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        username_lbl_accprof = new javax.swing.JLabel();
        name_accprof_lbl = new javax.swing.JLabel();
        email_accprof_lbl = new javax.swing.JLabel();
        phoneno_lbl_accprof = new javax.swing.JLabel();
        gender_lbl_accprof = new javax.swing.JLabel();
        dob_lbl_acc = new javax.swing.JLabel();
        usernm_txt_accprof = new javax.swing.JTextField();
        nm_txt_accprof = new javax.swing.JTextField();
        email_txt_accprof = new javax.swing.JTextField();
        phoneno_txt_accprof = new javax.swing.JTextField();
        male_checkbox_accprof = new javax.swing.JCheckBox();
        Female_checkbox_accprof = new javax.swing.JCheckBox();
        jCheckBox1 = new javax.swing.JCheckBox();
        dob_txt_accprof = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        aboutus_accprof_lbl = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menu_accprof_lbl.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        menu_accprof_lbl.setForeground(new java.awt.Color(255, 255, 255));
        menu_accprof_lbl.setText("MENU & SPECIALS");
        getContentPane().add(menu_accprof_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 110, 20));

        dineclub_accprof_lbl.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        dineclub_accprof_lbl.setForeground(new java.awt.Color(255, 255, 255));
        dineclub_accprof_lbl.setText("DINE CLUB");
        getContentPane().add(dineclub_accprof_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 80, 20));

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("ABOUT US");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, -1, -1));

        copyright1_accprof_lbl.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        copyright1_accprof_lbl.setForeground(new java.awt.Color(255, 255, 255));
        copyright1_accprof_lbl.setText("© The House of 7 Buffet.");
        getContentPane().add(copyright1_accprof_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 530, -1, -1));

        copyright2_accprof_lbl.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        copyright2_accprof_lbl.setForeground(new java.awt.Color(255, 255, 255));
        copyright2_accprof_lbl.setText("All Rights Reserved.");
        getContentPane().add(copyright2_accprof_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 550, -1, -1));

        accprof_btn.setBackground(new java.awt.Color(204, 204, 204));
        accprof_btn.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        accprof_btn.setForeground(new java.awt.Color(255, 255, 255));
        accprof_btn.setText("RESERVATIONS");
        accprof_btn.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        accprof_btn.addActionListener(this::accprof_btnActionPerformed);
        getContentPane().add(accprof_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 130, -1));

        logout_accprof_btn.setBackground(new java.awt.Color(255, 0, 0));
        logout_accprof_btn.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        logout_accprof_btn.setForeground(new java.awt.Color(255, 255, 255));
        logout_accprof_btn.setText("LOG OUT");
        logout_accprof_btn.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        logout_accprof_btn.addActionListener(this::logout_accprof_btnActionPerformed);
        getContentPane().add(logout_accprof_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 500, -1, 20));

        greet_user_accprof_label.setFont(new java.awt.Font("Century Gothic", 1, 48)); // NOI18N
        greet_user_accprof_label.setForeground(new java.awt.Color(47, 74, 91));
        greet_user_accprof_label.setText("Hello User!");
        getContentPane().add(greet_user_accprof_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 160, 290, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/avatar accprof.png"))); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 180, 130, 120));

        username_lbl_accprof.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        username_lbl_accprof.setText("Username");
        getContentPane().add(username_lbl_accprof, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 270, 60, 20));

        name_accprof_lbl.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        name_accprof_lbl.setText("Name");
        getContentPane().add(name_accprof_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 300, 60, -1));

        email_accprof_lbl.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        email_accprof_lbl.setText("Email");
        getContentPane().add(email_accprof_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 330, -1, -1));

        phoneno_lbl_accprof.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        phoneno_lbl_accprof.setText("Phone Number");
        getContentPane().add(phoneno_lbl_accprof, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 360, -1, 20));

        gender_lbl_accprof.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        gender_lbl_accprof.setText("Gender");
        getContentPane().add(gender_lbl_accprof, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 390, -1, -1));

        dob_lbl_acc.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        dob_lbl_acc.setText("Date of Birth");
        getContentPane().add(dob_lbl_acc, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 420, -1, -1));

        usernm_txt_accprof.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        getContentPane().add(usernm_txt_accprof, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 270, 200, -1));

        nm_txt_accprof.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        getContentPane().add(nm_txt_accprof, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 300, 230, -1));

        email_txt_accprof.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        getContentPane().add(email_txt_accprof, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 330, 230, -1));

        phoneno_txt_accprof.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        getContentPane().add(phoneno_txt_accprof, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 360, 180, -1));

        male_checkbox_accprof.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        male_checkbox_accprof.setText("Male");
        getContentPane().add(male_checkbox_accprof, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 390, -1, -1));

        Female_checkbox_accprof.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        Female_checkbox_accprof.setText("Female");
        getContentPane().add(Female_checkbox_accprof, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 390, -1, -1));

        jCheckBox1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jCheckBox1.setText("Other");
        getContentPane().add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 390, -1, -1));

        dob_txt_accprof.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        getContentPane().add(dob_txt_accprof, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 420, 130, -1));

        jTextField1.setBackground(new java.awt.Color(47, 74, 91));
        jTextField1.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(255, 255, 255));
        jTextField1.setText("PROFILE");
        jTextField1.setBorder(null);
        jTextField1.addActionListener(this::jTextField1ActionPerformed);
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, -1, -1));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel1.setText("Edit profile");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 310, -1, -1));

        aboutus_accprof_lbl.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        aboutus_accprof_lbl.setForeground(new java.awt.Color(255, 255, 255));
        aboutus_accprof_lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/backgrounds/ACC PROF.png"))); // NOI18N
        aboutus_accprof_lbl.setIconTextGap(0);
        getContentPane().add(aboutus_accprof_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 580));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logout_accprof_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logout_accprof_btnActionPerformed
        System.exit(0);   // TODO add your handling code here:
    }//GEN-LAST:event_logout_accprof_btnActionPerformed

    private void accprof_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accprof_btnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_accprof_btnActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

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
    private javax.swing.JCheckBox Female_checkbox_accprof;
    private javax.swing.JLabel aboutus_accprof_lbl;
    private javax.swing.JButton accprof_btn;
    private javax.swing.JLabel copyright1_accprof_lbl;
    private javax.swing.JLabel copyright2_accprof_lbl;
    private javax.swing.JLabel dineclub_accprof_lbl;
    private javax.swing.JLabel dob_lbl_acc;
    private javax.swing.JTextField dob_txt_accprof;
    private javax.swing.JLabel email_accprof_lbl;
    private javax.swing.JTextField email_txt_accprof;
    private javax.swing.JLabel gender_lbl_accprof;
    private javax.swing.JLabel greet_user_accprof_label;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton logout_accprof_btn;
    private javax.swing.JCheckBox male_checkbox_accprof;
    private javax.swing.JLabel menu_accprof_lbl;
    private javax.swing.JLabel name_accprof_lbl;
    private javax.swing.JTextField nm_txt_accprof;
    private javax.swing.JLabel phoneno_lbl_accprof;
    private javax.swing.JTextField phoneno_txt_accprof;
    private javax.swing.JLabel username_lbl_accprof;
    private javax.swing.JTextField usernm_txt_accprof;
    // End of variables declaration//GEN-END:variables
}
