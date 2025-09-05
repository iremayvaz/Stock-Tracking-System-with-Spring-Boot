package com.iremayvaz.model.entity;

import com.iremayvaz.model.entity.enums.Gender;
import com.iremayvaz.model.entity.enums.RoleName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "user")
public class Employee {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @Column(name = "tck_no")
    private String tck_no;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNum;

    @Column(name = "email")
    private String email;

    @Column(name = "position")
    @Enumerated(EnumType.STRING)
    private RoleName position;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;
}
