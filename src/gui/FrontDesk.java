/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui; 

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.RowFilter;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.ButtonGroup;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author kyshgel
 */
public class FrontDesk extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrontDesk.class.getName());
    private TableRowSorter<DefaultTableModel> sorter_reserve;
    private TableRowSorter<DefaultTableModel> sorter_walkin;
    private TableRowSorter<DefaultTableModel> sorter_inhouse;
    private DefaultTableModel getWalkInModel() {
    return (DefaultTableModel) tbl_walkin.getModel();
}
    private DefaultTableModel getInHouseModel() {
    return (DefaultTableModel) tbl_inhouse.getModel();
}
    private int editingInhouseRow = -1;
    private int editingWalkinRow = -1;
    private int editingRow = -1;
    
    public FrontDesk() {
    }
    
    public FrontDesk(String loggedInName) {
        initComponents();
        this.setLocationRelativeTo(null);
        
        lbl_username.setText(loggedInName);
        loadInhouseTable();
        loadWalkinTable();
        loadReserveTable();
        updateNavbarStats();
        
        pnl_walkin.setVisible(false);
        pnl_reserve.setVisible(true);
        pnl_inhouse.setVisible(false);
        
        btn_reserve.setForeground(new Color(255, 200, 120)); 
        makeFlatButton(btn_logout);
        makeFlatButton(btn_cancel);
        
        // DATE CHOOSER RESTRICTIONS
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.add(java.util.Calendar.DATE, 1); 
        dc_inhouse.setMinSelectableDate(cal.getTime());

        java.util.Calendar maxCal = java.util.Calendar.getInstance();
        maxCal.set(java.util.Calendar.YEAR, LocalDate.now().getYear() + 1);
        maxCal.set(java.util.Calendar.MONTH, java.util.Calendar.DECEMBER);
        maxCal.set(java.util.Calendar.DAY_OF_MONTH, 31);
        dc_inhouse.setMaxSelectableDate(maxCal.getTime());

        //CHARAC LIMITS  
        setTextFieldLimit(txt_IHcp,11,true);
        setTextFieldLimit(txt_IHfname,50,false);
        setTextFieldLimit(txt_IHlname,50,false);
        setTextFieldLimit(txt_walkinCp,11,true);
        setTextFieldLimit(txt_walkinFname,50,false);
        setTextFieldLimit(txt_walkinLname,50,false);
        
        //TABLE MOUSE LISTENERS
        
        tbl_walkin.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int viewRow = tbl_walkin.getSelectedRow();
                if (viewRow != -1) {
                    int clickedModelRow = tbl_walkin.convertRowIndexToModel(viewRow);
                    
                    if (editingWalkinRow == clickedModelRow) {
                        clearWalkinFields();
                    } else {
                        editingWalkinRow = clickedModelRow;
                        DefaultTableModel model = (DefaultTableModel) tbl_walkin.getModel();

                        txt_walkinFname.setText(model.getValueAt(editingWalkinRow, 1).toString());
                        txt_walkinLname.setText(model.getValueAt(editingWalkinRow, 2).toString());
                        
                        String time = model.getValueAt(editingWalkinRow, 4).toString();
                        if (time.equalsIgnoreCase("Lunch")) rb_walkinLunch.setSelected(true);
                        else rb_walkinDinner.setSelected(true);
                        
                        spn_walkinpax.setValue(Integer.valueOf(model.getValueAt(editingWalkinRow, 5).toString()));

                        String id = model.getValueAt(editingWalkinRow, 0).toString();
                        Connect db = new Connect();
                        db.DoConnect();
                        try (PreparedStatement pst = db.con.prepareStatement("SELECT CP_NUM FROM DBHOUSE.WALKINDINE WHERE WI_ID=?")) {
                            pst.setString(1, id);
                            ResultSet rs = pst.executeQuery();
                            if (rs.next()) txt_walkinCp.setText(rs.getString("CP_NUM"));
                            db.con.close();
                        } catch (SQLException e) {}
                    }
                }
            }
        });
        tbl_inhouse.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int viewRow = tbl_inhouse.getSelectedRow();
                if (viewRow != -1) {
                    int clickedModelRow = tbl_inhouse.convertRowIndexToModel(viewRow);
                    
                    if (editingInhouseRow == clickedModelRow) {
                        clearInhouseFields();
                    } else {
                        editingInhouseRow = clickedModelRow;
                        DefaultTableModel model = (DefaultTableModel) tbl_inhouse.getModel();

                        String id = model.getValueAt(editingInhouseRow, 0).toString();
                        
                        dc_inhouse.setDate((java.util.Date) model.getValueAt(editingInhouseRow, 1));
                        txt_IHfname.setText(model.getValueAt(editingInhouseRow, 2).toString());
                        txt_IHlname.setText(model.getValueAt(editingInhouseRow, 3).toString());
                        
                        String time = model.getValueAt(editingInhouseRow, 4).toString();
                        if (time.equalsIgnoreCase("Lunch")) rb_IHlunch.setSelected(true);
                        else rb_IHdinner.setSelected(true);
                        
                        spn_inhousepax.setValue(Integer.valueOf(model.getValueAt(editingInhouseRow, 5).toString()));

                        Connect db = new Connect();
                        db.DoConnect();
                        try (PreparedStatement pst = db.con.prepareStatement("SELECT CP_NUM FROM DBHOUSE.INHOUSERESERVATIONS WHERE IR_ID=?")) {
                            pst.setString(1, id);
                            ResultSet rs = pst.executeQuery();
                            if (rs.next()) {
                                txt_IHcp.setText(rs.getString("CP_NUM"));
                            }
                            db.con.close();
                        } catch (SQLException e) {}
                    }
                }
            }
        });
        tbl_reserve.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int viewRow = tbl_reserve.getSelectedRow();
                if (viewRow != -1) {
                    int clickedModelRow = tbl_reserve.convertRowIndexToModel(viewRow);
                    
                    if (editingRow == clickedModelRow) {
                        clearReserveFields(); 
                    } else {
                        editingRow = clickedModelRow;
                        DefaultTableModel model = (DefaultTableModel) tbl_reserve.getModel();

                        txt_rsvID.setText(model.getValueAt(editingRow, 0) != null ? model.getValueAt(editingRow, 0).toString() : "");
                        txt_rsvVIPID.setText(model.getValueAt(editingRow, 1) != null ? model.getValueAt(editingRow, 1).toString() : "");
                        txt_membFname.setText(model.getValueAt(editingRow, 2) != null ? model.getValueAt(editingRow, 2).toString() : "");
                        txt_membLname.setText(model.getValueAt(editingRow, 3) != null ? model.getValueAt(editingRow, 3).toString() : "");
                        txt_membCPnum.setText(model.getValueAt(editingRow, 4) != null ? model.getValueAt(editingRow, 4).toString() : "");
                        txt_rsvTime.setText(model.getValueAt(editingRow, 5) != null ? model.getValueAt(editingRow, 5).toString() : "");
                        txt_rsvPax.setText(model.getValueAt(editingRow, 6) != null ? model.getValueAt(editingRow, 6).toString() : "");
                        txt_rsvRemarks.setText(model.getValueAt(editingRow, 7) != null ? model.getValueAt(editingRow, 7).toString() : "");
                    }
                }
            }
        });
        
        // TABLE HEADER
        
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tbl_reserve.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER); 
        tbl_reserve.getTableHeader().setForeground(new Color(55, 77, 94));  
        tbl_reserve.getTableHeader().setFont(new java.awt.Font("Century Gothic", java.awt.Font.BOLD, 14));  
        
        DefaultTableCellRenderer headerRenderer2 = (DefaultTableCellRenderer) tbl_walkin.getTableHeader().getDefaultRenderer();
        headerRenderer2.setHorizontalAlignment(JLabel.CENTER); 
        tbl_walkin.getTableHeader().setForeground(new Color(55, 77, 94));  
        tbl_walkin.getTableHeader().setFont(new java.awt.Font("Century Gothic", java.awt.Font.BOLD, 14)); 
        
        DefaultTableCellRenderer headerRenderer3 = (DefaultTableCellRenderer) tbl_inhouse.getTableHeader().getDefaultRenderer();
        headerRenderer3.setHorizontalAlignment(JLabel.CENTER); 
        tbl_inhouse.getTableHeader().setForeground(new Color(55, 77, 94));  
        tbl_inhouse.getTableHeader().setFont(new java.awt.Font("Century Gothic", java.awt.Font.BOLD, 14)); 
        
        getContentPane().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                clearWalkInSelection();
            }
            private void clearWalkInSelection() { 
            }
           });
        
        // DATE TODAY
        LocalDate today = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        lbl_date.setText(today.format(format));
        
        // BUTTON GROUPS
        bg_walkinTime = new ButtonGroup();
        bg_walkinTime.add(rb_walkinLunch);
        bg_walkinTime.add(rb_walkinDinner);
        
        bg_IHtime = new ButtonGroup();
        bg_IHtime.add(rb_IHlunch);
        bg_IHtime.add(rb_IHdinner);
        
        // ROW SORTERS
        DefaultTableModel model_walkin = (DefaultTableModel) tbl_walkin.getModel();
        sorter_walkin = new TableRowSorter<>(model_walkin);
        tbl_walkin.setRowSorter(sorter_walkin);
        
        DefaultTableModel model_reserve = (DefaultTableModel) tbl_reserve.getModel();
        sorter_reserve = new TableRowSorter<>(model_reserve);
        tbl_reserve.setRowSorter(sorter_reserve);

        DefaultTableModel model_inhouse = (DefaultTableModel) tbl_inhouse.getModel();
        sorter_inhouse = new TableRowSorter<>(model_inhouse);
        tbl_inhouse.setRowSorter(sorter_inhouse);

        // CENTER TABLES
        DefaultTableCellRenderer center_reserve = new DefaultTableCellRenderer();
        center_reserve.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tbl_reserve.getColumnCount(); i++) {
            tbl_reserve.getColumnModel().getColumn(i).setCellRenderer(center_reserve);
        }
       
        DefaultTableCellRenderer center_walkin = new DefaultTableCellRenderer();
        center_reserve.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tbl_walkin.getColumnCount(); i++) {
            tbl_walkin.getColumnModel().getColumn(i).setCellRenderer(center_reserve);
        }
        
        DefaultTableCellRenderer center_inhouse = new DefaultTableCellRenderer();
        center_inhouse.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tbl_inhouse.getColumnCount(); i++) {
        tbl_inhouse.getColumnModel().getColumn(i).setCellRenderer(center_inhouse);
        }
        
        // SAFE PAX COMPARATOR 
        sorter_reserve.setComparator(3, (n1, n2) -> {
            if (n1 == null) return 1;
            if (n2 == null) return -1;
            try {
                return Integer.compare(Integer.parseInt(n1.toString()), Integer.parseInt(n2.toString()));
            } catch (NumberFormatException e) {
                return n1.toString().compareTo(n2.toString());
            }
        });

        // TIME COMPARATOR 
        sorter_reserve.setComparator(4, (t1, t2) -> {
            if (t1 == null) return 1;
            if (t2 == null) return -1;
            String time1 = t1.toString();
            String time2 = t2.toString();
            if (time1.equals(time2)) return 0;
            if (time1.equals("Lunch")) return -1;
            if (time2.equals("Lunch")) return 1;
            return time1.compareTo(time2);
        });

        sorter_walkin.setComparator(0, (n1, n2) -> {
            if (n1 == null) return 1;
            if (n2 == null) return -1;
            try {
                return Integer.compare(Integer.parseInt(n1.toString()), Integer.parseInt(n2.toString()));
            } catch (NumberFormatException e) {
                return n1.toString().compareTo(n2.toString());
            }
        });

        // PAX COMPARATOR 
        sorter_walkin.setComparator(3, (n1, n2) -> {
            if (n1 == null) return 1;
            if (n2 == null) return -1;
            try {
                return Integer.compare(Integer.parseInt(n1.toString()), Integer.parseInt(n2.toString()));
            } catch (NumberFormatException e) {
                return n1.toString().compareTo(n2.toString());
            }
        });

        // TIME COMPARATOR 
        sorter_walkin.setComparator(4, (t1, t2) -> {
            if (t1 == null) return 1;
            if (t2 == null) return -1;
            String time1 = t1.toString();
            String time2 = t2.toString();
            if (time1.equals(time2)) return 0;
            if (time1.equals("Lunch")) return -1;
            if (time2.equals("Lunch")) return 1;
            return time1.compareTo(time2);
        });
        
        // SEARCHES
        search_walkin.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }

            private void filter() {
                String text = search_walkin.getText().trim();
                if (text.isEmpty()) {
                    sorter_reserve.setRowFilter(null);
                } else {
                    sorter_reserve.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
        
        search_reserve.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }

            private void filter() {
                String text = search_reserve.getText().trim();
                if (text.isEmpty()) {
                    sorter_reserve.setRowFilter(null);
                } else {
                    sorter_reserve.setRowFilter(RowFilter.regexFilter("(?i)" + text));
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

        bg_walkinGender = new javax.swing.ButtonGroup();
        bg_walkinTime = new javax.swing.ButtonGroup();
        bg_IHgender = new javax.swing.ButtonGroup();
        bg_IHtime = new javax.swing.ButtonGroup();
        pnl_nav = new javax.swing.JPanel();
        btn_walkin = new javax.swing.JButton();
        btn_inhouse = new javax.swing.JButton();
        btn_logout = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btn_reserve = new javax.swing.JButton();
        pnl_header = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lbl_date = new javax.swing.JTextField();
        lbl_totalAvailSeatsLUNCH = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lbl_totalWalkInLUNCH = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        lbl_totalReserveLUNCH = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lbl_username = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        lbl_totalReserveDINNER = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        lbl_totalWalkInDINNER = new javax.swing.JLabel();
        lbl_totalAvailSeatsDINNER = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        pnl_reserve = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txt_membCPnum = new javax.swing.JTextField();
        txt_membLname = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txt_rsvVIPID = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        txt_membFname = new javax.swing.JTextField();
        txt_rsvID = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txt_rsvPax = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        btn_cancel = new javax.swing.JButton();
        txt_rsvRemarks = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        txt_rsvTime = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        search_reserve = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_reserve = new javax.swing.JTable();
        bg_today2 = new javax.swing.JLabel();
        pnl_inhouse = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txt_IHcp = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        btn_inhouseadd = new javax.swing.JButton();
        btn_inhouseedit = new javax.swing.JButton();
        btn_inhousedel = new javax.swing.JButton();
        dc_inhouse = new com.toedter.calendar.JDateChooser();
        rb_IHlunch = new javax.swing.JRadioButton();
        jLabel26 = new javax.swing.JLabel();
        txt_IHfname = new javax.swing.JTextField();
        rb_IHdinner = new javax.swing.JRadioButton();
        txt_IHlname = new javax.swing.JTextField();
        spn_inhousepax = new javax.swing.JSpinner();
        search_inhouse = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_inhouse = new javax.swing.JTable();
        bg_today3 = new javax.swing.JLabel();
        pnl_walkin = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txt_walkinCp = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        rb_walkinLunch = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        txt_walkinFname = new javax.swing.JTextField();
        btn_walkinadd = new javax.swing.JButton();
        btn_walkinedit = new javax.swing.JButton();
        btn_walkindel = new javax.swing.JButton();
        spn_walkinpax = new javax.swing.JSpinner();
        rb_walkinDinner = new javax.swing.JRadioButton();
        txt_walkinLname = new javax.swing.JTextField();
        search_walkin = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_walkin = new javax.swing.JTable();
        bg_today = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnl_nav.setBackground(new java.awt.Color(55, 77, 94));
        pnl_nav.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_walkin.setBackground(new java.awt.Color(55, 77, 94));
        btn_walkin.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        btn_walkin.setForeground(new java.awt.Color(255, 255, 255));
        btn_walkin.setText("WALK-IN");
        btn_walkin.setBorder(null);
        btn_walkin.setContentAreaFilled(false);
        btn_walkin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_walkin.setFocusPainted(false);
        btn_walkin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_walkinMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_walkinMouseExited(evt);
            }
        });
        btn_walkin.addActionListener(this::btn_walkinActionPerformed);
        pnl_nav.add(btn_walkin, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 140, 30));

        btn_inhouse.setBackground(new java.awt.Color(55, 77, 94));
        btn_inhouse.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        btn_inhouse.setForeground(new java.awt.Color(255, 255, 255));
        btn_inhouse.setText("IN-HOUSE");
        btn_inhouse.setBorder(null);
        btn_inhouse.setContentAreaFilled(false);
        btn_inhouse.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_inhouse.setFocusPainted(false);
        btn_inhouse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_inhouseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_inhouseMouseExited(evt);
            }
        });
        btn_inhouse.addActionListener(this::btn_inhouseActionPerformed);
        pnl_nav.add(btn_inhouse, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 100, 30));

        btn_logout.setBackground(new java.awt.Color(153, 0, 0));
        btn_logout.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        btn_logout.setForeground(new java.awt.Color(255, 255, 255));
        btn_logout.setText("LOG OUT");
        btn_logout.setBorder(null);
        btn_logout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_logout.addActionListener(this::btn_logoutActionPerformed);
        pnl_nav.add(btn_logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 511, 80, 30));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo.png"))); // NOI18N
        pnl_nav.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, -1, 80));

        btn_reserve.setBackground(new java.awt.Color(55, 77, 94));
        btn_reserve.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        btn_reserve.setForeground(new java.awt.Color(255, 255, 255));
        btn_reserve.setText("RESERVATIONS");
        btn_reserve.setBorder(null);
        btn_reserve.setContentAreaFilled(false);
        btn_reserve.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_reserve.setFocusPainted(false);
        btn_reserve.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_reserveMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_reserveMouseExited(evt);
            }
        });
        btn_reserve.addActionListener(this::btn_reserveActionPerformed);
        pnl_nav.add(btn_reserve, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 140, 30));

        getContentPane().add(pnl_nav, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 580));

        pnl_header.setBackground(new java.awt.Color(55, 91, 115));
        pnl_header.setMaximumSize(new java.awt.Dimension(681, 115));
        pnl_header.setPreferredSize(new java.awt.Dimension(681, 115));

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("FRONT DESK DASHBOARD");

        lbl_date.setBackground(new java.awt.Color(55, 91, 115));
        lbl_date.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lbl_date.setForeground(new java.awt.Color(255, 255, 255));
        lbl_date.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        lbl_date.setText("Date");
        lbl_date.setBorder(null);
        lbl_date.addActionListener(this::lbl_dateActionPerformed);

        lbl_totalAvailSeatsLUNCH.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lbl_totalAvailSeatsLUNCH.setForeground(new java.awt.Color(255, 255, 255));
        lbl_totalAvailSeatsLUNCH.setText("5");

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Available Seats:");

        lbl_totalWalkInLUNCH.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lbl_totalWalkInLUNCH.setForeground(new java.awt.Color(255, 255, 255));
        lbl_totalWalkInLUNCH.setText("5");

        jLabel15.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Walk-in:");

        lbl_totalReserveLUNCH.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lbl_totalReserveLUNCH.setForeground(new java.awt.Color(255, 255, 255));
        lbl_totalReserveLUNCH.setText("5");

        jLabel14.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Reserved:");

        lbl_username.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        lbl_username.setForeground(new java.awt.Color(255, 255, 255));
        lbl_username.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_username.setText("Delos Santos, Rhianne Leigh Anne");

        jLabel35.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText("LUNCH");

        jLabel36.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel36.setText("DINNER");

        jLabel37.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("Reserved:");

        lbl_totalReserveDINNER.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lbl_totalReserveDINNER.setForeground(new java.awt.Color(255, 255, 255));
        lbl_totalReserveDINNER.setText("5");

        jLabel38.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("Walk-in:");

        lbl_totalWalkInDINNER.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lbl_totalWalkInDINNER.setForeground(new java.awt.Color(255, 255, 255));
        lbl_totalWalkInDINNER.setText("5");

        lbl_totalAvailSeatsDINNER.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lbl_totalAvailSeatsDINNER.setForeground(new java.awt.Color(255, 255, 255));
        lbl_totalAvailSeatsDINNER.setText("5");

        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Available Seats:");

        javax.swing.GroupLayout pnl_headerLayout = new javax.swing.GroupLayout(pnl_header);
        pnl_header.setLayout(pnl_headerLayout);
        pnl_headerLayout.setHorizontalGroup(
            pnl_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_headerLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(pnl_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addGroup(pnl_headerLayout.createSequentialGroup()
                        .addGroup(pnl_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel35)
                            .addComponent(jLabel36))
                        .addGap(18, 18, 18)
                        .addGroup(pnl_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_totalReserveLUNCH, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_totalReserveDINNER, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnl_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(1, 1, 1)))
                .addGroup(pnl_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_headerLayout.createSequentialGroup()
                        .addGap(142, 142, 142)
                        .addComponent(lbl_username, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_headerLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(pnl_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_headerLayout.createSequentialGroup()
                                .addComponent(lbl_totalWalkInLUNCH, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_totalAvailSeatsLUNCH, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnl_headerLayout.createSequentialGroup()
                                .addComponent(lbl_totalWalkInDINNER, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_totalAvailSeatsDINNER, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 115, Short.MAX_VALUE)
                        .addComponent(lbl_date, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnl_headerLayout.setVerticalGroup(
            pnl_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_headerLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(pnl_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_headerLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_totalReserveLUNCH)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_totalWalkInLUNCH, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(lbl_totalAvailSeatsLUNCH, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel37)
                            .addComponent(jLabel36)
                            .addComponent(lbl_totalReserveDINNER)
                            .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_totalWalkInDINNER)
                            .addComponent(jLabel7)
                            .addComponent(lbl_totalAvailSeatsDINNER, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnl_headerLayout.createSequentialGroup()
                        .addComponent(lbl_username)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        getContentPane().add(pnl_header, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 740, -1));

        pnl_reserve.setForeground(new java.awt.Color(202, 199, 199));
        pnl_reserve.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Century Gothic", 1, 30)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(55, 77, 94));
        jLabel10.setText("TODAY'S RESERVATIONS");
        pnl_reserve.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, 50));

        jLabel18.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(55, 77, 94));
        jLabel18.setText("Search:");
        pnl_reserve.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 40, 60, 20));

        jLabel29.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(55, 77, 94));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel29.setText("CP Num:");
        pnl_reserve.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 410, 60, 30));

        txt_membCPnum.setEditable(false);
        txt_membCPnum.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_membCPnum.setFocusable(false);
        txt_membCPnum.addActionListener(this::txt_membCPnumNew_tableActionPerformed);
        pnl_reserve.add(txt_membCPnum, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 410, 140, 30));

        txt_membLname.setEditable(false);
        txt_membLname.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_membLname.setFocusable(false);
        txt_membLname.addActionListener(this::txt_membLnameNew_tableActionPerformed);
        pnl_reserve.add(txt_membLname, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 380, 140, 30));

        jLabel27.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(55, 77, 94));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel27.setText("Last Name:");
        pnl_reserve.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 380, 70, 30));

        jLabel30.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(55, 77, 94));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel30.setText("First Name:");
        pnl_reserve.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 350, 70, 30));

        txt_rsvVIPID.setEditable(false);
        txt_rsvVIPID.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_rsvVIPID.setFocusable(false);
        txt_rsvVIPID.addActionListener(this::txt_rsvVIPIDNew_tableActionPerformed);
        pnl_reserve.add(txt_rsvVIPID, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 380, 90, 30));

        jLabel31.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(55, 77, 94));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel31.setText("VIP ID:  ");
        pnl_reserve.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, 50, 30));

        txt_membFname.setEditable(false);
        txt_membFname.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_membFname.setFocusable(false);
        txt_membFname.addActionListener(this::txt_membFnameNew_tableActionPerformed);
        pnl_reserve.add(txt_membFname, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 350, 140, 30));

        txt_rsvID.setEditable(false);
        txt_rsvID.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_rsvID.setFocusable(false);
        txt_rsvID.addActionListener(this::txt_rsvIDNew_tableActionPerformed);
        pnl_reserve.add(txt_rsvID, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 350, 90, 30));

        jLabel21.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(55, 77, 94));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("ID:  ");
        pnl_reserve.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, 50, 30));

        txt_rsvPax.setEditable(false);
        txt_rsvPax.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_rsvPax.setFocusable(false);
        txt_rsvPax.addActionListener(this::txt_rsvPaxNew_tableActionPerformed);
        pnl_reserve.add(txt_rsvPax, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 380, 110, 30));

        jLabel34.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(55, 77, 94));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel34.setText("Pax:");
        pnl_reserve.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 380, 40, 30));

        btn_cancel.setBackground(new java.awt.Color(55, 91, 115));
        btn_cancel.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        btn_cancel.setForeground(new java.awt.Color(255, 255, 255));
        btn_cancel.setText("CANCEL RESERVATION");
        btn_cancel.setBorder(null);
        btn_cancel.addActionListener(this::btn_cancelActionPerformed);
        pnl_reserve.add(btn_cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 350, 150, 30));

        txt_rsvRemarks.setEditable(false);
        txt_rsvRemarks.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_rsvRemarks.setFocusable(false);
        txt_rsvRemarks.addActionListener(this::txt_rsvRemarksNew_tableActionPerformed);
        pnl_reserve.add(txt_rsvRemarks, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 410, 90, 30));

        jLabel33.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(55, 77, 94));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel33.setText("Remarks:  ");
        pnl_reserve.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 70, 30));

        txt_rsvTime.setEditable(false);
        txt_rsvTime.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_rsvTime.setFocusable(false);
        txt_rsvTime.addActionListener(this::txt_rsvTimeNew_tableActionPerformed);
        pnl_reserve.add(txt_rsvTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 350, 110, 30));

        jLabel32.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(55, 77, 94));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel32.setText("Time:");
        pnl_reserve.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 350, 50, 30));

        search_reserve.addActionListener(this::search_reserveActionPerformed);
        search_reserve.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_reserveKeyReleased(evt);
            }
        });
        pnl_reserve.add(search_reserve, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 40, 190, -1));

        jScrollPane3.setForeground(new java.awt.Color(55, 77, 94));

        tbl_reserve.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        tbl_reserve.setForeground(new java.awt.Color(55, 77, 94));
        tbl_reserve.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "VIP_ID", "F_NAME", "L_NAME", "CP_NUM", "TIME", "PAX", "REMARKS"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
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
        tbl_reserve.setOpaque(false);
        jScrollPane3.setViewportView(tbl_reserve);
        if (tbl_reserve.getColumnModel().getColumnCount() > 0) {
            tbl_reserve.getColumnModel().getColumn(0).setResizable(false);
            tbl_reserve.getColumnModel().getColumn(1).setResizable(false);
            tbl_reserve.getColumnModel().getColumn(2).setResizable(false);
            tbl_reserve.getColumnModel().getColumn(3).setResizable(false);
            tbl_reserve.getColumnModel().getColumn(4).setResizable(false);
        }

        pnl_reserve.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 720, 250));

        bg_today2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bgfd.jpg"))); // NOI18N
        bg_today2.setText("Today");
        pnl_reserve.add(bg_today2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 470));

        getContentPane().add(pnl_reserve, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 740, 470));

        pnl_inhouse.setForeground(new java.awt.Color(202, 199, 199));
        pnl_inhouse.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Century Gothic", 1, 30)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(55, 77, 94));
        jLabel8.setText("IN HOUSE RESERVATIONS");
        pnl_inhouse.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, 40));

        jLabel17.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(55, 77, 94));
        jLabel17.setText("Search:");
        pnl_inhouse.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 50, 60, 20));

        jLabel22.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(55, 77, 94));
        jLabel22.setText("Last Name:");
        pnl_inhouse.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 200, 110, -1));

        jLabel23.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(55, 77, 94));
        jLabel23.setText("Pax:");
        pnl_inhouse.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 350, 30, -1));

        txt_IHcp.addActionListener(this::txt_IHcpActionPerformed);
        txt_IHcp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_IHcpKeyReleased(evt);
            }
        });
        pnl_inhouse.add(txt_IHcp, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 270, 160, 30));

        jLabel24.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(55, 77, 94));
        jLabel24.setText("Time:");
        pnl_inhouse.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 306, -1, 20));

        jLabel25.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(55, 77, 94));
        jLabel25.setText("CP#:");
        pnl_inhouse.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 250, 70, 20));

        jLabel28.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(55, 77, 94));
        jLabel28.setText("Date:");
        pnl_inhouse.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 80, 40, -1));

        btn_inhouseadd.setBackground(new java.awt.Color(255, 255, 255));
        btn_inhouseadd.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btn_inhouseadd.setForeground(new java.awt.Color(65, 93, 120));
        btn_inhouseadd.setText("Add");
        btn_inhouseadd.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(65, 93, 120), 2, true));
        btn_inhouseadd.setContentAreaFilled(false);
        btn_inhouseadd.setFocusPainted(false);
        btn_inhouseadd.addActionListener(this::btn_inhouseaddAssign_ButtonActionPerformed);
        pnl_inhouse.add(btn_inhouseadd, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 410, 50, 30));

        btn_inhouseedit.setBackground(new java.awt.Color(255, 255, 255));
        btn_inhouseedit.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btn_inhouseedit.setForeground(new java.awt.Color(65, 93, 120));
        btn_inhouseedit.setText("Edit");
        btn_inhouseedit.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(65, 93, 120), 2, true));
        btn_inhouseedit.setContentAreaFilled(false);
        btn_inhouseedit.setFocusPainted(false);
        btn_inhouseedit.addActionListener(this::btn_inhouseeditAssign_ButtonActionPerformed);
        pnl_inhouse.add(btn_inhouseedit, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 410, 50, 30));

        btn_inhousedel.setBackground(new java.awt.Color(255, 255, 255));
        btn_inhousedel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btn_inhousedel.setForeground(new java.awt.Color(65, 93, 120));
        btn_inhousedel.setText("Delete");
        btn_inhousedel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(65, 93, 120), 2, true));
        btn_inhousedel.setContentAreaFilled(false);
        btn_inhousedel.setFocusPainted(false);
        btn_inhousedel.setFocusable(false);
        btn_inhousedel.setRequestFocusEnabled(false);
        btn_inhousedel.addActionListener(this::btn_inhousedelRemove_buttonActionPerformed);
        pnl_inhouse.add(btn_inhousedel, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 410, 60, 30));
        pnl_inhouse.add(dc_inhouse, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 100, 160, 30));

        rb_IHlunch.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        rb_IHlunch.setForeground(new java.awt.Color(55, 77, 94));
        rb_IHlunch.setText("Lunch");
        rb_IHlunch.addActionListener(this::rb_IHlunchActionPerformed);
        pnl_inhouse.add(rb_IHlunch, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 321, -1, 30));

        jLabel26.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(55, 77, 94));
        jLabel26.setText("First Name:");
        pnl_inhouse.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(573, 140, 70, -1));

        txt_IHfname.addActionListener(this::txt_IHfnameActionPerformed);
        txt_IHfname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_IHfnameKeyReleased(evt);
            }
        });
        pnl_inhouse.add(txt_IHfname, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 160, 160, 30));

        rb_IHdinner.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        rb_IHdinner.setForeground(new java.awt.Color(55, 77, 94));
        rb_IHdinner.setText("Dinner");
        pnl_inhouse.add(rb_IHdinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 321, -1, 30));

        txt_IHlname.addActionListener(this::txt_IHlnameActionPerformed);
        txt_IHlname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_IHlnameKeyReleased(evt);
            }
        });
        pnl_inhouse.add(txt_IHlname, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 220, 160, 30));

        spn_inhousepax.setModel(new javax.swing.SpinnerNumberModel(1, null, 100, 1));
        pnl_inhouse.add(spn_inhousepax, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 370, 100, 30));

        search_inhouse.addActionListener(this::search_inhouseActionPerformed);
        search_inhouse.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_inhouseKeyReleased(evt);
            }
        });
        pnl_inhouse.add(search_inhouse, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 50, 180, -1));

        jScrollPane4.setForeground(new java.awt.Color(55, 77, 94));

        tbl_inhouse.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        tbl_inhouse.setForeground(new java.awt.Color(55, 77, 94));
        tbl_inhouse.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IR_ID", "DATE", "F_NAME", "L_NAME", "TIME", "PAX"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
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
        tbl_inhouse.setOpaque(false);
        jScrollPane4.setViewportView(tbl_inhouse);
        if (tbl_inhouse.getColumnModel().getColumnCount() > 0) {
            tbl_inhouse.getColumnModel().getColumn(0).setResizable(false);
            tbl_inhouse.getColumnModel().getColumn(1).setResizable(false);
            tbl_inhouse.getColumnModel().getColumn(2).setResizable(false);
            tbl_inhouse.getColumnModel().getColumn(3).setResizable(false);
            tbl_inhouse.getColumnModel().getColumn(4).setResizable(false);
            tbl_inhouse.getColumnModel().getColumn(5).setResizable(false);
        }

        pnl_inhouse.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 550, 360));

        bg_today3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bgfd.jpg"))); // NOI18N
        bg_today3.setText("Today");
        pnl_inhouse.add(bg_today3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 470));

        getContentPane().add(pnl_inhouse, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 740, 470));

        pnl_walkin.setForeground(new java.awt.Color(202, 199, 199));
        pnl_walkin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 30)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(55, 77, 94));
        jLabel4.setText("TODAY'S WALK-INS");
        pnl_walkin.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, 40));

        jLabel16.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(55, 77, 94));
        jLabel16.setText("Search:");
        pnl_walkin.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 40, 60, 20));

        jLabel11.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(55, 77, 94));
        jLabel11.setText("Last Name:");
        pnl_walkin.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 160, 110, -1));

        jLabel20.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(55, 77, 94));
        jLabel20.setText("Pax:");
        pnl_walkin.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 340, -1, -1));

        txt_walkinCp.addActionListener(this::txt_walkinCpActionPerformed);
        txt_walkinCp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_walkinCpKeyReleased(evt);
            }
        });
        pnl_walkin.add(txt_walkinCp, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 250, 160, 30));

        jLabel19.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(55, 77, 94));
        jLabel19.setText("Time:");
        pnl_walkin.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 290, -1, -1));

        jLabel12.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(55, 77, 94));
        jLabel12.setText("CP Num:");
        pnl_walkin.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 230, 80, -1));

        rb_walkinLunch.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        rb_walkinLunch.setForeground(new java.awt.Color(65, 93, 120));
        rb_walkinLunch.setText("Lunch");
        rb_walkinLunch.addActionListener(this::rb_walkinLunchActionPerformed);
        pnl_walkin.add(rb_walkinLunch, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 310, -1, -1));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(55, 77, 94));
        jLabel1.setText("First Name:");
        pnl_walkin.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 90, 110, -1));

        txt_walkinFname.addActionListener(this::txt_walkinFnameActionPerformed);
        txt_walkinFname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_walkinFnameKeyReleased(evt);
            }
        });
        pnl_walkin.add(txt_walkinFname, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 110, 160, 30));

        btn_walkinadd.setBackground(new java.awt.Color(255, 255, 255));
        btn_walkinadd.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btn_walkinadd.setForeground(new java.awt.Color(65, 93, 120));
        btn_walkinadd.setText("Add");
        btn_walkinadd.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(65, 93, 120), 2, true));
        btn_walkinadd.setContentAreaFilled(false);
        btn_walkinadd.setFocusPainted(false);
        btn_walkinadd.addActionListener(this::btn_walkinaddAssign_ButtonActionPerformed);
        pnl_walkin.add(btn_walkinadd, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 400, 50, 30));

        btn_walkinedit.setBackground(new java.awt.Color(255, 255, 255));
        btn_walkinedit.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btn_walkinedit.setForeground(new java.awt.Color(65, 93, 120));
        btn_walkinedit.setText("Edit");
        btn_walkinedit.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(65, 93, 120), 2, true));
        btn_walkinedit.setContentAreaFilled(false);
        btn_walkinedit.setFocusPainted(false);
        btn_walkinedit.addActionListener(this::btn_walkineditAssign_ButtonActionPerformed);
        pnl_walkin.add(btn_walkinedit, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 400, 50, 30));

        btn_walkindel.setBackground(new java.awt.Color(255, 255, 255));
        btn_walkindel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btn_walkindel.setForeground(new java.awt.Color(65, 93, 120));
        btn_walkindel.setText("Delete");
        btn_walkindel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(65, 93, 120), 2, true));
        btn_walkindel.setContentAreaFilled(false);
        btn_walkindel.setFocusPainted(false);
        btn_walkindel.setFocusable(false);
        btn_walkindel.setRequestFocusEnabled(false);
        btn_walkindel.addActionListener(this::btn_walkindelRemove_buttonActionPerformed);
        pnl_walkin.add(btn_walkindel, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 400, 60, 30));

        spn_walkinpax.setModel(new javax.swing.SpinnerNumberModel(1, null, 100, 1));
        pnl_walkin.add(spn_walkinpax, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 360, 100, 30));

        rb_walkinDinner.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        rb_walkinDinner.setForeground(new java.awt.Color(65, 93, 120));
        rb_walkinDinner.setText("Dinner");
        pnl_walkin.add(rb_walkinDinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 310, -1, -1));

        txt_walkinLname.addActionListener(this::txt_walkinLnameActionPerformed);
        txt_walkinLname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_walkinLnameKeyReleased(evt);
            }
        });
        pnl_walkin.add(txt_walkinLname, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 180, 160, 30));

        search_walkin.addActionListener(this::search_walkinActionPerformed);
        search_walkin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_walkinKeyReleased(evt);
            }
        });
        pnl_walkin.add(search_walkin, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 40, 180, -1));

        jScrollPane1.setForeground(new java.awt.Color(55, 77, 94));

        tbl_walkin.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        tbl_walkin.setForeground(new java.awt.Color(55, 77, 94));
        tbl_walkin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "WI_ID", "F_NAME", "L_NAME", "CP_NUM", "TIME", "PAX"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
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
        tbl_walkin.setOpaque(false);
        jScrollPane1.setViewportView(tbl_walkin);
        if (tbl_walkin.getColumnModel().getColumnCount() > 0) {
            tbl_walkin.getColumnModel().getColumn(0).setResizable(false);
            tbl_walkin.getColumnModel().getColumn(1).setResizable(false);
            tbl_walkin.getColumnModel().getColumn(2).setResizable(false);
            tbl_walkin.getColumnModel().getColumn(3).setResizable(false);
            tbl_walkin.getColumnModel().getColumn(4).setResizable(false);
            tbl_walkin.getColumnModel().getColumn(5).setResizable(false);
        }

        pnl_walkin.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 550, 350));

        bg_today.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bgfd.jpg"))); // NOI18N
        bg_today.setText("Today");
        pnl_walkin.add(bg_today, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 470));

        jLabel5.setText("First Name:");
        pnl_walkin.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 80, -1, -1));

        getContentPane().add(pnl_walkin, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 740, 470));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_walkinMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_walkinMouseEntered
    if (!btn_walkin.getForeground().equals(new Color(255, 200, 120))) {
        btn_walkin.setForeground(new Color(255, 200, 120));
    }
    btn_walkin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));       
    }//GEN-LAST:event_btn_walkinMouseEntered

    private void btn_walkinMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_walkinMouseExited
    if (!pnl_walkin.isVisible()) {  
        btn_walkin.setForeground(Color.WHITE);
    }       // TODO add your handling code here:
    }//GEN-LAST:event_btn_walkinMouseExited

    private void search_walkinKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_walkinKeyReleased
    DefaultTableModel model = (DefaultTableModel) tbl_walkin.getModel();
    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
    tbl_walkin.setRowSorter(sorter);

    String text = search_walkin.getText();
    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));   
    }//GEN-LAST:event_search_walkinKeyReleased

    private void search_walkinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_walkinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_search_walkinActionPerformed

    private void btn_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_logoutActionPerformed
      int choice = JOptionPane.showConfirmDialog(null, "Log out of your account?", "Confirm Log Out", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            UserSession.loggedInEmail = null;
            new Customer_Login().setVisible(true);
            this.dispose();

        } else if (choice == JOptionPane.NO_OPTION) {
            return;
        }
    }//GEN-LAST:event_btn_logoutActionPerformed

    private void btn_walkinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_walkinActionPerformed
       
        pnl_walkin.setVisible(true);
        pnl_reserve.setVisible(false);
        pnl_inhouse.setVisible(false);
        
        btn_walkin.setForeground(new Color(255, 200, 120));  
        btn_inhouse.setForeground(Color.WHITE);           
        btn_reserve.setForeground(Color.WHITE);
    }//GEN-LAST:event_btn_walkinActionPerformed

    private void btn_inhouseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_inhouseActionPerformed
        pnl_walkin.setVisible(false);
        pnl_reserve.setVisible(false);
        pnl_inhouse.setVisible(true);
        

        btn_inhouse.setForeground(new Color(255, 200, 120));  
        btn_walkin.setForeground(Color.WHITE);          
        btn_reserve.setForeground(Color.WHITE);
    }//GEN-LAST:event_btn_inhouseActionPerformed

    private void btn_inhouseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_inhouseMouseExited
    if (!pnl_inhouse.isVisible()) {  
        btn_inhouse.setForeground(Color.WHITE);
    }  
    }//GEN-LAST:event_btn_inhouseMouseExited

    private void btn_inhouseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_inhouseMouseEntered
    if (!btn_inhouse.getForeground().equals(new Color(255, 200, 120))) {
        btn_inhouse.setForeground(new Color(255, 200, 120));
    }
    btn_inhouse.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_btn_inhouseMouseEntered

    private void lbl_dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lbl_dateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_dateActionPerformed

    private void btn_reserveMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_reserveMouseEntered
    if (!btn_reserve.getForeground().equals(new Color(255, 200, 120))) {
        btn_reserve.setForeground(new Color(255, 200, 120));
    }
    btn_reserve.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));           
    }//GEN-LAST:event_btn_reserveMouseEntered

    private void btn_reserveMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_reserveMouseExited
    if (!pnl_reserve.isVisible()) {  
        btn_reserve.setForeground(Color.WHITE);
    }       
    }//GEN-LAST:event_btn_reserveMouseExited

    private void btn_reserveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_reserveActionPerformed
        pnl_reserve.setVisible(true);
        pnl_walkin.setVisible(false);
        pnl_inhouse.setVisible(false);
        pnl_reserve.setForeground(new Color(255, 200, 120));  
        btn_inhouse.setForeground(Color.WHITE);           
        pnl_walkin.setForeground(Color.WHITE);
    }//GEN-LAST:event_btn_reserveActionPerformed

    private void btn_cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelActionPerformed
        int selectedRow = tbl_reserve.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a reservation from the table first!");
            return;
        }

        int modelRow = tbl_reserve.convertRowIndexToModel(selectedRow);
        DefaultTableModel model = (DefaultTableModel) tbl_reserve.getModel();

        String id = model.getValueAt(modelRow, 0).toString();
        Object status = model.getValueAt(modelRow, 7); 

        if (status != null && status.toString().equalsIgnoreCase("Cancelled")) {
            JOptionPane.showMessageDialog(this, "This reservation is already cancelled!");
            return; 
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to cancel reservation " + id + "?", "Confirm Cancel", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            Connect db = new Connect();
            db.DoConnect();
            
            String sql = "";
            if (id.startsWith("IR")) {
                sql = "UPDATE DBHOUSE.INHOUSERESERVATIONS SET REMARKS = 'Cancelled' WHERE IR_ID = ?";
            } else if (id.startsWith("OR")) {
                sql = "UPDATE DBHOUSE.ONLINERESERVATIONS SET REMARKS = 'Cancelled' WHERE OR_ID = ?";
            } else {
                JOptionPane.showMessageDialog(this, "Unknown ID format!");
                return;
            }
            
            try (PreparedStatement pst = db.con.prepareStatement(sql)) {
                pst.setString(1, id);
                pst.executeUpdate();
                
                JOptionPane.showMessageDialog(this, "Reservation cancelled successfully.");
                
                txt_rsvID.setText(""); 
                txt_rsvVIPID.setText(""); 
                txt_membFname.setText("");
                txt_membLname.setText(""); 
                txt_membCPnum.setText(""); 
                txt_rsvTime.setText("");
                txt_rsvPax.setText(""); 
                txt_rsvRemarks.setText("");
                
                loadReserveTable();
                updateNavbarStats(); 
                db.con.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btn_cancelActionPerformed

    private void txt_walkinFnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_walkinFnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_walkinFnameActionPerformed

    private void txt_walkinFnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_walkinFnameKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_walkinFnameKeyReleased

    private void txt_walkinLnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_walkinLnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_walkinLnameActionPerformed

    private void txt_walkinLnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_walkinLnameKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_walkinLnameKeyReleased

    private void rb_walkinLunchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_walkinLunchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_walkinLunchActionPerformed

    private void txt_walkinCpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_walkinCpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_walkinCpActionPerformed

    private void txt_walkinCpKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_walkinCpKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_walkinCpKeyReleased

    private void txt_IHcpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_IHcpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_IHcpActionPerformed

    private void txt_IHcpKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_IHcpKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_IHcpKeyReleased

    private void rb_IHlunchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_IHlunchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_IHlunchActionPerformed

    private void txt_IHfnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_IHfnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_IHfnameActionPerformed

    private void txt_IHfnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_IHfnameKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_IHfnameKeyReleased

    private void txt_IHlnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_IHlnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_IHlnameActionPerformed

    private void txt_IHlnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_IHlnameKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_IHlnameKeyReleased

    private void search_inhouseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_inhouseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_search_inhouseActionPerformed

    private void search_inhouseKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_inhouseKeyReleased
    DefaultTableModel model = (DefaultTableModel) tbl_inhouse.getModel();
    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
    tbl_inhouse.setRowSorter(sorter);

    String text = search_inhouse.getText();
    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));      // TODO add your handling code here:
    }//GEN-LAST:event_search_inhouseKeyReleased

    private void search_reserveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_reserveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_search_reserveActionPerformed

    private void search_reserveKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_reserveKeyReleased
        DefaultTableModel model = (DefaultTableModel) tbl_reserve.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tbl_reserve.setRowSorter(sorter);

        String text = search_reserve.getText();
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));          
    }//GEN-LAST:event_search_reserveKeyReleased

    private void btn_inhouseaddAssign_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_inhouseaddAssign_ButtonActionPerformed
        addInhouse();
        updateNavbarStats();
    }//GEN-LAST:event_btn_inhouseaddAssign_ButtonActionPerformed

    private void btn_inhouseeditAssign_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_inhouseeditAssign_ButtonActionPerformed
         editInhouse();
    }//GEN-LAST:event_btn_inhouseeditAssign_ButtonActionPerformed

    private void btn_inhousedelRemove_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_inhousedelRemove_buttonActionPerformed
        deleteInhouse();
        updateNavbarStats();
    }//GEN-LAST:event_btn_inhousedelRemove_buttonActionPerformed

    private void btn_walkinaddAssign_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_walkinaddAssign_ButtonActionPerformed
        addWalkin();
        updateNavbarStats();
    }//GEN-LAST:event_btn_walkinaddAssign_ButtonActionPerformed

    private void btn_walkineditAssign_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_walkineditAssign_ButtonActionPerformed
       editWalkin();
    }//GEN-LAST:event_btn_walkineditAssign_ButtonActionPerformed

    private void btn_walkindelRemove_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_walkindelRemove_buttonActionPerformed
       deleteWalkin();
       updateNavbarStats();
    }//GEN-LAST:event_btn_walkindelRemove_buttonActionPerformed

    private void txt_membCPnumNew_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_membCPnumNew_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_membCPnumNew_tableActionPerformed

    private void txt_membLnameNew_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_membLnameNew_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_membLnameNew_tableActionPerformed

    private void txt_membFnameNew_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_membFnameNew_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_membFnameNew_tableActionPerformed

    private void txt_rsvIDNew_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_rsvIDNew_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rsvIDNew_tableActionPerformed

    private void txt_rsvVIPIDNew_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_rsvVIPIDNew_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rsvVIPIDNew_tableActionPerformed

    private void txt_rsvTimeNew_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_rsvTimeNew_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rsvTimeNew_tableActionPerformed

    private void txt_rsvRemarksNew_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_rsvRemarksNew_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rsvRemarksNew_tableActionPerformed

    private void txt_rsvPaxNew_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_rsvPaxNew_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rsvPaxNew_tableActionPerformed
    
    private void updateNavbarStats() {
        int walkinPaxLunch = 0;
        int walkinPaxDinner = 0;
        int reservePaxLunch = 0;
        int reservePaxDinner = 0;

        for (int i = 0; i < tbl_walkin.getRowCount(); i++) {
            Object timeObj = tbl_walkin.getValueAt(i, 4);
            Object paxObj = tbl_walkin.getValueAt(i, 5); 
            
            if (paxObj != null && timeObj != null) {
                try {
                    int pax = Integer.parseInt(paxObj.toString());
                    String time = timeObj.toString().trim();
                    
                    if (time.equalsIgnoreCase("Lunch")) {
                        walkinPaxLunch += pax;
                    } else if (time.equalsIgnoreCase("Dinner")) {
                        walkinPaxDinner += pax;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Walkin Pax Error at row " + i + ": " + paxObj);
                }
            }
        }

        for (int i = 0; i < tbl_reserve.getRowCount(); i++) {
            Object statusObj = tbl_reserve.getValueAt(i, 7); 
            String status = (statusObj != null) ? statusObj.toString() : "";

            if (!status.equalsIgnoreCase("Cancelled")) {
                Object timeObj = tbl_reserve.getValueAt(i, 5);
                Object paxObj = tbl_reserve.getValueAt(i, 6); 
                
                if (paxObj != null && timeObj != null) {
                    try {
                        int pax = Integer.parseInt(paxObj.toString());
                        String time = timeObj.toString().trim();
                        
                        if (time.equalsIgnoreCase("Lunch")) {
                            reservePaxLunch += pax;
                        } else if (time.equalsIgnoreCase("Dinner")) {
                            reservePaxDinner += pax;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Reserve Pax Error at row " + i + ": " + paxObj);
                    }
                }
            }
        }

        int totalSeatsPerShift = 100;
        
        int occupiedLunch = walkinPaxLunch + reservePaxLunch;
        int availableLunch = totalSeatsPerShift - occupiedLunch;
        
        int occupiedDinner = walkinPaxDinner + reservePaxDinner;
        int availableDinner = totalSeatsPerShift - occupiedDinner;

        lbl_totalWalkInLUNCH.setText(String.valueOf(walkinPaxLunch));
        lbl_totalReserveLUNCH.setText(String.valueOf(reservePaxLunch));
        lbl_totalAvailSeatsLUNCH.setText(String.valueOf(Math.max(0, availableLunch)));
        
        lbl_totalWalkInDINNER.setText(String.valueOf(walkinPaxDinner));
        lbl_totalReserveDINNER.setText(String.valueOf(reservePaxDinner));
        lbl_totalAvailSeatsDINNER.setText(String.valueOf(Math.max(0, availableDinner)));
    }
    private void makeFlatButton(javax.swing.JButton btn) {
        btn.setFocusPainted(false);
        btn.setBorder(null);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
}
    private String getSelectedTime() {
        if (rb_walkinLunch.isSelected()) return "LUNCH";
        if (rb_walkinDinner.isSelected()) return "DINNER";
            return null;
    }
    private String getSelectedTimeIH() {
        if (rb_IHlunch.isSelected()) return "LUNCH";
        if (rb_IHdinner.isSelected()) return "DINNER";
            return null;
    }
    private void setTextFieldLimit(javax.swing.JTextField textField, int limit, boolean numbersOnly) {
        javax.swing.text.AbstractDocument doc = (javax.swing.text.AbstractDocument) textField.getDocument();
        doc.setDocumentFilter(new javax.swing.text.DocumentFilter() {
            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, javax.swing.text.AttributeSet attrs) 
                    throws javax.swing.text.BadLocationException {

                if (numbersOnly && !text.matches("\\d*")) {
                    return; 
                }

                int currentLength = fb.getDocument().getLength();
                if ((currentLength + text.length() - length) <= limit) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }
    private void setLoggedInUserName() {
        String currentUser = UserSession.loggedInEmail; 
        if (currentUser == null || currentUser.isEmpty()) {
            lbl_username.setText("User");
            return;
        }
        Connect db = new Connect();
        db.DoConnect();
        String sql = "SELECT F_NAME, L_NAME FROM DBHOUSE.EMPACCOUNTS WHERE USERNAME = ?";
        
        try (PreparedStatement pst = db.con.prepareStatement(sql)) {
            pst.setString(1, currentUser);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                String firstName = rs.getString("F_NAME");
                String lastName = rs.getString("L_NAME");
                
                lbl_username.setText(firstName + " " + lastName);
            } else {
                lbl_username.setText("User");
            }
            db.con.close();
        } catch (SQLException e) {
            System.out.println("Error name: " + e.getMessage());
        }
    }
    
    private void loadWalkinTable() {
        DefaultTableModel model = (DefaultTableModel) tbl_walkin.getModel();
        model.setRowCount(0);
        Connect db = new Connect();
        db.DoConnect();
        
        String sql = "SELECT WI_ID, F_NAME, L_NAME, CP_NUM, D_TIME, PAX FROM DBHOUSE.WALKINDINE WHERE D_DATE = CURRENT_DATE";
        
        try (PreparedStatement pst = db.con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("WI_ID"), rs.getString("F_NAME"), rs.getString("L_NAME"),
                    rs.getString("CP_NUM"), rs.getString("D_TIME"), rs.getInt("PAX")
                });
            }
            db.con.close();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    private void addWalkin() {
        if (txt_walkinFname.getText().trim().isEmpty() || txt_walkinLname.getText().trim().isEmpty() || 
            txt_walkinCp.getText().trim().isEmpty() || getSelectedTime() == null) {
            JOptionPane.showMessageDialog(this, "Please fill out all fields before adding!", "Incomplete Fields", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Connect db = new Connect();
        db.DoConnect();
        
        String checkSql = "SELECT COUNT(*) FROM DBHOUSE.WALKINDINE WHERE F_NAME=? AND L_NAME=? AND D_DATE=CURRENT_DATE AND D_TIME=?";
        try (PreparedStatement checkPst = db.con.prepareStatement(checkSql)) {
            checkPst.setString(1, txt_walkinFname.getText().trim());
            checkPst.setString(2, txt_walkinLname.getText().trim());
            checkPst.setString(3, getSelectedTime());
            ResultSet rs = checkPst.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this, "This walk-in record already exists.", "Duplicate Record", JOptionPane.ERROR_MESSAGE);
                db.con.close();
                return; 
            }
        } catch (SQLException e) { e.printStackTrace(); }

        String sql = "INSERT INTO DBHOUSE.WALKINDINE (WI_ID, D_DATE, D_TIME, PAX, F_NAME, L_NAME, CP_NUM, REMARKS) VALUES (?, CURRENT_DATE, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pst = db.con.prepareStatement(sql)) {
            String id = getNextWalkinId(); 
            pst.setString(1, id);
            pst.setString(2, getSelectedTime());
            pst.setInt(3, (Integer) spn_walkinpax.getValue()); 
            pst.setString(4, txt_walkinFname.getText().trim());
            pst.setString(5, txt_walkinLname.getText().trim());
            pst.setString(6, txt_walkinCp.getText().trim());
            pst.setString(7, "Going"); 

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Walk-in successfully added.");
            loadWalkinTable(); 
            clearWalkinFields();
            db.con.close();
        } catch (SQLException e) { JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage()); }
    }
    private void editWalkin() {
        if (editingWalkinRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a record first.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (txt_walkinFname.getText().trim().isEmpty() || txt_walkinLname.getText().trim().isEmpty() || 
            txt_walkinCp.getText().trim().isEmpty() || getSelectedTime() == null) {
            JOptionPane.showMessageDialog(this, "Please fill out all fields.", "Incomplete Fields", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String id = tbl_walkin.getModel().getValueAt(editingWalkinRow, 0).toString();
        Connect db = new Connect();
        db.DoConnect();
        String sql = "UPDATE DBHOUSE.WALKINDINE SET D_TIME=?, PAX=?, F_NAME=?, L_NAME=?, CP_NUM=? WHERE WI_ID=?";

        try (PreparedStatement pst = db.con.prepareStatement(sql)) {
            pst.setString(1, getSelectedTime());
            pst.setInt(2, (Integer) spn_walkinpax.getValue()); 
            pst.setString(3, txt_walkinFname.getText().trim());
            pst.setString(4, txt_walkinLname.getText().trim());
            pst.setString(5, txt_walkinCp.getText().trim());
            pst.setString(6, id);

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Walk-in edited successfully.");
            loadWalkinTable();
            clearWalkinFields();
            db.con.close();
        } catch (SQLException e) { JOptionPane.showMessageDialog(this, "Update Error: " + e.getMessage()); }
    }
    private void deleteWalkin() {
        int viewRow = tbl_walkin.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a record first.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = tbl_walkin.convertRowIndexToModel(viewRow);
        String id = tbl_walkin.getModel().getValueAt(modelRow, 0).toString();
        
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete walk-in record " + id + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            Connect db = new Connect();
            db.DoConnect();
            try (PreparedStatement pst = db.con.prepareStatement("DELETE FROM DBHOUSE.WALKINDINE WHERE WI_ID=?")) {
                pst.setString(1, id);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Walk-in deleted.");
                loadWalkinTable();
                clearWalkinFields();
                db.con.close();
            } catch (SQLException e) { JOptionPane.showMessageDialog(this, "Delete Error: " + e.getMessage()); }
        }
    }
    private void clearWalkinFields() {
        txt_walkinFname.setText("");
        txt_walkinLname.setText("");
        txt_walkinCp.setText("");
        spn_walkinpax.setValue(1); 
        bg_walkinTime.clearSelection();
        tbl_walkin.clearSelection();
        editingWalkinRow = -1;
    }
    private String getNextWalkinId() {
        int nextId = 1;
        Connect db = new Connect();
        db.DoConnect();
        try (PreparedStatement pst = db.con.prepareStatement("SELECT MAX(CAST(SUBSTR(WI_ID, 3) AS INT)) FROM DBHOUSE.WALKINDINE");
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                nextId = rs.getInt(1) + 1;
            }
            db.con.close();
        } catch (SQLException e) { System.out.println("ID Error: " + e.getMessage()); }
        return String.format("WD%04d", nextId);
    }

    private void loadInhouseTable() {
        DefaultTableModel model = (DefaultTableModel) tbl_inhouse.getModel();
        model.setRowCount(0);
        Connect db = new Connect();
        db.DoConnect();
        
        String sql = "SELECT IR_ID, D_DATE, F_NAME, L_NAME, D_TIME, PAX FROM DBHOUSE.INHOUSERESERVATIONS WHERE DATE_BOOKED = CURRENT_DATE";
        
        try (PreparedStatement pst = db.con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("IR_ID"), 
                    rs.getDate("D_DATE"), 
                    rs.getString("F_NAME"),
                    rs.getString("L_NAME"), 
                    rs.getString("D_TIME"), 
                    rs.getInt("PAX")
                });
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }
    private void addInhouse() {
        if (dc_inhouse.getDate() == null || txt_IHfname.getText().trim().isEmpty() || 
            txt_IHlname.getText().trim().isEmpty() || txt_IHcp.getText().trim().isEmpty() || 
            getSelectedTimeIH() == null) {
            JOptionPane.showMessageDialog(this, "Please fill out all fields.", "Incomplete Fields", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Connect db = new Connect();
        db.DoConnect();
        
        String checkSql = "SELECT COUNT(*) FROM DBHOUSE.INHOUSERESERVATIONS WHERE F_NAME=? AND L_NAME=? AND D_DATE=? AND D_TIME=?";
        try (PreparedStatement checkPst = db.con.prepareStatement(checkSql)) {
            checkPst.setString(1, txt_IHfname.getText().trim());
            checkPst.setString(2, txt_IHlname.getText().trim());
            checkPst.setDate(3, new java.sql.Date(dc_inhouse.getDate().getTime()));
            checkPst.setString(4, getSelectedTimeIH());
            ResultSet rs = checkPst.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this, "This reservation already exists.", "Duplicate Record", JOptionPane.ERROR_MESSAGE);
                db.con.close();
                return; 
            }
        } catch (SQLException e) { e.printStackTrace(); }
        
        String sql = "INSERT INTO DBHOUSE.INHOUSERESERVATIONS (IR_ID, D_DATE, D_TIME, PAX, F_NAME, L_NAME, CP_NUM, REMARKS, DATE_BOOKED) VALUES (?,?,?,?,?,?,?,?, CURRENT_DATE)";

        try (PreparedStatement pst = db.con.prepareStatement(sql)) {
            String newId = getNextInhouseId();
            pst.setString(1, newId);
            pst.setDate(2, new java.sql.Date(dc_inhouse.getDate().getTime())); 
            pst.setString(3, getSelectedTimeIH());
            pst.setInt(4, (Integer) spn_inhousepax.getValue());
            pst.setString(5, txt_IHfname.getText().trim());
            pst.setString(6, txt_IHlname.getText().trim());
            pst.setString(7, txt_IHcp.getText().trim());
            pst.setString(8, "Going"); 

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Reservation successfully added.");
            loadInhouseTable();
            clearInhouseFields();
            updateNavbarStats(); 
        } catch (SQLException e) { JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage()); }
    }
    private void editInhouse() {
        if (editingInhouseRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row first.", "No Selection", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (dc_inhouse.getDate() == null || txt_IHfname.getText().trim().isEmpty() || 
            txt_IHlname.getText().trim().isEmpty() || txt_IHcp.getText().trim().isEmpty() || 
            getSelectedTimeIH() == null) {
            JOptionPane.showMessageDialog(this, "Please fill out all fields.", "Incomplete Fields", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String id = tbl_inhouse.getModel().getValueAt(editingInhouseRow, 0).toString();
        Connect db = new Connect();
        db.DoConnect();
        String sql = "UPDATE DBHOUSE.INHOUSERESERVATIONS SET D_DATE=?, D_TIME=?, PAX=?, F_NAME=?, L_NAME=?, CP_NUM=? WHERE IR_ID=?";

        try (PreparedStatement pst = db.con.prepareStatement(sql)) {
            pst.setDate(1, new java.sql.Date(dc_inhouse.getDate().getTime()));
            pst.setString(2, getSelectedTimeIH());
            pst.setInt(3, (Integer) spn_inhousepax.getValue());
            pst.setString(4, txt_IHfname.getText().trim());
            pst.setString(5, txt_IHlname.getText().trim());
            pst.setString(6, txt_IHcp.getText().trim());
            pst.setString(7, id);

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Reservation updated successfully!");
            loadInhouseTable();
            clearInhouseFields();
        } catch (SQLException e) { JOptionPane.showMessageDialog(this, "DB Error: " + e.getMessage()); }
    }
    private void deleteInhouse() {
        int viewRow = tbl_inhouse.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.", "No Selection", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int modelRow = tbl_inhouse.convertRowIndexToModel(viewRow);
        String id = tbl_inhouse.getModel().getValueAt(modelRow, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete reservation " + id + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Connect db = new Connect();
            db.DoConnect();
            try (PreparedStatement pst = db.con.prepareStatement("DELETE FROM DBHOUSE.INHOUSERESERVATIONS WHERE IR_ID=?")) {
                pst.setString(1, id);
                pst.executeUpdate();
                loadInhouseTable();
                clearInhouseFields();
                JOptionPane.showMessageDialog(this, "Reservation deleted.");
            } catch (SQLException e) { JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage()); }
        }
    }
    private void clearInhouseFields() {
        txt_IHfname.setText("");
        txt_IHlname.setText("");
        txt_IHcp.setText("");
        dc_inhouse.setDate(null);
        spn_inhousepax.setValue(1); 
        bg_IHtime.clearSelection();
        tbl_inhouse.clearSelection();
        editingInhouseRow = -1;
    }
    private String getNextInhouseId() {
        int nextId = 1;
        Connect db = new Connect();
        db.DoConnect();
        try (PreparedStatement pst = db.con.prepareStatement("SELECT MAX(CAST(SUBSTR(IR_ID, 3) AS INT)) FROM DBHOUSE.INHOUSERESERVATIONS");
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                nextId = rs.getInt(1) + 1;
            }
        } catch (SQLException e) { System.out.println("ID Error: " + e.getMessage()); }
        return String.format("IR%04d", nextId);
    }
    
    private void loadReserveTable() {
        DefaultTableModel model = (DefaultTableModel) tbl_reserve.getModel();
        model.setRowCount(0);
        Connect db = new Connect();
        db.DoConnect();
        
        String sql = 
            "SELECT IR_ID AS ID, 'N/A' AS VIP_ID, F_NAME, L_NAME, CP_NUM, D_TIME AS TIME, PAX, REMARKS " +
            "FROM DBHOUSE.INHOUSERESERVATIONS WHERE D_DATE = CURRENT_DATE " +
            "UNION ALL " +
            "SELECT o.OR_ID AS ID, o.VIP_ID, v.F_NAME, v.L_NAME, v.CP_NUM, o.D_TIME AS TIME, o.PAX, o.REMARKS " +
            "FROM DBHOUSE.ONLINERESERVATIONS o " +
            "JOIN DBHOUSE.VIPACCOUNTS v ON o.VIP_ID = v.VIP_ID " +
            "WHERE o.D_DATE = CURRENT_DATE";
                     
        try (PreparedStatement pst = db.con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("ID"),
                    rs.getString("VIP_ID"),
                    rs.getString("F_NAME"),
                    rs.getString("L_NAME"),
                    rs.getString("CP_NUM"),
                    rs.getString("TIME"),
                    rs.getInt("PAX"),
                    rs.getString("REMARKS")
                });
            }
            db.con.close();
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
    }
    private void clearReserveFields() {
        txt_rsvID.setText(""); 
        txt_rsvVIPID.setText(""); 
        txt_membFname.setText("");
        txt_membLname.setText(""); 
        txt_membCPnum.setText(""); 
        txt_rsvTime.setText("");
        txt_rsvPax.setText(""); 
        txt_rsvRemarks.setText("");
        tbl_reserve.clearSelection();
        editingRow = -1;
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
        java.awt.EventQueue.invokeLater(() -> new FrontDesk(null).setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bg_IHgender;
    private javax.swing.ButtonGroup bg_IHtime;
    private javax.swing.JLabel bg_today;
    private javax.swing.JLabel bg_today2;
    private javax.swing.JLabel bg_today3;
    private javax.swing.ButtonGroup bg_walkinGender;
    private javax.swing.ButtonGroup bg_walkinTime;
    private javax.swing.JButton btn_cancel;
    private javax.swing.JButton btn_inhouse;
    private javax.swing.JButton btn_inhouseadd;
    private javax.swing.JButton btn_inhousedel;
    private javax.swing.JButton btn_inhouseedit;
    private javax.swing.JButton btn_logout;
    private javax.swing.JButton btn_reserve;
    private javax.swing.JButton btn_walkin;
    private javax.swing.JButton btn_walkinadd;
    private javax.swing.JButton btn_walkindel;
    private javax.swing.JButton btn_walkinedit;
    private com.toedter.calendar.JDateChooser dc_inhouse;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField lbl_date;
    private javax.swing.JLabel lbl_totalAvailSeatsDINNER;
    private javax.swing.JLabel lbl_totalAvailSeatsLUNCH;
    private javax.swing.JLabel lbl_totalReserveDINNER;
    private javax.swing.JLabel lbl_totalReserveLUNCH;
    private javax.swing.JLabel lbl_totalWalkInDINNER;
    private javax.swing.JLabel lbl_totalWalkInLUNCH;
    private javax.swing.JLabel lbl_username;
    private javax.swing.JPanel pnl_header;
    private javax.swing.JPanel pnl_inhouse;
    private javax.swing.JPanel pnl_nav;
    private javax.swing.JPanel pnl_reserve;
    private javax.swing.JPanel pnl_walkin;
    private javax.swing.JRadioButton rb_IHdinner;
    private javax.swing.JRadioButton rb_IHlunch;
    private javax.swing.JRadioButton rb_walkinDinner;
    private javax.swing.JRadioButton rb_walkinLunch;
    private javax.swing.JTextField search_inhouse;
    private javax.swing.JTextField search_reserve;
    private javax.swing.JTextField search_walkin;
    private javax.swing.JSpinner spn_inhousepax;
    private javax.swing.JSpinner spn_walkinpax;
    private javax.swing.JTable tbl_inhouse;
    private javax.swing.JTable tbl_reserve;
    private javax.swing.JTable tbl_walkin;
    private javax.swing.JTextField txt_IHcp;
    private javax.swing.JTextField txt_IHfname;
    private javax.swing.JTextField txt_IHlname;
    private javax.swing.JTextField txt_membCPnum;
    private javax.swing.JTextField txt_membFname;
    private javax.swing.JTextField txt_membLname;
    private javax.swing.JTextField txt_membVipId;
    private javax.swing.JTextField txt_membVipId1;
    private javax.swing.JTextField txt_membVipId2;
    private javax.swing.JTextField txt_rsvID;
    private javax.swing.JTextField txt_rsvPax;
    private javax.swing.JTextField txt_rsvRemarks;
    private javax.swing.JTextField txt_rsvTime;
    private javax.swing.JTextField txt_rsvVIPID;
    private javax.swing.JTextField txt_walkinCp;
    private javax.swing.JTextField txt_walkinFname;
    private javax.swing.JTextField txt_walkinLname;
    // End of variables declaration//GEN-END:variables
}

