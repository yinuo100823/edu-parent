package com.vo.serviceoss.controller;

import com.vo.commonutils.Resp;
import com.vo.serviceoss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/edu/oss/file")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;
    @PostMapping("/upload")
    public Resp uploadOssFile(MultipartFile file){
        String url = ossService.uploadFileAvatar(file);
        return Resp.ok().data("data",url);
    }
}
