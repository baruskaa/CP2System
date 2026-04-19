/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import static gui.Booking_DBConnection.savePayment;
import javax.swing.*;
import java.sql.*;
/**
 *
 * @author lotte
 */
public class Customer_BookingPayment extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Customer_BookingPayment.class.getName());
    private java.io.File selectedReceiptFile = null;
    private JFrame previousWindow;
    private String fName, lName, email, phone, mealType;
    private java.util.Date bookingDate;
    private int pax;
    
    public Customer_BookingPayment(){}
    
    public Customer_BookingPayment(JFrame previousWindow, String firstName, String lastName, 
                                   String email, String phone, java.util.Date bookingDate, 
                                   String mealType, int pax) {
        
        this.previousWindow = previousWindow;
        this.fName = firstName;
        this.lName = lastName;
        this.email = email;
        this.phone = phone;
        this.bookingDate = bookingDate;
        this.mealType = mealType;
        this.pax = pax;

        initComponents();
        setLocationRelativeTo(null);

        setTextFieldLimit(ewalletnum_txt_payment, 11);
        setTextFieldLimit(cardnum_txt_payment, 16);
        
        buttonGroup1.add(rb_payCard);
        buttonGroup1.add(rb_payGcash);
        buttonGroup1.add(rb_payPayMaya);

        updatePaymentFields();
        makeFlatButton(btn_toconfirm);
    }
    
    private void updatePaymentFields() {
        boolean isCard = rb_payCard.isSelected();
        boolean isEwallet = rb_payGcash.isSelected() || rb_payPayMaya.isSelected();

        cardnum_txt_payment.setEnabled(isCard);
        ewalletnum_txt_payment.setEnabled(isEwallet);

        if (!isCard) {
            cardnum_txt_payment.setText("");
        }
        if (!isEwallet) {
            ewalletnum_txt_payment.setText("");
        }
    }
    
    private void makeFlatButton(javax.swing.JButton btn) {
        btn.setFocusPainted(false);
        btn.setBorder(null);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }
    
    private void setTextFieldLimit(javax.swing.JTextField textField, int limit) {
        ((javax.swing.text.AbstractDocument) textField.getDocument()).setDocumentFilter(new javax.swing.text.DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, javax.swing.text.AttributeSet attrs) 
                    throws javax.swing.text.BadLocationException {
                int currentLength = fb.getDocument().getLength();
                if ((currentLength + text.length() - length) <= limit) {
                    
                    if (text.matches("\\d*")) {
                        super.replace(fb, offset, length, text, attrs);
                    }
                }
            }
        });
    }
    
    @SuppressWarnings("unchecked")
    
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        rb_payCard = new javax.swing.JRadioButton();
        rb_payGcash = new javax.swing.JRadioButton();
        lbl_receiptName = new javax.swing.JLabel();
        payername_txt_payment = new javax.swing.JTextField();
        btn_uploadReceipt = new javax.swing.JButton();
        ewalletnum_txt_payment = new javax.swing.JTextField();
        cardnum_txt_payment = new javax.swing.JTextField();
        btn_toconfirm = new javax.swing.JButton();
        rb_payPayMaya = new javax.swing.JRadioButton();
        btn_backbooking = new javax.swing.JButton();
        payment_bg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rb_payCard.addActionListener(this::rb_payCardActionPerformed);
        getContentPane().add(rb_payCard, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 160, -1, 30));

        rb_payGcash.addActionListener(this::rb_payGcashActionPerformed);
        getContentPane().add(rb_payGcash, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 150, -1, 50));

        lbl_receiptName.setBackground(new java.awt.Color(47, 74, 91));
        lbl_receiptName.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        lbl_receiptName.setForeground(new java.awt.Color(47, 74, 91));
        lbl_receiptName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_receiptName.setText("No file selected.");
        lbl_receiptName.setToolTipText("");
        getContentPane().add(lbl_receiptName, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 376, 280, 40));

        payername_txt_payment.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        getContentPane().add(payername_txt_payment, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 290, 190, 30));

        btn_uploadReceipt.setBackground(new java.awt.Color(255, 255, 255));
        btn_uploadReceipt.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btn_uploadReceipt.setForeground(new java.awt.Color(57, 77, 94));
        btn_uploadReceipt.setText("Upload");
        btn_uploadReceipt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(57, 77, 94), 2, true));
        btn_uploadReceipt.addActionListener(this::btn_uploadReceiptActionPerformed);
        getContentPane().add(btn_uploadReceipt, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 380, 90, 30));

        ewalletnum_txt_payment.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        getContentPane().add(ewalletnum_txt_payment, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 290, 190, 30));

        cardnum_txt_payment.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        getContentPane().add(cardnum_txt_payment, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 290, 190, 30));

        btn_toconfirm.setBackground(new java.awt.Color(57, 77, 94));
        btn_toconfirm.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btn_toconfirm.setForeground(new java.awt.Color(255, 255, 255));
        btn_toconfirm.setText("Book Reservation");
        btn_toconfirm.setBorder(null);
        btn_toconfirm.addActionListener(this::btn_toconfirmActionPerformed);
        getContentPane().add(btn_toconfirm, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 470, 150, 30));

        rb_payPayMaya.addActionListener(this::rb_payPayMayaActionPerformed);
        getContentPane().add(rb_payPayMaya, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 190, -1, 30));

        btn_backbooking.setBackground(new java.awt.Color(255, 255, 255));
        btn_backbooking.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        btn_backbooking.setForeground(new java.awt.Color(23, 57, 86));
        btn_backbooking.setText("<");
        btn_backbooking.setBorder(null);
        btn_backbooking.setBorderPainted(false);
        btn_backbooking.addActionListener(this::btn_backbookingActionPerformed);
        getContentPane().add(btn_backbooking, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, -1, -1));

        payment_bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/PAYMENT.png"))); // NOI18N
        getContentPane().add(payment_bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 580));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rb_payCardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_payCardActionPerformed
            updatePaymentFields();    
    }//GEN-LAST:event_rb_payCardActionPerformed

    private void btn_toconfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_toconfirmActionPerformed
       try {
        String payerName = payername_txt_payment.getText().trim();
        String ewalletNum = ewalletnum_txt_payment.getText().trim();
        String cardNum = cardnum_txt_payment.getText().trim();

        String paymentMethod = null;
        if (rb_payCard.isSelected()) paymentMethod = "CARD";
        else if (rb_payGcash.isSelected()) paymentMethod = "GCASH";
        else if (rb_payPayMaya.isSelected()) paymentMethod = "PAYMAYA";

        if (payerName.isEmpty() || paymentMethod == null) {
            JOptionPane.showMessageDialog(this, "Please fill out all fields.");
            return;
        }

        if (rb_payGcash.isSelected() || rb_payPayMaya.isSelected()) {
            if (ewalletNum.length() != 11) {
                JOptionPane.showMessageDialog(this, "Invalid E-wallet number.");
                return;
            }
            if (!ewalletNum.matches("^09\\d{9}$")) {
                JOptionPane.showMessageDialog(this, "Invalid E-wallet number.");
                return;
            }
        }

        if (rb_payCard.isSelected()) {
            if (cardNum.length() != 16) {
                JOptionPane.showMessageDialog(this, "Invalid Credit Card number.");
                return;
            }
        }
        
        if (selectedReceiptFile == null) {
            JOptionPane.showMessageDialog(this, "Please upload a screenshot of your payment receipt.");
            return;
        }

        Customer_BookingConfirmation confirmationWindow = new Customer_BookingConfirmation(
            this, fName, lName, email, phone, bookingDate, mealType, pax, paymentMethod, payerName, ewalletNum, cardNum, selectedReceiptFile);
        
        confirmationWindow.setVisible(true);
        this.dispose();

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        ex.printStackTrace();
    }
    }//GEN-LAST:event_btn_toconfirmActionPerformed

    private void rb_payGcashActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_payGcashActionPerformed
        updatePaymentFields();
    }//GEN-LAST:event_rb_payGcashActionPerformed

    private void rb_payPayMayaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_payPayMayaActionPerformed
        updatePaymentFields();        
    }//GEN-LAST:event_rb_payPayMayaActionPerformed

    private void btn_backbookingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_backbookingActionPerformed
        if (previousWindow != null) {
        previousWindow.setVisible(true);
        this.dispose();
    } else {
        JOptionPane.showMessageDialog(this, "Cannot go back. Previous window not found.");
        }       // TODO add your handling code here:
    }//GEN-LAST:event_btn_backbookingActionPerformed

    private void btn_uploadReceiptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_uploadReceiptActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Payment Screenshot");

        javax.swing.filechooser.FileNameExtensionFilter filter = new javax.swing.filechooser.FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            selectedReceiptFile = fileChooser.getSelectedFile();
            lbl_receiptName.setText(selectedReceiptFile.getName()); 
        }
    }//GEN-LAST:event_btn_uploadReceiptActionPerformed

    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new Customer_BookingPayment().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_backbooking;
    private javax.swing.JButton btn_toconfirm;
    private javax.swing.JButton btn_uploadReceipt;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField cardnum_txt_payment;
    private javax.swing.JTextField ewalletnum_txt_payment;
    private javax.swing.JLabel lbl_receiptName;
    private javax.swing.JTextField payername_txt_payment;
    private javax.swing.JLabel payment_bg;
    private javax.swing.JRadioButton rb_payCard;
    private javax.swing.JRadioButton rb_payGcash;
    private javax.swing.JRadioButton rb_payPayMaya;
    // End of variables declaration//GEN-END:variables
}
