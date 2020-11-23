package com.vo.servicecms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vo.commonutils.Resp;
import com.vo.servicebase.entity.VoCodeException;
import com.vo.servicecms.entity.CrmBanner;
import com.vo.servicecms.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author Loong
 * @since 2020-11-23
 */
@RestController
@RequestMapping("/edu/cms")
@CrossOrigin
@Api(value = "首页轮播图管理")
public class CrmBannerController {

    @Autowired
    private CrmBannerService bannerService;

    @PostMapping("/admin/banner/list/{pageNo}/{pageSize}")
    @ApiOperation(value = "分页获取banner列表")
    public Resp getBannerByPage(
            @ApiParam(required = true, name = "pageNo", value = "页数")
            @PathVariable Long pageNo,
            @ApiParam(required = true, name = "pageSize", value = "每页记录数")
            @PathVariable Long pageSize,
            @ApiParam(required = false)
            @RequestBody Map<String, String> queryMap) {
        String title = queryMap.get("title");
        QueryWrapper<CrmBanner> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title.trim());
        }
        Page<CrmBanner> bannerPage = new Page<>(pageNo, pageSize);
        bannerService.page(bannerPage, queryWrapper);
        long total = bannerPage.getTotal();
        List<CrmBanner> banners = bannerPage.getRecords();
        Map<String, Object> respMap = new HashMap<>();
        respMap.put("total", total);
        respMap.put("rows", banners);
        return Resp.ok().data("data", respMap);
    }

    @GetMapping("/admin/banner/info/{bannerId}")
    @ApiOperation(value = "根据轮播图Id获取轮播图详情")
    public Resp getBannerInfoById(
            @ApiParam(required = true, value = "轮播图ID", name = "bannerId")
            @PathVariable String bannerId) {
        CrmBanner banner = bannerService.getById(bannerId);
        if (banner == null) {
            throw new VoCodeException(20001, "查询轮播图详情失败");
        }
        return Resp.ok().data("data", banner);
    }


    @PostMapping("/admin/banner/add")
    @ApiOperation(value = "创建banner")
    public Resp addBanner(
            @ApiParam(required = true)
            @RequestBody CrmBanner banner) {
        boolean saveFlag = bannerService.save(banner);
        return saveFlag ? Resp.ok() : Resp.error();
    }

    @PostMapping("/admin/banner/update")
    @ApiOperation(value = "更新banner")
    public Resp updateBannerById(
            @ApiParam(required = true)
            @RequestBody CrmBanner banner) {
        boolean saveFlag = bannerService.updateById(banner);
        return saveFlag ? Resp.ok() : Resp.error();
    }

    @DeleteMapping("/admin/banner/delete/{bannerId}")
    @ApiOperation(value = "根据ID删除banner")
    public Resp deleteBannerById(
            @ApiParam(required = true)
            @PathVariable String bannerId) {
        boolean saveFlag = bannerService.removeById(bannerId);
        return saveFlag ? Resp.ok() : Resp.error();
    }

    @GetMapping("/front/banner/list")
    public Resp index() {
        List<CrmBanner> banners = bannerService.selectIndexList();
        return Resp.ok().data("data", banners);
    }
}

