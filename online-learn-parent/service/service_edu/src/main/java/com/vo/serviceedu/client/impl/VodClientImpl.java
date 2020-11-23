package com.vo.serviceedu.client.impl;

import com.vo.commonutils.Resp;
import com.vo.serviceedu.client.VodClient;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class VodClientImpl implements VodClient {
    @Override
    public Resp deleteVideoById(String videoId) {
        return Resp.ok().message("删除视频失败，视频ID：" + videoId);
    }

    @Override
    public Resp deleteVideoByIds(List<String> ids) {
        return Resp.ok().message("删除多个视频失败");
    }
}
