package com.taotao.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.util.JsonUtils;
import com.taotao.managerweb.util.FastDFSClient;
@Controller
public class PictureController {
	@Value("${TAOTAO_IMAGE_SERVER_URL}")
	private String TAOTAO_IMAGE_SERVER_URL;

	@RequestMapping(value="/pic/upload",produces=MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
	@ResponseBody
	public String uploadPic(MultipartFile uploadFile){
		try {
            // 首先接收页面上传的文件
            byte[] content = uploadFile.getBytes();
            // 取出文件的扩展名
            String originalFilename = uploadFile.getOriginalFilename();
            String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            // 把这个文件内容上传到图片服务器
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:resource/client.conf");
            String url = fastDFSClient.uploadFile(content, ext);
            // 从配置文件中取图片服务器的url
            // 创建返回结果对象
            Map result = new HashMap();
            result.put("error", 0);
            result.put("url", TAOTAO_IMAGE_SERVER_URL + url);
            // 返回结果
            return JsonUtils.objectToJson(result);
        } catch (Exception e) {
            e.printStackTrace();
            Map result = new HashMap();
            result.put("error", 1);
            result.put("message", "图片上传失败");
            return JsonUtils.objectToJson(result);
        }
	}
	/*@RequestMapping("/pic/upload")
    @ResponseBody
    public Map uploadPic(MultipartFile uploadFile) {
        try {
            // 首先接收页面上传的文件
            byte[] content = uploadFile.getBytes();
            // 取出文件的扩展名
            String originalFilename = uploadFile.getOriginalFilename();
            String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            // 把这个文件内容上传到图片服务器
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:resource/client.conf");
            String url = fastDFSClient.uploadFile(content, ext);
            // 从配置文件中取图片服务器的url
            // 创建返回结果对象
            Map result = new HashMap();
            result.put("error", 0);
            result.put("url", TAOTAO_IMAGE_SERVER_URL + url);
            // 返回结果
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            Map result = new HashMap();
            result.put("error", 1);
            result.put("message", "图片上传失败");
            return result;
        }
    }*/
	
	
}
