package com.vo.serviceedu.controller;


import com.vo.commonutils.Resp;
import com.vo.serviceedu.entity.EduVideo;
import com.vo.serviceedu.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author Loong
 * @since 2020-11-17
 */
@RestController
@RequestMapping("/edu/service/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @PostMapping("/add")
    public Resp addVideo(@RequestBody EduVideo eduVideo) {
        boolean saveFlag = videoService.save(eduVideo);
        return saveFlag ? Resp.ok() : Resp.error().message("添加小节失败");
    }

    @PostMapping("/update")
    public Resp updateVideoById(@RequestBody EduVideo eduVideo) {
        boolean updateFlag = videoService.updateById(eduVideo);
        return updateFlag ? Resp.ok() : Resp.error().message("更新小节失败");
    }

    @GetMapping("/info/{videoId}")
    public Resp getVideoInfoById(@PathVariable String videoId) {
        EduVideo eduVideo = videoService.getById(videoId);
        return eduVideo != null ? Resp.ok().data("data", eduVideo) : Resp.error().message("查询小节信息失败");
    }

    /**
     * 因为小结中会有视频，删除时需要考虑（TODO）
     *
     * @param videoId
     * @return
     */
    @DeleteMapping("/delete/{videoId}")
    public Resp deleteVideoById(@PathVariable String videoId) {
        boolean removeFlag = videoService.removeById(videoId);
        return removeFlag ? Resp.ok() : Resp.error().message("删除小节失败");
    }


}

