package com.ycl.deupload.controller;

import com.ycl.deupload.entity.BaseUser;
import com.ycl.util.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

/**
 * @description:
 * @projectName:zbf-jiaoyianquan
 * @see:com.ycl.deupload.controller
 * @author:袁成龙
 * @createTime:2020/9/29 15:20
 * @version:1.0
 */
@Component
@RequestMapping("/excelexport")
public class EasyExcel {

    @RequestMapping("/easy")
    public void exporteasyExcel(List<BaseUser> list, String name) throws FileNotFoundException {
        FileOutputStream file = new FileOutputStream("I:/getExec/"+ name +".xlsx");
        // 写法1
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        com.alibaba.excel.EasyExcel.write(file, BaseUser.class).sheet("模板").doWrite(list);
    }
}