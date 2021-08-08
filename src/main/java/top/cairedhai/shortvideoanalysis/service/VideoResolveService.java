package top.cairedhai.shortvideoanalysis.service;

import top.cairedhai.shortvideoanalysis.vo.VideoInfoVo;

import java.io.OutputStream;
import java.util.List;

/**
 * @Description: 视频解析
 * @Author: Tan
 * @CreateDate: 2021/7/27
 **/
public interface VideoResolveService {


    /**
     * 根据分享url解析出视频信息
     * @Author: Tan
     * @Date: 2021/7/27
     * @param url:  分享url
     * @return: top.cairedhai.shortvideoanalysis.vo.VideoInfoVo
     **/
    VideoInfoVo videoResolve(String url);

    /**
     * @Description:
     * @Author: Tan
     * @Date: 2021/8/8
     * @return: byte[]
     **/
   void  batchDownload(List<VideoInfoVo> videoInfoVoList, OutputStream out);

}
