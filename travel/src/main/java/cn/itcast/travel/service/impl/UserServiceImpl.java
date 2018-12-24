package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
private UserDao userDao=new UserDaoImpl();
    @Override
    public Boolean regist(User registUser) {
        User user=userDao.regist(registUser);
        if(user!=null){
            return false;
        }
        String uuid = UuidUtil.getUuid();
        registUser.setCode(uuid);
        registUser.setStatus("N");
        userDao.addUser(registUser);
        String content="<a href='http://localhost/travel/user/active?code="+registUser.getCode()+"'>点击激活黑马旅游网</a>";
        MailUtils.sendMail(registUser.getEmail(),content,"激活邮件");
        return true;
    }

    @Override
    public User activeUser(String code) {
        User user=userDao.activeUser(code);
        if(user!=null){
            userDao.updateActive(user);
        }
        return user;
    }

    @Override
    public User login(User loginUser) {
        User user=userDao.login(loginUser);
        return user;
    }
}
