package top.cairedhai.shortvideoanalysis.utils;

import cn.hutool.core.io.FileUtil;

import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: 视频合并操作
 * @Author: Tan
 * @CreateDate: 2021/8/11
 **/
public class VideoMergeUtil {

    /** mp4 转换为ts 格式 命令 参数1 输入视频路径 参数2 输出视频路径 */
    public final  static  String CONVERT_TS_TYPE_COMMAND="-i  %s   %s";
    /** 视频合并命令 参数1 待合并视频1路径 参数2 待合并视频2路径 参数3 合并完成视频路径 -profile:v high -level:v 4.1 这是设置视频画面质量 -s 设置视频的宽高 -aspect 设置视频的从横比 这个非常重要 不然你设置了宽高你会发现播放 视频还是变形的 -r 这个是设置视频帧率  */
    public final  static  String VIDEO_MERGE_COMMAND="-i \"concat:%s|%s\" -s 720x1280 -aspect \"0.5625\"  -profile:v high -level:v 4.1  -r 60 %s";
    /** 视频保存目录名 */
    public final  static String VIDEO_SAVE_DIRECTORY="video";

    /**
     * 视频合并
     * @Author: Tan
     * @Date: 2021/8/12
     * @param videoOne: MP4 视频文件1
     * @param videoTwo: MP4 视频文件2
     * @return: byte[]
     **/
    public static byte[]  videoMerge(byte[] videoOne,byte[] videoTwo) throws InterruptedException {
        //先保存视频到相对路径 resources 目录下
        String mp4Video1 = saveFileToResources(videoOne, VIDEO_SAVE_DIRECTORY, getTimestampFileName(".mp4"));
        String mp4Video2 = saveFileToResources(videoTwo, VIDEO_SAVE_DIRECTORY, getTimestampFileName(".mp4"));
        String tsVideo1=mp4Video1.replace(".mp4",".ts");
        String tsVideo2=mp4Video2.replace(".mp4",".ts");
        // MP4视频 转ts 格式
        String cmd=String.format(CONVERT_TS_TYPE_COMMAND,mp4Video1,tsVideo1);
        FfmpegCmdUtil.cmdExecut(cmd);
        cmd=String.format(CONVERT_TS_TYPE_COMMAND,mp4Video2,tsVideo2);
        FfmpegCmdUtil.cmdExecut(cmd);
        //ts格式合并视频
        String outVideo=getResourceAbsolutePath(VIDEO_SAVE_DIRECTORY)+"\\"+getTimestampFileName(".mp4");
        cmd=String.format(VIDEO_MERGE_COMMAND,tsVideo1,tsVideo2,outVideo);
        FfmpegCmdUtil.cmdExecut(cmd);
        //读取视频 返回byte[]
        byte[] data=readFile(outVideo);
        //删除视频
        deleteFileList(Arrays.asList(mp4Video1,mp4Video2,tsVideo1,tsVideo2,outVideo));
        return data;
    }

    /**
     * 获取时间戳文件名
     * @Author: Tan
     * @Date: 2021/8/12
     * @param fileType:
     * @return: java.lang.String
     **/
    public static String getTimestampFileName(String fileType){
        return System.currentTimeMillis()+fileType;
    }

    /**
     * 删除视频
     * @Author: Tan
     * @Date: 2021/8/12
     * @param fileList:
     * @return: void
     **/
    public static void deleteFileList(List<String> fileList){
        fileList.forEach(item->{
            FileUtil.del(item);
        });

    }


    /**
     * 读取文件
     * @Author: Tan
     * @Date: 2021/8/12
     * @param path:
     * @return: byte[]
     **/
    public static byte[] readFile(String path){
      return   FileUtil.readBytes(path);
    }

    /**
     * 保存指定文件到resource的目录下
     * @Author: Tan
     * @Date: 2021/8/12
     * @param data: 数据
    * @param directoryName:  目录名
    * @param fileName:  文件名
     * @return: java.lang.String
     **/
    public static String saveFileToResources(byte[] data,String directoryName,String fileName){
        String path=getResourceAbsolutePath(directoryName);
        File file=new File(path+"/"+fileName);
        FileUtil.writeBytes(data,file);
        return file.getAbsolutePath();
    }

    /**
     * 获取resources目录下的指定目录的绝对路径
     * @Author: Tan
     * @Date: 2021/8/12
     * @param directoryName: 目录名
     * @return: java.lang.String
     **/
    public static String getResourceAbsolutePath(String directoryName){
        String osName=System.getProperty("os.name");
        String userName=System.getProperty("user.name");
        String path="C:\\Users\\"+userName+"\\Desktop\\"+directoryName;
        if("Liunx".equals(osName)){
           path="/home/"+directoryName;
        }
        File directory=new File(path);
        directory.mkdirs();
        return path;
    }






}
