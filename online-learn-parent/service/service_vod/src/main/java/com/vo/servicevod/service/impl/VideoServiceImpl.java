package com.vo.servicevod.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.vo.servicebase.entity.VoCodeException;
import com.vo.servicevod.service.VideoService;
import com.vo.servicevod.utils.AliyunVodSDKUtils;
import com.vo.servicevod.utils.ConstantPropertiesUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {
    @Override
    public String uploadVideo(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));

            UploadStreamRequest request = new UploadStreamRequest(
                    ConstantPropertiesUtils.KEY_ID,
                    ConstantPropertiesUtils.KEY_SECRET,
                    title, originalFilename, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
            // 其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            String videoId = response.getVideoId();
            if (!response.isSuccess()) {
                String errorMessage = "阿里云上传错误：" + "code：" + response.getCode() + ", message：" + response.getMessage();
                if (StringUtils.isEmpty(videoId)) {
                    throw new VoCodeException(20001, errorMessage);
                }
            }

            return videoId;
        } catch (IOException e) {
            throw new VoCodeException(20001, "视频上传失败");
        }
    }

    @Override
    public void deleteVideo(String videoId) {
        try {
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(
                    ConstantPropertiesUtils.KEY_ID,
                    ConstantPropertiesUtils.KEY_SECRET);

            DeleteVideoRequest request = new DeleteVideoRequest();

            request.setVideoIds(videoId);

            client.getAcsResponse(request);
        } catch (Exception e) {
            e.printStackTrace();
            throw new VoCodeException(20001, "视频删除失败");
        }
    }

    @Override
    public void deleteVideoByIds(List<String> ids) {
        try {
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(
                    ConstantPropertiesUtils.KEY_ID,
                    ConstantPropertiesUtils.KEY_SECRET);

            DeleteVideoRequest request = new DeleteVideoRequest();
            String videoIds = org.apache.commons.lang.StringUtils.join(ids, ",");
            request.setVideoIds(videoIds);
            client.getAcsResponse(request);
        } catch (Exception e) {
            e.printStackTrace();
            throw new VoCodeException(20001, "批量视频删除失败");
        }
    }
}
