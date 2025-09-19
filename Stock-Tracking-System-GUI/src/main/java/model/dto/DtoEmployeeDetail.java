package model.dto;

import model.entity.enums.Gender;
import model.entity.enums.RoleName;

public class DtoEmployeeDetail {
    private Long id;
    private String tck_no;
    private String firstName;
    private String lastName;
    private String phoneNum;
    private String email;
    private RoleName position;
    private Gender gender;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTck_no() {
        return tck_no;
    }

    public void setTck_no(String tck_no) {
        this.tck_no = tck_no;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPosition(RoleName position) {
        this.position = position;
    }

    public RoleName getPosition() {
        return position;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Gender getGender() {
        return gender;
    }
}

