package com.zbf.common.entity;

/**
 * @description:
 * @projectName:zbf-jiaoyianquan
 * @see:com.zbf.common.entity
 * @author:袁成龙
 * @createTime:2020/9/13 19:52
 * @version:1.0
 */
public enum  AllRedisKey {

    ACTIVE_KEY("active","激活路径的key"),
    ALL_MENU_KEY("menuRole","系统所有权限的key"),
    USER_AUTH_KEY("user-auth","用户权限key"),
    USRE_INFO_KEY("user-info","用户信息的key"),
    CODES("codes","验证码的key"),
    USER_MENU_KEY("user-menu","菜单的key"),
    MENU_ALL("menuall","菜单列表的key");;


    private String code;

    private String key;

    AllRedisKey(String key, String code) {
        this.key = key;
        this.code = code;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}