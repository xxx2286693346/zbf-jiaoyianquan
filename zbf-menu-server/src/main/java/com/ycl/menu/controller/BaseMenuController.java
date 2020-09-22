package com.ycl.menu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ycl.menu.service.IBaseMenuService;
import com.ycl.menu.service.IBaseRoleMenuService;
import com.zbf.common.entity.my.BaseMenu;
import com.zbf.common.entity.my.BaseRoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 袁成龙
 * @since 2020-09-16
 */
@RestController

public class BaseMenuController {

    @Autowired
    private IBaseMenuService iBaseMenuService;

    @Autowired
    private IBaseRoleMenuService iBaseRoleMenuService;



    //左侧菜单的查询使用递归的方法是
    public  List<BaseMenu> findall(String loginName){
        System.out.println("zhi=="+loginName);
        List<BaseMenu> listmenu = iBaseMenuService.findall(loginName, 1, (long) 0);
        System.out.println(listmenu);
        this.digui(listmenu,loginName,1);
        return listmenu;
    }


    //递归的方法在这里获取二级以及三级
    public void digui(List<BaseMenu> baseMenus,String loginname,Integer level){
        Integer zhi = level+1;
        for (BaseMenu baseMenu: baseMenus) {
            List<BaseMenu> findall = iBaseMenuService.findall(loginname, zhi, baseMenu.getCode());
            if(findall.size()>0){
                baseMenu.setBaseMenus(findall);
                this.digui(findall,loginname,zhi);
            }else{
                ArrayList<BaseMenu> list = new ArrayList<>();
                baseMenu.setBaseMenus(list);
            }
        }
    }









    //左侧菜单的查询使用递归的方法是
    public  List<BaseMenu> findalldan(){
        List<BaseMenu> listmenu = iBaseMenuService.findalldan(1, (long) 0);
        System.out.println(listmenu);
        this.diguidan(listmenu,1);
        return listmenu;
    }


    //递归的方法在这里获取二级以及三级
    public void diguidan(List<BaseMenu> baseMenus,Integer level){
        Integer zhi = level+1;
        for (BaseMenu baseMenu: baseMenus) {
            List<BaseMenu> findall = iBaseMenuService.findalldan(zhi, baseMenu.getCode());
            if(findall.size()>0){
                baseMenu.setBaseMenus(findall);
                this.diguidan(findall,zhi);
            }else{
                ArrayList<BaseMenu> list = new ArrayList<>();
                baseMenu.setBaseMenus(list);
            }
        }
    }






    //左侧菜单的查询使用递归的方法是
    public  List<BaseMenu> finrid(Integer rid){
        List<BaseMenu> listmenu = iBaseMenuService.finrid(1,(long)0,rid);
        System.out.println(listmenu);
        this.digu1(listmenu,1,rid);
        return listmenu;
    }


    //递归的方法在这里获取二级以及三级
    public void digu1(List<BaseMenu> baseMenus,Integer level,Integer rid){
        Integer zhi = level+1;
        for (BaseMenu baseMenu: baseMenus) {
            List<BaseMenu> findall = iBaseMenuService.finrid(zhi, baseMenu.getCode(),rid);
            if(findall.size()>0){
                baseMenu.setBaseMenus(findall);
                this.digu1(findall,zhi,rid);
            }else{
                ArrayList<BaseMenu> list = new ArrayList<>();
                baseMenu.setBaseMenus(list);
            }
        }
    }













/*


    @RequestMapping("/list")
    public List<BaseMenu> menuList() {

        String str = "zhangsan1";
        String meid = "";

        for (BaseRoleMenu baseRoleMenu : zhangsan1) {
            System.out.println("坎坎坷坷" + baseRoleMenu);
            Long menuId = baseRoleMenu.getMenuId();

            //System.out.println("值是===" + substring);
            QueryWrapper queryWrapper = new QueryWrapper();
            //queryWrapper.eq("parentCode",1);
            queryWrapper.eq("id", menuId);
            List<BaseMenu> list = iBaseMenuService.list(queryWrapper);
            for (BaseMenu baseMenu : list) {
                System.out.println("zhi++++" + baseMenu);
                List<BaseMenu> list3 = iBaseMenuService.list();
                for (BaseMenu baseMen : list3) {
                    if(baseMen.getParentCode()==baseMen.getId()){
                        QueryWrapper queryWrapper1 = new QueryWrapper();
                        queryWrapper1.eq("parentCode", baseMenu.getId());
                        List<BaseMenu> list1 = iBaseMenuService.list(queryWrapper1);
                        baseMenu.setBaseMenus(list1);
                        if (baseMenu.getBaseMenus().size() > 0) {
                            for (BaseMenu baseMenu1 : list1) {
                                QueryWrapper queryWrapper2 = new QueryWrapper();
                                queryWrapper2.eq("parentCode", baseMenu1.getId());
                                List<BaseMenu> list2 = iBaseMenuService.list(queryWrapper2);
                                baseMenu1.setBaseMenus(list2);
                            }
                        }
                    }
                }

            }
             meid+="'"+menuId+"'"+",";
            *//*if(meid.length()){

            }*//*
            return list;
        }
*//*
        System.out.println(meid.length());
        String substring = meid.substring(0, 408);
        System.out.println(substring);
*//*

return null;

    }*/


}


