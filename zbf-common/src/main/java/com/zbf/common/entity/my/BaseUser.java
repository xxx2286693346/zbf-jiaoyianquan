package com.zbf.common.entity.my;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
//import com.google.common.annotations.VisibleForTesting;
import com.zbf.common.entity.enums.SexEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author 袁成龙
 * @since 2020-09-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("base_user")
public class BaseUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户表
     */
    @TableId("id")
    @ExcelProperty("id")
    private Long id;

    @TableField("version")
    @ExcelProperty("版本")
    private Integer version;

    //用户名
    @TableField("userName")
    @ExcelProperty("用户名")
    private String userName;

    //登录名
    @TableField("loginName")
    @ExcelProperty("登录名")
    private String loginName;

    //密码
    @TableField("passWord")
    @ExcelProperty("密码")
    private String passWord;

    @TableField("tel")
    @ExcelProperty("电话")
    private String tel;

    //版本
    @TableField("buMen")
    @ExcelProperty("部门")
    private String buMen;

    @TableField("salt")
    @ExcelProperty("盐")
    private String salt;

    @TableField("createTime")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @TableField("updateTime")
    @ExcelProperty("修改时间")
    private LocalDateTime updateTime;

    //性别
    @TableField("sex")
    @ExcelProperty("性别")
    private SexEnum sex;


    //邮箱
    @TableField("email")
    @ExcelProperty("邮箱")
    private String email;

    @TableField("status")
    @ExcelProperty("激活状态")
    private Integer status;

    @TableField("image")
    @ExcelProperty("图片")
    private String image;

    @TableField(exist = false)
    @ExcelIgnore
    private String rname;

    public BaseUser(Long id, String userName, String loginName, String passWord, String tel, SexEnum sex, String email, String salt,Integer status,LocalDateTime createTime) {
        this.id = id;
        this.userName = userName;
        this.loginName = loginName;
        this.passWord = passWord;
        this.tel = tel;
        this.sex = sex;
        this.email = email;
        this.salt = salt;
        this.status=status;
        this.createTime=createTime;
    }




    public BaseUser(Long id, String userName, String loginName, String passWord, String tel, SexEnum sex, String email, String salt,Integer status,String image,LocalDateTime createTime) {
        this.id = id;
        this.userName = userName;
        this.loginName = loginName;
        this.passWord = passWord;
        this.tel = tel;
        this.sex = sex;
        this.email = email;
        this.salt = salt;
        this.status=status;
        this.image=image;
        this.createTime=createTime;
    }






    public BaseUser(String passWord, String salt) {
        this.passWord = passWord;
        this.salt = salt;
    }

    public BaseUser(Integer status) {
        this.status = status;
    }

    public BaseUser(Long id, Integer version, String userName, String loginName, String passWord, String tel, String buMen, String salt, LocalDateTime createTime, LocalDateTime updateTime, SexEnum sex, String email, Integer status, String image, String rname) {
        this.id = id;
        this.version = version;
        this.userName = userName;
        this.loginName = loginName;
        this.passWord = passWord;
        this.tel = tel;
        this.buMen = buMen;
        this.salt = salt;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.sex = sex;
        this.email = email;
        this.status = status;
        this.image = image;
        this.rname = rname;
    }

    public BaseUser() {
    }
}
