package es.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.ycl.deupload.controller.EasyExcel;
import com.ycl.deupload.entity.BaseUser;
import com.zbf.common.entity.my.BaseExportTaskDetail;
import com.zbf.common.entity.my.BaseExportTaskHeader;
import com.zbf.common.entity.my.BaseJobType;
import es.dao.EasyExcelMapper;
import es.dao.UserDataMapper;
import lombok.SneakyThrows;
//import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * 作者: LCG
 * 日期: 2020/7/28 09:06
 * 描述:
 */
@Component
public class MyTask01 implements SimpleJob {

    @Value("${server.port}")
    private String port;

    @Autowired
    private UserDataMapper userDataMapper;

    /*@Autowired
    private EasyExcelMapper easyExcel;*/

    @SneakyThrows
    @Override
    public void execute(ShardingContext shardingContext) {
       // System.out.println("==========将来大家写自己的业务==========");

        //获取分片的总个数
        int shardingTotalCount = shardingContext.getShardingTotalCount();

        //当前运行的分片的下标
        int shardingItem = shardingContext.getShardingItem();

        EasyExcel easyExcel = new EasyExcel();


        int getheaderid = userDataMapper.getheaderid();
        if(getheaderid>0){
            BaseExportTaskHeader getid1 = userDataMapper.getid(getheaderid);
            System.out.println(getid1);
            if(getid1.getTaskStatus()==1){
                for (int i=0;i<getid1.getTaskTailNum();i++){
                    if(i==shardingItem){
                        System.out.println(port+"==============0-3000"+"--->"+    shardingContext.getShardingParameter());
                        Integer version = getid1.getVersion();
                        long start=System.currentTimeMillis();
                        try {
                                BaseExportTaskDetail getidheid = userDataMapper.getidheid(getheaderid, i);
                                String taskSql = getidheid.getTaskSql();
                                List<BaseUser> sqlLimit = userDataMapper.getSqlLimit(taskSql);
                                easyExcel.exporteasyExcel(sqlLimit,getidheid.getFileName());


                                Integer version1 = getid1.getVersion();
                                if(version==version1){
                                    userDataMapper.updateHeader(getheaderid);
                                }else{
                                    userDataMapper.updateHeader(getheaderid);
                                }
                        } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        long end=System.currentTimeMillis();
                        System.out.println("0=========="+(end-start)/1000.0);
                        break;
                    }else if(i==shardingItem){
                        System.out.println(port+"==============3001-6000");
                        Integer version = getid1.getVersion();
                        long start=System.currentTimeMillis();
                        try {
                            BaseExportTaskDetail getidheid = userDataMapper.getidheid(getheaderid, i);
                            String taskSql = getidheid.getTaskSql();
                            List<BaseUser> sqlLimit = userDataMapper.getSqlLimit(taskSql);
                            easyExcel.exporteasyExcel(sqlLimit,getidheid.getFileName());


                            Integer version1 = getid1.getVersion();
                            if(version==version1){
                                userDataMapper.updateHeader(getheaderid);
                            }else{
                                userDataMapper.updateHeader(getheaderid);
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        long end=System.currentTimeMillis();
                        System.out.println("0=========="+(end-start)/1000.0);
                        break;
                    }else if(i==shardingItem){
                        System.out.println(port+"==============6001-10000");
                        Integer version = getid1.getVersion();
                        long start=System.currentTimeMillis();
                        try {
                            BaseExportTaskDetail getidheid = userDataMapper.getidheid(getheaderid, i);
                            String taskSql = getidheid.getTaskSql();
                            List<BaseUser> sqlLimit = userDataMapper.getSqlLimit(taskSql);
                            easyExcel.exporteasyExcel(sqlLimit,getidheid.getFileName());


                            Integer version1 = getid1.getVersion();
                            if(version==version1){
                                userDataMapper.updateHeader(getheaderid);
                            }else{
                                userDataMapper.updateHeader(getheaderid);
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        long end=System.currentTimeMillis();
                        System.out.println("0=========="+(end-start)/1000.0);
                        break;
                    }else if(i==shardingItem){
                        System.out.println(port+"==============6001-10000");
                        Integer version = getid1.getVersion();
                        long start=System.currentTimeMillis();
                        try {
                            BaseExportTaskDetail getidheid = userDataMapper.getidheid(getheaderid, i);
                            String taskSql = getidheid.getTaskSql();
                            List<BaseUser> sqlLimit = userDataMapper.getSqlLimit(taskSql);
                            easyExcel.exporteasyExcel(sqlLimit,getidheid.getFileName());


                            Integer version1 = getid1.getVersion();
                            if(version==version1){
                                userDataMapper.updateHeader(getheaderid);
                            }else{
                                userDataMapper.updateHeader(getheaderid);
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        long end=System.currentTimeMillis();
                        System.out.println("0=========="+(end-start)/1000.0);
                        break;
                    }
                }
            }else{
                System.out.println("请先去前台执行");
            }
        }

    }
}
