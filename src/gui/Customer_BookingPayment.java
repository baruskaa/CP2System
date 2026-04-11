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
        payername_txt_payment = new javax.swing.JTextField();
        ewalletnum_txt_payment = new javax.swing.JTextField();
        cardnum_txt_payment = new javax.swing.JTextField();
        btn_toconfirm = new javax.swing.JButton();
        rb_payPayMaya = new javax.swing.JRadioButton();
        btn_backbooking = new javax.swing.JButton();
        payment_bg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rb_payCard.addActionListener(this::rb_payCardActionPerformed);
        getContentPane().add(rb_payCard, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 210, -1, 30));

        rb_payGcash.addActionListener(this::rb_payGcashActionPerformed);
        getContentPane().add(rb_payGcash, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 200, -1, 40));

        payername_txt_payment.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        getContentPane().add(payername_txt_payment, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 340, 160, 30));

        ewalletnum_txt_payment.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        getContentPane().add(ewalletnum_txt_payment, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 340, 160, 30));

        cardnum_txt_payment.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        getContentPane().add(cardnum_txt_payment, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 340, 160, 30));

        btn_toconfirm.setBackground(new java.awt.Color(57, 77, 94));
        btn_toconfirm.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btn_toconfirm.setForeground(new java.awt.Color(255, 255, 255));
        btn_toconfirm.setText("Book Reservation");
        btn_toconfirm.setBorder(null);
        btn_toconfirm.addActionListener(this::btn_toconfirmActionPerformed);
        getContentPane().add(btn_toconfirm, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 450, 150, 30));

        rb_payPayMaya.addActionListener(this::rb_payPayMayaActionPerformed);
        getContentPane().add(rb_payPayMaya, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 230, -1, 30));

        btn_backbooking.setBackground(new java.awt.Color(255, 255, 255));
        btn_backbooking.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        btn_backbooking.setForeground(new java.awt.Color(23, 57, 86));
        btn_backbooking.setText("<");
        btn_backbooking.setBorderPainted(false);
        btn_backbooking.addActionListener(this::btn_backbookingActionPerformed);
        getContentPane().add(btn_backbooking, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 120, -1, -1));

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
            JOptionPane.showMessageDialog(this, "Please complete required fields!");
            return;
        }

        Customer_BookingConfirmation confirmationWindow = new Customer_BookingConfirmation(
            this, fName, lName, email, phone, bookingDate, mealType, pax, paymentMethod, payerName, ewalletNum, cardNum);
        
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

    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new Customer_BookingPayment().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_backbooking;
    private javax.swing.JButton btn_toconfirm;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField cardnum_txt_payment;
    private javax.swing.JTextField ewalletnum_txt_payment;
    private javax.swing.JTextField payername_txt_payment;
    private javax.swing.JLabel payment_bg;
    private javax.swing.JRadioButton rb_payCard;
    private javax.swing.JRadioButton rb_payGcash;
    private javax.swing.JRadioButton rb_payPayMaya;
    // End of variables declaration//GEN-END:variables
}
