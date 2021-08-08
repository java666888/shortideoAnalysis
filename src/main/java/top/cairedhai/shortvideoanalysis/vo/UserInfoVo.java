package top.cairedhai.shortvideoanalysis.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @Description: TODO
 * @Author: Tan
 * @CreateDate: 2021/7/28
 **/
@Data
@Builder
public class UserInfoVo {

    private String userId;

    private String userNickName;

    private String userAccount;

}
