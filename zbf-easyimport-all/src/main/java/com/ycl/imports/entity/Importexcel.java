package com.ycl.imports.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author 袁成龙
 * @since 2020-09-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("importexcel")
public class Importexcel implements Serializable {

    private static final long serialVersionUID=1L;

    private Long id;

    private String version;

    @TableField("userName")
    private String userName;

    @TableField("loginName")
    private String loginName;

    private Integer gender;

    private String bumen;

    private String tel;

    private String password;

    @TableField("createTime")
    private Date createTime;

    private String url;

    private String pic;


}
