package es.controller;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.zbf.common.entity.my.BaseUser;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @description:
 * @projectName:zbf-jiaoyianquan
 * @see:es.controller
 * @author:袁成龙
 * @createTime:2020/9/28 18:56
 * @version:1.0
 */
public class Exportimport {


    @RequestMapping("/expor")
    public String exporExcel(HttpServletResponse response) throws IOException {
        System.out.println("导出成功");
        ExcelWriter writer = null;
        OutputStream outputStream = response.getOutputStream();
        try {

            response.setHeader("Content-disposition", "attachment; filename=" + "catagory.xls");
            response.setContentType("application/msexcel;charset=UTF-8");//设置类型
            response.setHeader("Pragma", "No-cache");//设置头
            response.setHeader("Cache-Control", "no-cache");//设置头
            response.setDateHeader("Expires", 0);//设置日期头

            writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLS, true);


          //  Sheet rows = new Sheet(1, 0, BaseUser.class);
            //rows.setSheetName("目录");

          //  List<BaseUser> catagoryList = excleService.findAll();

         //   writer.write(catagoryList, rows);
            writer.finish();

            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.getOutputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "index";
    }

}