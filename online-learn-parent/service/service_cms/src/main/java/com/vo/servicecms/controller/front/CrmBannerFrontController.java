package com.vo.servicecms.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vo.commonutils.Resp;
import com.vo.servicecms.entity.CrmBanner;
import com.vo.servicecms.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@Api(value = "前台获取banner的接口")
@RestController
@RequestMapping("/edu/cms/front")
public class CrmBannerFrontController {
    @Autowired
    private CrmBannerService bannerService;

    @GetMapping("banner/list")
    @ApiOperation(value = "获取banner的前4个")
    public Resp getAllBanner() {
        QueryWrapper queryWrapper = new QueryWrapper<CrmBanner>();
        queryWrapper.orderByDesc("sort");
        queryWrapper.last("limit 4");
        List<CrmBanner> list = bannerService.selectList(queryWrapper);
        return Resp.ok().data("data", list);
    }
}
