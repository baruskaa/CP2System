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
public class Manager extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Manager.class.getName());
    private TableRowSorter<DefaultTableModel> sorter_today;
    private TableRowSorter<DefaultTableModel> sorter_dinner;
    private TableRowSorter<DefaultTableModel> sorter_history;
    private TableRowSorter<DefaultTableModel> sorter_upcom;
    private TableRowSorter<DefaultTableModel> sorter_emp;
    private TableRowSorter<DefaultTableModel> sorter_memb;
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
    private int editingMembRow = -1; 
    private int editingEmpRow = -1;
    
    public Manager(String loggedInName) {
        initComponents();
        this.setLocationRelativeTo(null);
        
        lbl_username.setText(loggedInName);
        makeFlatButton(btn_navLogout);
        makeFlatButton(btn_histGenerate);
        makeFlatButton(btn_upcomGenerate);
        makeFlatButton(btn_histReset);
        makeFlatButton(btn_seatsReset);
        makeFlatButton(btn_upcomReset);
        makeFlatButton(btn_cancel);
        
        date_historyTo.setMaxSelectableDate(new java.util.Date());
        loadEmployeeTable();
        loadMemberTable();
        loadHistoryTable();
        loadUpcomTable();
        loadSeatTrackerTable();
        loadInhouseTable();
        loadWalkinTable();
        loadReserveTable();
        
        pnl_reserve.setVisible(true);
        pnl_today.setVisible(false);
        pnl_inhouse.setVisible(false);
        pnl_walkin.setVisible(false);
        pnl_history.setVisible(false);
        pnl_upcom.setVisible(false);
        pnl_emp.setVisible(false);
        pnl_memb.setVisible(false);
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
        
        centerTableData(tbl_reserve, tbl_walkin, tbl_inhouse, tbl_seatsLunch, tbl_seatsDinner, tbl_history, tbl_upcom, tbl_emp, tbl_memb);
        
         //TABLE HEADER CELL RENDERER
         
        styleTableHeaders(tbl_reserve, tbl_walkin, tbl_inhouse, tbl_seatsLunch, tbl_seatsDinner, tbl_history, tbl_upcom, tbl_emp, tbl_memb);
         
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
        startupKeys.add(new javax.swing.RowSorter.SortKey(1, javax.swing.SortOrder.ASCENDING)); 
        startupKeys.add(new javax.swing.RowSorter.SortKey(5, javax.swing.SortOrder.ASCENDING)); 
        sorter_history.setSortKeys(startupKeys);
        
        
        java.util.List<javax.swing.RowSorter.SortKey> reserveSortKeys = new java.util.ArrayList<>();
        reserveSortKeys.add(new javax.swing.RowSorter.SortKey(5, javax.swing.SortOrder.ASCENDING)); 
        reserveSortKeys.add(new javax.swing.RowSorter.SortKey(0, javax.swing.SortOrder.ASCENDING)); 
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

                        txt_membVipId.setText(vipId != null ? vipId.toString() : "");
                        txt_membDatereg.setText(dateReg != null ? dateReg.toString() : "");
                        txt_membFname.setText(fName != null ? fName.toString() : "");
                        txt_membLname.setText(lName != null ? lName.toString() : "");
                        txt_membVipgender.setText(gender != null ? gender.toString() : "");
                        txt_membVipbday.setText(bday != null ? bday.toString() : "");
                        txt_membCpnum.setText(cpNum != null ? cpNum.toString() : "");
                        txt_membEmail.setText(email != null ? email.toString() : "");
                    }
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
        pnl_header = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lbl_date = new javax.swing.JTextField();
        lbl_username = new javax.swing.JLabel();
        pnl_reserve = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        txt_membCPnumrsv = new javax.swing.JTextField();
        txt_membLnamersv = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        txt_rsvVIPID = new javax.swing.JTextField();
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
        date_upcomFrom = new com.toedter.calendar.JDateChooser();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
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
        pnl_nav.add(btn_emp, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 140, 30));

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
        pnl_nav.add(btn_members, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 140, 30));

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

        getContentPane().add(pnl_nav, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 580));

        pnl_header.setBackground(new java.awt.Color(55, 91, 115));
        pnl_header.setMaximumSize(new java.awt.Dimension(681, 115));
        pnl_header.setPreferredSize(new java.awt.Dimension(681, 115));

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("MANAGER DASHBOARD");

        lbl_date.setBackground(new java.awt.Color(55, 91, 115));
        lbl_date.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lbl_date.setForeground(new java.awt.Color(255, 255, 255));
        lbl_date.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        lbl_date.setText("Date");
        lbl_date.setBorder(null);
        lbl_date.addActionListener(this::lbl_dateActionPerformed);

        lbl_username.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        lbl_username.setForeground(new java.awt.Color(255, 255, 255));
        lbl_username.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_username.setText("Delos Santos, Rhianne Leigh Anne");

        javax.swing.GroupLayout pnl_headerLayout = new javax.swing.GroupLayout(pnl_header);
        pnl_header.setLayout(pnl_headerLayout);
        pnl_headerLayout.setHorizontalGroup(
            pnl_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_headerLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
                .addGroup(pnl_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_headerLayout.createSequentialGroup()
                        .addGap(181, 181, 181)
                        .addComponent(lbl_date, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbl_username, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        pnl_headerLayout.setVerticalGroup(
            pnl_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_headerLayout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addGroup(pnl_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_headerLayout.createSequentialGroup()
                        .addComponent(lbl_username)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2))
                .addGap(23, 23, 23))
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
        pnl_reserve.add(txt_rsvPax, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 380, 110, 30));

        jLabel41.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(55, 77, 94));
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel41.setText("Pax:");
        pnl_reserve.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 380, 40, 30));

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

        jLabel42.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(55, 77, 94));
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel42.setText("Remarks:  ");
        pnl_reserve.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 70, 30));

        txt_rsvTime.setEditable(false);
        txt_rsvTime.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_rsvTime.setFocusable(false);
        txt_rsvTime.addActionListener(this::txt_rsvTimeNew_tableActionPerformed);
        pnl_reserve.add(txt_rsvTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 350, 110, 30));

        jLabel43.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(55, 77, 94));
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel43.setText("Time:");
        pnl_reserve.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 350, 50, 30));

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

        spn_inhousepax.setModel(new javax.swing.SpinnerNumberModel(1, null, 100, 1));
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
        jScrollPane8.setViewportView(tbl_inhouse);

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
        pnl_walkin.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 160, 110, -1));

        jLabel54.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(55, 77, 94));
        jLabel54.setText("Pax:");
        pnl_walkin.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 340, -1, -1));

        txt_walkinCp.addActionListener(this::txt_walkinCpActionPerformed);
        txt_walkinCp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_walkinCpKeyReleased(evt);
            }
        });
        pnl_walkin.add(txt_walkinCp, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 250, 160, 30));

        jLabel55.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(55, 77, 94));
        jLabel55.setText("Time:");
        pnl_walkin.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 290, -1, -1));

        jLabel56.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(55, 77, 94));
        jLabel56.setText("CP Num:");
        pnl_walkin.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 230, 80, -1));

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

        jScrollPane9.setForeground(new java.awt.Color(55, 77, 94));

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
        tbl_upcom.setOpaque(false);
        jScrollPane6.setViewportView(tbl_upcom);
        if (tbl_upcom.getColumnModel().getColumnCount() > 0) {
            tbl_upcom.getColumnModel().getColumn(0).setResizable(false);
            tbl_upcom.getColumnModel().getColumn(1).setResizable(false);
            tbl_upcom.getColumnModel().getColumn(2).setResizable(false);
            tbl_upcom.getColumnModel().getColumn(3).setResizable(false);
            tbl_upcom.getColumnModel().getColumn(4).setResizable(false);
            tbl_upcom.getColumnModel().getColumn(5).setResizable(false);
            tbl_upcom.getColumnModel().getColumn(6).setResizable(false);
            tbl_upcom.getColumnModel().getColumn(7).setResizable(false);
        }

        pnl_upcom.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 700, 330));

        btn_upcomGenerate.setBackground(new java.awt.Color(55, 77, 94));
        btn_upcomGenerate.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        btn_upcomGenerate.setForeground(new java.awt.Color(255, 255, 255));
        btn_upcomGenerate.setText("GENERATE REPORT");
        btn_upcomGenerate.setBorder(null);
        btn_upcomGenerate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_upcomGenerate.addActionListener(this::btn_upcomGenerateActionPerformed);
        pnl_upcom.add(btn_upcomGenerate, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 420, 140, 30));

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
        jLabel21.setText("VIP ID:  ");
        pnl_memb.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 350, 50, 30));

        txt_membLname.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_membLname.addActionListener(this::txt_membLnameNew_tableActionPerformed);
        pnl_memb.add(txt_membLname, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 380, 140, 30));

        jLabel25.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(55, 77, 94));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel25.setText("Last Name:");
        pnl_memb.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 380, 70, 30));

        txt_membFname.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_membFname.addActionListener(this::txt_membFnameNew_tableActionPerformed);
        pnl_memb.add(txt_membFname, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 350, 140, 30));

        txt_membVipId.setEditable(false);
        txt_membVipId.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_membVipId.setFocusable(false);
        txt_membVipId.addActionListener(this::txt_membVipIdNew_tableActionPerformed);
        pnl_memb.add(txt_membVipId, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 350, 90, 30));

        jLabel29.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(55, 77, 94));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel29.setText("First Name:");
        pnl_memb.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 350, 70, 30));

        txt_membDatereg.setEditable(false);
        txt_membDatereg.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_membDatereg.setFocusable(false);
        txt_membDatereg.addActionListener(this::txt_membDateregNew_tableActionPerformed);
        pnl_memb.add(txt_membDatereg, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 380, 90, 30));

        jLabel30.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(55, 77, 94));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel30.setText("Date Reg:  ");
        pnl_memb.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 380, 70, 30));

        txt_membVipgender.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_membVipgender.addActionListener(this::txt_membVipgenderNew_tableActionPerformed);
        pnl_memb.add(txt_membVipgender, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 410, 90, 30));

        jLabel22.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(55, 77, 94));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("Gender:  ");
        pnl_memb.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 410, 60, 30));

        txt_membVipbday.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_membVipbday.addActionListener(this::txt_membVipbdayNew_tableActionPerformed);
        pnl_memb.add(txt_membVipbday, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 410, 140, 30));

        jLabel23.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(55, 77, 94));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel23.setText("Birthday:");
        pnl_memb.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 410, 50, 30));

        txt_membEmail.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_membEmail.addActionListener(this::txt_membEmailNew_tableActionPerformed);
        pnl_memb.add(txt_membEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 380, 140, 30));

        jLabel27.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(55, 77, 94));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel27.setText("Email:");
        pnl_memb.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 380, 50, 30));

        txt_membCpnum.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txt_membCpnum.addActionListener(this::txt_membCpnumNew_tableActionPerformed);
        pnl_memb.add(txt_membCpnum, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 350, 140, 30));

        jLabel28.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(55, 77, 94));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel28.setText("CP Num:");
        pnl_memb.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 350, 60, 30));

        jScrollPane5.setForeground(new java.awt.Color(55, 77, 94));

        tbl_memb.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        tbl_memb.setForeground(new java.awt.Color(55, 77, 94));
        tbl_memb.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"juandcruz@gmail.com", null, "Juan ", "Dela Cuz", null, null, null, null},
                {"jane.santos@houseofseven.com", null, "Jane ", "Santos", null, null, null, null},
                {"admin@email.com", null, "admin", "admin", null, null, null, null}
            },
            new String [] {
                "VIP_ID", "DATE_REG", "F_NAME", "L_NAME", "GENDER", "BDAY", "CP_NUM", "EMAIL"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class
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
        tbl_memb.setOpaque(false);
        jScrollPane5.setViewportView(tbl_memb);
        if (tbl_memb.getColumnModel().getColumnCount() > 0) {
            tbl_memb.getColumnModel().getColumn(0).setResizable(false);
            tbl_memb.getColumnModel().getColumn(1).setResizable(false);
            tbl_memb.getColumnModel().getColumn(2).setResizable(false);
            tbl_memb.getColumnModel().getColumn(3).setResizable(false);
            tbl_memb.getColumnModel().getColumn(4).setResizable(false);
            tbl_memb.getColumnModel().getColumn(5).setResizable(false);
            tbl_memb.getColumnModel().getColumn(6).setResizable(false);
            tbl_memb.getColumnModel().getColumn(7).setResizable(false);
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
                Object[] row = new Object[tbl_upcom.getColumnCount()];
                for (int j = 0; j < tbl_upcom.getColumnCount(); j++) {
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
        javax.swing.JButton[] buttons = {btn_fdesk, btn_emp,btn_members, btn_upcom, btn_history, btn_reserve, btn_walkin, btn_inhouse};

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
        try (PreparedStatement pst = db.con.prepareStatement("SELECT VIP_ID, DATE_REG, F_NAME, L_NAME, GENDER, BDAY, CP_NUM, EMAIL FROM DBHOUSE.VIPACCOUNTS");
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
                    rs.getString("EMAIL")
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
                "SELECT IR_ID AS ID, D_DATE, F_NAME, L_NAME, CP_NUM, D_TIME, PAX, REMARKS " +
                "FROM DBHOUSE.INHOUSERESERVATIONS WHERE D_DATE < CURRENT_DATE " + 
                "UNION ALL " +
                "SELECT WI_ID AS ID, D_DATE, F_NAME, L_NAME, CP_NUM, D_TIME, PAX, REMARKS " +
                "FROM DBHOUSE.WALKINDINE WHERE D_DATE < CURRENT_DATE " + 
                "UNION ALL " +
                "SELECT o.OR_ID AS ID, o.D_DATE, v.F_NAME, v.L_NAME, v.CP_NUM, o.D_TIME, o.PAX, o.REMARKS " +
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
                "SELECT IR_ID AS ID, D_DATE, F_NAME, L_NAME, CP_NUM, D_TIME, PAX, REMARKS " +
                "FROM DBHOUSE.INHOUSERESERVATIONS WHERE D_DATE >= CURRENT_DATE " + 
                "UNION ALL " +
                "SELECT WI_ID AS ID, D_DATE, F_NAME, L_NAME, CP_NUM, D_TIME, PAX, REMARKS " +
                "FROM DBHOUSE.WALKINDINE WHERE D_DATE >= CURRENT_DATE " + 
                "UNION ALL " +
                "SELECT o.OR_ID AS ID, o.D_DATE, v.F_NAME, v.L_NAME, v.CP_NUM, o.D_TIME, o.PAX, o.REMARKS " +
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
                        rs.getString("REMARKS")
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

    //INHOUSE
    private void loadInhouseTable() {
        DefaultTableModel model = (DefaultTableModel) tbl_inhouse.getModel();
        model.setRowCount(0);
        Connect db = new Connect();
        db.DoConnect();
        
        String sql = "SELECT IR_ID, D_DATE, F_NAME, L_NAME, D_TIME, PAX FROM DBHOUSE.INHOUSERESERVATIONS WHERE D_DATE >= CURRENT_DATE";
        
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
    
    //RSRV
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
                "  SELECT D_DATE, D_TIME, PAX FROM DBHOUSE.INHOUSERESERVATIONS WHERE REMARKS != 'Cancelled' " +
                "  UNION ALL " +
                "  SELECT D_DATE, D_TIME, PAX FROM DBHOUSE.ONLINERESERVATIONS WHERE REMARKS != 'Cancelled' " +
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
        java.awt.EventQueue.invokeLater(() -> new Manager(null).setVisible(true));
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
    private javax.swing.ButtonGroup bg_walkinTime;
    private javax.swing.JButton btn_accadd;
    private javax.swing.JButton btn_accdel;
    private javax.swing.JButton btn_accedit;
    private javax.swing.JButton btn_cancel;
    private javax.swing.JButton btn_emp;
    private javax.swing.JButton btn_fdesk;
    private javax.swing.JButton btn_histGenerate;
    private javax.swing.JButton btn_histReset;
    private javax.swing.JButton btn_history;
    private javax.swing.JButton btn_inhouse;
    private javax.swing.JButton btn_inhouseadd;
    private javax.swing.JButton btn_inhousedel;
    private javax.swing.JButton btn_inhouseedit;
    private javax.swing.JButton btn_members;
    private javax.swing.JButton btn_navLogout;
    private javax.swing.JButton btn_reserve;
    private javax.swing.JButton btn_seatsReset;
    private javax.swing.JButton btn_upcom;
    private javax.swing.JButton btn_upcomGenerate;
    private javax.swing.JButton btn_upcomReset;
    private javax.swing.JButton btn_walkin;
    private javax.swing.JButton btn_walkinadd;
    private javax.swing.JButton btn_walkindel;
    private javax.swing.JButton btn_walkinedit;
    private javax.swing.ButtonGroup buttonGroup1;
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
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextField lbl_date;
    private javax.swing.JLabel lbl_username;
    private javax.swing.JPanel pnl_emp;
    private javax.swing.JPanel pnl_header;
    private javax.swing.JPanel pnl_history;
    private javax.swing.JPanel pnl_inhouse;
    private javax.swing.JPanel pnl_memb;
    private javax.swing.JPanel pnl_nav;
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
    private javax.swing.JTable tbl_reserve;
    private javax.swing.JTable tbl_seatsDinner;
    private javax.swing.JTable tbl_seatsLunch;
    private javax.swing.JTable tbl_upcom;
    private javax.swing.JTable tbl_walkin;
    private javax.swing.JTextField txt_IHcp;
    private javax.swing.JTextField txt_IHfname;
    private javax.swing.JTextField txt_IHlname;
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

