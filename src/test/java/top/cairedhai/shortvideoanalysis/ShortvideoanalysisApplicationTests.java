package top.cairedhai.shortvideoanalysis;

import cn.hutool.core.io.FileUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.cairedhai.shortvideoanalysis.service.impl.BiliVideoUploadServiceImpl;
import top.cairedhai.shortvideoanalysis.service.impl.DyVideoResolveServiceImpl;

import java.net.SocketTimeoutException;

@SpringBootTest
class ShortvideoanalysisApplicationTests {

    @Autowired
    private DyVideoResolveServiceImpl dyVideoResolveService;

    @Autowired
  private BiliVideoUploadServiceImpl bibiVideoUploadService;

    @Test
    void contextLoads() throws Exception {
//        byte[] bytes = FileUtil.readBytes("C:\\Users\\tanqingquan\\Desktop\\11.mp4");
//        JSONObject jsonObject = bibiVideoUploadService.registerVideoSpace(System.currentTimeMillis()+".mp4", bytes.length);
//        String uposUri=  jsonObject.getString("upos_uri");
//        uposUri=uposUri.substring(uposUri.lastIndexOf("/"));
//        String auth=jsonObject.getString("auth");
//        jsonObject =  bibiVideoUploadService.preUpload(uposUri,auth);
//        String uploadId=jsonObject.getString("upload_id");
//        bibiVideoUploadService.uploadVideo(uposUri,uploadId,bytes,auth);

//        byte[] bytes = FileUtil.readBytes("C:\\Users\\tanqingquan\\Desktop\\1.jpg");
//
//        byte[] bytes1 = bibiVideoUploadService.zoomPicture(bytes, 1146, 717, "jpg");
//
//        System.out.println(bibiVideoUploadService.uploadCover(bytes1, "ak=1494471752&cdn=%2F%2Fupos-sz-upcdnbda2.bilivideo.com&os=upos&sign=e31edcd698a5fb7a438f5ae5cdfa86c5&timestamp=1628923569&uid=46313553&uip=58.62.32.213&uport=29924&use_dqp=1"));

        String url="https://upos-sz-upcdnbda2.bilivideo.com/ugc/n210816a233dpr7sx7hd6h1kpl2ywvfv.mp4?output=json&profile=ugcupos/bup&name=%s&uploadId=%s&biz_id=%s";
        url=String.format(url,"1629125264377.mp4","184697a7d3689d49f01464b89dc5a692","390594777");

        System.out.println(url);

    }


}
