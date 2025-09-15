package GUI;

import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.*;
import javax.swing.table.DefaultTableModel;

public class EmployeeListPage extends javax.swing.JFrame {

    private static final String EMPLOYEES        = "/employees";
    private static final String FILTER_EMPLOYEES = EMPLOYEES + "/filter"; //  /employees/filter

    public static DefaultTableModel personalList;

    public EmployeeListPage() {
        initComponents();
        personalList = (DefaultTableModel) tbl_personals.getModel();
        /*if (DatabaseManager.checkAuthority()) {
            DatabaseManager.showPersonals(personalList);
        }*/
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
        menuItem_export = new javax.swing.JMenuItem();
        menu_exit = new javax.swing.JMenu();
        menuıtem_logout = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        menuItem_exit = new javax.swing.JMenuItem();

        menu_delete.setText("Delete");
        //menu_delete.addActionListener(this::menu_deleteActionPerformed);
        popup_deleteUp.add(menu_delete);
        popup_deleteUp.add(seperator_deleteUp);

        menu_update.setText("Update");
        //menu_update.addActionListener(this::menu_updateActionPerformed);
        popup_deleteUp.add(menu_update);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Personal");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) { formWindowClosing(evt); }
        });

        pnl_personalList.setBackground(new java.awt.Color(255, 255, 255));
        pnl_personalList.setBorder(javax.swing.BorderFactory.createTitledBorder(
                null, "Personal List",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Sitka Text", 1, 14)));

        tbl_personals.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {},
                new String [] { "Name", "Surname", "Phone", "Email", "Password", "Position", "Gender" }
        ) {
            final boolean[] canEdit = new boolean [] { false,false,false,false,false,false,false };
            public boolean isCellEditable(int rowIndex, int columnIndex) { return canEdit [columnIndex]; }
        });
        tbl_personals.setComponentPopupMenu(popup_deleteUp);
        tbl_personals.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tbl_personals);

        lbl_filter.setText("FILTER");

        comboBox_columns.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[] { "Select", "Name", "Surname", "Phone", "Email", "Password", "Position", "Gender" }
        ));

        btn_search.setText("Search");
        //btn_search.addActionListener(this::btn_searchActionPerformed);

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
        //menu_report.addActionListener(this::menu_reportActionPerformed);
        menu_pages.add(menu_report);
        menu_pages.add(jSeparator4);

        menuItem_export.setText("Export");
        //menuItem_export.addActionListener(this::menuItem_exportActionPerformed);
        menu_pages.add(menuItem_export);

        menubar_pages.add(menu_pages);

        menu_exit.setText("EXIT");
        menuıtem_logout.setText("Logout");
        menuıtem_logout.addActionListener(this::menuıtem_logoutActionPerformed);
        menu_exit.add(menuıtem_logout);
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

    /*private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {
        String searchedColumn = comboBox_columns.getItemAt(comboBox_columns.getSelectedIndex());
        String query = txt_searched.getText().trim();

        if (!"Select".equalsIgnoreCase(searchedColumn)) {
            if (query.isEmpty()) {
                DatabaseManager.showPersonals(personalList);
                return;
            }
            if (!DatabaseManager.filterPersonal(personalList, query, searchedColumn)) {
                JOptionPane.showMessageDialog(rootPane, "Failed to be filtered.");
            } else {
                JOptionPane.showMessageDialog(rootPane, "Filtered successfully");
            }
        }
    }*/

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

    /*private void menu_reportActionPerformed(java.awt.event.ActionEvent evt) {
        new ProductListPage().setVisible(true);
        this.dispose();
    }

    private void menu_deleteActionPerformed(java.awt.event.ActionEvent evt) {
        int viewRow = tbl_personals.getSelectedRow();
        if (viewRow < 0) {
            JOptionPane.showMessageDialog(rootPane, "There is no selected personal to delete.", "WARNING", WARNING_MESSAGE);
            return;
        }
        int selectedRowIndex = tbl_personals.convertRowIndexToModel(viewRow);
        String email = personalList.getValueAt(selectedRowIndex, 3).toString();
        try {
            if (tbl_personals.getSelectedRowCount() == 1) {
                if (DatabaseManager.deletePerson(email)) {
                    DatabaseManager.showPersonals(personalList);
                    throw new Exception("Deleted successfully");
                } else {
                    throw new Exception("Failed to be deleted");
                }
            } else if (tbl_personals.getSelectedRowCount() == 0) {
                throw new Exception("There is no selected personal to delete.");
            } else {
                throw new Exception("Select JUST ONE(1) personal to update!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage(), "WARNING", WARNING_MESSAGE);
        }
    }

    private void menu_updateActionPerformed(java.awt.event.ActionEvent evt) {
        int viewRow = tbl_personals.getSelectedRow();
        if (viewRow < 0) {
            JOptionPane.showMessageDialog(rootPane, "Select personal to update.", "WARNING", WARNING_MESSAGE);
            return;
        }
        int selectedRowIndex = tbl_personals.convertRowIndexToModel(viewRow);
        try {
            if (tbl_personals.getSelectedRowCount() == 1) {
                Person person = new Person();
                person.setName(personalList.getValueAt(selectedRowIndex, 0).toString());
                person.setSurname(personalList.getValueAt(selectedRowIndex, 1).toString());
                person.setPhoneNum(personalList.getValueAt(selectedRowIndex, 2).toString());
                person.setEmail(personalList.getValueAt(selectedRowIndex, 3).toString());
                person.setPassword(personalList.getValueAt(selectedRowIndex, 4).toString());
                person.setPosition(personalList.getValueAt(selectedRowIndex, 5).toString());
                person.setGender(personalList.getValueAt(selectedRowIndex, 6).toString().charAt(0));
                new Person_update(person).setVisible(true);
            } else if (tbl_personals.getSelectedRowCount() == 0) {
                throw new Exception("Select personal to update.");
            } else {
                throw new Exception("Select JUST ONE(1) personal to update!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage(), "WARNING", WARNING_MESSAGE);
        }
    }*/

    private void menuItem_exitActionPerformed(java.awt.event.ActionEvent evt) {
        int option = JOptionPane.showConfirmDialog(rootPane,
                "We are sorry you are leaving. Good bye... ", "EXIT", OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) { this.dispose(); }
    }

    private void menuıtem_logoutActionPerformed(java.awt.event.ActionEvent evt) {
        new LoginPage().setVisible(true);
        this.dispose();
    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {
        new MainPage().setVisible(true);
        this.dispose();
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
    private javax.swing.JMenuItem menuItem_export;
    private javax.swing.JMenuItem menu_addProduct;
    private javax.swing.JMenuItem menu_delete;
    private javax.swing.JMenu menu_exit;
    private javax.swing.JMenu menu_pages;
    private javax.swing.JMenu menu_productPages;
    private javax.swing.JMenuItem menu_report;
    private javax.swing.JMenuItem menu_update;
    private javax.swing.JMenuItem menu_updateProduct;
    private javax.swing.JMenuBar menubar_pages;
    private javax.swing.JMenuItem menuıtem_logout;
    private javax.swing.JPanel pnl_personalList;
    private javax.swing.JPopupMenu popup_deleteUp;
    private javax.swing.JPopupMenu.Separator seperator_deleteUp;
    private javax.swing.JTable tbl_personals;
    private javax.swing.JTextField txt_searched;
}

