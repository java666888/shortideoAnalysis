package top.cairedhai.shortvideoanalysis.service.impl;

import cn.hutool.core.util.ReUtil;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import top.cairedhai.shortvideoanalysis.service.VideoResolveService;
import top.cairedhai.shortvideoanalysis.utils.HttpUtil;
import top.cairedhai.shortvideoanalysis.vo.UserInfoVo;
import top.cairedhai.shortvideoanalysis.vo.VideoInfoVo;

/**
 * @Description: 抖音解析
 * @Author: Tan
 * @CreateDate: 2021/7/29
 **/
@Log4j2
@Service("DyVideoResolveServiceImpl")
public class DyVideoResolveServiceImpl extends BaseVideoResolveServiceImpl  implements VideoResolveService {

    /**抖音视频详情接口*/
    final  static  String VIDEO_DETAILS_URL="https://www.iesdouyin.com/web/api/v2/aweme/iteminfo/?item_ids=";



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
            //解析短链 获取重定向地址
            HttpResponse response = HttpUtil.mockAndroidGetRequest(url);
            String redirectUrl = response.header("Location");
            //提取视频id
            String videoId = regexGetVideoId(redirectUrl);
            //请求详情接口
            response =  HttpUtil.mockAndroidGetRequest(VIDEO_DETAILS_URL+videoId);
            return   respJsonResolve(response);
        }catch (Exception e){
            log.error("解析抖音视频失败",e);
        }
        return null;
    }

    /**
     * 解析json 提取视频信息
     * @Author: Tan
     * @Date: 2021/7/29
     * @param response:
     * @return: top.cairedhai.shortvideoanalysis.vo.VideoInfoVo
     **/
    public VideoInfoVo respJsonResolve(HttpResponse response){
       JSONObject resp=  JSONObject.parseObject(response.body()).getJSONArray("item_list").getJSONObject(0);
        String VideoUrl= resp.getJSONObject("video").getJSONObject("play_addr").getJSONArray("url_list").getString(0);
        //将水印播放地址替换为无水印
        VideoUrl=VideoUrl.replace("playwm","play");

        String frontCoverUrl=resp.getJSONObject("video").getJSONObject("cover").getJSONArray("url_list").getString(0);

      return   VideoInfoVo.builder().userInfo(getUserInfoVo(resp))
        .synopsis(resp.getString("desc"))
        .videoUrl(VideoUrl)
        .frontCoverUrl(frontCoverUrl).build();
    }

    /**
     * 获取用户信息
     * @Author: Tan
     * @Date: 2021/7/29
     * @param resp:
     * @return: top.cairedhai.shortvideoanalysis.vo.UserInfoVo
     **/
    public UserInfoVo  getUserInfoVo(JSONObject resp){
        JSONObject author = resp.getJSONObject("author");
         return   UserInfoVo.builder()
        .userId(author.getString("uid"))
        .userNickName(author.getString("nickname"))
        .userAccount(author.getString("short_id")).build();
    }

    /**
     * 正则表达式提取视频id
     * @Author: Tan
     * @Date: 2021/7/29
     * @param redirectUrl:
     * @return: java.lang.String
     **/
    public String regexGetVideoId(String redirectUrl){
      return   ReUtil.getGroup0("/\\d+/",redirectUrl).replaceAll("/","");
    }



}
