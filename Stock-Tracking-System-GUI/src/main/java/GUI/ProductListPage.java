package GUI;

import client.AppContext;
import client.Client;
import model.dto.DtoProduct;

import javax.swing.*;

import static javax.swing.JOptionPane.*;
import javax.swing.table.DefaultTableModel;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

public class ProductListPage extends javax.swing.JFrame {

    private static final String PRODUCTS       = "/products";
    private static final String FILTER_PRODUCT = PRODUCTS + "/filter"; //  /products/filter
    private static final String DELETE_PRODUCT = PRODUCTS + "/delete"; //  /products/delete


    private final Client apiClient = AppContext.getClient();

    private static DefaultTableModel productList;
    private List<DtoProduct> current = Collections.emptyList();

    public ProductListPage() {
        initComponents();
        productList = (DefaultTableModel) tbl_product.getModel();
        if(applyACL("PRODUCT_LIST",
                    "BOSS", "ACCOUNTANT", "AUTHORIZED", "EMPLOYEE", "SECRETARY", "VISITOR")){
            loadProductsAsync("");
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        pnl_report = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_product = new javax.swing.JTable();
        lbl_filter = new javax.swing.JLabel();
        txt_searched = new javax.swing.JTextField();
        btn_search = new javax.swing.JButton();
        comboBox_columns = new javax.swing.JComboBox<>();
        menuBar_options = new javax.swing.JMenuBar();
        menu_options = new javax.swing.JMenu();
        menu_productPages = new javax.swing.JMenu();
        menuItem_addProduct = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        menuItem_makeUpdate = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        menuItem_personals = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        menu_exit = new javax.swing.JMenu();
        menuItem_logout = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuItem_exit = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Products' Report");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) { formWindowClosing(evt); }
        });

        pnl_report.setBackground(new java.awt.Color(255, 255, 255));
        pnl_report.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        tbl_product.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {},
                new String [] { "Barcode", "Name", "Color", "Size", "Price", "Stock Quantity" }
        ) {
            public boolean isCellEditable(int r, int c) { return false; }
        });
        jScrollPane1.setViewportView(tbl_product);

        lbl_filter.setText("FILTER");

        btn_search.setText("Search");
        btn_search.addActionListener(this::btn_searchActionPerformed);

        comboBox_columns.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[] { "Select", "Barcode", "Name", "Color", "Size", "Price", "Stock Quantity" }
        ));

        javax.swing.GroupLayout pnl_reportLayout = new javax.swing.GroupLayout(pnl_report);
        pnl_report.setLayout(pnl_reportLayout);
        pnl_reportLayout.setHorizontalGroup(
                pnl_reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnl_reportLayout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addGroup(pnl_reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(pnl_reportLayout.createSequentialGroup()
                                                .addComponent(lbl_filter, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(comboBox_columns, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(txt_searched, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(btn_search))
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 718, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(69, Short.MAX_VALUE))
        );
        pnl_reportLayout.setVerticalGroup(
                pnl_reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnl_reportLayout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(pnl_reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbl_filter)
                                        .addComponent(txt_searched, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btn_search)
                                        .addComponent(comboBox_columns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(21, Short.MAX_VALUE))
        );

        menu_options.setText("Options");

        menu_productPages.setText("Product pages");

        menuItem_addProduct.setText("Add a product");
        menuItem_addProduct.addActionListener(this::menuItem_addProductActionPerformed);
        menu_productPages.add(menuItem_addProduct);
        menu_productPages.add(jSeparator3);

        menuItem_makeUpdate.setText("Make an update");
        menuItem_makeUpdate.addActionListener(this::menuItem_makeUpdateActionPerformed);
        menu_productPages.add(menuItem_makeUpdate);

        menu_options.add(menu_productPages);
        menu_options.add(jSeparator2);

        menuItem_personals.setText("See personal list");
        menuItem_personals.addActionListener(this::menuItem_personalsActionPerformed);
        menu_options.add(menuItem_personals);
        menu_options.add(jSeparator4);

        menuBar_options.add(menu_options);

        menu_exit.setText("EXIT");

        menuItem_logout.setText("Logout");
        menuItem_logout.addActionListener(this::menuItem_logoutActionPerformed);
        menu_exit.add(menuItem_logout);
        menu_exit.add(jSeparator1);

        menuItem_exit.setText("Exit");
        menuItem_exit.addActionListener(this::menuItem_exitActionPerformed);
        menu_exit.add(menuItem_exit);

        menuBar_options.add(menu_exit);
        setJMenuBar(menuBar_options);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pnl_report, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pnl_report, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }

    private boolean applyACL(String permission, String... roles){ // Yetki kontrolü
        boolean havePermission = apiClient.hasPermission(permission)
                || apiClient.hasAnyRole(roles);

        if (!havePermission) {
            JOptionPane.showMessageDialog(this, "Bu sayfayı görüntüleme izniniz yok.", "Yetki", WARNING_MESSAGE);
        }

        return havePermission;
    }

    private String backendColumnFromSelection() {
        int idx = comboBox_columns.getSelectedIndex();
        return switch (idx) {
            case 1 -> "barcode";
            case 2 -> "productName";
            case 3 -> "color";
            case 4 -> "size";
            case 5 -> "price";
            case 6 -> "stockQuantity";
            default -> null; // "Select"
        };
    }

    // Tabloyu doldurur
    private void fillTable(List<DtoProduct> list) {
        current = (list != null) ? list : Collections.emptyList();
        productList.setRowCount(0);
        for (DtoProduct dtoProduct : current) {
            productList.addRow(new Object[]{
                    dtoProduct.getBarcode(),
                    dtoProduct.getProductName(),
                    dtoProduct.getColor(),
                    dtoProduct.getSize(),
                    dtoProduct.getPrice(),
                    dtoProduct.getStockQuantity()
            });
        }
    }

    private void loadProductsAsync(String endpoint) {
        new SwingWorker<List<DtoProduct>, Void>() {
            @Override
            protected List<DtoProduct> doInBackground() throws Exception {
                return apiClient.listProducts(FILTER_PRODUCT + endpoint);
            }
            @Override
            protected void done() {
                try { fillTable(get()); } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            ProductListPage.this,
                            "Filtreli liste yüklenemedi: " + ex.getMessage(),
                            "Hata",
                            javax.swing.JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        }.execute();
    }

    private void menuItem_addProductActionPerformed(java.awt.event.ActionEvent evt) {
        if(applyACL("PRODUCT_ADD",
                "BOSS", "ACCOUNTANT", "AUTHORIZED", "EMPLOYEE", "SECRETARY")){
            new MainPage().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(rootPane,
                    "You do NOT have permission to see main page",
                    "WARNING", WARNING_MESSAGE);
        }
    }

    private void menuItem_makeUpdateActionPerformed(java.awt.event.ActionEvent evt) {
        if(applyACL("PRODUCT_ADD",
                "BOSS", "ACCOUNTANT", "AUTHORIZED", "EMPLOYEE", "SECRETARY")){
            int optionToUpdate = JOptionPane.showConfirmDialog(rootPane,
                    "If you want to make an update, you should go to add a product page and select a product to make an update.",
                    "UPDATE INFORMATION", OK_CANCEL_OPTION);
            if (optionToUpdate == JOptionPane.OK_OPTION) {
                new MainPage().setVisible(true);
                this.dispose();
            }
        } else {
            JOptionPane.showMessageDialog(rootPane,
                    "You do NOT have permission to update",
                    "WARNING", WARNING_MESSAGE);
        }
    }

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {
        String column = backendColumnFromSelection();
        String content = txt_searched.getText().trim();

        if (column == null && content.isEmpty()) {
            loadProductsAsync("");
            return;
        }

        try {
            String endpoint = "?column=" + java.net.URLEncoder.encode(column, "UTF-8")
                            + "&content=" + java.net.URLEncoder.encode(content, "UTF-8");

            loadProductsAsync(endpoint); // veriler gelsin
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

    }

    private void menuItem_personalsActionPerformed(java.awt.event.ActionEvent evt) {
        if(applyACL("EMPLOYEE_LIST",
                "BOSS", "AUTHORIZED", "CONSULTANT", "SECRETARY")){
            new EmployeeListPage().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(rootPane,
                    "You do NOT have enough permission to see employee list", "", WARNING_MESSAGE);
        }
    }

    private void menuItem_logoutActionPerformed(java.awt.event.ActionEvent evt) {
        new LoginPage().setVisible(true);
        this.dispose();
    }

    private void menuItem_exitActionPerformed(java.awt.event.ActionEvent evt) {
        int option = JOptionPane.showConfirmDialog(rootPane,
                "We are sorry you are leaving. Good bye... ",
                "EXIT", OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) this.dispose();
    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {
        if(applyACL("PRODUCT_ADD",
                "BOSS", "ACCOUNTANT", "AUTHORIZED", "EMPLOYEE", "SECRETARY")){
            new MainPage().setVisible(true);
            this.dispose();
        } else {
            int option = JOptionPane.showConfirmDialog(rootPane,
                    "We are sorry you are leaving. Good bye... ",
                    "EXIT", OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) this.dispose();
        }
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info :
                    javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName()); break;
                }
            }
        } catch (Exception ignored) {}
        java.awt.EventQueue.invokeLater(() -> new ProductListPage().setVisible(true));
    }

    // Variables
    private javax.swing.JButton btn_search;
    private javax.swing.JComboBox<String> comboBox_columns;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JLabel lbl_filter;
    private javax.swing.JMenuBar menuBar_options;
    private javax.swing.JMenuItem menuItem_addProduct;
    private javax.swing.JMenuItem menuItem_exit;
    private javax.swing.JMenuItem menuItem_logout;
    private javax.swing.JMenuItem menuItem_makeUpdate;
    private javax.swing.JMenuItem menuItem_personals;
    private javax.swing.JMenu menu_exit;
    private javax.swing.JMenu menu_options;
    private javax.swing.JMenu menu_productPages;
    private javax.swing.JPanel pnl_report;
    private javax.swing.JTable tbl_product;
    private javax.swing.JTextField txt_searched;
}
