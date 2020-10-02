package com.ycl.job.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ycl.job.mapper.BaseJobTypeMapper;
import com.ycl.job.service.IBaseExportTaskDetailService;
import com.ycl.job.service.IBaseExportTaskHeaderService;
import com.ycl.job.service.IBaseJobTypeService;
import com.ycl.util.StringUtil;
import com.zbf.common.entity.ResponseResult;
import com.zbf.common.entity.my.BaseExportTaskDetail;
import com.zbf.common.entity.my.BaseExportTaskHeader;
import com.zbf.common.entity.my.BaseJobType;
//import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
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
@RequestMapping("/baseJobType")
public class BaseJobTypeController {


    @Autowired
    private IBaseJobTypeService iBaseJobTypeService;

    @Autowired
    private IBaseExportTaskDetailService detailService;

    @Autowired
    private IBaseExportTaskHeaderService headerService;

    @Autowired
    private BaseJobTypeMapper typeMapper;

    ResponseResult result = new ResponseResult();

    /**
     * @Author 袁成龙
     * @Description //TODO * 导出任务创建列表
    * @param miao
     * @Date 15:07 2020/9/29
     * @Param 
     * @return 
     **/
    @RequestMapping("/list")
    public ResponseResult result(String lei,String miao){
        QueryWrapper queryWrapper = new QueryWrapper();
        if(lei!=null&&!"".equals(lei)){
            queryWrapper.eq("job_class",lei);
        }
        if(miao!=null&&!"".equals(miao)){
            queryWrapper.like("job_mark",miao);
        }
        List<BaseJobType> list = iBaseJobTypeService.list(queryWrapper);
        result.setResult(list);
        return result;
    }


    /**
     * @Author 袁成龙
     * @Description //TODO 添加任务
     * @Date 15:07 2020/9/29
     * @Param 
     * @return 
     **/
    @RequestMapping("/addjob")
    public ResponseResult addJob(@RequestBody BaseJobType jobType){
        boolean save = iBaseJobTypeService.save(jobType);
        if(save){
            result.setCode(1006);
        }
        return result;
    }



    /**
     * @Author 袁成龙
     * @Description //TODO 导出任务时添加任务头表
     * @Date 15:08 2020/9/29
     * @Param 
     * @return 
     **/
    @RequestMapping("/addHeader")
    public ResponseResult addHeader(@RequestBody BaseJobType jobType){
        boolean save = iBaseJobTypeService.save(jobType);
        if(save){
            result.setCode(1006);
        }
        return result;
    }




    @RequestMapping("/export")
    public ResponseResult exportExcel(@RequestBody Map<String,String> map){
        System.out.println(map);
        int userName = typeMapper.getuserId(map.get("userName"));
        BaseExportTaskHeader taskHeader = new BaseExportTaskHeader();
        taskHeader.setCreateTime(LocalDateTime.now());
        taskHeader.setTaskName(map.get("taskName"));
        taskHeader.setTaskSql(map.get("sql"));
        taskHeader.setTaskStatus(0);
        taskHeader.setVersion(0);
        taskHeader.setTaskTailOkNum(0);
        taskHeader.setTaskTailNum(4);
        taskHeader.setUserId((long)userName);
        boolean save = headerService.save(taskHeader);
        if(save){
            BaseExportTaskDetail taskDetail = new BaseExportTaskDetail();
            String s = StringUtil.randomChineseString();
            int getheaderid = typeMapper.getheaderid();
            int count = typeMapper.getcountnum(map.get("count"));
            System.out.println("==========="+count);
            int i1 = count / 4;
            int star = 0;
            int end = i1;
            int end1=i1;
            int sta = 0;
            boolean save1 =false;
            for (int i=0;i<4;i++){
                if(i>0){
                    star=end*i+i;
                    end=i1;
                    if(i==3){
                        star=end*i+i;
                        end=count;
                    }
                }
                String str = map.get("sql")+" LIMIT "+star+","+end;
                taskDetail.setShardId(i);
                taskDetail.setTaskHeaderId(getheaderid);
                taskDetail.setTaskSql(str);
                taskDetail.setFileName(s+"_"+i);
                save1 = detailService.save(taskDetail);
                //if(i==2){

                    if(i==2){
                        sta=star=end*(i+1)+i+1;
                    }


              //  }
            }
            //任务头的id
            if(save1){
                typeMapper.updateheaderstatus(getheaderid);
            }
        }
        System.out.println(userName);
        return null;
    }







}

