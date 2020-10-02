package com.zbf.common.entity.my;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

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
@TableName("base_export_task_header")
public class BaseExportTaskHeader implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("version")
    private Integer version;

    @TableField("user_id")
    private Long userId;

    @TableField("task_name")
    private String taskName;

    @TableField("task_status")
    private Integer taskStatus;

    @TableField("task_tail_num")
    private Integer taskTailNum;

    @TableField("task_tail_ok_num")
    private Integer taskTailOkNum;

    @TableField("task_sql")
    private String taskSql;

    @TableField("createTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


}
