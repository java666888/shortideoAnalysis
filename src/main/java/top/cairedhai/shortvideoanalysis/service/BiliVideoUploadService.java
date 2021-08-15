package top.cairedhai.shortvideoanalysis.service;

import top.cairedhai.shortvideoanalysis.param.BiliContributeParam;
import top.cairedhai.shortvideoanalysis.vo.VideoInfoVo;

/**
 * @Description: b 站视频上传
 * @Author: Tan
 * @CreateDate: 2021/8/12
 **/
public interface BiliVideoUploadService {


    /**
     * 根据视频信息 以及 投稿信息 上传b站 返回投稿成功的bid
     * @Author: Tan
     * @Date: 2021/8/15
     * @param videoInfo:
     * @param param:
     * @return: java.lang.String
     **/
    String videoUploadBili(VideoInfoVo videoInfo, BiliContributeParam param);


}
