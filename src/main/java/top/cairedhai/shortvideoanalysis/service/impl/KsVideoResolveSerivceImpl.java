package top.cairedhai.shortvideoanalysis.service.impl;

import cn.hutool.http.HttpResponse;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import top.cairedhai.shortvideoanalysis.service.VideoResolveService;
import top.cairedhai.shortvideoanalysis.utils.HttpUtil;
import top.cairedhai.shortvideoanalysis.vo.VideoInfoVo;

/**
 * @Description: 快手视频解析
 * @Author: Tan
 * @CreateDate: 2021/7/27
 **/
@Log4j2
@Service("KsVideoResolveSerivceImpl")
public class KsVideoResolveSerivceImpl implements VideoResolveService {

    /**
     * 根据分享url解析出视频信息
     *
     * @param url :  分享url
     * @Author: Tan
     * @Date: 2021/7/27
     * @return: top.cairedhai.shortvideoanalysis.vo.VideoInfoVo
     **/
    @Override
    public VideoInfoVo videoResolve(String url) {
        try {
            // 解析短链 获取重定向url
            HttpResponse response = HttpUtil.mockAndroidGetRequest(url);
            String playUrl = response.header("Location");
            //解析 播放html 页面获取 封面和视频地址
            response = HttpUtil.mockAndroidGetRequest(playUrl);
            return videoHtmlResolve(response);
        }catch (Exception e){
            log.error("解析快手视频失败",e);
        }
        return null;
    }

    /**
     * 视频播放html页面解析 播放地址
     * @Author: Tan
     * @Date: 2021/7/28
     * @param response:
     * @return: top.cairedhai.shortvideoanalysis.vo.VideoInfoVo
     **/
    public VideoInfoVo videoHtmlResolve(HttpResponse response){
        Document document = Jsoup.parse(response.body());
        Element video = document.getElementById("video-player");
        return   VideoInfoVo.builder().frontCoverUrl(video.attr("poster")).videoUrl(video.attr("src")).synopsis(video.attr("alt")).build();
    }


}
