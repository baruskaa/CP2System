package gui;



public class Customer_About_Us extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Customer_About_Us.class.getName());

    
    public Customer_About_Us() {
        initComponents();
    }

        @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        Aboutus_biglabel1 = new javax.swing.JLabel();
        Aboutus_navipanel = new javax.swing.JPanel();
        Aboutus_logo = new javax.swing.JLabel();
        Aboutus_button4 = new javax.swing.JButton();
        Aboutus_button3 = new javax.swing.JButton();
        Aboutus_button2 = new javax.swing.JButton();
        Aboutus_button5 = new javax.swing.JButton();
        Aboutus_Label1 = new javax.swing.JLabel();
        Aboutus_label2 = new javax.swing.JLabel();
        Aboutus_button1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        customerbranches_text2 = new javax.swing.JLabel();
        customerbranches_text4 = new javax.swing.JLabel();
        customerbranches_text1 = new javax.swing.JLabel();
        customerbranches_text6 = new javax.swing.JLabel();
        customerbranches_text8 = new javax.swing.JLabel();
        customerbranches_text5 = new javax.swing.JLabel();
        customerbranches_text3 = new javax.swing.JLabel();
        customerbranches_text7 = new javax.swing.JLabel();
        Aboutus_biglabel2 = new javax.swing.JLabel();
        Aboutus_labelpanel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        Aboutus_text3 = new javax.swing.JLabel();
        Aboutus_text12 = new javax.swing.JLabel();
        Aboutus_text9 = new javax.swing.JLabel();
        Aboutus_text13 = new javax.swing.JLabel();
        Aboutus_text4 = new javax.swing.JLabel();
        Aboutus_text15 = new javax.swing.JLabel();
        Aboutus_text14 = new javax.swing.JLabel();
        Aboutus_text5 = new javax.swing.JLabel();
        Aboutus_text10 = new javax.swing.JLabel();
        Aboutus_text6 = new javax.swing.JLabel();
        Aboutus_text2 = new javax.swing.JLabel();
        Aboutus_text7 = new javax.swing.JLabel();
        Aboutus_text8 = new javax.swing.JLabel();
        Aboutus_text16 = new javax.swing.JLabel();
        Aboutus_text11 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Aboutus_biglabel1.setFont(new java.awt.Font("Segoe UI Emoji", 1, 48)); // NOI18N
        Aboutus_biglabel1.setForeground(new java.awt.Color(255, 255, 255));
        Aboutus_biglabel1.setText("About Us");
        getContentPane().add(Aboutus_biglabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, 280, 60));

        Aboutus_navipanel.setBackground(new java.awt.Color(57, 77, 94));

        Aboutus_logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Untitled design(3).png"))); // NOI18N

        Aboutus_button4.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        Aboutus_button4.setForeground(new java.awt.Color(255, 255, 255));
        Aboutus_button4.setText("Profile");
        Aboutus_button4.setBorderPainted(false);
        Aboutus_button4.setContentAreaFilled(false);
        Aboutus_button4.addActionListener(this::Aboutus_button4ActionPerformed);

        Aboutus_button3.setBackground(new java.awt.Color(211, 156, 52));
        Aboutus_button3.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        Aboutus_button3.setForeground(new java.awt.Color(255, 255, 255));
        Aboutus_button3.setText("Menu");
        Aboutus_button3.setBorderPainted(false);
        Aboutus_button3.setContentAreaFilled(false);
        Aboutus_button3.setName("Brancheslist_button4"); // NOI18N
        Aboutus_button3.addActionListener(this::Aboutus_button3ActionPerformed);

        Aboutus_button2.setBackground(new java.awt.Color(211, 156, 52));
        Aboutus_button2.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        Aboutus_button2.setForeground(new java.awt.Color(255, 255, 255));
        Aboutus_button2.setText("Booking");
        Aboutus_button2.setActionCommand("BookingButton");
        Aboutus_button2.setBorderPainted(false);
        Aboutus_button2.setContentAreaFilled(false);
        Aboutus_button2.setName("Brancheslist_button2"); // NOI18N
        Aboutus_button2.addActionListener(this::Aboutus_button2ActionPerformed);

        Aboutus_button5.setBackground(new java.awt.Color(211, 156, 52));
        Aboutus_button5.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        Aboutus_button5.setForeground(new java.awt.Color(255, 255, 255));
        Aboutus_button5.setText("Reservations");
        Aboutus_button5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Aboutus_button5.setName("Brancheslist_button3"); // NOI18N
        Aboutus_button5.addActionListener(this::Aboutus_button5ActionPerformed);

        Aboutus_Label1.setFont(new java.awt.Font("Segoe UI Emoji", 0, 10)); // NOI18N
        Aboutus_Label1.setForeground(new java.awt.Color(153, 153, 153));
        Aboutus_Label1.setText("© 2026 The House Of 7 Buffet.");

        Aboutus_label2.setFont(new java.awt.Font("Segoe UI Emoji", 0, 10)); // NOI18N
        Aboutus_label2.setForeground(new java.awt.Color(153, 153, 153));
        Aboutus_label2.setText("All Rights Reserved.");

        Aboutus_button1.setBackground(new java.awt.Color(211, 156, 52));
        Aboutus_button1.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        Aboutus_button1.setForeground(new java.awt.Color(255, 255, 255));
        Aboutus_button1.setText("Back");
        Aboutus_button1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Aboutus_button1.setBorderPainted(false);
        Aboutus_button1.setContentAreaFilled(false);
        Aboutus_button1.setName("Brancheslist_button1"); // NOI18N
        Aboutus_button1.addActionListener(this::Aboutus_button1ActionPerformed);

        javax.swing.GroupLayout Aboutus_navipanelLayout = new javax.swing.GroupLayout(Aboutus_navipanel);
        Aboutus_navipanel.setLayout(Aboutus_navipanelLayout);
        Aboutus_navipanelLayout.setHorizontalGroup(
            Aboutus_navipanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Aboutus_navipanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(Aboutus_button5, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(Aboutus_navipanelLayout.createSequentialGroup()
                .addGroup(Aboutus_navipanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Aboutus_navipanelLayout.createSequentialGroup()
                        .addGroup(Aboutus_navipanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Aboutus_navipanelLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(Aboutus_label2))
                            .addGroup(Aboutus_navipanelLayout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(Aboutus_navipanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Aboutus_logo)
                                    .addComponent(Aboutus_button2)
                                    .addComponent(Aboutus_button3)
                                    .addComponent(Aboutus_button4)
                                    .addComponent(Aboutus_button1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 25, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Aboutus_navipanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(Aboutus_Label1)))
                .addContainerGap())
        );
        Aboutus_navipanelLayout.setVerticalGroup(
            Aboutus_navipanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Aboutus_navipanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(Aboutus_logo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Aboutus_button1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(Aboutus_button2)
                .addGap(28, 28, 28)
                .addComponent(Aboutus_button3)
                .addGap(32, 32, 32)
                .addComponent(Aboutus_button4)
                .addGap(62, 62, 62)
                .addComponent(Aboutus_button5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                .addComponent(Aboutus_Label1)
                .addGap(0, 0, 0)
                .addComponent(Aboutus_label2)
                .addGap(30, 30, 30))
        );

        getContentPane().add(Aboutus_navipanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel1.setBackground(new java.awt.Color(65, 93, 120));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        customerbranches_text2.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        customerbranches_text2.setForeground(new java.awt.Color(255, 255, 255));
        customerbranches_text2.setText("3rd Level, Bldg. B SM Megamall Complex EDSA,");
        customerbranches_text2.setName("OurBranches_Text3"); // NOI18N

        customerbranches_text4.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        customerbranches_text4.setForeground(new java.awt.Color(255, 255, 255));
        customerbranches_text4.setText("(+02) 992 127 8711");

        customerbranches_text1.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        customerbranches_text1.setForeground(new java.awt.Color(255, 255, 255));
        customerbranches_text1.setText("SM Megamall");
        customerbranches_text1.setName("OurBranches_Text2"); // NOI18N

        customerbranches_text6.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        customerbranches_text6.setForeground(new java.awt.Color(255, 255, 255));
        customerbranches_text6.setText("2nd Floor, The Block, SM North EDSA Annex,");
        customerbranches_text6.setName("OurBranches_Text5"); // NOI18N

        customerbranches_text8.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        customerbranches_text8.setForeground(new java.awt.Color(255, 255, 255));
        customerbranches_text8.setText("(+02) 993 189 3167");

        customerbranches_text5.setBackground(new java.awt.Color(211, 156, 52));
        customerbranches_text5.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        customerbranches_text5.setForeground(new java.awt.Color(255, 255, 255));
        customerbranches_text5.setText("SM North Edsa");
        customerbranches_text5.setName("OurBranches_Text4"); // NOI18N

        customerbranches_text3.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        customerbranches_text3.setForeground(new java.awt.Color(255, 255, 255));
        customerbranches_text3.setText("Mandaluyong City, Metro Manila ");
        customerbranches_text3.setName("OurBranches_Text3"); // NOI18N

        customerbranches_text7.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        customerbranches_text7.setForeground(new java.awt.Color(255, 255, 255));
        customerbranches_text7.setText(" Bago Bantay, Quezon City, Metro Manila");
        customerbranches_text7.setName("OurBranches_Text5"); // NOI18N

        Aboutus_biglabel2.setFont(new java.awt.Font("Segoe UI Emoji", 0, 36)); // NOI18N
        Aboutus_biglabel2.setForeground(new java.awt.Color(255, 255, 255));
        Aboutus_biglabel2.setText("Contact Us");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(customerbranches_text3)
                    .addComponent(customerbranches_text5)
                    .addComponent(customerbranches_text1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customerbranches_text7)
                    .addComponent(customerbranches_text2)
                    .addComponent(Aboutus_biglabel2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(customerbranches_text4)
                            .addComponent(customerbranches_text8)))
                    .addComponent(customerbranches_text6))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(Aboutus_biglabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(customerbranches_text1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(customerbranches_text2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(customerbranches_text3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(customerbranches_text4)
                .addGap(18, 18, 18)
                .addComponent(customerbranches_text5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(customerbranches_text6)
                .addGap(0, 0, 0)
                .addComponent(customerbranches_text7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(customerbranches_text8)
                .addContainerGap(127, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 130, -1, -1));

        Aboutus_labelpanel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/top design revised.png"))); // NOI18N
        Aboutus_labelpanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 68, 93)));
        getContentPane().add(Aboutus_labelpanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 0, -1, -1));

        jPanel3.setBackground(new java.awt.Color(65, 93, 120));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        Aboutus_text3.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        Aboutus_text3.setForeground(new java.awt.Color(255, 255, 255));
        Aboutus_text3.setText("The House of 7 is an buffet concept that hosts seven unique culinary stations   ");
        Aboutus_text3.setName("Aboutus_text2"); // NOI18N

        Aboutus_text12.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        Aboutus_text12.setForeground(new java.awt.Color(255, 255, 255));
        Aboutus_text12.setText("-Wesley’s Western Comfort offers hearty Western favorites and ");

        Aboutus_text9.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        Aboutus_text9.setForeground(new java.awt.Color(255, 255, 255));
        Aboutus_text9.setText("-Stacy’s Asian Fusion showcases a blend of Asian culinary influences, ");

        Aboutus_text13.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        Aboutus_text13.setForeground(new java.awt.Color(255, 255, 255));
        Aboutus_text13.setText("-Yanna’s Sweet Corner provides a wide assortment of desserts, pastries, ");

        Aboutus_text4.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        Aboutus_text4.setForeground(new java.awt.Color(255, 255, 255));
        Aboutus_text4.setText("Each station has a unique flavors and personality.");

        Aboutus_text15.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        Aboutus_text15.setForeground(new java.awt.Color(255, 255, 255));
        Aboutus_text15.setText("-Anne’s Healthy Garden features fresh salads, vegetables, and lighter options,");

        Aboutus_text14.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        Aboutus_text14.setForeground(new java.awt.Color(255, 255, 255));
        Aboutus_text14.setText("and sweets, perfect for guests that has a sweet tooth.");

        Aboutus_text5.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        Aboutus_text5.setForeground(new java.awt.Color(255, 255, 255));
        Aboutus_text5.setText("-Kysha’s Kitchen serves comforting homemade favorites ");

        Aboutus_text10.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        Aboutus_text10.setForeground(new java.awt.Color(255, 255, 255));
        Aboutus_text10.setText("while combining traditional, nostalgic flavors, with a modern touch. ");

        Aboutus_text6.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        Aboutus_text6.setForeground(new java.awt.Color(255, 255, 255));
        Aboutus_text6.setText("that feel warm, familiar, and satisfying meal prepared with love at home.");

        Aboutus_text2.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        Aboutus_text2.setForeground(new java.awt.Color(255, 255, 255));
        Aboutus_text2.setText("Experience cusines ranging from Western, European, and Asian.");
        Aboutus_text2.setName("Aboutus_text3"); // NOI18N

        Aboutus_text7.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        Aboutus_text7.setForeground(new java.awt.Color(255, 255, 255));
        Aboutus_text7.setText("-Sveska’s European Table presents a selection of European-inspired cuisine,");

        Aboutus_text8.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        Aboutus_text8.setForeground(new java.awt.Color(255, 255, 255));
        Aboutus_text8.setText("highlighting traditional dishes, breads, and refined flavors from across Europe.");

        Aboutus_text16.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        Aboutus_text16.setForeground(new java.awt.Color(255, 255, 255));
        Aboutus_text16.setText("this for guests looking for more balanced meals.");

        Aboutus_text11.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        Aboutus_text11.setForeground(new java.awt.Color(255, 255, 255));
        Aboutus_text11.setText("-Caloy’s Grill Station focuses on expertly grilled and savory meats prepared ");

        jLabel6.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("classic comfort dishes.");

        jLabel3.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("fresh to order.");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Aboutus_text3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(86, 86, 86)
                                .addComponent(Aboutus_text4))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(Aboutus_text2, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(Aboutus_text9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addGap(6, 6, 6)
                                            .addComponent(Aboutus_text10, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(0, 0, Short.MAX_VALUE)))
                                    .addComponent(Aboutus_text7)
                                    .addComponent(Aboutus_text8, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Aboutus_text11)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(14, 14, 14)
                                        .addComponent(jLabel3))))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Aboutus_text6)
                                    .addComponent(Aboutus_text5))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(jLabel6))
                            .addComponent(Aboutus_text16)
                            .addComponent(Aboutus_text15)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(Aboutus_text14))
                            .addComponent(Aboutus_text13)
                            .addComponent(Aboutus_text12, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(Aboutus_text3)
                .addGap(0, 0, 0)
                .addComponent(Aboutus_text2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Aboutus_text4)
                .addGap(18, 18, 18)
                .addComponent(Aboutus_text5)
                .addGap(0, 0, 0)
                .addComponent(Aboutus_text6)
                .addGap(6, 6, 6)
                .addComponent(Aboutus_text7, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Aboutus_text8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Aboutus_text9, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Aboutus_text10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Aboutus_text11)
                .addGap(0, 0, 0)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Aboutus_text12)
                .addGap(0, 0, 0)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Aboutus_text13, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Aboutus_text14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Aboutus_text15, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Aboutus_text16)
                .addContainerGap(109, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 130, 440, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtoAboutus_text2nPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void Aboutus_button4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Aboutus_button4ActionPerformed

    }//GEN-LAST:event_Aboutus_button4ActionPerformed

    private void Aboutus_button3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Aboutus_button3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Aboutus_button3ActionPerformed

    private void Aboutus_button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Aboutus_button2ActionPerformed

    }//GEN-LAST:event_Aboutus_button2ActionPerformed

    private void Aboutus_button5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Aboutus_button5ActionPerformed

    }//GEN-LAST:event_Aboutus_button5ActionPerformed

    private void Aboutus_button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Aboutus_button1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Aboutus_button1ActionPerformed

  
    public static void main(String args[]) {
       
        java.awt.EventQueue.invokeLater(() -> new Customer_About_Us().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Aboutus_Label1;
    private javax.swing.JLabel Aboutus_biglabel1;
    private javax.swing.JLabel Aboutus_biglabel2;
    private javax.swing.JButton Aboutus_button1;
    private javax.swing.JButton Aboutus_button2;
    private javax.swing.JButton Aboutus_button3;
    private javax.swing.JButton Aboutus_button4;
    private javax.swing.JButton Aboutus_button5;
    private javax.swing.JLabel Aboutus_label2;
    private javax.swing.JLabel Aboutus_labelpanel1;
    private javax.swing.JLabel Aboutus_logo;
    private javax.swing.JPanel Aboutus_navipanel;
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
    private javax.swing.JLabel customerbranches_text1;
    private javax.swing.JLabel customerbranches_text2;
    private javax.swing.JLabel customerbranches_text3;
    private javax.swing.JLabel customerbranches_text4;
    private javax.swing.JLabel customerbranches_text5;
    private javax.swing.JLabel customerbranches_text6;
    private javax.swing.JLabel customerbranches_text7;
    private javax.swing.JLabel customerbranches_text8;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
}
