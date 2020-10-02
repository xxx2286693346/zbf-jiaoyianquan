package com.zbf.common.entity.my;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2020-09-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("base_export_task_detail")
public class BaseExportTaskDetail implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("shard_id")
    private Integer shardId;

    @TableField("task_header_id")
    private Integer taskHeaderId;

    @TableField("task_sql")
    private String taskSql;

    @TableField("file_name")
    private String fileName;

    @TableField("file_path")
    private String filePath;


}
