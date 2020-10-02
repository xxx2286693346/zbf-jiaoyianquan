package es.dao;

import com.ycl.deupload.entity.BaseUser;
import com.zbf.common.entity.my.BaseExportTaskDetail;
import com.zbf.common.entity.my.BaseExportTaskHeader;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author: LCG
 * 作者: LCG
 * 日期: 2020/9/28  15:50
 * 描述:
 */
@Mapper
@Repository
public interface UserDataMapper {

   /* //2009181132382212
    @Select("select yyy.* from (select @rownum:=@rownum+1 AS rownum,lui.* from (SELECT @rownum:=0) r,lcg_user_import lui) yyy where yyy.rownum >=#{start} and yyy.rownum <=#{end}")
    public List<Map<String,Object>> getBY(Map<String, Object> map);

    //2009181132382212
    @Select("select count(1) from (select @rownum:=@rownum+1 AS rownum,lui.* from (SELECT @rownum:=0) r,lcg_user_import lui where lui.id <=2) yyy where yyy.rownum>=#{starter} and yyy.rownum<= #{end}")
    public Long getDataCount(Map<String, Object> map);*/

    @Select("select max(id) from base_export_task_header")
    int getheaderid();

    @Select("select * from base_export_task_header where id = #{id}")
    BaseExportTaskHeader getid(Integer id);

    @Select("select * from base_export_task_detail where shard_id=#{id} and task_header_id=#{heid}")
    BaseExportTaskDetail getidheid(Integer heid,Integer id);

    @Select("${sql}")
    List<BaseUser> getSqlLimit(String sql);

    @Update("update base_export_task_header set version=version+1,task_tail_ok_num=task_tail_ok_num+1 where id =#{id}")
    int updateHeader(Integer id);


}
