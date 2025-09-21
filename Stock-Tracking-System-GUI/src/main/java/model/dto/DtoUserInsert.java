package model.dto;

import model.entity.enums.Gender;
import model.entity.enums.RoleName;

public class DtoUserInsert {
    private String tck_no;
    private String firstName;
    private String lastName;
    private String phoneNum;
    private String email;
    private String password;
    private RoleName position;
    private Gender gender;

    // ŞİFRE
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    // EMAIL
    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    // CİNSİYET
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    // POZİSYON
    public RoleName getPosition() {
        return position;
    }

    public void setPosition(RoleName position) {
        this.position = position;
    }

    // AD
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // SOYAD
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // TEL NO
    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    // TCK NO
    public String getTck_no() {
        return tck_no;
    }

    public void setTck_no(String tck_no) {
        this.tck_no = tck_no;
    }

}
