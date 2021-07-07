package com.mobifone.crm.authentication.Service.Impl;

import com.mobifone.crm.authentication.Dao.UserDao;
import com.mobifone.crm.authentication.Entity.UserEntity;
import com.mobifone.crm.authentication.Repository.UserRepository;
import com.mobifone.crm.authentication.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDao userDao;

    @Override
    public UserEntity getUserByUserId(Long userId) {
        try {
            return (UserEntity) userRepository.findById(userId).get();
        }catch (Exception ex){
            return null;
        }
    }

    @Override
    public UserEntity getUserByUsernamePassword(String userName, String password) throws Exception {
        return userDao.getUserByUserNamePassword(userName,password);
    }

    @Override
    public void createUserEntity(UserEntity entity) throws Exception {
        userRepository.save(entity);
    }

    @Override
    public UserEntity getUserByUserName(String userName) {
        return userDao.getUserByUserName(userName);
    }
}
