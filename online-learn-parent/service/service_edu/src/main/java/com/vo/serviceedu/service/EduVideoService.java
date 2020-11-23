package com.vo.serviceedu.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vo.serviceedu.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author Loong
 * @since 2020-11-17
 */
public interface EduVideoService extends IService<EduVideo> {

    void deleteVideoByCourseId(String courseId);

    boolean deleteVideoByChapterId(String chapterId);
}
