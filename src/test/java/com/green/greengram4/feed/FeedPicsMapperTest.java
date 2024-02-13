package com.green.greengram4.feed;

import com.green.greengram4.feed.model.FeedDelDto;
import com.green.greengram4.feed.model.FeedInsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FeedPicsMapperTest {

    private FeedInsDto dto;

    public FeedPicsMapperTest() {
        this.dto = new FeedInsDto();
        this.dto.setIfeed(6);

        List<String> pics = new ArrayList();

        //this.dto.setPics(pics);
        pics.add("a.jpg");
        pics.add("b.jpg");
        pics.add("c.jpg");
    }

    @Autowired private FeedPicsMapper mapper;

    @BeforeEach
    public void beforeEach() {
        FeedDelDto delDto = new FeedDelDto();
        delDto.setIfeed(this.dto.getIfeed());
        delDto.setIuser(2);
        int affectedRows = mapper.delFeedPicsAll(delDto);
        System.out.println("delRows : " + affectedRows);
    }

//    @Test
//    void insFeedPics() {
//        List<String> preList = mapper.selFeedPicsAll(dto.getIfeed());
//        assertEquals(0, preList.size());
//
//        int insAffectedRows = mapper.insFeedPics(dto);
//        assertEquals(dto.getPics().size(), insAffectedRows);
//
//        List<String> afterList = mapper.selFeedPicsAll(dto.getIfeed());
//        assertEquals(dto.getPics().size(), afterList.size());
//
//        for(int i=0; i<dto.getPics().size(); i++) {
//            assertEquals(dto.getPics().get(i), afterList.get(i));
//        }
//    }

}