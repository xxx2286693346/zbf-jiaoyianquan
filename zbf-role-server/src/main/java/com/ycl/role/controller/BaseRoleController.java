package com.ycl.role.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycl.role.service.IBaseRoleMenuService;
import com.ycl.role.service.IBaseRoleService;
import com.zbf.common.entity.ResponseResult;
import com.zbf.common.entity.my.BaseRole;
import com.zbf.common.entity.my.BaseRoleMenu;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 袁成龙
 * @since 2020-09-18
 */
@RestController
@RequestMapping("/baseRole")
public class BaseRoleController {

    @Autowired
    private IBaseRoleService iBaseRoleService;

    @Autowired
    private IBaseRoleMenuService menuService;

    ResponseResult responseResult = new ResponseResult();

    /**
     * @Author 袁成龙
     * @Description //TODO *根据角色名获取所有的角色信息
     * @Date 21:32 2020/9/22
     * @Param 
     * @return 
     **/
    @RequestMapping("/roles")
    public ResponseResult roles(String roleName){
        System.out.println(roleName);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like("name",roleName);
        List<BaseRole> list = iBaseRoleService.list(queryWrapper);
        responseResult.setResult(list);
        return responseResult;
    }


    /**
     * @Author 袁成龙
     * @Description //TODO * 查询当前role的所有数据且分页
    * @param page
     * @Date 21:14 2020/9/22
     * @Param 
     * @return 
     **/
    @RequestMapping("/list")
    public ResponseResult list(String name, Page page){
        QueryWrapper queryWrapper = new QueryWrapper();
        if(name!=null&& name!=""){
            queryWrapper.like("name",name);
        }
        Page page1 = iBaseRoleService.page(page, queryWrapper);
        responseResult.setResult(page1);
        return responseResult;
    }



    /*
     * @Author 袁成龙
     * @Description //TODO * 角色绑定菜单执行添加方法
    * @param meid
     * @Date 21:13 2020/9/22
     * @Param 
     * @return 
     **/
    @RequestMapping("addroleMenu")
    public ResponseResult add(Long rid,Long[] meid){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("role_id",rid);
        System.out.println("rid="+rid);
        if(meid.length>0){
            //System.out.println("meid="+mid);
            boolean remove = menuService.remove(queryWrapper);
            for (Long mid:meid) {
                BaseRoleMenu baseRoleMenu = new BaseRoleMenu();
                if(remove){
                    baseRoleMenu.setRoleId(rid);
                    baseRoleMenu.setMenuId(mid);
                    boolean save = menuService.save(baseRoleMenu);
                    if(save){
                        responseResult.setCode(1006);
                    }
                }
            }
        }
        return responseResult;
    }

}

