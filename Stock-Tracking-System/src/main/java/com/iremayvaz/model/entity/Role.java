package com.iremayvaz.model.entity;

import com.iremayvaz.model.entity.enums.Permission;
import com.iremayvaz.model.entity.enums.RoleName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RoleName name;

    @ElementCollection(fetch = FetchType.EAGER) // Set<Permission> bir entity değil o yüzden @ManyToMany yerine @ElementCollection. EAGER : role yüklenirken izinler hemen çekilir.
    @CollectionTable(name="role_permissions", // Koleksiyonun saklanacağı tablo
                    joinColumns=@JoinColumn(name="role_id"))
    @Enumerated(EnumType.STRING) // Enum'ı String olarak yazar
    @Column(name="permission", nullable = false)
    private Set<Permission> permissions = new HashSet<>();
}

