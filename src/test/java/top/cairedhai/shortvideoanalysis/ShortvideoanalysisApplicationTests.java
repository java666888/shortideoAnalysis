package top.cairedhai.shortvideoanalysis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.cairedhai.shortvideoanalysis.service.impl.DyVideoResolveServiceImpl;

@SpringBootTest
class ShortvideoanalysisApplicationTests {

    @Autowired
    private DyVideoResolveServiceImpl dyVideoResolveService;

    @Test
    void contextLoads() {
        System.out.println(dyVideoResolveService.videoResolve("https://v.douyin.com/evb825L/"));
    }


}
