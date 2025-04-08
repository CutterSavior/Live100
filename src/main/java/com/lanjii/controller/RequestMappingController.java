package com.lanjii.controller;

import com.lanjii.core.obj.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 获取本服务所有接口
 *
 * @author LiZheng
 * @date 2024-10-30
 */
@Slf4j
@RestController
@RequestMapping("/admin/request-mapping")
@RequiredArgsConstructor
public class RequestMappingController {

    private final WebApplicationContext applicationContext;

    /**
     * 获取所有路由映射关系
     *
     * @return
     */
    @GetMapping("list")
    public R<List<String>> getAllRouteMapping() {
        Map<String, RequestMappingHandlerMapping> beansOfType = applicationContext.getBeansOfType(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> requestMappingHandlerMapping = beansOfType.get("requestMappingHandlerMapping").getHandlerMethods();
        List<String> urls = new ArrayList<>();
        requestMappingHandlerMapping.keySet().forEach(requestMappingInfo -> {
            requestMappingInfo.getPathPatternsCondition().getPatterns().forEach(pathPattern -> {
                String url = pathPattern.getPatternString();
                if (url.startsWith("/admin/")) {
                    urls.add(url);
                }
            });
        });
        return R.success(urls);
    }

}
