
package gui;


public class Customer_Branches_List extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Customer_Branches_List.class.getName());

   
    public Customer_Branches_List() {
        initComponents();
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        customerbranches_text1 = new javax.swing.JLabel();
        customerbranches_button1 = new javax.swing.JButton();
        customerbranches_text4 = new javax.swing.JLabel();
        customerbranches_text2 = new javax.swing.JLabel();
        customerbranches_text3 = new javax.swing.JLabel();
        customerbranches_text5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        customerbranches_button2 = new javax.swing.JButton();
        customerbranches_button5 = new javax.swing.JButton();
        customerbranches_button3 = new javax.swing.JButton();
        customerbranches_button4 = new javax.swing.JButton();
        customerbranches_text7 = new javax.swing.JLabel();
        customerbranches_text8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(57, 77, 94));

        customerbranches_text1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        customerbranches_text1.setForeground(new java.awt.Color(255, 255, 255));
        customerbranches_text1.setText("Our Branches");
        customerbranches_text1.setToolTipText("");
        customerbranches_text1.setName("Ourbranches_text1"); // NOI18N

        customerbranches_button1.setBackground(new java.awt.Color(211, 156, 52));
        customerbranches_button1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        customerbranches_button1.setForeground(new java.awt.Color(255, 255, 255));
        customerbranches_button1.setText("Back");
        customerbranches_button1.setBorderPainted(false);
        customerbranches_button1.setContentAreaFilled(false);
        customerbranches_button1.setName("Brancheslist_button1"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(customerbranches_button1)
                .addGap(14, 809, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(349, Short.MAX_VALUE)
                .addComponent(customerbranches_text1)
                .addGap(337, 337, 337))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(customerbranches_button1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(customerbranches_text1)
                .addGap(25, 25, 25))
        );

        customerbranches_text4.setBackground(new java.awt.Color(211, 156, 52));
        customerbranches_text4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        customerbranches_text4.setText("SM North Edsa");
        customerbranches_text4.setName("OurBranches_Text4"); // NOI18N

        customerbranches_text2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        customerbranches_text2.setText("SM Megamall");
        customerbranches_text2.setName("OurBranches_Text2"); // NOI18N

        customerbranches_text3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        customerbranches_text3.setText("3rd Level, Bldg. B SM Megamall Complex EDSA, Mandaluyong City, Metro Manila ");
        customerbranches_text3.setName("OurBranches_Text3"); // NOI18N

        customerbranches_text5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        customerbranches_text5.setText("2nd Floor, The Block, SM North EDSA Annex, Bago Bantay, Quezon City, Metro Manila");
        customerbranches_text5.setName("OurBranches_Text5"); // NOI18N

        jPanel2.setBackground(new java.awt.Color(65, 93, 120));

        customerbranches_button2.setBackground(new java.awt.Color(211, 156, 52));
        customerbranches_button2.setForeground(new java.awt.Color(255, 255, 255));
        customerbranches_button2.setText("Booking");
        customerbranches_button2.setActionCommand("BookingButton");
        customerbranches_button2.setBorderPainted(false);
        customerbranches_button2.setContentAreaFilled(false);
        customerbranches_button2.setName("Brancheslist_button2"); // NOI18N
        customerbranches_button2.addActionListener(this::customerbranches_button2ActionPerformed);

        customerbranches_button5.setBackground(new java.awt.Color(211, 156, 52));
        customerbranches_button5.setForeground(new java.awt.Color(255, 255, 255));
        customerbranches_button5.setText("Reservations");
        customerbranches_button5.setBorder(null);
        customerbranches_button5.setName("Brancheslist_button3"); // NOI18N
        customerbranches_button5.addActionListener(this::customerbranches_button5ActionPerformed);

        customerbranches_button3.setBackground(new java.awt.Color(211, 156, 52));
        customerbranches_button3.setForeground(new java.awt.Color(255, 255, 255));
        customerbranches_button3.setBorderPainted(false);
        customerbranches_button3.setContentAreaFilled(false);
        customerbranches_button3.setLabel("Menu Lists");
        customerbranches_button3.setName("Brancheslist_button4"); // NOI18N

        customerbranches_button4.setBackground(new java.awt.Color(211, 156, 52));
        customerbranches_button4.setForeground(new java.awt.Color(255, 255, 255));
        customerbranches_button4.setText("About Us");
        customerbranches_button4.setBorderPainted(false);
        customerbranches_button4.setContentAreaFilled(false);
        customerbranches_button4.setName("Brancheslist_button5"); // NOI18N
        customerbranches_button4.addActionListener(this::customerbranches_button4ActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(customerbranches_button5, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(customerbranches_button3)
                    .addComponent(customerbranches_button4)
                    .addComponent(customerbranches_button2))
                .addGap(31, 31, 31))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(customerbranches_button2)
                .addGap(30, 30, 30)
                .addComponent(customerbranches_button3)
                .addGap(30, 30, 30)
                .addComponent(customerbranches_button4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 108, Short.MAX_VALUE)
                .addComponent(customerbranches_button5, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69))
        );

        customerbranches_text7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        customerbranches_text7.setText("(+02) 992 127 8711");

        customerbranches_text8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        customerbranches_text8.setText("(+02) 993 189 3167");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(customerbranches_text3)
                    .addComponent(customerbranches_text2)
                    .addComponent(customerbranches_text4)
                    .addComponent(customerbranches_text5)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(customerbranches_text7)
                            .addComponent(customerbranches_text8))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(customerbranches_text2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customerbranches_text3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(customerbranches_text7)
                        .addGap(82, 82, 82)
                        .addComponent(customerbranches_text4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(customerbranches_text5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(customerbranches_text8)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void customerbranches_button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customerbranches_button2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_customerbranches_button2ActionPerformed

    private void customerbranches_button5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customerbranches_button5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_customerbranches_button5ActionPerformed

    private void customerbranches_button4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customerbranches_button4ActionPerformed
        
    }//GEN-LAST:event_customerbranches_button4ActionPerformed

    
    public static void main(String args[]) {
        

       
        java.awt.EventQueue.invokeLater(() -> new Customer_Branches_List().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton customerbranches_button1;
    private javax.swing.JButton customerbranches_button2;
    private javax.swing.JButton customerbranches_button3;
    private javax.swing.JButton customerbranches_button4;
    private javax.swing.JButton customerbranches_button5;
    private javax.swing.JLabel customerbranches_text1;
    private javax.swing.JLabel customerbranches_text2;
    private javax.swing.JLabel customerbranches_text3;
    private javax.swing.JLabel customerbranches_text4;
    private javax.swing.JLabel customerbranches_text5;
    private javax.swing.JLabel customerbranches_text7;
    private javax.swing.JLabel customerbranches_text8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
