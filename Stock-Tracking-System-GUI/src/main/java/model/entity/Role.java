package model.entity;

import model.entity.enums.Permission;
import model.entity.enums.RoleName;

import java.util.HashSet;
import java.util.Set;

public class Role {
    private Long id;
    private RoleName name;
    private Set<Permission> permissions = new HashSet<>();
}

