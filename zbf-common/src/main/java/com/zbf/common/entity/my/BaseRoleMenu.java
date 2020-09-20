package com.zbf.common.entity.my;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 袁成龙
 * @since 2020-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("base_role_menu")
public class BaseRoleMenu implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 这是角色菜单关系表
     */
    @TableId("id")
    private Long id;

    @TableField("version")
    private Integer version;

    @TableField("role_id")
    private Long roleId;

    /**
     * 角色菜单表
     */
    @TableField("menu_id")
    private Long menuId;


}
