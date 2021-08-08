package top.cairedhai.shortvideoanalysis.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cairedhai.shortvideoanalysis.common.ResponseResult;
import top.cairedhai.shortvideoanalysis.common.enums.ResponseCodeEnum;
import top.cairedhai.shortvideoanalysis.service.VideoResolveService;
import top.cairedhai.shortvideoanalysis.utils.SpringUtil;
import top.cairedhai.shortvideoanalysis.vo.VideoInfoVo;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 视频解析
 * @Author: Tan
 * @CreateDate: 2021/7/27
 **/
@RestController
@RequestMapping("/videoResolve")
public class VideoResolveController {

    private VideoResolveService videoResolveService;

    static  Map<String,String> serviceMap;

    static {
        serviceMap=new HashMap<>();
        serviceMap.put("kuaishouapp","KsVideoResolveSerivceImpl");
        serviceMap.put("douyin","DyVideoResolveServiceImpl");
    }



    @GetMapping("/singleVideo")
    public ResponseResult singleVideoResolve(String url)throws Exception{
        videoResolveService=getVideoResolveService(url);
        VideoInfoVo videoInfoVo = videoResolveService.videoResolve(url);
        return  null==videoInfoVo?ResponseResult.failure(ResponseCodeEnum.FAULURE):ResponseResult.success(videoInfoVo);
    }


    public VideoResolveService getVideoResolveService(String url) throws Exception{
        for (String key : serviceMap.keySet()) {
            if (url.contains(key)) {
                return (VideoResolveService) SpringUtil.getBean(serviceMap.get(key));
            }
        }
        throw new Exception("无法解析此url");
    }



}
