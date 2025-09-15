package model.entity;

import model.entity.enums.Gender;

public class Employee {
    private Long id;
    private User user;
    private String tck_no;
    private String firstName;
    private String lastName;
    private String phoneNum;
    private Gender gender;

    public void setUser(User user) {
        this.user = user;
    }
}
