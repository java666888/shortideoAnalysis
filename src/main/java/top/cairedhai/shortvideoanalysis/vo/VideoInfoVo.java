package top.cairedhai.shortvideoanalysis.vo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @Description: TODO
 * @Author: Tan
 * @CreateDate: 2021/7/27
 **/
@Data
@Builder
@ToString
public class VideoInfoVo {

    private String frontCoverUrl;

    private String videoUrl;

    private String  synopsis;

    private UserInfoVo userInfo;

}
