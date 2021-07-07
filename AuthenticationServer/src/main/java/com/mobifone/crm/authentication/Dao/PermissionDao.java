package com.mobifone.crm.authentication.Dao;

import com.mobifone.crm.authentication.Entity.RoleEntity;

import java.sql.SQLException;
import java.util.List;

public interface PermissionDao {
    public List<RoleEntity> findGroupByUserId(Long userId);

    public boolean checkPermissionByUserIdAndMenuName(Long userId, String menuName) throws Exception;
}
