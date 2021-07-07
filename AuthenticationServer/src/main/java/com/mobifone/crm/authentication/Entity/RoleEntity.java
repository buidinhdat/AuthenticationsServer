package com.mobifone.crm.authentication.Entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "role", schema = "authenticationdb", catalog = "")
public class RoleEntity {
    private long roleId;
    private String roleName;
    private String status;
    private String description;
    private Date createDate;
    private Date modifyDate;

    @Id
    @Column(name = "ROLE_ID")
    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "ROLE_NAME")
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Basic
    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "CREATE_DATE")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "MODIFY_DATE")
    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleEntity that = (RoleEntity) o;
        return roleId == that.roleId && Objects.equals(roleName, that.roleName) && Objects.equals(status, that.status) && Objects.equals(description, that.description) && Objects.equals(createDate, that.createDate) && Objects.equals(modifyDate, that.modifyDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, roleName, status, description, createDate, modifyDate);
    }
}
