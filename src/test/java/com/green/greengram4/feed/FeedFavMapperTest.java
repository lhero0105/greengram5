package com.green.greengram4.feed;

import com.green.greengram4.feed.model.FeedDelDto;
import com.green.greengram4.feed.model.FeedFavDto;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class FeedFavMapperTest {

    @Autowired
    private FeedFavMapper mapper;

    @Test
    public void insFeedFavTest() {
        FeedFavDto dto = new FeedFavDto();
        dto.setIfeed(6);
        dto.setIuser(4);

        List<FeedFavDto> preResult1 = mapper.selFeedFavForTest(dto);
        assertEquals(0, preResult1.size(), "첫번째 insert전 미리 확인");

        int affectedRows1 = mapper.insFeedFav(dto);
        assertEquals(1, affectedRows1, "첫번째 insert");

        List<FeedFavDto> result = mapper.selFeedFavForTest(dto);
        assertEquals(1, result.size(), "첫번째 insert 확인");

        dto.setIfeed(212);
        dto.setIuser(1);

        int affectedRows2 = mapper.insFeedFav(dto);
        assertEquals(1, affectedRows2, "두번째 insert");

        List<FeedFavDto> result2 = mapper.selFeedFavForTest(dto);
        assertEquals(1, result2.size(), "두번째 insert 확인");
    }

    @Test
    public void delFeedFavTest() {
        FeedFavDto dto = new FeedFavDto();
        dto.setIfeed(203);
        dto.setIuser(4);

        int affectedRows1 = mapper.delFeedFav(dto);
        assertEquals(1, affectedRows1);

        int affectedRows2 = mapper.delFeedFav(dto);
        assertEquals(0, affectedRows2);

        List<FeedFavDto> result2 = mapper.selFeedFavForTest(dto);
        assertEquals(0, result2.size());
    }

    @Test
    public void delFeedFavAllTest() {
        final int ifeed = 203;

        FeedFavDto selDto = new FeedFavDto();
        selDto.setIfeed(ifeed);
        List<FeedFavDto> selList = mapper.selFeedFavForTest(selDto);

        FeedDelDto dto = new FeedDelDto();
        dto.setIfeed(ifeed);
        int delAffectedRows = mapper.delFeedFavAll(dto);
        assertEquals(selList.size(), delAffectedRows);

        List<FeedFavDto> selList2 = mapper.selFeedFavForTest(selDto);
        assertEquals(0, selList2.size());

    }
}