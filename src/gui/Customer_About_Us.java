package gui;



public class Customer_About_Us extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Customer_About_Us.class.getName());

    
    public Customer_About_Us() {
        initComponents();
    }

        @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        Aboutus_panel1 = new javax.swing.JPanel();
        aboutus_button1 = new javax.swing.JButton();
        Aboutus_text1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        Aboutus_text2 = new javax.swing.JLabel();
        Aboutus_panel2 = new javax.swing.JPanel();
        aboutus_button5 = new javax.swing.JButton();
        aboutus_button3 = new javax.swing.JButton();
        aboutus_button4 = new javax.swing.JButton();
        aboutus_button2 = new javax.swing.JButton();
        aboutus_button6 = new javax.swing.JLabel();
        Aboutus_text3 = new javax.swing.JLabel();
        Aboutus_text4 = new javax.swing.JLabel();
        Aboutus_text5 = new javax.swing.JLabel();
        Aboutus_text6 = new javax.swing.JLabel();
        Aboutus_text7 = new javax.swing.JLabel();
        Aboutus_text8 = new javax.swing.JLabel();
        Aboutus_text11 = new javax.swing.JLabel();
        Aboutus_text12 = new javax.swing.JLabel();
        Aboutus_text13 = new javax.swing.JLabel();
        Aboutus_text15 = new javax.swing.JLabel();
        Aboutus_text16 = new javax.swing.JLabel();
        Aboutus_text9 = new javax.swing.JLabel();
        Aboutus_text14 = new javax.swing.JLabel();
        Aboutus_text10 = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 106, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 425, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(900, 580));

        Aboutus_panel1.setBackground(new java.awt.Color(57, 77, 94));

        aboutus_button1.setBackground(new java.awt.Color(211, 156, 52));
        aboutus_button1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        aboutus_button1.setForeground(new java.awt.Color(255, 255, 255));
        aboutus_button1.setBorder(null);
        aboutus_button1.setBorderPainted(false);
        aboutus_button1.setContentAreaFilled(false);
        aboutus_button1.setLabel("Back");
        aboutus_button1.addActionListener(this::aboutus_button1ActionPerformed);

        Aboutus_text1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        Aboutus_text1.setForeground(new java.awt.Color(255, 255, 255));
        Aboutus_text1.setText("About Us");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Untitled design(3).png"))); // NOI18N

        javax.swing.GroupLayout Aboutus_panel1Layout = new javax.swing.GroupLayout(Aboutus_panel1);
        Aboutus_panel1.setLayout(Aboutus_panel1Layout);
        Aboutus_panel1Layout.setHorizontalGroup(
            Aboutus_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Aboutus_panel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(Aboutus_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(aboutus_button1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(267, 267, 267)
                .addComponent(Aboutus_text1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Aboutus_panel1Layout.setVerticalGroup(
            Aboutus_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Aboutus_panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Aboutus_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Aboutus_text1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(Aboutus_panel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(aboutus_button1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(63, 63, 63))
        );

        Aboutus_text2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        Aboutus_text2.setText("Experience cusines ranging from Western, European, and Asian.");
        Aboutus_text2.setName("Aboutus_text3"); // NOI18N

        Aboutus_panel2.setBackground(new java.awt.Color(65, 93, 120));

        aboutus_button5.setBackground(new java.awt.Color(211, 156, 52));
        aboutus_button5.setForeground(new java.awt.Color(255, 255, 255));
        aboutus_button5.setText("Reservations");
        aboutus_button5.setBorder(null);
        aboutus_button5.setName("Aboutus_Button3"); // NOI18N
        aboutus_button5.addActionListener(this::aboutus_button5ActionPerformed);

        aboutus_button3.setBackground(new java.awt.Color(211, 156, 52));
        aboutus_button3.setForeground(new java.awt.Color(255, 255, 255));
        aboutus_button3.setText("Menu List");
        aboutus_button3.setBorderPainted(false);
        aboutus_button3.setContentAreaFilled(false);
        aboutus_button3.setName("Aboutus_Button4"); // NOI18N
        aboutus_button3.addActionListener(this::aboutus_button3ActionPerformed);

        aboutus_button4.setBackground(new java.awt.Color(211, 156, 52));
        aboutus_button4.setForeground(new java.awt.Color(255, 255, 255));
        aboutus_button4.setText("Branches List");
        aboutus_button4.setBorderPainted(false);
        aboutus_button4.setContentAreaFilled(false);
        aboutus_button4.setName("Aboutus_Button5"); // NOI18N
        aboutus_button4.addActionListener(this::aboutus_button4ActionPerformed);

        aboutus_button2.setBackground(new java.awt.Color(211, 156, 52));
        aboutus_button2.setForeground(new java.awt.Color(255, 255, 255));
        aboutus_button2.setText("Booking");
        aboutus_button2.setBorderPainted(false);
        aboutus_button2.setContentAreaFilled(false);
        aboutus_button2.setName("Aboutus_button2"); // NOI18N
        aboutus_button2.addActionListener(this::aboutus_button2ActionPerformed);

        aboutus_button6.setForeground(new java.awt.Color(255, 255, 255));
        aboutus_button6.setText("Profile");

        javax.swing.GroupLayout Aboutus_panel2Layout = new javax.swing.GroupLayout(Aboutus_panel2);
        Aboutus_panel2.setLayout(Aboutus_panel2Layout);
        Aboutus_panel2Layout.setHorizontalGroup(
            Aboutus_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Aboutus_panel2Layout.createSequentialGroup()
                .addGap(0, 15, Short.MAX_VALUE)
                .addGroup(Aboutus_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(aboutus_button5, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(Aboutus_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(aboutus_button4)
                        .addComponent(aboutus_button3)
                        .addComponent(aboutus_button2)))
                .addGap(20, 20, 20))
            .addGroup(Aboutus_panel2Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(aboutus_button6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Aboutus_panel2Layout.setVerticalGroup(
            Aboutus_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Aboutus_panel2Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(aboutus_button2)
                .addGap(30, 30, 30)
                .addComponent(aboutus_button3)
                .addGap(29, 29, 29)
                .addComponent(aboutus_button4)
                .addGap(28, 28, 28)
                .addComponent(aboutus_button6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addComponent(aboutus_button5, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66))
        );

        Aboutus_text3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        Aboutus_text3.setText("The House of 7 is an buffet concept that hosts seven unique culinary stations,   ");
        Aboutus_text3.setName("Aboutus_text2"); // NOI18N

        Aboutus_text4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        Aboutus_text4.setText("Each station has a unique flavors and personality.");

        Aboutus_text5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        Aboutus_text5.setText("-Kysha’s Kitchen serves comforting homemade favorites ");

        Aboutus_text6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        Aboutus_text6.setText("that feel warm, familiar, and satisfying meal prepared with love at home.");

        Aboutus_text7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        Aboutus_text7.setText("-Sveska’s European Table presents a selection of European-inspired cuisine,");

        Aboutus_text8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        Aboutus_text8.setText("highlighting traditional dishes, breads, and refined flavors from across the continent.");

        Aboutus_text11.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        Aboutus_text11.setText("-Caloy’s Grill Station focuses on expertly grilled and savory meats prepared fresh to order.");

        Aboutus_text12.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        Aboutus_text12.setText("-Wesley’s Western Comfort offers hearty Western favorites and classic comfort dishes.");

        Aboutus_text13.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        Aboutus_text13.setText("-Yanna’s Sweet Corner provides a wide assortment of desserts, pastries, and sweets,");

        Aboutus_text15.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        Aboutus_text15.setText("-Anne’s Healthy Garden features fresh salads, vegetables, and lighter options, this for guests");

        Aboutus_text16.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        Aboutus_text16.setText("looking for more balanced meals.");

        Aboutus_text9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        Aboutus_text9.setText("-Stacy’s Asian Fusion showcases a blend of Asian culinary influences, while combining traditional,   ");

        Aboutus_text14.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        Aboutus_text14.setText("perfect for guests that has a sweet tooth.");

        Aboutus_text10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        Aboutus_text10.setText("nostalgic flavors, with a modern touch. ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Aboutus_panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Aboutus_panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Aboutus_text3)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(79, 79, 79)
                                .addComponent(Aboutus_text4))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(Aboutus_text2))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                .addContainerGap(128, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Aboutus_panel1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Aboutus_panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
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
                        .addContainerGap(61, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtoAboutus_text2nPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void aboutus_button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutus_button2ActionPerformed
        
    }//GEN-LAST:event_aboutus_button2ActionPerformed

    private void aboutus_button5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutus_button5ActionPerformed
        
    }//GEN-LAST:event_aboutus_button5ActionPerformed

    private void aboutus_button3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutus_button3ActionPerformed
        
    }//GEN-LAST:event_aboutus_button3ActionPerformed

    private void aboutus_button4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutus_button4ActionPerformed
        
    }//GEN-LAST:event_aboutus_button4ActionPerformed

  
    public static void main(String args[]) {
       
        java.awt.EventQueue.invokeLater(() -> new Customer_About_Us().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Aboutus_panel1;
    private javax.swing.JPanel Aboutus_panel2;
    private javax.swing.JLabel Aboutus_text1;
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
    private javax.swing.JButton aboutus_button1;
    private javax.swing.JButton aboutus_button2;
    private javax.swing.JButton aboutus_button3;
    private javax.swing.JButton aboutus_button4;
    private javax.swing.JButton aboutus_button5;
    private javax.swing.JLabel aboutus_button6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
