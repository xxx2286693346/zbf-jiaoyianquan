package com.ycl.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zbf.common.entity.my.BaseJobType;
import com.zbf.common.entity.my.BaseUser;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 袁成龙
 * @since 2020-09-28
 */
public interface BaseJobTypeMapper extends BaseMapper<BaseJobType> {

    @Select("select * from base_user where loginName=#{loginName}")
    int getuserId(String loginName);

    @Select("${sql}")
    int getcountnum(String sql);

    @Select("select max(id) from base_export_task_header")
    int getheaderid();

    @Select("${sql} LIMIT ${sta},${end}")
    List<BaseUser> listlimit(String sql,Integer sta,Integer end);

    @Update({"update base_export_task_header set task_status=1 where id =#{id}"})
    boolean updateheaderstatus(Integer id);

}
