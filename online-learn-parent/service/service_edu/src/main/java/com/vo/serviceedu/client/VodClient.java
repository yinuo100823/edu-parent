package com.vo.serviceedu.client;

import com.vo.commonutils.Resp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-vod")
public interface VodClient {

    //定义方法调用的地址
    @DeleteMapping("/edu/vod/video/delete/{videoId}")
    Resp deleteVideoById(@PathVariable("videoId") String videoId);
}
