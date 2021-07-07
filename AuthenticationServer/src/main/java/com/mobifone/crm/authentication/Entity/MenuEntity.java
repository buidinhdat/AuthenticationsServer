package com.mobifone.crm.authentication.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "menu", schema = "authenticationdb", catalog = "")
public class MenuEntity {
    private long menuId;
    private String menuName;
    private String description;
    private String status;

    @Id
    @Column(name = "MENU_ID")
    public long getMenuId() {
        return menuId;
    }

    public void setMenuId(long menuId) {
        this.menuId = menuId;
    }

    @Basic
    @Column(name = "MENU_NAME")
    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
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
        MenuEntity that = (MenuEntity) o;
        return menuId == that.menuId && Objects.equals(menuName, that.menuName) && Objects.equals(description, that.description) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuId, menuName, description, status);
    }
}
