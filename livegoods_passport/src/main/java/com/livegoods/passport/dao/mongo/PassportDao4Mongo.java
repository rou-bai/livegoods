package com.livegoods.passport.dao.mongo;

import com.livegoods.pojo.LoginLog;
import com.livegoods.pojo.User;

public interface PassportDao4Mongo {
    // 记录日志
    void insertPassportLog(LoginLog log);

    // 新增用户
    void insertUser(User user);

    // 更新用户
    void updateUser(User user);

    // 根据用户名查询用户
    User findByPhone(String phone);
}
