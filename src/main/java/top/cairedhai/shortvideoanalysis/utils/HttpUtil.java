package top.cairedhai.shortvideoanalysis.utils;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.Method;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.util.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.HttpCookie;
import java.util.Map;


/**
 * @Description: TODO
 * @Author: Tan
 * @CreateDate: 2021/7/27
 **/
@Log4j2
public class HttpUtil {

    final static String UA="Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Mobile Safari/537.36";


    final static String BROWSER_UA="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36";


    /**
     * 模拟安卓设备发起get请求
     * @Author: Tan
     * @Date: 2021/8/8
     * @param url:
     * @return: cn.hutool.http.HttpResponse
     **/
    public static HttpResponse mockAndroidGetRequest(String url){
        log.info("请求地址:{}",url);
        HttpRequest get = cn.hutool.http.HttpUtil.createGet(url);
        get.header("User-Agent",UA);
        HttpResponse response = get.execute();
        log.info("请求响应:{}",response);
        return   response;
    }

    /**
     * get 请求 并添加cookie
     * @Author: Tan
     * @Date: 2021/8/12
     * @param url:
     * @param httpCookie:
     * @return: cn.hutool.http.HttpResponse
     **/
    public static HttpResponse getRequestAddCookie(String url,String headName,String headValue,HttpCookie ... httpCookie){
        log.info("请求地址:{}",url);
        HttpRequest get = cn.hutool.http.HttpUtil.createGet(url);
        get.header("User-Agent",BROWSER_UA);
        get.cookie(httpCookie);
        if (null!=headName&&!"".equals(headName)) {
            get.header(headName,headValue);
        }
        HttpResponse response = get.execute();
        log.info("请求响应:{}",response);
        return   response;
    }

    /**
     * post 请求
     * @Author: Tan
     * @Date: 2021/8/12
     * @param url:
     * @param param:
     * @param httpCookie:
     * @return: cn.hutool.http.HttpResponse
     **/
    public static HttpResponse postRequestJson(String url,String param,String headName,String headValue,HttpCookie ... httpCookie){
        log.info("请求地址:{},请求参数:{}",url,param);
        HttpRequest post  = cn.hutool.http.HttpUtil.createPost(url);
        post.cookie(httpCookie);
        post.header("User-Agent",BROWSER_UA);
        if (null!=param&&!"".equals(param)) {
            post.body(param);
        }
        if (null!=headName&&!"".equals(headName)) {
            post.header(headName,headValue);
        }
        HttpResponse response = post.execute();
        log.info("请求响应:{}",response);
        return   response;
    }


    /**
     * post 请求
     * @Author: Tan
     * @Date: 2021/8/12
     * @param url:
     * @param param:
     * @param httpCookie:
     * @return: cn.hutool.http.HttpResponse
     **/
    public static HttpResponse postRequestForm(String url, Map<String,Object> param, String headName, String headValue, HttpCookie ... httpCookie){
        log.info("请求地址:{},请求参数:{}",url,param);
        HttpRequest post  = cn.hutool.http.HttpUtil.createPost(url);
        post.cookie(httpCookie);
        post.header("User-Agent",BROWSER_UA);
        if (MapUtil.isNotEmpty(param)) {
            post.form(param);
        }
        if (null!=headName&&!"".equals(headName)) {
            post.header(headName,headValue);
        }
        HttpResponse response = post.execute();
        log.info("请求响应:{}",response);
        return   response;
    }

    public static HttpResponse putRequest(String url,byte[] data,String headName,String headValue,HttpCookie ... httpCookie){
        log.info("请求地址:{}",url);
        HttpRequest put  = cn.hutool.http.HttpUtil.createRequest(Method.PUT,url);
        put.cookie(httpCookie);
        put.header("User-Agent",BROWSER_UA);
        put.body(data);
        if (null!=headName&&!"".equals(headName)) {
            put.header(headName,headValue);
        }
        HttpResponse response = put.execute();
        log.info("请求响应:{}",response);
        return   response;
    }



    /**
     * get请求方式下载文件
     * @Author: Tan
     * @Date: 2021/8/8
     * @param url:
     * @return: byte[]
     **/
    public static byte[] getRequestDownload(String url){
        log.info("文件下载地址:{}",url);
        HttpRequest get = cn.hutool.http.HttpUtil.createGet(url);
        HttpResponse execute = get.execute();
        return execute.bodyBytes();
    }

    /**
     * 设置下载头
     * @Author: Tan
     * @Date: 2021/8/8
     * @param response:
     * @param fileName:
     * @return: void
     **/
    public static void setDownLoadHeader(HttpServletResponse response,String fileName){
        response.setContentType("application/octet-stream");
        try {
            response.setHeader("Content-Disposition","attachment;filename=" + new String(fileName.getBytes("UTF-8"),"ISO-8859-1"));
        } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
        }
    }


    public static void main(String[] args) {
//        String url= mockAndroidGetRequest("https://v.kuaishouapp.com/s/7IxAnk2R").header("Location");
//        HttpResponse httpResponse = mockAndroidGetRequest(url);
//        Document document = Jsoup.parse(httpResponse.body());
//        Element video = document.getElementById("video-player");
//        System.out.println(video.attr("poster"));
//        System.out.println(video.attr("src"));


//           mockAndroidGetRequest("https://v.douyin.com/evb825L/");
//            mockAndroidGetRequest("https://v.kuaishouapp.com/s/Z5PLlITj");

      HttpCookie cookie1=  new HttpCookie("SESSDATA", "5a3287f3%2C1640232172%2C2c911*61");
//        HttpCookie cookie2=  new HttpCookie("bili_jct", "7a4638f1c1a01ac1ae5665401955f80c");

//        String url="https://member.bilibili.com/preupload?name=1.mp4&size=1048576&r=upos&profile=ugcupos%2Fbup&ssl=0&version=2.8.12&build=2081200&upcdn=qn&probe_version=20200810";
//
//        String url="https://member.bilibili.com/preupload?name=demo.mp4&size=19899950&r=upos&profile=ugcupos/bup&ssl=0&version=2.8.9&build=2080900&upcdn=bda2&probe_version=20200810";
//        System.out.println(getRequestAddCookie(url, new HttpCookie("SESSDATA", "5a3287f3%2C1640232172%2C2c911*61")).body());


        String url="https://upos-sz-upcdnbda2.bilivideo.com/ugcboss/n210812a23ofsa6rlz1bsm2rquek7xz0.mp4?uploads&output=json";
//        JSONObject param=new JSONObject();
//        param.put("auth","ak=1494471752&cdn=%2F%2Fupos-sz-upcdnqn.bilivideo.com\\u0026os=upos\\u0026sign=cc35100ee74d4e324338656b8777f858\\u0026timestamp=1628773687\\u0026uid=46313553\\u0026uip=58.62.32.242\\u0026uport=27431\\u0026use_dqp=1");
//
//        System.out.println(postRequest(url, null,"X-Upos-Auth","ak=1494471752\\u0026cdn=%2F%2Fupos-sz-upcdnbda2.bilivideo.com\\u0026os=upos\\u0026sign=e0d6207b94288900efe26b5047d31cee\\u0026timestamp=1628777985\\u0026uid=46313553\\u0026uip=58.62.32.242\\u0026uport=27939\\u0026use_dqp=1", cookie1).body());
    }


}
