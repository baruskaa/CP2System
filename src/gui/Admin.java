/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;
import java.util.HashMap;
import java.io.*;

/**
 *
 * @author kyshgel
 */
public class Admin extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Admin.class.getName());
    private TableRowSorter<DefaultTableModel> sorter_today;
    private TableRowSorter<DefaultTableModel> sorter_history;
    private TableRowSorter<DefaultTableModel> sorter_upcom;
    private TableRowSorter<DefaultTableModel> sorter_emp;
    private TableRowSorter<DefaultTableModel> sorter_memb;
    
    
    public Admin() {
        initComponents();
        this.setLocationRelativeTo(null);
        
        buttonGroup1.add(rb_genmanager);
        buttonGroup1.add(rb_manager);
        buttonGroup1.add(rb_fdesk);
        
        makeFlatButton(btn_navLogout);
        makeFlatButton(btn_histGenerate);
        
        date_historyTo.setMaxSelectableDate(new java.util.Date());
        loadEmployeeTable();
        loadMemberTable();
        loadHistoryTable();
        
        //TABLE SORTERS

        DefaultTableModel model_today = (DefaultTableModel) tbl_today.getModel();
        sorter_today = new TableRowSorter<>(model_today);
        tbl_today.setRowSorter(sorter_today);

        DefaultTableModel model_history = (DefaultTableModel) tbl_history.getModel();
        sorter_history = new TableRowSorter<>(model_history);
        tbl_history.setRowSorter(sorter_history);
        
        DefaultTableModel model_upcom = (DefaultTableModel) tbl_upcom.getModel();
        sorter_upcom = new TableRowSorter<>(model_upcom);
        tbl_upcom.setRowSorter(sorter_upcom);
        
        DefaultTableModel model_emp = (DefaultTableModel) tbl_emp.getModel();
        sorter_emp = new TableRowSorter<>(model_emp);
        tbl_emp.setRowSorter(sorter_emp);
        
        DefaultTableModel model_memb = (DefaultTableModel) tbl_memb.getModel();
        sorter_memb = new TableRowSorter<>(model_memb);
        tbl_memb.setRowSorter(sorter_memb);

        //TABLE CENTER ALIGNERS
        
        DefaultTableCellRenderer center_today = new DefaultTableCellRenderer();
        center_today.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tbl_today.getColumnCount(); i++) {
            tbl_today.getColumnModel().getColumn(i).setCellRenderer(center_today);
        }
        
        DefaultTableCellRenderer center_history = new DefaultTableCellRenderer();
        center_history.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tbl_history.getColumnCount(); i++) {
            tbl_history.getColumnModel().getColumn(i).setCellRenderer(center_history);
        }
        
        DefaultTableCellRenderer center_upcom = new DefaultTableCellRenderer();
        center_upcom.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tbl_upcom.getColumnCount(); i++) {
            tbl_upcom.getColumnModel().getColumn(i).setCellRenderer(center_upcom);
        }
        
        DefaultTableCellRenderer center_emp = new DefaultTableCellRenderer();
        center_emp.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tbl_emp.getColumnCount(); i++) {
            tbl_emp.getColumnModel().getColumn(i).setCellRenderer(center_emp); 
        }
        
        DefaultTableCellRenderer center_memb = new DefaultTableCellRenderer();
        center_memb.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tbl_memb.getColumnCount(); i++) {
            tbl_memb.getColumnModel().getColumn(i).setCellRenderer(center_memb);
        }
        
         //TABLE HEADER CELL RENDERER
        
        DefaultTableCellRenderer headerRenderertoday = (DefaultTableCellRenderer) tbl_today.getTableHeader().getDefaultRenderer();
        headerRenderertoday.setHorizontalAlignment(JLabel.CENTER); 
        tbl_today.getTableHeader().setForeground(new Color(55, 77, 94));  
        tbl_today.getTableHeader().setFont(new java.awt.Font("Century Gothic", java.awt.Font.BOLD, 14));  
        
        DefaultTableCellRenderer headerRendererhistory = (DefaultTableCellRenderer) tbl_history.getTableHeader().getDefaultRenderer();
        headerRendererhistory.setHorizontalAlignment(JLabel.CENTER); 
        tbl_history.getTableHeader().setForeground(new Color(55, 77, 94));  
        tbl_history.getTableHeader().setFont(new java.awt.Font("Century Gothic", java.awt.Font.BOLD, 14));  
      
        DefaultTableCellRenderer headerRendererupcom = (DefaultTableCellRenderer) tbl_upcom.getTableHeader().getDefaultRenderer();
        headerRendererupcom.setHorizontalAlignment(JLabel.CENTER);
        tbl_upcom.getTableHeader().setForeground(new Color(55, 77, 94));  
        tbl_upcom.getTableHeader().setFont(new java.awt.Font("Century Gothic", java.awt.Font.BOLD, 14));  
        
        DefaultTableCellRenderer headerRendereremp = (DefaultTableCellRenderer) tbl_emp.getTableHeader().getDefaultRenderer();
        headerRendereremp.setHorizontalAlignment(JLabel.CENTER);
        tbl_emp.getTableHeader().setForeground(new Color(55, 77, 94));  
        tbl_emp.getTableHeader().setFont(new java.awt.Font("Century Gothic", java.awt.Font.BOLD, 14));  
        
        DefaultTableCellRenderer headerRenderermemb = (DefaultTableCellRenderer) tbl_memb.getTableHeader().getDefaultRenderer();
        headerRenderermemb.setHorizontalAlignment(JLabel.CENTER);
        tbl_memb.getTableHeader().setForeground(new Color(55, 77, 94));  
        tbl_memb.getTableHeader().setFont(new java.awt.Font("Century Gothic", java.awt.Font.BOLD, 14));  
    
        
        
        //COMPARATORS

        // PAX COMPARATOR 
        sorter_today.setComparator(3, (n1, n2) -> {
            if (n1 == null) return 1;
            if (n2 == null) return -1;
            try {
                return Integer.compare(Integer.parseInt(n1.toString()), Integer.parseInt(n2.toString()));
            } catch (NumberFormatException e) {
                return n1.toString().compareTo(n2.toString());
            }
        });

        sorter_history.setComparator(4, (n1, n2) -> {
            if (n1 == null) return 1;
            if (n2 == null) return -1;
            try {
                return Integer.compare(Integer.parseInt(n1.toString()), Integer.parseInt(n2.toString()));
            } catch (NumberFormatException e) {
                return n1.toString().compareTo(n2.toString());
            }
        });

        sorter_upcom.setComparator(4, (n1, n2) -> {
            if (n1 == null) return 1;
            if (n2 == null) return -1;
            try {
                return Integer.compare(Integer.parseInt(n1.toString()), Integer.parseInt(n2.toString()));
            } catch (NumberFormatException e) {
                return n1.toString().compareTo(n2.toString());
            }
        });

        // TIME COMPARATOR (Lunch first) 
        sorter_today.setComparator(4, (t1, t2) -> {
            if (t1 == null) return 1;
            if (t2 == null) return -1;
            String time1 = t1.toString();
            String time2 = t2.toString();
            if (time1.equals(time2)) return 0;
            if (time1.equals("Lunch")) return -1;
            if (time2.equals("Lunch")) return 1;
            return time1.compareTo(time2);
        });

        sorter_history.setComparator(5, (t1, t2) -> {  
            if (t1 == null) return 1;
            if (t2 == null) return -1;
            String time1 = t1.toString();
            String time2 = t2.toString();
            if (time1.equals(time2)) return 0;
            if (time1.equals("Lunch")) return -1;
            if (time2.equals("Lunch")) return 1;
            return time1.compareTo(time2);
        });

        sorter_upcom.setComparator(5, (t1, t2) -> {
            if (t1 == null) return 1;
            if (t2 == null) return -1;
            String time1 = t1.toString();
            String time2 = t2.toString();
            if (time1.equals(time2)) return 0;
            if (time1.equals("Lunch")) return -1;
            if (time2.equals("Lunch")) return 1;
            return time1.compareTo(time2);
        });

            int total_today = 0;
            for (int i = 0; i < tbl_today.getRowCount(); i++) {
                Object value = tbl_today.getValueAt(i, 0);
                if (value != null && !value.toString().trim().equals("")) {
                    total_today++;
                }
            }
            lbl_total.setText(String.valueOf(total_today));
            
            
        // VIP_ID 
        sorter_memb.setComparator(0, (id1, id2) -> {
            if (id1 == null) return 1;
            if (id2 == null) return -1;
            try {
                int num1 = Integer.parseInt(id1.toString().replace("VIP", ""));
                int num2 = Integer.parseInt(id2.toString().replace("VIP", ""));
                return Integer.compare(num1, num2);
            } catch (Exception e) {
                return id1.toString().compareTo(id2.toString());
            }
        });
        
        
        
        // SEARCHES
        search_today.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }

            private void filter() {
                String text = search_today.getText().trim();
                if (text.isEmpty()) {
                    sorter_today.setRowFilter(null);
                } else {
                    sorter_today.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
        
        search_history.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }

            private void filter() {
                String text = search_history.getText().trim();
                if (text.isEmpty()) {
                    sorter_history.setRowFilter(null);
                } else {
                    sorter_history.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
        
        search_upcom.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }

            private void filter() {
                String text = search_upcom.getText().trim();
                if (text.isEmpty()) {
                    sorter_upcom.setRowFilter(null);
                } else {
                    sorter_upcom.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
        
        search_empacc.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }

            private void filter() {
                String text = search_empacc.getText().trim();
                if (text.isEmpty()) {
                    sorter_emp.setRowFilter(null);
                } else {
                    sorter_emp.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
        
        search_memb.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }

            private void filter() {
                String text = search_memb.getText().trim();
                if (text.isEmpty()) {
                    sorter_memb.setRowFilter(null);
                } else {
                    sorter_memb.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
        
        
        
        pnl_today.setVisible(true);
        pnl_history.setVisible(false);
        pnl_upcom.setVisible(false);
        pnl_emp.setVisible(false);
        pnl_memb.setVisible(false);
        btn_fdesk.setForeground(new Color(255, 200, 120)); 
        
        // REPLACE your current date_historyFrom listener with these two:
        date_historyFrom.addPropertyChangeListener("date", evt -> filterHistoryByDateRange());
        date_historyTo.addPropertyChangeListener("date", evt -> filterHistoryByDateRange());

        
        date_historyFrom.getDateEditor().getUiComponent().addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (date_historyFrom.getDate() == null) {
                    sorter_history.setRowFilter(null);
                }
            }
        });
        
         date_upcom.addPropertyChangeListener("date", evt -> applyUpcomingDateFilter());

        
        date_upcom.getDateEditor().getUiComponent().addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (date_upcom.getDate() == null) {
                    sorter_upcom.setRowFilter(null);
                }
            }
        });
        
        //MOUSE LISTENERS
        
        tbl_emp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int viewRow = tbl_emp.getSelectedRow();
                if (viewRow != -1) {
                    int modelRow = tbl_emp.convertRowIndexToModel(viewRow);

                    txt_empusername.setText(tbl_emp.getModel().getValueAt(modelRow, 0).toString());
                    txt_empfname.setText(tbl_emp.getModel().getValueAt(modelRow, 1).toString());
                    txt_emplname.setText(tbl_emp.getModel().getValueAt(modelRow, 2).toString());
                    txt_emppass.setText(tbl_emp.getModel().getValueAt(modelRow, 3).toString());

                    String role = tbl_emp.getModel().getValueAt(modelRow, 4).toString();
                    if (role.equalsIgnoreCase("Gen. Manager")) rb_genmanager.setSelected(true);
                    else if (role.equalsIgnoreCase("Manager")) rb_manager.setSelected(true);
                    else if (role.equalsIgnoreCase("Front Desk")) rb_fdesk.setSelected(true);
                    else buttonGroup1.clearSelection();
                }
            }
        });
        
        tbl_memb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int viewRow = tbl_memb.getSelectedRow();
                if (viewRow != -1) {
                    int modelRow = tbl_memb.convertRowIndexToModel(viewRow);

                    Object vipId = tbl_memb.getModel().getValueAt(modelRow, 0);
                    Object dateReg = tbl_memb.getModel().getValueAt(modelRow, 1);
                    Object fName = tbl_memb.getModel().getValueAt(modelRow, 2);
                    Object lName = tbl_memb.getModel().getValueAt(modelRow, 3);
                    Object cpNum = tbl_memb.getModel().getValueAt(modelRow, 6);
                    Object email = tbl_memb.getModel().getValueAt(modelRow, 7);
                    Object pass = tbl_memb.getModel().getValueAt(modelRow, 8);

                    txt_membVipId.setText(vipId != null ? vipId.toString() : "");
                    txt_membDatereg.setText(dateReg != null ? dateReg.toString() : "");
                    txt_membFname.setText(fName != null ? fName.toString() : "");
                    txt_membLname.setText(lName != null ? lName.toString() : "");
                    txt_membCpnum.setText(cpNum != null ? cpNum.toString() : "");
                    txt_membEmail.setText(email != null ? email.toString() : "");
                    txt_membPass.setText(pass != null ? pass.toString() : "");
                }
            }
        });
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        pnl_nav = new javax.swing.JPanel();
        btn_fdesk = new javax.swing.JButton();
        btn_upcom = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btn_history = new javax.swing.JButton();
        btn_emp = new javax.swing.JButton();
        btn_members = new javax.swing.JButton();
        btn_navLogout = new javax.swing.JButton();
        pnl_header = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        pnl_history = new javax.swing.JPanel();
        date_historyFrom = new com.toedter.calendar.JDateChooser();
        date_historyTo = new com.toedter.calendar.JDateChooser();
        jLabel20 = new javax.swing.JLabel();
        btn_histGenerate = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        search_history = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_history = new javax.swing.JTable();
        bg_today1 = new javax.swing.JLabel();
        pnl_memb = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        search_memb = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txt_membLname = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txt_membFname = new javax.swing.JTextField();
        txt_membVipId = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        txt_membPass = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        txt_membDatereg = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        txt_membEmail = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txt_membCpnum = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbl_memb = new javax.swing.JTable();
        bg_today4 = new javax.swing.JLabel();
        pnl_emp = new javax.swing.JPanel();
        rb_genmanager = new javax.swing.JRadioButton();
        rb_fdesk = new javax.swing.JRadioButton();
        rb_manager = new javax.swing.JRadioButton();
        jLabel15 = new javax.swing.JLabel();
        txt_empusername = new javax.swing.JTextField();
        txt_emppass = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        btn_accadd = new javax.swing.JButton();
        btn_accedit = new javax.swing.JButton();
        txt_emplname = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        btn_accdel = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        txt_empfname = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        search_empacc = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_emp = new javax.swing.JTable();
        bg_today3 = new javax.swing.JLabel();
        pnl_today = new javax.swing.JPanel();
        lbl_total = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        search_today = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_today = new javax.swing.JTable();
        bg_today = new javax.swing.JLabel();
        pnl_upcom = new javax.swing.JPanel();
        date_upcom = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        search_upcom = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_upcom = new javax.swing.JTable();
        bg_today2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnl_nav.setBackground(new java.awt.Color(55, 77, 94));
        pnl_nav.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_fdesk.setBackground(new java.awt.Color(55, 77, 94));
        btn_fdesk.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        btn_fdesk.setForeground(new java.awt.Color(255, 255, 255));
        btn_fdesk.setText("FRONT DESK");
        btn_fdesk.setBorder(null);
        btn_fdesk.setContentAreaFilled(false);
        btn_fdesk.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_fdesk.setFocusPainted(false);
        btn_fdesk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_fdeskMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_fdeskMouseExited(evt);
            }
        });
        btn_fdesk.addActionListener(this::btn_fdeskActionPerformed);
        pnl_nav.add(btn_fdesk, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 120, 30));

        btn_upcom.setBackground(new java.awt.Color(55, 77, 94));
        btn_upcom.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        btn_upcom.setForeground(new java.awt.Color(255, 255, 255));
        btn_upcom.setText("UPCOMING");
        btn_upcom.setBorder(null);
        btn_upcom.setContentAreaFilled(false);
        btn_upcom.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_upcom.setFocusPainted(false);
        btn_upcom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_upcomMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_upcomMouseExited(evt);
            }
        });
        btn_upcom.addActionListener(this::btn_upcomActionPerformed);
        pnl_nav.add(btn_upcom, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 90, 30));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo.png"))); // NOI18N
        pnl_nav.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, -1, 80));

        btn_history.setBackground(new java.awt.Color(55, 77, 94));
        btn_history.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        btn_history.setForeground(new java.awt.Color(255, 255, 255));
        btn_history.setText("HISTORY");
        btn_history.setBorder(null);
        btn_history.setContentAreaFilled(false);
        btn_history.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_history.setFocusPainted(false);
        btn_history.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_historyMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_historyMouseExited(evt);
            }
        });
        btn_history.addActionListener(this::btn_historyActionPerformed);
        pnl_nav.add(btn_history, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 90, 30));

        btn_emp.setBackground(new java.awt.Color(55, 77, 94));
        btn_emp.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        btn_emp.setForeground(new java.awt.Color(255, 255, 255));
        btn_emp.setText("EMPLOYEES");
        btn_emp.setBorder(null);
        btn_emp.setContentAreaFilled(false);
        btn_emp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_emp.setFocusPainted(false);
        btn_emp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_empMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_empMouseExited(evt);
            }
        });
        btn_emp.addActionListener(this::btn_empActionPerformed);
        pnl_nav.add(btn_emp, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 90, 30));

        btn_members.setBackground(new java.awt.Color(55, 77, 94));
        btn_members.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        btn_members.setForeground(new java.awt.Color(255, 255, 255));
        btn_members.setText("MEMBERS");
        btn_members.setBorder(null);
        btn_members.setContentAreaFilled(false);
        btn_members.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_members.setFocusPainted(false);
        btn_members.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_membersMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_membersMouseExited(evt);
            }
        });
        btn_members.addActionListener(this::btn_membersActionPerformed);
        pnl_nav.add(btn_members, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 90, 30));

        btn_navLogout.setBackground(new java.awt.Color(153, 0, 0));
        btn_navLogout.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        btn_navLogout.setForeground(new java.awt.Color(206, 206, 206));
        btn_navLogout.setText("LOG OUT");
        btn_navLogout.setBorder(null);
        btn_navLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_navLogout.addActionListener(this::btn_navLogoutActionPerformed);
        pnl_nav.add(btn_navLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 490, 80, 30));

        getContentPane().add(pnl_nav, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 580));

        pnl_header.setBackground(new java.awt.Color(55, 91, 115));
        pnl_header.setMaximumSize(new java.awt.Dimension(681, 115));
        pnl_header.setPreferredSize(new java.awt.Dimension(681, 115));

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("ADMIN DASHBOARD");

        javax.swing.GroupLayout pnl_headerLayout = new javax.swing.GroupLayout(pnl_header);
        pnl_header.setLayout(pnl_headerLayout);
        pnl_headerLayout.setHorizontalGroup(
            pnl_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_headerLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(314, Short.MAX_VALUE))
        );
        pnl_headerLayout.setVerticalGroup(
            pnl_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_headerLayout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(33, 33, 33))
        );

        getContentPane().add(pnl_header, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 740, -1));

        pnl_history.setForeground(new java.awt.Color(202, 199, 199));
        pnl_history.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        date_historyFrom.setDateFormatString("MM-dd-yy");
        pnl_history.add(date_historyFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 50, 130, -1));

        date_historyTo.setDateFormatString("MM-dd-yy");
        pnl_history.add(date_historyTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 50, 130, -1));

        jLabel20.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(55, 77, 94));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("to");
        pnl_history.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 50, 30, 20));

        btn_histGenerate.setBackground(new java.awt.Color(55, 77, 94));
        btn_histGenerate.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        btn_histGenerate.setForeground(new java.awt.Color(255, 255, 255));
        btn_histGenerate.setText("GENERATE REPORT");
        btn_histGenerate.setBorder(null);
        btn_histGenerate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_histGenerate.addActionListener(this::btn_histGenerateActionPerformed);
        pnl_history.add(btn_histGenerate, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 420, 140, 30));

        jLabel10.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(55, 77, 94));
        jLabel10.setText("From");
        pnl_history.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 50, 40, 20));

        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(55, 77, 94));
        jLabel7.setText("HISTORY");
        pnl_history.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 170, 50));

        jLabel9.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(55, 77, 94));
        jLabel9.setText("Search:");
        pnl_history.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 20, 60, 20));

        search_history.addActionListener(this::search_historyActionPerformed);
        search_history.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_historyKeyReleased(evt);
            }
        });
        pnl_history.add(search_history, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 20, 170, -1));

        jScrollPane2.setForeground(new java.awt.Color(55, 77, 94));

        tbl_history.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        tbl_history.setForeground(new java.awt.Color(55, 77, 94));
        tbl_history.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, "03-26-26", null, "Juan Dela Cruz", "09357873489", "Lunch",  new Integer(4), null},
                {null, "03-01-26", null, "Maria Santos", "09174532356", "Lunch",  new Integer(3), null},
                {null, "04-19-26", null, "Louise Lopez", "09876541453", "Lunch",  new Integer(2), null},
                {null, "03-01-26", null, "Rhian Espinosa", "09258653421", "Dinner",  new Integer(6), null},
                {null, "04-19-26", null, "Justine Dizon", "09987823421", "Dinner",  new Integer(7), null}
            },
            new String [] {
                "ID", "DATE", "F_NAME", "L_NAME", "CP_NUM", "TIME", "PAX", "REMARKS"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_history.setOpaque(false);
        jScrollPane2.setViewportView(tbl_history);
        if (tbl_history.getColumnModel().getColumnCount() > 0) {
            tbl_history.getColumnModel().getColumn(0).setResizable(false);
            tbl_history.getColumnModel().getColumn(1).setResizable(false);
            tbl_history.getColumnModel().getColumn(2).setResizable(false);
            tbl_history.getColumnModel().getColumn(3).setResizable(false);
            tbl_history.getColumnModel().getColumn(4).setResizable(false);
            tbl_history.getColumnModel().getColumn(5).setResizable(false);
            tbl_history.getColumnModel().getColumn(6).setResizable(false);
            tbl_history.getColumnModel().getColumn(7).setResizable(false);
        }

        pnl_history.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 700, 330));

        bg_today1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bgfd.jpg"))); // NOI18N
        bg_today1.setText("Today");
        pnl_history.add(bg_today1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 470));

        getContentPane().add(pnl_history, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 740, 470));

        pnl_memb.setForeground(new java.awt.Color(202, 199, 199));
        pnl_memb.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel19.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(55, 77, 94));
        jLabel19.setText("Search:");
        pnl_memb.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 40, 60, 20));

        jLabel24.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(55, 77, 94));
        jLabel24.setText("MEMBERS");
        pnl_memb.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 310, 50));

        search_memb.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        search_memb.setForeground(new java.awt.Color(55, 77, 94));
        search_memb.addActionListener(this::search_membActionPerformed);
        search_memb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_membKeyReleased(evt);
            }
        });
        pnl_memb.add(search_memb, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 40, 170, -1));

        jLabel21.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(55, 77, 94));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("VIP ID:");
        pnl_memb.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, 50, 30));

        txt_membLname.setEditable(false);
        txt_membLname.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_membLname.setFocusable(false);
        txt_membLname.addActionListener(this::txt_membLnameNew_tableActionPerformed);
        pnl_memb.add(txt_membLname, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 410, 140, 30));

        jLabel25.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(55, 77, 94));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel25.setText("Last Name:");
        pnl_memb.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(223, 410, 70, 30));

        txt_membFname.setEditable(false);
        txt_membFname.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_membFname.setFocusable(false);
        txt_membFname.addActionListener(this::txt_membFnameNew_tableActionPerformed);
        pnl_memb.add(txt_membFname, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 380, 140, 30));

        txt_membVipId.setEditable(false);
        txt_membVipId.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_membVipId.setFocusable(false);
        txt_membVipId.addActionListener(this::txt_membVipIdNew_tableActionPerformed);
        pnl_memb.add(txt_membVipId, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 380, 120, 30));

        jLabel29.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(55, 77, 94));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel29.setText("First Name:");
        pnl_memb.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(223, 380, 70, 30));

        txt_membPass.setEditable(false);
        txt_membPass.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_membPass.setFocusable(false);
        txt_membPass.addActionListener(this::txt_membPassNew_tableActionPerformed);
        pnl_memb.add(txt_membPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 410, 140, 30));

        jLabel26.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(55, 77, 94));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel26.setText("Pass:");
        pnl_memb.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 410, 50, 30));

        txt_membDatereg.setEditable(false);
        txt_membDatereg.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_membDatereg.setFocusable(false);
        txt_membDatereg.addActionListener(this::txt_membDateregNew_tableActionPerformed);
        pnl_memb.add(txt_membDatereg, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 350, 140, 30));

        jLabel30.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(55, 77, 94));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel30.setText("Date Reg:  ");
        pnl_memb.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 350, 70, 30));

        txt_membEmail.setEditable(false);
        txt_membEmail.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_membEmail.setFocusable(false);
        txt_membEmail.addActionListener(this::txt_membEmailNew_tableActionPerformed);
        pnl_memb.add(txt_membEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 380, 140, 30));

        jLabel27.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(55, 77, 94));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel27.setText("Email:");
        pnl_memb.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 380, 50, 30));

        txt_membCpnum.setEditable(false);
        txt_membCpnum.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_membCpnum.setFocusable(false);
        txt_membCpnum.addActionListener(this::txt_membCpnumNew_tableActionPerformed);
        pnl_memb.add(txt_membCpnum, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 350, 140, 30));

        jLabel28.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(55, 77, 94));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel28.setText("CP Num:");
        pnl_memb.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 350, 60, 30));

        jScrollPane5.setForeground(new java.awt.Color(55, 77, 94));

        tbl_memb.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        tbl_memb.setForeground(new java.awt.Color(55, 77, 94));
        tbl_memb.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"juandcruz@gmail.com", null, "Juan ", "Dela Cuz", null, null, null, null, "jdc26"},
                {"jane.santos@houseofseven.com", null, "Jane ", "Santos", null, null, null, null, "janesantos0"},
                {"admin@email.com", null, "admin", "admin", null, null, null, null, "000000"}
            },
            new String [] {
                "VIP_ID", "DATE_REG", "F_NAME", "L_NAME", "GENDER", "BDAY", "CP_NUM", "EMAIL", "PASS"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_memb.setOpaque(false);
        jScrollPane5.setViewportView(tbl_memb);
        if (tbl_memb.getColumnModel().getColumnCount() > 0) {
            tbl_memb.getColumnModel().getColumn(0).setResizable(false);
            tbl_memb.getColumnModel().getColumn(0).setHeaderValue("VIP_ID");
            tbl_memb.getColumnModel().getColumn(1).setResizable(false);
            tbl_memb.getColumnModel().getColumn(2).setResizable(false);
            tbl_memb.getColumnModel().getColumn(3).setResizable(false);
            tbl_memb.getColumnModel().getColumn(4).setResizable(false);
            tbl_memb.getColumnModel().getColumn(5).setResizable(false);
            tbl_memb.getColumnModel().getColumn(6).setResizable(false);
            tbl_memb.getColumnModel().getColumn(7).setResizable(false);
            tbl_memb.getColumnModel().getColumn(8).setResizable(false);
            tbl_memb.getColumnModel().getColumn(8).setHeaderValue("PASS");
        }

        pnl_memb.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 700, 250));

        bg_today4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bgfd.jpg"))); // NOI18N
        bg_today4.setText("Today");
        pnl_memb.add(bg_today4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 470));

        getContentPane().add(pnl_memb, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 740, 470));

        pnl_emp.setForeground(new java.awt.Color(202, 199, 199));
        pnl_emp.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rb_genmanager.setForeground(new java.awt.Color(55, 77, 94));
        rb_genmanager.setText("Gen. Manager");
        rb_genmanager.addActionListener(this::rb_genmanagerActionPerformed);
        pnl_emp.add(rb_genmanager, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 350, -1, -1));

        rb_fdesk.setForeground(new java.awt.Color(55, 77, 94));
        rb_fdesk.setText("Front Desk");
        pnl_emp.add(rb_fdesk, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 330, -1, -1));

        rb_manager.setForeground(new java.awt.Color(55, 77, 94));
        rb_manager.setText("Manager");
        rb_manager.addActionListener(this::rb_managerActionPerformed);
        pnl_emp.add(rb_manager, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 330, -1, -1));

        jLabel15.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(55, 77, 94));
        jLabel15.setText("Search:");
        pnl_emp.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 40, 60, 20));

        txt_empusername.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_empusername.addActionListener(this::txt_empusernameNew_tableActionPerformed);
        pnl_emp.add(txt_empusername, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 110, 160, 30));

        txt_emppass.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_emppass.addActionListener(this::txt_emppassNew_tableActionPerformed);
        pnl_emp.add(txt_emppass, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 290, 160, 30));

        jLabel16.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(55, 77, 94));
        jLabel16.setText("Username:");
        pnl_emp.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 90, -1, -1));

        jLabel13.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(55, 77, 94));
        jLabel13.setText("Password:");
        pnl_emp.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 270, -1, -1));

        btn_accadd.setBackground(new java.awt.Color(65, 93, 120));
        btn_accadd.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btn_accadd.setForeground(new java.awt.Color(65, 93, 120));
        btn_accadd.setText("Add");
        btn_accadd.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(65, 93, 120), 2, true));
        btn_accadd.setContentAreaFilled(false);
        btn_accadd.setFocusPainted(false);
        btn_accadd.addActionListener(this::btn_accaddAssign_ButtonActionPerformed);
        pnl_emp.add(btn_accadd, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 380, 50, 30));

        btn_accedit.setBackground(new java.awt.Color(65, 93, 120));
        btn_accedit.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btn_accedit.setForeground(new java.awt.Color(65, 93, 120));
        btn_accedit.setText("Edit");
        btn_accedit.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(65, 93, 120), 2, true));
        btn_accedit.setContentAreaFilled(false);
        btn_accedit.setFocusPainted(false);
        btn_accedit.addActionListener(this::btn_acceditAssign_ButtonActionPerformed);
        pnl_emp.add(btn_accedit, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 380, 50, 30));

        txt_emplname.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_emplname.addActionListener(this::txt_emplnameNew_tableActionPerformed);
        pnl_emp.add(txt_emplname, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 230, 160, 30));

        jLabel18.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(55, 77, 94));
        jLabel18.setText("Last Name:");
        pnl_emp.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 210, -1, -1));

        btn_accdel.setBackground(new java.awt.Color(65, 93, 120));
        btn_accdel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btn_accdel.setForeground(new java.awt.Color(65, 93, 120));
        btn_accdel.setText("Delete");
        btn_accdel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(65, 93, 120), 2, true));
        btn_accdel.setContentAreaFilled(false);
        btn_accdel.setFocusPainted(false);
        btn_accdel.setFocusable(false);
        btn_accdel.setRequestFocusEnabled(false);
        btn_accdel.addActionListener(this::btn_accdelRemove_buttonActionPerformed);
        pnl_emp.add(btn_accdel, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 380, 60, 30));

        jLabel14.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(55, 77, 94));
        jLabel14.setText("EMPLOYEES");
        pnl_emp.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 310, 50));

        txt_empfname.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_empfname.addActionListener(this::txt_empfnameNew_tableActionPerformed);
        pnl_emp.add(txt_empfname, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 170, 160, 30));

        jLabel17.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(55, 77, 94));
        jLabel17.setText("First Name:");
        pnl_emp.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 150, -1, -1));

        search_empacc.addActionListener(this::search_empaccActionPerformed);
        search_empacc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_empaccKeyReleased(evt);
            }
        });
        pnl_emp.add(search_empacc, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 40, 170, -1));

        jScrollPane4.setForeground(new java.awt.Color(55, 77, 94));

        tbl_emp.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        tbl_emp.setForeground(new java.awt.Color(55, 77, 94));
        tbl_emp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, "juandcruz@gmail.com", "Juan ", "Dela Cuz", "jdc26", "Customer"},
                {null, "jane.santos@houseofseven.com", "Jane ", "Santos", "janesantos0", "Front Desk"},
                {null, "admin@email.com", "admin", "admin", "000000", "ADMIN"}
            },
            new String [] {
                "EMP_ID", "USERNAME", "F_NAME", "L_NAME", "PASS", "ACC_TYPE"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_emp.setOpaque(false);
        jScrollPane4.setViewportView(tbl_emp);
        if (tbl_emp.getColumnModel().getColumnCount() > 0) {
            tbl_emp.getColumnModel().getColumn(0).setResizable(false);
            tbl_emp.getColumnModel().getColumn(1).setResizable(false);
            tbl_emp.getColumnModel().getColumn(2).setResizable(false);
            tbl_emp.getColumnModel().getColumn(3).setResizable(false);
            tbl_emp.getColumnModel().getColumn(4).setResizable(false);
            tbl_emp.getColumnModel().getColumn(5).setResizable(false);
        }

        pnl_emp.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 530, 330));

        bg_today3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bgfd.jpg"))); // NOI18N
        bg_today3.setText("Today");
        pnl_emp.add(bg_today3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 470));

        getContentPane().add(pnl_emp, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 740, 470));

        pnl_today.setForeground(new java.awt.Color(202, 199, 199));
        pnl_today.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_total.setFont(new java.awt.Font("Century Gothic", 1, 20)); // NOI18N
        lbl_total.setForeground(new java.awt.Color(102, 102, 102));
        lbl_total.setText("30");
        pnl_today.add(lbl_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 420, 70, 40));

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(55, 77, 94));
        jLabel4.setText("TODAY");
        pnl_today.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 170, 50));

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("TOTAL:");
        pnl_today.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 420, 70, 40));

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(55, 77, 94));
        jLabel5.setText("Search:");
        pnl_today.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 30, 60, 40));

        search_today.addActionListener(this::search_todayActionPerformed);
        search_today.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_todayKeyReleased(evt);
            }
        });
        pnl_today.add(search_today, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 40, 170, -1));

        jScrollPane1.setForeground(new java.awt.Color(55, 77, 94));

        tbl_today.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        tbl_today.setForeground(new java.awt.Color(55, 77, 94));
        tbl_today.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                { new Integer(1), "Juan Dela Cruz", "09357873489",  new Integer(4), "Lunch"},
                { new Integer(2), "Maria Santos", "09174532356",  new Integer(3), "Lunch"},
                { new Integer(3), "Louise Lopez", "09876541453",  new Integer(2), "Dinner"},
                { new Integer(4), "Rhian Espinosa", "09258653421",  new Integer(6), "Dinner"},
                { new Integer(5), "Justine Dizon", "09987823421",  new Integer(7), "Lunch"}
            },
            new String [] {
                "TABLE NO.", "CUSTOMER NAME", "CONTACT", "PAX", "TIME"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_today.setOpaque(false);
        jScrollPane1.setViewportView(tbl_today);
        if (tbl_today.getColumnModel().getColumnCount() > 0) {
            tbl_today.getColumnModel().getColumn(0).setResizable(false);
            tbl_today.getColumnModel().getColumn(1).setResizable(false);
            tbl_today.getColumnModel().getColumn(2).setResizable(false);
            tbl_today.getColumnModel().getColumn(3).setResizable(false);
            tbl_today.getColumnModel().getColumn(4).setResizable(false);
        }

        pnl_today.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 700, 330));

        bg_today.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bgfd.jpg"))); // NOI18N
        bg_today.setText("Today");
        pnl_today.add(bg_today, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 470));

        getContentPane().add(pnl_today, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 740, 470));

        pnl_upcom.setForeground(new java.awt.Color(202, 199, 199));
        pnl_upcom.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        date_upcom.setDateFormatString("MM-dd-yy");
        pnl_upcom.add(date_upcom, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 50, 170, -1));

        jLabel11.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(55, 77, 94));
        jLabel11.setText("Date:");
        pnl_upcom.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 50, 50, 20));

        jLabel8.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(55, 77, 94));
        jLabel8.setText("UPCOMING");
        pnl_upcom.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 310, 50));

        jLabel12.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(55, 77, 94));
        jLabel12.setText("Search:");
        pnl_upcom.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 20, 60, 20));

        search_upcom.addActionListener(this::search_upcomActionPerformed);
        search_upcom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_upcomKeyReleased(evt);
            }
        });
        pnl_upcom.add(search_upcom, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 20, 170, -1));

        jScrollPane3.setForeground(new java.awt.Color(55, 77, 94));

        tbl_upcom.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        tbl_upcom.setForeground(new java.awt.Color(55, 77, 94));
        tbl_upcom.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"03-26-26",  new Integer(1), "Juan Dela Cruz", "09357873489",  new Integer(4), "Lunch"},
                {"03-01-26",  new Integer(2), "Maria Santos", "09174532356",  new Integer(3), "Lunch"},
                {"04-19-26",  new Integer(3), "Louise Lopez", "09876541453",  new Integer(2), "Lunch"},
                {"03-01-26",  new Integer(4), "Rhian Espinosa", "09258653421",  new Integer(6), "Dinner"},
                {"04-19-26",  new Integer(5), "Justine Dizon", "09987823421",  new Integer(7), "Dinner"}
            },
            new String [] {
                "DATE", "TABLE NO.", "CUSTOMER NAME", "CONTACT", "PAX", "TIME"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_upcom.setOpaque(false);
        jScrollPane3.setViewportView(tbl_upcom);
        if (tbl_upcom.getColumnModel().getColumnCount() > 0) {
            tbl_upcom.getColumnModel().getColumn(0).setResizable(false);
            tbl_upcom.getColumnModel().getColumn(0).setHeaderValue("DATE");
            tbl_upcom.getColumnModel().getColumn(1).setResizable(false);
            tbl_upcom.getColumnModel().getColumn(1).setHeaderValue("TABLE NO.");
            tbl_upcom.getColumnModel().getColumn(2).setResizable(false);
            tbl_upcom.getColumnModel().getColumn(3).setResizable(false);
            tbl_upcom.getColumnModel().getColumn(4).setResizable(false);
            tbl_upcom.getColumnModel().getColumn(4).setHeaderValue("PAX");
            tbl_upcom.getColumnModel().getColumn(5).setResizable(false);
            tbl_upcom.getColumnModel().getColumn(5).setHeaderValue("TIME");
        }

        pnl_upcom.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 700, 330));

        bg_today2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bgfd.jpg"))); // NOI18N
        bg_today2.setText("Today");
        pnl_upcom.add(bg_today2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 470));

        getContentPane().add(pnl_upcom, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 740, 470));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_fdeskMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_fdeskMouseEntered
    if (!btn_fdesk.getForeground().equals(new Color(255, 200, 120))) {
        btn_fdesk.setForeground(new Color(255, 200, 120));
    }
    btn_fdesk.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));       // TODO add your handling code here:
    }//GEN-LAST:event_btn_fdeskMouseEntered

    private void btn_upcomMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_upcomMouseEntered
        if (!btn_upcom.getForeground().equals(new Color(255, 200, 120))) {
            btn_upcom.setForeground(new Color(255, 200, 120));
        }
        btn_upcom.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
         // TODO add your handling code here:
    }//GEN-LAST:event_btn_upcomMouseEntered

    private void btn_fdeskMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_fdeskMouseExited
        if (!pnl_today.isVisible()) {  
            btn_fdesk.setForeground(Color.WHITE);
        }       
    }//GEN-LAST:event_btn_fdeskMouseExited

    private void btn_upcomMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_upcomMouseExited
        if (!pnl_upcom.isVisible()) {  
            btn_upcom.setForeground(Color.WHITE);
        }        
    }//GEN-LAST:event_btn_upcomMouseExited

    private void search_todayKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_todayKeyReleased
        String text = search_today.getText();
        TableRowSorter sorter = (TableRowSorter) tbl_today.getRowSorter();
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));     
    }//GEN-LAST:event_search_todayKeyReleased

    private void btn_upcomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_upcomActionPerformed
   switchPanel(pnl_upcom);
    setActiveButton(btn_upcom);
    }//GEN-LAST:event_btn_upcomActionPerformed

    private void search_todayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_todayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_search_todayActionPerformed

    private void btn_fdeskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_fdeskActionPerformed
        switchPanel(pnl_today);
        setActiveButton(btn_fdesk);
    }//GEN-LAST:event_btn_fdeskActionPerformed

    private void search_historyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_historyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_search_historyActionPerformed

    private void search_historyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_historyKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_search_historyKeyReleased

    private void search_upcomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_upcomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_search_upcomActionPerformed

    private void search_upcomKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_upcomKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_search_upcomKeyReleased

    private void btn_historyMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_historyMouseEntered
        if (!btn_history.getForeground().equals(new Color(255, 200, 120))) {
            btn_history.setForeground(new Color(255, 200, 120));
        }
    }//GEN-LAST:event_btn_historyMouseEntered

    private void btn_historyMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_historyMouseExited
        if (!pnl_history.isVisible()) {
            btn_history.setForeground(Color.WHITE);
        }
    }//GEN-LAST:event_btn_historyMouseExited

    private void btn_historyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_historyActionPerformed
        switchPanel(pnl_history);
        setActiveButton(btn_history);
    }//GEN-LAST:event_btn_historyActionPerformed

    private void txt_empusernameNew_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_empusernameNew_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_empusernameNew_tableActionPerformed

    private void btn_acceditAssign_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_acceditAssign_ButtonActionPerformed
       
        int viewRow = tbl_emp.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an account first.");
            return;
        }

        int modelRow = tbl_emp.convertRowIndexToModel(viewRow);
        String originalUser = tbl_emp.getModel().getValueAt(modelRow, 0).toString(); 

        String role = rb_genmanager.isSelected() ? "Gen. Manager" : rb_manager.isSelected() ? "Manager" : "Front Desk";

        Connect db = new Connect();
        db.DoConnect();

        try (PreparedStatement pst = db.con.prepareStatement("UPDATE DBHOUSE.EMPACCOUNTS SET USERNAME=?, F_NAME=?, L_NAME=?, PASS=?, ACC_TYPE=? WHERE USERNAME=?")) {
            pst.setString(1, txt_empusername.getText().trim());
            pst.setString(2, txt_empfname.getText().trim());
            pst.setString(3, txt_emplname.getText().trim());
            pst.setString(4, txt_emppass.getText().trim());
            pst.setString(5, role);
            pst.setString(6, originalUser);

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Account updated successfully!");

            clearAccFields();
            loadEmployeeTable(); 
            db.con.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "DB Error: " + ex.getMessage());
        }
    }//GEN-LAST:event_btn_acceditAssign_ButtonActionPerformed

    private void btn_accdelRemove_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_accdelRemove_buttonActionPerformed
        
        int viewRow = tbl_emp.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(this, "Select an account to delete.");
            return;
        }

        int modelRow = tbl_emp.convertRowIndexToModel(viewRow);
        String userToDelete = tbl_emp.getModel().getValueAt(modelRow, 0).toString(); 

        int confirm = JOptionPane.showConfirmDialog(this, "Delete user: " + userToDelete + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            Connect db = new Connect();
            db.DoConnect();

            try (PreparedStatement pst = db.con.prepareStatement("DELETE FROM DBHOUSE.EMPACCOUNTS WHERE USERNAME=?")) {
                pst.setString(1, userToDelete);
                pst.executeUpdate();

                clearAccFields();
                loadEmployeeTable();
                db.con.close();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "DB Error: " + ex.getMessage());
            }
        }
    }//GEN-LAST:event_btn_accdelRemove_buttonActionPerformed

    private void search_empaccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_empaccActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_search_empaccActionPerformed

    private void search_empaccKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_empaccKeyReleased
        DefaultTableModel model = (DefaultTableModel) tbl_emp.getModel();
    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
    tbl_emp.setRowSorter(sorter);

    String text = search_empacc.getText();
    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
    }//GEN-LAST:event_search_empaccKeyReleased

    private void txt_emppassNew_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_emppassNew_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_emppassNew_tableActionPerformed

    
    private void btn_accaddAssign_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_accaddAssign_ButtonActionPerformed

        String user = txt_empusername.getText().trim();
        String fname = txt_empfname.getText().trim();
        String lname = txt_emplname.getText().trim();
        String password = txt_emppass.getText().trim();

        String role = "";
        if (rb_genmanager.isSelected()) role = "Gen. Manager";
        else if (rb_manager.isSelected()) role = "Manager";
        else if (rb_fdesk.isSelected()) role = "Front Desk";

        if (user.isEmpty() || fname.isEmpty() || lname.isEmpty() || password.isEmpty() || role.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        Connect db = new Connect();
        db.DoConnect();

        try (PreparedStatement pst = db.con.prepareStatement("INSERT INTO DBHOUSE.EMPACCOUNTS (EMP_ID, USERNAME, F_NAME, L_NAME, PASS, ACC_TYPE) VALUES (?, ?, ?, ?, ?, ?)")) {

            String newEmpId = getNextEmpId();
            pst.setString(1, newEmpId);
            pst.setString(2, user);
            pst.setString(3, fname);
            pst.setString(4, lname);
            pst.setString(5, password);
            pst.setString(6, role);

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Employee Created! ID: " + newEmpId);

            clearAccFields();
            loadEmployeeTable();
            db.con.close();

        } catch (SQLException ex) {
            if (ex.getSQLState() != null && ex.getSQLState().equals("23505")) {
                JOptionPane.showMessageDialog(this, "Username '" + user + "' already exists!");
            } else {
                JOptionPane.showMessageDialog(this, "DB Error: " + ex.getMessage());
            }
        }
    }//GEN-LAST:event_btn_accaddAssign_ButtonActionPerformed

    private void rb_genmanagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_genmanagerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_genmanagerActionPerformed

    private void btn_empMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_empMouseEntered
        if (!btn_emp.getForeground().equals(new Color(255, 200, 120))) {
        btn_emp.setForeground(new Color(255, 200, 120));
    }
    }//GEN-LAST:event_btn_empMouseEntered

    private void btn_empMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_empMouseExited
        if (!pnl_emp.isVisible()) {
        btn_emp.setForeground(Color.WHITE);
    }
    }//GEN-LAST:event_btn_empMouseExited

    private void btn_empActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_empActionPerformed
        switchPanel(pnl_emp);
        setActiveButton(btn_emp);
    }//GEN-LAST:event_btn_empActionPerformed

    private void txt_empfnameNew_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_empfnameNew_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_empfnameNew_tableActionPerformed

    private void txt_emplnameNew_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_emplnameNew_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_emplnameNew_tableActionPerformed

    private void btn_membersMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_membersMouseEntered
        if (!btn_members.getForeground().equals(new Color(255, 200, 120))) {
        btn_members.setForeground(new Color(255, 200, 120));
    }
    }//GEN-LAST:event_btn_membersMouseEntered

    private void btn_membersMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_membersMouseExited
        if (!pnl_memb.isVisible()) {
        btn_members.setForeground(Color.WHITE);
    }
    }//GEN-LAST:event_btn_membersMouseExited

    private void btn_membersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_membersActionPerformed
        switchPanel(pnl_memb);
        setActiveButton(btn_members);
    }//GEN-LAST:event_btn_membersActionPerformed

    private void search_membActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_membActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_search_membActionPerformed

    private void search_membKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_membKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_search_membKeyReleased

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
/*
    private void txt_empfnameNew_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_empname1New_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_empname1New_tableActionPerformed

    private void search_membActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_empacc1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_search_empacc1ActionPerformed

    private void search_membKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_empacc1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_search_empacc1KeyReleased

    private void btn_membersMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_emp1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_emp1MouseEntered

    private void btn_membersMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_emp1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_emp1MouseExited

    private void btn_membersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_emp1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_emp1ActionPerformed
*/
    private void rb_managerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_managerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_managerActionPerformed

    private void txt_membVipIdNew_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_membVipIdNew_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_membVipIdNew_tableActionPerformed

    private void txt_membFnameNew_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_membFnameNew_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_membFnameNew_tableActionPerformed

    private void txt_membLnameNew_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_membLnameNew_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_membLnameNew_tableActionPerformed

    private void txt_membPassNew_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_membPassNew_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_membPassNew_tableActionPerformed

    private void txt_membEmailNew_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_membEmailNew_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_membEmailNew_tableActionPerformed

    private void txt_membCpnumNew_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_membCpnumNew_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_membCpnumNew_tableActionPerformed

    private void txt_membDateregNew_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_membDateregNew_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_membDateregNew_tableActionPerformed

    private void btn_histGenerateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_histGenerateActionPerformed
     if (tbl_history.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No data available to generate a report.", "Empty Table", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // 1. Extract the filtered data from the JTable
            DefaultTableModel filteredData = new DefaultTableModel(
                new String[]{"ID", "DATE", "F_NAME", "L_NAME", "CP_NUM", "TIME", "PAX", "REMARKS"}, 0
            );

            for (int i = 0; i < tbl_history.getRowCount(); i++) {
                Object[] row = new Object[tbl_history.getColumnCount()];
                for (int j = 0; j < tbl_history.getColumnCount(); j++) {
                    row[j] = tbl_history.getValueAt(i, j); 
                }
                filteredData.addRow(row);
            }

            JRTableModelDataSource dataSource = new JRTableModelDataSource(filteredData);
            
            // --- UPDATED CODE: POINT TO THE .jasper FILE INSTEAD ---
            String reportPath = "src/reports/ReportHistory.jasper"; 
            
            // Use JRLoader to load the pre-compiled file (Skips the XML compiling crash!)
            JasperReport jr = (JasperReport) net.sf.jasperreports.engine.util.JRLoader.loadObjectFromFile(reportPath);
            // -------------------------------------------------------

            // --- GRAB DATES AND ADD TO PARAMETERS ---
            java.util.Date fromDate = date_historyFrom.getDate();
            java.util.Date toDate = date_historyTo.getDate();
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMMM dd, yyyy");

            // If a date isn't selected, provide a default fallback string
            String fromStr = (fromDate != null) ? sdf.format(fromDate) : "Beginning";
            String toStr = (toDate != null) ? sdf.format(toDate) : "Present";

            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("fromDateParam", fromStr);
            parameters.put("toDateParam", toStr);
            // ---------------------------------------------------

            // Pass the parameters map into the fillReport method
            JasperPrint jp = JasperFillManager.fillReport(jr, parameters, dataSource);
            JasperViewer.viewReport(jp, false);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error generating Jasper Report: " + e.getMessage(), "Report Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_histGenerateActionPerformed

     private void makeFlatButton(javax.swing.JButton btn) {
        btn.setFocusPainted(false);
        btn.setBorder(null);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        
}
     private void setActiveButton(javax.swing.JButton activeBtn) {
        javax.swing.JButton[] buttons = {btn_fdesk, btn_emp,btn_members, btn_upcom, btn_history};

        for (javax.swing.JButton btn : buttons) {
            btn.setForeground(Color.WHITE);
        }
        activeBtn.setForeground(new Color(255, 200, 120));
    }
     
     private void clearAccFields() {
        txt_empusername.setText("");
        txt_empfname.setText("");
        txt_emplname.setText("");
        txt_emppass.setText("");
        buttonGroup1.clearSelection();
        tbl_emp.clearSelection();
    }
     
     private void switchPanel(javax.swing.JPanel targetPanel) {
        pnl_today.setVisible(false);
        pnl_history.setVisible(false);
        pnl_upcom.setVisible(false);
        pnl_emp.setVisible(false);
        pnl_memb.setVisible(false);
        targetPanel.setVisible(true);
    }
     
    private void applyHistoryDateFilter() {
        java.util.Date selectedDate = date_historyFrom.getDate();
        if (selectedDate == null) {
            sorter_history.setRowFilter(null);  
            return;
        }

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM-dd-yy");
        String dateStr = sdf.format(selectedDate);

        sorter_history.setRowFilter(RowFilter.regexFilter("(?i)" + dateStr, 0));
    }
    private void applyUpcomingDateFilter() {
        java.util.Date selectedDate = date_upcom.getDate();

        if (selectedDate == null) {
            sorter_upcom.setRowFilter(null);
            return;
        }

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM-dd-yy");
        String dateStr = sdf.format(selectedDate);

        sorter_upcom.setRowFilter(RowFilter.regexFilter("(?i)" + dateStr, 0));
    }
    
     private String getNextEmpId() {
        int nextNumber = 1001; 
        Connect db = new Connect();
        db.DoConnect();

        try (PreparedStatement pst = db.con.prepareStatement("SELECT MAX(CAST(SUBSTR(EMP_ID, 4) AS INT)) FROM DBHOUSE.EMPACCOUNTS");
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                int maxId = rs.getInt(1);
                if (maxId > 0) nextNumber = maxId + 1;
            }
        } catch (SQLException e) { System.out.println("ID Error: " + e.getMessage()); }
        return "EMP" + nextNumber; 
    }
     
    private void loadEmployeeTable() {
        DefaultTableModel model = (DefaultTableModel) tbl_emp.getModel();
        model.setRowCount(0); 

        Connect db = new Connect();
        db.DoConnect();
        try (PreparedStatement pst = db.con.prepareStatement("SELECT USERNAME, F_NAME, L_NAME, PASS, ACC_TYPE FROM DBHOUSE.EMPACCOUNTS");
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("USERNAME"), 
                    rs.getString("F_NAME"),   
                    rs.getString("L_NAME"),   
                    rs.getString("PASS"),     
                    rs.getString("ACC_TYPE")  
                });
            }
            db.con.close();
        } catch (SQLException e) { JOptionPane.showMessageDialog(this, "Error loading employees: " + e.getMessage()); }
    }
     
    private void loadMemberTable() {
        DefaultTableModel model = (DefaultTableModel) tbl_memb.getModel();
        model.setRowCount(0); 

        Connect db = new Connect();
        db.DoConnect();
        try (PreparedStatement pst = db.con.prepareStatement("SELECT VIP_ID, DATE_REG, F_NAME, L_NAME, GENDER, BDAY, CP_NUM, EMAIL, PASS FROM DBHOUSE.VIPACCOUNTS");
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("VIP_ID"), 
                    rs.getDate("DATE_REG"), 
                    rs.getString("F_NAME"), 
                    rs.getString("L_NAME"), 
                    rs.getString("GENDER"), 
                    rs.getDate("BDAY"), 
                    rs.getString("CP_NUM"), 
                    rs.getString("EMAIL"), 
                    rs.getString("PASS")
                });
            }
            db.con.close();
        } catch (SQLException e) { JOptionPane.showMessageDialog(this, "Error loading members: " + e.getMessage()); }
    }
    
    private void loadHistoryTable() {
        DefaultTableModel model = (DefaultTableModel) tbl_history.getModel();
        model.setRowCount(0); 

        Connect db = new Connect();
        db.DoConnect();

        if (db.con != null) {
            String query = 
                "SELECT IR_ID AS ID, D_DATE, F_NAME, L_NAME, CP_NUM, D_TIME, PAX, REMARKS " +
                "FROM DBHOUSE.INHOUSERESERVATIONS " +
                "UNION ALL " +
                "SELECT WI_ID AS ID, D_DATE, F_NAME, L_NAME, CP_NUM, D_TIME, PAX, REMARKS " +
                "FROM DBHOUSE.WALKINDINE " +
                "UNION ALL " +
                "SELECT o.OR_ID AS ID, o.D_DATE, v.F_NAME, v.L_NAME, v.CP_NUM, o.D_TIME, o.PAX, o.REMARKS " +
                "FROM DBHOUSE.ONLINERESERVATIONS o " +
                "JOIN DBHOUSE.VIPACCOUNTS v ON o.VIP_ID = v.VIP_ID " +
                "ORDER BY D_DATE DESC"; // Order by most recent date first

            try (PreparedStatement pst = db.con.prepareStatement(query);
                 ResultSet rs = pst.executeQuery()) {

                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM-dd-yy");

                while (rs.next()) {
                    java.sql.Date sqlDate = rs.getDate("D_DATE");
                    String dateStr = (sqlDate != null) ? sdf.format(sqlDate) : "";

                    model.addRow(new Object[]{
                        rs.getString("ID"),
                        dateStr,
                        rs.getString("F_NAME"),
                        rs.getString("L_NAME"),
                        rs.getString("CP_NUM"),
                        rs.getString("D_TIME"),
                        rs.getInt("PAX"),
                        rs.getString("REMARKS")
                    });
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error loading history: " + e.getMessage());
            } finally {
                try { db.con.close(); } catch (SQLException ex) {}
            }
        }
    }
     
    private void filterHistoryByDateRange() {
        java.util.Date fromDate = date_historyFrom.getDate();
        java.util.Date toDate = date_historyTo.getDate();

        if (fromDate == null || toDate == null) {
            sorter_history.setRowFilter(null);
            return;
        }

        fromDate.setHours(0); fromDate.setMinutes(0); fromDate.setSeconds(0);
        toDate.setHours(23); toDate.setMinutes(59); toDate.setSeconds(59);

        RowFilter<DefaultTableModel, Object> rangeFilter = new RowFilter<DefaultTableModel, Object>() {
            @Override
            public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                String dateStr = entry.getStringValue(1); 
                try {
                    java.util.Date rowDate = new java.text.SimpleDateFormat("MM-dd-yy").parse(dateStr);
                   
                    return !rowDate.before(fromDate) && !rowDate.after(toDate);
                } catch (Exception e) {
                    return false; 
                }
            }
        };

        sorter_history.setRowFilter(rangeFilter);
    }
    /**
 * 
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
        java.awt.EventQueue.invokeLater(() -> new Admin().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bg_today;
    private javax.swing.JLabel bg_today1;
    private javax.swing.JLabel bg_today2;
    private javax.swing.JLabel bg_today3;
    private javax.swing.JLabel bg_today4;
    private javax.swing.JButton btn_accadd;
    private javax.swing.JButton btn_accdel;
    private javax.swing.JButton btn_accedit;
    private javax.swing.JButton btn_emp;
    private javax.swing.JButton btn_fdesk;
    private javax.swing.JButton btn_histGenerate;
    private javax.swing.JButton btn_history;
    private javax.swing.JButton btn_members;
    private javax.swing.JButton btn_navLogout;
    private javax.swing.JButton btn_upcom;
    private javax.swing.ButtonGroup buttonGroup1;
    private com.toedter.calendar.JDateChooser date_historyFrom;
    private com.toedter.calendar.JDateChooser date_historyTo;
    private com.toedter.calendar.JDateChooser date_upcom;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lbl_total;
    private javax.swing.JPanel pnl_emp;
    private javax.swing.JPanel pnl_header;
    private javax.swing.JPanel pnl_history;
    private javax.swing.JPanel pnl_memb;
    private javax.swing.JPanel pnl_nav;
    private javax.swing.JPanel pnl_today;
    private javax.swing.JPanel pnl_upcom;
    private javax.swing.JRadioButton rb_fdesk;
    private javax.swing.JRadioButton rb_genmanager;
    private javax.swing.JRadioButton rb_manager;
    private javax.swing.JTextField search_empacc;
    private javax.swing.JTextField search_history;
    private javax.swing.JTextField search_memb;
    private javax.swing.JTextField search_today;
    private javax.swing.JTextField search_upcom;
    private javax.swing.JTable tbl_emp;
    private javax.swing.JTable tbl_history;
    private javax.swing.JTable tbl_memb;
    private javax.swing.JTable tbl_today;
    private javax.swing.JTable tbl_upcom;
    private javax.swing.JTextField txt_empfname;
    private javax.swing.JTextField txt_emplname;
    private javax.swing.JTextField txt_emppass;
    private javax.swing.JTextField txt_empusername;
    private javax.swing.JTextField txt_membCpnum;
    private javax.swing.JTextField txt_membDatereg;
    private javax.swing.JTextField txt_membEmail;
    private javax.swing.JTextField txt_membFname;
    private javax.swing.JTextField txt_membLname;
    private javax.swing.JTextField txt_membPass;
    private javax.swing.JTextField txt_membVipId;
    // End of variables declaration//GEN-END:variables
}

