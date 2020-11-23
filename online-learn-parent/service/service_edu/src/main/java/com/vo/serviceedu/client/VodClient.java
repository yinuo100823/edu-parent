package com.vo.serviceedu.client;

import com.vo.commonutils.Resp;
import com.vo.serviceedu.client.impl.VodClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 熔断机制实现：
 * 1、添加依赖
 * 2、调用放配置熔断 以及 超时时间
 * 3、添加client的实现类
 * 4、在client接口@FeignClient中添加fallback = 实现类.class，表示如果远程接口调用失败后执行实现类中的方法
 */
@Component
@FeignClient(name = "service-vod", fallback = VodClientImpl.class)
public interface VodClient {

    //定义方法调用的地址
    @DeleteMapping("/edu/vod/video/delete/{videoId}")
    Resp deleteVideoById(@PathVariable("videoId") String videoId);

    @DeleteMapping("/edu/vod/video/delete/more")
    Resp deleteVideoByIds(@RequestBody List<String> ids);
}
