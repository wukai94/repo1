package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {
private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public User regist(User registUser) {
        try {
            String sql="select * from tab_user where username=?";
            User user=template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),registUser.getUsername());
             return user;
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public void addUser(User registUser) {
        String sql="insert into tab_user values(null,?,?,?,?,?,?,?,?,?)";
        template.update(sql,registUser.getUsername(),registUser.getPassword(),registUser.getName(),registUser.getBirthday(),registUser.getSex(),registUser.getTelephone(),registUser.getEmail(),registUser.getStatus(),registUser.getCode());
    }

    @Override
    public User activeUser(String code) {
        try {
            String sql="select * from tab_user where code=?";
            User user=template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),code);
            return user;
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public void updateActive(User user) {
        String sql="update tab_user set status='Y',code=null where uid=?";
        template.update(sql,user.getUid());
    }

    @Override
    public User login(User loginUser) {
        try {
            String sql="select * from tab_user where username=? and password=?";
            User user=template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),loginUser.getUsername(),loginUser.getPassword());
            return user;
        } catch (DataAccessException e) {
            return null;
        }
    }
}
