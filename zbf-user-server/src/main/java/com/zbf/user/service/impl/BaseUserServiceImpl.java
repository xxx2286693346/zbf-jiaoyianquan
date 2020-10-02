package com.zbf.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zbf.common.entity.my.BaseUser;
import com.zbf.user.mapper.BaseUserMapper;
import com.zbf.user.service.IBaseUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 袁成龙
 * @since 2020-09-11
 */
@Service
public class BaseUserServiceImpl extends ServiceImpl<BaseUserMapper, BaseUser> implements IBaseUserService {

    @Autowired
    private BaseUserMapper baseUserMapper;

    @Override
    public boolean adduserrole(long id) {
        return baseUserMapper.adduserrole(id);
    }

    @Override
    public BaseUser maxid() {
        return baseUserMapper.maxid();
    }

    @Override
    public IPage listpage(Page page, BaseUser vo) {
        return baseUserMapper.listpage(page,vo);
    }

    @Override
    public BaseUser finloginName(String loginName) {
        return baseUserMapper.finloginName(loginName);
    }

    @Override
    public boolean updatestatus(Integer status,Long id) {
        return baseUserMapper.updatestatus(status,id);
    }

    @Override
    public List<BaseUser> getlist(String pai,String ord,Integer limi, String names, String rname) {
        return baseUserMapper.getlist(pai,ord,limi,names,rname);
    }
}
