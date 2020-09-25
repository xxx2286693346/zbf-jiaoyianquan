package com.ycl.role.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 袁成龙
 * @since 2020-09-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("base_role")
public class BaseRole implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 这是角色表
     */
    @TableId("id")
    private Long id;

    /**
     * 角色编码
     */
    @TableField("role_code")
    private String roleCode;

    /**
     * 角色表
     */
    @TableField("name")
    private String name;

    /**
     * 描述
     */
    @TableField("miaoshu")
    private String miaoshu;


}
