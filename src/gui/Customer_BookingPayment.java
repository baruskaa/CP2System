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
    
    private Customer_BookingWindow previousBookingWindow;
    private int bookingId;
    private String mealType;
    private int pax;
    
    private Customer_BookingWindow previousWindow;
    /**
     * Creates new form Payment_Window_Method
    */
    private void updatePaymentFields() {
    boolean isCard = rb_payCard.isSelected();
    boolean isEwallet = rb_payGcash.isSelected() || rb_payPayMaya.isSelected();

    // Enable/disable card fields
    cardnum_txt_payment.setEnabled(isCard);
    secnum_txt_payment.setEnabled(isCard);
    cardexp_txt_payment.setEnabled(isCard);

    // Enable/disable ewallet field
    ewalletnum_txt_payment.setEnabled(isEwallet);

    // Clear fields when disabled
    if (!isCard) {
        cardnum_txt_payment.setText("");
        secnum_txt_payment.setText("");
        cardexp_txt_payment.setText("");
    }
    if (!isEwallet) {
        ewalletnum_txt_payment.setText("");
    }
    }
    
    public Customer_BookingPayment() {
        initComponents();
        buttonGroup1.add(rb_payCard);
        buttonGroup1.add(rb_payGcash);
        buttonGroup1.add(rb_payPayMaya);     
    }
    

    public Customer_BookingPayment(Customer_BookingWindow previousWindow,
                               int bookingId, String mealType, int pax) {
    this.previousWindow = previousWindow;
    this.bookingId = bookingId;
    this.mealType = mealType;
    this.pax = pax;

    initComponents();
    setLocationRelativeTo(null);

    buttonGroup1.add(rb_payCard);
    buttonGroup1.add(rb_payGcash);
    buttonGroup1.add(rb_payPayMaya);

    updatePaymentFields();
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
        secnum_txt_payment = new javax.swing.JTextField();
        cardexp_txt_payment = new javax.swing.JTextField();
        btn_toconfirm = new javax.swing.JButton();
        rb_payPayMaya = new javax.swing.JRadioButton();
        btn_backbooking = new javax.swing.JButton();
        payment_bg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rb_payCard.addActionListener(this::rb_payCardActionPerformed);
        getContentPane().add(rb_payCard, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 160, -1, -1));

        rb_payGcash.addActionListener(this::rb_payGcashActionPerformed);
        getContentPane().add(rb_payGcash, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 150, -1, 40));

        payername_txt_payment.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        getContentPane().add(payername_txt_payment, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 260, 170, 30));

        ewalletnum_txt_payment.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        getContentPane().add(ewalletnum_txt_payment, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 260, 180, 30));

        cardnum_txt_payment.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        getContentPane().add(cardnum_txt_payment, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 350, 170, 30));

        secnum_txt_payment.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        secnum_txt_payment.addActionListener(this::secnum_txt_paymentActionPerformed);
        getContentPane().add(secnum_txt_payment, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 350, 170, 30));

        cardexp_txt_payment.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        getContentPane().add(cardexp_txt_payment, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 410, 170, 30));

        btn_toconfirm.setBackground(new java.awt.Color(185, 153, 79));
        btn_toconfirm.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        btn_toconfirm.setText("Confirm Reservation");
        btn_toconfirm.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_toconfirm.addActionListener(this::btn_toconfirmActionPerformed);
        getContentPane().add(btn_toconfirm, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 490, 150, 30));

        rb_payPayMaya.addActionListener(this::rb_payPayMayaActionPerformed);
        getContentPane().add(rb_payPayMaya, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 180, -1, -1));

        btn_backbooking.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/left-arrow.png"))); // NOI18N
        btn_backbooking.setBorderPainted(false);
        btn_backbooking.addActionListener(this::btn_backbookingActionPerformed);
        getContentPane().add(btn_backbooking, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, -1, -1));

        payment_bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/backgrounds/PAYMENT.png"))); // NOI18N
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
        String secNum = secnum_txt_payment.getText().trim();
        String cardExp = cardexp_txt_payment.getText().trim();

        String paymentMethod = null;
        if (rb_payCard.isSelected()) paymentMethod = "CARD";
        else if (rb_payGcash.isSelected()) paymentMethod = "GCASH";
        else if (rb_payPayMaya.isSelected()) paymentMethod = "PAYMAYA";

        if (payerName.isEmpty() || paymentMethod == null) {
            JOptionPane.showMessageDialog(this, "Please complete required fields!");
            return;
        }

        boolean saved = Booking_DBConnection.savePayment(
            bookingId,
            paymentMethod,
            payerName,
            ewalletNum.isEmpty() ? null : ewalletNum,
            cardNum.isEmpty() ? null : cardNum,
            secNum.isEmpty() ? null : secNum,
            cardExp.isEmpty() ? null : cardExp
        );

        if (!saved) {
            JOptionPane.showMessageDialog(this, "Payment could not be saved. Please try again.");
            return;
        }

        new Customer_BookingConfirmation(this, bookingId, mealType).setVisible(true);
        this.dispose();

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        ex.printStackTrace();
        } 
    }//GEN-LAST:event_btn_toconfirmActionPerformed

    private void secnum_txt_paymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_secnum_txt_paymentActionPerformed
        
    }//GEN-LAST:event_secnum_txt_paymentActionPerformed

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
    private javax.swing.JTextField cardexp_txt_payment;
    private javax.swing.JTextField cardnum_txt_payment;
    private javax.swing.JTextField ewalletnum_txt_payment;
    private javax.swing.JTextField payername_txt_payment;
    private javax.swing.JLabel payment_bg;
    private javax.swing.JRadioButton rb_payCard;
    private javax.swing.JRadioButton rb_payGcash;
    private javax.swing.JRadioButton rb_payPayMaya;
    private javax.swing.JTextField secnum_txt_payment;
    // End of variables declaration//GEN-END:variables
}
