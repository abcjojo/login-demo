package com.lyj.wechatlogindemo.controller;
import com.lyj.wechatlogindemo.service.AppLoginRegisterService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 *    APP端授权微信登录demo
 *     参考文档地址：https://blog.csdn.net/a1101282836/article/details/101700263
 */
@RestController
@Slf4j
public class UserController {

    @Resource
    private AppLoginRegisterService appLoginRegisterService;

    /**
     * @throws
     * @title weChatLogin
     * @description 微信授权登录
     */
    @ApiOperation(value = "微信授权登录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "code", value = "用户换取access_token的code，仅在ErrCode为0时有效", required = true, dataType = "String")
    })
    @GetMapping("/weChatLogin")
    protected Map<String, Object> weChatLogin(@RequestParam String code) {
        return appLoginRegisterService.weChatLogin(code);
    }

}
