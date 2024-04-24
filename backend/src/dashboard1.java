
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Lenovo
 */
public class dashboard1 extends javax.swing.JFrame {
    File f = null;
    String path = null;
    private ImageIcon format = null;
    
  
    
    
    
            
    

    /**
     * Creates new form dashboard1
     */
    

    
    public dashboard1(String msg) {
        initComponents();
   
        ActionListener dtr = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Calendar cal = Calendar.getInstance();
                String ampm = null, hour = null;
                if (String.valueOf(cal.get(Calendar.AM_PM)).equals("0")) {
                    ampm = "AM";
                } else if (String.valueOf(cal.get(Calendar.AM_PM)).equals("1")) {
                    ampm = "PM";
                }
                if (String.valueOf(cal.get(Calendar.HOUR)).equals("0")) {
                    hour = "12";
                } else {
                    hour = String.valueOf(cal.get(Calendar.HOUR));
                }
                timer.setText(hour + ":" + String.valueOf(cal.get(Calendar.MINUTE)) + ":"
                        + String.valueOf(cal.get(Calendar.SECOND)) + " " + ampm);
                int month = cal.get(cal.MONTH);
                int year = cal.get(cal.YEAR);
                int day = cal.get(cal.DAY_OF_MONTH);
                int days_of_week = cal.get(cal.DAY_OF_WEEK);
                int sumOfDay, due, s1, s2;

                String a, b, c;
                a = String.valueOf(month);
                b = String.valueOf(year);
                c = String.valueOf(day);
                String[] strings = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
//        strings[11] = "11";
                if (a.equals("0")) {
                    date.setText(strings[0] + " " + c + ",  " + b);
                    months.setText(strings[0]);
                    months2.setText("1");
                } else if (a.equals("1")) {
                    date.setText(strings[1] + " " + c + ",  " + b);
                    months.setText(strings[1]);
                    months2.setText("2");
                } else if (a.equals("2")) {
                    date.setText(strings[2] + " " + c + ",  " + b);
                    months.setText(strings[2]);
                    months2.setText("3");
                } else if (a.equals("3")) {
                    date.setText(strings[3] + " " + c + ",  " + b);
                    months.setText(strings[3]);
                    months2.setText("4");
                } else if (a.equals("4")) {
                    date.setText(strings[4] + " " + c + ",  " + b);
                    months.setText(strings[4]);
                    months2.setText("5");
                } else if (a.equals("5")) {
                    date.setText(strings[5] + " " + c + ",  " + b);
                    months.setText(strings[5]);
                    months2.setText("6");
                } else if (a.equals("6")) {
                    date.setText(strings[6] + " " + c + ",  " + b);
                    months.setText(strings[6]);
                    months2.setText("7");
                } else if (a.equals("7")) {
                    date.setText(strings[7] + " " + c + ",  " + b);
                    months.setText(strings[7]);
                    months2.setText("8");
                } else if (a.equals("8")) {
                    date.setText(strings[8] + " " + c + ", " + b);
                    months.setText(strings[8]);
                    months2.setText("9");
                } else if (a.equals("9")) {
                    date.setText(strings[9] + " " + c + ", " + b);
                    months.setText(strings[9]);
                    months2.setText("10");
                } else if (a.equals("10")) {
                    date.setText(strings[10] + " " + c + ", " + b);
                    months.setText(strings[10]);
                    months2.setText("11");
                } else if (a.equals("11")) {
                    date.setText(strings[11] + " " + c + ", " + b);
                    months.setText(strings[11]);
                    months2.setText("12");
                } else {
                }
                years.setText(b);
                days.setText(c);
            }
        };
        new Timer(1, dtr).start();
        userd.setText(msg);
    }
    public void show_user(){
       try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost/shoeskopodb?user=root&password=";
            Connection con = DriverManager.getConnection(url);

            DefaultTableModel table1 = (DefaultTableModel) itemlist.getModel();

            Statement stmt11 = null;
            ResultSet rs11 = null;
            String sql11 = "SELECT * FROM inventory ";
            stmt11 = con.createStatement();
            rs11 = stmt11.executeQuery(sql11);
            int y1 = 0;

            while (y1 < table1.getRowCount()) {//for rowcount
                table1.removeRow(0);
            }
            while (rs11.next()) {//result from dbase content
                String a = rs11.getString("productID");
                String a1 = rs11.getString("productName");
                String a2 = rs11.getString("price");
                String a3 = rs11.getString("stock");
                String a4 = rs11.getString("sold");
                table1.addRow(new Object[]{
                    a, a1, a2,a3,a4
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } 
       
    }
   
    
         public void showReports(String date){
        try {
        Class.forName("com.mysql.jdbc.Driver");
	String url = "jdbc:mysql://localhost/shoeskopodb?user=root&password=";
        Connection con = DriverManager.getConnection(url);

        DefaultTableModel table = (DefaultTableModel) reports_table.getModel();

        Statement stmt4 = null;
        ResultSet rs4 = null;
        //String SQL4 = "select * from completed_orders WHERE date like '%" + date + "%'";
        String SQL4 = "SELECT completed_orders.*, inventory.productName, inventory.price FROM completed_orders INNER JOIN inventory ON completed_orders.productID = inventory.productID WHERE date like '%" + date + "%'";
        stmt4 = con.createStatement();
        rs4 = stmt4.executeQuery(SQL4);

        int y = 0;
        //   if(rs4.next()){
        while (y < table.getRowCount()) {
            table.removeRow(0);
        }
        while (rs4.next()) {
            String a1 = rs4.getString("productName");
            String a2 = rs4.getString("price");
            String a3 = rs4.getString("noOfItems");
            String a4 = rs4.getString("subtotalPrice");
            String a5 = rs4.getString("date");
            String a6 = rs4.getString("time");
           
            

            table.addRow(new Object[]{
                        a1, a2, a3, a4, a5, a6
                    });
        }
        //----------------------------
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
    }  
        
    public dashboard1() {
        initComponents();
        allYear();
       
        Connect();
        
    }
    public void allYear(){
        for (int i = 2023; i>=2000;i--){
          Year.addItem(i+"");
        }
    }
    public void report(){
        String yr = Year.getSelectedItem().toString();
        int index = Month.getSelectedIndex();
        String MT ="";
        
        if(index==0){
            this.showReports("");
        }
        else{
            int mt = index;
            if(mt<10){
                MT="0"+mt;
            }
            else{
                MT=""+mt;
            }
        }
        String allDate = yr+"-"+MT;
        showReports(allDate);
        System.out.println(MT);
        
    }
       
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
        
    public void Connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
             con = DriverManager.getConnection("jdbc:mysql://localhost/shoeskopodb","root","");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(dashboard1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(dashboard1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        months = new javax.swing.JLabel();
        months2 = new javax.swing.JLabel();
        years = new javax.swing.JLabel();
        days = new javax.swing.JLabel();
        imagePath = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        userd = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        date = new javax.swing.JLabel();
        timer = new javax.swing.JLabel();
        Tab = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        productName = new javax.swing.JTextField();
        description = new javax.swing.JTextField();
        brand1 = new javax.swing.JComboBox<>();
        people1 = new javax.swing.JComboBox<>();
        act1 = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        add_item = new javax.swing.JButton();
        productPrice = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        btnBrowse = new javax.swing.JButton();
        labelImage = new javax.swing.JLabel();
        Reset = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        productName1 = new javax.swing.JTextField();
        description1 = new javax.swing.JTextField();
        brand = new javax.swing.JComboBox<>();
        people = new javax.swing.JComboBox<>();
        act = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        add_item1 = new javax.swing.JButton();
        productPrice1 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        search_bar1 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        addstock = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        discountper = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        btnBrowse1 = new javax.swing.JButton();
        labelImage1 = new javax.swing.JLabel();
        productID = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        itemlist = new javax.swing.JTable();
        search_bar2 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        add_item2 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        accounts = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        reports = new javax.swing.JTable();
        search_bar = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        reports_table = new javax.swing.JTable();
        Month = new javax.swing.JComboBox<>();
        Year = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        totalIncome = new javax.swing.JLabel();
        generateTotal = new javax.swing.JButton();

        months.setText("jLabel12");

        months2.setText("jLabel13");

        years.setText("jLabel15");

        days.setText("jLabel14");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1150, 565));
        setUndecorated(true);
        setResizable(false);
        setSize(new java.awt.Dimension(1150, 565));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setPreferredSize(new java.awt.Dimension(250, 560));

        jLabel2.setFont(new java.awt.Font("Helvetica", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Inventory");
        jLabel2.setToolTipText("");
        jLabel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Helvetica", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Admin List");
        jLabel3.setToolTipText("");
        jLabel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Helvetica", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Admin Log");
        jLabel4.setToolTipText("");
        jLabel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Helvetica", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Reports");
        jLabel5.setToolTipText("");
        jLabel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        userd.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        userd.setForeground(new java.awt.Color(51, 255, 255));
        userd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userdMouseClicked(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Helvetica", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Log Out");
        jLabel12.setToolTipText("");
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/logo.png"))); // NOI18N
        jLabel19.setText("jLabel19");

        date.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        date.setForeground(new java.awt.Color(255, 255, 255));
        date.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        date.setText("date");

        timer.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        timer.setForeground(new java.awt.Color(255, 255, 255));
        timer.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        timer.setText("time");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(date, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                    .addComponent(timer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(37, 37, 37)))
                        .addComponent(userd, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(1, 1, 1))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(userd, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(date)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(timer)))
                .addGap(89, 89, 89)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 107, Short.MAX_VALUE)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -7, 250, 580));

        Tab.setFont(new java.awt.Font("Helvetica", 0, 13)); // NOI18N
        Tab.setMaximumSize(new java.awt.Dimension(900, 600));
        Tab.setMinimumSize(new java.awt.Dimension(900, 600));

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setMaximumSize(new java.awt.Dimension(900, 560));
        jPanel2.setMinimumSize(new java.awt.Dimension(900, 560));
        jPanel2.setPreferredSize(new java.awt.Dimension(900, 560));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Inventory");
        jLabel6.setToolTipText("");

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setToolTipText("");
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTabbedPane1MousePressed(evt);
            }
        });

        jPanel7.setBackground(new java.awt.Color(153, 153, 153));

        productName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        description.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        brand1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Select Brand --", "Adidas", "Converse", "Nike" }));

        people1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Select People --", "Men", "Women", "Kids" }));

        act1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Select Activity --", "Casual", "Sport" }));

        jLabel15.setFont(new java.awt.Font("Helvetica", 0, 10)); // NOI18N
        jLabel15.setText("Product Name");

        jLabel16.setFont(new java.awt.Font("Helvetica", 0, 10)); // NOI18N
        jLabel16.setText("Price");

        jLabel17.setFont(new java.awt.Font("Helvetica", 0, 10)); // NOI18N
        jLabel17.setText("Description");

        jLabel18.setFont(new java.awt.Font("Helvetica", 0, 10)); // NOI18N
        jLabel18.setText("Categories");

        add_item.setBackground(new java.awt.Color(51, 51, 51));
        add_item.setFont(new java.awt.Font("Helvetica", 1, 14)); // NOI18N
        add_item.setForeground(new java.awt.Color(255, 255, 255));
        add_item.setText("Add Item");
        add_item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_itemActionPerformed(evt);
            }
        });

        productPrice.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel20.setFont(new java.awt.Font("Helvetica", 0, 10)); // NOI18N
        jLabel20.setText("Upload Image");

        btnBrowse.setText("Browse Image");
        btnBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseActionPerformed(evt);
            }
        });

        labelImage.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        Reset.setBackground(new java.awt.Color(51, 51, 51));
        Reset.setFont(new java.awt.Font("Helvetica", 1, 14)); // NOI18N
        Reset.setForeground(new java.awt.Color(255, 255, 255));
        Reset.setText("Reset");
        Reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(add_item, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Reset, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(productName, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(productPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel17)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(brand1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(people1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(act1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(description, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addComponent(labelImage, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addComponent(btnBrowse, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(48, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(productName, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(description, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(brand1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(act1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(people1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(add_item, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Reset, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(productPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelImage, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBrowse, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34))))
        );

        jTabbedPane1.addTab("Add Item", jPanel7);

        jPanel9.setBackground(new java.awt.Color(153, 153, 153));

        productName1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        description1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        brand.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Select Brand --", "Adidas", "Converse", "Nike" }));
        brand.setBorder(null);

        people.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Select People --", "Men", "Women", "Kids" }));
        people.setBorder(null);

        act.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Select Activity --", "Casual", "Sport" }));
        act.setBorder(null);

        jLabel21.setFont(new java.awt.Font("Helvetica", 0, 10)); // NOI18N
        jLabel21.setText("Product Name");

        jLabel22.setFont(new java.awt.Font("Helvetica", 0, 10)); // NOI18N
        jLabel22.setText("Price");

        jLabel23.setFont(new java.awt.Font("Helvetica", 0, 10)); // NOI18N
        jLabel23.setText("Description");

        jLabel24.setFont(new java.awt.Font("Helvetica", 0, 10)); // NOI18N
        jLabel24.setText("Categories");

        add_item1.setBackground(new java.awt.Color(51, 51, 51));
        add_item1.setFont(new java.awt.Font("Helvetica", 1, 14)); // NOI18N
        add_item1.setForeground(new java.awt.Color(255, 255, 255));
        add_item1.setText("Update Details");
        add_item1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_item1ActionPerformed(evt);
            }
        });

        productPrice1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel25.setFont(new java.awt.Font("Helvetica", 0, 10)); // NOI18N
        jLabel25.setText("Upload Image");

        search_bar1.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        search_bar1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        search_bar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                search_bar1KeyTyped(evt);
            }
        });

        jLabel26.setBackground(new java.awt.Color(0, 0, 0));
        jLabel26.setFont(new java.awt.Font("Helvetica", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Search ID");
        jLabel26.setToolTipText("");
        jLabel26.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel26MouseClicked(evt);
            }
        });

        addstock.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel27.setFont(new java.awt.Font("Helvetica", 0, 10)); // NOI18N
        jLabel27.setText("Add Stocks");

        discountper.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel28.setFont(new java.awt.Font("Helvetica", 0, 10)); // NOI18N
        jLabel28.setText("Discount Percent");

        btnBrowse1.setText("Browse Image");
        btnBrowse1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowse1ActionPerformed(evt);
            }
        });

        labelImage1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        productID.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel30.setFont(new java.awt.Font("Helvetica", 0, 10)); // NOI18N
        jLabel30.setText("Product ID");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(productID, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(130, 130, 130)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(search_bar1)
                        .addGap(14, 14, 14))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(add_item1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBrowse1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(159, 159, 159))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(description1, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel23)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(brand, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(people, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(act, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jLabel24))
                                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel9Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel27)
                                                    .addComponent(addstock, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(jPanel9Layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel28)
                                                    .addComponent(discountper, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(productName1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel21))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel22)
                                            .addComponent(productPrice1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel25)
                                    .addComponent(labelImage1, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel30))
                        .addContainerGap(41, Short.MAX_VALUE))))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(search_bar1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(productID, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(302, 302, 302)
                                .addComponent(btnBrowse1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(labelImage1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(12, 12, 12))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(productName1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addComponent(productPrice1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(description1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(brand, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(addstock, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addComponent(act, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(people, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(discountper, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(add_item1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))))
        );

        jTabbedPane1.addTab("Edit Item", jPanel9);

        jPanel6.setBackground(new java.awt.Color(153, 153, 153));

        itemlist.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product ID", "Product Name", "Price", "Stock", "Sold"
            }
        ));
        jScrollPane5.setViewportView(itemlist);

        search_bar2.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        search_bar2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        search_bar2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                search_bar2KeyTyped(evt);
            }
        });

        jLabel29.setBackground(new java.awt.Color(0, 0, 0));
        jLabel29.setFont(new java.awt.Font("Helvetica", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("Search");
        jLabel29.setToolTipText("");
        jLabel29.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel29MouseClicked(evt);
            }
        });

        add_item2.setBackground(new java.awt.Color(51, 51, 51));
        add_item2.setFont(new java.awt.Font("Helvetica", 1, 14)); // NOI18N
        add_item2.setForeground(new java.awt.Color(255, 255, 255));
        add_item2.setText("Remove Item");
        add_item2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_item2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 830, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(add_item2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(search_bar2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(search_bar2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(add_item2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("View Items List", jPanel6);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 855, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        Tab.addTab("tab1", jPanel2);

        jPanel4.setBackground(new java.awt.Color(102, 102, 102));
        jPanel4.setMaximumSize(new java.awt.Dimension(900, 560));
        jPanel4.setPreferredSize(new java.awt.Dimension(900, 560));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Administrators");
        jLabel8.setToolTipText("");

        jLabel14.setBackground(new java.awt.Color(0, 0, 0));
        jLabel14.setFont(new java.awt.Font("Helvetica", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Search");
        jLabel14.setToolTipText("");
        jLabel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });

        accounts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Username", "Fullname", "Mobile Number", "Address", "Gender", "Email Address"
            }
        ));
        jScrollPane1.setViewportView(accounts);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel8)
                        .addGap(188, 188, 188)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap(32, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 845, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(23, 23, 23))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Tab.addTab("tab3", jPanel4);

        jPanel5.setBackground(new java.awt.Color(102, 102, 102));
        jPanel5.setMaximumSize(new java.awt.Dimension(900, 560));
        jPanel5.setPreferredSize(new java.awt.Dimension(900, 560));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Admin Log");
        jLabel9.setToolTipText("");

        reports.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "User", "Action", "Date", "Time", "Month"
            }
        ));
        jScrollPane2.setViewportView(reports);

        search_bar.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        search_bar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                search_barKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 845, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(250, 250, 250)
                    .addComponent(search_bar)
                    .addGap(251, 251, 251)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(268, 268, 268)
                    .addComponent(search_bar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(260, Short.MAX_VALUE)))
        );

        Tab.addTab("tab4", jPanel5);

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setMaximumSize(new java.awt.Dimension(900, 560));
        jPanel3.setMinimumSize(new java.awt.Dimension(900, 560));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Reports");
        jLabel7.setToolTipText("");

        reports_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Product Name", "Product Price", "Quantity", "Subtotal Price", "Date", "Time"
            }
        ));
        jScrollPane3.setViewportView(reports_table);

        Month.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" }));
        Month.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MonthItemStateChanged(evt);
            }
        });
        Month.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MonthMouseClicked(evt);
            }
        });
        Month.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MonthActionPerformed(evt);
            }
        });

        Year.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                YearItemStateChanged(evt);
            }
        });
        Year.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                YearMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("TOTAL INCOME:");

        totalIncome.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        totalIncome.setForeground(new java.awt.Color(255, 255, 255));
        totalIncome.setText("00000");

        generateTotal.setText("Generate Total Income");
        generateTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateTotalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel7))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 816, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(Month, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Year, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jLabel10)
                                .addGap(66, 66, 66))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(generateTotal)
                                .addGap(206, 206, 206)
                                .addComponent(jLabel1)
                                .addGap(35, 35, 35)
                                .addComponent(totalIncome)))))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(70, 70, 70)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Month, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Year, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(generateTotal)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalIncome))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addContainerGap(68, Short.MAX_VALUE))
        );

        Tab.addTab("tab2", jPanel3);

        getContentPane().add(Tab, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, -30, 900, 600));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        Tab.setSelectedIndex(0);
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        Tab.setSelectedIndex(1);
         try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost/shoeskopodb?user=root&password=";
            Connection con = DriverManager.getConnection(url);

            DefaultTableModel table1 = (DefaultTableModel) accounts.getModel();

            Statement stmt11 = null;
            ResultSet rs11 = null;
            String sql11 = "SELECT * FROM user_admin ";
            stmt11 = con.createStatement();
            rs11 = stmt11.executeQuery(sql11);
            int y1 = 0;

            while (y1 < table1.getRowCount()) {//for rowcount
                table1.removeRow(0);
            }
            while (rs11.next()) {//result from dbase content
                String a = rs11.getString("id");
                String a1 = rs11.getString("username");
                String a2 = rs11.getString("fullname");
                String a3 = rs11.getString("mobile_no");
                String a4 = rs11.getString("address");
                String a5 = rs11.getString("gender");
                String a6 = rs11.getString("email");
                table1.addRow(new Object[]{
                    a, a1, a2,a3,a4,a5,a6
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        Tab.setSelectedIndex(2);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost/shoeskopodb?user=root&password=";
            Connection con = DriverManager.getConnection(url);

            DefaultTableModel table1 = (DefaultTableModel) reports.getModel();

            Statement stmt11 = null;
            ResultSet rs11 = null;
            String sql11 = "SELECT * FROM records ";
            stmt11 = con.createStatement();
            rs11 = stmt11.executeQuery(sql11);
            int y1 = 0;

            while (y1 < table1.getRowCount()) {//for rowcount
                table1.removeRow(0);
            }
            while (rs11.next()) {//result from dbase content
                String a = rs11.getString("username");
                String a1 = rs11.getString("action");
                String a2 = rs11.getString("date");
                String a3 = rs11.getString("time");
                String a4 = rs11.getString("month");
               
                table1.addRow(new Object[]{
                    a, a1, a2, a3,a4,
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        Tab.setSelectedIndex(3);
        
        allYear();
        report();
        
         int numrow = reports_table.getRowCount();
        double sum = 0.00;
        for (int i = 0; i < numrow; i++) {
         
        double val = Double.valueOf(reports_table.getValueAt (i, 3).toString());
        sum += val;
    }
        
     totalIncome.setText("₱"+ Double.toString(sum));
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        Object[] logout = {"Log Out","Cancel"};
        Object defaultChoice = logout[1];
        int reply = JOptionPane.showInternalOptionDialog(null, "Do you want to log out this account?", "ShoesKoPo Administrator", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, logout, defaultChoice);
        if (reply == JOptionPane.YES_OPTION)
        {
                Log_in toLogin = new Log_in();
                toLogin.setVisible(true);
                this.setVisible(false);
        }

        if (logout.equals(0)) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://localhost/shoeskopodb?user=root&password=";
                Connection con = DriverManager.getConnection(url);

                Statement stmt11 = null;
                String SQL11 = "INSERT INTO `shoeskopodb`.`records` (`username`, `action`, `date`, `time`, `month`)"
                        + " VALUES ('" + userd.getText() + "', 'PROGRAM CLOSED', "
                        + "'" + date.getText() + "', '" + timer.getText() + "', '" + months.getText() + "') ;";
                stmt11 = con.createStatement();
                stmt11.executeUpdate(SQL11);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
            System.exit(0);
        } else {
        }
        
    }//GEN-LAST:event_jLabel12MouseClicked

    private void userdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userdMouseClicked


    }//GEN-LAST:event_userdMouseClicked

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
         try {
        Class.forName("com.mysql.jdbc.Driver");
	String url = "jdbc:mysql://localhost/shoeskopodb?user=root&password=";
        Connection con = DriverManager.getConnection(url);

        DefaultTableModel table = (DefaultTableModel) accounts.getModel();

        Statement stmt4 = null;
        ResultSet rs4 = null;
        String SQL4 = "select * from user_admin where fullname like '%" + search_bar.getText() + "%'";
        stmt4 = con.createStatement();
        rs4 = stmt4.executeQuery(SQL4);

        int y = 0;
        //   if(rs4.next()){
        while (y < table.getRowCount()) {
            table.removeRow(0);
        }
        while (rs4.next()) {
            String a = rs4.getString("id");
            String a1 = rs4.getString("username");
            String a2 = rs4.getString("fullname");
            String a3 = rs4.getString("mobile_no");
            String a4 = rs4.getString("address");
            String a5 = rs4.getString("gender");
            String a6 = rs4.getString("email");
            

            table.addRow(new Object[]{
                        a, a1, a2, a3, a4, a5, a6
                    });
        }
        //----------------------------
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
    }//GEN-LAST:event_jLabel14MouseClicked

    private void search_barKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_barKeyTyped
          try {
        Class.forName("com.mysql.jdbc.Driver");
	String url = "jdbc:mysql://localhost/shoeskopodb?user=root&password=";
        Connection con = DriverManager.getConnection(url);

        DefaultTableModel table = (DefaultTableModel) accounts.getModel();

        Statement stmt4 = null;
        ResultSet rs4 = null;
        String SQL4 = "select * from user_admin where fullname like '%" + search_bar.getText() + "%'";
        stmt4 = con.createStatement();
        rs4 = stmt4.executeQuery(SQL4);

        int y = 0;
        //   if(rs4.next()){
        while (y < table.getRowCount()) {
            table.removeRow(0);
        }
        while (rs4.next()) {
            String a = rs4.getString("id");
            String a1 = rs4.getString("username");
            String a2 = rs4.getString("fullname");
            String a3 = rs4.getString("mobile_no");
            String a4 = rs4.getString("address");
            String a5 = rs4.getString("gender");
            String a6 = rs4.getString("email");
            

            table.addRow(new Object[]{
                        a, a1, a2, a3, a4, a5, a6
                    });
        }
        //----------------------------
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
    }//GEN-LAST:event_search_barKeyTyped

    private void add_itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_itemActionPerformed

        File f = new File(path);
        try {
            InputStream is = new FileInputStream(f);
            pst = con.prepareStatement("INSERT INTO inventory(productName,price,description,displayImage,brand,used_for,people)"
                    + "VALUES(?,?,?,?,?,?,?)");
            pst.setString(1,productName.getText());
            pst.setString(2,productPrice.getText());
            pst.setString(3,description.getText());
            pst.setBlob(4, is);
            pst.setString(5,brand1.getSelectedItem().toString());
            pst.setString(6,act1.getSelectedItem().toString());
            pst.setString(7,people1.getSelectedItem().toString());
           
            
            int inserted=pst.executeUpdate();
            if(inserted>0){
                JOptionPane.showMessageDialog(null, "Data Successfully Inserted");
            }
       
        } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_add_itemActionPerformed

    private void add_item1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_item1ActionPerformed
           File f = new File(path);
          
        try {
            InputStream is = new FileInputStream(f);
            pst = con.prepareStatement("UPDATE inventory SET productName=?,price=?,description=?,displayImage=?,"
                    + "brand=?,used_for=?,people=?,stock=?,discount=? WHERE productID=?");
            pst.setString(1,productName1.getText());
            pst.setString(2,productPrice1.getText());
            pst.setString(3,description1.getText());
            pst.setBlob(4, is);
            pst.setString(5,brand.getSelectedItem().toString());
            pst.setString(6,act.getSelectedItem().toString());
            pst.setString(7,people.getSelectedItem().toString());
            pst.setString(8,addstock.getText());
            pst.setString(9,discountper.getText());
            pst.setString(10,productID.getText());
            
       
//            
            int inserted=pst.executeUpdate();
            if(inserted>0){
                JOptionPane.showMessageDialog(null, "Data Upadated Successfully");
                

//               
            
            }
        } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
        
        }
        
    }//GEN-LAST:event_add_item1ActionPerformed

    private void jLabel26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel26MouseClicked
         try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost/shoeskopodb?user=root&password=";
            Connection con = DriverManager.getConnection(url);
            

            //mysql query
            ResultSet rs2 = null;

            String sql2 = "SELECT `productID`,`productName`, `price`, `description`, `displayImage` FROM `inventory` WHERE productId='"+search_bar1.getText()+"'";
            pst = con.prepareStatement(sql2);
            rs2 = pst.executeQuery();
            int id = Integer.parseInt(search_bar1.getText());
          

            if (rs2.next() == false) {
                JOptionPane.showMessageDialog(this, "Sorry Record Not Found");
                productID.setText(null);
                productName1.setText(null);
                productPrice1.setText(null);
                description1.setText(null);
                

            } else {
                productID.setText(rs2.getString("productID"));
                productName1.setText(rs2.getString("productName"));
                productPrice1.setText(rs2.getString("price"));
                description1.setText(rs2.getString("description"));
                
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }

    }//GEN-LAST:event_jLabel26MouseClicked

    private void search_bar1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_bar1KeyTyped
     
    }//GEN-LAST:event_search_bar1KeyTyped

    private void search_bar2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_bar2KeyTyped
         try {
        Class.forName("com.mysql.jdbc.Driver");
	String url = "jdbc:mysql://localhost/shoeskopodb?user=root&password=";
        Connection con = DriverManager.getConnection(url);

        DefaultTableModel table = (DefaultTableModel) itemlist.getModel();

        Statement stmt4 = null;
        ResultSet rs4 = null;
        String SQL4 = "select * from inventory where productID like '%" + search_bar2.getText() + "%'";
        stmt4 = con.createStatement();
        rs4 = stmt4.executeQuery(SQL4);

        int y = 0;
        //   if(rs4.next()){
        while (y < table.getRowCount()) {
            table.removeRow(0);
        }
        while (rs4.next()) {
            String a = rs4.getString("productID");
            String a1 = rs4.getString("productName");
            String a2 = rs4.getString("price");
            String a3 = rs4.getString("stock");
            String a4 = rs4.getString("sold");
           
            

            table.addRow(new Object[]{
                        a, a1, a2, a3, a4
                    });
        }
        //----------------------------
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
    }//GEN-LAST:event_search_bar2KeyTyped

    private void jLabel29MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel29MouseClicked
       try {
        Class.forName("com.mysql.jdbc.Driver");
	String url = "jdbc:mysql://localhost/shoeskopodb?user=root&password=";
        Connection con = DriverManager.getConnection(url);

        DefaultTableModel table = (DefaultTableModel) itemlist.getModel();

        Statement stmt4 = null;
        ResultSet rs4 = null;
        String SQL4 = "select * from inventory where productID like '%" + search_bar1.getText() + "%'";
        stmt4 = con.createStatement();
        rs4 = stmt4.executeQuery(SQL4);

        int y = 0;
        //   if(rs4.next()){
        while (y < table.getRowCount()) {
            table.removeRow(0);
        }
        while (rs4.next()) {
            String a = rs4.getString("productID");
            String a1 = rs4.getString("productName");
            String a2 = rs4.getString("price");
            String a3 = rs4.getString("stock");
            String a4 = rs4.getString("sold");
           
            

            table.addRow(new Object[]{
                        a, a1, a2, a3, a4
                    });
        }
        //----------------------------
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
    }//GEN-LAST:event_jLabel29MouseClicked

    private void btnBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter fnwf = new FileNameExtensionFilter("PNG JPG AND JPEG","png","jpg","jpeg");
        fileChooser.addChoosableFileFilter(fnwf);
        int load = fileChooser.showOpenDialog(null);
        
        if(load==fileChooser.APPROVE_OPTION){
            f = fileChooser.getSelectedFile();
            path = f.getAbsolutePath();
            imagePath.setText(path);
            ImageIcon ii = new ImageIcon(path);
            Image img = ii.getImage().getScaledInstance(400,400,Image.SCALE_SMOOTH);
            labelImage.setIcon(new ImageIcon(img));
            }}//GEN-LAST:event_btnBrowseActionPerformed

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
 try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost/shoeskopodb?user=root&password=";
            Connection con = DriverManager.getConnection(url);

            DefaultTableModel table1 = (DefaultTableModel) itemlist.getModel();

            Statement stmt11 = null;
            ResultSet rs11 = null;
            String sql11 = "SELECT * FROM inventory ";
            stmt11 = con.createStatement();
            rs11 = stmt11.executeQuery(sql11);
            int y1 = 0;

            while (y1 < table1.getRowCount()) {//for rowcount
                table1.removeRow(0);
            }
            while (rs11.next()) {//result from dbase content
                String a = rs11.getString("productID");
                String a1 = rs11.getString("productName");
                String a2 = rs11.getString("price");
                String a3 = rs11.getString("stock");
                String a4 = rs11.getString("sold");
                table1.addRow(new Object[]{
                    a, a1, a2,a3,a4
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }         
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void jTabbedPane1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MousePressed
        
    }//GEN-LAST:event_jTabbedPane1MousePressed

    private void btnBrowse1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowse1ActionPerformed
    JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter fnwf = new FileNameExtensionFilter("PNG JPG AND JPEG","png","jpg","jpeg");
        fileChooser.addChoosableFileFilter(fnwf);
        int load = fileChooser.showOpenDialog(null);
        
        if(load==fileChooser.APPROVE_OPTION){
            f = fileChooser.getSelectedFile();
            path = f.getAbsolutePath();
            imagePath.setText(path);
            ImageIcon ii = new ImageIcon(path);
            Image img = ii.getImage().getScaledInstance(400,400,Image.SCALE_SMOOTH);
            labelImage1.setIcon(new ImageIcon(img));
        }
    }//GEN-LAST:event_btnBrowse1ActionPerformed

    private void add_item2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_item2ActionPerformed
        try{
        Class.forName("com.mysql.jdbc.Driver");
	String url = "jdbc:mysql://localhost/shoeskopodb?user=root&password=";
        Connection con = DriverManager.getConnection(url);
        int row = (itemlist.getSelectedRow());
        String value = (itemlist.getModel().getValueAt(row, 0).toString());
        String query = "DELETE FROM inventory where productID="+value;
        PreparedStatement pst = con.prepareStatement(query);
        pst.executeUpdate();
        DefaultTableModel model = (DefaultTableModel)itemlist.getModel();
        model.setRowCount(0);
        show_user();
        JOptionPane.showMessageDialog(null,"Deleted Successfully");
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }//GEN-LAST:event_add_item2ActionPerformed

    private void ResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetActionPerformed
       
       productName.setText(null);
       productPrice.setText(null);
       description.setText(null);
       brand1.setSelectedItem(0);
       act1.setSelectedItem(0);
       people1.setSelectedItem(0);
    }//GEN-LAST:event_ResetActionPerformed

    private void MonthMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MonthMouseClicked
        
    }//GEN-LAST:event_MonthMouseClicked

    private void MonthItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MonthItemStateChanged
    report();
    }//GEN-LAST:event_MonthItemStateChanged

    private void YearItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_YearItemStateChanged
     report();
    }//GEN-LAST:event_YearItemStateChanged

    private void YearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_YearMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_YearMouseClicked

    private void MonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MonthActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MonthActionPerformed

    private void generateTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateTotalActionPerformed
        int numrow = reports_table.getRowCount();
        double sum = 0.00;
        for (int i = 0; i < numrow; i++) {
         
        double val = Double.valueOf(reports_table.getValueAt (i, 3).toString());
        sum += val;
    }
        
     totalIncome.setText("₱"+ Double.toString(sum));
        
    }//GEN-LAST:event_generateTotalActionPerformed
    /**
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(dashboard1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dashboard1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dashboard1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dashboard1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dashboard1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> Month;
    private javax.swing.JButton Reset;
    private javax.swing.JTabbedPane Tab;
    private javax.swing.JComboBox<String> Year;
    private javax.swing.JTable accounts;
    private javax.swing.JComboBox<String> act;
    private javax.swing.JComboBox<String> act1;
    private javax.swing.JButton add_item;
    private javax.swing.JButton add_item1;
    private javax.swing.JButton add_item2;
    private javax.swing.JTextField addstock;
    private javax.swing.JComboBox<String> brand;
    private javax.swing.JComboBox<String> brand1;
    private javax.swing.JButton btnBrowse;
    private javax.swing.JButton btnBrowse1;
    private javax.swing.JLabel date;
    private javax.swing.JLabel days;
    private javax.swing.JTextField description;
    private javax.swing.JTextField description1;
    private javax.swing.JTextField discountper;
    private javax.swing.JButton generateTotal;
    private javax.swing.JTextField imagePath;
    private javax.swing.JTable itemlist;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel labelImage;
    private javax.swing.JLabel labelImage1;
    private javax.swing.JLabel months;
    private javax.swing.JLabel months2;
    private javax.swing.JComboBox<String> people;
    private javax.swing.JComboBox<String> people1;
    private javax.swing.JTextField productID;
    private javax.swing.JTextField productName;
    private javax.swing.JTextField productName1;
    private javax.swing.JTextField productPrice;
    private javax.swing.JTextField productPrice1;
    private javax.swing.JTable reports;
    private javax.swing.JTable reports_table;
    private javax.swing.JTextField search_bar;
    private javax.swing.JTextField search_bar1;
    private javax.swing.JTextField search_bar2;
    private javax.swing.JLabel timer;
    private javax.swing.JLabel totalIncome;
    private javax.swing.JLabel userd;
    private javax.swing.JLabel years;
    // End of variables declaration//GEN-END:variables

    private void tableRefresh() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private double sumColumnValues(JTable reports_table, int columnIndexToSum) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
