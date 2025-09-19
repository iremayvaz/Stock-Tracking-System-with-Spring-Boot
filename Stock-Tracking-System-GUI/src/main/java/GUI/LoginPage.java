package GUI;

import client.AppContext;
import client.Client;
import model.dto.DtoEmployee;
import model.login.AuthRequest;
import javax.swing.*;
import static javax.swing.JOptionPane.WARNING_MESSAGE;

public class LoginPage extends javax.swing.JFrame {

    private static final String AUTH_LOGIN = "/login";
    private static final String AUTH_ME    = "/employees/me";
    private final Client apiClient = AppContext.getClient();

    // from JTextField
    public String getEmail() { return txt_email.getText(); }

    // from JPasswordField
    public String getPassword() {
        char[] password = pass_password.getPassword();
        return String.valueOf(password);
    }

    public LoginPage() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        pnl_login = new javax.swing.JPanel();
        lbl_icon = new javax.swing.JLabel();
        lbl_email = new javax.swing.JLabel();
        lbl_password = new javax.swing.JLabel();
        pass_password = new javax.swing.JPasswordField();
        txt_email = new javax.swing.JTextField();
        btn_register = new javax.swing.JButton();
        btn_login = new javax.swing.JButton();
        lbl_sts = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");

        pnl_login.setBackground(new java.awt.Color(255, 255, 255));
        pnl_login.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "LOGIN",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Sitka Text", java.awt.Font.BOLD, 14)));

        lbl_email.setText("Email");
        lbl_password.setText("Password");

        btn_register.setText("Register");
        btn_register.addActionListener(evt -> btn_registerActionPerformed());

        btn_login.setText("Login");
        btn_login.addActionListener(evt -> btn_loginActionPerformed());

        lbl_sts.setFont(new java.awt.Font("Sitka Text", java.awt.Font.ITALIC, 14));
        lbl_sts.setForeground(new java.awt.Color(255, 0, 0));
        lbl_sts.setText("STOCK TRACKING SYSTEM");

        javax.swing.GroupLayout pnl_loginLayout = new javax.swing.GroupLayout(pnl_login);
        pnl_login.setLayout(pnl_loginLayout);
        pnl_loginLayout.setHorizontalGroup(
                pnl_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnl_loginLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pnl_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lbl_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lbl_sts, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnl_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnl_loginLayout.createSequentialGroup()
                                                .addGroup(pnl_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lbl_email)
                                                        .addComponent(lbl_password))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                                                .addGroup(pnl_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(pass_password)
                                                        .addComponent(txt_email, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_loginLayout.createSequentialGroup()
                                                .addComponent(btn_register)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                                                .addComponent(btn_login)))
                                .addGap(16, 16, 16))
        );
        pnl_loginLayout.setVerticalGroup(
                pnl_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnl_loginLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pnl_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(pnl_loginLayout.createSequentialGroup()
                                                .addGroup(pnl_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lbl_email)
                                                        .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(pnl_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lbl_password)
                                                        .addComponent(pass_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(pnl_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(btn_register)
                                                        .addComponent(btn_login)))
                                        .addComponent(lbl_icon, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbl_sts)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pnl_login, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pnl_login, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void btn_registerActionPerformed() {
        RegisterPage loginToRegister = new RegisterPage();
        loginToRegister.setVisible(true);
        this.dispose();
    }

    private void btn_loginActionPerformed() {
        String email = getEmail();
        String password = getPassword();

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane,
                    "E-posta ve şifre zorunludur!",
                    "WARNING!",
                    WARNING_MESSAGE);
            return;
        }

        AuthRequest request = new AuthRequest();
        request.setEmail(email);
        request.setPassword(password);

        // butonu kilitle, imleci bekleme yap:
        btn_login.setEnabled(false);
        setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));

        new javax.swing.SwingWorker<DtoEmployee, Void>() {
            @Override
            protected DtoEmployee doInBackground() throws Exception{
                boolean login = apiClient.login(AUTH_LOGIN, request);
                if (!login) { throw new IllegalStateException("Geçersiz e-posta veya şifre!"); }

                try {
                    return apiClient.me(AUTH_ME);
                } catch (Exception ignore){
                    return null;
                }
            }

            @Override
            protected void done(){
                try {
                    DtoEmployee me = get();
                    String message = (me != null && me.getEmail() != null)
                            ? "Giriş başarılı: " + me.getEmail()
                            : "Giriş başarılı";

                    JOptionPane.showMessageDialog(LoginPage.this, message);

                    if(apiClient.hasAnyRole("VISITOR")) {
                        new ProductListPage().setVisible(true);
                    } else if(apiClient.hasAnyRole("CONSULTANT")) {
                        new EmployeeListPage().setVisible(true);
                    } else {
                        new MainPage().setVisible(true);
                    }

                    LoginPage.this.dispose();
                } catch (java.util.concurrent.ExecutionException e) {
                    Throwable cause = e.getCause();
                    JOptionPane.showMessageDialog(
                            LoginPage.this,
                            (cause != null ? cause.getMessage() : "Bilinmeyen hata"),
                            "Giriş başarısız",
                            JOptionPane.ERROR_MESSAGE
                    );
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    JOptionPane.showMessageDialog(
                            LoginPage.this,
                            "İşlem kesildi.",
                            "Giriş başarısız",
                            JOptionPane.ERROR_MESSAGE
                    );
                } finally {
                    btn_login.setEnabled(true);
                    setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                }
            }
        }.execute();
    }

    public static void main(String[] args) {
        // Nimbus varsa kullan, yoksa default Look&Feel kalsın
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}

        java.awt.EventQueue.invokeLater(() -> new LoginPage().setVisible(true));
    }

    // Variables
    private javax.swing.JButton btn_login;
    private javax.swing.JButton btn_register;
    private javax.swing.JLabel lbl_email;
    private javax.swing.JLabel lbl_icon;
    private javax.swing.JLabel lbl_password;
    private javax.swing.JLabel lbl_sts;
    private javax.swing.JPasswordField pass_password;
    private javax.swing.JPanel pnl_login;
    private javax.swing.JTextField txt_email;
}
