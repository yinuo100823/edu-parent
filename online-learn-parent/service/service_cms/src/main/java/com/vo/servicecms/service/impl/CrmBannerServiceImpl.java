package com.vo.servicecms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vo.servicecms.entity.CrmBanner;
import com.vo.servicecms.mapper.CrmBannerMapper;
import com.vo.servicecms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author Loong
 * @since 2020-11-23
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    @Cacheable(value = "banner", key = "'indexList'")
    public List<CrmBanner> selectList(QueryWrapper<CrmBanner> queryWrapper) {
        List<CrmBanner> banners = this.list(queryWrapper);
        return banners != null ? banners : new ArrayList<>();
    }
}
