package top.cairedhai.shortvideoanalysis.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


/**
 * @Description: TODO
 * @Author: Tan
 * @CreateDate: 2021/7/27
 **/
@Log4j2
public class HttpUtil {

    final static String UA="Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Mobile Safari/537.36";


    public static HttpResponse mockAndroidGetRequest(String url){
        log.info("请求地址:{}",url);
        HttpRequest get = cn.hutool.http.HttpUtil.createGet(url);
        get.header("User-Agent",UA);
        HttpResponse response = get.execute();
        log.info("请求响应:{}",response);
        return   response;
    }





    public static void main(String[] args) {
//        String url= mockAndroidGetRequest("https://v.kuaishouapp.com/s/7IxAnk2R").header("Location");
//        HttpResponse httpResponse = mockAndroidGetRequest(url);
//        Document document = Jsoup.parse(httpResponse.body());
//        Element video = document.getElementById("video-player");
//        System.out.println(video.attr("poster"));
//        System.out.println(video.attr("src"));


//           mockAndroidGetRequest("https://v.douyin.com/evb825L/");
            mockAndroidGetRequest("https://v.kuaishouapp.com/s/Z5PLlITj");
    }


}
