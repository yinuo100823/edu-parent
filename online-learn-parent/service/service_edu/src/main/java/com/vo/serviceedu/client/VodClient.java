package com.vo.serviceedu.client;

import com.vo.commonutils.Resp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Component
@FeignClient("service-vod")
public interface VodClient {

    //定义方法调用的地址
    @DeleteMapping("/edu/vod/video/delete/{videoId}")
    Resp deleteVideoById(@PathVariable("videoId") String videoId);

    @DeleteMapping("/edu/vod/video/delete/more")
    Resp deleteVideoByIds(@RequestBody List<String> ids);
}
