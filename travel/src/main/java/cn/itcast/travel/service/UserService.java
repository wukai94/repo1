package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {
    Boolean regist(User registUser);

    User activeUser(String code);

    User login(User loginUser);
}
