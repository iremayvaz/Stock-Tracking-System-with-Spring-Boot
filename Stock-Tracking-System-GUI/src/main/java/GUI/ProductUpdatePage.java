package GUI;

import client.AppContext;
import client.Client;
import model.dto.DtoProductUpdate;
import model.dto.DtoProductInsert;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

import static javax.swing.JOptionPane.WARNING_MESSAGE;

public class ProductUpdatePage extends javax.swing.JFrame {

    private static final String PRODUCTS       = "/products";
    private static final String UPDATE_PRODUCT = PRODUCTS + "/update";

    private final Client apiClient = AppContext.getClient();
    private final Long id;
    private final UpdateListener listener;

    // from JTextField
    public String getTxtCategory() { return text_category.getText(); }

    public String getTxtBarcode() {
        return text_barcode.getText();
    }

    public String getTxtProductName() { return text_productName.getText(); }

    public String getTxtColor() { return text_color.getText(); }

    public String getTxtSize() { return text_size.getText(); }

    public String getTxtPrice() { return text_price.getText(); }

    public String getTxtNumber() { return text_number.getText(); }

    // Cancel'da çık
    public String getTxtExplanation() {
        String cur = text_explanation.getText();
        if (!cur.equals("")) {
            Pattern pattern = Pattern.compile("^[A-Z][a-z\\s]{1,50}$");
            Matcher matcher = pattern.matcher(cur);

            if (!matcher.matches()) {
                while (!matcher.matches()) {
                    String newData = JOptionPane.showInputDialog(rootPane, "explanation invalid");
                    if (newData == null) { // İptal edildi
                        return ""; // alanı boş bırak
                    }
                    matcher = pattern.matcher(newData);
                }
            }
            text_explanation.setText(matcher.group());
        }
        return text_explanation.getText();
    }

    public ProductUpdatePage() {
        this.id = null;
        this.listener = null;
        initComponents();
    }

    public ProductUpdatePage(Long id, DtoProductUpdate product, UpdateListener listener) {
        this.id = id;
        this.listener = listener;
        initComponents();
        if(applyACL("PRODUCT_ADD",
                "BOSS", "ACCOUNTANT", "AUTHORIZED", "EMPLOYEE", "SECRETARY")) {
            text_category.setText(product.getCategory());
            text_barcode.setText(product.getBarcode());
            text_productName.setText(product.getProductName());
            text_color.setText(product.getColor());
            text_size.setText(product.getSize());
            text_price.setText(product.getPrice().toString());
            text_number.setText(product.getStockQuantity().toString());
            text_explanation.setText(product.getExplanation());
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        pnl_update = new javax.swing.JPanel();
        label_category = new javax.swing.JLabel();
        label_barcode = new javax.swing.JLabel();
        label_productName = new javax.swing.JLabel();
        label_color = new javax.swing.JLabel();
        label_size = new javax.swing.JLabel();
        label_price = new javax.swing.JLabel();
        label_number = new javax.swing.JLabel();
        label_explanation = new javax.swing.JLabel();
        text_category = new javax.swing.JTextField();
        text_barcode = new javax.swing.JTextField();
        text_productName = new javax.swing.JTextField();
        text_color = new javax.swing.JTextField();
        text_size = new javax.swing.JTextField();
        text_price = new javax.swing.JTextField();
        text_number = new javax.swing.JTextField();
        text_explanation = new javax.swing.JTextField();
        button_update = new javax.swing.JButton();
        lbl_warning = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Product Updating");

        pnl_update.setBackground(new java.awt.Color(255, 255, 255));
        pnl_update.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "UPDATE", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Sitka Text", 1, 14))); // NOI18N

        label_category.setText("Category");
        label_barcode.setText("Barcode");
        label_productName.setText("Product Name");
        label_color.setText("Color");
        label_size.setText("Size");
        label_price.setText("Price");
        label_number.setText("Number");
        label_explanation.setText("Explanation");

        button_update.setText("UPDATE");
        button_update.addActionListener(this::button_updateActionPerformed);

        lbl_warning.setFont(new java.awt.Font("Sitka Text", 3, 12)); // NOI18N
        lbl_warning.setForeground(new java.awt.Color(255, 0, 0));
        lbl_warning.setText("Please change the data that you want to update!!!");

        javax.swing.GroupLayout pnl_updateLayout = new javax.swing.GroupLayout(pnl_update);
        pnl_update.setLayout(pnl_updateLayout);
        pnl_updateLayout.setHorizontalGroup(
                pnl_updateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnl_updateLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pnl_updateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(pnl_updateLayout.createSequentialGroup()
                                                .addGroup(pnl_updateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(label_category)
                                                        .addComponent(label_barcode)
                                                        .addComponent(label_productName)
                                                        .addComponent(label_color)
                                                        .addComponent(label_size)
                                                        .addComponent(label_price)
                                                        .addComponent(label_number)
                                                        .addComponent(label_explanation))
                                                .addGap(104, 104, 104)
                                                .addGroup(pnl_updateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(text_category)
                                                        .addComponent(text_barcode)
                                                        .addComponent(text_productName)
                                                        .addComponent(text_color)
                                                        .addComponent(text_size)
                                                        .addComponent(text_price)
                                                        .addComponent(text_number)
                                                        .addComponent(text_explanation, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_updateLayout.createSequentialGroup()
                                                .addComponent(lbl_warning, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                                                .addGap(18, 18, 18)
                                                .addComponent(button_update)))
                                .addContainerGap(27, Short.MAX_VALUE))
        );
        pnl_updateLayout.setVerticalGroup(
                pnl_updateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnl_updateLayout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(pnl_updateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(label_category)
                                        .addComponent(text_category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnl_updateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(label_barcode)
                                        .addComponent(text_barcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnl_updateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(label_productName)
                                        .addComponent(text_productName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnl_updateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(label_color)
                                        .addComponent(text_color, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnl_updateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(label_size)
                                        .addComponent(text_size, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnl_updateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(label_price)
                                        .addComponent(text_price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnl_updateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(label_number)
                                        .addComponent(text_number, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnl_updateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(label_explanation)
                                        .addComponent(text_explanation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnl_updateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(button_update)
                                        .addComponent(lbl_warning))
                                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pnl_update, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 6, Short.MAX_VALUE)
                                .addComponent(pnl_update, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void button_updateActionPerformed(java.awt.event.ActionEvent evt) {
        if(applyACL("PRODUCT_ADD",
                "BOSS", "ACCOUNTANT", "AUTHORIZED", "EMPLOYEE", "SECRETARY")) {
            DtoProductInsert dtoProductIU = new DtoProductInsert();
            dtoProductIU.setBarcode(text_barcode.getText().trim());
            dtoProductIU.setCategory(text_category.getText().trim());
            dtoProductIU.setProductName(text_productName.getText().trim());
            dtoProductIU.setColor(text_color.getText().trim());
            dtoProductIU.setSize(text_size.getText().trim());
            dtoProductIU.setPrice(new BigDecimal(text_price.getText().trim()));
            dtoProductIU.setStockQuantity(Integer.parseInt(text_number.getText().trim()));
            dtoProductIU.setExplanation(text_explanation.getText().trim());

            new javax.swing.SwingWorker<Boolean, Void>() {
                protected Boolean doInBackground() throws Exception {
                    return apiClient.putById(UPDATE_PRODUCT, id, dtoProductIU); // http://localhost:8080/products/update/{id}
                }

                protected void done() {
                    try {
                        if (get()) {
                            JOptionPane.showMessageDialog(ProductUpdatePage.this, "Güncellendi");
                            dispose();
                            listener.onUpdated();
                        } else {
                            JOptionPane.showMessageDialog(ProductUpdatePage.this, "Güncellenemedi", "Hata", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(ProductUpdatePage.this, "Hata: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }.execute();
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
        java.awt.EventQueue.invokeLater(() -> new ProductUpdatePage().setVisible(true));
    }

    // Variables
    private javax.swing.JButton button_update;
    private javax.swing.JLabel label_barcode;
    private javax.swing.JLabel label_category;
    private javax.swing.JLabel label_color;
    private javax.swing.JLabel label_explanation;
    private javax.swing.JLabel label_number;
    private javax.swing.JLabel label_price;
    private javax.swing.JLabel label_productName;
    private javax.swing.JLabel label_size;
    private javax.swing.JLabel lbl_warning;
    private javax.swing.JPanel pnl_update;
    private javax.swing.JTextField text_barcode;
    private javax.swing.JTextField text_category;
    private javax.swing.JTextField text_color;
    private javax.swing.JTextField text_explanation;
    private javax.swing.JTextField text_number;
    private javax.swing.JTextField text_price;
    private javax.swing.JTextField text_productName;
    private javax.swing.JTextField text_size;
}
