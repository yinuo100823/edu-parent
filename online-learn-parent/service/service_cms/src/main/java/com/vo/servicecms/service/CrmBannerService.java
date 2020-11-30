package com.vo.servicecms.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vo.servicecms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author Loong
 * @since 2020-11-23
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> selectList(QueryWrapper<CrmBanner> queryWrapper);
}
