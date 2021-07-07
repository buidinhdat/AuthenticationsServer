package com.mobifone.crm.authentication.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_role", schema = "authenticationdb", catalog = "")
public class UserRoleEntity {
    private long userRoleId;
    private String status;

    @Id
    @Column(name = "USER_ROLE_ID")
    public long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(long userRoleId) {
        this.userRoleId = userRoleId;
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
        UserRoleEntity that = (UserRoleEntity) o;
        return userRoleId == that.userRoleId && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userRoleId, status);
    }
}
