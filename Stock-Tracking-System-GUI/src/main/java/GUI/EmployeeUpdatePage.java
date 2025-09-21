package GUI;

import client.AppContext;
import client.Client;
import model.dto.DtoUserUpdate;
import model.dto.DtoUserInsert;
import model.entity.enums.Gender;
import model.entity.enums.RoleName;

import javax.swing.*;

public class EmployeeUpdatePage extends javax.swing.JFrame {

    private final Client apiClient = AppContext.getClient();
    private final Long id;

    private static final String EMPLOYEES        = "/employees";
    private static final String UPDATE_EMPLOYEES = EMPLOYEES + "/update"; //   /employees/update

    public String getTckNo() { return txt_tckno.getText(); }

    public String getFirstname() { return txt_name.getText(); }

    public String getLastname() { return txt_surname.getText(); }

    public String getPhoneNum() { return txt_phoneNum.getText(); }

    public String getEmail() {
        return txt_email.getText();
    }

    public String getPassword() { return txt_password.getText();}

    public RoleName getPosition() {
        return comboBox_position.getItemAt(comboBox_position.getSelectedIndex());
    }

    public Gender getGender() {
        if (rBtn_female.isSelected()) return Gender.FEMALE;
        if (rBtn_male.isSelected())   return Gender.MALE;
        return null;
    }

    public EmployeeUpdatePage() {
        this.id = null;
        initComponents();
        comboBox_position.setModel(new javax.swing.DefaultComboBoxModel<>(RoleName.values()));
        rBtn_male.setSelected(true);
        btn_update.setEnabled(false);
    }


    public EmployeeUpdatePage(Long id, DtoUserUpdate dto) {
        this.id = id;
        initComponents();
        txt_tckno.setText(dto.getTck_no());
        txt_name.setText(dto.getFirstName());
        txt_surname.setText(dto.getLastName());
        txt_phoneNum.setText(dto.getPhoneNum());
        txt_email.setText(dto.getEmail());
        comboBox_position.setSelectedItem(dto.getPosition()); // RoleName enum combobox
        if (dto.getGender() == Gender.MALE) rBtn_male.setSelected(true);
        else if(dto.getGender() == Gender.FEMALE) rBtn_female.setSelected(true);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        btnGroup_gender = new javax.swing.ButtonGroup();
        pnl_personUpdate = new javax.swing.JPanel();
        lbl_tckno = new javax.swing.JLabel();
        lbl_name = new javax.swing.JLabel();
        lbl_surname = new javax.swing.JLabel();
        lbl_phoneNum = new javax.swing.JLabel();
        txt_tckno = new javax.swing.JTextField();
        txt_name = new javax.swing.JTextField();
        txt_surname = new javax.swing.JTextField();
        txt_phoneNum = new javax.swing.JTextField();
        lbl_email = new javax.swing.JLabel();
        txt_email = new javax.swing.JTextField();
        lbl_password = new javax.swing.JLabel();
        txt_password = new javax.swing.JTextField();
        lbl_position = new javax.swing.JLabel();
        lbl_gender = new javax.swing.JLabel();
        comboBox_position = new javax.swing.JComboBox<>();
        rBtn_male = new javax.swing.JRadioButton();
        rBtn_female = new javax.swing.JRadioButton();
        btn_update = new javax.swing.JButton();
        lbl_warning = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pnl_personUpdate.setBackground(new java.awt.Color(255, 255, 255));
        pnl_personUpdate.setBorder(javax.swing.BorderFactory.createTitledBorder(
                null, "Update", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Sitka Text", 1, 14)));

        lbl_tckno.setText("TCK number");
        lbl_name.setText("Name");
        lbl_surname.setText("Surname");
        lbl_phoneNum.setText("Phone Number");
        lbl_email.setText("Email");
        lbl_password.setText("Password");
        lbl_position.setText("Position");
        lbl_gender.setText("Gender");

        comboBox_position.setModel(new javax.swing.DefaultComboBoxModel<>(RoleName.values()));

        btnGroup_gender.add(rBtn_male);
        rBtn_male.setText("Male");
        btnGroup_gender.add(rBtn_female);
        rBtn_female.setText("Female");

        btn_update.setText("Update");
        btn_update.addActionListener(this::btn_updateActionPerformed);

        lbl_warning.setFont(new java.awt.Font("Segoe UI", 3, 14));
        lbl_warning.setForeground(new java.awt.Color(255, 0, 0));
        lbl_warning.setText("Change the informations in accordance with demand.");

        javax.swing.GroupLayout pnl_personUpdateLayout = new javax.swing.GroupLayout(pnl_personUpdate);
        pnl_personUpdate.setLayout(pnl_personUpdateLayout);
        pnl_personUpdateLayout.setHorizontalGroup(
                pnl_personUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_personUpdateLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(pnl_personUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(pnl_personUpdateLayout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(btn_update))
                                        .addGroup(pnl_personUpdateLayout.createSequentialGroup()
                                                .addGroup(pnl_personUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lbl_tckno)
                                                        .addComponent(lbl_name)
                                                        .addComponent(lbl_phoneNum)
                                                        .addComponent(lbl_surname)
                                                        .addComponent(lbl_email)
                                                        .addComponent(lbl_password)
                                                        .addComponent(lbl_position)
                                                        .addComponent(lbl_gender))
                                                .addGap(49, 49, 49)
                                                .addGroup(pnl_personUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(pnl_personUpdateLayout.createSequentialGroup()
                                                                .addComponent(rBtn_male)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                                                                .addComponent(rBtn_female))
                                                        .addComponent(txt_password)
                                                        .addComponent(txt_email)
                                                        .addComponent(txt_surname)
                                                        .addComponent(txt_phoneNum)
                                                        .addComponent(comboBox_position, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(txt_name)
                                                        .addComponent(txt_tckno)))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnl_personUpdateLayout.createSequentialGroup()
                                                .addGap(38, 38, 38)
                                                .addComponent(lbl_warning, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)))
                                .addGap(31, 31, 31))
        );
        pnl_personUpdateLayout.setVerticalGroup(
                pnl_personUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnl_personUpdateLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lbl_warning)
                                .addGap(9, 9, 9)
                                .addGroup(pnl_personUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbl_tckno)
                                        .addComponent(txt_tckno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnl_personUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbl_name)
                                        .addComponent(txt_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnl_personUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbl_surname)
                                        .addComponent(txt_surname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnl_personUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbl_phoneNum)
                                        .addComponent(txt_phoneNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnl_personUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbl_email)
                                        .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnl_personUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbl_password)
                                        .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnl_personUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbl_position, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(comboBox_position, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnl_personUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbl_gender)
                                        .addComponent(rBtn_male)
                                        .addComponent(rBtn_female))
                                .addGap(18, 18, 18)
                                .addComponent(btn_update)
                                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pnl_personUpdate, javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pnl_personUpdate, javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {
        // ACL kontrol
        if (!apiClient.hasPermission("EMPLOYEE_UPDATE")
                && !apiClient.hasAnyRole("BOSS", "AUTHORIZED", "CONSULTANT", "SECRETARY")) {
            JOptionPane.showMessageDialog(this, "İzniniz yok.", "Yetki", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DtoUserInsert dtoUserInsert = new DtoUserInsert();
        dtoUserInsert.setTck_no(txt_tckno.getText().trim());
        dtoUserInsert.setFirstName(txt_name.getText().trim());
        dtoUserInsert.setLastName(txt_surname.getText().trim());
        dtoUserInsert.setPhoneNum(txt_phoneNum.getText().trim());
        dtoUserInsert.setEmail(txt_email.getText().trim());
        dtoUserInsert.setPosition((RoleName) comboBox_position.getSelectedItem());
        dtoUserInsert.setGender(rBtn_male.isSelected() ? Gender.MALE : Gender.FEMALE);

        String newPass = new String(getPassword()).trim();
        dtoUserInsert.setPassword(newPass.isEmpty() ? null : newPass); // boşsa null gönder

        new javax.swing.SwingWorker<Boolean, Void>() {
            protected Boolean doInBackground() throws Exception {
                return apiClient.putById(UPDATE_EMPLOYEES, id, dtoUserInsert); // http://localhost:8080/employees/update/{id}
            }

            protected void done() {
                try {
                    if (get()) {
                        JOptionPane.showMessageDialog(EmployeeUpdatePage.this, "Güncellendi");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(EmployeeUpdatePage.this, "Güncellenemedi", "Hata", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(EmployeeUpdatePage.this, "Hata: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info :
                    javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}
        java.awt.EventQueue.invokeLater(() -> new EmployeeUpdatePage().setVisible(true));
    }

    // Variables
    private javax.swing.ButtonGroup btnGroup_gender;
    private javax.swing.JButton btn_update;
    private javax.swing.JComboBox<RoleName> comboBox_position;
    private javax.swing.JLabel lbl_tckno;
    private javax.swing.JLabel lbl_email;
    private javax.swing.JLabel lbl_gender;
    private javax.swing.JLabel lbl_name;
    private javax.swing.JLabel lbl_password;
    private javax.swing.JLabel lbl_phoneNum;
    private javax.swing.JLabel lbl_position;
    private javax.swing.JLabel lbl_surname;
    private javax.swing.JLabel lbl_warning;
    private javax.swing.JPanel pnl_personUpdate;
    private javax.swing.JRadioButton rBtn_female;
    private javax.swing.JRadioButton rBtn_male;
    private javax.swing.JTextField txt_tckno;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_name;
    private javax.swing.JTextField txt_password;
    private javax.swing.JTextField txt_phoneNum;
    private javax.swing.JTextField txt_surname;
}
