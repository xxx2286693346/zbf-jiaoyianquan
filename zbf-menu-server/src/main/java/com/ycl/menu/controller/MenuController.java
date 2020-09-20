package com.ycl.menu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ycl.menu.service.IBaseMenuService;
import com.ycl.menu.service.IBaseRoleMenuService;
import com.zbf.common.entity.AllRedisKey;
import com.zbf.common.entity.ResponseResult;
import com.zbf.common.entity.ResponseResultEnum;
import com.zbf.common.entity.my.BaseMenu;
import com.zbf.common.exception.AllStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @Author 张鑫
 * @Description //TODO 18:59
 * @Date 2020/9/16
 * @Param
 * @return
 **/
@RestController
@RequestMapping("/baseMenu")
public class MenuController {


    @Autowired
    private BaseMenuController menuService;

    @Autowired
    private IBaseMenuService iBaseMenuService;

    @Autowired
    private IBaseRoleMenuService iBaseRoleMenuService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    String loginname="";

    ResponseResult responseResult = new ResponseResult();


    /**
     * @return 返回的是自己封装的一个类, 用来返回所有的数据
     * @Author 袁成龙
     * @Description //TODO 18:59
     * @Date 2020/9/16
     * @Param 登录名
     **/
    @RequestMapping("/list")
    public ResponseResult getAuthList(String loginName) {
        this.loginname=loginName;
        ResponseResult responseResult = ResponseResult.getResponseResult();
        //从redis缓存中读取menu列表
        String authMenuId = AllRedisKey.USER_MENU_KEY.getKey() + loginName;
        String menuList = redisTemplate.opsForValue().get(authMenuId);
        //判断是否为空如果为空存到redis以方便下次不用再到数据库查询
        if (menuList == null || menuList.equals("")) {
            System.out.println("+++++++++++===");
            List<BaseMenu> findall = menuService.findall(loginName);
            redisTemplate.opsForValue().set(authMenuId, JSON.toJSONString(findall));
            responseResult.setCode(AllStatusEnum.LOGIN_SUCCESS.getCode());
            responseResult.setMsg(AllStatusEnum.LOGIN_SUCCESS.getMsg());
            responseResult.setResult(findall);
        } else {
            System.out.println("=====");
            //如果redis没有  查询
            JSONArray jsonArray = JSON.parseArray(menuList);
            responseResult.setCode(AllStatusEnum.LOGIN_SUCCESS.getCode());
            responseResult.setMsg(AllStatusEnum.LOGIN_SUCCESS.getMsg());
            responseResult.setResult(jsonArray);
        }
        return responseResult;
    }






    /**
     * @return 返回的是自己封装的一个类, 用来返回所有的数据
     * @Author 袁成龙
     * @Description //TODO 18:59
     * @Date 2020/9/16
     * @Param 登录名
     **/
    @RequestMapping("/listfind")
    public ResponseResult getAuthListDan() {
        System.out.println("======================");
        ResponseResult responseResult = ResponseResult.getResponseResult();
        //从redis缓存中读取menu列表
        String authMenuId = AllRedisKey.MENU_ALL.getKey();
        System.out.println("redis的值"+authMenuId);
        String menuList = redisTemplate.opsForValue().get(authMenuId);
        System.out.println(menuList);
        //判断是否为空如果为空存到redis以方便下次不用再到数据库查询
        if (menuList == null || menuList.equals("")) {
            System.out.println("+++++++++++===");
            List<BaseMenu> findall = menuService.findalldan();
            redisTemplate.opsForValue().set(authMenuId, JSON.toJSONString(findall));
            responseResult.setCode(AllStatusEnum.LOGIN_SUCCESS.getCode());
            responseResult.setMsg(AllStatusEnum.LOGIN_SUCCESS.getMsg());
            responseResult.setResult(findall);
        } else {
            System.out.println("=====");
            //如果redis没有  查询
            JSONArray jsonArray = JSON.parseArray(menuList);
            responseResult.setCode(AllStatusEnum.LOGIN_SUCCESS.getCode());
            responseResult.setMsg(AllStatusEnum.LOGIN_SUCCESS.getMsg());
            responseResult.setResult(jsonArray);
        }
        return responseResult;
    }




    @RequestMapping("find")
    public List<BaseMenu> baseMenus(){
        List<BaseMenu> list = iBaseMenuService.list();
        return list;
    }



    @RequestMapping("update")
    public ResponseResult  UpdateMenu(@RequestBody BaseMenu baseMenu){
        if(baseMenu.getId()!=null){
            boolean b = iBaseMenuService.saveOrUpdate(baseMenu);
            if(b){
                responseResult.setSuccess(ResponseResultEnum.SECCESS.getMessage());
                responseResult.setCode(ResponseResultEnum.SECCESS.getCode());
                redisTemplate.delete(AllRedisKey.MENU_ALL.getKey());
                return responseResult;
            }else{
                responseResult.setSuccess(ResponseResultEnum.FAILURE.getMessage());
                responseResult.setCode(ResponseResultEnum.FAILURE.getCode());
                return responseResult;
            }
        }else{
            BaseMenu baseMenu1 = new BaseMenu();
            //baseMenu1.setCreateTime();
            baseMenu1.setMenuName(baseMenu.getMenuName());
            baseMenu1.setUrl(baseMenu.getUrl());
            baseMenu1.setLeval(baseMenu.getLeval());
            if(baseMenu.getLeval()==1){
                System.out.println("++++_++_+_*)&*(T&%^");
                baseMenu1.setParentCode(Long.valueOf("0").longValue());
            }else{
                baseMenu1.setParentCode(baseMenu.getParentCode());
            }

            LocalDateTime now = LocalDateTime.now();
            baseMenu1.setId(Long.valueOf(this.time()).longValue());
            baseMenu1.setCode(Long.valueOf(this.time()).longValue());
            System.out.println(now);
            baseMenu1.setCreateTime(now);
            System.out.println(baseMenu1);
            boolean b = iBaseMenuService.saveOrUpdate(baseMenu1);
            if(b){
                responseResult.setSuccess(ResponseResultEnum.SECCESS.getMessage());
                responseResult.setCode(ResponseResultEnum.SECCESS.getCode());
                redisTemplate.delete(AllRedisKey.MENU_ALL.getKey());
                return responseResult;
            }else{
                responseResult.setSuccess(ResponseResultEnum.FAILURE.getMessage());
                responseResult.setCode(ResponseResultEnum.FAILURE.getCode());
                return responseResult;
            }
        }

        }



    @RequestMapping("delete")
    public ResponseResult  delete(Long id,Long code) {
        System.out.println(id+"---"+code);
        boolean remove = iBaseMenuService.removeById(id);
        if(remove) {
            ResponseResult digui = this.digui(id,code);
            return digui;
        }
        responseResult.setSuccess(ResponseResultEnum.FAILURE.getMessage());
        responseResult.setCode(ResponseResultEnum.FAILURE.getCode());
        redisTemplate.delete(AllRedisKey.MENU_ALL.getKey());
        return responseResult;
    }


    public ResponseResult  digui(Long id,Long code){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("parentCode",code);
        List<BaseMenu> list = iBaseMenuService.list(queryWrapper);
        if(list!=null&&list.size()>0){
            for (BaseMenu me : list) {
                QueryWrapper queryWrapper1 = new QueryWrapper();
                queryWrapper1.eq("id",me.getId());
                boolean remove = iBaseMenuService.remove(queryWrapper1);
                this.digui(me.getId(),me.getCode());
            }

            responseResult.setSuccess(ResponseResultEnum.SECCESS.getMessage());
            responseResult.setCode(ResponseResultEnum.SECCESS.getCode());
            redisTemplate.delete(AllRedisKey.MENU_ALL.getKey());
            return responseResult;
        }else{
            responseResult.setSuccess(ResponseResultEnum.SECCESS.getMessage());
            responseResult.setCode(ResponseResultEnum.SECCESS.getCode());
            redisTemplate.delete(AllRedisKey.MENU_ALL.getKey());
            return responseResult;
        }
    }


    public String time(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String format = simpleDateFormat.format(date);
        String response = format.replaceAll("[[\\s-:punct:]]","");
        return response;
    }

}
