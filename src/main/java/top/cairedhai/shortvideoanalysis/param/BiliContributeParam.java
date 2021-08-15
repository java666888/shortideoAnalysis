package top.cairedhai.shortvideoanalysis.param;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: b站投稿参数
 * @Author: Tan
 * @CreateDate: 2021/8/15
 **/
@Data
public class BiliContributeParam {

    //是否原创 1 为原创
    private int copyright=1;
    //封面url
    private String cover;
    //视频简介
    private String desc;
    //粉丝动态
    private String dynamic="";
    //自制声明 1 为自制
    private int no_reprint=1;
    //发布时间 yyyy-MM-dd HH:mm
    private String releaseTime;
    // 发布时间戳 0 为立即发布
    private Long dtime;
    // 字幕 语言设置
    private  JSONObject subtitle;
    //视频标签
    private String tag;
    //分区id
    private int tid;
    //视频标题
    private String title;
    //是否关闭弹幕
    private boolean up_close_danmu=false;
    //是否关闭评论
    private boolean up_close_reply=false;
    //视频信息
    private    List<JSONObject> videos;




}
