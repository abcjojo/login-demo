package com.lyj.wechatlogindemo.service;

import java.util.Map;

public interface AppLoginRegisterService {

    /**
     * @throws
     * @title weChatLogin
     * @description 微信授权登录
     * @author Kuangzc
     * @updateTime 2019-9-12 16:00:51
     */
    Map<String, Object> weChatLogin(String code);
}
