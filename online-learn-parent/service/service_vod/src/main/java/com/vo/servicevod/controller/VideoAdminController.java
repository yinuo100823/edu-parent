package com.vo.servicevod.controller;

import com.vo.commonutils.Resp;
import com.vo.servicevod.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api("阿里云视频点播微服务")
@RestController
@RequestMapping("/edu/vod/video")
@CrossOrigin
public class VideoAdminController {

    @Autowired
    private VideoService videoService;

    @PostMapping("upload")
    public Resp uploadVideo(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file) throws Exception {

        String videoId = videoService.uploadVideo(file);
        return Resp.ok().message("视频上传成功").data("videoId", videoId);
    }
}
