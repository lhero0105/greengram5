package com.green.greengram4.common;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ExtendWith(SpringExtension.class)
@Import({ MyFileUtils.class })
@TestPropertySource(properties = {
        "file.dir=D:/ggg/download",
})
public class MyFileUtilsTest {

    @Autowired
    private MyFileUtils myFileUtils;

    @Test
    public void makeFolderTest() {
        String path = "/yyy22";
        File preFolder = new File(myFileUtils.getUploadPrefixPath(), path);
        assertEquals(false, preFolder.exists());

        String newPath = myFileUtils.makeFolders(path);
        File newFolder = new File(newPath);
        assertEquals(preFolder.getAbsolutePath(), newFolder.getAbsolutePath());
        assertEquals(true, newFolder.exists());
    }

    @Test
    public void getRandomFileNmTest() {
        String fileNm = myFileUtils.getRandomFileNm();
        System.out.println("fileNm: " + fileNm);
        assertNotNull(fileNm);
        assertNotEquals("", fileNm);
    }

    @Test
    public void getExtTest() {
        String fileNm = "abc.efg.eee.jpg";
        String ext = myFileUtils.getExt(fileNm);
        assertEquals(".jpg", ext);

        String fileNm2 = "jjj-asdfasdf.pnge";
        String ext2 = myFileUtils.getExt(fileNm2);
        assertEquals(".pnge", ext2);
    }

    @Test
    public void getRandomFileNm2() {
        String fileNm1 = "반갑다.친구야.jpeg";
        String rFileNm1 = myFileUtils.getRandomFileNm(fileNm1);
        System.out.println("rFileNm1: " + rFileNm1);
        //랜덤문자열.jpeg

        String fileNm2 = "크크크.ㄴ미ㅏㅇ럼너ㅣㅏ.qq";
        String rFileNm2 = myFileUtils.getRandomFileNm(fileNm2);
        System.out.println("rFileNm2: " + rFileNm2);
        //랜덤문자열.qq
    }

    @Test
    public void transferToTest() throws Exception {
        String fileNm = "2c31f753-fd27-4fd5-beed-b7f88f127cc3.png";
        String filePath = "D:/home/download/diaryPics/25/" + fileNm;
        FileInputStream fis = new FileInputStream(filePath);
        MultipartFile mf = new MockMultipartFile("pic", fileNm, "png", fis);

        String saveFileNm = myFileUtils.transferTo(mf, "/feed/10");
        log.info("myFileUtils: {}", saveFileNm);
    }

}
