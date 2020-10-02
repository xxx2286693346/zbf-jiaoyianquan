package com.ycl.job.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycl.job.service.IBaseExportTaskHeaderService;
import com.zbf.common.entity.ResponseResult;
import com.zbf.common.entity.my.BaseExportTaskHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 袁成龙
 * @since 2020-09-28
 */
@RestController
@RequestMapping("/baseExportTaskHeader")
public class BaseExportTaskHeaderController {

    @Autowired
    private IBaseExportTaskHeaderService taskHeaderService;

    ResponseResult responseResult = new ResponseResult();

    @RequestMapping("/list")
     public ResponseResult listpage(BaseExportTaskHeader baseExportTaskHeader, Page page){
         QueryWrapper<BaseExportTaskHeader> queryWrapper = new QueryWrapper();
         if (baseExportTaskHeader.getTaskName()!=null){
             queryWrapper.like("task_name",baseExportTaskHeader.getTaskName());
         }
         if(baseExportTaskHeader.getCreateTime()!=null){
             queryWrapper.gt("createTime",baseExportTaskHeader.getCreateTime());
         }
         if(baseExportTaskHeader.getTaskStatus()!=null){
             queryWrapper.eq("task_status",baseExportTaskHeader.getTaskStatus());
         }
         IPage page1 = taskHeaderService.page(page, queryWrapper);
         responseResult.setResult(page1);
         return responseResult;
     }


}

