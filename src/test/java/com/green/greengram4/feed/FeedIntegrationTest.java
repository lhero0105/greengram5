package com.green.greengram4.feed;


import com.green.greengram4.BaseIntegrationTest;
import com.green.greengram4.common.ResVo;
import com.green.greengram4.feed.model.FeedInsDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FeedIntegrationTest extends BaseIntegrationTest {

    @Test
    @Rollback(false)
    public void postFeed() throws Exception {
        FeedInsDto dto = new FeedInsDto();
        dto.setIuser(3);
        dto.setContents("통합 테스트 작업 중3");
        dto.setLocation("그린컴퓨터학원3");

        List<String> pics = new ArrayList();
        //dto.setPics(pics);
        pics.add("https://search.pstatic.net/common/?src=http%3A%2F%2Fimgnews.naver.net%2Fimage%2F5268%2F2023%2F01%2F30%2F0001751386_001_20230130092601625.jpg&type=sc960_832");
        pics.add("https://search.pstatic.net/common/?src=http%3A%2F%2Fimgnews.naver.net%2Fimage%2F003%2F2023%2F12%2F27%2FNISI20231227_0020174760_web_20231227100846_20231227101024426.jpg&type=sc960_832");

        String json = om.writeValueAsString(dto);
        System.out.println("json: " + json);

        MvcResult mr = mvc.perform(
                MockMvcRequestBuilders
                        .post("/api/feed")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )
        .andExpect(status().isOk())
        .andDo(print())
        .andReturn();

        String content = mr.getResponse().getContentAsString();
        ResVo vo = om.readValue(content, ResVo.class);
        assertEquals(true, vo.getResult() > 0);
    }

    @Test
    @Rollback(false)
    public void delFeed() throws Exception {
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap();
        requestParams.add("ifeed", "219");
        requestParams.add("iuser", "2");

        MvcResult mr = mvc.perform(
                        MockMvcRequestBuilders
                                //.delete("/api/feed?ifeed=220&iuser=3")
                                //.delete("/api/feed?ifeed={ifeed}&iuser={iuser}", "220", "3")
                                .delete("/api/feed")
                                .params(requestParams)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String content = mr.getResponse().getContentAsString();
        ResVo vo = om.readValue(content, ResVo.class);
        assertEquals(1, vo.getResult());
    }
}
