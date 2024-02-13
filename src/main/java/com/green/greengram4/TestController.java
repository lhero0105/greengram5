package com.green.greengram4;

import com.green.greengram4.common.ResVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pic")
public class TestController {

    @GetMapping("/feed")
    public ResVo test() {
        return new ResVo(1);
    }
}
