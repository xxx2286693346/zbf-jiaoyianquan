package com.ycl.menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zbf.common.entity.my.BaseMenu;
import com.zbf.common.entity.my.BaseRoleMenu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 袁成龙
 * @since 2020-09-16
 */
public interface IBaseMenuService extends IService<BaseMenu> {

    List<BaseMenu> findall(@Param("loginName") String loginName, @Param("leval") Integer leval, @Param("code") Long code);

    List<BaseMenu> findalldan( @Param("leval") Integer leval, @Param("code") Long code);

    List<BaseMenu> finrid( @Param("leval") Integer leval, @Param("code") Long code,@Param("rid") Integer rid);
}
