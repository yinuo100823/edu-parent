package com.vo.serviceoss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.vo.serviceoss.service.OssService;
import com.vo.serviceoss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        String endPoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
        OSS ossClient=new OSSClientBuilder().build(endPoint,accessKeyId,accessKeySecret);
        try {
            String originalFilename = file.getOriginalFilename();//获取文件名称
            //生成随机字符串，拼接文件名称（为避免文件名称相同时，覆盖问题）
            String uuid= UUID.randomUUID().toString().replace("-","");

            //获取当前日期，OSS中按日期存储
            String datePath = new DateTime().toString("yyyy/MM/dd");

            //最终的文件路径+名称
            originalFilename=datePath+"/"+uuid+originalFilename;

            InputStream inputStream=file.getInputStream();//获取文件输入流
            ossClient.putObject(bucketName,originalFilename,inputStream);//上传
            ossClient.shutdown();
            //返回上传的文件路径
            return "https://"+bucketName+"."+endPoint+"/"+originalFilename;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
