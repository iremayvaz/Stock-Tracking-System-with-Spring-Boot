package GUI;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class EmployeeUpdatePage extends javax.swing.JFrame {

    private static final String EMPLOYEES        = "/employees";
    private static final String UPDATE_EMPLOYEES = EMPLOYEES + "/update"; //   /employees/update

    //check data (iptalde sonsuz döngü fix)
    public void checkData(JTextField field, String regex, String input, String fieldName) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        boolean isFound = false;

        if (!matcher.matches()) {
            while (!matcher.matches()) {
                String newData = JOptionPane.showInputDialog(
                        rootPane, fieldName + " invalid");
                if (newData == null) { // kullanıcı iptal etti
                    isFound = false;
                    break;
                } else {
                    matcher = pattern.matcher(newData);
                }
            }
            if (matcher.matches()) {
                field.setText(matcher.group());
                isFound = true;
            }
        } else {
            isFound = true;
        }

        if (!isFound) {
            field.setText("");
        }
    }

    // get person name
    public String getName() {
        checkData(txt_name, "^[A-Z][a-z]+$", txt_name.getText(), "name");
        return txt_name.getText();
    }

    // get person surname
    public String getSurname() {
        checkData(txt_surname, "^[A-Z][a-z]+$", txt_surname.getText(), "surname");
        return txt_surname.getText();
    }

    // get person phone number
    public String getPhoneNum() {
        checkData(txt_phoneNum, "^[1-9][0-9]{9}$", txt_phoneNum.getText(), "phone number");
        return txt_phoneNum.getText();
    }

    // get person email (değiştirilemez)
    public String getEmail() {
        return txt_email.getText();
    }

    // get person password
    public String getPassword() {
        checkData(txt_password, "^(.{8,})$", txt_password.getText(), "password");
        return txt_password.getText();
    }

    // get person position
    public String getPosition() {
        return comboBox_position.getItemAt(comboBox_position.getSelectedIndex());
    }

    // get person gender (seçili değilse '\0')
    public char getGender() {
        if (rBtn_female.isSelected()) return 'F';
        if (rBtn_male.isSelected())   return 'M';
        return '\0';
    }

    public EmployeeUpdatePage() {
        initComponents();
    }

    /*public GUI.EmployeeUpdatePage(Person person) {
        initComponents();
        txt_name.setText(person.getName());
        txt_surname.setText(person.getSurname());
        txt_phoneNum.setText(person.getPhoneNum());
        txt_email.setText(person.getEmail());
        txt_password.setText(person.getPassword());
        comboBox_position.setSelectedItem(person.getPosition());

        if (person.getGender() == 'M') rBtn_male.setSelected(true);
        else if (person.getGender() == 'F') rBtn_female.setSelected(true);

        // değiştirilemesin:
        txt_email.setEditable(false);
        rBtn_female.setEnabled(false);
        rBtn_male.setEnabled(false);
    }*/

    @SuppressWarnings("unchecked")
    private void initComponents() {
        btnGroup_gender = new javax.swing.ButtonGroup();
        pnl_personUpdate = new javax.swing.JPanel();
        lbl_name = new javax.swing.JLabel();
        lbl_surname = new javax.swing.JLabel();
        lbl_phoneNum = new javax.swing.JLabel();
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

        lbl_name.setText("Name");
        lbl_surname.setText("Surname");
        lbl_phoneNum.setText("Phone Number");
        lbl_email.setText("Email");
        lbl_password.setText("Password");
        lbl_position.setText("Position");
        lbl_gender.setText("Gender");

        comboBox_position.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[] { "Choose...", "Employee", "Accountant", "Secretary",
                        "Boss", "Consultant", "Visitor", "Authorized" }));

        btnGroup_gender.add(rBtn_male);
        rBtn_male.setText("Male");
        btnGroup_gender.add(rBtn_female);
        rBtn_female.setText("Female");

        btn_update.setText("Update");
        //btn_update.addActionListener(this::btn_updateActionPerformed);

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
                                                        .addComponent(txt_name)))
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

    /*private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {
        boolean ok = DatabaseManager.updatePerson(
                getEmail(),
                new Person(getName(), getSurname(),
                        getPhoneNum(), getEmail(),
                        getPassword(), getPosition(),
                        getGender()));

        if (ok) {
            JOptionPane.showMessageDialog(rootPane,
                    "Updated successfully",
                    "Person Updating",
                    PLAIN_MESSAGE);

            // Demo: listede ne var göster
            DatabaseManager.showPersonals(Person_personalList.personalList);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(rootPane,
                    "Failed to update.",
                    "FAIL",
                    WARNING_MESSAGE);
        }
    }*/

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
    private javax.swing.JComboBox<String> comboBox_position;
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
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_name;
    private javax.swing.JTextField txt_password;
    private javax.swing.JTextField txt_phoneNum;
    private javax.swing.JTextField txt_surname;
}
