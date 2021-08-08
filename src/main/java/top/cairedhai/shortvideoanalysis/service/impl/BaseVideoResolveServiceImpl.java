package top.cairedhai.shortvideoanalysis.service.impl;

import top.cairedhai.shortvideoanalysis.service.VideoResolveService;
import top.cairedhai.shortvideoanalysis.utils.HttpUtil;
import top.cairedhai.shortvideoanalysis.vo.VideoInfoVo;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Description: TODO
 * @Author: Tan
 * @CreateDate: 2021/8/8
 **/
public class BaseVideoResolveServiceImpl implements VideoResolveService {

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
        return null;
    }

    /**
     * @param videoInfoVoList
     * @Description:
     * @Author: Tan
     * @Date: 2021/8/8
     * @return: byte[]
     */
    @Override
    public void batchDownload(List<VideoInfoVo> videoInfoVoList, OutputStream out) {
        ZipOutputStream zipOut = new ZipOutputStream(out);
        for (int i = 1; i <= videoInfoVoList.size(); i++) {
            byte[] videoByte = HttpUtil.getRequestDownload(videoInfoVoList.get(i-1).getVideoUrl());
            byte[] frontCoverByte = HttpUtil.getRequestDownload(videoInfoVoList.get(i-1).getFrontCoverUrl());
            try {
                zipOut.putNextEntry(new ZipEntry(i+".mp4"));
                zipOut.write(videoByte);
                zipOut.putNextEntry(new ZipEntry(i+".jpg"));
                zipOut.write(frontCoverByte);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            zipOut.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
