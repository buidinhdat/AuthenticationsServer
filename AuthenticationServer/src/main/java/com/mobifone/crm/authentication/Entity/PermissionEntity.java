package com.mobifone.crm.authentication.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "permission", schema = "authenticationdb", catalog = "")
public class PermissionEntity {
    private long permissionId;
    private String status;

    @Id
    @Column(name = "PERMISSION_ID")
    public long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(long permissionId) {
        this.permissionId = permissionId;
    }

    @Basic
    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PermissionEntity that = (PermissionEntity) o;
        return permissionId == that.permissionId && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(permissionId, status);
    }
}
