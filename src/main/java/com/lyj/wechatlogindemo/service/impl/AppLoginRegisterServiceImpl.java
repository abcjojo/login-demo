package com.lyj.wechatlogindemo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lyj.wechatlogindemo.dao.AppCoreMemberMapper;
import com.lyj.wechatlogindemo.dto.ResponseMemberDTO;
import com.lyj.wechatlogindemo.dto.UserInfo;
import com.lyj.wechatlogindemo.service.AppLoginRegisterService;
import com.lyj.wechatlogindemo.utils.AuthUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class AppLoginRegisterServiceImpl implements AppLoginRegisterService {

    @Resource
    private AppCoreMemberMapper appCoreMemberMapper;
    /**
     * @throws
     * @title weChatLogin
     * @description 微信授权登录
     * @author Kuangzc
     * @updateTime 2019-9-12 16:00:51
     */
    public Map<String, Object> weChatLogin(String code) {
        try {
            //通过第一步获得的code获取微信授权信息
            String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + AuthUtil.APPID + "&secret="
                    + AuthUtil.APPSECRET + "&code=" + code + "&grant_type=authorization_code";
            JSONObject jsonObject = AuthUtil.doGetJson(url);
            String openid = jsonObject.getString("openid");
            String token = jsonObject.getString("access_token");
            String infoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + token + "&openid=" + openid
                    + "&lang=zh_CN";
            JSONObject userInfo = AuthUtil.doGetJson(infoUrl);

            //成功获取授权,以下部分为业务逻辑处理了，根据个人业务需求写就可以了
            // todo 业务逻辑伪代码
            if (userInfo != null && openid != null) {
                String nickname = userInfo.getString("nickname");
                String headimgurl = userInfo.getString("headimgurl");
                headimgurl = headimgurl.replace("\\", "");
                //根据openid查询时候有用户信息
                ResponseMemberDTO memberDTO = appCoreMemberMapper.getMemberInfoByOpenid(openid);
                if (Objects.isNull(memberDTO)) {


                    //没有绑定用户请前往绑定
                    HashMap map = new HashMap();
                    map.put("state", "1");
                    map.put("openid", openid);
                    map.put("token", "");
                    map.put("nickname", nickname);
                    map.put("photo", headimgurl);
                    map.put("msg", "登录成功");
                    return map;
                } else {

                    //已经绑定用户信息直接登录
                    UserInfo userInfos = UserInfo.build(memberDTO.getId().longValue(), memberDTO.getMobile(), new Date(), 1);
                    String Token = tokenManager.setToken(userInfos);
                    // 缓存权限
                    Map<String, String> httpUrl = new HashMap<>();
                    httpUrl.put("kylin", "shuoye.com:8484");
                    authHandler.setRoleUrl(memberDTO.getId().toString(), httpUrl);
                    HashMap map = new HashMap();
                    map.put("state", "2");
                    map.put("openid", openid);
                    map.put("token", Token);
                    map.put("msg", "登录成功");
                    return map;
                }
            } else {
                HashMap map = new HashMap();
                map.put("msg", "登录失败");
                return map;
            }
        } catch (Exception e) {
            HashMap map = new HashMap();
            map.put("msg", "登录失败");
            return map;
        }
    }
}
