package com.zbf.common.entity.my;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2020-09-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("base_job_type")
public class BaseJobType implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * job描述
     */
    @TableField("job_mark")
    private String jobMark;

    /**
     * job类
     */
    @TableField("job_class")
    private String jobClass;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private LocalDateTime createTime;


}
