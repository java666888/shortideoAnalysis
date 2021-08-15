package top.cairedhai.shortvideoanalysis.controller;

import cn.hutool.core.util.StrUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cairedhai.shortvideoanalysis.common.ResponseResult;
import top.cairedhai.shortvideoanalysis.common.enums.ResponseCodeEnum;
import top.cairedhai.shortvideoanalysis.param.BiliContributeParam;
import top.cairedhai.shortvideoanalysis.service.BiliVideoUploadService;
import top.cairedhai.shortvideoanalysis.service.VideoResolveService;
import top.cairedhai.shortvideoanalysis.vo.VideoInfoVo;

/**
 * @Description: b站投稿接口
 * @Author: Tan
 * @CreateDate: 2021/8/15
 **/
@Log4j2
@RestController
@RequestMapping("/biliContribution")
public class BiliContributionController {

    @Autowired
    private BiliVideoUploadService biliVideoUploadService;


    @PostMapping("/contribution")
    public ResponseResult contribution(String url,@RequestBody BiliContributeParam param) throws Exception{
        //解析视频信息
        VideoResolveService videoResolveService = VideoResolveController.getVideoResolveService(url);
        VideoInfoVo videoInfoVo = videoResolveService.videoResolve(url);
        //投稿
        String bvId = biliVideoUploadService.videoUploadBili(videoInfoVo, param);
        return StrUtil.isNotBlank(bvId)?ResponseResult.success(bvId):ResponseResult.failure(ResponseCodeEnum.FAILED_TO_SUBMIT);
    }





}
