package com.zbf.common.entity.my;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author 袁成龙
 * @since 2020-09-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("base_menu")
public class BaseMenu implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 菜单表
     */
    @TableId("id")
    private Long id;

    @TableField("version")
    private Integer version;

    @TableField("code")
    private Long code;

    //菜单名称
    @TableField("menuName")
    private String menuName;

    @TableField("imagePath")
    private String imagePath;

    //菜单路径
    @TableField("url")
    private String url;

    //父级菜单id
    @TableField("parentCode")
    private Long parentCode;

    //自己的等级
    @TableField("leval")
    private Integer leval;

    @TableField("createTime")
    private LocalDateTime createTime;

    //表示不与数据库所映射
    @TableField(exist = false)
    private List<BaseMenu> baseMenus;

    @TableField(exist = false)
    private String loginName;



}
