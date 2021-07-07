package com.mobifone.crm.authentication.Service;

import com.mobifone.crm.authentication.Entity.UserEntity;

public interface UserService {

    public UserEntity getUserByUserId(Long userId);

    public UserEntity getUserByUsernamePassword(String userName, String password) throws Exception;

    public void createUserEntity(UserEntity entity) throws Exception;

    public UserEntity getUserByUserName(String userName);


}
