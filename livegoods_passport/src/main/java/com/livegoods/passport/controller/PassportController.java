package com.livegoods.passport.controller;

import com.livegoods.commons.pojo.Result;
import com.livegoods.passport.service.PassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class PassportController {
    @Autowired
    private PassportService passportService;

    /**
     * 发送验证码。正规实现是使用SMS服务，发送短信。
     * 当前系统使用输出打印的方式实现。
     *
     * 验证码使用逻辑：
     *  每个验证码只能使用唯一一次。无论是否正确，使用后立刻删除。
     *  重复申请验证码：
     *   删除已有验证码，生成新的验证码，每次发送的验证码给一个标记符号。
     *
     * @param phone
     * @return
     *  { "status":200, "msg":"成功结果消息" }
     *  { "status":500, "msg":"失败结果消息" }
     */

    @PostMapping("/sendyzm")
    public Result<Object> sendValidateCode(String phone){
        return passportService.sendValidateCode(phone);
    }
}
