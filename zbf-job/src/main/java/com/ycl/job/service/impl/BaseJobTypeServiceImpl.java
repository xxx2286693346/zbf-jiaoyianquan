package com.ycl.job.service.impl;

import com.ycl.job.mapper.BaseJobTypeMapper;
import com.ycl.job.service.IBaseJobTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zbf.common.entity.my.BaseJobType;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 袁成龙
 * @since 2020-09-28
 */
@Service
public class BaseJobTypeServiceImpl extends ServiceImpl<BaseJobTypeMapper, BaseJobType> implements IBaseJobTypeService {

}
