package com.vo.servicevod.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface VideoService {

    String uploadVideo(MultipartFile file);

    void deleteVideo(String videoId);

}
