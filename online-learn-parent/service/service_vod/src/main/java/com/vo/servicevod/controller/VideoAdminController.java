package com.vo.servicevod.controller;

import com.vo.commonutils.Resp;
import com.vo.servicevod.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @DeleteMapping("delete/{videoId}")
    public Resp deleteVideoById(@PathVariable String videoId) {
        videoService.deleteVideo(videoId);
        return Resp.ok().message("删除视频成功");
    }

    @DeleteMapping("delete/more")
    public Resp deleteVideoByIds(@RequestBody List<String> ids){
        videoService.deleteVideoByIds(ids);
        return Resp.ok();
    }
}
