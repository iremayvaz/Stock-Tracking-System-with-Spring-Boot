package GUI;

import java.util.regex.*;
import javax.swing.*;
import static javax.swing.JOptionPane.*;
import javax.swing.table.DefaultTableModel;

public class MainPage extends javax.swing.JFrame {

    private static final String PRODUCTS       = "/products";
    private static final String ADD_PRODUCT    = PRODUCTS + "/add";
    private static final String DELETE_PRODUCT = PRODUCTS + "/delete";

    public static DefaultTableModel productList;

    //getters (trim ile)
    public String getTxtCategory()   { return txt_category.getText().trim(); }
    public String getTxtBarcode()    { return txt_barcode.getText().trim(); }
    public String getTxtProductName(){ return txt_productName.getText().trim(); }
    public String getTxtColor()      { return txt_color.getText().trim(); }
    public String getTxtSize()       { return txt_size.getText().trim(); }
    public String getTxtPrice()      { return txt_price.getText().trim(); }
    public String getTxtNumber()     { return txt_number.getText().trim(); }

    //setters
    public void setTxtCategory(String v){ txt_category.setText(v); }
    public void setTxtBarcode(String v){ txt_barcode.setText(v); }
    public void setTxtProductName(String v){ txt_productName.setText(v); }
    public void setTxtColor(String v){ txt_color.setText(v); }
    public void setTxtSize(String v){ txt_size.setText(v); }
    public void setTxtPrice(String v){ txt_price.setText(v); }
    public void setTxtNumber(String v){ txt_number.setText(v); }

    // regex kontrolleri (Cancel'da çık)
    public boolean checkData(String regex, String input, String fieldName) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            while (!matcher.matches()) {
                String newData = JOptionPane.showInputDialog(rootPane, fieldName + " invalid");
                if (newData == null) return false; // iptal
                matcher = pattern.matcher(newData);
            }
            return true;
        }
        return true;
    }

    public boolean checkData(JTextField field, String regex, String input, String fieldName) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            while (!matcher.matches()) {
                String newData = JOptionPane.showInputDialog(rootPane, fieldName + " invalid");
                if (newData == null) return false; // iptal
                matcher = pattern.matcher(newData);
            }
            field.setText(matcher.group());
            return true;
        }
        return true;
    }

    public void data(JTextField field, String regex, String input, String fieldName) {
        if (!checkData(field, regex, input, fieldName)) {
            field.setText("");
        }
    }

    public MainPage() {
        initComponents();
        productList = (DefaultTableModel) tbl_products.getModel();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        popup_upDelete = new javax.swing.JPopupMenu();
        mbtn_update = new javax.swing.JMenuItem();
        seperator_upDelete = new javax.swing.JPopupMenu.Separator();
        mbtn_delete = new javax.swing.JMenuItem();
        pnl_addProduct = new javax.swing.JPanel();
        lbl_category = new javax.swing.JLabel();
        lbl_barcode = new javax.swing.JLabel();
        lbl_productName = new javax.swing.JLabel();
        lbl_color = new javax.swing.JLabel();
        lbl_size = new javax.swing.JLabel();
        lbl_price = new javax.swing.JLabel();
        txt_category = new javax.swing.JTextField();
        txt_barcode = new javax.swing.JTextField();
        txt_productName = new javax.swing.JTextField();
        txt_color = new javax.swing.JTextField();
        txt_size = new javax.swing.JTextField();
        txt_price = new javax.swing.JTextField();
        scroll_products = new javax.swing.JScrollPane();
        tbl_products = new javax.swing.JTable();
        lbl_number = new javax.swing.JLabel();
        txt_number = new javax.swing.JTextField();
        isExplanationAdded = new javax.swing.JCheckBox();
        btn_add = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        menuBar_options = new javax.swing.JMenuBar();
        menu_options = new javax.swing.JMenu();
        menuItem_report = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuItem_personals = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        menuItem_export = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        menuItem_update = new javax.swing.JMenuItem();
        exit_menu = new javax.swing.JMenu();
        menuItem_logout = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        menuItem_exit = new javax.swing.JMenuItem();

        mbtn_update.setText("Update");
        //mbtn_update.addActionListener(this::btn_updateActionPerformed);
        popup_upDelete.add(mbtn_update);
        popup_upDelete.add(seperator_upDelete);

        mbtn_delete.setText("Delete");
        //mbtn_delete.addActionListener(this::btn_deleteActionPerformed);
        popup_upDelete.add(mbtn_delete);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Product Adding");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) { formWindowClosing(evt); }
            //public void windowOpened(java.awt.event.WindowEvent evt) { formWindowOpened(evt); }
        });

        pnl_addProduct.setBackground(new java.awt.Color(255, 255, 255));
        pnl_addProduct.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        lbl_category.setText("Category");
        lbl_barcode.setText("Barcode");
        lbl_productName.setText("Product Name");
        lbl_color.setText("Color");
        lbl_size.setText("Size");
        lbl_price.setText("Price");

        scroll_products.setToolTipText("");
        scroll_products.setAutoscrolls(true);

        tbl_products.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {},
                new String [] {
                        "Category", "Barcode", "Product Name", "Color", "Size", "Price", "Number", "Explanation"
                }
        ) {
            boolean[] canEdit = new boolean [] { false,false,false,false,false,false,false,false };
            public boolean isCellEditable(int rowIndex, int columnIndex) { return canEdit [columnIndex]; }
        });
        tbl_products.setComponentPopupMenu(popup_upDelete);
        tbl_products.getTableHeader().setReorderingAllowed(false);
        scroll_products.setViewportView(tbl_products);

        lbl_number.setText("Number");
        isExplanationAdded.setText("Add an explanation");

        btn_add.setText("ADD");
        //btn_add.addActionListener(this::btn_addActionPerformed);

        btn_update.setText("UPDATE");
        //btn_update.addActionListener(this::btn_updateActionPerformed);

        btn_delete.setText("DELETE");
        //btn_delete.addActionListener(this::btn_deleteActionPerformed);

        javax.swing.GroupLayout pnl_addProductLayout = new javax.swing.GroupLayout(pnl_addProduct);
        pnl_addProduct.setLayout(pnl_addProductLayout);
        pnl_addProductLayout.setHorizontalGroup(
                pnl_addProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnl_addProductLayout.createSequentialGroup()
                                .addContainerGap(27, Short.MAX_VALUE)
                                .addGroup(pnl_addProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnl_addProductLayout.createSequentialGroup()
                                                .addGroup(pnl_addProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lbl_category)
                                                        .addComponent(lbl_barcode)
                                                        .addComponent(lbl_productName)
                                                        .addComponent(lbl_color)
                                                        .addComponent(lbl_size)
                                                        .addComponent(lbl_price)
                                                        .addComponent(lbl_number))
                                                .addGap(77, 77, 77)
                                                .addGroup(pnl_addProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(txt_category, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                                        .addComponent(txt_barcode)
                                                        .addComponent(txt_productName)
                                                        .addComponent(txt_color)
                                                        .addComponent(txt_size)
                                                        .addComponent(txt_price)
                                                        .addComponent(txt_number)))
                                        .addGroup(pnl_addProductLayout.createSequentialGroup()
                                                .addComponent(isExplanationAdded)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 116, Short.MAX_VALUE)
                                                .addGroup(pnl_addProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(btn_update)
                                                        .addComponent(btn_add)
                                                        .addComponent(btn_delete))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scroll_products, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                                .addContainerGap(37, Short.MAX_VALUE))
        );
        pnl_addProductLayout.setVerticalGroup(
                pnl_addProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnl_addProductLayout.createSequentialGroup()
                                .addContainerGap(30, Short.MAX_VALUE)
                                .addGroup(pnl_addProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(scroll_products)
                                        .addGroup(pnl_addProductLayout.createSequentialGroup()
                                                .addGroup(pnl_addProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lbl_category)
                                                        .addComponent(txt_category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(32, 32, 32)
                                                .addGroup(pnl_addProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lbl_barcode)
                                                        .addComponent(txt_barcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(30, 30, 30)
                                                .addGroup(pnl_addProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lbl_productName)
                                                        .addComponent(txt_productName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(29, 29, 29)
                                                .addGroup(pnl_addProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lbl_color)
                                                        .addComponent(txt_color, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(21, 21, 21)
                                                .addGroup(pnl_addProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lbl_size)
                                                        .addComponent(txt_size, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(21, 21, 21)
                                                .addGroup(pnl_addProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lbl_price)
                                                        .addComponent(txt_price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(23, 23, 23)
                                                .addGroup(pnl_addProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lbl_number)
                                                        .addComponent(txt_number, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(33, 33, 33)
                                                .addGroup(pnl_addProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(isExplanationAdded)
                                                        .addComponent(btn_add))
                                                .addGap(18, 18, 18)
                                                .addComponent(btn_update)
                                                .addGap(18, 18, 18)
                                                .addComponent(btn_delete)))
                                .addContainerGap(44, Short.MAX_VALUE))
        );

        menu_options.setText("Options");

        menuItem_report.setText("See report ");
        //menuItem_report.addActionListener(this::menuItem_reportActionPerformed);
        menu_options.add(menuItem_report);
        menu_options.add(jSeparator1);

        menuItem_personals.setText("See personals");
        //menuItem_personals.addActionListener(this::menuItem_personalsActionPerformed);
        menu_options.add(menuItem_personals);
        menu_options.add(jSeparator2);

        menuItem_export.setText("Export");
        //menuItem_export.addActionListener(this::menuItem_exportActionPerformed);
        menu_options.add(menuItem_export);
        menu_options.add(jSeparator4);

        menuItem_update.setText("Make an update");
        //menuItem_update.addActionListener(this::menuItem_updateActionPerformed);
        menu_options.add(menuItem_update);

        menuBar_options.add(menu_options);

        exit_menu.setText("EXIT");

        menuItem_logout.setText("Logout");
        menuItem_logout.addActionListener(this::menuItem_logoutActionPerformed);
        exit_menu.add(menuItem_logout);
        exit_menu.add(jSeparator3);

        menuItem_exit.setText("Exit");
        menuItem_exit.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(rootPane,
                    "We are sorry you are leaving. Good bye... ",
                    "EXIT",
                    OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) dispose();
        });
        exit_menu.add(menuItem_exit);

        menuBar_options.add(exit_menu);

        setJMenuBar(menuBar_options);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pnl_addProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pnl_addProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }
/*
    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {
        // validations
        data(txt_category,    "^[A-Z][a-z]+$",          getTxtCategory(),    "category");
        data(txt_barcode,     "^[0-9]{8,13}$",          getTxtBarcode(),     "barcode");
        data(txt_productName, "^[A-Z][a-z]+$",          getTxtProductName(), "product name");
        data(txt_color,       "^[A-Z][a-z]+$",          getTxtColor(),       "color");
        data(txt_size,        "^[A-Za-z0-9]+$",         getTxtSize(),        "size");
        data(txt_price,       "^[0-9]+(\\.[0-9]{1,2})?$", getTxtPrice(),     "price");
        data(txt_number,      "^[0-9]+$",               getTxtNumber(),      "number");

        String inputExplanation = "";
        if (isExplanationAdded.isSelected()) {
            String ex = JOptionPane.showInputDialog(rootPane,
                    "WRITE AN Explanation ABOUT THE PRODUCT",
                    "EXPLANATION",
                    INFORMATION_MESSAGE);
            if (ex != null && checkData("^[A-Z][a-z\\s]{1,50}$", ex, "explanation")) {
                inputExplanation = ex;
            }
        }

        Product newProduct = new Product(
                getTxtCategory(), getTxtBarcode(), getTxtProductName(), getTxtColor(),
                getTxtSize(), getTxtPrice(), getTxtNumber(), inputExplanation
        );

        if (getTxtCategory().isEmpty() || getTxtBarcode().isEmpty() ||
                getTxtProductName().isEmpty() || getTxtColor().isEmpty() ||
                getTxtSize().isEmpty() || getTxtPrice().isEmpty() ||
                getTxtNumber().isEmpty())
        {
            JOptionPane.showMessageDialog(rootPane, "Empty field", "Error", ERROR_MESSAGE);
            return;
        }

        if (DatabaseManager.addProduct(newProduct)) {
            JOptionPane.showMessageDialog(rootPane, "The product is added successfully.", "", PLAIN_MESSAGE);
            DatabaseManager.showProducts(productList);
            // reset
            setTxtCategory(""); setTxtBarcode(""); setTxtProductName(""); setTxtColor("");
            setTxtSize(""); setTxtPrice(""); setTxtNumber(""); isExplanationAdded.setSelected(false);
        } else {
            JOptionPane.showMessageDialog(rootPane, "The product canNOT be added.", "", WARNING_MESSAGE);
        }
    }*/

    /*private void menuItem_reportActionPerformed(java.awt.event.ActionEvent evt) {
        ProductListPage mainToList = new ProductListPage();
        mainToList.setVisible(true);
        this.dispose();
    }*/

    /*private void menuItem_updateActionPerformed(java.awt.event.ActionEvent evt) {
        if (tbl_products.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(rootPane,
                    "You should select a product to make an update",
                    "UPDATE INFORMATION",
                    INFORMATION_MESSAGE);
        } else {
            int selectedRowIndex = tbl_products.convertRowIndexToModel(tbl_products.getSelectedRow());
            Product product = new Product();
            product.setCategory(productList.getValueAt(selectedRowIndex, 0).toString());
            product.setBarcode(productList.getValueAt(selectedRowIndex, 1).toString());
            product.setProductName(productList.getValueAt(selectedRowIndex, 2).toString());
            product.setColor(productList.getValueAt(selectedRowIndex, 3).toString());
            product.setSize(productList.getValueAt(selectedRowIndex, 4).toString());
            product.setPrice(productList.getValueAt(selectedRowIndex, 5).toString());
            product.setNumber(productList.getValueAt(selectedRowIndex, 6).toString());
            product.setExplanation(productList.getValueAt(selectedRowIndex, 7).toString());
            new Product_update(product).setVisible(true);
        }
    }

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            int count = tbl_products.getSelectedRowCount();
            if (count == 1) {
                int idx = tbl_products.convertRowIndexToModel(tbl_products.getSelectedRow());
                Product product = new Product();
                product.setCategory(productList.getValueAt(idx, 0).toString());
                product.setBarcode(productList.getValueAt(idx, 1).toString());
                product.setProductName(productList.getValueAt(idx, 2).toString());
                product.setColor(productList.getValueAt(idx, 3).toString());
                product.setSize(productList.getValueAt(idx, 4).toString());
                product.setPrice(productList.getValueAt(idx, 5).toString());
                product.setNumber(productList.getValueAt(idx, 6).toString());
                product.setExplanation(productList.getValueAt(idx, 7).toString());
                new Product_update(product).setVisible(true);
            } else if (count == 0) {
                throw new Exception("Select product to update.");
            } else {
                throw new Exception("Select JUST ONE(1) product to update!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage(), "WARNING", WARNING_MESSAGE);
        }
    }

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            int count = tbl_products.getSelectedRowCount();
            if (count == 1) {
                int idx = tbl_products.convertRowIndexToModel(tbl_products.getSelectedRow());
                String barcode = productList.getValueAt(idx, 1).toString();
                if (DatabaseManager.deleteProduct(barcode)) {
                    DatabaseManager.showProducts(productList);
                    JOptionPane.showMessageDialog(rootPane, "Deleted successfully", "", INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Failed to be deleted", "WARNING", WARNING_MESSAGE);
                }
            } else if (count == 0) {
                throw new Exception("There is no selected product to delete.");
            } else {
                throw new Exception("Select JUST ONE(1) product to update!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage(), "WARNING", WARNING_MESSAGE);
        }
    }

    private void formWindowOpened(java.awt.event.WindowEvent evt) {
        DatabaseManager.showProducts(productList);
    }*/

    private void formWindowClosing(java.awt.event.WindowEvent evt) {
        int option = JOptionPane.showConfirmDialog(rootPane,
                "We are sorry you are leaving. Good bye... ",
                "EXIT",
                OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) this.dispose();
    }

    /*private void menuItem_personalsActionPerformed(java.awt.event.ActionEvent evt) {
        if (DatabaseManager.checkAuthority()) {
            Person_personalList addingToPersonalList = new Person_personalList();
            addingToPersonalList.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(rootPane,
                    "You do not have enough authority for see personal list",
                    "",
                    INFORMATION_MESSAGE);
        }
    }*/

    private void menuItem_logoutActionPerformed(java.awt.event.ActionEvent evt) {
        LoginPage addToLogin = new LoginPage();
        addToLogin.setVisible(true);
        this.dispose();
    }

    public static void main(String args[]) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
                if ("Nimbus".equals(info.getName())) { UIManager.setLookAndFeel(info.getClassName()); break; }
        } catch (Exception ignored) {}
        java.awt.EventQueue.invokeLater(() -> new MainPage().setVisible(true));
    }

    // Variables
    private javax.swing.JButton btn_add;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_update;
    private javax.swing.JMenu exit_menu;
    private javax.swing.JCheckBox isExplanationAdded;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JLabel lbl_barcode;
    private javax.swing.JLabel lbl_category;
    private javax.swing.JLabel lbl_color;
    private javax.swing.JLabel lbl_number;
    private javax.swing.JLabel lbl_price;
    private javax.swing.JLabel lbl_productName;
    private javax.swing.JLabel lbl_size;
    private javax.swing.JMenuItem mbtn_delete;
    private javax.swing.JMenuItem mbtn_update;
    private javax.swing.JMenuBar menuBar_options;
    private javax.swing.JMenuItem menuItem_exit;
    private javax.swing.JMenuItem menuItem_export;
    private javax.swing.JMenuItem menuItem_logout;
    private javax.swing.JMenuItem menuItem_personals;
    private javax.swing.JMenuItem menuItem_report;
    private javax.swing.JMenuItem menuItem_update;
    private javax.swing.JMenu menu_options;
    private javax.swing.JPanel pnl_addProduct;
    private javax.swing.JPopupMenu popup_upDelete;
    private javax.swing.JScrollPane scroll_products;
    private javax.swing.JPopupMenu.Separator seperator_upDelete;
    private javax.swing.JTable tbl_products;
    private javax.swing.JTextField txt_barcode;
    private javax.swing.JTextField txt_category;
    private javax.swing.JTextField txt_color;
    private javax.swing.JTextField txt_number;
    private javax.swing.JTextField txt_price;
    private javax.swing.JTextField txt_productName;
    private javax.swing.JTextField txt_size;
}

