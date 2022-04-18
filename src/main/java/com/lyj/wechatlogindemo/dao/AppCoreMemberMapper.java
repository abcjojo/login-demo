package com.lyj.wechatlogindemo.dao;

import com.lyj.wechatlogindemo.dto.ResponseMemberDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface AppCoreMemberMapper {

    ResponseMemberDTO getMemberInfoByOpenid(String openid);
}
