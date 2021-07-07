package com.mobifone.crm.authentication.Dao.Impl;

import com.mobifone.crm.authentication.Dao.PermissionDao;
import com.mobifone.crm.authentication.Entity.RoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PermissionDaoImpl implements PermissionDao {
    @Autowired
    public EntityManager em;

    @Autowired public JdbcTemplate jdbcTemplate;

    @Override
    public List<RoleEntity> findGroupByUserId(Long userId) {
        try {
            Query query = em.createQuery("select   r from RoleEntity  r where  r.roleId in ( select ru.roleId from UserRoleEntity ru where ru.userId=:userId ) ");
            query.setParameter("userId",userId);
            return query.getResultList();

        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public boolean checkPermissionByUserIdAndMenuName(Long userId, String menuName) throws Exception {
        Connection con= null;
        PreparedStatement stmt= null;
        ResultSet rs= null;
        String sql = "  select m.menu_id from menu m where m.menu_name= ? and m.status ='1'  and m.menu_id in " +
                " (select per.menu_id from permission per where per.status='1' and  per.role_id in " +
                " (select r.role_id from user_role ur ,user u , role r where ur.user_id= u.user_id and ur.role_id = r.role_id and r.status = '1' and u.status='1'  and ur.status ='1' and u.user_id= ? ) " +
                " ) ";
        try{
            con= jdbcTemplate.getDataSource().getConnection();
            stmt=con.prepareStatement(sql);
            stmt.setString(1,menuName);
            stmt.setLong(2,userId);
            rs= stmt.executeQuery();
            if(rs.next()){
                return true;
            }


        }catch (Exception ex){
            ex.printStackTrace();

        }finally {
            if(con!=null) con.close();
            if(stmt!=null) stmt.close();
            if(rs!=null) rs.close();
        }
        return false;
    }
}
