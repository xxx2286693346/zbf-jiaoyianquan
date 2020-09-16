package com.ycl.menu.service.impl;

import com.ycl.menu.mapper.BaseRoleMenuMapper;
import com.ycl.menu.service.IBaseRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class BaseRoleMenuServiceImpl extends ServiceImpl<BaseRoleMenuMapper, BaseRoleMenu> implements IBaseRoleMenuService {

}
