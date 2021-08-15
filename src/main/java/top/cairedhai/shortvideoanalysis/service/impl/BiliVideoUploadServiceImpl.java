package top.cairedhai.shortvideoanalysis.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.aop.aspectj.annotation.MetadataAwareAspectInstanceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;
import top.cairedhai.shortvideoanalysis.param.BiliContributeParam;
import top.cairedhai.shortvideoanalysis.service.BiliVideoUploadService;
import top.cairedhai.shortvideoanalysis.utils.HttpUtil;
import top.cairedhai.shortvideoanalysis.utils.VideoMergeUtil;
import top.cairedhai.shortvideoanalysis.vo.VideoInfoVo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.HttpCookie;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: TODO
 * @Author: Tan
 * @CreateDate: 2021/8/14
 **/
@Log4j2
@Service
public class BiliVideoUploadServiceImpl implements BiliVideoUploadService {

    @Value("${bilibili.registerVideoSpaceUrl}")
    String registerVideoSpaceUrl;

    @Value("${bilibili.SESSDATA}")
    String sessData;

    @Value("${bilibili.bili_jct}")
    String biliJct;

    @Value("${bilibili.preUpload}")
    String preUpload;

    @Value("${bilibili.uploadVideo}")
    String uploadVideo;

    @Value("${bilibili.uploadCover}")
    String uploadCover;

    @Value("${bilibili.contribute}")
    String contribute;


    /**
     * 根据视频信息 以及 投稿信息 上传b站 返回投稿成功的bid
     *
     * @param videoInfo :
     * @param param     :
     * @Author: Tan
     * @Date: 2021/8/15
     * @return: java.lang.String
     **/
    @Override
    public String videoUploadBili(VideoInfoVo videoInfo, BiliContributeParam param) {
        try {
            //先下载视频和图片
            byte[] sourceVideo=  HttpUtil.getRequestDownload(videoInfo.getVideoUrl());
            byte[] sourceImg=  HttpUtil.getRequestDownload(videoInfo.getFrontCoverUrl());
            //视频合并 加上专属片头
            byte[] uploadVideo = VideoMergeUtil.videoMerge(getTitleVideo(), sourceVideo);
            //缩放图片
            byte[] uploadImg = zoomPicture(sourceImg, 1146, 717, "jpg");
            //注册视频空间
            JSONObject response = registerVideoSpace(System.currentTimeMillis() + ".mp4", uploadVideo.length);
            String uposUri=  response.getString("upos_uri");
            uposUri=uposUri.substring(uposUri.indexOf("/")+1);
            String auth=response.getString("auth");
            //预上传
            response = preUpload(uposUri, auth);
            String uploadId=response.getString("upload_id");
            String bizId=response.getString("biz_id");
            //上传视频
            uploadVideo(uposUri,uploadId,uploadVideo,auth);
            //上传封面
            String coverUrl = uploadCover(uploadImg, auth);
            param.setCover(coverUrl);
            //投稿
            submissionParameterProcessing(param,bizId,uposUri);
            String bvId= contribute(param,auth);
            log.info("投稿成功,bvid:{}",bvId);
            return bvId;
        }catch (Exception e){
            log.error("投稿发生错误",e);
        }
        return null;
    }


    /**
     * 投稿参数处理
     * @Author: Tan
     * @Date: 2021/8/15
     * @param param:
     * @param bizId:
     * @param uposUri:
     * @return: void
     **/
    public void submissionParameterProcessing(BiliContributeParam param,String bizId,String uposUri){
        //视频参数设置
        JSONObject video = new JSONObject();
        video.put("filename",uposUri.substring(uposUri.lastIndexOf("/")+1,uposUri.indexOf(".")));
        video.put("title",param.getTitle());
        video.put("desc",param.getDesc());
        video.put("cid",bizId);

        param.setVideos(Arrays.asList(video));

        //字幕设置
        JSONObject  subtitle=new JSONObject();
        //设置开启字幕
        subtitle.put("open",0);
        // 字幕语言
        subtitle.put("lan","zh-CN");

        param.setSubtitle(subtitle);

        //设置发布时间
        if ("0".equals(param.getReleaseTime())) {
            param.setDtime(0L);
        } else{
            DateTime date = DateUtil.parse(param.getReleaseTime(), "yyyy-MM-dd HH:mm");
            param.setDtime(date.getTime());
        }

    }



    /**
     * 获取片头视频
     * @Author: Tan
     * @Date: 2021/8/15
     * @return: byte[]
     **/
    public byte[]  getTitleVideo(){
        String path = VideoMergeUtil.getResourceAbsolutePath(VideoMergeUtil.VIDEO_SAVE_DIRECTORY) +"/pt.mp4";
        return VideoMergeUtil.readFile(path);
    }




    /**
     * 从配置文件读取 创建cookie
     * @Author: Tan
     * @Date: 2021/8/14
     * @return: java.net.HttpCookie[]
     **/
    public HttpCookie[] creteCookies(){
        String[] sessDataArray = sessData.split("\\=");
        String[] biliJctArray = biliJct.split("\\=");
        return  new HttpCookie[]{new HttpCookie(sessDataArray[0],sessDataArray[1]),new HttpCookie(biliJctArray[0],biliJctArray[1])};
    }



    /**
     * 注册视频空间
     * @Author: Tan
     * @Date: 2021/8/14
     * @param videoName:
     * @param videoSize:
     * @return: com.alibaba.fastjson.JSONObject
     **/
    public JSONObject registerVideoSpace(String videoName,Integer videoSize){
        String url=String.format(registerVideoSpaceUrl,videoName,videoSize);
        HttpCookie[] httpCookies = creteCookies();
        HttpResponse response = HttpUtil.getRequestAddCookie(url, null, null, httpCookies);
        return JSONObject.parseObject(response.body());
    }

    /**
     * 预上传 主要返回 upload_id  biz_id
     * @Author: Tan
     * @Date: 2021/8/14
     * @param uposUri:
     * @param auth:
     * @return: com.alibaba.fastjson.JSONObject
     **/
    public JSONObject preUpload(String uposUri,String auth){
        String url=String.format(preUpload,uposUri);
        HttpCookie[] httpCookies = creteCookies();
        HttpResponse response = HttpUtil.postRequestJson(url,null,"x-upos-auth",auth,httpCookies);
        return JSONObject.parseObject(response.body());
    }

    /**
     * 上传视频
     * @Author: Tan
     * @Date: 2021/8/14
     * @param uposUri:
     * @param uploadId:
     * @param videoData:
     * @param auth:
     * @return: void
     **/
    public void uploadVideo(String uposUri,String uploadId,byte[] videoData,String auth){
        String url=String.format(uploadVideo,uposUri,uploadId,videoData.length,videoData.length,videoData.length);
        HttpCookie[] httpCookies = creteCookies();
         HttpUtil.putRequest(url, videoData, "x-upos-auth",auth, httpCookies);
    }


    /**
     * 图片缩放
     * @Author: Tan
     * @Date: 2021/8/15
     * @param sourceImgData:
     * @param widht:
     * @param height:
     * @param typeName:
     * @return: byte[]
     **/
    public byte[] zoomPicture(byte[] sourceImgData,int widht,int height,String typeName) throws Exception{
        //创建源图片
        BufferedImage sourceImg= ImageIO.read(new ByteArrayInputStream(sourceImgData));
        //创建新图片
        BufferedImage image = new BufferedImage(widht, height, BufferedImage.TYPE_INT_BGR);
        //创建画板 写入图形
        Graphics graphics = image.createGraphics();
        graphics.drawImage(sourceImg, 0, 0, widht, height, null);
        //输出返回byte数组
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        ImageIO.write(image,typeName,out);
        out.flush();
        out.close();
        return   out.toByteArray();
    }

    /**
     * 上传封面
     * @Author: Tan
     * @Date: 2021/8/15
     * @param image:
     * @param auth:
     * @return: java.lang.String
     **/
    public String uploadCover(byte[] image,String auth){
        //获取url
        String url=uploadCover;
        //图片转base64 字符串
        BASE64Encoder encoder = new BASE64Encoder();
        String base64Img="data:image/jpeg;base64,"+encoder.encode(image);
        //获取cookie
        HttpCookie[] httpCookies = creteCookies();
        String csrf= httpCookies[1].getValue();
        //发送请求
        Map<String,Object> form=new HashMap<>();
        form.put("cover",base64Img);
        form.put("csrf",csrf);
        HttpResponse response = HttpUtil.postRequestForm(url, form, "x-upos-auth", auth, httpCookies);
       return JSONObject.parseObject(response.body()).getJSONObject("data").getString("url");
    }

    /**
     * 投稿接口
     * @Author: Tan
     * @Date: 2021/8/15
     * @param param:
     * @param auth:
     * @return: java.lang.String
     **/
    public  String  contribute(BiliContributeParam param,String auth){
        HttpCookie[] httpCookies = creteCookies();
        String csrf= httpCookies[1].getValue();
        String url=String.format(contribute,csrf);
        HttpResponse response = HttpUtil.postRequestJson(url, JSONObject.toJSONString(param), "x-upos-auth", auth, httpCookies);
        return   JSONObject.parseObject(response.body()).getJSONObject("data").getString("bvid");
    }





}
