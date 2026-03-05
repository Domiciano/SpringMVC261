package edu.co.icesi.introspringboot.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "permission")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "permission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RolePermission> rolePermissions;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<RolePermission> getRolePermissions() { return rolePermissions; }
    public void setRolePermissions(List<RolePermission> rolePermissions) { this.rolePermissions = rolePermissions; }
}
