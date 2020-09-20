package com.ycl.deupload.controller;


import com.zbf.common.entity.ResponseResult;
import com.zbf.common.entity.my.BaseUser;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 袁成龙
 * @since 2020-09-11
 */
@RestController
public class DocController {

    ResponseResult responseResult = new ResponseResult();

    @RequestMapping("getDoc")
    public ResponseResult getDoc(@RequestBody BaseUser user) throws IOException, TemplateException {
        try {
            Map<String,String> dataMap = new HashMap<String,String>();
            if(user.getId() != null ){
                dataMap.put("id", String.valueOf(user.getId()));
            }else{
                dataMap.put("id", "该用户没有id");
            }
            if(user.getUserName() != null){
                dataMap.put("userName", user.getUserName());
            }else{
                dataMap.put("userName","该用户没有姓名");
            }
            if(user.getLoginName() != null){
                dataMap.put("loginName", user.getLoginName());
            }else{
                dataMap.put("loginName","该用户没有登录姓名");
            }
            if(user.getTel() != null ){
                dataMap.put("tel", user.getTel());
            }else{
                dataMap.put("tel","该用户没有手机号");
            }
            if(user.getEmail()!= null ){
                dataMap.put("email", user.getEmail());
            }else{
                dataMap.put("email","该用户没有邮箱");
            }
            if(user.getStatus()!= null ){
                dataMap.put("status", String.valueOf(user.getStatus()));
            }else{
                dataMap.put("status","该用户没有激活状态");
            }
            if(user.getCreateTime()!= null ){
                dataMap.put("createTime", String.valueOf(user.getCreateTime()));
            }else{
                dataMap.put("createTime","该用户没有创建时间");
            }
           /* if(user.getRnames() != null){
                dataMap.put("rnames", user.getRnames());
            }else{
                dataMap.put("rnames","该用户没有所属角色");
            }
*/

            Configuration configuration = new Configuration();
            configuration.setDefaultEncoding("utf-8");
            //指定模板路径的第二种方式,我的路径是D:/      还有其他方式
            configuration.setDirectoryForTemplateLoading(new File("I:/getDoc"));


            // 输出文档路径及名称
            File outFile = new File("I:/getDoc/"+user.getUserName()+".doc");
            //以utf-8的编码读取ftl文件
            Template t =  configuration.getTemplate("docxml.xml","utf-8");
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"),10240);
            t.process(dataMap, out);
            out.close();
            responseResult.setCode(1006);
            return responseResult;
        }catch (Exception e){
            e.printStackTrace();
        }
        return responseResult;

    }






}

