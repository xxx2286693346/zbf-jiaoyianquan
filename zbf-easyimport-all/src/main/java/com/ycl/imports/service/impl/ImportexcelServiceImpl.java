package com.ycl.imports.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ycl.imports.entity.Importexcel;
import com.ycl.imports.mapper.ImportexcelMapper;
import com.ycl.imports.service.IImportexcelService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 袁成龙
 * @since 2020-09-30
 */
@Service
public class ImportexcelServiceImpl extends ServiceImpl<ImportexcelMapper, Importexcel> implements IImportexcelService {

}
