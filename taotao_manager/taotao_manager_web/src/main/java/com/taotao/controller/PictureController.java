package com.taotao.controller;

import com.taotao.utils.FastDFSClient;
import com.taotao.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PictureController {

    @Value("${IMAGE_SERVER_URL}")
    private String IMAGE_SERVER_URL;

    @RequestMapping(value = "/pic/upload", produces = MediaType.TEXT_PLAIN_VALUE + ";charset=utf-8")
    @ResponseBody
    private String uploadPic(MultipartFile uploadFile) {
        //接收上传的文件
        try {
            byte[] bytes = uploadFile.getBytes();
            //取文件的扩展名
            String originalFilename = uploadFile.getOriginalFilename();
            String filename = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            //把文件内容上传到图片服务器。
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:resource/fast_dfs.conf");
            String url = fastDFSClient.uploadFile(bytes, filename);
            //从配置文件中取图片服务器的url
            //创建返回结果对象
            Map result = new HashMap<>();
            result.put("error",0);
            result.put("url", IMAGE_SERVER_URL + url);
            //返回结果
            return JsonUtils.objectToJson(result);
        } catch (Exception e) {
            e.printStackTrace();
            Map result = new HashMap<>();
            result.put("error", 1);
            result.put("message", "图片上传失败");
            return JsonUtils.objectToJson(result);
        }

    }

}
