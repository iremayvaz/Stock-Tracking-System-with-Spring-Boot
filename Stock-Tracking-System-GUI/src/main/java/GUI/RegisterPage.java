package GUI;

import client.AppContext;
import client.Client;
import model.dto.DtoUser;
import model.dto.DtoUserIU;
import model.entity.enums.Gender;
import model.entity.enums.RoleName;

import javax.swing.JOptionPane;

public class RegisterPage extends javax.swing.JFrame {

    private final Client apiClient = AppContext.getClient();

    private static final String AUTH_REGISTER = "/register";

    // from JTextField
    public String getFirstName() { return txt_name.getText().trim(); }

    public String getLastName() { return txt_surname.getText().trim(); }

    public String getPhoneNum() { return txt_phoneNum.getText().trim(); }

    public String getEmail() { return txt_email.getText().trim(); }

    public String getTckNo() { return txt_tckno.getText(); }

    // from JPasswordField
    public String getPassword() {
        char[] password = pass_password.getPassword();
        return String.valueOf(password);
    }

    // from JComboBox
    public RoleName getPosition() {
         return comboBox_position.getItemAt(comboBox_position.getSelectedIndex());
    }

    // from JRadioButton
    public Gender getGender() {
        if (rBtn_male.isSelected()) return Gender.MALE;
        if (rBtn_female.isSelected()) return Gender.FEMALE;
        return null;
    }

    public RegisterPage() { initComponents(); }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        btnGroup_gender = new javax.swing.ButtonGroup();
        pnl_register = new javax.swing.JPanel();
        btn_cancel = new javax.swing.JButton();
        btn_register = new javax.swing.JButton();
        lbl_tckno = new javax.swing.JLabel();
        lbl_name = new javax.swing.JLabel();
        lbl_surname = new javax.swing.JLabel();
        lbl_email = new javax.swing.JLabel();
        lbl_phoneNum = new javax.swing.JLabel();
        txt_name = new javax.swing.JTextField();
        txt_surname = new javax.swing.JTextField();
        txt_email = new javax.swing.JTextField();
        txt_phoneNum = new javax.swing.JTextField();
        lbl_position = new javax.swing.JLabel();
        comboBox_position = new javax.swing.JComboBox<>();
        lbl_password = new javax.swing.JLabel();
        lbl_gender = new javax.swing.JLabel();
        rBtn_male = new javax.swing.JRadioButton();
        rBtn_female = new javax.swing.JRadioButton();
        pass_password = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Register");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) { formWindowClosing(evt); }
        });

        pnl_register.setBackground(new java.awt.Color(255, 255, 255));
        pnl_register.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "REGISTER",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.TOP, new java.awt.Font("Sitka Text", 1, 14)));

        btn_cancel.setText("Cancel");
        btn_cancel.addActionListener(this::btn_cancelActionPerformed);

        btn_register.setText("Register");
        btn_register.addActionListener(this::btn_registerActionPerformed);

        lbl_tckno.setText("TCK number");
        lbl_name.setText("Name");
        lbl_surname.setText("Surname");
        lbl_email.setText("Email");
        lbl_phoneNum.setText("Phone Number");
        lbl_position.setText("Position");
        lbl_password.setText("Password");
        lbl_gender.setText("Gender");

        comboBox_position.setModel(new javax.swing.DefaultComboBoxModel<>(RoleName.values()));

        btnGroup_gender.add(rBtn_male);
        rBtn_male.setText("Male");
        btnGroup_gender.add(rBtn_female);
        rBtn_female.setText("Female");

        javax.swing.GroupLayout pnl_registerLayout = new javax.swing.GroupLayout(pnl_register);
        pnl_register.setLayout(pnl_registerLayout);
        pnl_registerLayout.setHorizontalGroup(
                pnl_registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnl_registerLayout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addGroup(pnl_registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnl_registerLayout.createSequentialGroup()
                                                .addComponent(btn_cancel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 200, Short.MAX_VALUE)
                                                .addComponent(btn_register))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_registerLayout.createSequentialGroup()
                                                .addGroup(pnl_registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lbl_tckno)
                                                        .addComponent(lbl_name)
                                                        .addComponent(lbl_surname)
                                                        .addComponent(lbl_email)
                                                        .addComponent(lbl_phoneNum)
                                                        .addComponent(lbl_position)
                                                        .addComponent(lbl_password)
                                                        .addComponent(lbl_gender))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                                                .addGroup(pnl_registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addGroup(pnl_registerLayout.createSequentialGroup()
                                                                .addComponent(rBtn_male)
                                                                .addGap(45, 45, 45)
                                                                .addComponent(rBtn_female))
                                                        .addComponent(txt_tckno)
                                                        .addComponent(txt_phoneNum)
                                                        .addComponent(txt_email)
                                                        .addComponent(txt_surname)
                                                        .addComponent(txt_name)
                                                        .addComponent(comboBox_position, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(pass_password))))
                                .addGap(77, 77, 77))
        );
        pnl_registerLayout.setVerticalGroup(
                pnl_registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_registerLayout.createSequentialGroup()
                                .addGap(34, 34, 34)

                                // 1) TCK NO satırı (YENİ)
                                .addGroup(pnl_registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbl_tckno)
                                        .addComponent(txt_tckno, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))

                                .addGap(23, 23, 23)

                                // 2) Name satırı (mevcut)
                                .addGroup(pnl_registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbl_name)
                                        .addComponent(txt_name, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))

                                .addGap(23, 23, 23)
                                .addGroup(pnl_registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbl_surname)
                                        .addComponent(txt_surname, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(31, 31, 31)
                                .addGroup(pnl_registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbl_phoneNum)
                                        .addComponent(txt_phoneNum, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(32, 32, 32)
                                .addGroup(pnl_registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lbl_email))
                                .addGap(23, 23, 23)
                                .addGroup(pnl_registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbl_password)
                                        .addComponent(pass_password, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26)
                                .addGroup(pnl_registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbl_position)
                                        .addComponent(comboBox_position, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(43, 43, 43)
                                .addGroup(pnl_registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbl_gender)
                                        .addComponent(rBtn_male)
                                        .addComponent(rBtn_female))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                                .addGroup(pnl_registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btn_cancel)
                                        .addComponent(btn_register))
                                .addGap(36, 36, 36))
        );


        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pnl_register, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pnl_register, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void btn_registerActionPerformed(java.awt.event.ActionEvent evt) {
        DtoUserIU dto = new DtoUserIU();

        dto.setFirstName(getFirstName());
        dto.setLastName(getLastName());
        dto.setPhoneNum(getPhoneNum());
        dto.setEmail(getEmail());
        dto.setPassword(getPassword());
        dto.setPosition(getPosition());
        dto.setGender(getGender());
        dto.setTck_no(getTckNo());

        new javax.swing.SwingWorker<DtoUser, Void>(){
            @Override
            protected DtoUser doInBackground() throws Exception{
                return apiClient.register(AUTH_REGISTER, dto);
            }

            @Override
            protected void done() {
                try {
                    DtoUser created = get();
                    JOptionPane.showMessageDialog(rootPane,
                            "Kayıt başarılı: " + created.getEmail() + "Lüfen giriş yapınız.");

                    GUI.LoginPage toLogin = new GUI.LoginPage();
                    toLogin.setVisible(true);

                    RegisterPage.this.dispose();

                } catch (IllegalStateException e) {
                    JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Uyarı", JOptionPane.WARNING_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, "Kayıt başarısız: " + e.getMessage(),
                            "Hata", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }

    private void btn_cancelActionPerformed(java.awt.event.ActionEvent evt) {
        Object[] options = {"Exit", "Login"};
        int isExited = JOptionPane.showOptionDialog(rootPane,
                "Are you sure to exit?",
                "WARNING!",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                options,
                options[0]);

        if (isExited == JOptionPane.YES_OPTION) {
            this.dispose();
        } else if (isExited == JOptionPane.NO_OPTION) {
            GUI.LoginPage registerToLogin = new GUI.LoginPage();
            registerToLogin.setVisible(true);
            this.dispose();
        }
    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {
        LoginPage login = new LoginPage();
        login.setVisible(true);
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
        java.awt.EventQueue.invokeLater(() -> new RegisterPage().setVisible(true));
    }

    // Variables
    private javax.swing.ButtonGroup btnGroup_gender;
    private javax.swing.JButton btn_cancel;
    private javax.swing.JButton btn_register;
    private javax.swing.JComboBox<RoleName> comboBox_position;
    private javax.swing.JLabel lbl_email;
    private javax.swing.JLabel lbl_gender;
    private javax.swing.JLabel lbl_tckno;
    private javax.swing.JLabel lbl_name;
    private javax.swing.JLabel lbl_password;
    private javax.swing.JLabel lbl_phoneNum;
    private javax.swing.JLabel lbl_position;
    private javax.swing.JLabel lbl_surname;
    private javax.swing.JPasswordField pass_password;
    private javax.swing.JPanel pnl_register;
    private javax.swing.JRadioButton rBtn_female;
    private javax.swing.JRadioButton rBtn_male;
    private javax.swing.JTextField txt_tckno;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_name;
    private javax.swing.JTextField txt_phoneNum;
    private javax.swing.JTextField txt_surname;
}
