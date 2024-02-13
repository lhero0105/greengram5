package com.green.greengram4.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram4.exception.ErrorResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
//@RestControllerAdvice(basePackages = "com.green.greengram4")
// 일단 학습할 때 꺼놓습니다.
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {
// 리턴하는 구조가 다 다를 수 밖에 없는데 컨트롤러가 리턴하기전에 작업을 통일시킬 수 있습니다.
    private final ObjectMapper om;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        //컨트롤러의 반환값이 객체일 때만 return true
        return MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        HttpServletResponse servletResponse =
                ((ServletServerHttpResponse) response).getServletResponse();
        int status = servletResponse.getStatus();
        HttpStatus resolve = HttpStatus.resolve(status);
        String path = request.getURI().getPath();
        if(resolve != null) {
            if(resolve.is2xxSuccessful()) {
                //ApiResponse에 통일 시킬 멘트를 적습니다
                return ApiResponse.builder()
                        .path(path)
                        .data(body)
                        .code("Success")
                        .message("통신 성공")
                        .build();
            } else if(body instanceof ErrorResponse) {
                Map<String, Object> map = om.convertValue(body, Map.class);
                map.put("path", path);
                return map;
            }
        }
        return body;
    }
}
