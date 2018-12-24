package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {

    User regist(User registUser);

    void addUser(User registUser);

    User activeUser(String code);

    void updateActive(User user);

    User login(User loginUser);
}
