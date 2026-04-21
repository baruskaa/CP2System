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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;
import java.util.HashMap;
import javax.swing.ButtonGroup;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author Ska
 */
public class Admin extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Admin.class.getName());
    private TableRowSorter<DefaultTableModel> sorter_today;
    private TableRowSorter<DefaultTableModel> sorter_dinner;
    private TableRowSorter<DefaultTableModel> sorter_history;
    private TableRowSorter<DefaultTableModel> sorter_upcom;
    private TableRowSorter<DefaultTableModel> sorter_emp;
    private TableRowSorter<DefaultTableModel> sorter_memb;
    private TableRowSorter<DefaultTableModel> sorter_reserve;
    private TableRowSorter<DefaultTableModel> sorter_walkin;
    private TableRowSorter<DefaultTableModel> sorter_inhouse;
    private TableRowSorter<DefaultTableModel> sorter_menuList;
    private java.io.File selectedMenuImage = null;
    private java.io.File selectedPromoImage = null;
    private DefaultTableModel getWalkInModel() {
    return (DefaultTableModel) tbl_walkin.getModel();
}
    private DefaultTableModel getInHouseModel() {
    return (DefaultTableModel) tbl_inhouse.getModel();
}
    private int editingInhouseRow = -1;
    private int editingWalkinRow = -1;
    private int editingRow = -1;
    private int editingMembRow = -1; 
    private int editingEmpRow = -1;
    private int editingMenuRow = -1;
    private int editingMenuItemId = -1;
    private int editingPromoRow = -1;
    private int editingPromoId = -1;
    
    
    public Admin() {
        initComponents();
        this.setLocationRelativeTo(null);
        
        
        makeFlatButton(btn_navLogout);
        makeFlatButton(btn_histGenerate);
        makeFlatButton(btn_upcomGenerate);
        makeFlatButton(btn_histReset);
        makeFlatButton(btn_seatsReset);
        makeFlatButton(btn_upcomReset);
        makeFlatButton(btn_cancel);
        makeFlatButton(btn_upcomCancel);
        makeFlatButton(btn_upcomConfirm);
        
        date_historyTo.setMaxSelectableDate(new java.util.Date());
        loadEmployeeTable();
        loadMemberTable();
        loadHistoryTable();
        loadUpcomTable();
        loadSeatTrackerTable();
        loadInhouseTable();
        loadWalkinTable();
        loadReserveTable();
        
        txt_dishName.setVisible(false);
        pnl_reserve.setVisible(true);
        pnl_today.setVisible(false);
        pnl_inhouse.setVisible(false);
        pnl_walkin.setVisible(false);
        pnl_history.setVisible(false);
        pnl_upcom.setVisible(false);
        pnl_emp.setVisible(false);
        pnl_memb.setVisible(false);
        pnl_menuedit.setVisible(false);
        pnl_promoedit.setVisible(false);
        btn_reserve.setForeground(new Color(255, 200, 120));
        
        // BUTTON GROUPS
        bg_walkinTime = new ButtonGroup();
        bg_walkinTime.add(rb_walkinLunch);
        bg_walkinTime.add(rb_walkinDinner);
        
        bg_IHtime = new ButtonGroup();
        bg_IHtime.add(rb_IHlunch);
        bg_IHtime.add(rb_IHdinner);
        
        buttonGroup1.add(rb_manager);
        buttonGroup1.add(rb_fdesk);

        //TABLE SORTERS
        
        sorter_walkin  = setupSorter(tbl_walkin);
        sorter_reserve = setupSorter(tbl_reserve);
        sorter_inhouse = setupSorter(tbl_inhouse);
        sorter_today   = setupSorter(tbl_seatsLunch);
        sorter_dinner  = setupSorter(tbl_seatsDinner);
        sorter_upcom   = setupSorter(tbl_upcom);
        sorter_emp     = setupSorter(tbl_emp);
        sorter_memb    = setupSorter(tbl_memb);
        sorter_menuList = setupSorter(tbl_menuList);
        
        loadSeatTrackerTable();

        DefaultTableModel model_history = (DefaultTableModel) tbl_history.getModel();
        
        sorter_history = new TableRowSorter<DefaultTableModel>(model_history) {
            @Override
            public void toggleSortOrder(int column) {
                super.toggleSortOrder(column); 
                
                java.util.List<? extends javax.swing.RowSorter.SortKey> currentKeys = getSortKeys();
                
                if (!currentKeys.isEmpty() && currentKeys.get(0).getColumn() == 1) {
                    java.util.List<javax.swing.RowSorter.SortKey> newKeys = new java.util.ArrayList<>();
                    
                    newKeys.add(currentKeys.get(0)); 
                    newKeys.add(new javax.swing.RowSorter.SortKey(5, javax.swing.SortOrder.ASCENDING)); 
                    
                    setSortKeys(newKeys);
                }
            }
        };
        tbl_history.setRowSorter(sorter_history);
        
        //TABLE CENTER ALIGNERS
        
        centerTableData(tbl_reserve, tbl_walkin, tbl_inhouse, tbl_seatsLunch, tbl_seatsDinner, tbl_history, tbl_upcom, tbl_emp, tbl_memb,tbl_menuList, tbl_menuList1);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tbl_upcom.getColumnModel().getColumn(8).setCellRenderer(centerRenderer);
        
         //TABLE HEADER CELL RENDERER
         
        styleTableHeaders(tbl_reserve, tbl_walkin, tbl_inhouse, tbl_seatsLunch, tbl_seatsDinner, tbl_history, tbl_upcom, tbl_emp, tbl_memb,tbl_menuList, tbl_menuList1);
         
        //COMPARATORS
        
        // DATE COMPARATOR 
        sorter_history.setComparator(1, (d1, d2) -> {
            if (d1 == null && d2 == null) return 0;
            if (d1 == null) return 1;
            if (d2 == null) return -1;
            try {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM-dd-yy");
                java.util.Date date1 = sdf.parse(d1.toString());
                java.util.Date date2 = sdf.parse(d2.toString());
                return date1.compareTo(date2);
            } catch (Exception e) {
                return d1.toString().compareTo(d2.toString());
            }
        });

        // PAX COMPARATOR 
        
        java.util.Comparator<Object> paxComparator = (n1, n2) -> {
            if (n1 == null) return 1;
            if (n2 == null) return -1;
            try {
                return Integer.compare(Integer.parseInt(n1.toString()), Integer.parseInt(n2.toString()));
            } catch (NumberFormatException e) {
                return n1.toString().compareTo(n2.toString());
            }
        };
        
        // TIME COMPARATOR 
        
        java.util.Comparator<Object> timeComparator = (t1, t2) -> {
            if (t1 == null && t2 == null) return 0;
            if (t1 == null) return 1;
            if (t2 == null) return -1;
            String time1 = t1.toString().trim();
            String time2 = t2.toString().trim();
            if (time1.equalsIgnoreCase(time2)) return 0;
            if (time1.equalsIgnoreCase("Lunch")) return -1;
            if (time2.equalsIgnoreCase("Lunch")) return 1;
            return time1.compareToIgnoreCase(time2);
        };

       sorter_upcom.setComparator(5, timeComparator); 
       sorter_reserve.setComparator(4, paxComparator); 
       
       sorter_history.setComparator(5, timeComparator); 
       sorter_history.setComparator(6, paxComparator);  
       
       sorter_reserve.setComparator(5, timeComparator); 
       sorter_reserve.setComparator(6, paxComparator);  

       sorter_walkin.setComparator(4, timeComparator);  
       sorter_walkin.setComparator(5, paxComparator);   
        
       sorter_inhouse.setComparator(4, timeComparator); 
       sorter_inhouse.setComparator(5, paxComparator);
        
        // PRECENDENCE SORTING
        
        java.util.List<javax.swing.RowSorter.SortKey> startupKeys = new java.util.ArrayList<>();
        startupKeys.add(new javax.swing.RowSorter.SortKey(1, javax.swing.SortOrder.DESCENDING)); 
        startupKeys.add(new javax.swing.RowSorter.SortKey(5, javax.swing.SortOrder.ASCENDING)); 
        sorter_history.setSortKeys(startupKeys);
        
        
        java.util.List<javax.swing.RowSorter.SortKey> reserveSortKeys = new java.util.ArrayList<>();
        reserveSortKeys.add(new javax.swing.RowSorter.SortKey(5, javax.swing.SortOrder.ASCENDING)); 
        reserveSortKeys.add(new javax.swing.RowSorter.SortKey(0, javax.swing.SortOrder.DESCENDING)); 
        sorter_reserve.setSortKeys(reserveSortKeys);
        sorter_reserve.sort();

        java.util.List<javax.swing.RowSorter.SortKey> walkinSortKeys = new java.util.ArrayList<>();
        walkinSortKeys.add(new javax.swing.RowSorter.SortKey(4, javax.swing.SortOrder.ASCENDING)); 
        walkinSortKeys.add(new javax.swing.RowSorter.SortKey(0, javax.swing.SortOrder.ASCENDING)); 
        sorter_walkin.setSortKeys(walkinSortKeys);
        sorter_walkin.sort();
            
        
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
        addSearchListener(search_history, sorter_history);
        addSearchListener(search_upcom, sorter_upcom);
        addSearchListener(search_empacc, sorter_emp);
        addSearchListener(search_walkin, sorter_walkin);
        addSearchListener(search_inhouse, sorter_inhouse);
        addSearchListener(search_reserve, sorter_reserve);
        addSearchListener(search_memb, sorter_memb);
       
        
        //CHARAC LIMITS (FRONT DESK)
        setTextFieldLimit(txt_IHcp,11,true);
        setTextFieldLimit(txt_IHfname,50,false);
        setTextFieldLimit(txt_IHlname,50,false);
        setTextFieldLimit(txt_walkinCp,11,true);
        setTextFieldLimit(txt_walkinFname,50,false);
        setTextFieldLimit(txt_walkinLname,50,false);
        
        
        // DATES
        java.util.Date today = new java.util.Date();
        
        date_historyFrom.setMaxSelectableDate(today);
        date_historyTo.setMaxSelectableDate(today);
        
        date_upcomFrom.setMinSelectableDate(today);
        date_upcomTo.setMinSelectableDate(today);

        date_historyFrom.addPropertyChangeListener("date", evt -> filterHistoryByDateRange());
        date_historyTo.addPropertyChangeListener("date", evt -> filterHistoryByDateRange());
        
        date_upcomFrom.addPropertyChangeListener("date", evt -> filterUpcomByDateRange());
        date_upcomTo.addPropertyChangeListener("date", evt -> filterUpcomByDateRange());
        
        LocalDate datetoday = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        lbl_date.setText(datetoday.format(format));
        
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.add(java.util.Calendar.DATE, 1); 
        dc_inhouse.setMinSelectableDate(cal.getTime());

        java.util.Calendar maxCal = java.util.Calendar.getInstance();
        maxCal.set(java.util.Calendar.YEAR, LocalDate.now().getYear() + 1);
        maxCal.set(java.util.Calendar.MONTH, java.util.Calendar.DECEMBER);
        maxCal.set(java.util.Calendar.DAY_OF_MONTH, 31);
        dc_inhouse.setMaxSelectableDate(maxCal.getTime());
        
        date_seats.addPropertyChangeListener("date", evt -> {
            java.util.Date selected = date_seats.getDate();
            if (selected != null) {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM-dd-yy");
                String filterDate = sdf.format(selected);
                RowFilter<Object, Object> dateFilter = RowFilter.regexFilter("^" + filterDate + "$", 0);
                
                sorter_today.setRowFilter(dateFilter);
                sorter_dinner.setRowFilter(dateFilter);
            }
        });
        
        //MOUSE LISTENERS
        
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
                        txt_membFnamersv.setText(model.getValueAt(editingRow, 2) != null ? model.getValueAt(editingRow, 2).toString() : "");
                        txt_membLnamersv.setText(model.getValueAt(editingRow, 3) != null ? model.getValueAt(editingRow, 3).toString() : "");
                        txt_membCPnumrsv.setText(model.getValueAt(editingRow, 4) != null ? model.getValueAt(editingRow, 4).toString() : "");
                        txt_rsvTime.setText(model.getValueAt(editingRow, 5) != null ? model.getValueAt(editingRow, 5).toString() : "");
                        txt_rsvPax.setText(model.getValueAt(editingRow, 6) != null ? model.getValueAt(editingRow, 6).toString() : "");
                        txt_rsvRemarks.setText(model.getValueAt(editingRow, 7) != null ? model.getValueAt(editingRow, 7).toString() : "");
                    }
                }
            }
        });
        
       tbl_emp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int viewRow = tbl_emp.getSelectedRow();
                if (viewRow != -1) {
                    int modelRow = tbl_emp.convertRowIndexToModel(viewRow);

                    if (editingEmpRow == modelRow) {
                        clearAccFields();
                    } else {
                        editingEmpRow = modelRow;
                        txt_empusername.setText(tbl_emp.getModel().getValueAt(modelRow, 1).toString());
                        txt_empfname.setText(tbl_emp.getModel().getValueAt(modelRow, 2).toString());
                        txt_emplname.setText(tbl_emp.getModel().getValueAt(modelRow, 3).toString());
                        txt_emppass.setText(tbl_emp.getModel().getValueAt(modelRow, 4).toString());

                        String role = tbl_emp.getModel().getValueAt(modelRow, 5).toString();
                        if (role.equalsIgnoreCase("Manager")) rb_manager.setSelected(true);
                        else if (role.equalsIgnoreCase("Front Desk")) rb_fdesk.setSelected(true);
                        else buttonGroup1.clearSelection();
                    }
                }
            }
        });
        
        tbl_memb.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int viewRow = tbl_memb.getSelectedRow();
                if (viewRow != -1) {
                    int modelRow = tbl_memb.convertRowIndexToModel(viewRow);

                    if (editingMembRow == modelRow) {
                        clearMemberFields();
                    } else {
                        editingMembRow = modelRow;
                        Object vipId   = tbl_memb.getModel().getValueAt(modelRow, 0);
                        Object dateReg = tbl_memb.getModel().getValueAt(modelRow, 1);
                        Object fName   = tbl_memb.getModel().getValueAt(modelRow, 2);
                        Object lName   = tbl_memb.getModel().getValueAt(modelRow, 3);
                        Object gender  = tbl_memb.getModel().getValueAt(modelRow, 4); 
                        Object bday    = tbl_memb.getModel().getValueAt(modelRow, 5); 
                        Object cpNum   = tbl_memb.getModel().getValueAt(modelRow, 6);
                        Object email   = tbl_memb.getModel().getValueAt(modelRow, 7);
                        Object pass    = tbl_memb.getModel().getValueAt(modelRow, 8);

                        txt_membVipId.setText(vipId != null ? vipId.toString() : "");
                        txt_membDatereg.setText(dateReg != null ? dateReg.toString() : "");
                        txt_membFname.setText(fName != null ? fName.toString() : "");
                        txt_membLname.setText(lName != null ? lName.toString() : "");
                        txt_membVipgender.setText(gender != null ? gender.toString() : "");
                        txt_membVipbday.setText(bday != null ? bday.toString() : "");
                        txt_membCpnum.setText(cpNum != null ? cpNum.toString() : "");
                        txt_membEmail.setText(email != null ? email.toString() : "");
                        txt_membPass.setText(pass != null ? pass.toString() : "");
                    }
                }
            }
        });
        
        tbl_upcom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) { 
                    int row = tbl_upcom.getSelectedRow();
                    int col = tbl_upcom.getSelectedColumn();

                    if (row != -1 && tbl_upcom.getColumnName(col).equals("PROOF")) {
                        String status = tbl_upcom.getValueAt(row, col).toString();

                        if (status.equals("View Receipt")) {
                            String id = tbl_upcom.getValueAt(row, 0).toString(); // Get the OR_ID
                            showPaymentProof(id);
                        } else if (status.equals("N/A")) {
                            JOptionPane.showMessageDialog(null, "No payment proof required for Walk-ins or In-House reservations.");
                        }
                    }
                }
            }
        });
        
        tbl_history.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Check for double-click
                if (evt.getClickCount() == 2) { 
                    int row = tbl_history.getSelectedRow();
                    int col = tbl_history.getSelectedColumn();

                    if (row != -1 && tbl_history.getColumnName(col).equals("PROOF")) {
                        String status = tbl_history.getValueAt(row, col).toString();

                        if (status.equals("View Receipt")) {
                            String id = tbl_history.getValueAt(row, 0).toString(); 
                            showPaymentProof(id);
                        } else if (status.equals("N/A")) {
                            JOptionPane.showMessageDialog(null, "No payment proof available or required for this reservation.");
                        }
                    }
                }
            }
        });
        
        tbl_menuList.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int viewRow = tbl_menuList.getSelectedRow();
                
                if (viewRow != -1) {
                    int modelRow = tbl_menuList.convertRowIndexToModel(viewRow);
                    
                    if (editingMenuRow == modelRow) {
                        clearMenuFields(); 
                    } 
                    else {
                        editingMenuRow = modelRow; 
                        DefaultTableModel model = (DefaultTableModel) tbl_menuList.getModel();
                        
                        editingMenuItemId = Integer.parseInt(model.getValueAt(modelRow, 0).toString());
                        String category = model.getValueAt(modelRow, 1).toString();
                        String dishName = model.getValueAt(modelRow, 2).toString();
                        
                        //cb_menuCategory.setSelectedItem(category);
                        txt_dishName.setText(dishName);
                        
                        Connect db = new Connect();
                        db.DoConnect();
                        try (PreparedStatement pst = db.con.prepareStatement("SELECT IMAGE_DATA FROM DBHOUSE.MENU_ITEMS WHERE ITEM_ID = ?")) {
                            pst.setInt(1, editingMenuItemId);
                            ResultSet rs = pst.executeQuery();
                            
                            if (rs.next()) {
                                java.sql.Blob blob = rs.getBlob("IMAGE_DATA");
                                if (blob != null) {
                                    byte[] imageBytes = blob.getBytes(1, (int) blob.length());
                                    javax.swing.ImageIcon icon = new javax.swing.ImageIcon(imageBytes);
                                    
                                    java.awt.Image scaled = icon.getImage().getScaledInstance(lbl_imagePreview.getWidth(), lbl_imagePreview.getHeight(), java.awt.Image.SCALE_SMOOTH);
                                    lbl_imagePreview.setIcon(new javax.swing.ImageIcon(scaled));
                                    lbl_imagePreview.setText(""); 
                                } else {
                                    lbl_imagePreview.setIcon(null);
                                    lbl_imagePreview.setText("No Image");
                                }
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } finally {
                            try { if (db.con != null) db.con.close(); } catch (SQLException ex) {}
                        }
                        
                        selectedMenuImage = null; 
                    }
                }
            }
        });
        
        tbl_menuList1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int viewRow = tbl_menuList1.getSelectedRow();
                
                if (viewRow != -1) {
                    int modelRow = tbl_menuList1.convertRowIndexToModel(viewRow);
                    
                    if (editingPromoRow == modelRow) {
                        clearPromoFields(); 
                    } else {
                        editingPromoRow = modelRow; 
                        DefaultTableModel model = (DefaultTableModel) tbl_menuList1.getModel();
                        
                        editingPromoId = Integer.parseInt(model.getValueAt(modelRow, 0).toString());
                        String promoName = model.getValueAt(modelRow, 1).toString();
                        
                        txt_dishName1.setText(promoName);
                        
                        Connect db = new Connect();
                        db.DoConnect();
                        try (PreparedStatement pst = db.con.prepareStatement("SELECT IMAGE_DATA FROM DBHOUSE.PROMO_ITEMS WHERE PROMO_ID = ?")) {
                            pst.setInt(1, editingPromoId);
                            ResultSet rs = pst.executeQuery();
                            
                            if (rs.next()) {
                                java.sql.Blob blob = rs.getBlob("IMAGE_DATA");
                                if (blob != null) {
                                    byte[] imageBytes = blob.getBytes(1, (int) blob.length());
                                    javax.swing.ImageIcon icon = new javax.swing.ImageIcon(imageBytes);
                                    
                                    java.awt.Image scaled = icon.getImage().getScaledInstance(lbl_imagePreview1.getWidth(), lbl_imagePreview1.getHeight(), java.awt.Image.SCALE_SMOOTH);
                                    lbl_imagePreview1.setIcon(new javax.swing.ImageIcon(scaled));
                                    lbl_imagePreview1.setText(""); 
                                } else {
                                    lbl_imagePreview1.setIcon(null);
                                    lbl_imagePreview1.setText("No Image");
                                }
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } finally {
                            try { if (db.con != null) db.con.close(); } catch (SQLException ex) {}
                        }
                        
                        selectedPromoImage = null; 
                    }
                }
            }
        });
        
        // Don't forget to call this so the table loads when you open Admin!
        loadPromoTable();
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
        bg_IHtime = new javax.swing.ButtonGroup();
        bg_walkinTime = new javax.swing.ButtonGroup();
        pnl_nav = new javax.swing.JPanel();
        btn_fdesk = new javax.swing.JButton();
        btn_upcom = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btn_history = new javax.swing.JButton();
        btn_emp = new javax.swing.JButton();
        btn_members = new javax.swing.JButton();
        btn_navLogout = new javax.swing.JButton();
        btn_reserve = new javax.swing.JButton();
        btn_walkin = new javax.swing.JButton();
        btn_inhouse = new javax.swing.JButton();
        btn_menu = new javax.swing.JButton();
        btn_promos = new javax.swing.JButton();
        pnl_header = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lbl_date = new javax.swing.JTextField();
        pnl_reserve = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        txt_membCPnumrsv = new javax.swing.JTextField();
        txt_membLnamersv = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        txt_rsvVIPID = new javax.swing.JTextField();
        btn_rsrvcomplete = new javax.swing.JButton();
        jLabel39 = new javax.swing.JLabel();
        txt_membFnamersv = new javax.swing.JTextField();
        txt_rsvID = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        txt_rsvPax = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        btn_cancel = new javax.swing.JButton();
        txt_rsvRemarks = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        txt_rsvTime = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        search_reserve = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        tbl_reserve = new javax.swing.JTable();
        bg_today5 = new javax.swing.JLabel();
        pnl_inhouse = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        txt_IHcp = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        btn_inhouseadd = new javax.swing.JButton();
        btn_inhouseedit = new javax.swing.JButton();
        btn_inhousedel = new javax.swing.JButton();
        dc_inhouse = new com.toedter.calendar.JDateChooser();
        rb_IHlunch = new javax.swing.JRadioButton();
        jLabel51 = new javax.swing.JLabel();
        txt_IHfname = new javax.swing.JTextField();
        rb_IHdinner = new javax.swing.JRadioButton();
        txt_IHlname = new javax.swing.JTextField();
        spn_inhousepax = new javax.swing.JSpinner();
        search_inhouse = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        tbl_inhouse = new javax.swing.JTable();
        bg_today6 = new javax.swing.JLabel();
        pnl_walkin = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        txt_walkinCp = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        btn_walkincomplete = new javax.swing.JButton();
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
        jScrollPane9 = new javax.swing.JScrollPane();
        tbl_walkin = new javax.swing.JTable();
        bg_today7 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        pnl_today = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btn_seatsReset = new javax.swing.JButton();
        jLabel33 = new javax.swing.JLabel();
        date_seats = new com.toedter.calendar.JDateChooser();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_seatsDinner = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_seatsLunch = new javax.swing.JTable();
        bg_today = new javax.swing.JLabel();
        pnl_upcom = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        btn_upcomConfirm = new javax.swing.JButton();
        date_upcomFrom = new com.toedter.calendar.JDateChooser();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        btn_upcomCancel = new javax.swing.JButton();
        date_upcomTo = new com.toedter.calendar.JDateChooser();
        btn_upcomReset = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbl_upcom = new javax.swing.JTable();
        btn_upcomGenerate = new javax.swing.JButton();
        search_upcom = new javax.swing.JTextField();
        bg_today2 = new javax.swing.JLabel();
        pnl_history = new javax.swing.JPanel();
        date_historyFrom = new com.toedter.calendar.JDateChooser();
        date_historyTo = new com.toedter.calendar.JDateChooser();
        jLabel20 = new javax.swing.JLabel();
        btn_histGenerate = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btn_histReset = new javax.swing.JButton();
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
        txt_membVipgender = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txt_membVipbday = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txt_membEmail = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txt_membCpnum = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        btn_membedit = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbl_memb = new javax.swing.JTable();
        bg_today4 = new javax.swing.JLabel();
        pnl_emp = new javax.swing.JPanel();
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
        jLabel58 = new javax.swing.JLabel();
        btn_accdel = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        txt_empfname = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        search_empacc = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_emp = new javax.swing.JTable();
        bg_today3 = new javax.swing.JLabel();
        pnl_promoedit = new javax.swing.JPanel();
        jLabel65 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tbl_menuList1 = new javax.swing.JTable();
        jLabel61 = new javax.swing.JLabel();
        btn_deleteMenuItem1 = new javax.swing.JButton();
        btn_editMenuItem1 = new javax.swing.JButton();
        lbl_imagePreview1 = new javax.swing.JLabel();
        txt_dishName1 = new javax.swing.JTextField();
        btn_saveMenuItem1 = new javax.swing.JButton();
        btn_chooseImage1 = new javax.swing.JButton();
        bg_today9 = new javax.swing.JLabel();
        pnl_menuedit = new javax.swing.JPanel();
        cb_menuCategory = new javax.swing.JComboBox<>();
        jLabel64 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tbl_menuList = new javax.swing.JTable();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        btn_deleteMenuItem = new javax.swing.JButton();
        btn_editMenuItem = new javax.swing.JButton();
        lbl_imagePreview = new javax.swing.JLabel();
        txt_dishName = new javax.swing.JTextField();
        btn_saveMenuItem = new javax.swing.JButton();
        btn_chooseImage = new javax.swing.JButton();
        bg_today8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnl_nav.setBackground(new java.awt.Color(55, 77, 94));
        pnl_nav.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_fdesk.setBackground(new java.awt.Color(55, 77, 94));
        btn_fdesk.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        btn_fdesk.setForeground(new java.awt.Color(255, 255, 255));
        btn_fdesk.setText("SEATS");
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
        pnl_nav.add(btn_fdesk, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 140, 30));

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
        pnl_nav.add(btn_upcom, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 140, 30));

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
        pnl_nav.add(btn_history, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 140, 30));

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
        pnl_nav.add(btn_emp, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 140, 30));

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
        pnl_nav.add(btn_members, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 140, 30));

        btn_navLogout.setBackground(new java.awt.Color(153, 0, 0));
        btn_navLogout.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        btn_navLogout.setForeground(new java.awt.Color(206, 206, 206));
        btn_navLogout.setText("LOG OUT");
        btn_navLogout.setBorder(null);
        btn_navLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_navLogout.addActionListener(this::btn_navLogoutActionPerformed);
        pnl_nav.add(btn_navLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 490, 80, 30));

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
        pnl_nav.add(btn_reserve, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 140, 30));

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
        pnl_nav.add(btn_walkin, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 140, 30));

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
        pnl_nav.add(btn_inhouse, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 100, 30));

        btn_menu.setBackground(new java.awt.Color(55, 77, 94));
        btn_menu.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        btn_menu.setForeground(new java.awt.Color(255, 255, 255));
        btn_menu.setText("MENU");
        btn_menu.setBorder(null);
        btn_menu.setContentAreaFilled(false);
        btn_menu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_menu.setFocusPainted(false);
        btn_menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_menuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_menuMouseExited(evt);
            }
        });
        btn_menu.addActionListener(this::btn_menuActionPerformed);
        pnl_nav.add(btn_menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 140, 30));

        btn_promos.setBackground(new java.awt.Color(55, 77, 94));
        btn_promos.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        btn_promos.setForeground(new java.awt.Color(255, 255, 255));
        btn_promos.setText("PROMOS");
        btn_promos.setBorder(null);
        btn_promos.setContentAreaFilled(false);
        btn_promos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_promos.setFocusPainted(false);
        btn_promos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_promosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_promosMouseExited(evt);
            }
        });
        btn_promos.addActionListener(this::btn_promosActionPerformed);
        pnl_nav.add(btn_promos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 140, 30));

        getContentPane().add(pnl_nav, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 580));

        pnl_header.setBackground(new java.awt.Color(55, 91, 115));
        pnl_header.setMaximumSize(new java.awt.Dimension(681, 115));
        pnl_header.setPreferredSize(new java.awt.Dimension(681, 115));

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("ADMIN DASHBOARD");

        lbl_date.setBackground(new java.awt.Color(55, 91, 115));
        lbl_date.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lbl_date.setForeground(new java.awt.Color(255, 255, 255));
        lbl_date.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        lbl_date.setText("Date");
        lbl_date.setBorder(null);
        lbl_date.addActionListener(this::lbl_dateActionPerformed);

        javax.swing.GroupLayout pnl_headerLayout = new javax.swing.GroupLayout(pnl_header);
        pnl_header.setLayout(pnl_headerLayout);
        pnl_headerLayout.setHorizontalGroup(
            pnl_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_headerLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 274, Short.MAX_VALUE)
                .addComponent(lbl_date, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnl_headerLayout.setVerticalGroup(
            pnl_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_headerLayout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addGroup(pnl_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lbl_date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33))
        );

        getContentPane().add(pnl_header, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 740, -1));

        pnl_reserve.setForeground(new java.awt.Color(202, 199, 199));
        pnl_reserve.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel34.setFont(new java.awt.Font("Century Gothic", 1, 30)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(55, 77, 94));
        jLabel34.setText("TODAY'S RESERVATIONS");
        pnl_reserve.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, 50));

        jLabel35.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(55, 77, 94));
        jLabel35.setText("Search:");
        pnl_reserve.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 40, 60, 20));

        jLabel36.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(55, 77, 94));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel36.setText("CP Num:");
        pnl_reserve.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 410, 60, 30));

        txt_membCPnumrsv.setEditable(false);
        txt_membCPnumrsv.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_membCPnumrsv.setFocusable(false);
        txt_membCPnumrsv.addActionListener(this::txt_membCPnumrsvNew_tableActionPerformed);
        pnl_reserve.add(txt_membCPnumrsv, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 410, 140, 30));

        txt_membLnamersv.setEditable(false);
        txt_membLnamersv.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_membLnamersv.setFocusable(false);
        txt_membLnamersv.addActionListener(this::txt_membLnamersvNew_tableActionPerformed);
        pnl_reserve.add(txt_membLnamersv, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 380, 140, 30));

        jLabel37.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(55, 77, 94));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel37.setText("Last Name:");
        pnl_reserve.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 380, 70, 30));

        jLabel38.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(55, 77, 94));
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel38.setText("First Name:");
        pnl_reserve.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 350, 70, 30));

        txt_rsvVIPID.setEditable(false);
        txt_rsvVIPID.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_rsvVIPID.setFocusable(false);
        txt_rsvVIPID.addActionListener(this::txt_rsvVIPIDNew_tableActionPerformed);
        pnl_reserve.add(txt_rsvVIPID, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 380, 90, 30));

        btn_rsrvcomplete.setBackground(new java.awt.Color(255, 255, 255));
        btn_rsrvcomplete.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        btn_rsrvcomplete.setForeground(new java.awt.Color(65, 93, 120));
        btn_rsrvcomplete.setText("COMPLETE RESERVATION ");
        btn_rsrvcomplete.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(65, 93, 120), 2, true));
        btn_rsrvcomplete.setContentAreaFilled(false);
        btn_rsrvcomplete.setFocusPainted(false);
        btn_rsrvcomplete.addActionListener(this::btn_rsrvcompleteAssign_ButtonActionPerformed);
        pnl_reserve.add(btn_rsrvcomplete, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 350, 160, 30));

        jLabel39.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(55, 77, 94));
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel39.setText("VIP ID:  ");
        pnl_reserve.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, 50, 30));

        txt_membFnamersv.setEditable(false);
        txt_membFnamersv.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_membFnamersv.setFocusable(false);
        txt_membFnamersv.addActionListener(this::txt_membFnamersvNew_tableActionPerformed);
        pnl_reserve.add(txt_membFnamersv, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 350, 140, 30));

        txt_rsvID.setEditable(false);
        txt_rsvID.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_rsvID.setFocusable(false);
        txt_rsvID.addActionListener(this::txt_rsvIDNew_tableActionPerformed);
        pnl_reserve.add(txt_rsvID, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 350, 90, 30));

        jLabel40.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(55, 77, 94));
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel40.setText("ID:  ");
        pnl_reserve.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, 50, 30));

        txt_rsvPax.setEditable(false);
        txt_rsvPax.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_rsvPax.setFocusable(false);
        txt_rsvPax.addActionListener(this::txt_rsvPaxNew_tableActionPerformed);
        pnl_reserve.add(txt_rsvPax, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 380, 110, 30));

        jLabel41.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(55, 77, 94));
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel41.setText("Pax:");
        pnl_reserve.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 380, 40, 30));

        btn_cancel.setBackground(new java.awt.Color(55, 91, 115));
        btn_cancel.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        btn_cancel.setForeground(new java.awt.Color(255, 255, 255));
        btn_cancel.setText("CANCEL RESERVATION");
        btn_cancel.setBorder(null);
        btn_cancel.addActionListener(this::btn_cancelActionPerformed);
        pnl_reserve.add(btn_cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 390, 160, 30));

        txt_rsvRemarks.setEditable(false);
        txt_rsvRemarks.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_rsvRemarks.setFocusable(false);
        txt_rsvRemarks.addActionListener(this::txt_rsvRemarksNew_tableActionPerformed);
        pnl_reserve.add(txt_rsvRemarks, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 410, 90, 30));

        jLabel42.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(55, 77, 94));
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel42.setText("Remarks:  ");
        pnl_reserve.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 70, 30));

        txt_rsvTime.setEditable(false);
        txt_rsvTime.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_rsvTime.setFocusable(false);
        txt_rsvTime.addActionListener(this::txt_rsvTimeNew_tableActionPerformed);
        pnl_reserve.add(txt_rsvTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 350, 110, 30));

        jLabel43.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(55, 77, 94));
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel43.setText("Time:");
        pnl_reserve.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 350, 40, 30));

        search_reserve.addActionListener(this::search_reserveActionPerformed);
        search_reserve.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_reserveKeyReleased(evt);
            }
        });
        pnl_reserve.add(search_reserve, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 40, 190, -1));

        jScrollPane7.setForeground(new java.awt.Color(55, 77, 94));

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
        jScrollPane7.setViewportView(tbl_reserve);

        pnl_reserve.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 720, 250));

        bg_today5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bgfd.jpg"))); // NOI18N
        bg_today5.setText("Today");
        pnl_reserve.add(bg_today5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 470));

        getContentPane().add(pnl_reserve, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 740, 470));

        pnl_inhouse.setForeground(new java.awt.Color(202, 199, 199));
        pnl_inhouse.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel44.setFont(new java.awt.Font("Century Gothic", 1, 30)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(55, 77, 94));
        jLabel44.setText("IN HOUSE RESERVATIONS");
        pnl_inhouse.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, 40));

        jLabel45.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(55, 77, 94));
        jLabel45.setText("Search:");
        pnl_inhouse.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 50, 60, 20));

        jLabel46.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(55, 77, 94));
        jLabel46.setText("Last Name:");
        pnl_inhouse.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 200, 110, -1));

        jLabel47.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(55, 77, 94));
        jLabel47.setText("Pax:");
        pnl_inhouse.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 350, 30, -1));

        txt_IHcp.addActionListener(this::txt_IHcpActionPerformed);
        txt_IHcp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_IHcpKeyReleased(evt);
            }
        });
        pnl_inhouse.add(txt_IHcp, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 270, 160, 30));

        jLabel48.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(55, 77, 94));
        jLabel48.setText("Time:");
        pnl_inhouse.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 306, -1, 20));

        jLabel49.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(55, 77, 94));
        jLabel49.setText("CP#:");
        pnl_inhouse.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 250, 70, 20));

        jLabel50.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(55, 77, 94));
        jLabel50.setText("Date:");
        pnl_inhouse.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 80, 40, -1));

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

        jLabel51.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(55, 77, 94));
        jLabel51.setText("First Name:");
        pnl_inhouse.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(573, 140, 70, -1));

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

        spn_inhousepax.setModel(new javax.swing.SpinnerNumberModel(1, 1, 100, 1));
        pnl_inhouse.add(spn_inhousepax, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 370, 100, 30));

        search_inhouse.addActionListener(this::search_inhouseActionPerformed);
        search_inhouse.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_inhouseKeyReleased(evt);
            }
        });
        pnl_inhouse.add(search_inhouse, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 50, 180, -1));

        jScrollPane8.setForeground(new java.awt.Color(55, 77, 94));

        tbl_inhouse.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        tbl_inhouse.setForeground(new java.awt.Color(55, 77, 94));
        tbl_inhouse.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IR_ID", "DATE", "F_NAME", "L_NAME", "TIME", "PAX", "REMARKS"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_inhouse.setOpaque(false);
        jScrollPane8.setViewportView(tbl_inhouse);
        if (tbl_inhouse.getColumnModel().getColumnCount() > 0) {
            tbl_inhouse.getColumnModel().getColumn(0).setResizable(false);
            tbl_inhouse.getColumnModel().getColumn(1).setResizable(false);
            tbl_inhouse.getColumnModel().getColumn(2).setResizable(false);
            tbl_inhouse.getColumnModel().getColumn(3).setResizable(false);
            tbl_inhouse.getColumnModel().getColumn(4).setResizable(false);
            tbl_inhouse.getColumnModel().getColumn(5).setResizable(false);
        }

        pnl_inhouse.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 550, 360));

        bg_today6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bgfd.jpg"))); // NOI18N
        bg_today6.setText("Today");
        pnl_inhouse.add(bg_today6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 470));

        getContentPane().add(pnl_inhouse, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 740, 470));

        pnl_walkin.setForeground(new java.awt.Color(202, 199, 199));
        pnl_walkin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 30)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(55, 77, 94));
        jLabel4.setText("TODAY'S WALK-INS");
        pnl_walkin.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, 40));

        jLabel52.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(55, 77, 94));
        jLabel52.setText("Search:");
        pnl_walkin.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 40, 60, 20));

        jLabel53.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(55, 77, 94));
        jLabel53.setText("Last Name:");
        pnl_walkin.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 140, 110, -1));

        jLabel54.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(55, 77, 94));
        jLabel54.setText("Pax:");
        pnl_walkin.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 310, -1, -1));

        txt_walkinCp.addActionListener(this::txt_walkinCpActionPerformed);
        txt_walkinCp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_walkinCpKeyReleased(evt);
            }
        });
        pnl_walkin.add(txt_walkinCp, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 220, 160, 30));

        jLabel55.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(55, 77, 94));
        jLabel55.setText("Time:");
        pnl_walkin.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 260, -1, -1));

        jLabel56.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(55, 77, 94));
        jLabel56.setText("CP Num:");
        pnl_walkin.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 200, 80, -1));

        btn_walkincomplete.setBackground(new java.awt.Color(255, 255, 255));
        btn_walkincomplete.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btn_walkincomplete.setForeground(new java.awt.Color(65, 93, 120));
        btn_walkincomplete.setText("Complete");
        btn_walkincomplete.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(65, 93, 120), 2, true));
        btn_walkincomplete.setContentAreaFilled(false);
        btn_walkincomplete.setFocusPainted(false);
        btn_walkincomplete.addActionListener(this::btn_walkincompleteAssign_ButtonActionPerformed);
        pnl_walkin.add(btn_walkincomplete, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 400, 160, 30));

        rb_walkinLunch.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        rb_walkinLunch.setForeground(new java.awt.Color(65, 93, 120));
        rb_walkinLunch.setText("Lunch");
        rb_walkinLunch.addActionListener(this::rb_walkinLunchActionPerformed);
        pnl_walkin.add(rb_walkinLunch, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 280, -1, -1));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(55, 77, 94));
        jLabel1.setText("First Name:");
        pnl_walkin.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 80, 110, -1));

        txt_walkinFname.addActionListener(this::txt_walkinFnameActionPerformed);
        txt_walkinFname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_walkinFnameKeyReleased(evt);
            }
        });
        pnl_walkin.add(txt_walkinFname, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 100, 160, 30));

        btn_walkinadd.setBackground(new java.awt.Color(255, 255, 255));
        btn_walkinadd.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btn_walkinadd.setForeground(new java.awt.Color(65, 93, 120));
        btn_walkinadd.setText("Add");
        btn_walkinadd.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(65, 93, 120), 2, true));
        btn_walkinadd.setContentAreaFilled(false);
        btn_walkinadd.setFocusPainted(false);
        btn_walkinadd.addActionListener(this::btn_walkinaddAssign_ButtonActionPerformed);
        pnl_walkin.add(btn_walkinadd, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 370, 50, 30));

        btn_walkinedit.setBackground(new java.awt.Color(255, 255, 255));
        btn_walkinedit.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btn_walkinedit.setForeground(new java.awt.Color(65, 93, 120));
        btn_walkinedit.setText("Edit");
        btn_walkinedit.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(65, 93, 120), 2, true));
        btn_walkinedit.setContentAreaFilled(false);
        btn_walkinedit.setFocusPainted(false);
        btn_walkinedit.addActionListener(this::btn_walkineditAssign_ButtonActionPerformed);
        pnl_walkin.add(btn_walkinedit, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 370, 50, 30));

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
        pnl_walkin.add(btn_walkindel, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 370, 60, 30));

        spn_walkinpax.setModel(new javax.swing.SpinnerNumberModel(1, 1, 100, 1));
        pnl_walkin.add(spn_walkinpax, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 330, 100, 30));

        rb_walkinDinner.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        rb_walkinDinner.setForeground(new java.awt.Color(65, 93, 120));
        rb_walkinDinner.setText("Dinner");
        pnl_walkin.add(rb_walkinDinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 280, -1, -1));

        txt_walkinLname.addActionListener(this::txt_walkinLnameActionPerformed);
        txt_walkinLname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_walkinLnameKeyReleased(evt);
            }
        });
        pnl_walkin.add(txt_walkinLname, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 160, 160, 30));

        search_walkin.addActionListener(this::search_walkinActionPerformed);
        search_walkin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_walkinKeyReleased(evt);
            }
        });
        pnl_walkin.add(search_walkin, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 40, 180, -1));

        jScrollPane9.setForeground(new java.awt.Color(55, 77, 94));

        tbl_walkin.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        tbl_walkin.setForeground(new java.awt.Color(55, 77, 94));
        tbl_walkin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "WI_ID", "F_NAME", "L_NAME", "CP_NUM", "TIME", "PAX", "REMARKS"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_walkin.setOpaque(false);
        jScrollPane9.setViewportView(tbl_walkin);

        pnl_walkin.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 550, 350));

        bg_today7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bgfd.jpg"))); // NOI18N
        bg_today7.setText("Today");
        pnl_walkin.add(bg_today7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 470));

        jLabel57.setText("First Name:");
        pnl_walkin.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 80, -1, -1));

        getContentPane().add(pnl_walkin, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 740, 470));

        pnl_today.setForeground(new java.awt.Color(202, 199, 199));
        pnl_today.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(55, 77, 94));
        jLabel5.setText("Search:");
        pnl_today.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 30, 60, 40));

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(55, 77, 94));
        jLabel6.setText("Dinner");
        pnl_today.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 70, 90, 30));

        jLabel11.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(55, 77, 94));
        jLabel11.setText("SEAT TRACKER");
        pnl_today.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 270, 50));

        btn_seatsReset.setBackground(new java.awt.Color(55, 77, 94));
        btn_seatsReset.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        btn_seatsReset.setForeground(new java.awt.Color(255, 255, 255));
        btn_seatsReset.setText("RESET TABLES");
        btn_seatsReset.setBorder(null);
        btn_seatsReset.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_seatsReset.addActionListener(this::btn_seatsResetActionPerformed);
        pnl_today.add(btn_seatsReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 100, 30));

        jLabel33.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(55, 77, 94));
        jLabel33.setText("Lunch");
        pnl_today.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 80, 30));

        date_seats.setDateFormatString("MM-dd-yy");
        pnl_today.add(date_seats, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 40, 160, -1));

        jScrollPane3.setForeground(new java.awt.Color(55, 77, 94));

        tbl_seatsDinner.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        tbl_seatsDinner.setForeground(new java.awt.Color(55, 77, 94));
        tbl_seatsDinner.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null,  new Integer(1)},
                {null, null,  new Integer(2)},
                {null, null,  new Integer(3)},
                {null, null,  new Integer(4)},
                {null, null,  new Integer(5)}
            },
            new String [] {
                "DATE", "OCCUPIED", "AVAILABLE"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_seatsDinner.setOpaque(false);
        jScrollPane3.setViewportView(tbl_seatsDinner);
        if (tbl_seatsDinner.getColumnModel().getColumnCount() > 0) {
            tbl_seatsDinner.getColumnModel().getColumn(0).setResizable(false);
            tbl_seatsDinner.getColumnModel().getColumn(1).setResizable(false);
            tbl_seatsDinner.getColumnModel().getColumn(2).setResizable(false);
        }

        pnl_today.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 110, 340, 300));

        jScrollPane1.setForeground(new java.awt.Color(55, 77, 94));

        tbl_seatsLunch.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        tbl_seatsLunch.setForeground(new java.awt.Color(55, 77, 94));
        tbl_seatsLunch.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null,  new Integer(1)},
                {null, null,  new Integer(2)},
                {null, null,  new Integer(3)},
                {null, null,  new Integer(4)},
                {null, null,  new Integer(5)}
            },
            new String [] {
                "DATE", "OCCUPIED", " AVAILABLE"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_seatsLunch.setOpaque(false);
        jScrollPane1.setViewportView(tbl_seatsLunch);
        if (tbl_seatsLunch.getColumnModel().getColumnCount() > 0) {
            tbl_seatsLunch.getColumnModel().getColumn(0).setResizable(false);
            tbl_seatsLunch.getColumnModel().getColumn(1).setResizable(false);
            tbl_seatsLunch.getColumnModel().getColumn(2).setResizable(false);
        }

        pnl_today.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 340, 300));

        bg_today.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bgfd.jpg"))); // NOI18N
        bg_today.setText("Today");
        pnl_today.add(bg_today, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 470));

        getContentPane().add(pnl_today, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 740, 470));

        pnl_upcom.setForeground(new java.awt.Color(202, 199, 199));
        pnl_upcom.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(55, 77, 94));
        jLabel8.setText("UPCOMING");
        pnl_upcom.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 310, 50));

        jLabel12.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(55, 77, 94));
        jLabel12.setText("Search:");
        pnl_upcom.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 20, 60, 20));

        btn_upcomConfirm.setBackground(new java.awt.Color(55, 77, 94));
        btn_upcomConfirm.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        btn_upcomConfirm.setForeground(new java.awt.Color(255, 255, 255));
        btn_upcomConfirm.setText("CONFIRM RSRV");
        btn_upcomConfirm.setBorder(null);
        btn_upcomConfirm.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_upcomConfirm.addActionListener(this::btn_upcomConfirmActionPerformed);
        pnl_upcom.add(btn_upcomConfirm, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 420, 120, 30));

        date_upcomFrom.setDateFormatString("MM-dd-yy");
        pnl_upcom.add(date_upcomFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 50, 130, -1));

        jLabel31.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(55, 77, 94));
        jLabel31.setText("From");
        pnl_upcom.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 50, 40, 20));

        jLabel32.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(55, 77, 94));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("to");
        pnl_upcom.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 50, 30, 20));

        btn_upcomCancel.setBackground(new java.awt.Color(55, 77, 94));
        btn_upcomCancel.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        btn_upcomCancel.setForeground(new java.awt.Color(255, 255, 255));
        btn_upcomCancel.setText("CANCEL RSRV");
        btn_upcomCancel.setBorder(null);
        btn_upcomCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_upcomCancel.addActionListener(this::btn_upcomCancelActionPerformed);
        pnl_upcom.add(btn_upcomCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 420, 110, 30));

        date_upcomTo.setDateFormatString("MM-dd-yy");
        pnl_upcom.add(date_upcomTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 50, 130, -1));

        btn_upcomReset.setBackground(new java.awt.Color(55, 77, 94));
        btn_upcomReset.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        btn_upcomReset.setForeground(new java.awt.Color(255, 255, 255));
        btn_upcomReset.setText("RESET TABLE");
        btn_upcomReset.setBorder(null);
        btn_upcomReset.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_upcomReset.addActionListener(this::btn_upcomResetActionPerformed);
        pnl_upcom.add(btn_upcomReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 90, 30));

        jScrollPane6.setForeground(new java.awt.Color(55, 77, 94));

        tbl_upcom.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        tbl_upcom.setForeground(new java.awt.Color(55, 77, 94));
        tbl_upcom.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, "03-26-26", null, "Juan Dela Cruz", "09357873489", "Lunch",  new Integer(4), null, null, null},
                {null, "03-01-26", null, "Maria Santos", "09174532356", "Lunch",  new Integer(3), null, null, null},
                {null, "04-19-26", null, "Louise Lopez", "09876541453", "Lunch",  new Integer(2), null, null, null},
                {null, "03-01-26", null, "Rhian Espinosa", "09258653421", "Dinner",  new Integer(6), null, null, null},
                {null, "04-19-26", null, "Justine Dizon", "09987823421", "Dinner",  new Integer(7), null, null, null}
            },
            new String [] {
                "ID", "DATE", "F_NAME", "L_NAME", "CP_NUM", "TIME", "PAX", "REMARKS", "REF_NUM", "PROOF"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_upcom.setOpaque(false);
        jScrollPane6.setViewportView(tbl_upcom);

        pnl_upcom.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 700, 320));

        btn_upcomGenerate.setBackground(new java.awt.Color(55, 77, 94));
        btn_upcomGenerate.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        btn_upcomGenerate.setForeground(new java.awt.Color(255, 255, 255));
        btn_upcomGenerate.setText("GENERATE REPORT");
        btn_upcomGenerate.setBorder(null);
        btn_upcomGenerate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_upcomGenerate.addActionListener(this::btn_upcomGenerateActionPerformed);
        pnl_upcom.add(btn_upcomGenerate, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 420, 130, 30));

        search_upcom.addActionListener(this::search_upcomActionPerformed);
        search_upcom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_upcomKeyReleased(evt);
            }
        });
        pnl_upcom.add(search_upcom, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 20, 170, -1));

        bg_today2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bgfd.jpg"))); // NOI18N
        bg_today2.setText("Today");
        pnl_upcom.add(bg_today2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 470));

        getContentPane().add(pnl_upcom, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 740, 470));

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

        btn_histReset.setBackground(new java.awt.Color(55, 77, 94));
        btn_histReset.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        btn_histReset.setForeground(new java.awt.Color(255, 255, 255));
        btn_histReset.setText("RESET TABLE");
        btn_histReset.setBorder(null);
        btn_histReset.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_histReset.addActionListener(this::btn_histResetActionPerformed);
        pnl_history.add(btn_histReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 90, 30));

        search_history.addActionListener(this::search_historyActionPerformed);
        search_history.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_historyKeyReleased(evt);
            }
        });
        pnl_history.add(search_history, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 20, 170, -1));

        jScrollPane2.setForeground(new java.awt.Color(55, 77, 94));

        tbl_history.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        tbl_history.setForeground(new java.awt.Color(55, 77, 94));
        tbl_history.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, "03-26-26", null, "Juan Dela Cruz", "09357873489", "Lunch",  new Integer(4), null, null, null},
                {null, "03-01-26", null, "Maria Santos", "09174532356", "Lunch",  new Integer(3), null, null, null},
                {null, "04-19-26", null, "Louise Lopez", "09876541453", "Lunch",  new Integer(2), null, null, null},
                {null, "03-01-26", null, "Rhian Espinosa", "09258653421", "Dinner",  new Integer(6), null, null, null},
                {null, "04-19-26", null, "Justine Dizon", "09987823421", "Dinner",  new Integer(7), null, null, null}
            },
            new String [] {
                "ID", "DATE", "F_NAME", "L_NAME", "CP_NUM", "TIME", "PAX", "REMARKS", "REF_NUM", "PROOF"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
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
        jLabel21.setText("VIP ID:  ");
        pnl_memb.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, 50, 30));

        txt_membLname.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_membLname.addActionListener(this::txt_membLnameNew_tableActionPerformed);
        pnl_memb.add(txt_membLname, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 380, 140, 30));

        jLabel25.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(55, 77, 94));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel25.setText("Last Name:");
        pnl_memb.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 380, 70, 30));

        txt_membFname.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_membFname.addActionListener(this::txt_membFnameNew_tableActionPerformed);
        pnl_memb.add(txt_membFname, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 350, 140, 30));

        txt_membVipId.setEditable(false);
        txt_membVipId.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_membVipId.setFocusable(false);
        txt_membVipId.addActionListener(this::txt_membVipIdNew_tableActionPerformed);
        pnl_memb.add(txt_membVipId, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 350, 90, 30));

        jLabel29.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(55, 77, 94));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel29.setText("First Name:");
        pnl_memb.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 350, 70, 30));

        txt_membPass.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_membPass.addActionListener(this::txt_membPassNew_tableActionPerformed);
        pnl_memb.add(txt_membPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 410, 140, 30));

        jLabel26.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(55, 77, 94));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel26.setText("Password:");
        pnl_memb.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 410, 60, 30));

        txt_membDatereg.setEditable(false);
        txt_membDatereg.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_membDatereg.setFocusable(false);
        txt_membDatereg.addActionListener(this::txt_membDateregNew_tableActionPerformed);
        pnl_memb.add(txt_membDatereg, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 380, 90, 30));

        jLabel30.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(55, 77, 94));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel30.setText("Date Reg:  ");
        pnl_memb.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, 70, 30));

        txt_membVipgender.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_membVipgender.addActionListener(this::txt_membVipgenderNew_tableActionPerformed);
        pnl_memb.add(txt_membVipgender, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 410, 90, 30));

        jLabel22.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(55, 77, 94));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("Gender:  ");
        pnl_memb.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, 60, 30));

        txt_membVipbday.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_membVipbday.addActionListener(this::txt_membVipbdayNew_tableActionPerformed);
        pnl_memb.add(txt_membVipbday, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 410, 140, 30));

        jLabel23.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(55, 77, 94));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel23.setText("Birthday:");
        pnl_memb.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 410, 50, 30));

        txt_membEmail.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_membEmail.addActionListener(this::txt_membEmailNew_tableActionPerformed);
        pnl_memb.add(txt_membEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 380, 140, 30));

        jLabel27.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(55, 77, 94));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel27.setText("Email:");
        pnl_memb.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 380, 50, 30));

        txt_membCpnum.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_membCpnum.addActionListener(this::txt_membCpnumNew_tableActionPerformed);
        pnl_memb.add(txt_membCpnum, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 350, 140, 30));

        jLabel28.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(55, 77, 94));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel28.setText("CP Num:");
        pnl_memb.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 350, 60, 30));

        btn_membedit.setBackground(new java.awt.Color(65, 93, 120));
        btn_membedit.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btn_membedit.setForeground(new java.awt.Color(65, 93, 120));
        btn_membedit.setText("Edit");
        btn_membedit.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(65, 93, 120), 2, true));
        btn_membedit.setContentAreaFilled(false);
        btn_membedit.setFocusPainted(false);
        btn_membedit.addActionListener(this::btn_membeditAssign_ButtonActionPerformed);
        pnl_memb.add(btn_membedit, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 350, 90, 30));

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

        rb_fdesk.setForeground(new java.awt.Color(55, 77, 94));
        rb_fdesk.setText("Front Desk");
        pnl_emp.add(rb_fdesk, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 350, -1, -1));

        rb_manager.setForeground(new java.awt.Color(55, 77, 94));
        rb_manager.setText("Manager");
        rb_manager.addActionListener(this::rb_managerActionPerformed);
        pnl_emp.add(rb_manager, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 350, -1, -1));

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
        jLabel13.setText("Account Type:");
        pnl_emp.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 330, -1, -1));

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

        jLabel58.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(55, 77, 94));
        jLabel58.setText("Password:");
        pnl_emp.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 270, -1, -1));

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
            tbl_emp.getColumnModel().getColumn(0).setHeaderValue("EMP_ID");
            tbl_emp.getColumnModel().getColumn(1).setResizable(false);
            tbl_emp.getColumnModel().getColumn(1).setHeaderValue("USERNAME");
            tbl_emp.getColumnModel().getColumn(2).setResizable(false);
            tbl_emp.getColumnModel().getColumn(2).setHeaderValue("F_NAME");
            tbl_emp.getColumnModel().getColumn(3).setResizable(false);
            tbl_emp.getColumnModel().getColumn(3).setHeaderValue("L_NAME");
            tbl_emp.getColumnModel().getColumn(4).setResizable(false);
            tbl_emp.getColumnModel().getColumn(4).setHeaderValue("PASS");
            tbl_emp.getColumnModel().getColumn(5).setResizable(false);
            tbl_emp.getColumnModel().getColumn(5).setHeaderValue("ACC_TYPE");
        }

        pnl_emp.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 530, 330));

        bg_today3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bgfd.jpg"))); // NOI18N
        bg_today3.setText("Today");
        pnl_emp.add(bg_today3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 470));

        getContentPane().add(pnl_emp, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 740, 470));

        pnl_promoedit.setForeground(new java.awt.Color(202, 199, 199));
        pnl_promoedit.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel65.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(55, 77, 94));
        jLabel65.setText("PROMOS EDITOR");
        pnl_promoedit.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 390, 50));

        jScrollPane11.setForeground(new java.awt.Color(55, 77, 94));

        tbl_menuList1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        tbl_menuList1.setForeground(new java.awt.Color(55, 77, 94));
        tbl_menuList1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "PROMO_ID", "PROMO_NAME"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_menuList1.setOpaque(false);
        jScrollPane11.setViewportView(tbl_menuList1);
        if (tbl_menuList1.getColumnModel().getColumnCount() > 0) {
            tbl_menuList1.getColumnModel().getColumn(0).setResizable(false);
            tbl_menuList1.getColumnModel().getColumn(1).setResizable(false);
        }

        pnl_promoedit.add(jScrollPane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 480, 330));

        jLabel61.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(55, 77, 94));
        jLabel61.setText("Promo Name:");
        pnl_promoedit.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 280, -1, -1));

        btn_deleteMenuItem1.setBackground(new java.awt.Color(65, 93, 120));
        btn_deleteMenuItem1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btn_deleteMenuItem1.setForeground(new java.awt.Color(65, 93, 120));
        btn_deleteMenuItem1.setText("Delete");
        btn_deleteMenuItem1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(65, 93, 120), 2, true));
        btn_deleteMenuItem1.setContentAreaFilled(false);
        btn_deleteMenuItem1.setFocusPainted(false);
        btn_deleteMenuItem1.addActionListener(this::btn_deleteMenuItem1Assign_ButtonActionPerformed);
        pnl_promoedit.add(btn_deleteMenuItem1, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 390, 70, 30));

        btn_editMenuItem1.setBackground(new java.awt.Color(65, 93, 120));
        btn_editMenuItem1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btn_editMenuItem1.setForeground(new java.awt.Color(65, 93, 120));
        btn_editMenuItem1.setText("Edit");
        btn_editMenuItem1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(65, 93, 120), 2, true));
        btn_editMenuItem1.setContentAreaFilled(false);
        btn_editMenuItem1.setFocusPainted(false);
        btn_editMenuItem1.addActionListener(this::btn_editMenuItem1Assign_ButtonActionPerformed);
        pnl_promoedit.add(btn_editMenuItem1, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 390, 60, 30));

        lbl_imagePreview1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(55, 77, 94)));
        pnl_promoedit.add(lbl_imagePreview1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 90, 190, 180));

        txt_dishName1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_dishName1.addActionListener(this::txt_dishName1New_tableActionPerformed);
        pnl_promoedit.add(txt_dishName1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 300, 190, 30));

        btn_saveMenuItem1.setBackground(new java.awt.Color(65, 93, 120));
        btn_saveMenuItem1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btn_saveMenuItem1.setForeground(new java.awt.Color(65, 93, 120));
        btn_saveMenuItem1.setText("Save");
        btn_saveMenuItem1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(65, 93, 120), 2, true));
        btn_saveMenuItem1.setContentAreaFilled(false);
        btn_saveMenuItem1.setFocusPainted(false);
        btn_saveMenuItem1.addActionListener(this::btn_saveMenuItem1Assign_ButtonActionPerformed);
        pnl_promoedit.add(btn_saveMenuItem1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 390, 60, 30));

        btn_chooseImage1.setBackground(new java.awt.Color(65, 93, 120));
        btn_chooseImage1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btn_chooseImage1.setForeground(new java.awt.Color(65, 93, 120));
        btn_chooseImage1.setText("Select Image");
        btn_chooseImage1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(65, 93, 120), 2, true));
        btn_chooseImage1.setContentAreaFilled(false);
        btn_chooseImage1.setFocusPainted(false);
        btn_chooseImage1.addActionListener(this::btn_chooseImage1Assign_ButtonActionPerformed);
        pnl_promoedit.add(btn_chooseImage1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 340, 190, 30));

        bg_today9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bgfd.jpg"))); // NOI18N
        bg_today9.setText("Today");
        pnl_promoedit.add(bg_today9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 470));

        getContentPane().add(pnl_promoedit, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 740, 470));

        pnl_menuedit.setForeground(new java.awt.Color(202, 199, 199));
        pnl_menuedit.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cb_menuCategory.setBackground(new java.awt.Color(255, 255, 255));
        cb_menuCategory.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        cb_menuCategory.setForeground(new java.awt.Color(55, 77, 94));
        cb_menuCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Asian", "European", "Latina", "Western", "Grilled", "Salads", "Dessert" }));
        cb_menuCategory.addActionListener(this::cb_menuCategoryActionPerformed);
        pnl_menuedit.add(cb_menuCategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 110, 190, 30));

        jLabel64.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(55, 77, 94));
        jLabel64.setText("MENU EDITOR");
        pnl_menuedit.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 390, 50));

        jScrollPane10.setForeground(new java.awt.Color(55, 77, 94));

        tbl_menuList.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        tbl_menuList.setForeground(new java.awt.Color(55, 77, 94));
        tbl_menuList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ITEM_ID", "CATEGORY", "DISH_NAME"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_menuList.setOpaque(false);
        jScrollPane10.setViewportView(tbl_menuList);
        if (tbl_menuList.getColumnModel().getColumnCount() > 0) {
            tbl_menuList.getColumnModel().getColumn(0).setResizable(false);
            tbl_menuList.getColumnModel().getColumn(1).setHeaderValue("CATEGORY");
            tbl_menuList.getColumnModel().getColumn(2).setResizable(false);
        }

        pnl_menuedit.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 480, 330));

        jLabel59.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(55, 77, 94));
        jLabel59.setText("Dish Name:");
        pnl_menuedit.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 280, -1, -1));

        jLabel60.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(55, 77, 94));
        jLabel60.setText("Category:");
        pnl_menuedit.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 90, -1, -1));

        btn_deleteMenuItem.setBackground(new java.awt.Color(65, 93, 120));
        btn_deleteMenuItem.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btn_deleteMenuItem.setForeground(new java.awt.Color(65, 93, 120));
        btn_deleteMenuItem.setText("Delete");
        btn_deleteMenuItem.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(65, 93, 120), 2, true));
        btn_deleteMenuItem.setContentAreaFilled(false);
        btn_deleteMenuItem.setFocusPainted(false);
        btn_deleteMenuItem.addActionListener(this::btn_deleteMenuItemAssign_ButtonActionPerformed);
        pnl_menuedit.add(btn_deleteMenuItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 390, 70, 30));

        btn_editMenuItem.setBackground(new java.awt.Color(65, 93, 120));
        btn_editMenuItem.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btn_editMenuItem.setForeground(new java.awt.Color(65, 93, 120));
        btn_editMenuItem.setText("Edit");
        btn_editMenuItem.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(65, 93, 120), 2, true));
        btn_editMenuItem.setContentAreaFilled(false);
        btn_editMenuItem.setFocusPainted(false);
        btn_editMenuItem.addActionListener(this::btn_editMenuItemAssign_ButtonActionPerformed);
        pnl_menuedit.add(btn_editMenuItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 390, 60, 30));

        lbl_imagePreview.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(55, 77, 94)));
        pnl_menuedit.add(lbl_imagePreview, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 150, 190, 120));

        txt_dishName.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_dishName.addActionListener(this::txt_dishNameNew_tableActionPerformed);
        pnl_menuedit.add(txt_dishName, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 300, 190, 30));

        btn_saveMenuItem.setBackground(new java.awt.Color(65, 93, 120));
        btn_saveMenuItem.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btn_saveMenuItem.setForeground(new java.awt.Color(65, 93, 120));
        btn_saveMenuItem.setText("Save");
        btn_saveMenuItem.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(65, 93, 120), 2, true));
        btn_saveMenuItem.setContentAreaFilled(false);
        btn_saveMenuItem.setFocusPainted(false);
        btn_saveMenuItem.addActionListener(this::btn_saveMenuItemAssign_ButtonActionPerformed);
        pnl_menuedit.add(btn_saveMenuItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 390, 60, 30));

        btn_chooseImage.setBackground(new java.awt.Color(65, 93, 120));
        btn_chooseImage.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btn_chooseImage.setForeground(new java.awt.Color(65, 93, 120));
        btn_chooseImage.setText("Select Image");
        btn_chooseImage.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(65, 93, 120), 2, true));
        btn_chooseImage.setContentAreaFilled(false);
        btn_chooseImage.setFocusPainted(false);
        btn_chooseImage.addActionListener(this::btn_chooseImageAssign_ButtonActionPerformed);
        pnl_menuedit.add(btn_chooseImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 340, 190, 30));

        bg_today8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bgfd.jpg"))); // NOI18N
        bg_today8.setText("Today");
        pnl_menuedit.add(bg_today8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 470));

        getContentPane().add(pnl_menuedit, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 740, 470));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_fdeskMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_fdeskMouseEntered
    if (!btn_fdesk.getForeground().equals(new Color(255, 200, 120))) {
        btn_fdesk.setForeground(new Color(255, 200, 120));
    }
    btn_fdesk.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));       
    }//GEN-LAST:event_btn_fdeskMouseEntered

    private void btn_upcomMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_upcomMouseEntered
        if (!btn_upcom.getForeground().equals(new Color(255, 200, 120))) {
            btn_upcom.setForeground(new Color(255, 200, 120));
        }
        btn_upcom.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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

    private void btn_upcomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_upcomActionPerformed
        switchPanel(pnl_upcom);
        setActiveButton(btn_upcom);
        loadUpcomTable(); 
    }//GEN-LAST:event_btn_upcomActionPerformed

    private void btn_fdeskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_fdeskActionPerformed
        switchPanel(pnl_today);
        setActiveButton(btn_fdesk);
        loadSeatTrackerTable(); 
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
        loadHistoryTable(); 
    }//GEN-LAST:event_btn_historyActionPerformed

    
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
        loadEmployeeTable(); 
    }//GEN-LAST:event_btn_empActionPerformed

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
        loadMemberTable();
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
            
            String reportPath = "src/reports/ReportHistory.jasper"; 
            
            JasperReport jr = (JasperReport) net.sf.jasperreports.engine.util.JRLoader.loadObjectFromFile(reportPath);
            
            java.util.Date fromDate = date_historyFrom.getDate();
            java.util.Date toDate = date_historyTo.getDate();
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMMM dd, yyyy");

            String fromStr = (fromDate != null) ? sdf.format(fromDate) : "Beginning";
            String toStr = (toDate != null) ? sdf.format(toDate) : "Present";
            int totalRecords = tbl_history.getRowCount();

            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("fromDateParam", fromStr);
            parameters.put("toDateParam", toStr);
            parameters.put("totalRecordsParam", totalRecords);
            JasperPrint jp = JasperFillManager.fillReport(jr, parameters, dataSource);
            JasperViewer.viewReport(jp, false);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error generating Jasper Report: " + e.getMessage(), "Report Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_histGenerateActionPerformed

    private void txt_membVipgenderNew_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_membVipgenderNew_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_membVipgenderNew_tableActionPerformed

    private void txt_membVipbdayNew_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_membVipbdayNew_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_membVipbdayNew_tableActionPerformed

    private void btn_membeditAssign_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_membeditAssign_ButtonActionPerformed
       int viewRow = tbl_memb.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a member from the table first.");
            return;
        }

        int modelRow = tbl_memb.convertRowIndexToModel(viewRow);
        String targetVipId = tbl_memb.getModel().getValueAt(modelRow, 0).toString(); 

        Connect db = new Connect();
        db.DoConnect();

        String sql = "UPDATE DBHOUSE.VIPACCOUNTS SET F_NAME=?, L_NAME=?, GENDER=?, BDAY=?, CP_NUM=?, EMAIL=?, PASS=? WHERE VIP_ID=?";

        try (PreparedStatement pst = db.con.prepareStatement(sql)) {
            pst.setString(1, txt_membFname.getText().trim());
            pst.setString(2, txt_membLname.getText().trim());
            pst.setString(3, txt_membVipgender.getText().trim());

            pst.setString(4, txt_membVipbday.getText().trim()); 

            pst.setString(5, txt_membCpnum.getText().trim());
            pst.setString(6, txt_membEmail.getText().trim());
            pst.setString(7, txt_membPass.getText().trim());
            pst.setString(8, targetVipId);

            int updated = pst.executeUpdate();
            if (updated > 0) {
                JOptionPane.showMessageDialog(this, "Member details updated successfully!");
                loadMemberTable(); 
                clearMemberFields(); 
            }
            db.con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Update Error: " + ex.getMessage());
        }
    }//GEN-LAST:event_btn_membeditAssign_ButtonActionPerformed

    private void btn_upcomGenerateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_upcomGenerateActionPerformed
        if (tbl_upcom.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No data available to generate a report.", "Empty Table", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            DefaultTableModel filteredData = new DefaultTableModel(
                new String[]{"ID", "DATE", "F_NAME", "L_NAME", "CP_NUM", "TIME", "PAX", "REMARKS"}, 0
            );

            for (int i = 0; i < tbl_upcom.getRowCount(); i++) {
               Object[] row = new Object[8]; 
                for (int j = 0; j < 8; j++) { 
                    row[j] = tbl_upcom.getValueAt(i, j); 
                }
                filteredData.addRow(row);
            }

            JRTableModelDataSource dataSource = new JRTableModelDataSource(filteredData);
            
            String reportPath = "src/reports/ReportUpcoming.jasper"; 
            
            JasperReport jr = (JasperReport) net.sf.jasperreports.engine.util.JRLoader.loadObjectFromFile(reportPath);
            
            java.util.Date fromDate = date_upcomFrom.getDate();
            java.util.Date toDate = date_upcomTo.getDate();
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMMM dd, yyyy");

            String fromStr = (fromDate != null) ? sdf.format(fromDate) : "Beginning";
            String toStr = (toDate != null) ? sdf.format(toDate) : "Present";
            int totalRecords = tbl_upcom.getRowCount();

            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("fromDateParam", fromStr);
            parameters.put("toDateParam", toStr);
            parameters.put("totalRecordsParam", totalRecords);
            JasperPrint jp = JasperFillManager.fillReport(jr, parameters, dataSource);
            JasperViewer.viewReport(jp, false);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error generating Jasper Report: " + e.getMessage(), "Report Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_upcomGenerateActionPerformed

    private void btn_upcomResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_upcomResetActionPerformed
        date_upcomFrom.setDate(null);
        date_upcomTo.setDate(null);
        
        if (sorter_upcom != null) {
            sorter_upcom.setRowFilter(null);
        }
    }//GEN-LAST:event_btn_upcomResetActionPerformed

    private void btn_histResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_histResetActionPerformed
        date_historyFrom.setDate(null);
        date_historyTo.setDate(null);
        
        if (sorter_history != null) {
            sorter_history.setRowFilter(null);
        }
    }//GEN-LAST:event_btn_histResetActionPerformed

    private void btn_seatsResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_seatsResetActionPerformed
        date_seats.setDate(null);
         sorter_today.setRowFilter(null);
         sorter_dinner.setRowFilter(null);
    }//GEN-LAST:event_btn_seatsResetActionPerformed

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
        switchPanel(pnl_reserve);
        setActiveButton(btn_reserve);
        loadReserveTable();
    }//GEN-LAST:event_btn_reserveActionPerformed

    private void btn_walkinMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_walkinMouseEntered
        if (!btn_walkin.getForeground().equals(new Color(255, 200, 120))) {
            btn_walkin.setForeground(new Color(255, 200, 120));
        }
        btn_walkin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_btn_walkinMouseEntered

    private void btn_walkinMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_walkinMouseExited
        if (!pnl_walkin.isVisible()) {
            btn_walkin.setForeground(Color.WHITE);
        }      
    }//GEN-LAST:event_btn_walkinMouseExited

    private void btn_walkinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_walkinActionPerformed
        switchPanel(pnl_walkin);
        setActiveButton(btn_walkin);
        loadWalkinTable();
    }//GEN-LAST:event_btn_walkinActionPerformed

    private void btn_inhouseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_inhouseMouseEntered
        if (!btn_inhouse.getForeground().equals(new Color(255, 200, 120))) {
            btn_inhouse.setForeground(new Color(255, 200, 120));
        }
        btn_inhouse.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_btn_inhouseMouseEntered

    private void btn_inhouseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_inhouseMouseExited
        if (!pnl_inhouse.isVisible()) {
            btn_inhouse.setForeground(Color.WHITE);
        }
    }//GEN-LAST:event_btn_inhouseMouseExited

    private void btn_inhouseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_inhouseActionPerformed
        switchPanel(pnl_inhouse);
        setActiveButton(btn_inhouse);
        loadInhouseTable();
    }//GEN-LAST:event_btn_inhouseActionPerformed

    private void txt_membCPnumrsvNew_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_membCPnumrsvNew_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_membCPnumrsvNew_tableActionPerformed

    private void txt_membLnamersvNew_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_membLnamersvNew_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_membLnamersvNew_tableActionPerformed

    private void txt_rsvVIPIDNew_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_rsvVIPIDNew_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rsvVIPIDNew_tableActionPerformed

    private void txt_membFnamersvNew_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_membFnamersvNew_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_membFnamersvNew_tableActionPerformed

    private void txt_rsvIDNew_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_rsvIDNew_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rsvIDNew_tableActionPerformed

    private void txt_rsvPaxNew_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_rsvPaxNew_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rsvPaxNew_tableActionPerformed

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
        
        if (status != null) {
            if (status.toString().equalsIgnoreCase("Cancelled")) {
                JOptionPane.showMessageDialog(this, "This reservation is already cancelled!");
                return;
            } else if (status.toString().equalsIgnoreCase("Completed")) {
                JOptionPane.showMessageDialog(this, "You cannot cancel a reservation that is already completed!");
                return; 
            }
        }

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
                txt_membFnamersv.setText("");
                txt_membLnamersv.setText("");
                txt_membCPnumrsv.setText("");
                txt_rsvTime.setText("");
                txt_rsvPax.setText("");
                txt_rsvRemarks.setText("");

                loadReserveTable();
                db.con.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btn_cancelActionPerformed

    private void txt_rsvRemarksNew_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_rsvRemarksNew_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rsvRemarksNew_tableActionPerformed

    private void txt_rsvTimeNew_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_rsvTimeNew_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rsvTimeNew_tableActionPerformed

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

    private void txt_IHcpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_IHcpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_IHcpActionPerformed

    private void txt_IHcpKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_IHcpKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_IHcpKeyReleased

    private void btn_inhouseaddAssign_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_inhouseaddAssign_ButtonActionPerformed
        addInhouse();
    }//GEN-LAST:event_btn_inhouseaddAssign_ButtonActionPerformed

    private void btn_inhouseeditAssign_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_inhouseeditAssign_ButtonActionPerformed
        editInhouse();
    }//GEN-LAST:event_btn_inhouseeditAssign_ButtonActionPerformed

    private void btn_inhousedelRemove_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_inhousedelRemove_buttonActionPerformed
        deleteInhouse();
    }//GEN-LAST:event_btn_inhousedelRemove_buttonActionPerformed

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

    private void txt_walkinCpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_walkinCpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_walkinCpActionPerformed

    private void txt_walkinCpKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_walkinCpKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_walkinCpKeyReleased

    private void rb_walkinLunchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_walkinLunchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_walkinLunchActionPerformed

    private void txt_walkinFnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_walkinFnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_walkinFnameActionPerformed

    private void txt_walkinFnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_walkinFnameKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_walkinFnameKeyReleased

    private void btn_walkinaddAssign_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_walkinaddAssign_ButtonActionPerformed
        addWalkin();
    }//GEN-LAST:event_btn_walkinaddAssign_ButtonActionPerformed

    private void btn_walkineditAssign_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_walkineditAssign_ButtonActionPerformed
        editWalkin();
    }//GEN-LAST:event_btn_walkineditAssign_ButtonActionPerformed

    private void btn_walkindelRemove_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_walkindelRemove_buttonActionPerformed
        deleteWalkin();
    }//GEN-LAST:event_btn_walkindelRemove_buttonActionPerformed

    private void txt_walkinLnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_walkinLnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_walkinLnameActionPerformed

    private void txt_walkinLnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_walkinLnameKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_walkinLnameKeyReleased

    private void search_walkinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_walkinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_search_walkinActionPerformed

    private void search_walkinKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_walkinKeyReleased
        DefaultTableModel model = (DefaultTableModel) tbl_walkin.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tbl_walkin.setRowSorter(sorter);

        String text = search_walkin.getText();
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
    }//GEN-LAST:event_search_walkinKeyReleased

    private void lbl_dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lbl_dateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_dateActionPerformed

    private void btn_upcomCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_upcomCancelActionPerformed
        int selectedRow = tbl_upcom.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a reservation from the table first!");
            return;
        }

        int modelRow = tbl_upcom.convertRowIndexToModel(selectedRow);
        DefaultTableModel model = (DefaultTableModel) tbl_upcom.getModel();

        String id = model.getValueAt(modelRow, 0).toString();
        Object status = model.getValueAt(modelRow, 7);
        
        if (status != null && status.toString().equalsIgnoreCase("Cancelled")) {
            JOptionPane.showMessageDialog(this, "This reservation is already cancelled!");
            return;
        } else if (status.toString().equalsIgnoreCase("Completed")) {
            JOptionPane.showMessageDialog(this, "You cannot cancel a reservation that is already completed!");
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
                txt_membFnamersv.setText("");
                txt_membLnamersv.setText("");
                txt_membCPnumrsv.setText("");
                txt_rsvTime.setText("");
                txt_rsvPax.setText("");
                txt_rsvRemarks.setText("");

                loadUpcomTable();
                db.con.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "DB Error: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btn_upcomCancelActionPerformed

    private void btn_upcomConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_upcomConfirmActionPerformed
        int selectedRow = tbl_upcom.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a reservation from the table first.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = tbl_upcom.convertRowIndexToModel(selectedRow);
        DefaultTableModel model = (DefaultTableModel) tbl_upcom.getModel();

        String id = model.getValueAt(modelRow, 0).toString();
        Object currentStatus = model.getValueAt(modelRow, 7);

        if (currentStatus != null) {
            if (currentStatus.toString().equalsIgnoreCase("Confirmed")) {
                JOptionPane.showMessageDialog(this, "This reservation is already confirmed.");
                return;
            } else if (currentStatus.toString().equalsIgnoreCase("Cancelled")) {
                JOptionPane.showMessageDialog(this, "You cannot confirm a cancelled reservation.");
                return;
            }
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to confirm reservation " + id + "?", "Confirm Reservation", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            Connect db = new Connect();
            db.DoConnect();

            String sql = "";
            if (id.startsWith("IR")) {
                sql = "UPDATE DBHOUSE.INHOUSERESERVATIONS SET REMARKS = 'Confirmed' WHERE IR_ID = ?";
            } else if (id.startsWith("OR")) {
                sql = "UPDATE DBHOUSE.ONLINERESERVATIONS SET REMARKS = 'Confirmed' WHERE OR_ID = ?";
            } else if (id.startsWith("WI")) {
                sql = "UPDATE DBHOUSE.WALKINDINE SET REMARKS = 'Confirmed' WHERE WI_ID = ?";
            } else {
                JOptionPane.showMessageDialog(this, "Unknown ID format!");
                return;
            }

            try (PreparedStatement pst = db.con.prepareStatement(sql)) {
                pst.setString(1, id);
                int updated = pst.executeUpdate();

                if (updated > 0) {
                    JOptionPane.showMessageDialog(this, "Reservation confirmed successfully!");
                    loadUpcomTable(); 
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to confirm. ID not found.");
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
            } finally {
                try { if (db.con != null) db.con.close(); } catch (SQLException ex) {}
            }
        }
    }//GEN-LAST:event_btn_upcomConfirmActionPerformed

    private void btn_walkincompleteAssign_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_walkincompleteAssign_ButtonActionPerformed
        int selectedRow = tbl_walkin.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a walk-in record from the table first!");
            return;
        }

        int modelRow = tbl_walkin.convertRowIndexToModel(selectedRow);
        DefaultTableModel model = (DefaultTableModel) tbl_walkin.getModel();

        String id = model.getValueAt(modelRow, 0).toString();
        Object status = model.getValueAt(modelRow, 6); 

        if (status != null && (status.toString().equalsIgnoreCase("Completed") || status.toString().equalsIgnoreCase("Cancelled"))) {
            JOptionPane.showMessageDialog(this, "This record is already " + status.toString() + ".");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure the reservation is completed?", "Confirm Completion", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            Connect db = new Connect();
            db.DoConnect();

            String sql = "UPDATE DBHOUSE.WALKINDINE SET REMARKS = 'Completed' WHERE WI_ID = ?";

            try (PreparedStatement pst = db.con.prepareStatement(sql)) {
                pst.setString(1, id);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(this, "Walk-in marked as completed.");
                loadWalkinTable();
                clearWalkinFields();
                db.con.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btn_walkincompleteAssign_ButtonActionPerformed

    private void btn_rsrvcompleteAssign_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_rsrvcompleteAssign_ButtonActionPerformed
       int selectedRow = tbl_reserve.getSelectedRow();
    
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a reservation from the table first!");
            return;
        }

        int modelRow = tbl_reserve.convertRowIndexToModel(selectedRow);
        DefaultTableModel model = (DefaultTableModel) tbl_reserve.getModel();

        String id = model.getValueAt(modelRow, 0).toString();
        Object status = model.getValueAt(modelRow, 7); 

        if (status != null && (status.toString().equalsIgnoreCase("Completed") || status.toString().equalsIgnoreCase("Cancelled"))) {
            JOptionPane.showMessageDialog(this, "This reservation is already " + status.toString() + ".");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure the reservation is completed?", "Confirm Completion", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            Connect db = new Connect();
            db.DoConnect();

            String sql = "";
            if (id.startsWith("IR")) {
                sql = "UPDATE DBHOUSE.INHOUSERESERVATIONS SET REMARKS = 'Completed' WHERE IR_ID = ?";
            } else if (id.startsWith("OR")) {
                sql = "UPDATE DBHOUSE.ONLINERESERVATIONS SET REMARKS = 'Completed' WHERE OR_ID = ?";
            } else {
                JOptionPane.showMessageDialog(this, "Unknown ID format!");
                return;
            }

            try (PreparedStatement pst = db.con.prepareStatement(sql)) {
                pst.setString(1, id);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(this, "Reservation marked as completed.");
                loadReserveTable();
                clearReserveFields();
                db.con.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btn_rsrvcompleteAssign_ButtonActionPerformed

    private void search_empaccKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_empaccKeyReleased
        DefaultTableModel model = (DefaultTableModel) tbl_emp.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tbl_emp.setRowSorter(sorter);

        String text = search_empacc.getText();
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
    }//GEN-LAST:event_search_empaccKeyReleased

    private void search_empaccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_empaccActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_search_empaccActionPerformed

    private void txt_empusernameNew_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_empusernameNew_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_empusernameNew_tableActionPerformed

    private void txt_empfnameNew_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_empfnameNew_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_empfnameNew_tableActionPerformed

    private void txt_emplnameNew_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_emplnameNew_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_emplnameNew_tableActionPerformed

    private void btn_accaddAssign_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_accaddAssign_ButtonActionPerformed

        String user = txt_empusername.getText().trim();
        String fname = txt_empfname.getText().trim();
        String lname = txt_emplname.getText().trim();
        String password = txt_emppass.getText().trim();

        String role = "";
        if (rb_manager.isSelected()) role = "Manager";
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

    private void btn_acceditAssign_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_acceditAssign_ButtonActionPerformed

        int viewRow = tbl_emp.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an account first.");
            return;
        }

        int modelRow = tbl_emp.convertRowIndexToModel(viewRow);
        String originalUser = tbl_emp.getModel().getValueAt(modelRow, 1).toString();

        String role = rb_manager.isSelected() ? "Manager" : "Front Desk";

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
        String userToDelete = tbl_emp.getModel().getValueAt(modelRow, 1).toString();

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

/*
    private void txt_empfnameNew_tableActionPerformed(java.awt.event.ActionEvent evt) {                                                      
        // TODO add your handling code here:
    }                                                     

    private void search_membActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void search_membKeyReleased(java.awt.event.KeyEvent evt) {                                           
        // TODO add your handling code here:
    }                                          

    private void btn_membersMouseEntered(java.awt.event.MouseEvent evt) {                                      
        // TODO add your handling code here:
    }                                     

    private void btn_membersMouseExited(java.awt.event.MouseEvent evt) {                                     
        // TODO add your handling code here:
    }                                    

    private void btn_membersActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
    }                                        
*/
    private void rb_managerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_managerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rb_managerActionPerformed

    private void txt_emppassNew_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_emppassNew_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_emppassNew_tableActionPerformed

    private void txt_dishNameNew_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_dishNameNew_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dishNameNew_tableActionPerformed

    private void btn_chooseImageAssign_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_chooseImageAssign_ButtonActionPerformed
       javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Images", "jpg", "png", "jpeg"));
    
    if (fileChooser.showOpenDialog(this) == javax.swing.JFileChooser.APPROVE_OPTION) {
        selectedMenuImage = fileChooser.getSelectedFile();
        
        javax.swing.ImageIcon icon = new javax.swing.ImageIcon(selectedMenuImage.getAbsolutePath());
        java.awt.Image scaled = icon.getImage().getScaledInstance(lbl_imagePreview.getWidth(), lbl_imagePreview.getHeight(), java.awt.Image.SCALE_SMOOTH);
        lbl_imagePreview.setIcon(new javax.swing.ImageIcon(scaled));
    }
    }//GEN-LAST:event_btn_chooseImageAssign_ButtonActionPerformed

    private void btn_saveMenuItemAssign_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveMenuItemAssign_ButtonActionPerformed
        String category = cb_menuCategory.getSelectedItem().toString();
    String dishName = txt_dishName.getText().trim();
    
    if (dishName.isEmpty() || selectedMenuImage == null) {
        JOptionPane.showMessageDialog(this, "Please enter a dish name and select an image.", "Incomplete", JOptionPane.WARNING_MESSAGE);
        return;
    }

    Connect db = new Connect();
    db.DoConnect();

    try {
        // 1. Check the 5-item limit
        String countSql = "SELECT COUNT(*) FROM DBHOUSE.MENU_ITEMS WHERE CATEGORY = ?";
        PreparedStatement countPst = db.con.prepareStatement(countSql);
        countPst.setString(1, category);
        ResultSet rs = countPst.executeQuery();

        String insertSql = "INSERT INTO DBHOUSE.MENU_ITEMS (CATEGORY, DISH_NAME, IMAGE_DATA) VALUES (?, ?, ?)";
        PreparedStatement insertPst = db.con.prepareStatement(insertSql);
        insertPst.setString(1, category);
        insertPst.setString(2, dishName);
        
        java.io.FileInputStream fis = new java.io.FileInputStream(selectedMenuImage);
        insertPst.setBlob(3, fis);
        
        insertPst.executeUpdate();
        JOptionPane.showMessageDialog(this, "Dish added successfully!");
        
        txt_dishName.setText("");
        lbl_imagePreview.setIcon(null);
        selectedMenuImage = null;
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error saving menu item: " + e.getMessage());
    }
    
    loadMenuTable();
    }//GEN-LAST:event_btn_saveMenuItemAssign_ButtonActionPerformed

    private void btn_deleteMenuItemAssign_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteMenuItemAssign_ButtonActionPerformed
        int selectedRow = tbl_menuList.getSelectedRow();

    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select a dish from the table to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int modelRow = tbl_menuList.convertRowIndexToModel(selectedRow);
    DefaultTableModel model = (DefaultTableModel) tbl_menuList.getModel();
    
    String itemId = model.getValueAt(modelRow, 0).toString();
    String dishName = model.getValueAt(modelRow, 2).toString();

    int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete '" + dishName + "' from the menu?", 
            "Confirm Delete", JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
        Connect db = new Connect();
        db.DoConnect();

        String sql = "DELETE FROM DBHOUSE.MENU_ITEMS WHERE ITEM_ID = ?";

        try (PreparedStatement pst = db.con.prepareStatement(sql)) {
            pst.setString(1, itemId);
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Dish deleted successfully.");
                loadMenuTable(); 
            } else {
                JOptionPane.showMessageDialog(this, "Error: Dish not found in the database.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
        } finally {
            try { if (db.con != null) db.con.close(); } catch (SQLException ex) {}
        }
    }
    clearMenuFields();
    }//GEN-LAST:event_btn_deleteMenuItemAssign_ButtonActionPerformed

    private void btn_menuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_menuMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_menuMouseEntered

    private void btn_menuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_menuMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_menuMouseExited

    private void btn_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_menuActionPerformed
        txt_dishName.setVisible(true);
        switchPanel(pnl_menuedit);
        setActiveButton(btn_menu);
        loadMenuTable(); 
    }//GEN-LAST:event_btn_menuActionPerformed

    private void btn_editMenuItemAssign_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editMenuItemAssign_ButtonActionPerformed
        if (editingMenuItemId == -1) {
        JOptionPane.showMessageDialog(this, "Please select a dish from the table to edit.", "No Selection", JOptionPane.WARNING_MESSAGE);
        return;
    }

    String category = cb_menuCategory.getSelectedItem().toString();
    String dishName = txt_dishName.getText().trim();
    
    if (dishName.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Dish name cannot be empty.", "Incomplete", JOptionPane.WARNING_MESSAGE);
        return;
    }

    Connect db = new Connect();
    db.DoConnect();

    try {
        if (selectedMenuImage != null) {
            String sql = "UPDATE DBHOUSE.MENU_ITEMS SET CATEGORY = ?, DISH_NAME = ?, IMAGE_DATA = ? WHERE ITEM_ID = ?";
            PreparedStatement pst = db.con.prepareStatement(sql);
            pst.setString(1, category);
            pst.setString(2, dishName);
            
            java.io.FileInputStream fis = new java.io.FileInputStream(selectedMenuImage);
            pst.setBlob(3, fis);
            pst.setInt(4, editingMenuItemId);
            
            pst.executeUpdate();
        } 
        else {
            String sql = "UPDATE DBHOUSE.MENU_ITEMS SET CATEGORY = ?, DISH_NAME = ? WHERE ITEM_ID = ?";
            PreparedStatement pst = db.con.prepareStatement(sql);
            pst.setString(1, category);
            pst.setString(2, dishName);
            pst.setInt(3, editingMenuItemId);
            
            pst.executeUpdate();
        }
        
        JOptionPane.showMessageDialog(this, "Dish updated successfully!");
        
        txt_dishName.setText("");
        lbl_imagePreview.setIcon(null);
        lbl_imagePreview.setText("Image Preview"); 
        editingMenuItemId = -1;
        tbl_menuList.clearSelection();
        
        loadMenuTable(); 
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error updating menu item: " + e.getMessage());
    } finally {
        try { if (db.con != null) db.con.close(); } catch (SQLException ex) {}
    }
    clearMenuFields();
    }//GEN-LAST:event_btn_editMenuItemAssign_ButtonActionPerformed

    private void cb_menuCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_menuCategoryActionPerformed
   
    String selectedCategory = cb_menuCategory.getSelectedItem().toString();
    
    if (sorter_menuList != null) {
        sorter_menuList.setRowFilter(RowFilter.regexFilter("^" + selectedCategory + "$", 1));
    }
    
    clearMenuFields();
    }//GEN-LAST:event_cb_menuCategoryActionPerformed

    private void btn_deleteMenuItem1Assign_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteMenuItem1Assign_ButtonActionPerformed
        int selectedRow = tbl_menuList1.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a promo from the table to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = tbl_menuList1.convertRowIndexToModel(selectedRow);
        DefaultTableModel model = (DefaultTableModel) tbl_menuList1.getModel();
        
        String id = model.getValueAt(modelRow, 0).toString();
        String promoName = model.getValueAt(modelRow, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete promo '" + promoName + "'?", 
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            Connect db = new Connect();
            db.DoConnect();

            String sql = "DELETE FROM DBHOUSE.PROMO_ITEMS WHERE PROMO_ID = ?";

            try (PreparedStatement pst = db.con.prepareStatement(sql)) {
                pst.setString(1, id);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(this, "Promo deleted successfully.");
                loadPromoTable();
                clearPromoFields();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
            } finally {
                try { if (db.con != null) db.con.close(); } catch (SQLException ex) {}
            }
        }
    }//GEN-LAST:event_btn_deleteMenuItem1Assign_ButtonActionPerformed

    private void btn_editMenuItem1Assign_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editMenuItem1Assign_ButtonActionPerformed
        int selectedRow = tbl_menuList1.getSelectedRow();
        if (selectedRow == -1 || editingPromoId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a promo from the table to edit.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String promoName = txt_dishName1.getText().trim();
        if (promoName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Promo name cannot be empty.", "Incomplete", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Connect db = new Connect();
        db.DoConnect();

        try {
            if (selectedPromoImage != null) { 
                String sql = "UPDATE DBHOUSE.PROMO_ITEMS SET PROMO_NAME = ?, IMAGE_DATA = ? WHERE PROMO_ID = ?";
                PreparedStatement pst = db.con.prepareStatement(sql);
                pst.setString(1, promoName);
                
                java.io.FileInputStream fis = new java.io.FileInputStream(selectedPromoImage);
                pst.setBlob(2, fis);
                pst.setInt(3, editingPromoId);
                
                pst.executeUpdate();
            } else { 
                String sql = "UPDATE DBHOUSE.PROMO_ITEMS SET PROMO_NAME = ? WHERE PROMO_ID = ?";
                PreparedStatement pst = db.con.prepareStatement(sql);
                pst.setString(1, promoName);
                pst.setInt(2, editingPromoId);
                
                pst.executeUpdate();
            }
            
            JOptionPane.showMessageDialog(this, "Promo updated successfully!");
            loadPromoTable();
            clearPromoFields();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating promo: " + e.getMessage());
        } finally {
            try { if (db.con != null) db.con.close(); } catch (SQLException ex) {}
        }
    }//GEN-LAST:event_btn_editMenuItem1Assign_ButtonActionPerformed

    private void txt_dishName1New_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_dishName1New_tableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dishName1New_tableActionPerformed

    private void btn_saveMenuItem1Assign_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveMenuItem1Assign_ButtonActionPerformed
        String promoName = txt_dishName1.getText().trim();
        
        if (promoName.isEmpty() || selectedPromoImage == null) {
            JOptionPane.showMessageDialog(this, "Please enter a promo name and select an image.", "Incomplete", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Connect db = new Connect();
        db.DoConnect();

        try {
            String countSql = "SELECT COUNT(*) FROM DBHOUSE.PROMO_ITEMS";
            PreparedStatement countPst = db.con.prepareStatement(countSql);
            ResultSet rs = countPst.executeQuery();

            if (rs.next() && rs.getInt(1) >= 2) {
                JOptionPane.showMessageDialog(this, "Maximum limit of 2 Promos reached! Please delete one first.", "Limit Reached", JOptionPane.ERROR_MESSAGE);
                db.con.close();
                return;
            }

            String insertSql = "INSERT INTO DBHOUSE.PROMO_ITEMS (PROMO_NAME, IMAGE_DATA) VALUES (?, ?)";
            PreparedStatement insertPst = db.con.prepareStatement(insertSql);
            insertPst.setString(1, promoName);
            
            java.io.FileInputStream fis = new java.io.FileInputStream(selectedPromoImage);
            insertPst.setBlob(2, fis);
            
            insertPst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Promo added successfully!");
            
            loadPromoTable();
            clearPromoFields();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving promo: " + e.getMessage());
        } finally {
            try { if (db.con != null) db.con.close(); } catch (SQLException ex) {}
        }
    }//GEN-LAST:event_btn_saveMenuItem1Assign_ButtonActionPerformed

    private void btn_chooseImage1Assign_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_chooseImage1Assign_ButtonActionPerformed
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Images", "jpg", "png", "jpeg"));
        
        if (fileChooser.showOpenDialog(this) == javax.swing.JFileChooser.APPROVE_OPTION) {
            selectedPromoImage = fileChooser.getSelectedFile();
            
            javax.swing.ImageIcon icon = new javax.swing.ImageIcon(selectedPromoImage.getAbsolutePath());
            java.awt.Image scaled = icon.getImage().getScaledInstance(lbl_imagePreview1.getWidth(), lbl_imagePreview1.getHeight(), java.awt.Image.SCALE_SMOOTH);
            lbl_imagePreview1.setIcon(new javax.swing.ImageIcon(scaled));
            lbl_imagePreview1.setText("");
        }
    
    }//GEN-LAST:event_btn_chooseImage1Assign_ButtonActionPerformed

    private void btn_promosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_promosMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_promosMouseEntered

    private void btn_promosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_promosMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_promosMouseExited

    private void btn_promosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_promosActionPerformed
        
        switchPanel(pnl_promoedit);
        setActiveButton(btn_promos);
        loadPromoTable();
    }//GEN-LAST:event_btn_promosActionPerformed

    //HELPER METHODS
    
    //STYLING, LISTENERS, GETTERS, SORTERS
    private void makeFlatButton(javax.swing.JButton btn) {
        btn.setFocusPainted(false);
        btn.setBorder(null);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        
}
    private void setActiveButton(javax.swing.JButton activeBtn) {
        javax.swing.JButton[] buttons = {btn_fdesk, btn_emp,btn_members, btn_upcom, btn_history, btn_reserve, btn_walkin, btn_inhouse,btn_menu,btn_promos};

        for (javax.swing.JButton btn : buttons) {
            btn.setForeground(Color.WHITE);
        }
        activeBtn.setForeground(new Color(255, 200, 120));
    }
    private void switchPanel(javax.swing.JPanel targetPanel) {
        pnl_inhouse.setVisible(false);
        pnl_walkin.setVisible(false);
        pnl_reserve.setVisible(false);
        pnl_today.setVisible(false);
        pnl_history.setVisible(false);
        pnl_upcom.setVisible(false);
        pnl_emp.setVisible(false);
        pnl_memb.setVisible(false);
        pnl_menuedit.setVisible(false);
        pnl_promoedit.setVisible(false);
        targetPanel.setVisible(true);
    }
    private void styleTableHeaders(javax.swing.JTable... tables) {
        for (javax.swing.JTable table : tables) {
            DefaultTableCellRenderer hr = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
            hr.setHorizontalAlignment(JLabel.CENTER);
            table.getTableHeader().setForeground(new Color(55, 77, 94));
            table.getTableHeader().setFont(new java.awt.Font("Century Gothic", java.awt.Font.BOLD, 14));
        }
    }
    private void addSearchListener(javax.swing.JTextField textField, TableRowSorter<DefaultTableModel> sorter) {
        textField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { update(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { update(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { update(); }

            private void update() {
                String text = textField.getText().trim();
                if (text.isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
    }
    private void centerTableData(javax.swing.JTable... tables) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (javax.swing.JTable table : tables) {
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }
    }
    private TableRowSorter<DefaultTableModel> setupSorter(javax.swing.JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        return sorter;
    }
    private int[] getSeatBreakdown(java.util.Date date, String time) {
        int reserved = 0;
        int walkin = 0;
        Connect db = new Connect();
        db.DoConnect();

        if (db.con != null) {
            String query = 
                "SELECT " +
                "  SUM(CASE WHEN source != 'WALKINDINE' THEN PAX ELSE 0 END) AS RESERVED_TOTAL, " +
                "  SUM(CASE WHEN source = 'WALKINDINE' THEN PAX ELSE 0 END) AS WALKIN_TOTAL " +
                "FROM (" +
                "  SELECT 'INHOUSE' as source, PAX FROM DBHOUSE.INHOUSERESERVATIONS WHERE D_DATE = ? AND D_TIME = ? AND REMARKS != 'Cancelled' " +
                "  UNION ALL " +
                "  SELECT 'ONLINE' as source, PAX FROM DBHOUSE.ONLINERESERVATIONS WHERE D_DATE = ? AND D_TIME = ? AND REMARKS != 'Cancelled' " +
                "  UNION ALL " +
                "  SELECT 'WALKINDINE' as source, PAX FROM DBHOUSE.WALKINDINE WHERE D_DATE = ? AND D_TIME = ? " +
                ") t";

            try (PreparedStatement pst = db.con.prepareStatement(query)) {
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                for (int i = 1; i <= 5; i += 2) {
                    pst.setDate(i, sqlDate);
                    pst.setString(i + 1, time);
                }

                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        reserved = rs.getInt("RESERVED_TOTAL");
                        walkin = rs.getInt("WALKIN_TOTAL");
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Database Error while checking seats: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                try { db.con.close(); } catch (SQLException ex) {}
            }
        }
        return new int[]{reserved, walkin};
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
    
    
    private void showPaymentProof(String orId) {
        Connect db = new Connect();
        db.DoConnect();

        if (db.con != null) {
            try (PreparedStatement pst = db.con.prepareStatement("SELECT PAYMENT_PROOF FROM DBHOUSE.ONLINERESERVATIONS WHERE OR_ID = ?")) {
                pst.setString(1, orId);

                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        java.sql.Blob blob = rs.getBlob("PAYMENT_PROOF");

                        if (blob != null) {
                            byte[] imageBytes = blob.getBytes(1, (int) blob.length());
                            javax.swing.ImageIcon icon = new javax.swing.ImageIcon(imageBytes);

                            java.awt.Image img = icon.getImage();
                            int origW = img.getWidth(null);
                            int origH = img.getHeight(null);

                            int maxW = 700;
                            int maxH = 600;
                            
                            java.awt.Image displayImage = img;

                            if (origW > maxW || origH > maxH) {
                                double scale = Math.min((double) maxW / origW, (double) maxH / origH);
                                int newW = (int) (origW * scale);
                                int newH = (int) (origH * scale);
                                displayImage = img.getScaledInstance(newW, newH, java.awt.Image.SCALE_SMOOTH);
                            }

                            icon = new javax.swing.ImageIcon(displayImage);

                            JOptionPane.showMessageDialog(null, new JLabel(icon), "Payment Screenshot - " + orId, JOptionPane.PLAIN_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(this, "Image not found in the database.");
                        }
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error loading image: " + e.getMessage());
            } finally {
                try { db.con.close(); } catch (SQLException ex) {}
            }
        }
    }

   // EMP
    private void loadEmployeeTable() {
        DefaultTableModel model = (DefaultTableModel) tbl_emp.getModel();
        model.setRowCount(0); 

        Connect db = new Connect();
        db.DoConnect();
        try (PreparedStatement pst = db.con.prepareStatement("SELECT EMP_ID, USERNAME, F_NAME, L_NAME, PASS, ACC_TYPE FROM DBHOUSE.EMPACCOUNTS");
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("EMP_ID"),
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
    private void clearAccFields() {
        txt_empusername.setText("");
        txt_empfname.setText("");
        txt_emplname.setText("");
        txt_emppass.setText("");
        buttonGroup1.clearSelection();
        tbl_emp.clearSelection();
        editingEmpRow = -1;
    }
    
    //MEMB
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
     private void clearMemberFields() {
        txt_membVipId.setText("");
        txt_membDatereg.setText("");
        txt_membFname.setText("");
        txt_membLname.setText("");
        txt_membVipgender.setText("");
        txt_membVipbday.setText("");
        txt_membCpnum.setText("");
        txt_membEmail.setText("");
        txt_membPass.setText("");
        tbl_memb.clearSelection();
        editingMembRow = -1;
}
    
     //HIST
   private void loadHistoryTable() {
        DefaultTableModel model = (DefaultTableModel) tbl_history.getModel();
        model.setRowCount(0); 

        Connect db = new Connect();
        db.DoConnect();

        if (db.con != null) {
           String query = 
                "SELECT * FROM (" +
                "SELECT IR_ID AS ID, D_DATE, F_NAME, L_NAME, CP_NUM, D_TIME, PAX, REMARKS, 'N/A' AS REF_NUM, 'N/A' AS PROOF " +
                "FROM DBHOUSE.INHOUSERESERVATIONS WHERE D_DATE < CURRENT_DATE " + 
                "UNION ALL " +
                "SELECT WI_ID AS ID, D_DATE, F_NAME, L_NAME, CP_NUM, D_TIME, PAX, REMARKS, 'N/A' AS REF_NUM, 'N/A' AS PROOF " +
                "FROM DBHOUSE.WALKINDINE WHERE D_DATE < CURRENT_DATE " + 
                "UNION ALL " +
                "SELECT o.OR_ID AS ID, o.D_DATE, v.F_NAME, v.L_NAME, v.CP_NUM, o.D_TIME, o.PAX, o.REMARKS, " +
                "CASE WHEN o.REF_NUM IS NOT NULL AND o.REF_NUM != '' THEN o.REF_NUM ELSE 'N/A' END AS REF_NUM, " +
                "CASE WHEN o.PAYMENT_PROOF IS NOT NULL THEN 'View Receipt' ELSE 'N/A' END AS PROOF " +
                "FROM DBHOUSE.ONLINERESERVATIONS o " +
                "JOIN DBHOUSE.VIPACCOUNTS v ON o.VIP_ID = v.VIP_ID " +
                "WHERE o.D_DATE < CURRENT_DATE " + 
                ") t " +
                "ORDER BY D_DATE ASC, CASE WHEN UPPER(D_TIME) = 'LUNCH' THEN 1 ELSE 2 END ASC";
                
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
                        rs.getString("REMARKS"),
                        rs.getString("REF_NUM"),
                        rs.getString("PROOF")
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
        java.util.Date rawFrom = date_historyFrom.getDate();
        java.util.Date rawTo = date_historyTo.getDate();

        if (rawFrom == null && rawTo == null) {
            sorter_history.setRowFilter(null);
            return;
        }

        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM-dd-yy");
            
            java.util.Date fromDate = (rawFrom != null) ? sdf.parse(sdf.format(rawFrom)) : null;
            java.util.Date toDate = (rawTo != null) ? sdf.parse(sdf.format(rawTo)) : null;

            RowFilter<DefaultTableModel, Object> rangeFilter = new RowFilter<DefaultTableModel, Object>() {
                @Override
                public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                    String dateStr = entry.getStringValue(1); 
                    try {
                        java.util.Date rowDate = sdf.parse(dateStr);

                        boolean isAfterFrom = (fromDate == null) || !rowDate.before(fromDate);
                        
                        boolean isBeforeTo = (toDate == null) || !rowDate.after(toDate);
                        
                        return isAfterFrom && isBeforeTo;
                    } catch (Exception e) {
                        return false; 
                    }
                }
            };
            sorter_history.setRowFilter(rangeFilter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //UPCOM
  private void loadUpcomTable() {
        DefaultTableModel model = (DefaultTableModel) tbl_upcom.getModel();
        model.setRowCount(0); 

        Connect db = new Connect();
        db.DoConnect();

        if (db.con != null) {
            String query = 
                "SELECT * FROM (" +
                "SELECT IR_ID AS ID, D_DATE, F_NAME, L_NAME, CP_NUM, D_TIME, PAX, REMARKS, 'N/A' AS REF_NUM, 'N/A' AS PROOF " +
                "FROM DBHOUSE.INHOUSERESERVATIONS WHERE D_DATE >= CURRENT_DATE " + 
                "UNION ALL " +
                "SELECT WI_ID AS ID, D_DATE, F_NAME, L_NAME, CP_NUM, D_TIME, PAX, REMARKS, 'N/A' AS REF_NUM, 'N/A' AS PROOF " +
                "FROM DBHOUSE.WALKINDINE WHERE D_DATE >= CURRENT_DATE " + 
                "UNION ALL " +
                "SELECT o.OR_ID AS ID, o.D_DATE, v.F_NAME, v.L_NAME, v.CP_NUM, o.D_TIME, o.PAX, o.REMARKS, " +
                "CASE WHEN o.REF_NUM IS NOT NULL AND o.REF_NUM != '' THEN o.REF_NUM ELSE 'N/A' END AS REF_NUM, " +
                "CASE WHEN o.PAYMENT_PROOF IS NOT NULL THEN 'View Receipt' ELSE 'N/A' END AS PROOF " +
                "FROM DBHOUSE.ONLINERESERVATIONS o " +
                "JOIN DBHOUSE.VIPACCOUNTS v ON o.VIP_ID = v.VIP_ID " +
                "WHERE o.D_DATE >= CURRENT_DATE " + 
                ") t " +
                "ORDER BY D_DATE ASC, CASE WHEN UPPER(D_TIME) = 'LUNCH' THEN 1 ELSE 2 END ASC";

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
                        rs.getString("REMARKS"),
                        rs.getString("REF_NUM"),
                        rs.getString("PROOF") 
                    });
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error upcoming: " + e.getMessage());
            } finally {
                try { db.con.close(); } catch (SQLException ex) {}
            }
        }
    }
    private void filterUpcomByDateRange() {
        java.util.Date rawFrom = date_upcomFrom.getDate();
        java.util.Date rawTo = date_upcomTo.getDate();

        if (rawFrom == null && rawTo == null) {
            sorter_upcom.setRowFilter(null);
        loadUpcomTable();
        }

        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM-dd-yy");
            
            java.util.Date fromDate = (rawFrom != null) ? sdf.parse(sdf.format(rawFrom)) : null;
            java.util.Date toDate = (rawTo != null) ? sdf.parse(sdf.format(rawTo)) : null;

            RowFilter<DefaultTableModel, Object> rangeFilter = new RowFilter<DefaultTableModel, Object>() {
                @Override
                public boolean include(RowFilter.Entry<? extends DefaultTableModel, ? extends Object> entry) {
                    String dateStr = entry.getStringValue(1); 
                    try {
                        java.util.Date rowDate = sdf.parse(dateStr);
                        boolean isAfterFrom = (fromDate == null) || !rowDate.before(fromDate);
                        
                        boolean isBeforeTo = (toDate == null) || !rowDate.after(toDate);
                        
                        return isAfterFrom && isBeforeTo;
                    } catch (Exception e) {
                        return false; 
                    }
                }
            };
            sorter_upcom.setRowFilter(rangeFilter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    //WALKIN
    private void loadWalkinTable() {
        DefaultTableModel model = (DefaultTableModel) tbl_walkin.getModel();
        model.setRowCount(0);
        Connect db = new Connect();
        db.DoConnect();

        String sql = "SELECT WI_ID, F_NAME, L_NAME, CP_NUM, D_TIME, PAX, REMARKS FROM DBHOUSE.WALKINDINE WHERE D_DATE = CURRENT_DATE";

        try (PreparedStatement pst = db.con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("WI_ID"), rs.getString("F_NAME"), rs.getString("L_NAME"),
                    rs.getString("CP_NUM"), rs.getString("D_TIME"), rs.getInt("PAX"), 
                    rs.getString("REMARKS") 
                });
            }
            db.con.close();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    private void addWalkin() {
        if (txt_walkinFname.getText().trim().isEmpty() || txt_walkinLname.getText().trim().isEmpty() || 
            txt_walkinCp.getText().trim().isEmpty() || getSelectedTime() == null) {
            JOptionPane.showMessageDialog(this, "Please fill out all fields.", "Incomplete Fields", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String phone = txt_walkinCp.getText().trim();
    if (!phone.startsWith("09") || phone.length() != 11) {
        JOptionPane.showMessageDialog(this, "Invalid Mobile Number.", "Validation Error", JOptionPane.WARNING_MESSAGE);
        return;
    }

        java.util.Date today = new java.util.Date(); 
        String mealTime = getSelectedTime();
        int requestedPax = (Integer) spn_walkinpax.getValue();
        int maxCapacity = 100;

        int[] breakdown = getSeatBreakdown(today, mealTime);
        int totalOccupied = breakdown[0] + breakdown[1]; 
        int availableSeats = maxCapacity - totalOccupied;

        if (requestedPax > availableSeats) {
            String message = String.format(
                "Sorry, %d out of %d seats are already reserved for %s today.\n" + "Current Status: (Reserved: %d, Walk-in: %d)\n\n" + "Please change the pax count or advise the customer.",
                totalOccupied, maxCapacity, mealTime, breakdown[0], breakdown[1]
            );
            JOptionPane.showMessageDialog(this, message, "Capacity Reached", JOptionPane.WARNING_MESSAGE);
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
            pst.setString(7, "Confirmed"); 

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
    
    String phone = txt_walkinCp.getText().trim();
    if (!phone.startsWith("09") || phone.length() != 11) {
        JOptionPane.showMessageDialog(this, "Invalid Mobile Number! It must start with '09' and be 11 digits long.", "Validation Error", JOptionPane.WARNING_MESSAGE);
        return;
    }

    java.util.Date today = new java.util.Date(); 
    String newTime = getSelectedTime();
    int newPax = (Integer) spn_walkinpax.getValue();
    int maxCapacity = 100;

    DefaultTableModel model = (DefaultTableModel) tbl_walkin.getModel();
    String oldTime = model.getValueAt(editingWalkinRow, 4).toString().trim();
    int oldPax = Integer.parseInt(model.getValueAt(editingWalkinRow, 5).toString());

    int[] breakdown = getSeatBreakdown(today, newTime);
    int totalOccupied = breakdown[0] + breakdown[1];

    if (newTime.equalsIgnoreCase(oldTime)) {
        totalOccupied -= oldPax;
    }

    int availableSeats = maxCapacity - totalOccupied;

    if (newPax > availableSeats) {
        String message = String.format(
            "Sorry, there are not enough seats to change this walk-in to %d people.\n" +
            "Remaining seats for %s today: %d\n\n" +
            "Please adjust the pax count.",
            newPax, newTime, availableSeats
        );
        JOptionPane.showMessageDialog(this, message, "Capacity Reached", JOptionPane.WARNING_MESSAGE);
        return; // Stops the edit from being saved!
    }
    // --- SEAT CAPACITY CHECK FOR EDITING END ---

    String id = model.getValueAt(editingWalkinRow, 0).toString();
    Connect db = new Connect();
    db.DoConnect();
    String sql = "UPDATE DBHOUSE.WALKINDINE SET D_TIME=?, PAX=?, F_NAME=?, L_NAME=?, CP_NUM=? WHERE WI_ID=?";

    try (PreparedStatement pst = db.con.prepareStatement(sql)) {
        pst.setString(1, newTime);
        pst.setInt(2, newPax); 
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
        
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete walk-in record " + id 
                + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
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

    //INHOUSE
    private void loadInhouseTable() {
        DefaultTableModel model = (DefaultTableModel) tbl_inhouse.getModel();
        model.setRowCount(0);
        Connect db = new Connect();
        db.DoConnect();
        
        String sql = "SELECT IR_ID, D_DATE, F_NAME, L_NAME, D_TIME, PAX, REMARKS FROM DBHOUSE.INHOUSERESERVATIONS WHERE D_DATE >= CURRENT_DATE";
        
        try (PreparedStatement pst = db.con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("IR_ID"), 
                    rs.getDate("D_DATE"), 
                    rs.getString("F_NAME"),
                    rs.getString("L_NAME"), 
                    rs.getString("D_TIME"), 
                    rs.getInt("PAX"),
                    rs.getString("REMARKS")
                });
            }
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
    }
    private void addInhouse() {
        if (dc_inhouse.getDate() == null || txt_IHfname.getText().trim().isEmpty() || 
            txt_IHlname.getText().trim().isEmpty() || txt_IHcp.getText().trim().isEmpty() || 
            getSelectedTimeIH() == null) {
            JOptionPane.showMessageDialog(this, "Please fill out all fields.", "Incomplete Fields", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String phone = txt_IHcp.getText().trim();
    if (!phone.startsWith("09") || phone.length() != 11) {
        JOptionPane.showMessageDialog(this, "Invalid Mobile Number! It must start with '09' and be 11 digits long.", "Validation Error", JOptionPane.WARNING_MESSAGE);
        return;
    }

        java.util.Date selectedDate = dc_inhouse.getDate();
        String mealTime = getSelectedTimeIH();
        int requestedPax = (Integer) spn_inhousepax.getValue();
        int maxCapacity = 100;

        int[] breakdown = getSeatBreakdown(selectedDate, mealTime);
        int totalOccupied = breakdown[0] + breakdown[1]; 
        int availableSeats = maxCapacity - totalOccupied;

        if (requestedPax > availableSeats) {
            String message = String.format(
                "Sorry, %d out of %d seats are already reserved for %s on that date.\n" +
                "Current Status: (Reserved: %d, Walk-in: %d)\n\n" +
                "Please choose a different date or change the pax count.",
                totalOccupied, maxCapacity, mealTime, breakdown[0], breakdown[1]
            );
            JOptionPane.showMessageDialog(this, message, "Capacity Reached", JOptionPane.WARNING_MESSAGE);
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
            pst.setString(8, "Confirmed"); 

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Reservation successfully added.");
            loadInhouseTable();
            clearInhouseFields();
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
    
    String phone = txt_IHcp.getText().trim();
    if (!phone.startsWith("09") || phone.length() != 11) {
        JOptionPane.showMessageDialog(this, "Invalid Mobile Number! It must start with '09' and be 11 digits long.", "Validation Error", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // --- SEAT CAPACITY CHECK FOR EDITING START ---
    java.util.Date newDate = dc_inhouse.getDate();
    String newTime = getSelectedTimeIH();
    int newPax = (Integer) spn_inhousepax.getValue();
    int maxCapacity = 100;

    // Get the old values from the table
    DefaultTableModel model = (DefaultTableModel) tbl_inhouse.getModel();
    java.util.Date oldDate = (java.util.Date) model.getValueAt(editingInhouseRow, 1);
    String oldTime = model.getValueAt(editingInhouseRow, 4).toString().trim();
    int oldPax = Integer.parseInt(model.getValueAt(editingInhouseRow, 5).toString());

    int[] breakdown = getSeatBreakdown(newDate, newTime);
    int totalOccupied = breakdown[0] + breakdown[1];

    // Format dates to ignore exact hours/seconds when comparing them
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
    boolean isSameDate = sdf.format(newDate).equals(sdf.format(oldDate));
    boolean isSameTime = newTime.equalsIgnoreCase(oldTime);

    // CRITICAL: If the date and meal time haven't changed, subtract their old seats 
    // from the occupied count so we don't double-count them!
    if (isSameDate && isSameTime) {
        totalOccupied -= oldPax;
    }

    int availableSeats = maxCapacity - totalOccupied;

    if (newPax > availableSeats) {
        String message = String.format(
            "Sorry, there are not enough seats to change this reservation to %d people.\n" +
            "Remaining seats for %s on that date: %d\n\n" +
            "Please adjust the pax count or choose a different slot.",
            newPax, newTime, availableSeats
        );
        JOptionPane.showMessageDialog(this, message, "Capacity Reached", JOptionPane.WARNING_MESSAGE);
        return; // Stops the edit from being saved!
    }
    // --- SEAT CAPACITY CHECK FOR EDITING END ---

    String id = model.getValueAt(editingInhouseRow, 0).toString();
    Connect db = new Connect();
    db.DoConnect();
    String sql = "UPDATE DBHOUSE.INHOUSERESERVATIONS SET D_DATE=?, D_TIME=?, PAX=?, F_NAME=?, L_NAME=?, CP_NUM=? WHERE IR_ID=?";

    try (PreparedStatement pst = db.con.prepareStatement(sql)) {
        pst.setDate(1, new java.sql.Date(newDate.getTime()));
        pst.setString(2, newTime);
        pst.setInt(3, newPax);
        pst.setString(4, txt_IHfname.getText().trim());
        pst.setString(5, txt_IHlname.getText().trim());
        pst.setString(6, txt_IHcp.getText().trim());
        pst.setString(7, id);

        pst.executeUpdate();
        JOptionPane.showMessageDialog(this, "Reservation updated successfully!");
        loadInhouseTable();
        clearInhouseFields();
        db.con.close();
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
    
    //RSRV
    private void loadReserveTable() {
        DefaultTableModel model = (DefaultTableModel) tbl_reserve.getModel();
        model.setRowCount(0);
        Connect db = new Connect();
        db.DoConnect();
        
        String sql = 
            "SELECT IR_ID AS ID, 'N/A' AS VIP_ID, F_NAME, L_NAME, CP_NUM, D_TIME AS TIME, PAX, REMARKS " +
            "FROM DBHOUSE.INHOUSERESERVATIONS WHERE D_DATE = CURRENT_DATE AND REMARKS != 'Processing' " +
            "UNION ALL " +
            "SELECT o.OR_ID AS ID, o.VIP_ID, v.F_NAME, v.L_NAME, v.CP_NUM, o.D_TIME AS TIME, o.PAX, o.REMARKS " +
            "FROM DBHOUSE.ONLINERESERVATIONS o " +
            "JOIN DBHOUSE.VIPACCOUNTS v ON o.VIP_ID = v.VIP_ID " +
            "WHERE o.D_DATE = CURRENT_DATE AND o.REMARKS != 'Processing'";
                     
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
        txt_membFnamersv.setText("");
        txt_membLnamersv.setText(""); 
        txt_membCPnumrsv.setText(""); 
        txt_rsvTime.setText("");
        txt_rsvPax.setText(""); 
        txt_rsvRemarks.setText("");
        tbl_reserve.clearSelection();
        editingRow = -1;
    }
    
    //SEAT
     private void loadSeatTrackerTable() {
        DefaultTableModel lunchModel = (DefaultTableModel) tbl_seatsLunch.getModel();
        DefaultTableModel dinnerModel = (DefaultTableModel) tbl_seatsDinner.getModel();
        lunchModel.setRowCount(0);
        dinnerModel.setRowCount(0);
        
        Connect db = new Connect();
        db.DoConnect();

        if (db.con != null) {
            String query = 
                "SELECT D_DATE, D_TIME, SUM(PAX) AS TOTAL_OCCUPIED, (100 - SUM(PAX)) AS AVAILABLE " +
                "FROM (" +
                "  SELECT D_DATE, D_TIME, PAX FROM DBHOUSE.INHOUSERESERVATIONS WHERE REMARKS != 'Cancelled' AND REMARKS != 'Processing' " +
                "  UNION ALL " +
                "  SELECT D_DATE, D_TIME, PAX FROM DBHOUSE.ONLINERESERVATIONS WHERE REMARKS != 'Cancelled' AND REMARKS != 'Processing'" +
                "  UNION ALL " +
                "  SELECT D_DATE, D_TIME, PAX FROM DBHOUSE.WALKINDINE " +
                ") t " +
                "GROUP BY D_DATE, D_TIME " +
                "ORDER BY D_DATE ASC";

            try (PreparedStatement pst = db.con.prepareStatement(query);
                 ResultSet rs = pst.executeQuery()) {
                
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM-dd-yy");
                
                while (rs.next()) {
                    java.sql.Date sqlDate = rs.getDate("D_DATE");
                    String time = rs.getString("D_TIME");
                    Object[] rowData = new Object[]{
                        (sqlDate != null) ? sdf.format(sqlDate) : "",
                        rs.getInt("TOTAL_OCCUPIED"),
                        rs.getInt("AVAILABLE")
                    };
                    
                    if (time != null && time.equalsIgnoreCase("Lunch")) {
                        lunchModel.addRow(rowData);
                    } else if (time != null && time.equalsIgnoreCase("Dinner")) {
                        dinnerModel.addRow(rowData);
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error loading seats: " + e.getMessage());
            } finally {
                try { db.con.close(); } catch (SQLException ex) {}
            }
        }
    }
     
     private void loadMenuTable() {
    DefaultTableModel model = (DefaultTableModel) tbl_menuList.getModel();
    model.setRowCount(0); 

    Connect db = new Connect();
    db.DoConnect();

    String sql = "SELECT ITEM_ID, CATEGORY, DISH_NAME FROM DBHOUSE.MENU_ITEMS ORDER BY CATEGORY, DISH_NAME";

    try (PreparedStatement pst = db.con.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {
         
        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("ITEM_ID"),
                rs.getString("CATEGORY"),
                rs.getString("DISH_NAME")
            });
        }
        
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error loading menu items: " + e.getMessage());
    } finally {
        try { if (db.con != null) db.con.close(); } catch (SQLException ex) {}
    }
    cb_menuCategoryActionPerformed(null);
}
     private void clearMenuFields() {
    txt_dishName.setText("");
    //cb_menuCategory.setSelectedIndex(0); 
    lbl_imagePreview.setIcon(null);
    lbl_imagePreview.setText("Image Preview"); 
    
    selectedMenuImage = null;
    editingMenuItemId = -1;
    editingMenuRow = -1;
    
    tbl_menuList.clearSelection();
}
     
     
     private void loadPromoTable() {
        DefaultTableModel model = (DefaultTableModel) tbl_menuList1.getModel();
        model.setRowCount(0); 

        Connect db = new Connect();
        db.DoConnect();

        String sql = "SELECT PROMO_ID, PROMO_NAME FROM DBHOUSE.PROMO_ITEMS";

        try (PreparedStatement pst = db.con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
             
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("PROMO_ID"),
                    rs.getString("PROMO_NAME")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading promos: " + e.getMessage());
        } finally {
            try { if (db.con != null) db.con.close(); } catch (SQLException ex) {}
        }
    }

    private void clearPromoFields() {
        txt_dishName1.setText("");
        lbl_imagePreview1.setIcon(null);
        lbl_imagePreview1.setText("Image Preview"); 
        
        selectedPromoImage = null;
        editingPromoId = -1;
        editingPromoRow = -1;
        
        tbl_menuList1.clearSelection();
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
    private javax.swing.ButtonGroup bg_IHtime;
    private javax.swing.JLabel bg_today;
    private javax.swing.JLabel bg_today1;
    private javax.swing.JLabel bg_today2;
    private javax.swing.JLabel bg_today3;
    private javax.swing.JLabel bg_today4;
    private javax.swing.JLabel bg_today5;
    private javax.swing.JLabel bg_today6;
    private javax.swing.JLabel bg_today7;
    private javax.swing.JLabel bg_today8;
    private javax.swing.JLabel bg_today9;
    private javax.swing.ButtonGroup bg_walkinTime;
    private javax.swing.JButton btn_accadd;
    private javax.swing.JButton btn_accdel;
    private javax.swing.JButton btn_accedit;
    private javax.swing.JButton btn_cancel;
    private javax.swing.JButton btn_chooseImage;
    private javax.swing.JButton btn_chooseImage1;
    private javax.swing.JButton btn_deleteMenuItem;
    private javax.swing.JButton btn_deleteMenuItem1;
    private javax.swing.JButton btn_editMenuItem;
    private javax.swing.JButton btn_editMenuItem1;
    private javax.swing.JButton btn_emp;
    private javax.swing.JButton btn_fdesk;
    private javax.swing.JButton btn_histGenerate;
    private javax.swing.JButton btn_histReset;
    private javax.swing.JButton btn_history;
    private javax.swing.JButton btn_inhouse;
    private javax.swing.JButton btn_inhouseadd;
    private javax.swing.JButton btn_inhousedel;
    private javax.swing.JButton btn_inhouseedit;
    private javax.swing.JButton btn_membedit;
    private javax.swing.JButton btn_members;
    private javax.swing.JButton btn_menu;
    private javax.swing.JButton btn_navLogout;
    private javax.swing.JButton btn_promos;
    private javax.swing.JButton btn_reserve;
    private javax.swing.JButton btn_rsrvcomplete;
    private javax.swing.JButton btn_saveMenuItem;
    private javax.swing.JButton btn_saveMenuItem1;
    private javax.swing.JButton btn_seatsReset;
    private javax.swing.JButton btn_upcom;
    private javax.swing.JButton btn_upcomCancel;
    private javax.swing.JButton btn_upcomConfirm;
    private javax.swing.JButton btn_upcomGenerate;
    private javax.swing.JButton btn_upcomReset;
    private javax.swing.JButton btn_walkin;
    private javax.swing.JButton btn_walkinadd;
    private javax.swing.JButton btn_walkincomplete;
    private javax.swing.JButton btn_walkindel;
    private javax.swing.JButton btn_walkinedit;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cb_menuCategory;
    private com.toedter.calendar.JDateChooser date_historyFrom;
    private com.toedter.calendar.JDateChooser date_historyTo;
    private com.toedter.calendar.JDateChooser date_seats;
    private com.toedter.calendar.JDateChooser date_upcomFrom;
    private com.toedter.calendar.JDateChooser date_upcomTo;
    private com.toedter.calendar.JDateChooser dc_inhouse;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextField lbl_date;
    private javax.swing.JLabel lbl_imagePreview;
    private javax.swing.JLabel lbl_imagePreview1;
    private javax.swing.JPanel pnl_emp;
    private javax.swing.JPanel pnl_header;
    private javax.swing.JPanel pnl_history;
    private javax.swing.JPanel pnl_inhouse;
    private javax.swing.JPanel pnl_memb;
    private javax.swing.JPanel pnl_menuedit;
    private javax.swing.JPanel pnl_nav;
    private javax.swing.JPanel pnl_promoedit;
    private javax.swing.JPanel pnl_reserve;
    private javax.swing.JPanel pnl_today;
    private javax.swing.JPanel pnl_upcom;
    private javax.swing.JPanel pnl_walkin;
    private javax.swing.JRadioButton rb_IHdinner;
    private javax.swing.JRadioButton rb_IHlunch;
    private javax.swing.JRadioButton rb_fdesk;
    private javax.swing.JRadioButton rb_manager;
    private javax.swing.JRadioButton rb_walkinDinner;
    private javax.swing.JRadioButton rb_walkinLunch;
    private javax.swing.JTextField search_empacc;
    private javax.swing.JTextField search_history;
    private javax.swing.JTextField search_inhouse;
    private javax.swing.JTextField search_memb;
    private javax.swing.JTextField search_reserve;
    private javax.swing.JTextField search_upcom;
    private javax.swing.JTextField search_walkin;
    private javax.swing.JSpinner spn_inhousepax;
    private javax.swing.JSpinner spn_walkinpax;
    private javax.swing.JTable tbl_emp;
    private javax.swing.JTable tbl_history;
    private javax.swing.JTable tbl_inhouse;
    private javax.swing.JTable tbl_memb;
    private javax.swing.JTable tbl_menuList;
    private javax.swing.JTable tbl_menuList1;
    private javax.swing.JTable tbl_reserve;
    private javax.swing.JTable tbl_seatsDinner;
    private javax.swing.JTable tbl_seatsLunch;
    private javax.swing.JTable tbl_upcom;
    private javax.swing.JTable tbl_walkin;
    private javax.swing.JTextField txt_IHcp;
    private javax.swing.JTextField txt_IHfname;
    private javax.swing.JTextField txt_IHlname;
    private javax.swing.JTextField txt_dishName;
    private javax.swing.JTextField txt_dishName1;
    private javax.swing.JTextField txt_empfname;
    private javax.swing.JTextField txt_emplname;
    private javax.swing.JTextField txt_emppass;
    private javax.swing.JTextField txt_empusername;
    private javax.swing.JTextField txt_membCPnumrsv;
    private javax.swing.JTextField txt_membCpnum;
    private javax.swing.JTextField txt_membDatereg;
    private javax.swing.JTextField txt_membEmail;
    private javax.swing.JTextField txt_membFname;
    private javax.swing.JTextField txt_membFnamersv;
    private javax.swing.JTextField txt_membLname;
    private javax.swing.JTextField txt_membLnamersv;
    private javax.swing.JTextField txt_membPass;
    private javax.swing.JTextField txt_membVipId;
    private javax.swing.JTextField txt_membVipbday;
    private javax.swing.JTextField txt_membVipgender;
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

