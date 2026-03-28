
package gui;


public class Customer_Branches_List extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Customer_Branches_List.class.getName());

   
    public Customer_Branches_List() {
        initComponents();
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        customerbranches_panel2 = new javax.swing.JPanel();
        customerbranches_button2 = new javax.swing.JButton();
        customerbranches_button5 = new javax.swing.JButton();
        customerbranches_button3 = new javax.swing.JButton();
        customerbranches_button4 = new javax.swing.JButton();
        customerbranches_button6 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        customerbranches_button1 = new javax.swing.JButton();
        customerbranches_text1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Aboutus_text16 = new javax.swing.JLabel();
        Aboutus_text3 = new javax.swing.JLabel();
        Aboutus_text9 = new javax.swing.JLabel();
        Aboutus_text4 = new javax.swing.JLabel();
        Aboutus_text14 = new javax.swing.JLabel();
        Aboutus_text5 = new javax.swing.JLabel();
        Aboutus_text10 = new javax.swing.JLabel();
        Aboutus_text6 = new javax.swing.JLabel();
        Aboutus_text2 = new javax.swing.JLabel();
        Aboutus_text7 = new javax.swing.JLabel();
        Aboutus_text8 = new javax.swing.JLabel();
        Aboutus_text11 = new javax.swing.JLabel();
        Aboutus_text12 = new javax.swing.JLabel();
        Aboutus_text13 = new javax.swing.JLabel();
        Aboutus_text15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(getBackground());
        setMaximumSize(new java.awt.Dimension(900, 580));

        customerbranches_panel2.setBackground(new java.awt.Color(57, 77, 94));

        customerbranches_button2.setBackground(new java.awt.Color(211, 156, 52));
        customerbranches_button2.setForeground(new java.awt.Color(255, 255, 255));
        customerbranches_button2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/book.png"))); // NOI18N
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
        customerbranches_button3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/menu.png"))); // NOI18N
        customerbranches_button3.setText("Menu");
        customerbranches_button3.setBorderPainted(false);
        customerbranches_button3.setContentAreaFilled(false);
        customerbranches_button3.setName("Brancheslist_button4"); // NOI18N
        customerbranches_button3.addActionListener(this::customerbranches_button3ActionPerformed);

        customerbranches_button4.setBackground(new java.awt.Color(211, 156, 52));
        customerbranches_button4.setForeground(new java.awt.Color(255, 255, 255));
        customerbranches_button4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/information.png"))); // NOI18N
        customerbranches_button4.setText("About Us");
        customerbranches_button4.setBorderPainted(false);
        customerbranches_button4.setContentAreaFilled(false);
        customerbranches_button4.setName("Brancheslist_button5"); // NOI18N
        customerbranches_button4.addActionListener(this::customerbranches_button4ActionPerformed);

        customerbranches_button6.setForeground(new java.awt.Color(255, 255, 255));
        customerbranches_button6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/user.png"))); // NOI18N
        customerbranches_button6.setText("Profile");
        customerbranches_button6.setBorderPainted(false);
        customerbranches_button6.setContentAreaFilled(false);
        customerbranches_button6.addActionListener(this::customerbranches_button6ActionPerformed);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Houseof7Logo.png"))); // NOI18N

        customerbranches_button1.setBackground(new java.awt.Color(211, 156, 52));
        customerbranches_button1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        customerbranches_button1.setForeground(new java.awt.Color(255, 255, 255));
        customerbranches_button1.setText("Back");
        customerbranches_button1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        customerbranches_button1.setBorderPainted(false);
        customerbranches_button1.setContentAreaFilled(false);
        customerbranches_button1.setName("Brancheslist_button1"); // NOI18N

        javax.swing.GroupLayout customerbranches_panel2Layout = new javax.swing.GroupLayout(customerbranches_panel2);
        customerbranches_panel2.setLayout(customerbranches_panel2Layout);
        customerbranches_panel2Layout.setHorizontalGroup(
            customerbranches_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, customerbranches_panel2Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(customerbranches_button5, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
            .addGroup(customerbranches_panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(customerbranches_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(customerbranches_button2)
                    .addComponent(customerbranches_button4)
                    .addComponent(customerbranches_button3)
                    .addComponent(customerbranches_button6)
                    .addComponent(jLabel1)
                    .addComponent(customerbranches_button1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        customerbranches_panel2Layout.setVerticalGroup(
            customerbranches_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customerbranches_panel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(customerbranches_button1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(customerbranches_button2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(customerbranches_button3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(customerbranches_button4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(customerbranches_button6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(customerbranches_button5, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62))
        );

        customerbranches_text1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        customerbranches_text1.setForeground(new java.awt.Color(255, 255, 255));
        customerbranches_text1.setText("About Us");
        customerbranches_text1.setToolTipText("");
        customerbranches_text1.setName("Ourbranches_text1"); // NOI18N

        jLabel2.setText("add wavy blue theme here");

        Aboutus_text16.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10)); // NOI18N
        Aboutus_text16.setText("looking for more balanced meals.");

        Aboutus_text3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10)); // NOI18N
        Aboutus_text3.setText("The House of 7 is an buffet concept that hosts seven unique culinary stations,   ");
        Aboutus_text3.setName("Aboutus_text2"); // NOI18N

        Aboutus_text9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10)); // NOI18N
        Aboutus_text9.setText("-Stacy’s Asian Fusion showcases a blend of Asian culinary influences, while combining traditional,   ");

        Aboutus_text4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10)); // NOI18N
        Aboutus_text4.setText("Each station has a unique flavors and personality.");

        Aboutus_text14.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10)); // NOI18N
        Aboutus_text14.setText("perfect for guests that has a sweet tooth.");

        Aboutus_text5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10)); // NOI18N
        Aboutus_text5.setText("-Kysha’s Kitchen serves comforting homemade favorites ");

        Aboutus_text10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10)); // NOI18N
        Aboutus_text10.setText("nostalgic flavors, with a modern touch. ");

        Aboutus_text6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10)); // NOI18N
        Aboutus_text6.setText("that feel warm, familiar, and satisfying meal prepared with love at home.");

        Aboutus_text2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10)); // NOI18N
        Aboutus_text2.setText("Experience cusines ranging from Western, European, and Asian.");
        Aboutus_text2.setName("Aboutus_text3"); // NOI18N

        Aboutus_text7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10)); // NOI18N
        Aboutus_text7.setText("-Sveska’s European Table presents a selection of European-inspired cuisine,");

        Aboutus_text8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10)); // NOI18N
        Aboutus_text8.setText("highlighting traditional dishes, breads, and refined flavors from across the continent.");

        Aboutus_text11.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10)); // NOI18N
        Aboutus_text11.setText("-Caloy’s Grill Station focuses on expertly grilled and savory meats prepared fresh to order.");

        Aboutus_text12.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10)); // NOI18N
        Aboutus_text12.setText("-Wesley’s Western Comfort offers hearty Western favorites and classic comfort dishes.");

        Aboutus_text13.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10)); // NOI18N
        Aboutus_text13.setText("-Yanna’s Sweet Corner provides a wide assortment of desserts, pastries, and sweets,");

        Aboutus_text15.setFont(new java.awt.Font("Segoe UI Semibold", 0, 10)); // NOI18N
        Aboutus_text15.setText("-Anne’s Healthy Garden features fresh salads, vegetables, and lighter options, this for guests");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(customerbranches_panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(customerbranches_text1)
                        .addGap(373, 373, 373))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(320, 320, 320)
                                .addComponent(jLabel2))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(30, 30, 30)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(Aboutus_text3)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(79, 79, 79)
                                                .addComponent(Aboutus_text4))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(20, 20, 20)
                                                .addComponent(Aboutus_text2))))
                                    .addComponent(Aboutus_text5)
                                    .addComponent(Aboutus_text7)
                                    .addComponent(Aboutus_text8)
                                    .addComponent(Aboutus_text6)
                                    .addComponent(Aboutus_text11)
                                    .addComponent(Aboutus_text12)
                                    .addComponent(Aboutus_text13)
                                    .addComponent(Aboutus_text15)
                                    .addComponent(Aboutus_text16)
                                    .addComponent(Aboutus_text9)
                                    .addComponent(Aboutus_text14)
                                    .addComponent(Aboutus_text10))))
                        .addContainerGap(247, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addComponent(jLabel2)
                .addGap(74, 74, 74)
                .addComponent(customerbranches_text1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Aboutus_text3)
                .addGap(0, 0, 0)
                .addComponent(Aboutus_text2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Aboutus_text4)
                .addGap(18, 18, 18)
                .addComponent(Aboutus_text5)
                .addGap(0, 0, 0)
                .addComponent(Aboutus_text6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Aboutus_text7, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Aboutus_text8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Aboutus_text9)
                .addGap(0, 0, 0)
                .addComponent(Aboutus_text10)
                .addGap(12, 12, 12)
                .addComponent(Aboutus_text11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Aboutus_text12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Aboutus_text13, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Aboutus_text14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Aboutus_text15, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Aboutus_text16)
                .addGap(327, 327, 327))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(customerbranches_panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void customerbranches_button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customerbranches_button2ActionPerformed
        
    }//GEN-LAST:event_customerbranches_button2ActionPerformed

    private void customerbranches_button5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customerbranches_button5ActionPerformed
        
    }//GEN-LAST:event_customerbranches_button5ActionPerformed

    private void customerbranches_button4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customerbranches_button4ActionPerformed
        
    }//GEN-LAST:event_customerbranches_button4ActionPerformed

    private void customerbranches_button6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customerbranches_button6ActionPerformed

    }//GEN-LAST:event_customerbranches_button6ActionPerformed

    private void customerbranches_button3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customerbranches_button3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_customerbranches_button3ActionPerformed

    
    public static void main(String args[]) {
        

       
        java.awt.EventQueue.invokeLater(() -> new Customer_Branches_List().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Aboutus_text10;
    private javax.swing.JLabel Aboutus_text11;
    private javax.swing.JLabel Aboutus_text12;
    private javax.swing.JLabel Aboutus_text13;
    private javax.swing.JLabel Aboutus_text14;
    private javax.swing.JLabel Aboutus_text15;
    private javax.swing.JLabel Aboutus_text16;
    private javax.swing.JLabel Aboutus_text2;
    private javax.swing.JLabel Aboutus_text3;
    private javax.swing.JLabel Aboutus_text4;
    private javax.swing.JLabel Aboutus_text5;
    private javax.swing.JLabel Aboutus_text6;
    private javax.swing.JLabel Aboutus_text7;
    private javax.swing.JLabel Aboutus_text8;
    private javax.swing.JLabel Aboutus_text9;
    private javax.swing.JButton customerbranches_button1;
    private javax.swing.JButton customerbranches_button2;
    private javax.swing.JButton customerbranches_button3;
    private javax.swing.JButton customerbranches_button4;
    private javax.swing.JButton customerbranches_button5;
    private javax.swing.JButton customerbranches_button6;
    private javax.swing.JPanel customerbranches_panel2;
    private javax.swing.JLabel customerbranches_text1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
