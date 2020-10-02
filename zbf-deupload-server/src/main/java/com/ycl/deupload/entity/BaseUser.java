package com.ycl.deupload.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ycl.deupload.config.LocalDateTimeConverter;
import com.zbf.common.entity.enums.SexEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import com.alibaba.excel.converters.Converter;

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
@TableName("base_user_export")
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
    @ExcelProperty(value = "创建时间",converter = LocalDateTimeConverter.class)
    private LocalDateTime createTime;


    //性别
    @TableField("url")
    @ExcelProperty("性别")
    private String url;
}
