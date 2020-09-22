package com.ycl.menu.service.impl;

import com.ycl.menu.mapper.BaseMenuMapper;
import com.ycl.menu.service.IBaseMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zbf.common.entity.my.BaseMenu;
import com.zbf.common.entity.my.BaseRoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 袁成龙
 * @since 2020-09-16
 */
@Service
public class BaseMenuServiceImpl extends ServiceImpl<BaseMenuMapper, BaseMenu> implements IBaseMenuService {

    @Autowired
    private BaseMenuMapper baseMenuMapper;

    @Override
    public List<BaseMenu> findall(String loginName, Integer leval, Long code) {
        return baseMenuMapper.findall(loginName,leval,code);
    }

    @Override
    public List<BaseMenu> findalldan(Integer leval, Long code) {
        return baseMenuMapper.findalldan(leval,code);
    }

    @Override
    public List<BaseMenu> finrid(Integer leval,Long code,Integer rid) {
        return baseMenuMapper.finrid(leval,code,rid);
    }
}
