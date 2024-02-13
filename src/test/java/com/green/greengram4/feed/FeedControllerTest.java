package com.green.greengram4.feed;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram4.CharEncodingConfig;
import com.green.greengram4.common.ResVo;
import com.green.greengram4.feed.model.FeedInsDto;
import com.green.greengram4.feed.model.FeedSelVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@MockMvcConfig
@Import(CharEncodingConfig.class)
@WebMvcTest(FeedController.class)
class FeedControllerTest {

    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper mapper;

    @MockBean private FeedService service;
/*
    @Test
    void postFeed() throws Exception {
        ResVo result = new ResVo(7);
        given(service.postFeed(any())).willReturn(result);

        FeedInsDto dto = new FeedInsDto();
        String json = mapper.writeValueAsString(dto);
        System.out.println("json: " + json);
        mvc.perform(
                MockMvcRequestBuilders
                        .post("/api/feed")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(mapper.writeValueAsString(result)))
        .andDo(print());

        verify(service).postFeed(any());
    }
*/
    @Test
    void getFeedAll() throws Exception {
        //given - when - then
        List<FeedSelVo> list = new ArrayList<>();
        FeedSelVo item1 = new FeedSelVo();
        item1.setIfeed(1);
        item1.setContents("안녕하세요.");

        FeedSelVo item2 = new FeedSelVo();
        item2.setIfeed(2);
        item2.setContents("반가워요.");

        list.add(item1);
        list.add(item2);

        given(service.getFeedAll(any())).willReturn(list);

        String json = mapper.writeValueAsString(list);

        MultiValueMap<String, String> params = new LinkedMultiValueMap();
        params.add("page", "3");
        params.add("loginedIuser", "4");

        mvc.perform(
                MockMvcRequestBuilders
                        .get("/api/feed")
                        //.params(params)

        )
                .andDo(print())
                .andExpect(content().string(json));

        verify(service).getFeedAll(any());
    }

    @Test
    void delFeed() {}

    @Test
    void toggleFeedFav() {
    }
}