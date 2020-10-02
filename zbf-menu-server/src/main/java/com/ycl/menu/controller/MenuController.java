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
        System.out.println(loginName);
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




    /**
     * @Author 袁成龙
     * @Description //TODO 查询所有的Menu菜单(没有用到当时只是测试了一下mybatisPlus是否可用)
     * @Date 9:03 2020/9/23
     * @Param 
     * @return 
     **/
    @RequestMapping("find")
    public List<BaseMenu> baseMenus(){
        List<BaseMenu> list = iBaseMenuService.list();
        return list;
    }



    /**
     * @Author 袁成龙
     * @Description //TODO 修改菜单
     * @Date 9:03 2020/9/23
     * @Param 
     * @return 
     **/
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



        /**
         * @Author 袁成龙
         * @Description //TODO 递归的方式删除菜单  当要删除1级的时候2,3级也要删除
        * @param code
         * @Date 9:04 2020/9/23
         * @Param 
         * @return 
         **/
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


//删除的递归方法
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


    /**
     * @Author 袁成龙
     * @Description //TODO 解决menu的id主键过长所以用了存入menu的时候吧createTime拿出来解析成数字存到id的位置上
     * @Date 9:05 2020/9/23
     * @Param 
     * @return 
     **/
    public String time(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String format = simpleDateFormat.format(date);
        String response = format.replaceAll("[[\\s-:punct:]]","");
        return response;
    }



    /**
     * @Author 袁成龙
     * @Description //TODO =根据角色查询当前角色所拥有的菜单(方便回显菜单)和查询所有菜单
     * @Date 9:09 2020/9/23
     * @Param 
     * @return 
     **/
    @RequestMapping("/meuns")
    public ResponseResult responseResult(Integer rid){
        List<BaseMenu> findalldan = menuService.findalldan();
        responseResult.setResult(findalldan);
        List<BaseMenu> finrid = menuService.finrid(rid);
        String zhi = "";

        if(findalldan.size()>0){
            for (BaseMenu baseMenu:finrid) {
                zhi+=baseMenu.getId()+",";
                if(baseMenu.getBaseMenus().size()>0){
                    for (BaseMenu baseMenu1:baseMenu.getBaseMenus()) {
                        zhi+=baseMenu1.getId()+",";
                        if(baseMenu1.getBaseMenus().size()>0){
                            for (BaseMenu baseMenu2:baseMenu1.getBaseMenus()) {
                                zhi+=baseMenu2.getId()+",";
                            }
                        }
                    }
                }
            }
        }


        responseResult.setUserInfo(finrid);
        if(zhi.length()>0){
            String substring = zhi.substring(0, zhi.length() - 1);
            responseResult.setMsg(substring);
        }else{
            responseResult.setMsg(zhi);
        }
        return responseResult;
    }


}
