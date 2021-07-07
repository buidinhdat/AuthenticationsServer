package com.mobifone.crm.authentication.Configuration.Service.Impl;

import com.mobifone.crm.authentication.Configuration.Service.AppAuthorizer;
import com.mobifone.crm.authentication.Dao.PermissionDao;
import com.mobifone.crm.authentication.Entity.RoleEntity;
import com.mobifone.crm.authentication.Entity.UserEntity;
import com.mobifone.crm.authentication.Repository.GroupRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service("appAuthorizer")
public class AppAuthorizerImpl implements AppAuthorizer {

    private final Logger logger = LoggerFactory.getLogger(AppAuthorizerImpl.class);

    @Autowired
    public PermissionDao permissionDao;

    @Override
    public boolean authorize(Authentication authentication, String menuName) {

        boolean isAllow = false;
        try {
            UsernamePasswordAuthenticationToken user = (UsernamePasswordAuthenticationToken) authentication;
            if (user==null){
                return isAllow;
            }
            UserEntity userDetails = (UserEntity)user.getPrincipal();

            String userId=String.valueOf(userDetails.getUserId());
            if (userId==null || "".equals(userId.trim())) {
                return isAllow;
            }
            //Truy vấn vào CSDL theo userId + menuCode + action
            //Nếu có quyền thì
            if(checkPermission(Long.parseLong(userId),menuName))
            {
                isAllow = true;
            }else{
                logger.info("USER:{} not allow permission menuName {}",userId,menuName);
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
            throw e;
        }
        return isAllow;
    }

    public boolean checkPermission(Long userId , String menuName){
        try{
            return permissionDao.checkPermissionByUserIdAndMenuName(userId,menuName);
        }catch (Exception ex){
            return false;
        }
    }




}
