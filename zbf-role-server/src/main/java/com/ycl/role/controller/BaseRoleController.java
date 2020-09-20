package com.ycl.role.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ycl.role.service.IBaseRoleService;
import com.zbf.common.entity.ResponseResult;
import com.zbf.common.entity.my.BaseRole;
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

    ResponseResult responseResult = new ResponseResult();

    @RequestMapping("/roles")
    public ResponseResult roles(String roleName){
        System.out.println(roleName);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like("name",roleName);
        List<BaseRole> list = iBaseRoleService.list(queryWrapper);
        responseResult.setResult(list);
        return responseResult;
    }



}

