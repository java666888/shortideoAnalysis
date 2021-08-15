package top.cairedhai.shortvideoanalysis.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import top.cairedhai.shortvideoanalysis.common.ResponseResult;
import top.cairedhai.shortvideoanalysis.common.enums.ResponseCodeEnum;
import top.cairedhai.shortvideoanalysis.service.VideoResolveService;
import top.cairedhai.shortvideoanalysis.utils.HttpUtil;
import top.cairedhai.shortvideoanalysis.utils.SpringUtil;
import top.cairedhai.shortvideoanalysis.vo.VideoInfoVo;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: 视频解析
 * @Author: Tan
 * @CreateDate: 2021/7/27
 **/
@Log4j2
@RestController
@RequestMapping("/videoResolve")
public class VideoResolveController {

    static  Map<String,String> serviceMap;

    static {
        serviceMap=new HashMap<>();
        serviceMap.put("kuaishouapp","KsVideoResolveSerivceImpl");
        serviceMap.put("douyin","DyVideoResolveServiceImpl");
    }


    /**
     * 单个视频解析 返回视频信息
     * @Author: Tan
     * @Date: 2021/8/8
     * @param url:
     * @return: top.cairedhai.shortvideoanalysis.common.ResponseResult
     **/
    @GetMapping("/singleVideo")
    public ResponseResult singleVideoResolve(String url)throws Exception{
        VideoResolveService   videoResolveService=getVideoResolveService(url);
        VideoInfoVo videoInfoVo = videoResolveService.videoResolve(url);
        return  null==videoInfoVo?ResponseResult.failure(ResponseCodeEnum.FAULURE):ResponseResult.success(videoInfoVo);
    }

    /**
     * 批量下载视频 返回zip文件
     * @Author: Tan
     * @Date: 2021/8/8
     * @param urlList:
     * @return: void
     **/
    @PostMapping("/batchDownload")
    public void batchDownload(@RequestBody List<String> urlList, HttpServletResponse response) throws Exception{
        if (CollectionUtils.isEmpty(urlList)) {
            response.getWriter().write(JSON.toJSONString(ResponseResult.failure(ResponseCodeEnum.PARAM_EMPTY)));
        }


        VideoResolveService videoResolveService=getVideoResolveService(urlList.get(0));
        //解析提取视频信息集合
        List<VideoInfoVo> videoInfoVoList=  urlList.stream().map(item->{
            try {
                return videoResolveService.videoResolve(item);
            }catch (Exception e){
                log.error("批量下载失败,{}",e);
            }
            return null;
        }).filter(item->null!=item).collect(Collectors.toList());
        //批量下载
        ServletOutputStream outputStream = response.getOutputStream();
        HttpUtil.setDownLoadHeader(response,System.currentTimeMillis()+".zip");
        videoResolveService.batchDownload(videoInfoVoList,outputStream);
    }


    public static VideoResolveService  getVideoResolveService(String url) throws Exception{
        for (String key : serviceMap.keySet()) {
            if (url.contains(key)) {
                return (VideoResolveService) SpringUtil.getBean(serviceMap.get(key));
            }
        }
        throw new Exception("无法解析此url");
    }



}
