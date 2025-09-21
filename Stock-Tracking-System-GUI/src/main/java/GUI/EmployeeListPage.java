package GUI;

import client.AppContext;
import client.Client;
import model.dto.DtoEmployee;
import model.dto.DtoUserUpdate;

import javax.swing.*;

import static javax.swing.JOptionPane.*;
import javax.swing.table.DefaultTableModel;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

public class EmployeeListPage extends javax.swing.JFrame {

    private static final String EMPLOYEES        = "/employees";
    private static final String FILTER_EMPLOYEES = EMPLOYEES + "/filter"; //  /employees/filter
    private static final String DELETE_EMPLOYEE  = EMPLOYEES + "/delete"; //  /employees/delete

    private final Client apiClient = AppContext.getClient();

    private static DefaultTableModel personalList;
    private List<DtoEmployee> current = Collections.emptyList();

    public EmployeeListPage() {
        initComponents();
        personalList = (DefaultTableModel) tbl_personals.getModel();
        if(applyACL("EMPLOYEE_LIST", "BOSS", "AUTHORIZED", "CONSULTANT", "SECRETARY")){
            loadEmployeesAsync("");
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        popup_deleteUp = new javax.swing.JPopupMenu();
        menu_delete = new javax.swing.JMenuItem();
        seperator_deleteUp = new javax.swing.JPopupMenu.Separator();
        menu_update = new javax.swing.JMenuItem();
        pnl_personalList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_personals = new javax.swing.JTable();
        lbl_filter = new javax.swing.JLabel();
        comboBox_columns = new javax.swing.JComboBox<>();
        txt_searched = new javax.swing.JTextField();
        btn_search = new javax.swing.JButton();
        menubar_pages = new javax.swing.JMenuBar();
        menu_pages = new javax.swing.JMenu();
        menu_productPages = new javax.swing.JMenu();
        menu_addProduct = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        menu_updateProduct = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menu_report = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        menu_exit = new javax.swing.JMenu();
        menuItem_logout = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        menuItem_exit = new javax.swing.JMenuItem();

        menu_delete.setText("Delete");
        menu_delete.addActionListener(this::menu_deleteActionPerformed);
        popup_deleteUp.add(menu_delete);
        popup_deleteUp.add(seperator_deleteUp);

        menu_update.setText("Update");
        menu_update.addActionListener(this::menu_updateActionPerformed);
        popup_deleteUp.add(menu_update);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Employee");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) { formWindowClosing(evt); }
        });

        pnl_personalList.setBackground(new java.awt.Color(255, 255, 255));
        pnl_personalList.setBorder(javax.swing.BorderFactory.createTitledBorder(
                null, "Employee List",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Sitka Text", 1, 14)));

        tbl_personals.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {},
                new String [] { "Name", "Surname", "Email", "Role" }
        ) {
            final boolean[] canEdit = new boolean [] { false,false,false,false };
            public boolean isCellEditable(int rowIndex, int columnIndex) { return canEdit [columnIndex]; }
        });
        tbl_personals.setComponentPopupMenu(popup_deleteUp);
        tbl_personals.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tbl_personals);

        lbl_filter.setText("FILTER");

        comboBox_columns.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[] { "Select", "Name", "Surname", "Email", "Role" }
        ));

        btn_search.setText("Search");
        btn_search.addActionListener(this::btn_searchActionPerformed);

        javax.swing.GroupLayout pnl_personalListLayout = new javax.swing.GroupLayout(pnl_personalList);
        pnl_personalList.setLayout(pnl_personalListLayout);
        pnl_personalListLayout.setHorizontalGroup(
                pnl_personalListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnl_personalListLayout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addGroup(pnl_personalListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(pnl_personalListLayout.createSequentialGroup()
                                                .addComponent(lbl_filter, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(50, 50, 50)
                                                .addComponent(comboBox_columns, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(50, 50, 50)
                                                .addComponent(txt_searched, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(50, 50, 50)
                                                .addComponent(btn_search))
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 847, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(43, Short.MAX_VALUE))
        );
        pnl_personalListLayout.setVerticalGroup(
                pnl_personalListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_personalListLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(pnl_personalListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbl_filter)
                                        .addComponent(txt_searched, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btn_search)
                                        .addComponent(comboBox_columns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        menu_pages.setText("Other Pages");

        menu_productPages.setText("Product");
        menu_addProduct.setText("Adding");
        menu_addProduct.addActionListener(this::menu_addProductActionPerformed);
        menu_productPages.add(menu_addProduct);
        menu_productPages.add(jSeparator2);

        menu_updateProduct.setText("Updating");
        menu_updateProduct.addActionListener(this::menu_updateProductActionPerformed);
        menu_productPages.add(menu_updateProduct);

        menu_pages.add(menu_productPages);
        menu_pages.add(jSeparator1);

        menu_report.setText("Report");
        menu_report.addActionListener(this::menu_reportActionPerformed);
        menu_pages.add(menu_report);
        menu_pages.add(jSeparator4);

        menubar_pages.add(menu_pages);

        menu_exit.setText("EXIT");
        menuItem_logout.setText("Logout");
        menuItem_logout.addActionListener(this::menuItem_logoutActionPerformed);
        menu_exit.add(menuItem_logout);
        menu_exit.add(jSeparator3);

        menuItem_exit.setText("Exit");
        menuItem_exit.addActionListener(this::menuItem_exitActionPerformed);
        menu_exit.add(menuItem_exit);

        menubar_pages.add(menu_exit);

        setJMenuBar(menubar_pages);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pnl_personalList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pnl_personalList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            case 1 -> "firstName";
            case 2 -> "lastName";
            case 3 -> "email";
            case 4 -> "role";
            default -> null; // "Select"
        };
    }

    // Tabloyu doldurur
    private void fillTable(List<DtoEmployee> list) {
        current = (list != null) ? list : Collections.emptyList();
        personalList.setRowCount(0);
        for (DtoEmployee e : current) {
            personalList.addRow(new Object[]{
                    e.getFirstName(),
                    e.getLastName(),
                    e.getEmail(),
                    e.getRoleName()
            });
        }
    }

    private void loadEmployeesAsync(String endpoint) {
        new SwingWorker<List<DtoEmployee>, Void>() {
            @Override
            protected List<DtoEmployee> doInBackground() throws Exception {
                return apiClient.filterEmployees(FILTER_EMPLOYEES + endpoint);
            }
            @Override
            protected void done() {
                try { fillTable(get()); } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            EmployeeListPage.this,
                            "Filtreli liste yüklenemedi: " + ex.getMessage(),
                            "Hata",
                            javax.swing.JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        }.execute();
    }


    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {
        String column = backendColumnFromSelection();
        String content = txt_searched.getText().trim();

        if (column == null || content.isEmpty()) {
            loadEmployeesAsync("");
            return;
        }

        try {
            String endpoint = "?column=" + java.net.URLEncoder.encode(column, "UTF-8")
                            + "&content=" + java.net.URLEncoder.encode(content, "UTF-8");

            loadEmployeesAsync(endpoint); // veriler gelsin
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

    }

    private void menu_addProductActionPerformed(java.awt.event.ActionEvent evt) {
        new MainPage().setVisible(true);
        this.dispose();
    }

    private void menu_updateProductActionPerformed(java.awt.event.ActionEvent evt) {
        int option = JOptionPane.showConfirmDialog(rootPane,
                "If you want to make an update on a product, "
                        + "you should go to add product page to select a product to update",
                "Information About Making An Update",
                OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            new MainPage().setVisible(true);
            this.dispose();
            JOptionPane.showMessageDialog(rootPane,
                    "Now, you should select a product to update.",
                    "Information About Making An Update",
                    INFORMATION_MESSAGE);
        }
    }

    private void menu_reportActionPerformed(java.awt.event.ActionEvent evt) {
        if (apiClient.hasPermission("PRODUCT_LIST")
                && apiClient.hasAnyRole("BOSS", "AUTHORIZED", "CONSULTANT", "SECRETARY")) {
            new ProductListPage().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Silme izniniz yok.", "Yetki", WARNING_MESSAGE);
        }
    }

    private void menu_deleteActionPerformed(java.awt.event.ActionEvent evt) {
        int viewRow = tbl_personals.getSelectedRow();
        if (viewRow < 0) {
            JOptionPane.showMessageDialog(rootPane, "There is no selected personal to delete.", "WARNING", WARNING_MESSAGE);
            return;
        }

        if (!apiClient.hasPermission("EMPLOYEE_DELETE")
                && !apiClient.hasAnyRole("BOSS", "AUTHORIZED", "CONSULTANT", "SECRETARY")) {
            JOptionPane.showMessageDialog(this, "Silme izniniz yok.", "Yetki", WARNING_MESSAGE);
            return;
        }

        int selectedRowIndex = tbl_personals.convertRowIndexToModel(viewRow);
        long id = current.get(selectedRowIndex).getId();

        int ok = JOptionPane.showConfirmDialog(this, "Bu çalışan silinsin mi?", "Onay", YES_NO_OPTION);
        if (ok != YES_OPTION) return;

        new javax.swing.SwingWorker<Boolean, Void>() {
            @Override protected Boolean doInBackground() throws Exception {
                return apiClient.deleteById(DELETE_EMPLOYEE, id); // DELETE /employees/{id}
            }
            @Override protected void done() {
                try {
                    if (get()) { JOptionPane.showMessageDialog(EmployeeListPage.this, "Silindi."); loadEmployeesAsync(""); }
                    else       { JOptionPane.showMessageDialog(EmployeeListPage.this, "Silme başarısız.", "Hata", ERROR_MESSAGE); }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(EmployeeListPage.this, "Silme hatası: " + ex.getMessage(), "Hata", ERROR_MESSAGE);
                }
            }
        }.execute();
    }

    private void menu_updateActionPerformed(java.awt.event.ActionEvent evt) {
        int viewRow = tbl_personals.getSelectedRow();
        if (viewRow < 0) {
            JOptionPane.showMessageDialog(rootPane, "Select personal to update.", "WARNING", WARNING_MESSAGE);
            return;
        }

        if (!apiClient.hasPermission("EMPLOYEE_UPDATE")
                && !apiClient.hasAnyRole("BOSS", "AUTHORIZED", "CONSULTANT", "SECRETARY")) {
            JOptionPane.showMessageDialog(this, "Güncelleme izniniz yok.", "Yetki", WARNING_MESSAGE);
            return;
        }

        int selectedRowIndex = tbl_personals.convertRowIndexToModel(viewRow);
        if (selectedRowIndex < 0 || selectedRowIndex >= current.size()) {
            JOptionPane.showMessageDialog(this, "Seçim geçersiz.", "Uyarı", WARNING_MESSAGE);
            return;
        }

        long id = current.get(selectedRowIndex).getId(); // DTO’dan gerçek DB id

        new javax.swing.SwingWorker<DtoUserUpdate, Void>() {
            @Override
            protected DtoUserUpdate doInBackground() throws Exception {
                return apiClient.getById(EMPLOYEES, id, DtoUserUpdate.class);
            }
            @Override
            protected void done() {
                try {
                    DtoUserUpdate dto = get();
                    new EmployeeUpdatePage(id, dto).setVisible(true);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(EmployeeListPage.this,
                            "Detaylar alınamadı: " + ex.getMessage(), "Hata", ERROR_MESSAGE);
                }
            }
        }.execute();
    }

    private void menuItem_exitActionPerformed(java.awt.event.ActionEvent evt) {
        int option = JOptionPane.showConfirmDialog(rootPane,
                "We are sorry you are leaving. Good bye... ", "EXIT", OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) { this.dispose(); }
    }

    private void menuItem_logoutActionPerformed(java.awt.event.ActionEvent evt) {
        new LoginPage().setVisible(true);
        this.dispose();
    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {
        if(applyACL("PRODUCT_LIST",
                "BOSS", "ACCOUNTANT", "AUTHORIZED", "EMPLOYEE", "SECRETARY")) {
            new MainPage().setVisible(true);
            this.dispose();
        } else {

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
        java.awt.EventQueue.invokeLater(() -> new EmployeeListPage().setVisible(true));
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
    private javax.swing.JMenuItem menuItem_exit;
    private javax.swing.JMenuItem menu_addProduct;
    private javax.swing.JMenuItem menu_delete;
    private javax.swing.JMenu menu_exit;
    private javax.swing.JMenu menu_pages;
    private javax.swing.JMenu menu_productPages;
    private javax.swing.JMenuItem menu_report;
    private javax.swing.JMenuItem menu_update;
    private javax.swing.JMenuItem menu_updateProduct;
    private javax.swing.JMenuBar menubar_pages;
    private javax.swing.JMenuItem menuItem_logout;
    private javax.swing.JPanel pnl_personalList;
    private javax.swing.JPopupMenu popup_deleteUp;
    private javax.swing.JPopupMenu.Separator seperator_deleteUp;
    private javax.swing.JTable tbl_personals;
    private javax.swing.JTextField txt_searched;
}

