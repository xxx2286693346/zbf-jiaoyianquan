package com.zbf.user.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycl.util.RandomUtil;
import com.ycl.util.StringUtil;
import com.zbf.common.entity.AllRedisKey;
import com.zbf.common.entity.ResponseResult;
import com.zbf.common.entity.ResponseResultEnum;
import com.zbf.common.entity.enums.SexEnum;
import com.zbf.common.entity.my.BaseUser;
import com.zbf.common.utils.*;
import com.zbf.user.mapper.BaseUserMapper;
import com.zbf.user.mapper.FeignMapper;
import com.zbf.user.service.IBaseUserService;
import com.zbf.user.service.ServiceYan;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 袁成龙
 * @since 2020-09-11
 */
@RestController
public class BaseUserController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Value("${oK.path}")
    private String actipaht;

    @Autowired
    ServiceYan baseUser;

    @Autowired
    IBaseUserService iBaseUserService;

    @Autowired
    FeignMapper feignMapper;


    //返回值的总类
    ResponseResult responseResult = new ResponseResult();

    //验证是否为手机号
    private static final Pattern PATTERN_PHONE = Pattern.compile("^-?\\d+(\\.\\d+)?$");


    /**
     * @param code
     * @return
     * @Author 袁成龙
     * @Description //TODO * 登录验证与redis作对比
     * @Date 8:26 2020/9/16
     * @Param tel:带过来的手机号,code带过来的验证码
     **/
    @RequestMapping("/register")
    public ResponseResult test01(String tel, String code) {
        boolean b = StringUtil.gitPhone(tel);
        if (b) {
            System.out.println("tel==" + tel + "code==" + code);
            String s = redisTemplate.opsForValue().get("codes");
            String str = "1234";
            if ("1234" != null) {
                // System.out.println("登录的验证"+s);
                if ("1234".equals(code)) {
                    responseResult.setCode(1006);
                    responseResult.setSuccess("2233");
                    redisTemplate.delete("codes");
                    return responseResult;
                } else {
                    responseResult.setCode(1004);
                    return responseResult;
                }
            } else {
                responseResult.setCode(1004);
                return responseResult;
            }
        } else {
            responseResult.setCode(1004);
            return responseResult;
        }


    }


    /**
     * @return
     * @Author 袁成龙
     * @Description //TODO 注册的时候验证操作有无手机号重复等
     * @Date 9:12 2020/9/23
     * @Param phone:传来的手机号
     **/
    @RequestMapping("/Yan")
    public ResponseResult yanzheng(String phone) {
        if (!StringUtil.gitPhone(phone)) {
            responseResult.setCode(1003);
            return responseResult;
        }
        System.out.println(phone);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("tel", phone);
        BaseUser one = iBaseUserService.getOne(queryWrapper);
        System.out.println("===" + one);
        if (one == null) {
            boolean loginyan = baseUser.loginyan(phone);
            System.out.println(loginyan);
            if (loginyan) {
                responseResult.setCode(1006);
                return responseResult;
            } else {
                responseResult.setCode(1005);
                return responseResult;
            }
        } else {
            responseResult.setCode(1004);
            return responseResult;
        }
    }


    /**
     * @return
     * @Author 袁成龙
     * @Description //TODO 修改密码的时候验证主要验证有没有相同的手机号等操作,发送邮箱
     * @Date 9:12 2020/9/23
     * @Param phone也是代表是手机号也可以是邮箱
     **/
    @RequestMapping("/UpdateYan")
    public ResponseResult UpdateYan(String phone) {
        if (StringUtil.gitEmail(phone)) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("email", phone);
            BaseUser one = iBaseUserService.getOne(queryWrapper);
            if (one != null) {
                System.out.println("======" + phone);
                String s = RandomUtil.randomNumber(4);
                System.out.println(s);
                redisTemplate.opsForValue().set("codes", s);
                MailQQUtils.sendMessage(phone, s, "湖人招募", "");
                responseResult.setCode(1006);
                return responseResult;
            }
            responseResult.setCode(1004);
            return responseResult;
        } else if (isPhone(phone)) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("tel", phone);
            BaseUser one = iBaseUserService.getOne(queryWrapper);
            System.out.println(one);
            if (one != null) {
                System.out.println(phone);
                System.out.println("++++++++++++++++=");
                boolean b = baseUser.loginyan(phone);
                responseResult.setCode(1006);
                return responseResult;
            } else {
                responseResult.setCode(1004);
                return responseResult;
            }
        } else {
            responseResult.setCode(1006);
            return responseResult;
        }
    }


    /**
     * @param code
     * @return
     * @Author 袁成龙
     * @Description //TODO 登录的时候和redis进行对比看看是否输入有误
     * @Date 14:35 2020/9/18
     * @Param
     **/
    @RequestMapping("/BiDui")
    public ResponseResult BiDui(String tel, String code) {
        System.out.println("tel==" + tel + "code==" + code);
        String s = redisTemplate.opsForValue().get("codes");
        System.out.println("登录的验证" + s);
        if (s == null) {
            s = "1";
        }
        if (s.equals(code)) {
            responseResult.setCode(1006);
            responseResult.setSuccess(s);
            redisTemplate.delete("codes");
            return responseResult;
        } else {
            responseResult.setCode(1004);
            return responseResult;
        }
    }


    /**
     * @return
     * @Author 袁成龙
     * @Description //TODO 枚举类获得一个数组分别为sex{1,2}
     * @Date 14:31 2020/9/18
     * @Param
     **/
    @RequestMapping("/Sex")
    public ResponseResult sexlist() {
        System.out.println("==================");
        SexEnum[] values = SexEnum.values();
        responseResult.setResult(values);
        return responseResult;
    }


    /**
     * @param phone
     * @return
     * @Author 袁成龙
     * @Description //TODO 执行了用户的修改方法
     * @Date 14:32 2020/9/18
     * @Param passWord 收到的新的密码  phone手机号根据手机号或者邮箱修改密码
     **/
    @RequestMapping("/Update")
    public ResponseResult upda(String passWord, String phone) {
        System.out.println("pass" + passWord + "ph" + phone);
        String s = RandomUtil.randomNumber(4);
        String encodePass = Md5.encode(passWord + s, "MD5");
        QueryWrapper queryWrapper = new QueryWrapper();
        if (StringUtil.gitEmail(phone)) {
            queryWrapper.eq("email", phone);
        }
        if (isPhone(phone)) {
            queryWrapper.eq("tel", phone);
        }
        BaseUser baseUser1 = new BaseUser(encodePass, s);
        boolean b = iBaseUserService.saveOrUpdate(baseUser1, queryWrapper);
        System.out.println(b);
        if (b) {
            responseResult.setCode(1006);
            return responseResult;
        }else{
            responseResult.setCode(1004);
            return responseResult;
        }

    }

    public boolean isPhone(String phone) {
        return PATTERN_PHONE.matcher(phone).matches();
    }

    /*
     * @Author 袁成龙
     * @Description //TODO 用户信息的查询
     * @Date 8:42 2020/9/18
     * @Param
     * @return
     **/
    @RequestMapping("listuser")
    public ResponseResult iPage(Page page, BaseUser vo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("id");
        IPage<BaseUser> listpage = iBaseUserService.listpage(page, vo);
        responseResult.setResult(listpage);
        return responseResult;
    }


    /**
     * @return
     * @Author 袁成龙
     * @Description //TODO 根据用户的登录名查询所得的角色(登录名唯一)
     * @Date 14:26 2020/9/18
     * @Param
     **/
    @RequestMapping("findloginName")
    public ResponseResult baseUser(String loginName) {
        BaseUser user = iBaseUserService.finloginName(loginName);
        System.out.println(user);
        if (user != null) {
            System.out.println(user);
            responseResult.setResult(user);
            responseResult.setCode(1006);
        } else {
            System.out.println(user);
            responseResult.setCode(1004);
        }
        return responseResult;
    }


    /**
     * @return
     * @Author 袁成龙
     * @Description //TODO 激活冻结
     * @Date 18:39 2020/9/18
     * @Param
     */
    @RequestMapping("updateStatus")
    public ResponseResult result(Integer status, String id) {
        boolean updatestatus = iBaseUserService.updatestatus(status, Long.valueOf(id).longValue());
        System.out.println(updatestatus);
        if (updatestatus) {
            responseResult.setCode(ResponseResultEnum.SECCESS.getCode());
            return responseResult;
        } else {
            responseResult.setCode(ResponseResultEnum.FAILURE.getCode());
            return responseResult;
        }
    }


    /**
     * @param pai
     * @param idcreatetime
     * @param checklist
     * @return
     * @Author 袁成龙
     * @Description //TODO * 实现exec的导出调用了fegin的方法
     * @Date 2020/9/22
     * @Param
     **/
    @RequestMapping("/execlist")
    public ResponseResult responseResult(Integer num, String pai, String idcreatetime, String checklist) {
        String str = "";
        String str1 = "";
        List<BaseUser> getlist = null;
        System.out.println("aa=" + num + "bb=" + pai + "cc=" + idcreatetime + "dd=" + checklist);
        String[] split = checklist.split("=");
        for (int i = 0; i < split.length; i++) {
            if (i % 2 == 0) {
                str += split[i] + ",";
            }
            if (i % 2 == 1) {
                str1 += split[i] + ",";
            }
        }
        if (str != null && !str.equals("") && str1 != null && !str1.equals("")) {
            if (str.contains("rname")) {
                String ll = "";
                String[] split1 = str.split(",");
                for (int i = 0; i < split1.length; i++) {
                    if (split1[i].equals("rname")) {
                        System.out.println("包含了");
                    } else {
                        ll += split1[i] + ",";
                    }

                }
                System.out.println(ll);
                getlist = iBaseUserService.getlist(pai, idcreatetime, num, ll, "rname");
            } else {
                int length = str.length();
                String substring = str.substring(0, length - 1);
                getlist = iBaseUserService.getlist(pai, idcreatetime, num, substring, null);
            }
            boolean getname = feignMapper.getname(str, str1);
            System.out.println(getname);
            if (getname) {
                boolean getlistexec = feignMapper.getlistexec(getlist);
                if (getlistexec) {
                    responseResult.setCode(1006);
                }
            }
        } else {
            getlist = iBaseUserService.list();
            boolean getlistexec = feignMapper.getlistexec(getlist);
            if (getlistexec) {
                responseResult.setCode(1006);
            }
        }


        return responseResult;

    }


    /**
     * @return
     * @Author 袁成龙
     * @Description //TODO exec的导入实现批量添加
     * @Date 21:14 2020/9/22
     * @Param
     **/
    @RequestMapping("execimport")
    public ResponseResult add(MultipartFile file) {
        //对应excel模板内容总行数
        int excelTemplateRowNum = 2;
        //以excel模板某行作为JSON对象键
        int jsonRowNum = 1;
        JSONArray array = new JSONArray();
        try {
            array = new ParseExcelToJSONArrayUtil().parseExcelFile(file, excelTemplateRowNum, jsonRowNum);
            System.out.println(array);
            List<BaseUser> list = JSON.parseArray(array.toString(), BaseUser.class);
            boolean b = iBaseUserService.saveBatch(list);
            if (b) {
                responseResult.setCode(1006);
            }
        } catch (Exception e) {

        }
        return responseResult;
    }


}

