package com.mobifone.crm.authentication.Dao.Impl;

import com.mobifone.crm.authentication.Dao.UserDao;
import com.mobifone.crm.authentication.Entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    EntityManager em;

    public UserEntity getUserByUserNamePassword(String userName, String password) throws Exception {
        try {
            String hql = "select ur from UserEntity ur where ur.userName=:userName and ur.password=:password and ur.status='1'";
            Query query = em.createQuery(hql, UserEntity.class);
            query.setParameter("userName", userName);
            query.setParameter("password", password);
            return (UserEntity) query.getSingleResult();
        }catch (Exception ex){
            throw new Exception("Account or password does not exist");
        }

    }

    @Override
    public UserEntity getUserByUserName(String userName) {
        try {
            String hql = "select ur from UserEntity ur where ur.userName=:userName";
            Query query = em.createQuery(hql, UserEntity.class);
            query.setParameter("userName", userName);
            return (UserEntity) query.getSingleResult();

        }catch (Exception ex){
           return null;
        }
    }

}
