package com.mobifone.crm.authentication.Dao;

import com.mobifone.crm.authentication.Entity.UserEntity;

public interface UserDao {
    public UserEntity getUserByUserNamePassword(String userName, String password) throws Exception;

    public UserEntity getUserByUserName(String userName);

}
